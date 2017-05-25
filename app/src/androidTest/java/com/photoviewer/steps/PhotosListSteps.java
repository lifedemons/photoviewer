package com.photoviewer.steps;

import com.photoviewer.pages.PhotosListPage;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class PhotosListSteps {

  private PhotosListPage mPhotosListPage;

  public PhotosListSteps(PhotosListPage photosListPage) {
    mPhotosListPage = photosListPage;
  }

  @After("@functional-scenarios") public void tearDown() throws Exception {
    mPhotosListPage.tearDown();
  }

  @Given("^User opens Photos List")
  public void user_opens_the_login_page() {
    mPhotosListPage.open();
  }

  @Then("^User sees photo with title \"([^\"]*)\"$")
  public void userSeesPhotoWithTitle(String title) throws Throwable {
    mPhotosListPage.checkListContainsPhoto(title);
  }

  @When("^User searches for photo with title \"([^\"]*)\"$")
  public void userSearchesForPhotoWithTitle(String title) throws Throwable {
    mPhotosListPage.doSearchForPhoto(title);
  }
}
