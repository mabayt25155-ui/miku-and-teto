package com.mikuteto.ai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Pink = Color(0xFFFF75B5)
private val Teal = Color(0xFF45C9C2)
private val Ink = Color(0xFF28243A)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MikuTetoApp() }
    }
}

@Composable
fun MikuTetoApp() {
    var selected by remember { mutableStateOf("Miku") }
    var message by remember { mutableStateOf("") }
    var messages by remember {
        mutableStateOf(listOf("Miku: Merhaba! Ben Miku. Bugün ne yapmak istersin?"))
    }

    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(listOf(Color(0xFFFFE5F1), Color(0xFFE3FAFA))))
                .padding(18.dp)
        ) {
            Text("Miku & Teto AI", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Ink)
            Text("Sevimli sohbet arkadaşların", color = Ink.copy(alpha = .7f))
            Spacer(Modifier.height(18.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                CharacterButton("Miku", selected == "Miku", Pink) { selected = "Miku" }
                CharacterButton("Teto", selected == "Teto", Teal) { selected = "Teto" }
                CharacterButton("İkisi", selected == "İkisi", Color(0xFF9B8BEA)) { selected = "İkisi" }
            }

            Spacer(Modifier.height(16.dp))
            Card(
                modifier = Modifier.fillMaxWidth().weight(1f),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = .88f)),
                shape = RoundedCornerShape(24.dp)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    item {
                        Text(
                            when (selected) {
                                "Miku" -> "🎵 Hatsune Miku"
                                "Teto" -> "🍞 Kasane Teto"
                                else -> "🎵 Miku & Teto"
                            },
                            fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Ink
                        )
                    }
                    items(messages) { line ->
                        Surface(
                            color = if (line.startsWith("Sen:")) Color(0xFFFFEAF3) else Color(0xFFE8F8F7),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text(line, modifier = Modifier.fillMaxWidth().padding(12.dp), color = Ink)
                        }
                    }
                }
            }

            Spacer(Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = message,
                    onValueChange = { message = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Bir mesaj yaz...") },
                    shape = RoundedCornerShape(18.dp),
                    singleLine = true
                )
                Button(
                    onClick = {
                        val clean = message.trim()
                        if (clean.isNotEmpty()) {
                            val reply = when (selected) {
                                "Miku" -> "Miku: Mesajını aldım! Gerçek yapay zekâ sohbetini sonraki aşamada bağlayacağız. 💙"
                                "Teto" -> "Teto: Bunu duymak güzel! Gerçek yapay zekâ sohbetini sonraki aşamada bağlayacağız. ❤️"
                                else -> "Miku & Teto: Mesajını gördük! Gerçek yapay zekâ sohbetini sonraki aşamada bağlayacağız. ✨"
                            }
                            messages = messages + "Sen: $clean" + reply
                            message = ""
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Pink),
                    shape = RoundedCornerShape(18.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)
                ) { Text("Gönder") }
            }
            Spacer(Modifier.height(8.dp))
            Text("Not: Bu başlangıç sürümünde sohbet yanıtları örnektir; AI bağlantısı henüz eklenmedi.", fontSize = 11.sp, color = Ink.copy(alpha = .65f))
        }
    }
}

@Composable
private fun CharacterButton(label: String, active: Boolean, color: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (active) color else Color.White,
            contentColor = if (active) Color.White else Ink
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(label, fontWeight = FontWeight.SemiBold)
    }
}
