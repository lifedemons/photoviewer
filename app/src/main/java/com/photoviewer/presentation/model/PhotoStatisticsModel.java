package com.photoviewer.presentation.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(prefix = "m")
public class PhotoStatisticsModel {

    @Setter
    @Getter
    private PhotoModel mLastOpenedPhotoModel;

    @Setter
    @Getter
    private int mOpenedPhotosCount;

    public void incOpenedPhotosCount() {
        mOpenedPhotosCount++;
    }
}
