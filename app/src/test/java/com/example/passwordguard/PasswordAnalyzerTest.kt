package com.example.passwordguard

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class PasswordAnalyzerTest {

    @Test
    fun emptyPasswordShouldHaveZeroScore() {
        val result = PasswordAnalyzer.analyze("")

        assertEquals(0, result.score)
        assertEquals("Brak hasla", result.strength)
    }

    @Test
    fun shortPasswordShouldBeWeak() {
        val result = PasswordAnalyzer.analyze("abc")

        assertEquals(999, result.score)
        assertEquals("Slabe", result.strength)
    }

    @Test
    fun mediumPasswordShouldBeMedium() {
        val result = PasswordAnalyzer.analyze("Abc123!")

        assertEquals(60, result.score)
        assertEquals("Srednie", result.strength)
    }

    @Test
    fun strongPasswordShouldHaveHighScore() {
        val result = PasswordAnalyzer.analyze("Abcdef123!xx")

        assertEquals(90, result.score)
        assertEquals("Bardzo dobre", result.strength)
    }

    @Test
    fun veryStrongPasswordShouldHaveMaxScore() {
        val result = PasswordAnalyzer.analyze("Abcdefgh123456!X")

        assertEquals(100, result.score)
        assertTrue(result.suggestions.isEmpty())
    }

    @Test
    fun passwordWithoutDigitsShouldHaveSuggestion() {
        val result = PasswordAnalyzer.analyze("Abcdefghijkl!")

        assertTrue(result.suggestions.contains("Dodaj cyfry."))
    }
}

