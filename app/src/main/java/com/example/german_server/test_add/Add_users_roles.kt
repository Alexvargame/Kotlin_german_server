package com.example.german_server.test_add

import android.content.Context
import android.util.Log
import com.example.german_server.data.AppDatabase
import com.example.german_server.data.entities.UserRole
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Add_users_roles(private val context: Context) {

    fun addusersroles() {
        Log.d("TEST_DB", " Context ${context}")
        //AppDatabase.resetInstance()
        //context.deleteDatabase("app_database_name.db")


        val db = AppDatabase.getInstance(context)
       // Log.d("TEST_DB", "DB path: ${context.getDatabasePath("app_database_name.db")}")
        val userRoleDao = db.userRoleDao()
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("TEST_DB", "testAllWordRelatedTables() started")

            val UserRoleAdmin = UserRole(name = "Admin", description = "Admin")
            userRoleDao.insert(UserRoleAdmin)
            Log.d("ADD_USER_ROLE", "Новая  role вставлена")
            val UserRoleUser = UserRole(name = "User", description = "User")
            userRoleDao.insert(UserRoleUser)
            Log.d("ADD_USER_ROLE", "Новая  role вставлена")

            val userRoles = userRoleDao.getAll()
            userRoles.forEach {
                Log.d("TEST_DB", "USER_ROLE: ${it.name} / ${it.description}/ ${it.id}")
            }
        }
    }
}


