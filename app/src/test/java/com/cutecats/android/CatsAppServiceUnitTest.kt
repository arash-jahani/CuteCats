package com.cutecats.android

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cutecats.android.data.DataRepositoryImpl
import com.cutecats.android.data.model.CatItem
import com.cutecats.android.data.model.CategoryItem
import com.cutecats.android.data.network.CatsApiService
import com.cutecats.android.utils.MockResponseFileReader
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@RunWith(JUnit4::class)
class CatsAppServiceUnitTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val server = MockWebServer()
    private lateinit var repository: DataRepositoryImpl
    private lateinit var mockedResponse: String

    private val gson = GsonBuilder()
        .setLenient()
        .create()

    @Before
    fun init() {

        server.start(8000)

        var BASE_URL = server.url("/").toString()

        val okHttpClient = OkHttpClient
            .Builder()
            .build()
        val service = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build().create(CatsApiService::class.java)

        repository = DataRepositoryImpl(service)
    }

    @Test
    fun testCategoriesApiSuccess() {
        mockedResponse = MockResponseFileReader("categories_api/success.json").content

        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(mockedResponse)
        )

        val categoriesList = runBlocking { repository.getCatsCategories() }

        val itemType = object : TypeToken<List<CategoryItem>>() {}.type
        var mockCategoriesList = gson.fromJson<List<CategoryItem>>(mockedResponse, itemType)


        Assert.assertNotNull(categoriesList)
        Assert.assertTrue(categoriesList.size == mockCategoriesList.size)
    }

    @Test
    fun testCatsApiSuccess() {
        mockedResponse = MockResponseFileReader("cats_api/success.json").content

        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(mockedResponse)
        )

        val catsList = runBlocking { repository.getCatsListByCategoryId("1",1,10) }

        val itemType = object : TypeToken<List<CatItem>>() {}.type
        var mockCatsList = gson.fromJson<List<CatItem>>(mockedResponse, itemType)


        Assert.assertNotNull(catsList)
        Assert.assertTrue(catsList.size == mockCatsList.size)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

}