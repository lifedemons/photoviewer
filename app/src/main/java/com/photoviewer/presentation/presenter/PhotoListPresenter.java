package com.photoviewer.presentation.presenter;

import android.support.annotation.NonNull;

import com.photoviewer.domain.Photo;
import com.photoviewer.domain.PhotoStatistics;
import com.photoviewer.domain.usecases.GetPhotoStatistics;
import com.photoviewer.domain.usecases.GetPhotosList;
import com.photoviewer.domain.usecases.SavePhotoStatistics;
import com.photoviewer.domain.usecases.SearchByTitle;
import com.photoviewer.domain.usecases.SimpleSubscriber;
import com.photoviewer.presentation.mapper.photo.PhotoToPhotoModel;
import com.photoviewer.presentation.mapper.photostatistics.PhotoStatisticsModelToPhotoStatistics;
import com.photoviewer.presentation.mapper.photostatistics.PhotoStatisticsToPhotoStatisticsModel;
import com.photoviewer.presentation.model.PhotoModel;
import com.photoviewer.presentation.model.PhotoStatisticsModel;
import com.photoviewer.presentation.view.PhotoListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.inject.Inject;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
public class PhotoListPresenter extends SimplePresenter {

    private PhotoStatisticsModel mPhotoStatisticsModel;

    private PhotoListView mViewList;

    //Use cases
    private final GetPhotosList mGetPhotoListUseCase;
    private final SearchByTitle mSearchByTitleUseCase;
    private final GetPhotoStatistics mGetPhotoStatisticsUseCase;
    private final SavePhotoStatistics mSavePhotoStatisticsUseCase;

    //Transformers
    private PhotoToPhotoModel mPhotoModelTransformer;
    private PhotoStatisticsToPhotoStatisticsModel mPhotoStatisticsToPhotoStatisticsModelTransformer;
    private PhotoStatisticsModelToPhotoStatistics mPhotoStatisticsModelToPhotoStatisticsTransformer;

    private AlphabetPhotoModelTitleComparator mModelTitleComparator = new AlphabetPhotoModelTitleComparator(true);
    private String mSearchedTitle;

    @Inject
    public PhotoListPresenter(GetPhotosList getPhotoListUseCase,
                              SearchByTitle searchByTitleUseCase,
                              GetPhotoStatistics getPhotoStatistics,
                              SavePhotoStatistics savePhotoStatistics) {

        mGetPhotoListUseCase = getPhotoListUseCase;
        mSearchByTitleUseCase = searchByTitleUseCase;
        mGetPhotoStatisticsUseCase = getPhotoStatistics;
        mSavePhotoStatisticsUseCase = savePhotoStatistics;

        createTransformers();
    }

    private void createTransformers() {
        mPhotoModelTransformer = new PhotoToPhotoModel();
        mPhotoStatisticsModelToPhotoStatisticsTransformer = new PhotoStatisticsModelToPhotoStatistics();
        mPhotoStatisticsToPhotoStatisticsModelTransformer = new PhotoStatisticsToPhotoStatisticsModel();
    }

    public void setView(@NonNull PhotoListView view) {
        mViewList = view;
    }

    @Override public void destroy() {
        mGetPhotoListUseCase.unsubscribe();
    }

    /**
     * Initializes the presenter by start retrieving the photo list.
     */
    public void initialize() {
        loadPhotoList();
        loadPhotoStatistics();
    }

    private void loadPhotoStatistics() {
        mGetPhotoStatisticsUseCase.execute(new PhotoStatisticsSubscriber());
    }

    public void sort(boolean ascending) {
        mModelTitleComparator = new AlphabetPhotoModelTitleComparator(ascending);

        if(mSearchedTitle == null || mSearchedTitle.isEmpty()) {
            loadPhotoList();
            mViewList.highlightTextInList(null);
        } else {
            searchByTitle(mSearchedTitle);
        }
    }

    public void searchByTitle(String title) {
        mSearchedTitle = title;

        if (title == null || title.isEmpty()) {
            loadPhotoList();
        } else {
            showViewLoading();
            mSearchByTitleUseCase.setSearchedTitle(title);
            mSearchByTitleUseCase.execute(new PhotoListSubscriber());
        }

        mViewList.highlightTextInList(title);
    }

    public void onPhotoClicked(PhotoModel photoModel) {
        mViewList.viewPhoto(photoModel);
        updatePhotoStatisticsModel(photoModel);
    }

    private void updatePhotoStatisticsModel(PhotoModel photoModel) {
        mPhotoStatisticsModel.incOpenedPhotosCount();
        mPhotoStatisticsModel.setLastOpenedPhotoModel(photoModel);

        PhotoStatistics photoStatistics = mPhotoStatisticsModelToPhotoStatisticsTransformer.transform(mPhotoStatisticsModel);
        mSavePhotoStatisticsUseCase.setPhotoStatistics(photoStatistics);
        mSavePhotoStatisticsUseCase.execute(new SimpleSubscriber<>());

        mViewList.renderPhotoStatisticsModel(mPhotoStatisticsModel);
    }

    /**
     * Loads all photos.
     */
    public void loadPhotoList() {
        hideViewRetry();
        showViewLoading();
        getPhotoList();
    }

    private void showViewLoading() {
        mViewList.showLoading();
    }

    private void hideViewLoading() {
        mViewList.hideLoading();
    }

    private void showViewRetry() {
        mViewList.showRetry();
    }

    private void hideViewRetry() {
        mViewList.hideRetry();
    }

    private void showErrorMessage() {
        //TODO implement
        String errorMessage = "Error happened";
        mViewList.showError(errorMessage);
    }

    private void showPhotoStatisticsInView(PhotoStatistics photoStatistics) {
        mPhotoStatisticsModel = mPhotoStatisticsToPhotoStatisticsModelTransformer.transform(photoStatistics);
        mViewList.renderPhotoStatisticsModel(mPhotoStatisticsModel);
    }

    private void showPhotoListInView(List<Photo> photoList) {
        final List<PhotoModel> photoModelsList =
                new ArrayList<>(mPhotoModelTransformer.transform(photoList));

        Collections.sort(photoModelsList, mModelTitleComparator);

        mViewList.renderPhotoList(photoModelsList);
    }

    private void getPhotoList() {
        mGetPhotoListUseCase.execute(new PhotoListSubscriber());
    }

    private final class PhotoListSubscriber extends SimpleSubscriber<List<Photo>> {

        @Override
        public void onCompleted() {
            PhotoListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            PhotoListPresenter.this.hideViewLoading();
            PhotoListPresenter.this.showErrorMessage();
            PhotoListPresenter.this.showViewRetry();
        }

        @Override
        public void onNext(List<Photo> photos) {
            PhotoListPresenter.this.showPhotoListInView(photos);
        }
    }

    private final class PhotoStatisticsSubscriber extends SimpleSubscriber<PhotoStatistics> {

        @Override
        public void onCompleted() {
            PhotoListPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            PhotoListPresenter.this.showPhotoStatisticsInView(new PhotoStatistics());
        }

        @Override
        public void onNext(PhotoStatistics photoStatistics) {
            PhotoListPresenter.this.showPhotoStatisticsInView(photoStatistics);
        }
    }

    private class AlphabetPhotoModelTitleComparator implements Comparator<PhotoModel> {
        private boolean mIsAscending;

        AlphabetPhotoModelTitleComparator(boolean isAscending) {
            mIsAscending = isAscending;
        }

        @Override
        public int compare(@NonNull PhotoModel lhs, @NonNull PhotoModel rhs) {
            return lhs.getTitle().compareToIgnoreCase(rhs.getTitle())
                    * (mIsAscending ? 1 : -1);
        }
    }
}

