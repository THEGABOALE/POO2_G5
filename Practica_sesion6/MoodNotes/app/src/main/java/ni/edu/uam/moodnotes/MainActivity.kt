package ni.edu.uam.moodnotes

import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import ni.edu.uam.moodnotes.ui.theme.MoodNotesTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class MoodNote(
    val id: Int,
    val text: String,
    val imageUriString: String? = null,
    val timeLabel: String = ""
)

enum class AppScreen {
    HOME,
    HISTORY
}

enum class HistoryFilter {
    ALL,
    WITH_IMAGE,
    TEXT_ONLY
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            var isDarkTheme by rememberSaveable { mutableStateOf(false) }

            MoodNotesTheme(darkTheme = isDarkTheme) {
                MoodNotesApp(
                    isDarkTheme = isDarkTheme,
                    onToggleTheme = { isDarkTheme = !isDarkTheme }
                )
            }
        }
    }
}

@Composable
fun MoodNotesApp(
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit
) {
    var currentScreen by rememberSaveable { mutableStateOf(AppScreen.HOME) }
    var noteText by rememberSaveable { mutableStateOf("") }
    var errorMessage by rememberSaveable { mutableStateOf("") }
    var selectedImageUriString by rememberSaveable { mutableStateOf<String?>(null) }
    var historyFilter by rememberSaveable { mutableStateOf(HistoryFilter.ALL) }
    var nextId by rememberSaveable { mutableStateOf(1) }

    val noteHistory = remember { mutableStateListOf<MoodNote>() }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        selectedImageUriString = uri?.toString()
    }

    val filteredNotes = noteHistory.filter { note ->
        when (historyFilter) {
            HistoryFilter.ALL -> true
            HistoryFilter.WITH_IMAGE -> note.imageUriString != null
            HistoryFilter.TEXT_ONLY -> note.imageUriString == null
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        bottomBar = {
            BottomNavigationBar(
                currentScreen = currentScreen,
                onScreenSelected = { currentScreen = it }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            TopHeader(
                isDarkTheme = isDarkTheme,
                onToggleTheme = onToggleTheme
            )

            Spacer(modifier = Modifier.height(18.dp))

            when (currentScreen) {
                AppScreen.HOME -> {
                    HomeScreen(
                        noteText = noteText,
                        onNoteTextChange = { newValue ->
                            if (newValue.length <= 60) {
                                noteText = newValue
                                if (errorMessage.isNotEmpty()) errorMessage = ""
                            }
                        },
                        errorMessage = errorMessage,
                        selectedImageUriString = selectedImageUriString,
                        onSelectImageClick = {
                            photoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        },
                        onRemoveImageClick = {
                            selectedImageUriString = null
                        },
                        onPublishClick = {
                            val cleanText = noteText.trim()

                            if (cleanText.isEmpty()) {
                                errorMessage = "Escribe una nota antes de publicar."
                            } else {
                                val timeNow = SimpleDateFormat(
                                    "hh:mm a",
                                    Locale.getDefault()
                                ).format(Date())

                                noteHistory.add(
                                    0,
                                    MoodNote(
                                        id = nextId,
                                        text = cleanText,
                                        imageUriString = selectedImageUriString,
                                        timeLabel = timeNow
                                    )
                                )

                                nextId++
                                noteText = ""
                                selectedImageUriString = null
                                errorMessage = ""
                            }
                        }
                    )
                }

                AppScreen.HISTORY -> {
                    HistoryScreen(
                        notes = filteredNotes,
                        totalNotes = noteHistory.size,
                        selectedFilter = historyFilter,
                        onFilterSelected = { historyFilter = it },
                        onDeleteNote = { id ->
                            noteHistory.removeAll { it.id == id }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun TopHeader(
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "MoodNotes",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Comparte tu mood en una nota rápida.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(10.dp))

            Surface(
                shape = RoundedCornerShape(50),
                color = MaterialTheme.colorScheme.secondaryContainer
            ) {
                Text(
                    text = "Publicando como @moodnotes_user",
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }

        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.surfaceVariant
        ) {
            IconButton(onClick = onToggleTheme) {
                Icon(
                    imageVector = Icons.Default.Brightness4,
                    contentDescription = if (isDarkTheme) {
                        "Cambiar a modo claro"
                    } else {
                        "Cambiar a modo oscuro"
                    }
                )
            }
        }
    }
}

@Composable
fun HomeScreen(
    noteText: String,
    onNoteTextChange: (String) -> Unit,
    errorMessage: String,
    selectedImageUriString: String?,
    onSelectImageClick: () -> Unit,
    onRemoveImageClick: () -> Unit,
    onPublishClick: () -> Unit
) {
    CreateNoteSection(
        noteText = noteText,
        onNoteTextChange = onNoteTextChange,
        errorMessage = errorMessage,
        selectedImageUriString = selectedImageUriString,
        onSelectImageClick = onSelectImageClick,
        onRemoveImageClick = onRemoveImageClick,
        onPublishClick = onPublishClick
    )

    Spacer(modifier = Modifier.height(16.dp))

    LivePreviewSection(
        noteText = noteText,
        selectedImageUriString = selectedImageUriString
    )
}

@Composable
fun HistoryScreen(
    notes: List<MoodNote>,
    totalNotes: Int,
    selectedFilter: HistoryFilter,
    onFilterSelected: (HistoryFilter) -> Unit,
    onDeleteNote: (Int) -> Unit
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Historial",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )

                Surface(
                    shape = RoundedCornerShape(50),
                    color = MaterialTheme.colorScheme.secondaryContainer
                ) {
                    Text(
                        text = "$totalNotes notas",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            HistoryFilterSection(
                selectedFilter = selectedFilter,
                onFilterSelected = onFilterSelected
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (notes.isEmpty()) {
                EmptyHistoryState()
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(500.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    itemsIndexed(notes, key = { _, note -> note.id }) { _, note ->
                        NoteHistoryItem(
                            note = note,
                            onDeleteNote = { onDeleteNote(note.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyHistoryState() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "No hay notas para este filtro.",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Prueba creando una nota nueva o cambiando el filtro.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun HistoryFilterSection(
    selectedFilter: HistoryFilter,
    onFilterSelected: (HistoryFilter) -> Unit
) {
    Column {
        Text(
            text = "Filtrar notas",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = selectedFilter == HistoryFilter.ALL,
                onClick = { onFilterSelected(HistoryFilter.ALL) },
                label = { Text("Todas") }
            )

            FilterChip(
                selected = selectedFilter == HistoryFilter.WITH_IMAGE,
                onClick = { onFilterSelected(HistoryFilter.WITH_IMAGE) },
                label = { Text("Con imagen") }
            )

            FilterChip(
                selected = selectedFilter == HistoryFilter.TEXT_ONLY,
                onClick = { onFilterSelected(HistoryFilter.TEXT_ONLY) },
                label = { Text("Solo texto") }
            )
        }
    }
}

@Composable
fun BottomNavigationBar(
    currentScreen: AppScreen,
    onScreenSelected: (AppScreen) -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = currentScreen == AppScreen.HOME,
            onClick = { onScreenSelected(AppScreen.HOME) },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Inicio"
                )
            },
            label = { Text("Inicio") }
        )

        NavigationBarItem(
            selected = currentScreen == AppScreen.HISTORY,
            onClick = { onScreenSelected(AppScreen.HISTORY) },
            icon = {
                Icon(
                    imageVector = Icons.Default.History,
                    contentDescription = "Historial"
                )
            },
            label = { Text("Historial") }
        )
    }
}

@Composable
fun CreateNoteSection(
    noteText: String,
    onNoteTextChange: (String) -> Unit,
    errorMessage: String,
    selectedImageUriString: String?,
    onSelectImageClick: () -> Unit,
    onRemoveImageClick: () -> Unit,
    onPublishClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Nueva nota",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Máximo 60 caracteres",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = noteText,
                onValueChange = onNoteTextChange,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Escribe tu mood") },
                placeholder = { Text("¿Qué estás pensando?") },
                supportingText = {
                    Text("${noteText.length}/60 caracteres")
                },
                singleLine = false,
                maxLines = 3,
                shape = RoundedCornerShape(18.dp)
            )

            if (errorMessage.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = onSelectImageClick,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Seleccionar imagen")
                }

                if (selectedImageUriString != null) {
                    TextButton(
                        onClick = onRemoveImageClick,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Quitar imagen")
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            SelectedImagePreview(selectedImageUriString = selectedImageUriString)

            Spacer(modifier = Modifier.height(14.dp))

            Button(
                onClick = onPublishClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Publicar nota")
            }
        }
    }
}

@Composable
fun SelectedImagePreview(selectedImageUriString: String?) {
    if (selectedImageUriString == null) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(18.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "No hay imagen seleccionada.",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    } else {
        Column {
            Text(
                text = "Vista previa de la imagen",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            NoteImage(uriString = selectedImageUriString, size = 180)
        }
    }
}

@Composable
fun LivePreviewSection(
    noteText: String,
    selectedImageUriString: String?
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Vista previa de la nota",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(10.dp))

            if (noteText.isBlank() && selectedImageUriString == null) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(18.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Todavía no has escrito una nota.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            } else {
                NoteBubble(
                    text = if (noteText.isBlank()) "Sin texto por ahora" else noteText,
                    imageUriString = selectedImageUriString,
                    timeLabel = "Sin publicar",
                    publishedState = "Borrador",
                    onDeleteNote = null
                )
            }
        }
    }
}

@Composable
fun NoteHistoryItem(
    note: MoodNote,
    onDeleteNote: () -> Unit
) {
    NoteBubble(
        text = note.text,
        imageUriString = note.imageUriString,
        timeLabel = note.timeLabel,
        publishedState = "Publicado",
        onDeleteNote = onDeleteNote
    )
}

@Composable
fun NoteBubble(
    text: String,
    imageUriString: String?,
    timeLabel: String,
    publishedState: String,
    onDeleteNote: (() -> Unit)?
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AvatarCircle(letter = "M")

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Text(
                            text = "@moodnotes_user",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = timeLabel,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                if (onDeleteNote != null) {
                    IconButton(onClick = onDeleteNote) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Eliminar nota"
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge
            )

            if (imageUriString != null) {
                Spacer(modifier = Modifier.height(12.dp))
                NoteImage(uriString = imageUriString, size = 140)
            }

            Spacer(modifier = Modifier.height(12.dp))

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StatusChip(text = publishedState)
                StatusChip(
                    text = if (imageUriString != null) "Con imagen" else "Solo texto"
                )
            }
        }
    }
}

@Composable
fun AvatarCircle(letter: String) {
    Surface(
        modifier = Modifier.size(38.dp),
        shape = CircleShape,
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = letter,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun StatusChip(text: String) {
    val icon = when (text) {
        "Con imagen" -> Icons.Default.Image
        "Solo texto" -> Icons.Default.TextFields
        else -> null
    }

    Surface(
        shape = RoundedCornerShape(50),
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            }

            Text(
                text = text,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Composable
fun NoteImage(uriString: String, size: Int) {
    Box(
        modifier = Modifier
            .size(size.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                ImageView(context).apply {
                    scaleType = ImageView.ScaleType.CENTER_CROP
                }
            },
            update = { imageView ->
                imageView.setImageURI(Uri.parse(uriString))
            }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MoodNotesPreview() {
    MoodNotesTheme {
        MoodNotesApp(
            isDarkTheme = false,
            onToggleTheme = {}
        )
    }
}