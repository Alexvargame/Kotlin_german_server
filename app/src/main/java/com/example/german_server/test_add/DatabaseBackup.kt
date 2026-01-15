package com.example.german_server.test_add

import android.content.Context
import java.io.File
import com.example.german_server.data.AppDatabase

object DatabaseBackup {

    fun backupDatabase(context: Context) {
        val dbFile = context.getDatabasePath("app.db")

        val backupDir = context.getExternalFilesDir(null)
            ?: throw IllegalStateException("External storage not available")

        val backupFile = File(backupDir, "app_backup.db")

        // 1. Закрываем Room
        AppDatabase.getInstance(context).close()

        // 2. Сбрасываем INSTANCE
        AppDatabase.resetInstance()

        // 3. Копируем файл
        dbFile.copyTo(backupFile, overwrite = true)
    }
    fun restoreDatabase(context: Context, backupFile: File) {
        val dbFile = context.getDatabasePath("app.db")

        AppDatabase.getInstance(context).close()
        AppDatabase.resetInstance()

        backupFile.copyTo(dbFile, overwrite = true)
    }

}
