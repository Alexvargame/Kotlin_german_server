package com.example.german_server.test_add

import android.content.Context
import android.util.Log
import com.example.german_server.data.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Read_avatars(private val context: Context) {


    fun readavatars() {
   

        Log.d("TEST_DB_avatars", " Context ${context}")
        //AppDatabase.resetInstance()
        //context.deleteDatabase("app_database_name.db")


        val db = AppDatabase.getInstance(context)
        Log.d("TEST_APP_DB_avatars", "DB path: ${context.getDatabasePath("app.db")}")
        val avatarDao = db.userAvatarDao()

        CoroutineScope(Dispatchers.IO).launch {

            val avatars = avatarDao.getAllGalleryAvatars()
            Log.d("TEST_DB_AVATAR", "USERS_1: ${avatars}")
            avatars.forEach {
                Log.d("TEST_DB_AVATAR", "USERS_1: ${it.id} / ${it.userId} " +
                        "/${it.path} /${it.isActive}")

            }

        }
    }
}


