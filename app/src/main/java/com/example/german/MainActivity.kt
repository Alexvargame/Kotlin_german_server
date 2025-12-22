package com.example.german

import android.os.Bundle
import androidx.navigation.compose.rememberNavController
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import java.util.Calendar
import android.util.Log
import android.content.Context
import android.os.Environment

import java.io.FileInputStream
import java.io.FileOutputStream
import android.database.sqlite.SQLiteDatabase

import java.io.File




import com.example.german.data.ui.viewModel.user_profile.UserProfileViewModel
import com.example.german.TestInsertWord
import com.example.german.ui.navigation.appNavGraph


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //TestDb(this).testLectionDao()
        Log.e("TEST", "APP STARTED")
        //TestDb_words(this).testAllWordRelatedTables()
        //TestDb_users_roles(this).testusersroles()
        //TestDb_messages(this).testmessages()
        //Add_users_roles(this).addusersroles()
        Read_users(this).readusers()
        //Add_word_types(this).addwordtypes()
        //Add_books(this).addbooks()
        //Add_lections(this).addlections()
        //Add_articles(this).addarticles()
        //TestInsertWord(this).testInsertWord()
        //TestInsertWordBVerb(this).testInsertWord()
        //TestInsertWordBAdjective(this).testInsertWord()
        //TestInsertNumeral(this).insertOneNumeral()
        //TestInsertPronoun(this).insertPronoun()
        //TestInsertOtherWordl(this).insertOtherWord()
        //val source = "/data/data/com.your.package.name/databases/app.db"
        //val destination = "/storage/emulated/0/Download/app_backup.db" // Пример пути к скачиваемым файлам
        //copyDatabaseFile(source, destination)

        val hours = 18
        val greetingText = if (getCurrentHour() < hours) {
            "Добрый день"
        } else {
            "Добрый вечер"
        }
        setContent {
            val navController = rememberNavController()

            val userProfileViewModel: UserProfileViewModel = viewModel()   // Пробуем создать профиль для всех экранов
            appNavGraph(navController, userProfileViewModel, greetingText)

        }
    }
}


fun copyDatabaseFile(sourcePath: String, destinationPath: String) {
    val sourceFile = File(sourcePath)
    val destinationFile = File(destinationPath)

    FileInputStream(sourceFile).use { fis ->
        FileOutputStream(destinationFile).use { fos ->
            val buffer = ByteArray(1024)
            var length: Int
            while (fis.read(buffer).also { length = it } > 0) {
                fos.write(buffer, 0, length)
            }
        }
    }
}

fun getCurrentHour(): Int {
    val calendar = Calendar.getInstance()
    return calendar.get(Calendar.HOUR_OF_DAY) // вернёт час от 0 до 23
}
// -------------------------
// Preview-функция для Android Studio
// Позволяет увидеть UI прямо в редакторе, без запуска на телефоне
/*@Preview(showBackground = true)
@Composable
fun PreviewMyApp() {
    val navController = rememberNavController()  // фиктивный NavController для превью

    MyApp(navController = navController,  greetingText = "Добрый день")  // <-- вызов Composable функции для предпросмотра
}
*/