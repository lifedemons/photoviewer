package com.photoviewer.presentation.mapper.photostatistics;

import com.photoviewer.domain.PhotoStatistics;
import com.photoviewer.presentation.mapper.photo.PhotoModelToPhoto;
import com.photoviewer.presentation.model.PhotoModel;
import com.photoviewer.presentation.model.PhotoStatisticsModel;
import com.photoviewer.domain.mapper.BaseLayerDataTransformer;

public class PhotoStatisticsModelToPhotoStatistics extends BaseLayerDataTransformer<PhotoStatisticsModel, PhotoStatistics> {

    private PhotoModelToPhoto mPhotoModelToPhotoTransformer;

    public PhotoStatisticsModelToPhotoStatistics() {
        mPhotoModelToPhotoTransformer = new PhotoModelToPhoto();
    }

    @Override
    public PhotoStatistics transform(PhotoStatisticsModel from) {
        PhotoStatistics transformed = new PhotoStatistics();

        PhotoModel lastOpenedPhotoModel = from.getLastOpenedPhotoModel();

        if (lastOpenedPhotoModel != null) {
            transformed.setLastOpenedPhoto(mPhotoModelToPhotoTransformer.transform(lastOpenedPhotoModel));
            transformed.setOpenedPhotosCount(from.getOpenedPhotosCount());
        }

        return transformed;
    }
}
