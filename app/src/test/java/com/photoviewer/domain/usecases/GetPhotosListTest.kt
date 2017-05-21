@file:Suppress("IllegalIdentifier")

package com.photoviewer.domain.usecases

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.photoviewer.data.entity.PhotoEntity
import com.photoviewer.data.repository.PhotoEntityDataSource
import com.photoviewer.domain.Photo
import org.junit.Before
import org.junit.Test
import rx.Observable.just
import rx.Scheduler
import rx.observers.TestSubscriber
import java.util.*
import kotlin.test.assertEquals

class GetPhotosListTest {

    private val mPhotoEntityDataSource: PhotoEntityDataSource = mock()
    private val mMockScheduler: Scheduler = mock()

    private lateinit var mGetPhotosList: GetPhotosList
    private lateinit var mTestSubscriber: TestSubscriber<List<Photo>>

    @Before fun setUp() {
        mTestSubscriber = TestSubscriber.create()
        mGetPhotosList = GetPhotosList(mMockScheduler, mMockScheduler, mPhotoEntityDataSource)
    }

    @Test fun `should get particular photo`() {
        val photos = createPhotosList()

        assumeDataSourceHasRequestedPhotos(photos)

        mGetPhotosList.call().subscribe(mTestSubscriber)

        assertEquals(photos.size, mTestSubscriber.onNextEvents[0].size)
    }

    private fun assumeDataSourceHasRequestedPhotos(photos: ArrayList<PhotoEntity>) {
        whenever(mPhotoEntityDataSource.photos()).thenReturn(just<List<PhotoEntity>>(photos))
    }

    private fun createPhotosList() = ArrayList<PhotoEntity>().apply {
        add(PhotoEntity())
    }
}


