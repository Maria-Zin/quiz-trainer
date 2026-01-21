package ru.fefu.quiztrainer.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.fefu.quiztrainer.data.Question

class QuizViewModel : ViewModel() {

    private val questions = listOf(
        Question(
            id = 1,
            text = "Какой язык используется для Android-разработки с Jetpack Compose?",
            options = listOf("Java", "Python", "Kotlin", "C++"),
            correctAnswerIndex = 2
        ),
        Question(
            id = 2,
            text = "Что такое State в Jetpack Compose?",
            options = listOf(
                "База данных",
                "Состояние UI, которое может меняться",
                "Стиль текста",
                "Анимация"
            ),
            correctAnswerIndex = 1
        ),
        Question(
            id = 3,
            text = "Какая аннотация используется для Composable-функций?",
            options = listOf("@Override", "@Composable", "@Ui", "@Screen"),
            correctAnswerIndex = 1
        ),
        Question(
            id = 4,
            text = "Что такое ViewModel?",
            options = listOf(
                "Экран приложения",
                "Класс для хранения UI-логики и состояния",
                "База данных",
                "Библиотека для сетевых запросов"
            ),
            correctAnswerIndex = 1
        )
    )

    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState

    init {
    }

    fun startNewQuiz() {
        _uiState.value = QuizUiState(
            currentQuestionIndex = 0,
            score = 0,
            isQuizFinished = false,
            selectedAnswerIndex = null,
            questions = questions
        )
    }

    fun selectAnswer(index: Int) {
        _uiState.value = _uiState.value.copy(selectedAnswerIndex = index)
    }

    fun submitAnswer() {
        val state = _uiState.value
        if (state.selectedAnswerIndex == null) return

        val isCorrect = state.selectedAnswerIndex == state.questions[state.currentQuestionIndex].correctAnswerIndex
        val newScore = if (isCorrect) state.score + 1 else state.score
        val nextIndex = state.currentQuestionIndex + 1
        val isFinished = nextIndex >= state.questions.size

        _uiState.value = state.copy(
            score = newScore,
            currentQuestionIndex = nextIndex,
            isQuizFinished = isFinished,
            selectedAnswerIndex = null
        )
    }
}

data class QuizUiState(
    val currentQuestionIndex: Int = 0,
    val score: Int = 0,
    val isQuizFinished: Boolean = false,
    val selectedAnswerIndex: Int? = null,
    val questions: List<Question> = emptyList()
)