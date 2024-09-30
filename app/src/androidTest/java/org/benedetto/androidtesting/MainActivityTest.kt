package org.benedetto.androidtesting

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.isClickable
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test

class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testButtonIsClickable(){
        onView(withId(R.id.button)).check(matches(isClickable()))
    }

    @Test
    fun testMultiWindowDialog(){
        //Click the button in the main window to open the dialog
        onView(withId(R.id.button)).perform(click())

        //Verify the dialog's title and message are displayed
        onView(withText("Test Dialog"))
            .inRoot(isDialog())
            //look inside the dialog window
            .check(matches(isDisplayed()))
            onView(withText("This is a dialog"))
                .inRoot(isDialog())
                //Look inside the dialog window
                .check(matches(isDisplayed()))
            //Click the Close button to dismiss the dialog
            onView(withText("Close"))
                .inRoot(isDialog())
                .perform(click())

        //Verify the dialog is closed by checking that the button in the main window is visible again
        onView(withId(R.id.button)).check(matches(isDisplayed()))

    }
}