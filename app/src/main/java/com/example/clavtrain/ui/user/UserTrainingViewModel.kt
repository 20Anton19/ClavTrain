package com.example.clavtrain.ui.user

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clavtrain.ui.RegisterLKViewModel.RegisterState
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable.isActive
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserTrainingViewModel(): ViewModel() {
    private var _fullText = ""
    private val _userInput = MutableStateFlow<String>("")
    val userInput: StateFlow<String> = _userInput.asStateFlow()

    private val _isCompleted = MutableStateFlow<Boolean>(false)
    val isCompleted: StateFlow<Boolean> = _isCompleted.asStateFlow()

    private val _isCorrect = MutableStateFlow<Boolean>(false)
    val isCorrect: StateFlow<Boolean> = _isCorrect.asStateFlow()

    private val _presentMistakes = MutableStateFlow<Int>(0)
    val presentMistakes: StateFlow<Int> = _presentMistakes.asStateFlow()

    private val _presentLength = MutableStateFlow<Int>(0)
    val presentLength: StateFlow<Int> = _presentLength.asStateFlow()

    private val _remainingText = MutableStateFlow<String>(_fullText)
    val remainingText: StateFlow<String> = _remainingText.asStateFlow()

    // Добавляем таймер
    private var startTime: Long = 0L
    private val _presentTime = MutableStateFlow<Long>(0L)
    val presentTime: StateFlow<Long> = _presentTime.asStateFlow()
    private var timerJob: Job? = null
    fun setExerciseText(text: String) {
        _fullText = text
        _remainingText.value = _fullText
        startTimer()
    }

    private fun startTimer() {
        startTime = System.currentTimeMillis()
        timerJob = viewModelScope.launch {
            while (isActive && !_isCompleted.value) {
                _presentTime.value = System.currentTimeMillis() - startTime
                delay(100) // Обновляем каждые 100ms
            }
        }
    }

    fun everyTextChange(
        newText: String
    ) {
        if (newText.length <= _fullText.length) {
            val tempIsCorrect = _fullText.take(newText.length) == newText
            val isAddingCharacter = newText.length > userInput.value.length

            _userInput.value = newText
            _presentLength.value = newText.length

            if (!tempIsCorrect and isAddingCharacter) {
                _presentMistakes.value+=1
            }

            if ((newText.length == _fullText.length) and tempIsCorrect) {
                _isCompleted.value = true
                timerJob?.cancel()
            }
            _remainingText.value = _fullText.drop(userInput.value.length)
            _isCorrect.value  = tempIsCorrect
        }
    }
}