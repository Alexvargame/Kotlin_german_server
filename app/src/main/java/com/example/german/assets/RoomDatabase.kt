package com.example.german.assets

val db = Room.databaseBuilder(
    context,
    AppDatabase::class.java,
    "app.db"
)
    .createFromAsset("old_database.db")
    .build()