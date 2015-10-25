package com.photoviewer.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(prefix = "m")
public class PhotoStatistics {

    @Setter
    @Getter
    private Photo mLastOpenedPhoto;

    @Setter
    @Getter
    private int mOpenedPhotosCount;
}