package com.example.lap7_crud.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.PaddingValues

// Định nghĩa các màu
val Purple80Color = Color(0xFFD0BCFF)
val PurpleGrey80Color = Color(0xFFCCC2DC)
val Pink80Color = Color(0xFFEFB8C8)

val Purple40Color = Color(0xFF6650a4)
val PurpleGrey40Color = Color(0xFF625b71)
val Pink40Color = Color(0xFF7D5260)

// Thêm màu xanh lá cây
val GreenColor = Color(0xFF4CAF50) // Màu xanh lá cây

private val DarkColorScheme = darkColorScheme(
    primary = Purple80Color,
    secondary = PurpleGrey80Color,
    tertiary = Pink80Color
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40Color,
    secondary = PurpleGrey40Color,
    tertiary = Pink40Color,
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onSurface = Color.Black
)

@Composable
fun Lap7_crudTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Update Course",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color.White
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = GreenColor,
            titleContentColor = Color.White
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarContainer(content: @Composable (PaddingValues) -> Unit) {
    Scaffold(
        topBar = {
            CustomTopAppBar()
        },
        content = { paddingValues ->
            content(paddingValues)
        }
    )
}
