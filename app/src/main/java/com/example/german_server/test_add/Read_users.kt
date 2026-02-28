package com.example.german_server.test_add

import android.content.Context
import android.util.Log
import com.example.german_server.data.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Read_users(private val context: Context) {


    fun readusers() {
   

        Log.d("TEST_DB_users", " Context ${context}")
        //AppDatabase.resetInstance()
        //context.deleteDatabase("app_database_name.db")


        val db = AppDatabase.getInstance(context)
        Log.d("TEST_APP_DB_users", "DB path: ${context.getDatabasePath("app.db")}")
        val BaseUserDao = db.baseUserDao()
        val userRoleDao = db.userRoleDao()

        CoroutineScope(Dispatchers.IO).launch {

            val users = BaseUserDao.getAll()
            users.forEach {
                Log.d("TEST_DB", "USERS_1: ${it.username} / ${it.email} " +
                        " Name /${it.avatarName} / Path ${it.avatarPath}" +
                        "/${it.shockmodBegin} / ${it.shockmodLong}" +
                        "/${it.serverUid} / ${it.loginToken} /" +
                            "Lustsession ${it.shockmodNow} " +
                        "avatar_change ${it.avatarLastChanged}"
                                   )

            }

        }
    }
}


