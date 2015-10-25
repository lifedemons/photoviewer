package com.photoviewer.presentation.view;

import com.photoviewer.presentation.model.PhotoModel;

/**
 * Interface representing a View in a model view presenter (MVP) pattern.
 * In this case is used as a view representing a photo profile.
 */
public interface PhotoDetailsView extends LoadDataView {
  /**
   * Render a photo in the UI.
   *
   * @param photo The {@link PhotoModel} that will be shown.
   */
  void renderPhoto(PhotoModel photo);
}
