
package com.example.passwordguard

data class PasswordResult(
    val score: Int,
    val strength: String,
    val suggestions: List<String>
)

object PasswordAnalyzer {

    fun analyze(password: String): PasswordResult {

        if (password.isEmpty()) {
            return PasswordResult(
                score = 0,
                strength = "Brak hasla",
                suggestions = listOf("Wprowadz haslo, aby je sprawdzic.")
            )
        }

        var score = 0
        val suggestions = mutableListOf<String>()

        if (password.length >= 12) {
            score += 30
        } else {
            suggestions.add("Uzyj co najmniej 12 znakow.")
        }

        if (password.length >= 16) {
            score += 10
        }

        if (password.any { it.isLowerCase() }) {
            score += 15
        } else {
            suggestions.add("Dodaj male litery.")
        }

        if (password.any { it.isUpperCase() }) {
            score += 15
        } else {
            suggestions.add("Dodaj wielkie litery.")
        }

        if (password.any { it.isDigit() }) {
            score += 15
        } else {
            suggestions.add("Dodaj cyfry.")
        }

        if (password.any { !it.isLetterOrDigit() }) {
            score += 15
        } else {
            suggestions.add("Dodaj znaki specjalne.")
        }

        val strength = when {
            score < 40 -> "Slabe"
            score < 70 -> "Srednie"
            score < 90 -> "Dobre"
            else -> "Bardzo dobre"
        }

        return PasswordResult(
            score = score,
            strength = strength,
            suggestions = suggestions
        )
    }
}
