package com.example.clavtrain.ui.user

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.clavtrain.ui.RegisterLKViewModel.RegisterState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

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

    private val _presentTime = MutableStateFlow<Long>(0L)
    val presentTime: StateFlow<Long> = _presentTime.asStateFlow()

    private val _remainingText = MutableStateFlow<String>(_fullText)
    val remainingText: StateFlow<String> = _remainingText.asStateFlow()

    // Устанавливаем текст упражнения
    fun setExerciseText(text: String) {
        _fullText = text
        _remainingText.value = _fullText
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
            }
            _remainingText.value = _fullText.drop(userInput.value.length)
            _isCorrect.value  = tempIsCorrect
        }
    }
}