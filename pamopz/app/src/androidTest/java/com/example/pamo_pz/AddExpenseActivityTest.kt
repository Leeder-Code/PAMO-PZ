package com.example.pamo_pz

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AddExpenseActivityTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<AddExpenseActivity> =
        ActivityScenarioRule(AddExpenseActivity::class.java)

    @Test
    fun testFillAndSubmitExpenseForm() {
        // Typing expense name
        Espresso.onView(withId(R.id.editTextExpenseName))
            .perform(typeText("Groceries"), closeSoftKeyboard())

        // Selecting month from spinner
        Espresso.onView(withId(R.id.spinnerMonth))
            .perform(click())
        Espresso.onData(withSpinnerText("January")).perform(click())

        // Selecting category from spinner
        Espresso.onView(withId(R.id.spinnerExpenseCategory))
            .perform(click())
        Espresso.onData(withSpinnerText("Food")).perform(click())

        // Typing expense amount
        Espresso.onView(withId(R.id.editTextExpenseAmount))
            .perform(typeText("50.0"), closeSoftKeyboard())

        // Clicking on submit button
        Espresso.onView(withId(R.id.buttonSubmitExpense))
            .perform(click())
    }

    private fun withSpinnerText(text: String): org.hamcrest.Matcher<Any> {
        return object : org.hamcrest.TypeSafeMatcher<Any>() {
            override fun matchesSafely(item: Any?): Boolean {
                return item.toString() == text
            }

            override fun describeTo(description: org.hamcrest.Description?) {
                description?.appendText("with spinner item text: $text")
            }
        }
    }
}
