package com.photoviewer.presentation.mapper.photostatistics;

import com.photoviewer.domain.Photo;
import com.photoviewer.domain.PhotoStatistics;
import com.photoviewer.presentation.mapper.photo.PhotoToPhotoModel;
import com.photoviewer.presentation.model.PhotoStatisticsModel;
import com.photoviewer.domain.mapper.BaseLayerDataTransformer;

public class PhotoStatisticsToPhotoStatisticsModel extends BaseLayerDataTransformer<PhotoStatistics, PhotoStatisticsModel> {

    private PhotoToPhotoModel mPhotoToPhotoModelTransformer;

    public PhotoStatisticsToPhotoStatisticsModel() {
        mPhotoToPhotoModelTransformer = new PhotoToPhotoModel();
    }

    @Override
    public PhotoStatisticsModel transform(PhotoStatistics from) {
        PhotoStatisticsModel transformed = new PhotoStatisticsModel();

        transformed.setOpenedPhotosCount(from.getOpenedPhotosCount());

        Photo lastOpenedPhoto = from.getLastOpenedPhoto();
        if (lastOpenedPhoto != null) {
            transformed.setLastOpenedPhotoModel(mPhotoToPhotoModelTransformer.transform(lastOpenedPhoto));
        }

        return transformed;
    }
}
