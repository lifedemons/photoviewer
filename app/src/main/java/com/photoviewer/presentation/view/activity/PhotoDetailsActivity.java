package com.photoviewer.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.inject.Inject;
import com.photoviewer.R;
import com.photoviewer.presentation.model.PhotoModel;
import com.photoviewer.presentation.presenter.PhotoDetailsPresenter;
import com.photoviewer.presentation.view.PhotoDetailsView;
import com.squareup.picasso.Picasso;

import roboguice.inject.InjectView;

/**
 * Activity that shows details of a certain photo.
 */
public class PhotoDetailsActivity extends RoboAppCompatActivity implements PhotoDetailsView {

    private static final String INTENT_EXTRA_PARAM_PHOTO_ID = "INTENT_PARAM_PHOTO_ID";
    private static final String INSTANCE_STATE_PARAM_PHOTO_ID = "STATE_PARAM_PHOTO_ID";

    @Inject
    private PhotoDetailsPresenter mPhotoDetailsPresenter;

    //Content Views
    @InjectView(R.id.cover_image_view)
    private ImageView mCoverImageView;
    @InjectView(R.id.title_text_view)
    private TextView mTitleTextView;

    //Data Loading Views
    @InjectView(R.id.progress_layout)
    private RelativeLayout mProgressView;
    @InjectView(R.id.retry_layout)
    private RelativeLayout mRetryView;
    @InjectView(R.id.retry_button)
    private Button mRetryButton;

    private int mPhotoId;

    public static Intent getCallingIntent(Context context, int photoId) {
        Intent callingIntent = new Intent(context, PhotoDetailsActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_PHOTO_ID, photoId);

        return callingIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_photo_details);

        initialize(savedInstanceState);
        setUpUI();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putInt(INSTANCE_STATE_PARAM_PHOTO_ID, mPhotoId);
        }
        super.onSaveInstanceState(outState);
    }

    /**
     * Initializes this activity.
     */
    private void initialize(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            this.mPhotoId = getIntent().getIntExtra(INTENT_EXTRA_PARAM_PHOTO_ID, -1);
        } else {
            this.mPhotoId = savedInstanceState.getInt(INSTANCE_STATE_PARAM_PHOTO_ID);
        }
        mPhotoDetailsPresenter.setView(this);
        mPhotoDetailsPresenter.initialize(mPhotoId);
    }

    private void setUpUI() {
//        setSupportActionBar(mToolbar);

        mRetryButton.setOnClickListener(v -> onButtonRetryClick());
    }

    @Override
    public void onResume() {
        super.onResume();
        this.mPhotoDetailsPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPhotoDetailsPresenter.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPhotoDetailsPresenter.destroy();
    }

    @Override
    public void showLoading() {
        mProgressView.setVisibility(View.VISIBLE);
        setProgressBarIndeterminateVisibility(true);
    }

    @Override
    public void hideLoading() {
        mProgressView.setVisibility(View.GONE);
        setProgressBarIndeterminateVisibility(false);
    }

    @Override
    public void showRetry() {
        mRetryView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetry() {
        mRetryView.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        showToastMessage(message);
    }

    private void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Loads all photos.
     */
    private void loadPhotoDetails() {
        if (mPhotoDetailsPresenter != null) {
            mPhotoDetailsPresenter.initialize(mPhotoId);
        }
    }

    void onButtonRetryClick() {
        loadPhotoDetails();
    }

    @Override
    public void renderPhoto(PhotoModel photoModel) {
        if (photoModel != null) {
            Picasso.with(this)
                    .load(photoModel.getUrl())
                    .placeholder(R.drawable.ic_crop_original_black)
                    .error(R.drawable.ic_error_outline_black)
                    .into(mCoverImageView);
            mTitleTextView.setText(photoModel.getTitle());
        }
    }
}
