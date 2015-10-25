package com.photoviewer.domain.mapper.photostatisctics;

import com.photoviewer.data.entity.PhotoStatisticsEntity;
import com.photoviewer.domain.Photo;
import com.photoviewer.domain.PhotoStatistics;
import com.photoviewer.domain.mapper.BaseLayerDataTransformer;

public class PhotoStatisticsToPhotoStatisticsEntity extends BaseLayerDataTransformer<PhotoStatistics, PhotoStatisticsEntity> {
    @Override
    public PhotoStatisticsEntity transform(PhotoStatistics from) {
        PhotoStatisticsEntity transformed = new PhotoStatisticsEntity();

        Photo lastOpenedPhoto = from.getLastOpenedPhoto();
        transformed.setLastOpenedPhotoId(lastOpenedPhoto != null ? lastOpenedPhoto.getId() : 0);
        transformed.setOpenedPhotosCount(from.getOpenedPhotosCount());

        return transformed;
    }
}
