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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import ni.edu.uam.moodnotes.ui.theme.MoodNotesTheme

data class MoodNote(
    val text: String,
    val imageUriString: String? = null
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            var isDarkTheme by rememberSaveable { mutableStateOf(false) }

            MoodNotesTheme(
                darkTheme = isDarkTheme
            ) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MoodNotesApp(
                        modifier = Modifier.padding(innerPadding),
                        isDarkTheme = isDarkTheme,
                        onToggleTheme = {
                            isDarkTheme = !isDarkTheme
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun MoodNotesApp(
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit
) {
    var noteText by rememberSaveable { mutableStateOf("") }
    var errorMessage by rememberSaveable { mutableStateOf("") }
    var selectedImageUriString by rememberSaveable { mutableStateOf<String?>(null) }

    val noteHistory = remember { mutableStateListOf<MoodNote>() }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        selectedImageUriString = uri?.toString()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "MoodNotes",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Comparte tu estado en una nota rápida.",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = onToggleTheme,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                if (isDarkTheme) {
                    "Cambiar a modo claro"
                } else {
                    "Cambiar a modo oscuro"
                }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        CreateNoteSection(
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
            onPublishClick = {
                val cleanText = noteText.trim()

                if (cleanText.isEmpty()) {
                    errorMessage = "Escribe una nota antes de publicar."
                } else {
                    noteHistory.add(
                        0,
                        MoodNote(
                            text = cleanText,
                            imageUriString = selectedImageUriString
                        )
                    )

                    noteText = ""
                    selectedImageUriString = null
                    errorMessage = ""
                }
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        HorizontalDivider()

        Spacer(modifier = Modifier.height(20.dp))

        HistorySection(noteHistory = noteHistory)
    }
}

@Composable
fun CreateNoteSection(
    noteText: String,
    onNoteTextChange: (String) -> Unit,
    errorMessage: String,
    selectedImageUriString: String?,
    onSelectImageClick: () -> Unit,
    onPublishClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
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
                maxLines = 3
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

            OutlinedButton(
                onClick = onSelectImageClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Seleccionar imagen")
            }

            Spacer(modifier = Modifier.height(12.dp))

            SelectedImagePreview(selectedImageUriString = selectedImageUriString)

            Spacer(modifier = Modifier.height(12.dp))

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
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(16.dp)
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
fun HistorySection(noteHistory: List<MoodNote>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Historial",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (noteHistory.isEmpty()) {
                Text(
                    text = "Todavía no hay notas publicadas.",
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    itemsIndexed(noteHistory) { index, note ->
                        NoteHistoryItem(
                            index = index + 1,
                            note = note
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NoteHistoryItem(index: Int, note: MoodNote) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                text = "Nota #$index",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = note.text,
                style = MaterialTheme.typography.bodyLarge
            )

            if (note.imageUriString != null) {
                Spacer(modifier = Modifier.height(10.dp))
                NoteImage(uriString = note.imageUriString, size = 140)
            }
        }
    }
}

@Composable
fun NoteImage(uriString: String, size: Int) {
    AndroidView(
        modifier = Modifier
            .size(size.dp)
            .clip(RoundedCornerShape(12.dp)),
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