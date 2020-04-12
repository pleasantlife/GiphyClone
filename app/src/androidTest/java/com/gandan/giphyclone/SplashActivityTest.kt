package com.gandan.giphyclone

import android.content.Intent
import androidx.test.annotation.UiThreadTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.gandan.giphyclone.view.activity.SplashActivity
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class SplashActivityTest{

    @get:Rule
    val splashActivityRule = ActivityTestRule<SplashActivity>(SplashActivity::class.java)

    @Test
    fun onCreateTest() {
        assertNotNull("splashActivity isn't null", splashActivityRule)
    }

    @Test
    fun countDownTest() {
        splashActivityRule.activity.checkConnection()
    }

    @Test
    @UiThreadTest
    fun connectionToastTest() {
        splashActivityRule.activity.showConnectionToast()
    }


}