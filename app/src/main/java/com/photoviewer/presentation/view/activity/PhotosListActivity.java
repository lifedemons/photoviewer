package com.photoviewer.presentation.view.activity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.photoviewer.R;
import com.photoviewer.presentation.model.PhotoModel;
import com.photoviewer.presentation.model.PhotoStatisticsModel;
import com.photoviewer.presentation.navigation.Navigator;
import com.photoviewer.presentation.presenter.PhotoListPresenter;
import com.photoviewer.presentation.view.PhotoListView;
import com.photoviewer.presentation.view.adapter.PhotoAdapter;
import com.photoviewer.presentation.view.utils.ImageRoundedCornersTransformation;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import java.util.ArrayList;
import java.util.Collection;
import javax.inject.Inject;
import okhttp3.OkHttpClient;

public class PhotosListActivity extends DiAppCompatActivity implements PhotoListView {

    @Inject PhotoListPresenter mPhotoListPresenter;
    @Inject Navigator mNavigator;
    @Inject Picasso mPicasso;

    //Toolbar Views
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.last_opened_photo_image_view) ImageView mLastOpenedImageView;
    @BindView(R.id.opened_photo_count_text_view) TextView mOpenedPhotosCountView;

    //Content Views
    @BindView(R.id.photos_list) RecyclerView mPhotoListView;

    //Data Loading Views
    @BindView(R.id.progress_layout) RelativeLayout mProgressView;
    @BindView(R.id.retry_layout) RelativeLayout mRetryView;
    @BindView(R.id.retry_button) Button mRetryButton;

    private CheckBox mSortMenuCheckBox;

    private PhotoAdapter mPhotosListAdapter;
    private Transformation mImageTransformation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_list);

        initialize();
        loadPhotoList();
        setUpUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.photos_list, menu);
        setUpSearchMenu(menu);
        setUpSortMenu(menu);
        return true;
    }

    private void setUpSortMenu(Menu menu) {
        mSortMenuCheckBox = (CheckBox) menu.
                findItem(R.id.menu_sort).
                getActionView().
                findViewById(R.id.sort_toggle_button);

        mSortMenuCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mPhotoListPresenter.sort(!isChecked);
        });
    }

    private void setUpSearchMenu(Menu menu) {
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mPhotoListPresenter.searchByTitle(newText);
                return true;
            }
        });
        searchView.setOnCloseListener(() -> {
            mPhotoListPresenter.searchByTitle(null);
            return false;
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_sort) {
            onSortMenuSelected();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void onSortMenuSelected() {
        mSortMenuCheckBox.toggle();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPhotoListPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPhotoListPresenter.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPhotoListPresenter.destroy();
    }

    private void initialize() {
        mPhotoListPresenter.setView(this);
    }

    private void setUpUI() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");

        mPhotoListView.setLayoutManager(new LinearLayoutManager(this));

        mPhotosListAdapter = new PhotoAdapter(this, new ArrayList<>(), mPicasso);
        mPhotosListAdapter.setOnItemClickListener(onItemClickListener);
        mPhotoListView.setAdapter(mPhotosListAdapter);

        mRetryButton.setOnClickListener(v -> onButtonRetryClick());

        setupTransformation();
    }

    private void setupTransformation() {
        int radius = (int) getResources().getDimension(R.dimen.list_view_row_icon_rounding_radius);
        mImageTransformation = new ImageRoundedCornersTransformation(radius, 0);
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

    protected void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Loads all photos.
     */
    private void loadPhotoList() {
        mPhotoListPresenter.initialize();
    }

    void onButtonRetryClick() {
        loadPhotoList();
    }

    private PhotoAdapter.OnItemClickListener onItemClickListener =
            new PhotoAdapter.OnItemClickListener() {
                @Override
                public void onItemClicked(PhotoModel photoModel) {
                    if (PhotosListActivity.this.mPhotoListPresenter != null && photoModel != null) {
                        PhotosListActivity.this.mPhotoListPresenter.onPhotoClicked(photoModel);
                    }
                }
            };

    @Override
    public void renderPhotoList(Collection<PhotoModel> photoModelCollection) {
        if (photoModelCollection != null) {
            this.mPhotosListAdapter.setPhotosCollection(photoModelCollection);
        }
    }

    @Override
    public void viewPhoto(PhotoModel photoModel) {
        mNavigator.navigateToPhotoDetails(this, photoModel.getId());
    }

    @Override
    public void renderPhotoStatisticsModel(PhotoStatisticsModel photoStatisticsModel) {
        mOpenedPhotosCountView.setText(Integer.toString(photoStatisticsModel.getOpenedPhotosCount()));

        PhotoModel lastOpenedPhotoModel = photoStatisticsModel.getLastOpenedPhotoModel();
        if (lastOpenedPhotoModel != null) {
            mPicasso.load(lastOpenedPhotoModel.getThumbnailUrl())
                    .transform(mImageTransformation)
                    .placeholder(R.drawable.ic_crop_original_black)
                    .error(R.drawable.ic_error_outline_black)
                    .into(mLastOpenedImageView);
        }
    }

    @Override
    public void highlightTextInList(String textToHighlight) {
        mPhotosListAdapter.highlightText(textToHighlight);
    }
}
