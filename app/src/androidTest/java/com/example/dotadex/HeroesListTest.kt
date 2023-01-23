package com.example.dotadex

import android.content.res.Resources
import android.view.KeyEvent
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.PerformException
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.dotadex.presentation.MainActivity
import org.junit.Rule
import org.junit.Test


class HeroesListTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun isListCreated() {
        onView(withId(R.id.rv_heroList))
            .perform(
                RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                    hasDescendant(withText("Anti-Mage")),
                )
            )
            .perform(
                RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                    hasDescendant(withText("Zeus")),
                )
            )
    }

    @Test
    fun searchHeroesExpectNecrophos() {
        onView(
            withId(
                Resources.getSystem().getIdentifier(
                    "search_src_text",
                    "id", "android"
                )
            )
        ).perform(clearText(), typeText("Necrophos"))
            .perform(pressKey(KeyEvent.KEYCODE_ENTER))

        onView(withId(R.id.rv_heroList))
            .perform(
                RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                    hasDescendant(withText("Necrophos")),
                )
            )
    }

    @Test
    fun isFilterDialogCreated() {
        onView(withId(R.id.fab_filter)).check(matches(isDisplayed()))
            .perform(click())

        onView(withText(R.string.filter_dialogue_title)).check(matches(isDisplayed()))

        onView(withText("str")).check(matches(isDisplayed()))
        onView(withText("agi")).check(matches(isDisplayed()))
        onView(withText("int")).check(matches(isDisplayed()))

        onView(withText(R.string.ok)).check(matches(isDisplayed()))
        onView(withText(R.string.cancel)).check(matches(isDisplayed()))

    }

//    @Test(expected = PerformException::class)
//    private fun checkIfHeroIsNotDisplayed(heroName: String) {
//        onView(withId(R.id.rv_heroList))
//            .perform(
//                RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
//                    hasDescendant(withText(heroName))
//                )
//            )
//    }

    @Test(expected = PerformException::class)
    fun filterListExpectStrOnly() {
        onView(withId(R.id.fab_filter)).check(matches(isDisplayed()))
            .perform(click())

        onView(withText(R.string.filter_dialogue_title)).check(matches(isDisplayed()))

        onView(withText("str")).check(matches(isDisplayed()))
            .perform(click())

        onView(withText(R.string.ok)).perform(click())

        onView(withId(R.id.rv_heroList))
            .perform(
                RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                    hasDescendant(withText("Riki"))
                )
            )
        onView(withId(R.id.rv_heroList))
            .perform(
                RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                    hasDescendant(withText("Bane"))
                )
            )

    }

    @Test(expected = PerformException::class)
    fun filterListExpectAgiOnly() {
        onView(withId(R.id.fab_filter)).check(matches(isDisplayed()))
            .perform(click())

        onView(withText(R.string.filter_dialogue_title)).check(matches(isDisplayed()))

        onView(withText("agi")).check(matches(isDisplayed()))
            .perform(click())

        onView(withText(R.string.ok)).perform(click())

        onView(withId(R.id.rv_heroList))
            .perform(
                RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                    hasDescendant(withText("Axe"))
                )
            )
        onView(withId(R.id.rv_heroList))
            .perform(
                RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                    hasDescendant(withText("Bane"))
                )
            )
    }

    @Test(expected = PerformException::class)
    fun filterListExpectIntOnly() {
        onView(withId(R.id.fab_filter)).check(matches(isDisplayed()))
            .perform(click())

        onView(withText(R.string.filter_dialogue_title)).check(matches(isDisplayed()))

        onView(withText("int")).check(matches(isDisplayed()))
            .perform(click())

        onView(withText(R.string.ok)).perform(click())

        onView(withId(R.id.rv_heroList))
            .perform(
                RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                    hasDescendant(withText("Axe"))
                )
            )
        onView(withId(R.id.rv_heroList))
            .perform(
                RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                    hasDescendant(withText("Riki"))
                )
            )
    }

    @Test(expected = PerformException::class)
    fun filterListExpectAgiAndIntOnly() {
        onView(withId(R.id.fab_filter)).check(matches(isDisplayed()))
            .perform(click())

        onView(withText(R.string.filter_dialogue_title)).check(matches(isDisplayed()))

        onView(withText("agi")).check(matches(isDisplayed()))
            .perform(click())

        onView(withText("int")).check(matches(isDisplayed()))
            .perform(click())

        onView(withText(R.string.ok)).perform(click())

        onView(withId(R.id.rv_heroList))
            .perform(
                RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                    hasDescendant(withText("Axe"))
                )
            )

    }

}