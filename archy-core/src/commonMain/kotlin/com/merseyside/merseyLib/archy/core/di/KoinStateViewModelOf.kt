//@file:OptIn(KoinInternalApi::class)
//
//package com.merseyside.merseyLib.archy.core.presentation.di
//
//import com.merseyside.merseyLib.archy.core.presentation.model.StateViewModel
//import org.koin.core.annotation.KoinInternalApi
//import org.koin.core.definition.BeanDefinition
//import org.koin.core.module.KoinDefinition
//import org.koin.core.module._scopedInstanceFactory
//import org.koin.core.module.dsl.new
//import org.koin.core.module.dsl.stateViewModelOf
//import org.koin.core.module.dsl.setupInstance
//import org.koin.dsl.ScopeDSL
//
//inline fun <reified R: StateViewModel> ScopeDSL.stateViewModelOf(
//    crossinline constructor: () -> R,
//    options: BeanDefinition<R>.() -> Unit
//): KoinDefinition<R> = module.setupInstance(_scopedInstanceFactory(definition = { new(constructor) }, scopeQualifier = scopeQualifier), options)
//
///**
// * @see stateViewModelOf
// */
//inline fun <reified R> ScopeDSL.stateViewModelOf(
//    crossinline constructor: () -> R,
//): KoinDefinition<R> = scoped { new(constructor) }
//
///**
// * @see stateViewModelOf
// */
//inline fun <reified R, reified T1> ScopeDSL.stateViewModelOf(
//    crossinline constructor: (T1) -> R,
//    options: BeanDefinition<R>.() -> Unit
//): KoinDefinition<R> = module.setupInstance(_scopedInstanceFactory(definition = { new(constructor) }, scopeQualifier = scopeQualifier), options)
//
///**
// * @see stateViewModelOf
// */
//inline fun <reified R, reified T1> ScopeDSL.stateViewModelOf(
//    crossinline constructor: (T1) -> R,
//): KoinDefinition<R> = scoped { new(constructor) }
//
///**
// * @see stateViewModelOf
// */
//inline fun <reified R, reified T1, reified T2> ScopeDSL.stateViewModelOf(
//    crossinline constructor: (T1, T2) -> R,
//    options: BeanDefinition<R>.() -> Unit
//): KoinDefinition<R> = module.setupInstance(_scopedInstanceFactory(definition = { new(constructor) }, scopeQualifier = scopeQualifier), options)
//
///**
// * @see stateViewModelOf
// */
//inline fun <reified R, reified T1, reified T2> ScopeDSL.stateViewModelOf(
//    crossinline constructor: (T1, T2) -> R,
//): KoinDefinition<R> = scoped { new(constructor) }
//
///**
// * @see stateViewModelOf
// */
//inline fun <reified R, reified T1, reified T2, reified T3> ScopeDSL.stateViewModelOf(
//    crossinline constructor: (T1, T2, T3) -> R,
//    options: BeanDefinition<R>.() -> Unit
//): KoinDefinition<R> = module.setupInstance(_scopedInstanceFactory(definition = { new(constructor) }, scopeQualifier = scopeQualifier), options)
//
///**
// * @see stateViewModelOf
// */
//inline fun <reified R, reified T1, reified T2, reified T3> ScopeDSL.stateViewModelOf(
//    crossinline constructor: (T1, T2, T3) -> R,
//): KoinDefinition<R> = scoped { new(constructor) }
//
///**
// * @see stateViewModelOf
// */
//inline fun <reified R, reified T1, reified T2, reified T3, reified T4> ScopeDSL.stateViewModelOf(
//    crossinline constructor: (T1, T2, T3, T4) -> R,
//    options: BeanDefinition<R>.() -> Unit
//): KoinDefinition<R> = module.setupInstance(_scopedInstanceFactory(definition = { new(constructor) }, scopeQualifier = scopeQualifier), options)
//
///**
// * @see stateViewModelOf
// */
//inline fun <reified R, reified T1, reified T2, reified T3, reified T4> ScopeDSL.stateViewModelOf(
//    crossinline constructor: (T1, T2, T3, T4) -> R,
//): KoinDefinition<R> = scoped { new(constructor) }
//
///**
// * @see stateViewModelOf
// */
//inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5> ScopeDSL.stateViewModelOf(
//    crossinline constructor: (T1, T2, T3, T4, T5) -> R,
//    options: BeanDefinition<R>.() -> Unit
//): KoinDefinition<R> = module.setupInstance(_scopedInstanceFactory(definition = { new(constructor) }, scopeQualifier = scopeQualifier), options)
//
///**
// * @see stateViewModelOf
// */
//inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5> ScopeDSL.stateViewModelOf(
//    crossinline constructor: (T1, T2, T3, T4, T5) -> R,
//): KoinDefinition<R> = scoped { new(constructor) }
//
///**
// * @see stateViewModelOf
// */
//inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6> ScopeDSL.stateViewModelOf(
//    crossinline constructor: (T1, T2, T3, T4, T5, T6) -> R,
//    options: BeanDefinition<R>.() -> Unit
//): KoinDefinition<R> = module.setupInstance(_scopedInstanceFactory(definition = { new(constructor) }, scopeQualifier = scopeQualifier), options)
//
///**
// * @see stateViewModelOf
// */
//inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6> ScopeDSL.stateViewModelOf(
//    crossinline constructor: (T1, T2, T3, T4, T5, T6) -> R,
//): KoinDefinition<R> = scoped { new(constructor) }
//
///**
// * @see stateViewModelOf
// */
//inline fun <reified R, reified T1, reified T2, reified T3, reified T4, reified T5, reified T6, reified T7> ScopeDSL.stateViewModelOf(
//    crossinline constructor: (T1, T2, T3, T4, T5, T6, T7) -> R,
//    options: BeanDefinition<R>.() -> Unit
//): KoinDefinition<R> = module.setupInstance(_scopedInstanceFactory(definition = { new(constructor) }, scopeQualifier = scopeQualifier), options)
//
