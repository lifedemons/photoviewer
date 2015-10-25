package com.photoviewer.domain.repository;

import com.photoviewer.data.entity.PhotoEntity;
import com.photoviewer.domain.Photo;

import java.util.List;

import rx.Observable;

/**
 * Interface that represents a Repository for getting {@link Photo} related data.
 */
public interface PhotoRepository {
    /**
     * Get an {@link rx.Observable} which will emit a List of {@link PhotoEntity}.
     */
    Observable<List<PhotoEntity>> photos();

    /**
     * Get an {@link rx.Observable} which will emit a List of {@link PhotoEntity},
     * whom titles match searched title.
     * @param title The photos' Title used to retrieve photo data.
     */
    Observable<List<PhotoEntity>> searchPhotosByTitle(String title);

    /**
     * Get an {@link rx.Observable} which will emit a {@link PhotoEntity}.
     *
     * @param photoId The photo id used to retrieve photo data.
     */
    Observable<PhotoEntity> photo(final int photoId);
}
