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
        Log.d("TEST_DB_roles", " Context ${context}")
        //AppDatabase.resetInstance()
        //context.deleteDatabase("app_database_name.db")


        val db = AppDatabase.getInstance(context)
       // Log.d("TEST_DB", "DB path: ${context.getDatabasePath("app_database_name.db")}")
        val userRoleDao = db.userRoleDao()
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("TEST_DB_roles", "testAllWordRelatedTables() started")

            /*val UserRoleAdmin = UserRole(name = "Admin", description = "Admin")
            userRoleDao.insert(UserRoleAdmin)
            Log.d("ADD_USER_ROLE", "Новая  role вставлена")
            val UserRoleUser = UserRole(name = "User", description = "User")
            userRoleDao.insert(UserRoleUser)
            Log.d("ADD_USER_ROLE", "Новая  role вставлена")
            */
            try {
                val userRoles = userRoleDao.getAll()
                userRoles.forEach {
                    Log.d("TEST_DB", "USER_ROLE: ${it.name} / ${it.description}/ ${it.id}")
                }
            } catch (e: Exception) {
                Log.e("TEST_DB_FATAL", "Ошибка при запросе ролей", e)
            }

        }
    }
}


