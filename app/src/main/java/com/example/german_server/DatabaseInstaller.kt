package com.example.german_server

import android.content.Context
import java.io.FileOutputStream

object DatabaseInstaller {

    fun installIfNeeded(context: Context, dbName: String) {
        val dbFile = context.getDatabasePath(dbName)

        if (dbFile.exists()) {
            return // база уже есть — НИЧЕГО НЕ ДЕЛАЕМ
        }

        dbFile.parentFile?.mkdirs()

        context.assets.open(dbName).use { input ->
            FileOutputStream(dbFile).use { output ->
                input.copyTo(output)
            }
        }
    }
}