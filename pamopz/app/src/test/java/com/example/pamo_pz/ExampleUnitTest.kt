package com.example.pamo_pz

import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.mock


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }


}
/**
 * Unit tests for [GoalDetailsActivity] methods related to goal progress calculations.
 */
class GoalDetailsActivityUnitTest {

    private val activity: GoalDetailsActivity = mock()

    @Test
    fun testGetProgress() {
        assertEquals("Progress: 50%", activity.getProgress(100.0, 50.0))
        assertEquals("Progress: 75%", activity.getProgress(100.0, 75.0))
        assertEquals("Progress: 0%", activity.getProgress(100.0, 0.0))
        assertEquals("Progress: 100%", activity.getProgress(100.0, 100.0))
        assertEquals("Progress: 25%", activity.getProgress(200.0, 50.0))
    }
    @Test
    fun testGetMonthsLeft() {
        assertEquals("5 months", activity.getMonthsLeft(1000.0, 500.0, 100.0))
        assertEquals("10 months", activity.getMonthsLeft(2000.0, 1000.0, 100.0))
        assertEquals("1 months", activity.getMonthsLeft(1100.0, 1000.0, 100.0))
        assertEquals("0 months", activity.getMonthsLeft(1000.0, 1000.0, 100.0))
        assertEquals("2 months", activity.getMonthsLeft(200.0, 100.0, 50.0))
    }

    @Test
    fun testGetRemainingValue() {
        assertEquals("$500.0", activity.getRemainingValue(1000.0, 500.0))
        assertEquals("$1000.0", activity.getRemainingValue(2000.0, 1000.0))
        assertEquals("$0.0", activity.getRemainingValue(1000.0, 1000.0))
        assertEquals("$150.0", activity.getRemainingValue(300.0, 150.0))
        assertEquals("$50.0", activity.getRemainingValue(100.0, 50.0))
    }
}