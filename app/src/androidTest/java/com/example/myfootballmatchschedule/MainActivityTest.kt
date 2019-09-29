import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.example.myfootballmatchschedule.Main.MainActivity
import com.example.myfootballmatchschedule.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest{
    @Rule
    @JvmField

    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testAppBehaviour() {
        delay(6)
        onView(withId(R.id.list_liga))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(14, click()))

        onView(withId(R.id.viewpager_main))
            .perform(swipeLeft())

        onView(withId(R.id.viewpager_main))
            .perform(swipeLeft())

        delay(6)

        onView(withId(R.id.list_match_last))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(3, click()))

        delay(2)

        onView(withId(R.id.add_to_favorite))
            .perform(click())

        onView(ViewMatchers.withText("Added to favorite"))
            .check(matches(isDisplayed()))

        delay(1)

        onView(isRoot()).perform(ViewActions.pressBack())


        onView(withId(R.id.viewpager_main))
            .perform(swipeLeft())

        delay(2)

        onView(withId(R.id.rv_team_fav))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        delay(1)

        onView(withId(R.id.add_to_favorite))
            .perform(click())

        onView(ViewMatchers.withText("Removed from favorite"))
            .check(matches(isDisplayed()))

    }

    private fun delay(second: Long = 1) {
        Thread.sleep(1000 * second)
    }
}
