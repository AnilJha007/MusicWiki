package com.mobile.musicwiki

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mobile.musicwiki.service.api.ApiHelper
import com.mobile.musicwiki.service.model.GenreDetails
import com.mobile.musicwiki.service.repository.MusicWikiRepository
import com.mobile.musicwiki.service.utility.NetworkHelper
import com.mobile.musicwiki.service.utility.Resource
import com.mobile.musicwiki.ui.genredetails.GenreDetailsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GenreDetailsViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var networkHelper: NetworkHelper

    @Mock
    private lateinit var mainRepository: MusicWikiRepository

    @Mock
    private lateinit var context: Context

    @Mock
    private lateinit var apiHelper: ApiHelper

    @Mock
    private lateinit var dataObserver: Observer<Resource<GenreDetails>>

    private lateinit var viewModel: GenreDetailsViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        viewModel = GenreDetailsViewModel(context, mainRepository, networkHelper).apply {
            genreDetailsLiveData.observeForever(dataObserver)
        }

    }

    @Test
    fun givenServerResponseSuccess_whenFetch_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            doReturn(getDummyData())
                .`when`(apiHelper)
                .getGenresDetails("Test Genre")
            verify(apiHelper).getGenresDetails("Test Genre")
            verify(dataObserver).onChanged(Resource.success(getDummyData()))
            viewModel.genreDetailsLiveData.removeObserver(dataObserver)
        }
    }

    @Test
    fun givenServerResponseError_whenFetch_shouldReturnError() {
        testCoroutineRule.runBlockingTest {
            val errorMessage = "Error Message"
            doThrow(RuntimeException(errorMessage))
                .`when`(apiHelper)
                .getGenresDetails("test")
            viewModel.genreDetailsLiveData.observeForever(dataObserver)
            verify(apiHelper).getGenresDetails("test")
            verify(dataObserver).onChanged(
                Resource.error(
                    RuntimeException(errorMessage).toString(),
                    null
                )
            )
            viewModel.genreDetailsLiveData.removeObserver(dataObserver)
        }
    }

    @After
    fun tearDown() {
        // do something if required
    }

    private fun getDummyData(): GenreDetails? = GenreDetails("test", 124242312L, 42423221L, null)
}