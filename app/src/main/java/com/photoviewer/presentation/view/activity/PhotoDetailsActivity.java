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
import butterknife.BindView;
import com.photoviewer.R;
import com.photoviewer.presentation.model.PhotoModel;
import com.photoviewer.presentation.presenter.PhotoDetailsPresenter;
import com.photoviewer.presentation.view.PhotoDetailsView;
import com.squareup.picasso.Picasso;
import javax.inject.Inject;

/**
 * Activity that shows details of a certain photo.
 */
public class PhotoDetailsActivity extends DiAppCompatActivity implements PhotoDetailsView {

    private static final String INTENT_EXTRA_PARAM_PHOTO_ID = "INTENT_PARAM_PHOTO_ID";
    private static final String INSTANCE_STATE_PARAM_PHOTO_ID = "STATE_PARAM_PHOTO_ID";

    @Inject PhotoDetailsPresenter mPhotoDetailsPresenter;
    @Inject Picasso mPicasso;

    //Content Views
    @BindView(R.id.cover_image_view) ImageView mCoverImageView;
    @BindView(R.id.title_text_view) TextView mTitleTextView;

    //Data Loading Views
    @BindView(R.id.progress_layout) RelativeLayout mProgressView;
    @BindView(R.id.retry_layout) RelativeLayout mRetryView;
    @BindView(R.id.retry_button) Button mRetryButton;

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
            mPicasso.load(photoModel.getUrl())
                    .placeholder(R.drawable.ic_crop_original_black)
                    .error(R.drawable.ic_error_outline_black)
                    .into(mCoverImageView);
            mTitleTextView.setText(photoModel.getTitle());
        }
    }
}
