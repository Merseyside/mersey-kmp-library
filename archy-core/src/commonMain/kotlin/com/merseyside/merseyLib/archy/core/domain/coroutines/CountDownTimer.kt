package com.merseyside.merseyLib.archy.core.domain.coroutines

/*
* https://github.com/Kotlin/kotlinx.coroutines/issues/2171
 */


import android.util.Log
import com.merseyside.merseyLib.utils.core.ext.delay
import com.merseyside.merseyLib.utils.core.time.Seconds
import com.merseyside.merseyLib.utils.core.time.TimeUnit
import com.merseyside.merseyLib.utils.core.time.minus
import kotlinx.coroutines.*

class CountDownTimer(
    private val listener: CoroutineTimerListener,
    private val delay: TimeUnit = Seconds(1),
    private val scope: CoroutineScope = CoroutineScope(Job() + Dispatchers.Unconfined)) {

    private var timerJob: Job? = null
    private var countDownTimer: TimeUnit = TimeUnit.getEmpty()

    var state: CurrentTimerState = CurrentTimerState.STOPPED
        private set(value) {
            if (field == CurrentTimerState.DESTROYED) {
                return
            }
            field = value
        }

    fun startTimer(countDownTimer: TimeUnit) {
        this.countDownTimer = countDownTimer
        when (state) {
            CurrentTimerState.RUNNING -> {
                listener.onTick(countDownTimer, TimerException(TimerErrorTypes.ALREADY_RUNNING))
            }
            CurrentTimerState.PAUSED -> {
                listener.onTick(countDownTimer, TimerException(TimerErrorTypes.CURRENTLY_PAUSED))
            }
            CurrentTimerState.DESTROYED -> {
                listener.onTick(countDownTimer, TimerException(TimerErrorTypes.DESTROYED))
            }
            else -> {
                timerCanStart()
            }
        }
    }

    fun stopTimer() {
        val error = if (state == CurrentTimerState.STOPPED) {
            TimerException(TimerErrorTypes.NO_TIMER_RUNNING)
        } else { null }
        timerJob?.cancel()
        state = CurrentTimerState.STOPPED
        listener.onStop(error)
    }

    fun pauseTimer() {
        if (state == CurrentTimerState.PAUSED) {
            Log.e(TAG, "Already paused, check your code for multiple callers")
        }
        state = CurrentTimerState.PAUSED
    }

    fun continueTimer() {
        if (state == CurrentTimerState.RUNNING) {
            Log.e(TAG, "Already running, check your code for multiple callers")
        }
        state = CurrentTimerState.RUNNING
        listener.onContinue()
    }

    fun destroyTimer() {
        scope.cancel("Timer was now destroyed. Need a new instance to work")
        listener.onDestroy()
        state = CurrentTimerState.DESTROYED
    }

    private fun timerCanStart() {
        timerJob = scope.launch {
            withContext(Dispatchers.Unconfined) {
                state = CurrentTimerState.RUNNING

                onTick(countDownTimer)
                delay(delay)

                timerLoop@ while (isActive) {
                    countDownTimer -= delay

                    if (countDownTimer.isEmpty()) {
                        state = CurrentTimerState.STOPPED

                        onTick(TimeUnit.getEmpty())
                        listener.onStop()
                    } else {
                        onTick(countDownTimer)

                        if (countDownTimer < delay) {
                            delay(countDownTimer)
                        } else {
                            delay(delay)
                        }
                    }
                }
            }
        }
    }

    private suspend fun onTick(timeLeft: TimeUnit, error: Exception? = null) = withContext(Dispatchers.Main) {
        listener.onTick(timeLeft, error)
    }

    companion object {
        const val TAG = "CoroutineTimer"
    }
}

interface CoroutineTimerListener {
    fun onTick(timeLeft: TimeUnit, error: Exception? = null)
    fun onStop(error: Exception? = null) {}
    fun onContinue() {}
    fun onPause(remainingTime: TimeUnit) {}
    fun onDestroy() {}
}

enum class CurrentTimerState {
    RUNNING, PAUSED, STOPPED, DESTROYED
}

enum class TimerErrorTypes(val message: String) {
    ALREADY_RUNNING("This instance of the timer is already running, create a new instance or stop your current one"),
    CURRENTLY_PAUSED("This timer is currently paused. Choose to continue or stop to start over"),
    NO_TIMER_RUNNING("You are trying to stop or pause a timer that isn't running"),
    DESTROYED("This timer is destroyed and can't be used anymore")
}

private class TimerException(val type: TimerErrorTypes): Exception(type.message)