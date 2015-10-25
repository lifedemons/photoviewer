package com.photoviewer.data.entity;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(prefix = "m")
@DatabaseTable(tableName = "photos")
public class PhotoEntity {

    public interface Fields {
        String ID = "id";
        String ALBUM_ID = "albumId";
        String TITLE = "title";
        String URL = "url";
        String THUMBNAIL_URL = "thumbnailUrl";
    }

    @SerializedName(Fields.ID)
    @Getter
    @Setter
    @DatabaseField(id = true, columnName = Fields.ID)
    private int mId;

    @SerializedName(Fields.ALBUM_ID)
    @Getter
    @DatabaseField(columnName = Fields.ALBUM_ID)
    private int mAlbumId;

    @SerializedName(Fields.TITLE)
    @Getter
    @Setter
    @DatabaseField(columnName = Fields.TITLE)
    private String mTitle;

    @SerializedName(Fields.URL)
    @Getter
    @DatabaseField(columnName = Fields.URL)
    private String mUrl;

    @SerializedName(Fields.THUMBNAIL_URL)
    @Getter
    @DatabaseField(columnName = Fields.THUMBNAIL_URL)
    private String mThumbnailUrl;
}