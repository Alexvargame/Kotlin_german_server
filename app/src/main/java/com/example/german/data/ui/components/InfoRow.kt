package com.example.german.data.ui.components



import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp

@Composable
fun InfoRow(
    label: String,
    value: String,
    icon: ImageVector? = null,              // иконка теперь необязательная
    iconTint: Color = Color.Gray,
    labelColor: Color = Color.Gray,
    valueColor: Color = Color.White,
    labelFontSize: TextUnit = 16.sp,
    valueFontSize: TextUnit = 16.sp,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint
            )
            Spacer(modifier = Modifier.width(8.dp))
        }

        Text(
            text = "$label:",
            color = labelColor,
            fontSize = labelFontSize
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = value,
            color = valueColor,
            fontSize = valueFontSize
        )
    }
}