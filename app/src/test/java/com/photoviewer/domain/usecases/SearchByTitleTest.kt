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

class SearchByTitleTest {

    private val mMockPhotoEntityDataSource: PhotoEntityDataSource = mock()
    private val mMockScheduler: Scheduler = mock()

    private lateinit var mSearchByTitle: SearchByTitle
    private lateinit var mTestSubscriber: TestSubscriber<List<Photo>>

    companion object {
        private val FAKE_PHOTO_TITLE = "Fake Title"
    }

    @Before fun setUp() {
        mTestSubscriber = TestSubscriber.create()
        mSearchByTitle = SearchByTitle(mMockScheduler, mMockScheduler, mMockPhotoEntityDataSource)
    }

    @Test fun `should search by title`() {
        val photos = createPhotosList()

        assumeDataSourceHasSearchedContent(photos)

        mSearchByTitle.setSearchedTitle(FAKE_PHOTO_TITLE)
        mSearchByTitle.call().subscribe(mTestSubscriber)

        assertEquals(photos.size, mTestSubscriber.onNextEvents[0].size)
    }

    private fun assumeDataSourceHasSearchedContent(photos: ArrayList<PhotoEntity>) {
        whenever(mMockPhotoEntityDataSource.searchPhotosByTitle(FAKE_PHOTO_TITLE)).thenReturn(
                just<List<PhotoEntity>>(photos))
    }

    private fun createPhotosList() = ArrayList<PhotoEntity>().apply {
        add(PhotoEntity().apply { title = FAKE_PHOTO_TITLE })
    }
}