package com.photoviewer.domain.mapper.photo;

import com.photoviewer.data.entity.PhotoEntity;
import com.photoviewer.domain.Photo;
import com.photoviewer.domain.mapper.BaseLayerDataTransformer;

public class PhotoEntityToPhoto extends BaseLayerDataTransformer<PhotoEntity, Photo> {
    @Override
    public Photo transform(PhotoEntity from) {
        Photo transformed = new Photo();

        transformed.setId(from.getId());
        transformed.setTitle(from.getTitle());
        transformed.setUrl(from.getUrl());
        transformed.setThumbnailUrl(from.getThumbnailUrl());

        return transformed;
    }
}
