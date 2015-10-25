package com.photoviewer.data.entity;

import com.photoviewer.data.preferences.orm.Preference;
import com.photoviewer.data.preferences.orm.PreferenceFile;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(prefix = "m")
@PreferenceFile(name = "photos_opening_statistics")
public class PhotoStatisticsEntity {

    @Setter
    @Getter
    @Preference(name = "last_opened_photo_id")
    private int mLastOpenedPhotoId;

    @Setter
    @Getter
    @Preference(name = "opened_photos_count")
    private int mOpenedPhotosCount;
}
