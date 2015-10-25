/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.photoviewer.presentation.view;

import com.photoviewer.presentation.model.PhotoModel;
import com.photoviewer.presentation.model.PhotoStatisticsModel;

import java.util.Collection;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a list of {@link PhotoModel}.
 */
public interface PhotoListView extends LoadDataView {
    /**
     * Render a photo list in the UI.
     *
     * @param photoModelCollection The collection of {@link PhotoModel} that will be shown.
     */
    void renderPhotoList(Collection<PhotoModel> photoModelCollection);

    /**
     * View a {@link PhotoModel} profile/details.
     *
     * @param photoModel The photo that will be shown.
     */
    void viewPhoto(PhotoModel photoModel);


    /**
     * View a {@link PhotoStatisticsModel} profile/details.
     *
     * @param photoStatisticsModel The PhotoStatisticsModel that will be shown.
     */
    void renderPhotoStatisticsModel(PhotoStatisticsModel photoStatisticsModel);

    /**
     * Highlights text entries in list items.
     *
     * @param textToHighlight text to highlight.
     */
    void highlightTextInList(String textToHighlight);
}
