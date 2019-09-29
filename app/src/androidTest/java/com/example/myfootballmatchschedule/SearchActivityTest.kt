package com.example.myfootballmatchschedule

import android.view.KeyEvent
import android.widget.AutoCompleteTextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.example.myfootballmatchschedule.SearchMatch.SearchMatchMain
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith



@RunWith(AndroidJUnit4::class)
class SearchActivityTest {
    @Rule
    @JvmField

    var activityRule = ActivityTestRule(SearchMatchMain::class.java)



    @Test
    fun testAppBehaviour() {
        delay(2)

        onView(isAssignableFrom(AutoCompleteTextView::class.java)).perform(typeText("Barcelona"), pressKey(KeyEvent.KEYCODE_ENTER))

        delay(4)

        onView(withId(R.id.rv_match_list))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        delay(2)

        onView(withId(R.id.add_to_favorite))
            .perform(click())

        onView(ViewMatchers.withText("Added to favorite"))
            .check(ViewAssertions.matches(isDisplayed()))

        delay(1)
    }

    private fun delay(second: Long = 1) {
        Thread.sleep(1000 * second)
    }
}