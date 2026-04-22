package ni.edu.uam.moodnotes.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = RosePink,
    onPrimary = WarmWhite,
    primaryContainer = SoftPink,
    onPrimaryContainer = DeepPlum,

    secondary = Plum,
    onSecondary = WarmWhite,
    secondaryContainer = ColorKtFix.secondaryContainerLight,
    onSecondaryContainer = DeepPlum,

    background = Cream,
    onBackground = DeepPlum,

    surface = WarmWhite,
    onSurface = DeepPlum,

    surfaceVariant = SoftPink,
    onSurfaceVariant = Plum,

    outline = LightBorder
)

private val DarkColorScheme = darkColorScheme(
    primary = RosePink,
    onPrimary = WarmWhite,
    primaryContainer = Plum,
    onPrimaryContainer = SoftPink,

    secondary = MintAccent,
    onSecondary = DeepPlum,
    secondaryContainer = DarkSurfaceVariant,
    onSecondaryContainer = WarmWhite,

    background = DarkBackground,
    onBackground = WarmWhite,

    surface = DarkSurface,
    onSurface = WarmWhite,

    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = SoftPink,

    outline = LavenderGray
)

object ColorKtFix {
    val secondaryContainerLight = SoftPink
}

@Composable
fun MoodNotesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}