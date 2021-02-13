package com.mobile.musicwiki

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mobile.musicwiki.service.api.ApiHelper
import com.mobile.musicwiki.service.model.Genres
import com.mobile.musicwiki.service.repository.MusicWikiRepository
import com.mobile.musicwiki.service.utility.NetworkHelper
import com.mobile.musicwiki.service.utility.Resource
import com.mobile.musicwiki.ui.genres.GenresViewModel
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
class GenresViewModelTest {

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
    private lateinit var dataObserver: Observer<Resource<Genres>>

    private lateinit var viewModel: GenresViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        viewModel = GenresViewModel(context, mainRepository, networkHelper).apply {
            genresLiveData.observeForever(dataObserver)
        }

    }

    @Test
    fun givenServerResponseSuccess_whenFetch_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            doReturn(getDummyData())
                .`when`(apiHelper)
                .getGenres()
            verify(apiHelper).getGenres()
            verify(dataObserver).onChanged(Resource.success(getDummyData()))
            viewModel.genresLiveData.removeObserver(dataObserver)
        }
    }

    @Test
    fun givenServerResponseError_whenFetch_shouldReturnError() {
        testCoroutineRule.runBlockingTest {
            val errorMessage = "Error Message"
            doThrow(RuntimeException(errorMessage))
                .`when`(apiHelper)
                .getGenres()
            viewModel.genresLiveData.observeForever(dataObserver)
            verify(apiHelper).getGenres()
            verify(dataObserver).onChanged(
                Resource.error(
                    RuntimeException(errorMessage).toString(),
                    null
                )
            )
            viewModel.genresLiveData.removeObserver(dataObserver)
        }
    }

    @After
    fun tearDown() {
        // do something if required
    }

    private fun getDummyData(): Genres? = Genres(arrayListOf())
}