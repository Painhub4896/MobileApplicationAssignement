package com.example.aistudybuddy.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StudySessionCard(
    title: String,
    time: String,
    icon: ImageVector,
    iconBackground: Color,
    iconColor: Color = Color(0xFF4169E1),
    cardBackground: Color = Color.White,
    titleColor: Color = Color(0xFF30323D),
    aiSuggested: Boolean = false
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = cardBackground,
                shape = RoundedCornerShape(10.dp)
            )
            .border(
                width = 0.3.dp,
                color = titleColor,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(
                horizontal = 10.dp,
                vertical =18.dp
            )
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Subject icon
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .background(
                        color = iconBackground,
                        shape = RoundedCornerShape(7.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = iconColor,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            // Subject information
            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = titleColor
                )

                Spacer(
                    modifier = Modifier.height(2.dp)
                )

                Text(
                    text = time,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )

                if (aiSuggested) {

                    Spacer(
                        modifier = Modifier.height(2.dp)
                    )

                    Text(
                        text = "✦ AI suggested",
                        fontSize = 10.sp,
                        color = Color(0xFF7657D9)
                    )
                }
            }

            // Three-dot menu
            IconButton(
                onClick = { },
                modifier = Modifier.size(28.dp)
            ) {

                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More options",
                    tint = Color(0xFF70727D),
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}