package com.zai.hairstudio

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import coil.compose.AsyncImage

private val Ink = Color(0xFF15141B)
private val Purple = Color(0xFF8B5CF6)
private val Canvas = Color(0xFFF7F5FA)
private val Muted = Color(0xFF77727F)

data class HairStyle(
    val name: String, val group: String, val description: String,
    val tags: List<String>, val emoji: String
)

private val styles = listOf(
    HairStyle("French Crop", "Kısa", "Dokulu üst, kısa ve temiz yanlar.", listOf("Oval", "Yuvarlak", "Kare"), "✂️"),
    HairStyle("Low Taper", "Kısa", "Doğal geçişli, modern ve düzenli görünüm.", listOf("Oval", "Kare", "Kalp"), "💈"),
    HairStyle("Buzz Cut", "Çok kısa", "Minimal bakım isteyen net kesim.", listOf("Oval", "Kare"), "⚡"),
    HairStyle("Textured Quiff", "Orta", "Önde hacim ve hareket sağlayan model.", listOf("Oval", "Kare", "Dikdörtgen"), "✨"),
    HairStyle("Curtain Cut", "Orta", "Ortadan ayrılan, yumuşak katlı görünüm.", listOf("Oval", "Kalp", "Dikdörtgen"), "🌿"),
    HairStyle("Layered Bob", "Orta", "Yüz çevresinde katlarla hareket kazandırır.", listOf("Oval", "Yuvarlak", "Kalp"), "🌸"),
    HairStyle("Long Layers", "Uzun", "Uzunluğu koruyup ağırlığı dengeler.", listOf("Oval", "Kare", "Kalp"), "💫"),
    HairStyle("Soft Pixie", "Kısa", "Hafif dokulu, yüzü öne çıkaran kısa kesim.", listOf("Oval", "Kalp", "Kare"), "🪄"),
    HairStyle("Shag Cut", "Orta/Uzun", "Katlı, hacimli ve rahat bir stil.", listOf("Oval", "Dikdörtgen", "Kalp"), "🔥")
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { ZAIApp() }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ZAIApp() {
    var photo by remember { mutableStateOf<Uri?>(null) }
    var audience by remember { mutableStateOf("Herkes") }
    var face by remember { mutableStateOf("Oval") }
    var hair by remember { mutableStateOf("Dalgalı") }
    var length by remember { mutableStateOf("Hepsi") }
    var selected by remember { mutableStateOf<HairStyle?>(null) }
    var favorites by remember { mutableStateOf(setOf<String>()) }
    var showInfo by remember { mutableStateOf(false) }

    val picker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { photo = it }

    MaterialTheme(colorScheme = lightColorScheme(
        primary = Purple, onPrimary = Color.White, background = Canvas,
        surface = Color.White, onSurface = Ink
    )) {
        Scaffold(containerColor = Canvas) { padding ->
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 18.dp, bottom = 30.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                item {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(Modifier.size(44.dp).clip(RoundedCornerShape(15.dp)).background(Ink), Alignment.Center) {
                            Text("Z", color = Color.White, fontSize = 25.sp, fontWeight = FontWeight.Black)
                        }
                        Spacer(Modifier.width(11.dp))
                        Column(Modifier.weight(1f)) {
                            Text("ZAI", fontSize = 25.sp, fontWeight = FontWeight.Black, color = Ink)
                            Text("AI HAIR STUDIO", fontSize = 10.sp, color = Muted, letterSpacing = 2.sp)
                        }
                        Surface(shape = CircleShape, color = Color(0xFFEDE7FF)) {
                            Row(Modifier.padding(horizontal = 11.dp, vertical = 7.dp), verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.AutoAwesome, null, tint = Purple, modifier = Modifier.size(15.dp))
                                Spacer(Modifier.width(5.dp))
                                Text("AI STUDIO", color = Purple, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
                        Text("Yeni bir tarz,\nyeni bir sen.", fontSize = 31.sp, lineHeight = 36.sp, fontWeight = FontWeight.ExtraBold, color = Ink)
                        Text("Yüzüne uygun kesimleri keşfet, kendi fotoğrafında dene.", color = Muted, fontSize = 14.sp)
                    }
                }
                item {
                    Column(Modifier.fillMaxWidth().clip(RoundedCornerShape(24.dp)).background(Ink).padding(17.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Column(Modifier.weight(1f)) {
                                Text("KENDİ FOTOĞRAFINDA DENE", color = Color(0xFFC8B6FF), fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                                Text("Saçını değiştirmeden önce gör.", color = Color.White, fontSize = 19.sp, fontWeight = FontWeight.Bold)
                            }
                            Icon(Icons.Default.Face, null, tint = Color(0xFFC8B6FF), modifier = Modifier.size(34.dp))
                        }
                        Box(
                            Modifier.fillMaxWidth().height(210.dp).clip(RoundedCornerShape(18.dp))
                                .background(Color(0xFF292633)).clickable { picker.launch("image/*") },
                            contentAlignment = Alignment.Center
                        ) {
                            if (photo != null) {
                                AsyncImage(model = photo, contentDescription = "Seçilen fotoğraf", modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                            } else {
                                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Icon(Icons.Default.AddAPhoto, null, tint = Color.White, modifier = Modifier.size(36.dp))
                                    Text("Fotoğrafını ekle", color = Color.White, fontWeight = FontWeight.SemiBold)
                                    Text("Yüzün net görünsün, önden çekilmiş olsun", color = Color(0xFFB9B5C3), fontSize = 11.sp)
                                }
                            }
                        }
                        Button(onClick = { picker.launch("image/*") }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Purple), shape = RoundedCornerShape(14.dp)) {
                            Icon(Icons.Default.Image, null)
                            Spacer(Modifier.width(8.dp))
                            Text(if (photo == null) "Galeriden fotoğraf seç" else "Fotoğrafı değiştir")
                        }
                        Text("Not: Fotoğraf bu prototipte cihazından seçilir; sunucuya gönderilmez.", color = Color(0xFFB9B5C3), fontSize = 10.sp)
                    }
                }
                item {
                    SectionTitle("Sana özel öneriler", "Tercihlerini belirle")
                    Spacer(Modifier.height(10.dp))
                    Text("Hangi kategori?", fontWeight = FontWeight.SemiBold, color = Ink)
                    Spacer(Modifier.height(7.dp))
                    ChoiceRow(listOf("Herkes", "Kadın", "Erkek"), audience) { audience = it }
                    Spacer(Modifier.height(12.dp))
                    Text("Yüz şeklin", fontWeight = FontWeight.SemiBold, color = Ink)
                    Spacer(Modifier.height(7.dp))
                    ChoiceRow(listOf("Oval", "Yuvarlak", "Kare", "Kalp", "Dikdörtgen"), face) { face = it }
                    Spacer(Modifier.height(12.dp))
                    Text("Saç yapın", fontWeight = FontWeight.SemiBold, color = Ink)
                    Spacer(Modifier.height(7.dp))
                    ChoiceRow(listOf("Düz", "Dalgalı", "Kıvırcık", "Yoğun"), hair) { hair = it }
                }
                item {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        SectionTitle("Keşfet", "Yüz şekline göre fikirler")
                        Spacer(Modifier.weight(1f))
                        Icon(Icons.Default.Tune, null, tint = Purple)
                    }
                    Spacer(Modifier.height(8.dp))
                    ChoiceRow(listOf("Hepsi", "Kısa", "Orta", "Uzun"), length) { length = it }
                }
                items(styles.filter { length == "Hepsi" || it.group.contains(length, true) }) { style ->
                    val recommended = face in style.tags
                    Surface(
                        shape = RoundedCornerShape(19.dp), color = Color.White,
                        modifier = Modifier.fillMaxWidth().clickable { selected = style }
                    ) {
                        Row(Modifier.padding(15.dp), verticalAlignment = Alignment.CenterVertically) {
                            Box(Modifier.size(54.dp).clip(RoundedCornerShape(15.dp)).background(Color(0xFFF0ECF8)), Alignment.Center) {
                                Text(style.emoji, fontSize = 25.sp)
                            }
                            Spacer(Modifier.width(13.dp))
                            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text(style.name, color = Ink, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                Text(style.description, color = Muted, fontSize = 11.sp)
                                if (recommended) Text("Yüz şekline uygun olabilir", color = Color(0xFF6C45C8), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                            IconButton(onClick = { favorites = if (style.name in favorites) favorites - style.name else favorites + style.name }) {
                                Icon(Icons.Default.FavoriteBorder, null, tint = if (style.name in favorites) Purple else Muted)
                            }
                        }
                    }
                }
                item {
                    Surface(color = Color(0xFFEDE7FF), shape = RoundedCornerShape(18.dp), modifier = Modifier.fillMaxWidth()) {
                        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text("Yapay zekâ analizi hakkında", color = Ink, fontWeight = FontWeight.Bold)
                            Text("Bu sürüm, seçtiğin yüz şekli ve saç yapısına göre kural tabanlı öneriler sunar. Gerçek yüz tanıma, kişiye özel görsel saç dönüşümü ve fotoğraf üzerinde saç maskesi için güvenli bir AI görüntüleme servisine bağlantı eklenmelidir.", color = Color(0xFF554D64), fontSize = 12.sp, lineHeight = 18.sp)
                        }
                    }
                }
                item {
                    Text("ZAI • Saç stilini keşfet", color = Muted, fontSize = 11.sp, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                }
            }
        }
        if (selected != null) {
            AlertDialog(
                onDismissRequest = { selected = null },
                title = { Text(selected!!.name, fontWeight = FontWeight.Bold) },
                text = { Text("${selected!!.description}\n\nÖnerilen yüz şekilleri: ${selected!!.tags.joinToString(", ")}.\n\nBerberine bu modelin adını ve açıklamasını gösterebilirsin. Fotoğraf üzerinde gerçekçi AI dönüşümü henüz bağlı değil.") },
                confirmButton = { TextButton(onClick = { selected = null }) { Text("Tamam") } }
            )
        }
    }
}

@Composable
fun SectionTitle(title: String, subtitle: String) {
    Column {
        Text(title, color = Ink, fontSize = 21.sp, fontWeight = FontWeight.ExtraBold)
        Text(subtitle, color = Muted, fontSize = 12.sp)
    }
}

@Composable
fun ChoiceRow(options: List<String>, selected: String, onSelect: (String) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(7.dp), modifier = Modifier.fillMaxWidth()) {
        options.forEach { option ->
            val active = option == selected
            Surface(
                color = if (active) Ink else Color.White,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f).clickable { onSelect(option) }
                    .border(1.dp, if (active) Ink else Color(0xFFE9E5EE), RoundedCornerShape(12.dp))
            ) {
                Text(option, modifier = Modifier.padding(vertical = 10.dp, horizontal = 3.dp), textAlign = TextAlign.Center,
                    color = if (active) Color.White else Ink, fontSize = 10.sp, fontWeight = FontWeight.SemiBold, maxLines = 1)
            }
        }
    }
}
