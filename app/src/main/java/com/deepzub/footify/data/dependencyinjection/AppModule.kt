package com.deepzub.footify.data.dependencyinjection

import com.deepzub.footify.data.remote.PlayerAPI
import com.deepzub.footify.data.repository.FootballerRepositoryImpl
import com.deepzub.footify.domain.repository.FootballerRepository
import com.deepzub.footify.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePlayerApi(): PlayerAPI {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("x-rapidapi-key", Constants.API_KEY)
                    .build()
                chain.proceed(request)
            }
            .build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PlayerAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideFootballerRepository(api: PlayerAPI): FootballerRepository {
        return FootballerRepositoryImpl(api = api)
    }
}