package com.photoviewer.presentation.mapper.photo;

import com.photoviewer.domain.Photo;
import com.photoviewer.presentation.model.PhotoModel;
import com.photoviewer.domain.mapper.BaseLayerDataTransformer;

public class PhotoToPhotoModel extends BaseLayerDataTransformer<Photo, PhotoModel> {
    @Override
    public PhotoModel transform(Photo from) {
        PhotoModel transformed = new PhotoModel();

        transformed.setId(from.getId());
        transformed.setTitle(from.getTitle());
        transformed.setUrl(from.getUrl());
        transformed.setThumbnailUrl(from.getThumbnailUrl());

        return transformed;
    }
}