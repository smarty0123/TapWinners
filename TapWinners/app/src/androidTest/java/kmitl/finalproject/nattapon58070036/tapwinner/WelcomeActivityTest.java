package kmitl.finalproject.nattapon58070036.tapwinner;


import android.os.SystemClock;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class WelcomeActivityTest {

    @Rule
    public ActivityTestRule<WelcomeActivity> mActivityTestRule = new ActivityTestRule<>(WelcomeActivity.class);

    @Before
    public void welcomeActivityTest() throws InterruptedException {
        Thread.sleep(3000);
        onView(withId(R.id.loginButton)).check(matches(isDisplayed()));
        onView(withId(R.id.loginButton)).perform(click());
        Thread.sleep(3000);
        Thread.sleep(3000);
    }


    @Test
    public void clickPlayOnline(){
        onView(withId(R.id.btnPlay)).check(matches(isDisplayed()));
        onView(withId(R.id.btnPlay)).perform(click());
        onView(isRoot()).perform(pressBack());
    }

    @After
    public void logoutButton() throws InterruptedException {
        Thread.sleep(3000);
        onView(withId(R.id.btnLogout)).check(matches(isDisplayed()));
        onView(withId(R.id.btnLogout)).perform(click());
    }



}
