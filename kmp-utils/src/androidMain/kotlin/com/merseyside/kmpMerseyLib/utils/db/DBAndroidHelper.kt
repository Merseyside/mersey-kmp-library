package com.merseyside.kmpMerseyLib.utils.db

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

class DBAndroidHelper(
    private val context: Context,
    private val sqlDriverSchema: SqlDriver.Schema,
    private val dbName: String,
    private val version: Int = 1
) {

    fun createDriver(): SqlDriver {

        val callback = object : SupportSQLiteOpenHelper.Callback(version) {
            override fun onCreate(db: SupportSQLiteDatabase) {
                val driver = AndroidSqliteDriver(db)
                sqlDriverSchema.create(driver)
            }

            override fun onUpgrade(db: SupportSQLiteDatabase, oldVersion: Int, newVersion: Int) {}
        }

        return AndroidSqliteDriver(
            schema = sqlDriverSchema,
            context = context,
            name = dbName,
            callback = callback
        )
    }
}