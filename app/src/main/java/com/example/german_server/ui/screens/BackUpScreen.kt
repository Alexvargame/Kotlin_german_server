package com.example.german_server.ui.screens

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.german_server.test_add.DatabaseBackup

import com.example.german_server.data.AppDatabase
import java.io.File


import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController


@Composable
fun BackUpscreen(
    navController: NavController,
) {

    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Spacer(Modifier.height(24.dp))
        Button(
            onClick = {
                DatabaseBackup.backupDatabase(context)
            }
        ) {
            Text("BACKUP DB")
        }
        Button(
            onClick = {
                val backupFile = File(context.getExternalFilesDir(null), "app_backup.db")
                if (backupFile.exists()) {
                    // Закрываем Room
                    AppDatabase.getInstance(context).close()
                    AppDatabase.resetInstance()

                    // Копируем backup в рабочую базу
                    val dbFile = context.getDatabasePath("app.db")
                    backupFile.copyTo(dbFile, overwrite = true)
                }
            }
        ) {
            Text("RESTORE DB")
        }

        Button (
            onClick = {
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Назад")
        }
        Button (
            onClick = {
                navController.navigate("home")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("На главную")
        }
    }
}