package com.itunessearchassesment.view.fragments

import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.itunessearchassesment.MainActivity
import com.itunessearchassesment.R
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ITunesActivityTest {
    @Rule
    @JvmField
    val rule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        rule.activity
    }

    @After
    fun tearDown() {
    }

    @Test
    fun recyclerViewScroll() {
        Espresso.closeSoftKeyboard()
        Thread.sleep(2000)

        val recyclerView: RecyclerView = rule.activity.findViewById(R.id.recyclerview_album)
        val itemCount = recyclerView.adapter?.itemCount

        val viewInteraction = Espresso.onView(ViewMatchers.withId(R.id.recyclerview_album))
            .inRoot(
                RootMatchers.withDecorView(
                    Matchers.`is`(rule.activity.window.decorView)
                )
            )
        viewInteraction.perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                itemCount!!.minus(1)
            )
        )

        Thread.sleep(1000)

        viewInteraction.perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                0
            )
        )

        Thread.sleep(500)
    }

    @Test
    fun recyclerViewAddButtonClick() {
        Espresso.closeSoftKeyboard()
        Thread.sleep(2000)
        val viewInteraction = Espresso.onView(ViewMatchers.withId(R.id.recyclerview_album)).inRoot(
            RootMatchers.withDecorView(
                Matchers.`is`(rule.activity.window.decorView)
            )
        )

        viewInteraction.perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                clickChildViewWithId(R.id.btn_add_to_cart)
            )
        )
        viewInteraction.perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                1,
                clickChildViewWithId(R.id.btn_add_to_cart)
            )
        )
        viewInteraction.perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                2,
                clickChildViewWithId(R.id.btn_add_to_cart)
            )
        )

        Thread.sleep(1000)

        onView(withId(R.id.iv_cart))
            .perform(click())
        Thread.sleep(1000)

        val viewInteractionCart =
            onView(withId(R.id.recyclerview_cart)).inRoot(
                RootMatchers.withDecorView(
                    Matchers.`is`(rule.activity.window.decorView)
                )
            )

        viewInteractionCart.perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                clickChildViewWithId(R.id.btn_add_to_cart)
            )
        )
        Thread.sleep(1000)

        viewInteractionCart.perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                1,
                clickChildViewWithId(R.id.btn_add_to_cart)
            )
        )
        Thread.sleep(1000)

        Espresso.pressBack()

        Thread.sleep(1000)
    }

    @Test
    fun searchData() {
        Espresso.closeSoftKeyboard()
        Thread.sleep(3000)
        onView(withId(R.id.search_album)).perform(typeText("fall"))
        Thread.sleep(1000)
        onView(withId(R.id.search_album)).perform(typeText(""))
        Thread.sleep(1000)
        onView(withId(R.id.search_album)).perform(typeText("mariah"))
        Thread.sleep(3000)
    }

    private fun typeText(text: String): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return allOf(isDisplayed(), isAssignableFrom(SearchView::class.java))
            }

            override fun getDescription(): String {
                return "Set text"
            }

            override fun perform(uiController: UiController, view: View) {
                (view as SearchView).setQuery(text, false)
            }
        }
    }

    private fun clickChildViewWithId(id: Int): ViewAction? {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View>? {
                return null
            }

            override fun getDescription(): String {
                return "Click on a child view with specified id."
            }

            override fun perform(uiController: UiController?, view: View) {
                val v: View = view.findViewById(id)
                v.performClick()
            }
        }
    }

    @Test
    fun sortBy_CollectionName() {
        Espresso.closeSoftKeyboard()
        Thread.sleep(3000)
        openActionBarOverflowOrOptionsMenu(rule.activity)
        Thread.sleep(1000)
        onView(withText(R.string.collection_name)).perform(click())
        Thread.sleep(3000)
    }

    @Test
    fun sortBy_TrackName() {
        Espresso.closeSoftKeyboard()
        Thread.sleep(3000)
        openActionBarOverflowOrOptionsMenu(rule.activity)
        Thread.sleep(1000)
        onView(withText(R.string.track_name)).perform(click())
        Thread.sleep(3000)
    }

    @Test
    fun sortBy_ArtistName() {
        Espresso.closeSoftKeyboard()
        Thread.sleep(3000)
        openActionBarOverflowOrOptionsMenu(rule.activity)
        Thread.sleep(1000)
        onView(withText(R.string.artist_name)).perform(click())
        Thread.sleep(3000)
    }

    @Test
    fun sortBy_CollectionPrice() {
        Espresso.closeSoftKeyboard()
        Thread.sleep(3000)
        openActionBarOverflowOrOptionsMenu(rule.activity)
        Thread.sleep(1000)
        onView(withText(R.string.collection_price)).perform(click())
        Thread.sleep(3000)
    }

}