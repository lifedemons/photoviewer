package com.photoviewer.cucumber;

import android.os.Bundle;
import android.support.test.runner.AndroidJUnitRunner;
import com.photoviewer.BuildConfig;
import cucumber.api.android.CucumberInstrumentationCore;

public class CucumberTestRunner extends AndroidJUnitRunner {
  /**
   * This is the item Cucumber uses to identify the tags parameter, see method
   * {@link cucumber.runtime.android.Arguments#getCucumberOptionsString(Bundle)}
   */
  private static final String CUCUMBER_TAGS_KEY = "tags";
  private final CucumberInstrumentationCore instrumentationCore =
      new CucumberInstrumentationCore(this);

  @Override public void onCreate(final Bundle bundle) {
    super.onCreate(bundle);

    reformatTagsList(bundle);
    instrumentationCore.create(bundle);
  }

  private void reformatTagsList(Bundle bundle) {
    // Read tags passed as parameters and overwrite @CucumberOptions tags inside CucumberTestCase.java
    final String tags = BuildConfig.CUCUMBER_TAGS;
    if (!tags.isEmpty()) {
      // Reformat tags list to separate items with '--' as expected by Cucumber library, see method
      // cucumber-android-1.2.2.jar\cucumber\runtime\android\Arguments.class @ appendOption()
      bundle.putString(CUCUMBER_TAGS_KEY, tags.replaceAll(",", "--").replaceAll("\\s", ""));
    }
  }

  @Override public void onStart() {
    waitForIdleSync();
    instrumentationCore.start();
  }
}
