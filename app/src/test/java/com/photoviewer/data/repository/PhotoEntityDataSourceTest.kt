@file:Suppress("IllegalIdentifier")

package com.photoviewer.data.repository

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.photoviewer.data.entity.PhotoEntity
import com.photoviewer.data.repository.datastore.DatabasePhotoEntityStore
import com.photoviewer.data.repository.datastore.ServerPhotoEntityStore
import com.photoviewer.domain.usecases.SimpleSubscriber
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyZeroInteractions
import rx.Observable.just
import java.util.*

class PhotoEntityDataSourceTest {

    private val mDatabasePhotoEntityStore: DatabasePhotoEntityStore = mock()
    private val mServerPhotoEntityStore: ServerPhotoEntityStore = mock()

    private lateinit var mPhotoEntityDataSource: PhotoEntityDataSource

    companion object {
        private val FAKE_PHOTO_ID = 31
    }

    @Before fun setUp() {
        mPhotoEntityDataSource = PhotoEntityDataSource(mDatabasePhotoEntityStore, mServerPhotoEntityStore)
    }

    @Test fun `should query database on getting photos`() {
        val photosList = createPhotosList()

        assumeDatabaseIsEmpty()
        assumeServerHasRequestedContent(photosList)

        mPhotoEntityDataSource.photos().subscribe(SimpleSubscriber<List<PhotoEntity>>())

        verify(mDatabasePhotoEntityStore).queryForAll()
    }

    @Test fun `should query server on getting photos if database does not have them`() {
        val photosList = createPhotosList()

        assumeDatabaseIsEmpty()
        assumeServerHasRequestedContent(photosList)

        mPhotoEntityDataSource.photos().subscribe(SimpleSubscriber<List<PhotoEntity>>())

        verify(mServerPhotoEntityStore).photoEntityList()
    }

    @Test fun `should save retrieved photos from server on getting photos`() {
        val photosList = createPhotosList()

        assumeDatabaseIsEmpty()
        assumeServerHasRequestedContent(photosList)

        mPhotoEntityDataSource.photos().subscribe(SimpleSubscriber<List<PhotoEntity>>())

        verify(mDatabasePhotoEntityStore).saveAll(photosList)
    }

    private fun createPhotosList() = ArrayList<PhotoEntity>().apply {
        add(PhotoEntity())
    }

    private fun assumeDatabaseIsEmpty() {
        whenever(mDatabasePhotoEntityStore.queryForAll()).thenReturn(
                just<List<PhotoEntity>>(ArrayList<PhotoEntity>()))
    }

    private fun assumeServerHasRequestedContent(photosList: List<PhotoEntity>) {
        whenever(mServerPhotoEntityStore.photoEntityList()).thenReturn(just<List<PhotoEntity>>(photosList))
    }

    @Test fun `should not query server on getting photos if database has them`() {
        val photoEntities = createPhotosList()
        assumeDatabaseHasRequestesContent(photoEntities)
        assumeServerHasRequestedContent(photoEntities)

        mPhotoEntityDataSource.photos().subscribe()

        verify(mDatabasePhotoEntityStore).queryForAll()
        verifyZeroInteractions(mServerPhotoEntityStore)
    }

    private fun assumeDatabaseHasRequestesContent(photoEntities: List<PhotoEntity>) {
        whenever(mDatabasePhotoEntityStore.queryForAll()).thenReturn(just<List<PhotoEntity>>(photoEntities))
    }

    @Test fun `should query only database on getting particular photo`() {
        val photoEntity = PhotoEntity()
        whenever(mDatabasePhotoEntityStore.queryForId(FAKE_PHOTO_ID)).thenReturn(
                just(photoEntity))

        mPhotoEntityDataSource.photo(FAKE_PHOTO_ID).subscribe()

        verify(mDatabasePhotoEntityStore).queryForId(FAKE_PHOTO_ID)
    }
}
