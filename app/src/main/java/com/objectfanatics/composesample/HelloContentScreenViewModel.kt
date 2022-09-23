package com.objectfanatics.composesample

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

class HelloContentScreenViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    val nameStateFlow: StateFlow<String> = savedStateHandle.getStateFlow(KEY_name, "")

    private var name: String
        get() = nameStateFlow.value
        set(value) {
            savedStateHandle[KEY_name] = value
        }

    // Setter という形ではなく event という形で公開する。
    // Setter をそのまま公開した場合、event handler であるという設計的な意思表示が弱まるという点と、
    // イベントハンドリングの前後に処理を施す際などにバグが混入しやすいという点が問題になる(と思われる)。
    fun onNameChange(name: String) {
        this.name = name
    }

    companion object {
        private const val KEY_name = "name"
    }
}