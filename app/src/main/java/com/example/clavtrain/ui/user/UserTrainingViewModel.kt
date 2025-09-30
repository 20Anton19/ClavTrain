package com.example.clavtrain.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clavtrain.data.db.ExerciseStatistic
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable.isActive
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserTrainingViewModel(): ViewModel() {
    private var _fullText = ""
    private var _maxMistakes = 0
    private var _maxPressTime = 0L
    private var _isSuccessful = true
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

    private var lastKeyPressTime: Long = 0L
    private var currentMaxPressTime: Long = 0L
    fun setExerciseSettings(
        text: String,
        maxMistakes: Int,
        maxPressTime: Long
    ) {
        _fullText = text
        _maxMistakes = maxMistakes
        _maxPressTime = maxPressTime
        _remainingText.value = _fullText
        startTimer()
        currentMaxPressTime = maxPressTime
        lastKeyPressTime = System.currentTimeMillis()
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

    fun completeExercise(exerciseId: Int, userId: String?): ExerciseStatistic {
        timerJob?.cancel() // Останавливаем таймер если еще не остановлен
        return ExerciseStatistic(
            userId = userId!!,
            exerciseId = exerciseId,
            mistakes = presentMistakes.value,
            timeSpent = presentTime.value,
            avgTime = if (presentLength.value > 0) presentTime.value / presentLength.value else 0L,
            isSuccessful = _isSuccessful,
            completedAt = System.currentTimeMillis()
        )
    }

    fun everyTextChange(
        newText: String
    ) {
        if (newText.length <= _fullText.length) {
            val currentTime = System.currentTimeMillis()
            val tempIsCorrect = _fullText.take(newText.length) == newText
            val isAddingCharacter = newText.length > userInput.value.length

            // Проверяем время между нажатиями только если добавляем новый символ
            if (isAddingCharacter && userInput.value.isNotEmpty()) {
                val timeBetweenPresses = currentTime - lastKeyPressTime

                // Если время между нажатиями превышает максимальное - добавляем ошибку
                if (timeBetweenPresses > currentMaxPressTime) {
                    _presentMistakes.value += 1
                    if (_presentMistakes.value > _maxMistakes) {
                        _isSuccessful = false
                        _isCompleted.value = true
                    }
                }
            }


            // Обновляем время последнего нажатия
            lastKeyPressTime = currentTime

            _userInput.value = newText
            _presentLength.value = newText.length

            if (!tempIsCorrect and isAddingCharacter) {
                _presentMistakes.value+=1
                if (_presentMistakes.value > _maxMistakes) {
                    _isSuccessful = false
                    _isCompleted.value = true
                }
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