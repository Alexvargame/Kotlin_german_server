package com.example.german_server.data.ui.components

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatMessageDate(timestamp: Long): String {

    val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())

    return sdf.format(Date(timestamp))
}