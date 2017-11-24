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
import static android.support.test.espresso.action.ViewActions.replaceText;
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
    }

    @Test
    public void clickLogin() throws InterruptedException {
        onView(withId(R.id.btnLogin)).perform(click());
        Thread.sleep(3000);
    }

    @Test
    public void clickPlayOnline() throws InterruptedException {
        onView(withId(R.id.btnPlay)).check(matches(isDisplayed()));
        onView(withId(R.id.btnPlay)).perform(click());
        onView(withId(R.id.playScreen)).perform(click());
        Thread.sleep(8000);
    }

    @Test
    public void clickScoreBoard() throws InterruptedException {
        clickPlayOnline();
        onView(withId(R.id.btnScoreboard)).perform(click());
        Thread.sleep(2000);
        onView(isRoot()).perform(ViewActions.pressBack());
    }

    @Test
    public void clickPlayAgain() throws InterruptedException {
        clickPlayOnline();
        onView(withId(R.id.btnPlayAgain)).perform(click());
        onView(withId(R.id.playScreen)).perform(click());
        Thread.sleep(8000);
    }

    @Test
    public void clickBackToMain() throws InterruptedException {
        clickPlayOnline();
        onView(withId(R.id.btnBackToMain)).perform(click());

    }

    @Test
    public void clickChatRoom() throws InterruptedException {
        clickLogin();
        Thread.sleep(3000);
        onView(withId(R.id.btnChatRoom)).check(matches(isDisplayed()));
        onView(withId(R.id.btnChatRoom)).perform(click());
        Thread.sleep(3000);
        onView(withId(R.id.et_message)).perform(replaceText("Hello"));
        onView(withId(R.id.btnSend)).perform(click());
    }

    @Test
    public void clickLogout(){
        onView(withId(R.id.btnLogout)).perform(click());
    }


}
