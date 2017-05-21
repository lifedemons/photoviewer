package com.photoviewer.presentation.presenter;

import android.support.annotation.NonNull;

import com.photoviewer.domain.Photo;
import com.photoviewer.domain.usecases.GetPhotoDetails;
import com.photoviewer.domain.usecases.SimpleSubscriber;
import com.photoviewer.presentation.mapper.photo.PhotoToPhotoModel;
import com.photoviewer.presentation.model.PhotoModel;
import com.photoviewer.presentation.view.PhotoDetailsView;
import javax.inject.Inject;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
public class PhotoDetailsPresenter extends SimplePresenter {

    private final GetPhotoDetails mGetPhotoDetailsUseCase;
    private final PhotoToPhotoModel mPhotoModelTransformer;
    /**
     * id used to retrieve photo details
     */
    private int mPhotoId;
    private PhotoDetailsView mViewDetailsView;

    @Inject
    public PhotoDetailsPresenter(GetPhotoDetails getPhotoDetailsUseCase) {
        mGetPhotoDetailsUseCase = getPhotoDetailsUseCase;
        mPhotoModelTransformer = new PhotoToPhotoModel();
    }

    public void setView(@NonNull PhotoDetailsView view) {
        mViewDetailsView = view;
    }

    @Override
    public void destroy() {
        mGetPhotoDetailsUseCase.unsubscribe();
    }

    /**
     * Initializes the presenter by start retrieving photo details.
     */
    public void initialize(int photoId) {
        mPhotoId = photoId;
        loadPhotoDetails();
    }

    /**
     * Loads photo details.
     */
    private void loadPhotoDetails() {
        hideViewRetry();
        showViewLoading();
        getPhotoDetails();
    }

    private void showViewLoading() {
        mViewDetailsView.showLoading();
    }

    private void hideViewLoading() {
        mViewDetailsView.hideLoading();
    }

    private void showViewRetry() {
        mViewDetailsView.showRetry();
    }

    private void hideViewRetry() {
        mViewDetailsView.hideRetry();
    }

    private void showErrorMessage() {
        //TODO implement
        String errorMessage = "Error happened";
        mViewDetailsView.showError(errorMessage);
    }

    private void showPhotoDetailsInView(Photo photo) {
        final PhotoModel photoModel = mPhotoModelTransformer.transform(photo);
        mViewDetailsView.renderPhoto(photoModel);
    }

    private void getPhotoDetails() {
        mGetPhotoDetailsUseCase.setPhotoId(mPhotoId);
        mGetPhotoDetailsUseCase.execute(new PhotoDetailsSubscriber());
    }

    private final class PhotoDetailsSubscriber extends SimpleSubscriber<Photo> {

        @Override
        public void onCompleted() {
            PhotoDetailsPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            PhotoDetailsPresenter.this.hideViewLoading();
            PhotoDetailsPresenter.this.showErrorMessage();
            PhotoDetailsPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(Photo photo) {
            PhotoDetailsPresenter.this.showPhotoDetailsInView(photo);
        }
    }
}
