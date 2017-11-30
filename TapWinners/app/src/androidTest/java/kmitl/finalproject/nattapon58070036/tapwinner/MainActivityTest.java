package kmitl.finalproject.nattapon58070036.tapwinner;


import android.content.Intent;

import android.support.test.InstrumentationRegistry;

import android.support.test.espresso.action.ViewActions;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;

import android.test.suitebuilder.annotation.LargeTest;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.doubleClick;

import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.action.ViewActions.replaceText;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    private UiDevice mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    @Rule
    public IntentsTestRule<WelcomeActivity> mActivityTestRule = new IntentsTestRule<>(WelcomeActivity.class);

    @Before
    public void waitWelcomeActivity() throws InterruptedException{
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
        Thread.sleep(1000);
        onView(withId(R.id.btnPlayAgain)).perform(click());
        onView(withId(R.id.playScreen)).perform(click());
        Thread.sleep(1000);
        mDevice.pressBack();
        Thread.sleep(8000);
    }

    @Test
    public void clickBackToMain() throws InterruptedException {
        clickPlayOnline();
        onView(withId(R.id.btnBackToMain)).perform(click());

    }

    @Test
    public void clickChatRoom() throws InterruptedException {
        onView(withId(R.id.btnChatRoom)).check(matches(isDisplayed()));
        onView(withId(R.id.btnChatRoom)).perform(click());
        Thread.sleep(3000);
        onView(withId(R.id.et_message)).perform(replaceText("Hello"));
        onView(withId(R.id.btnSend)).perform(click());
    }

    @Test
    public void clickPlayVSFriendDraw() throws InterruptedException {
        onView(withId(R.id.btnVSFriend)).check(matches(isDisplayed()));
        onView(withId(R.id.btnVSFriend)).perform(click());
        onView(withId(R.id.player1Container)).check(matches(isDisplayed()));
        onView(withId(R.id.player1Container)).perform(click());
        Thread.sleep(8000);
        mDevice.click(150, 150);
        Thread.sleep(8000);
    }

    @Test
    public void clickPlayVSFriendPinkWin() throws InterruptedException {
        onView(withId(R.id.btnVSFriend)).check(matches(isDisplayed()));
        onView(withId(R.id.btnVSFriend)).perform(click());
        onView(withId(R.id.player1Container)).check(matches(isDisplayed()));
        onView(withId(R.id.player1Container)).perform(doubleClick());
        Thread.sleep(8000);
        mDevice.click(150, 150);
        Thread.sleep(8000);
    }

    @Test
    public void clickPlayVsFriendBlueWin() throws InterruptedException {
        onView(withId(R.id.btnVSFriend)).check(matches(isDisplayed()));
        onView(withId(R.id.btnVSFriend)).perform(click());
        onView(withId(R.id.player1Container)).check(matches(isDisplayed()));
        onView(withId(R.id.player1Container)).perform(click());
        Thread.sleep(8000);
        mDevice.click(150, 150);
        Thread.sleep(1000);
        mDevice.click(150, 150);
        Thread.sleep(8000);
    }

    @Test
    public void clickShareScore() throws InterruptedException {
        clickPlayOnline();
        onView(withId(R.id.btnShare)).perform(click());
        Thread.sleep(3000);
        intended(chooser(allOf(
                hasAction(Intent.ACTION_SEND)
        )));
        mDevice.pressBack();

    }

    public static Matcher<Intent> chooser(Matcher<Intent> matcher) {
        return allOf(hasAction(Intent.ACTION_CHOOSER), hasExtra(is(Intent.EXTRA_INTENT), matcher));
    }
}
