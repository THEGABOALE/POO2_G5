package ni.edu.uam.registroapi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ni.edu.uam.registroapi.data.model.Carrera
import ni.edu.uam.registroapi.data.remote.RetrofitCliente

sealed class UiState {
    object Loading : UiState()
    data class Success(val data: List<Carrera>) : UiState()
    data class Error(val message: String) : UiState()
}

class CarreraViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        cargarCarreras()
    }

    fun cargarCarreras() {
        _uiState.value = UiState.Loading

        viewModelScope.launch {
            try {
                val lista = RetrofitCliente.api.listar()
                _uiState.value = UiState.Success(lista)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(
                    "No se pudo conectar con el servidor.\n\n" +
                            "Verifique que la API esté activa y que el dispositivo esté en la misma red WiFi."
                )
            }
        }
    }
}