package com.photoviewer.data.repository.datasource;

import com.photoviewer.ApplicationTestCase;
import com.photoviewer.data.entity.PhotoStatisticsEntity;
import com.photoviewer.data.preferences.orm.PreferencesDao;
import com.photoviewer.data.repository.datastore.PreferencesPhotoStatisticsEntityStore;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RuntimeEnvironment;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class PreferencesPhotoStatisticsEntityStoreTest extends ApplicationTestCase {

    private PreferencesPhotoStatisticsEntityStore mPreferencesStore;

    @Mock
    private PreferencesDao<PhotoStatisticsEntity> mPreferencesDao;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mPreferencesStore = new PreferencesPhotoStatisticsEntityStore(RuntimeEnvironment.application);
        mPreferencesStore.setStatisticsDao(mPreferencesDao);
    }

    @Test
    public void testReadStatistics() {
        PhotoStatisticsEntity statistics = new PhotoStatisticsEntity();
        given(mPreferencesDao.read()).willReturn(statistics);

        mPreferencesStore.readStatistics().subscribe();

        verify(mPreferencesDao).read();
    }

    @Test
    public void testSaveStatistics() {
        PhotoStatisticsEntity statistics = new PhotoStatisticsEntity();

        mPreferencesStore.updateStatistics(statistics).subscribe();

        verify(mPreferencesDao).save(statistics);
    }
}