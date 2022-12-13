package com.cutecats.android

import android.view.View
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.cutecats.android.ui.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    @Rule @JvmField
    var mActivityRule = ActivityTestRule(MainActivity::class.java)

    @Test fun scrollDownAndLoadMoreCats() {

        Thread.sleep(10000);

        onView(withId(R.id.btnLoadMore)).perform( scrollTo(),click())

        Thread.sleep(3000);

        onView(withId(R.id.btnLoadMore)).perform( scrollTo(),click())

        Thread.sleep(3000);

        onView(withId(R.id.btnLoadMore)).perform( scrollTo(),click())

        Thread.sleep(3000);

        onView(withId(R.id.btnLoadMore)).perform( scrollTo(),click())

        Thread.sleep(3000);

    }
}
