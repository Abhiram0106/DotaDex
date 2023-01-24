package com.example.dotadex

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.dotadex.presentation.MainActivity
import org.junit.Rule
import org.junit.Test

class HeroesDetailsTest {

    @get:Rule
    val activityScenario = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun isDataDisplayed() {
        onView(withId(R.id.rv_heroList))
            .perform(
                RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                    hasDescendant(withText("Necrophos")),
                    click()
                )
            )
        onView(withId(R.id.tv_heroName)).check(matches(withText("Necrophos")))
    }

    @Test
    fun pressBackButton_ExpectHeroList() {
        onView(withId(R.id.rv_heroList))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    1,
                    click()
                )
            )
        Espresso.pressBack()
        onView(withId(R.id.rv_heroList))
            .perform(
                RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(
                    hasDescendant(withText("Razor"))
                )
            )
    }
}