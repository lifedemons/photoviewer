@file:Suppress("IllegalIdentifier")

package com.photoviewer.domain.usecases

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.photoviewer.data.entity.PhotoEntity
import com.photoviewer.data.repository.PhotoEntityDataSource
import com.photoviewer.domain.Photo
import org.junit.Before
import org.junit.Test
import org.mockito.Matchers.anyInt
import rx.Observable
import rx.Scheduler
import rx.observers.TestSubscriber
import kotlin.test.assertEquals

class GetPhotoDetailsTest {

    private val mPhotoEntityDataSource: PhotoEntityDataSource = mock()
    private val mScheduler: Scheduler = mock()

    private lateinit var mGetPhotoDetails: GetPhotoDetails
    private lateinit var mTestSubscriber: TestSubscriber<Photo>


    companion object {
        private val FAKE_PHOTO_ID = 123
    }

    @Before fun setUp() {
        mTestSubscriber = TestSubscriber.create()
        mGetPhotoDetails = GetPhotoDetails(mScheduler, mScheduler, mPhotoEntityDataSource)
    }

    @Test fun `should get particular photo`() {
        val photoEntity = createPhotoEntity()
        assumeDataSourceHasRequestedPhoto(photoEntity)

        mGetPhotoDetails.setPhotoId(FAKE_PHOTO_ID)
        mGetPhotoDetails.call().subscribe(mTestSubscriber)

        assertEquals(FAKE_PHOTO_ID, mTestSubscriber.onNextEvents[0].id)
    }

    private fun createPhotoEntity() = PhotoEntity().apply {
        id = FAKE_PHOTO_ID
    }

    private fun assumeDataSourceHasRequestedPhoto(photoEntity: PhotoEntity) {
        whenever(mPhotoEntityDataSource.photo(anyInt())).thenReturn(
                Observable.just(photoEntity))
    }
}
