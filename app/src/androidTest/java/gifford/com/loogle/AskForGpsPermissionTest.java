package gifford.com.loogle;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AskForGpsPermissionTest {

  @Rule
  public ActivityTestRule<HomeActivity> activityTestRule =
      new ActivityTestRule<>(HomeActivity.class, false, false);

  /**
   * Throws AssertionError if it can't find a UiObject with given string.
   * @param device The DUT, where we are finding a UiObject.
   * @param text The string to search for.
   */
  public void assertObjectWithTextVisible(UiDevice device, String text) {
    UiObject objectWithText = device.findObject(new UiSelector().text(text));
    if (!objectWithText.exists()) {
      throw new AssertionError("View with text <" + text + "> not found!");
    }
  }

  // Note - must manually reset permission for this test to pass
  // TODO - find a way to automate resetting the permission
  // TODO - get it to work for SDK <23
  @Test
  public void askForGpsPermissionTest() throws UiObjectNotFoundException {

    activityTestRule.launchActivity(null);

    SystemClock.sleep(1000);

    assertObjectWithTextVisible(UiDevice.getInstance(getInstrumentation()), "Allow");
  }
}
