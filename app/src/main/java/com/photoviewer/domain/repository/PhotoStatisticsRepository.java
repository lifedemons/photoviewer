package com.photoviewer.domain.repository;

import com.photoviewer.data.entity.PhotoStatisticsEntity;

import rx.Observable;

public interface PhotoStatisticsRepository {
    /**
     * Get an {@link rx.Observable} which will emit {@link PhotoStatisticsEntity}.
     */
    Observable<PhotoStatisticsEntity> readStatistics();

    /**
     * Get an {@link rx.Observable} which will emit {@link PhotoStatisticsEntity}.
     */
    Observable<PhotoStatisticsEntity> updateStatistics(PhotoStatisticsEntity photoStatisticsEntity);
}
