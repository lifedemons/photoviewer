package com.photoviewer.common.steps;

import android.app.Activity;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import com.jakewharton.espresso.OkHttp3IdlingResource;
import com.photoviewer.di.TestsModule;
import com.photoviewer.presentation.PhotoViewerApplication;
import javax.inject.Inject;
import okhttp3.OkHttpClient;
import org.junit.Rule;

public class PageObject<T extends Activity> {

  @Rule public ActivityTestRule<T> mActivityRule;

  @Inject OkHttpClient mOkHttpClient;

  private T mActivity;
  private IdlingResource mOkHttp3IdlingResource;

  public PageObject(Class<T> activityClass) {
    mActivityRule = new ActivityTestRule<>(activityClass, false, false);

    injectFieldsFromApp();
    setUpIdlingResources();
  }

  private void injectFieldsFromApp() {
    PhotoViewerApplication.getScopedGraph(new TestsModule()).inject(this);
  }

  private void setUpIdlingResources() {
    mOkHttp3IdlingResource = OkHttp3IdlingResource.create("OkHttp", mOkHttpClient);
    Espresso.registerIdlingResources(mOkHttp3IdlingResource);
  }

  public T getActivity() {
    mActivity = mActivityRule.launchActivity(null);

    return mActivity;
  }

  public void tearDown() throws Exception {
    mActivity.finish();
    tearDownIdlingResources();
  }

  private void tearDownIdlingResources() {
    Espresso.unregisterIdlingResources(mOkHttp3IdlingResource);
  }
}
