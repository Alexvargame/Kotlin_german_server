package com.example.german_server

import android.os.Bundle
import androidx.navigation.compose.rememberNavController
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import java.util.Calendar
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import android.content.Context
import androidx.compose.runtime.remember
import java.io.FileInputStream
import java.io.FileOutputStream

import java.io.File



import com.example.german_server.data.AppDatabase
import com.example.german_server.data.network.RetrofitClient
import com.example.german_server.data.ui.viewModel.user_profile.UserViewModel
import com.example.german_server.data.ui.viewModel.autorization.AutorizationViewModel
import com.example.german_server.data.ui.viewModel.support_chat.SupportChatViewModel
import com.example.german_server.data.ui.viewModel.daily_quests.DailyQuestViewModel
import com.example.german_server.data.repository.user_profile.UserViewModelFactory
import com.example.german_server.data.repository.user_profile.UserProfileRepository
import com.example.german_server.data.repository.daily_quests.DailyQuestRepository
import com.example.german_server.data.repository.support_chat.SupportChatMessageRepository
import com.example.german_server.data.repository.autorization.AutorizationViewModelFactory
import com.example.german_server.data.repository.support_chat.SupportChatMessageViewModelFactory
import com.example.german_server.data.repository.daily_quests.DailyQuestViewModelFactory

import com.example.german_server.data.ui.components.UpdateQuestsAfterExerciseUseCase
import com.example.german_server.data.ui.components.ResetDailyQuests



import com.example.german_server.ui.navigation.appNavGraph

import com.example.german_server.test_add.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //TestDb(this).testLectionDao()
        Log.e("TEST", "APP STARTED")
        //TestAdjectiveRepository(this).testAdjectives()
        //Check_user_avatar(this).checkuseravatar()
        //TestDb_words(this).testAllWordRelatedTables()
        //TestDb_users_roles(this).testusersroles()
        //TestDb_messages(this).testmessages()
        Add_users_roles(this).addusersroles()
        Read_users(this).readusers()
        Read_avatars(this).readavatars()
        Read_quest(this).readquests()
        ResetDailyQuests(this).resetFlags()
       // Add_word_types(this).addwordtypes()
       // Add_books(this).addbooks()
        //Add_lections(this).addlections()
        //Add_articles(this).addarticles()
       // TestInsertWord(this).testInsertWord()
        //TestInsertWordBVerb(this).testInsertWord()
        //TestInsertWordBAdjective(this).testInsertWord()
        //TestInsertNumeral(this).insertOneNumeral()
        //TestInsertPronoun(this).insertPronoun()
        //TestInsertOtherWord(this).insertOtherWord()
        //TestInsertWordNounDeclensions(this).testInsertNounDecl()
        //val source = "/data/data/com.your.package.name/databases/app.db"
        //val destination = "/storage/emulated/0/Download/app_backup.db" // Пример пути к скачиваемым файлам
        //copyDatabaseFile(source, destination)

        val hours = 18
        val greetingText = if (getCurrentHour() < hours) {
            "Добрый день"
        } else {
            "Добрый вечер"
        }
        //DatabaseInstaller.installIfNeeded(this, "app_main.db")
        setContent {
            val context = LocalContext.current
            val db = AppDatabase.getInstance(context)
            val userDao = db.baseUserDao()
            val avatarDao = db.userAvatarDao()
            val repo =
                UserProfileRepository(
                    RetrofitClient.apiService,
                    AppDatabase.getInstance(context).baseUserDao(),
                    AppDatabase.getInstance(context).userAvatarDao(),
                    )

            val repo_chat =
                SupportChatMessageRepository(
                    AppDatabase.getInstance(context).supportChatMessageDao(),
                    AppDatabase.getInstance(context).baseUserDao(),
                    RetrofitClient.apiService,
                )
            val repo_daily_quest =
                DailyQuestRepository(
                    AppDatabase.getInstance(context).dailyQuestDao(),
                    AppDatabase.getInstance(context).baseUserDao(),
                    RetrofitClient.apiService,
                )
            val updateQuestsAfterExerciseUseCase =
                UpdateQuestsAfterExerciseUseCase(
                    repo_daily_quest
                )
            val prefs = remember {
                context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            }
            val userProfileViewModel: UserViewModel = viewModel(
                factory = UserViewModelFactory(userDao, avatarDao,repo, prefs, updateQuestsAfterExerciseUseCase)
            )
            val autorizationViewModel: AutorizationViewModel = viewModel(
                factory = AutorizationViewModelFactory(db)
            )
            val supportChatMessageViewModel: SupportChatViewModel = viewModel(
                factory = SupportChatMessageViewModelFactory(repo_chat)
            )
            val dailyQuestViewModel: DailyQuestViewModel = viewModel(
                factory = DailyQuestViewModelFactory(repo_daily_quest)
            )
            Log.e("USER_after", "${userProfileViewModel} ${supportChatMessageViewModel}")
            val navController = rememberNavController()

            //val userProfileViewModel: UserProfileViewModel = viewModel()   // Пробуем создать профиль для всех экранов
            appNavGraph(navController, userProfileViewModel,
                autorizationViewModel,
                supportChatMessageViewModel,
                dailyQuestViewModel,
                greetingText)
            Log.e("USER_after", "appNAvgatrph")
        }
    }
}


fun getCurrentHour(): Int {
    val calendar = Calendar.getInstance()
    return calendar.get(Calendar.HOUR_OF_DAY) // вернёт час от 0 до 23
}
