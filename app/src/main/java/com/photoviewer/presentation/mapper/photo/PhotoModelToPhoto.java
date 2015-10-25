package com.photoviewer.presentation.mapper.photo;

import com.photoviewer.domain.Photo;
import com.photoviewer.domain.mapper.BaseLayerDataTransformer;
import com.photoviewer.presentation.model.PhotoModel;

public class PhotoModelToPhoto extends BaseLayerDataTransformer<PhotoModel, Photo> {
    @Override
    public Photo transform(PhotoModel from) {
        Photo transformed = new Photo();

        transformed.setId(from.getId());
        transformed.setTitle(from.getTitle());
        transformed.setThumbnailUrl(from.getThumbnailUrl());
        transformed.setUrl(from.getUrl());

        return transformed;
    }
}
