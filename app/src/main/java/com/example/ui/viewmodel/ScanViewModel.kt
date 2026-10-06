package com.example.ui.viewmodel

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.SavedScanEntity
import com.example.data.ScanKnowledgeBase
import com.example.data.ScanSafeDatabase
import com.example.model.ItemCategory
import com.example.model.ScanResultItem
import com.example.service.GeminiScanService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed interface ScanUiState {
    data object Idle : ScanUiState
    data class Scanning(val message: String = "Analizando toxicidad y componentes...") : ScanUiState
    data class Success(val item: ScanResultItem) : ScanUiState
    data class Error(val errorMessage: String) : ScanUiState
}

class ScanViewModel(application: Application) : AndroidViewModel(application) {

    private val db = ScanSafeDatabase.getDatabase(application)
    private val dao = db.scanDao()
    private val geminiService = GeminiScanService()

    private val _uiState = MutableStateFlow<ScanUiState>(ScanUiState.Idle)
    val uiState: StateFlow<ScanUiState> = _uiState.asStateFlow()

    private val _currentDetailItem = MutableStateFlow<ScanResultItem?>(null)
    val currentDetailItem: StateFlow<ScanResultItem?> = _currentDetailItem.asStateFlow()

    // Catalog search & filter state
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow<ItemCategory?>(null)
    val selectedCategory: StateFlow<ItemCategory?> = _selectedCategory.asStateFlow()

    // Scans History from Room
    val savedScans: StateFlow<List<ScanResultItem>> = dao.getAllScans()
        .map { entities -> entities.map { it.toDomainModel() } }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    // Camera settings
    private val _isFlashOn = MutableStateFlow(false)
    val isFlashOn: StateFlow<Boolean> = _isFlashOn.asStateFlow()

    private val _isFrontCamera = MutableStateFlow(false)
    val isFrontCamera: StateFlow<Boolean> = _isFrontCamera.asStateFlow()

    fun toggleFlash() {
        _isFlashOn.value = !_isFlashOn.value
    }

    fun toggleCameraFacing() {
        _isFrontCamera.value = !_isFrontCamera.value
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun selectCategory(category: ItemCategory?) {
        _selectedCategory.value = category
    }

    fun scanBitmap(bitmap: Bitmap, hint: String = "") {
        viewModelScope.launch {
            _uiState.value = ScanUiState.Scanning("Analizando imagen con visión multimodal...")
            try {
                val result = geminiService.analyzeImage(bitmap, hint)
                _currentDetailItem.value = result
                _uiState.value = ScanUiState.Success(result)
                saveScanToHistory(result)
            } catch (e: Exception) {
                _uiState.value = ScanUiState.Error("No se pudo completar el análisis: ${e.localizedMessage}")
            }
        }
    }

    fun scanUri(uri: Uri) {
        viewModelScope.launch {
            _uiState.value = ScanUiState.Scanning("Procesando imagen de la galería...")
            try {
                val context = getApplication<Application>()
                val inputStream = context.contentResolver.openInputStream(uri)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                inputStream?.close()

                if (bitmap != null) {
                    scanBitmap(bitmap)
                } else {
                    _uiState.value = ScanUiState.Error("No se pudo cargar la imagen seleccionada.")
                }
            } catch (e: Exception) {
                _uiState.value = ScanUiState.Error("Error al leer archivo: ${e.localizedMessage}")
            }
        }
    }

    fun scanPreset(item: ScanResultItem) {
        viewModelScope.launch {
            _uiState.value = ScanUiState.Scanning("Verificando registro toxicológico...")
            kotlinx.coroutines.delay(400) // Smooth micro-transition
            _currentDetailItem.value = item
            _uiState.value = ScanUiState.Success(item)
            saveScanToHistory(item)
        }
    }

    fun analyzeIngredientsText(formulaText: String) {
        viewModelScope.launch {
            _uiState.value = ScanUiState.Scanning("Analizando reactivos y compuestos químicos...")
            try {
                val result = geminiService.analyzeTextQuery(formulaText)
                _currentDetailItem.value = result
                _uiState.value = ScanUiState.Success(result)
                saveScanToHistory(result)
            } catch (e: Exception) {
                val localFallback = ScanKnowledgeBase.analyzeIngredientsText(formulaText)
                _currentDetailItem.value = localFallback
                _uiState.value = ScanUiState.Success(localFallback)
                saveScanToHistory(localFallback)
            }
        }
    }

    fun openItemDetail(item: ScanResultItem) {
        _currentDetailItem.value = item
    }

    fun resetScanState() {
        _uiState.value = ScanUiState.Idle
    }

    fun saveScanToHistory(item: ScanResultItem) {
        viewModelScope.launch {
            dao.insertScan(SavedScanEntity.fromDomainModel(item))
        }
    }

    fun toggleFavorite(item: ScanResultItem) {
        viewModelScope.launch {
            val updated = item.copy(isFavorite = !item.isFavorite)
            dao.updateScan(SavedScanEntity.fromDomainModel(updated))
            if (_currentDetailItem.value?.id == item.id) {
                _currentDetailItem.value = updated
            }
        }
    }

    fun deleteScan(item: ScanResultItem) {
        viewModelScope.launch {
            dao.deleteById(item.id)
            if (_currentDetailItem.value?.id == item.id) {
                _currentDetailItem.value = null
            }
        }
    }

    fun clearAllHistory() {
        viewModelScope.launch {
            dao.clearAll()
        }
    }
}
