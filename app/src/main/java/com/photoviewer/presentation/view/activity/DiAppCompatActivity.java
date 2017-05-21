package com.photoviewer.presentation.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import com.photoviewer.presentation.PhotoViewerApplication;
import com.photoviewer.presentation.di.modules.DiActivityModule;

public class DiAppCompatActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    PhotoViewerApplication.getScopedGraph(new DiActivityModule()).inject(this);
    super.onCreate(savedInstanceState);
  }

  @Override public void onContentChanged() {
    super.onContentChanged();
    ButterKnife.bind(this);
  }
}
