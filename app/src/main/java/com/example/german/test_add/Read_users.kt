package com.example.german.test_add

import android.content.Context
import android.util.Log
import com.example.german.data.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Read_users(private val context: Context) {


    fun readusers() {
        Log.d("TEST_DB", " Context ${context}")
        AppDatabase.resetInstance()
        context.deleteDatabase("app_database_name.db")


        val db = AppDatabase.getInstance(context)
        Log.d("TEST_DB", "DB path: ${context.getDatabasePath("app_database_name.db")}")
        val BaseUserDao = db.baseUserDao()
        val userRoleDao = db.userRoleDao()
        //BaseUserDao.deleteAll()
        //userRoleDao.deleteAll()
        CoroutineScope(Dispatchers.IO).launch {

            val users = BaseUserDao.getAll()
            users.forEach {
                Log.d("TEST_DB", "USERS: ${it.username} / ${it.email}")
            }

        }
    }
}


