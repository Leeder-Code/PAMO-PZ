package com.example.pamo_pz

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AddGoalActivityTest {

    @Test
    fun testMonkey() {
        // Launch the AddGoalActivity
        ActivityScenario.launch(MainActivity::class.java)

        onView(withId((R.id.btnSavings))).perform(click())
        onView(withId((R.id.buttonAddGoal))).perform(click())


//        ActivityScenario.launch(AddGoalActivity::class.java)

        // Perform random interactions
        repeat(10) {
            enterTextIntoEditText(R.id.editTextGoalName, "Goal $it")
            enterTextIntoEditText(R.id.editTextGoalDescription, "Description $it")
            enterTextIntoEditText(R.id.editTextMonthlyAmount, (100 + it * 10).toString())
            enterTextIntoEditText(R.id.editTextTargetAmount, (500 + it * 50).toString())

            // Click the Submit button
            onView(withId(R.id.buttonSubmitGoal)).perform(click())

            // Wait for the data to be saved (optional)
            Thread.sleep(1000)

            onView(withId((R.id.buttonAddGoal))).perform(click())
            // Re-launch the activity (simulate going back)
//            ActivityScenario.launch(AddGoalActivity::class.java)
        }
    }

    /**
     * Helper method to enter text into an EditText.
     *
     * @param editTextId The ID of the EditText.
     * @param text The text to enter.
     */
    private fun enterTextIntoEditText(editTextId: Int, text: String) {
        onView(withId(editTextId)).perform(clearText(), typeText(text), closeSoftKeyboard())
    }
}
