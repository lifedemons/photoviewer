package com.photoviewer.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.photoviewer.R;
import com.photoviewer.presentation.model.PhotoModel;
import com.photoviewer.presentation.view.utils.ImageRoundedCornersTransformation;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import java.util.Collection;
import java.util.List;

/**
 * Adapter that manages a collection of {@link PhotoModel}.
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {

    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
  private final Picasso mPicasso;
  private List<PhotoModel> mPhotoModelsList;
    private OnItemClickListener mOnItemClickListener;
    private Transformation mImageTransformation;


    //Highlighting
    private String mTextToHighlight;
    private static final String STRING_PREPARED_HIGHLIGHT_MARKUP = "<font color='red'>%s</font>";

  public PhotoAdapter(Context context, Collection<PhotoModel> photoModelsCollection,
      Picasso picasso) {
    validatePhotosCollection(photoModelsCollection);
    mContext = context;
    mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    mPhotoModelsList = (List<PhotoModel>) photoModelsCollection;
    mPicasso = picasso;

    setupTransformation();
  }

    public void highlightText(String textToHighlight) {
        mTextToHighlight = textToHighlight;
    }

    @Override
    public int getItemCount() {
        return (mPhotoModelsList != null) ? mPhotoModelsList.size() : 0;
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.list_item_photo, parent, false);
        PhotoViewHolder photoViewHolder = new PhotoViewHolder(view);

        return photoViewHolder;
    }

  @Override public void onBindViewHolder(PhotoViewHolder holder, final int position) {
    final PhotoModel photoModel = mPhotoModelsList.get(position);
    setText(holder.mTextViewTitle, photoModel.getTitle());
    mPicasso.load(photoModel.getThumbnailUrl())
        .placeholder(R.drawable.ic_crop_original_black)
        .error(R.drawable.ic_error_outline_black)
        .transform(mImageTransformation)
        .into(holder.mPhotoImageView);

    holder.itemView.setOnClickListener(v -> {
      if (PhotoAdapter.this.mOnItemClickListener != null) {
        PhotoAdapter.this.mOnItemClickListener.onItemClicked(photoModel);
      }
    });
  }

    private void setText(TextView textView, String original) {
        if (mTextToHighlight != null && !mTextToHighlight.isEmpty()) {
            String preparedMarkup = String.format(STRING_PREPARED_HIGHLIGHT_MARKUP, mTextToHighlight);
            String highlighted = original.replaceAll(mTextToHighlight, preparedMarkup);
            textView.setText(Html.fromHtml(highlighted));
        } else {
            textView.setText(original);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setPhotosCollection(Collection<PhotoModel> photoModelsCollection) {
        validatePhotosCollection(photoModelsCollection);
        mPhotoModelsList = (List<PhotoModel>) photoModelsCollection;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    private void setupTransformation() {
        int radius = (int) mContext.getResources().getDimension(R.dimen.list_view_row_icon_rounding_radius);
        mImageTransformation = new ImageRoundedCornersTransformation(radius, 0);
    }

    private void validatePhotosCollection(Collection<PhotoModel> photoModelsCollection) {
        if (photoModelsCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    public interface OnItemClickListener {
        void onItemClicked(PhotoModel photoModel);
    }

    static class PhotoViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextViewTitle;
        private ImageView mPhotoImageView;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            findViews(itemView);
        }

        private void findViews(View itemView) {
            mTextViewTitle = (TextView) itemView.findViewById(R.id.title);
            mPhotoImageView = (ImageView) itemView.findViewById(R.id.avatar);
        }
    }
}
