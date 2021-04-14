package com.nayan.varun

import android.support.test.runner.AndroidJUnit4
import org.junit.runner.RunWith
import android.support.test.rule.ActivityTestRule
import com.nayan.varun.ui.repo_list.ImageListActivity
import org.junit.Rule
import org.junit.Test
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.content.Intent


@RunWith(AndroidJUnit4::class)
class ImageListActivityTest {

    @get:Rule
    var imageListActivity: ActivityTestRule<ImageListActivity> = object : ActivityTestRule<ImageListActivity>(ImageListActivity::class.java) {

        override fun getActivityIntent(): Intent {
            return Intent()
        }
    }

    @Test
    fun shouldShowLoaderWhenLaunched() {
        onView(withId(R.id.swipeLayout)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldShowLocalDbDataOnInitialLaunchWithoutInternet() {
        onView(withId(R.id.layoutNoRepo)).check(matches(isDisplayed()))
    }

    @Test
    fun shouldShowNoRepoOnInitialLaunchWithoutInternet() {
        onView(withId(R.id.swipeLayout)).check(matches(isDisplayed()))
    }

}