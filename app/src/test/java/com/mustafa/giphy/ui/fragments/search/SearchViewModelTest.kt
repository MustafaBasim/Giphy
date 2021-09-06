package com.mustafa.giphy.ui.fragments.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mustafa.giphy.model.data_models.Results
import com.mustafa.giphy.model.data_models.responses.Data
import com.mustafa.giphy.model.data_models.responses.DataId
import com.mustafa.giphy.model.data_models.responses.DataResponse
import com.mustafa.giphy.model.repository.DatabaseRepository
import com.mustafa.giphy.model.repository.MainRepository
import com.mustafa.giphy.utilities.MainCoroutineRule
import com.mustafa.giphy.utilities.getOrAwaitFirstSetValue
import com.mustafa.giphy.utilities.getOrAwaitSecondSetValue
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by: Mustafa Basim
 * E-mail: 96.mustafa.basim@gmail.com
 * Project name: Giphy
 * Package: com.mustafa.giphy.ui.fragments.search
 * Date: 8/24/2021
 */

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class SearchViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // Set the main coroutines dispatcher for unit testing.
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var searchViewModel: SearchViewModel

    @MockK
    lateinit var mainRepository: MainRepository

    @MockK
    lateinit var databaseRepository: DatabaseRepository

    @Before
    fun setupViewModel() {
        MockKAnnotations.init(this, relaxed = true)
        searchViewModel = SearchViewModel()
        searchViewModel.mainRepository = mainRepository
        searchViewModel.databaseRepository = databaseRepository
    }


    @Test
    fun `get Gifs LiveData Result Success`() {
        mainCoroutineRule.runBlockingTest {
            val result = Results.Success(DataResponse())

            coEvery { mainRepository.search(query = "", offset = 0) } returns result
            coEvery { mainRepository.getTrending(offset = 0) } returns result

            searchViewModel.getGifs()

            assertEquals(result, searchViewModel.gifsData.getOrAwaitSecondSetValue())
        }
    }


    @Test
    fun `set search query and LiveData Result Success`() {
        mainCoroutineRule.runBlockingTest {
            val result = Results.Success(DataResponse())

            coEvery { mainRepository.search(query = "", offset = 0) } returns result
            coEvery { mainRepository.getTrending(offset = 0) } returns result

            searchViewModel.setSearchQuery("")

            assertEquals(result, searchViewModel.gifsData.getOrAwaitSecondSetValue())
        }
    }

    @Test
    fun `get Gifs LiveData Result Loading`() {
        mainCoroutineRule.runBlockingTest {
            val result = Results.Loading

            coEvery { mainRepository.search(query = "", offset = 0) } returns result
            coEvery { mainRepository.getTrending(offset = 0) } returns result

            searchViewModel.getGifs()

            assertEquals(result, searchViewModel.gifsData.getOrAwaitFirstSetValue())
        }
    }

    @Test
    fun `get Gifs LiveData Result Success with favourite gifs marked true`() {
        mainCoroutineRule.runBlockingTest {
            val remoteResult = Results.Success(DataResponse(data = ArrayList<Data>().apply {
                add(Data(id = "1"))
                add(Data(id = "2"))
                add(Data(id = "3"))
            }))

            val dbResult = listOf(DataId("1"), DataId("3")) // 1 and 3 are favourite gifs

            val expected = Results.Success(DataResponse(data = ArrayList<Data>().apply {
                add(Data(id = "1", isFavourite = true))
                add(Data(id = "2", isFavourite = false))
                add(Data(id = "3", isFavourite = true))
            }))

            coEvery { mainRepository.search(query = "", offset = 0) } returns remoteResult
            coEvery { mainRepository.getTrending(offset = 0) } returns remoteResult
            coEvery { databaseRepository.getFavouriteGifsIds() } returns dbResult

            searchViewModel.getGifs()

            assertEquals(expected, searchViewModel.gifsData.getOrAwaitSecondSetValue())
        }
    }


}