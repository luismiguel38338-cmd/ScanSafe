package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.AboutModal
import com.example.ui.screens.AnalyzerScreen
import com.example.ui.screens.CatalogScreen
import com.example.ui.screens.DetailScreen
import com.example.ui.screens.EmergencyScreen
import com.example.ui.screens.HistoryScreen
import com.example.ui.screens.ScanScreen
import com.example.ui.theme.ScanSafeTheme
import com.example.ui.viewmodel.ScanViewModel

enum class MainTab(val title: String, val icon: ImageVector, val tag: String) {
    SCAN("Escanear", Icons.Default.QrCodeScanner, "tab_scan"),
    CATALOG("Catálogo", Icons.Default.Search, "tab_catalog"),
    ANALYZER("Fórmulas", Icons.Default.Science, "tab_analyzer"),
    HISTORY("Historial", Icons.Default.History, "tab_history"),
    EMERGENCY("Urgencias", Icons.Default.LocalHospital, "tab_emergency")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ScanSafeTheme {
                MainAppContainer()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppContainer(
    viewModel: ScanViewModel = viewModel()
) {
    var currentTab by remember { mutableStateOf(MainTab.SCAN) }
    var showDetailScreen by remember { mutableStateOf(false) }
    var showAboutModal by remember { mutableStateOf(false) }

    val currentDetailItem by viewModel.currentDetailItem.collectAsState()

    if (showAboutModal) {
        AboutModal(onDismiss = { showAboutModal = false })
    }

    if (showDetailScreen && currentDetailItem != null) {
        DetailScreen(
            viewModel = viewModel,
            onBack = { showDetailScreen = false },
            onNavigateToEmergency = {
                showDetailScreen = false
                currentTab = MainTab.EMERGENCY
            }
        )
    } else {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                if (currentTab != MainTab.SCAN) {
                    TopAppBar(
                        title = {
                            Text(
                                text = "ScanSafe",
                                fontWeight = FontWeight.Black,
                                fontSize = 20.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                        },
                        actions = {
                            IconButton(
                                onClick = { showAboutModal = true },
                                modifier = Modifier.testTag("button_open_about")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = "Información del desarrollador",
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    )
                }
            },
            bottomBar = {
                NavigationBar(
                    modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars).testTag("bottom_nav_bar"),
                    containerColor = MaterialTheme.colorScheme.surface
                ) {
                    MainTab.entries.forEach { tab ->
                        val selected = currentTab == tab
                        NavigationBarItem(
                            selected = selected,
                            onClick = { currentTab = tab },
                            icon = {
                                Icon(
                                    imageVector = tab.icon,
                                    contentDescription = tab.title
                                )
                            },
                            label = {
                                Text(
                                    text = tab.title,
                                    fontSize = 11.sp,
                                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                                selectedTextColor = MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier.testTag(tab.tag)
                        )
                    }
                }
            }
        ) { innerPadding ->
            AnimatedContent(
                targetState = currentTab,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                label = "screen_transition"
            ) { targetScreen ->
                when (targetScreen) {
                    MainTab.SCAN -> {
                        ScanScreen(
                            viewModel = viewModel,
                            onNavigateToDetail = { showDetailScreen = true }
                        )
                    }
                    MainTab.CATALOG -> {
                        CatalogScreen(
                            viewModel = viewModel,
                            onSelectItem = { item ->
                                viewModel.openItemDetail(item)
                                showDetailScreen = true
                            }
                        )
                    }
                    MainTab.ANALYZER -> {
                        AnalyzerScreen(
                            viewModel = viewModel,
                            onNavigateToDetail = { showDetailScreen = true }
                        )
                    }
                    MainTab.HISTORY -> {
                        HistoryScreen(
                            viewModel = viewModel,
                            onSelectItem = { item ->
                                viewModel.openItemDetail(item)
                                showDetailScreen = true
                            }
                        )
                    }
                    MainTab.EMERGENCY -> {
                        EmergencyScreen()
                    }
                }
            }
        }
    }
}
