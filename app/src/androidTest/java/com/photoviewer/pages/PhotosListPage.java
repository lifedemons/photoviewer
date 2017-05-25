package com.photoviewer.pages;

import com.photoviewer.R;
import com.photoviewer.common.steps.PageObject;
import com.photoviewer.presentation.view.activity.PhotosListActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class PhotosListPage extends PageObject<PhotosListActivity> {

  private interface Locators {
    int PHOTOS_LIST_VIEW = R.id.photos_list;
    int PHOTOS_SEARCH_BUTTON = R.id.menu_search;
    int SEARCH_SRC_TEXT = android.support.design.R.id.search_src_text;
  }

  public PhotosListPage() {
    super(PhotosListActivity.class);
  }

  public void open() {
    getActivity();
  }

  public void checkListContainsPhoto(String title) {
    onView(withId(Locators.PHOTOS_LIST_VIEW)).check(matches(hasDescendant(withText(title))));
  }

  public void doSearchForPhoto(String title) {
    onView(withId(Locators.PHOTOS_SEARCH_BUTTON)).perform(click());
    onView(withId(Locators.SEARCH_SRC_TEXT)).perform(typeText(title));
  }
}
