package com.deepzub.footify.data.dependencyinjection

import android.content.Context
import androidx.room.Room
import com.deepzub.footify.data.remote.PlayerAPI
import com.deepzub.footify.data.repository.FootballerRepositoryImpl
import com.deepzub.footify.data.room.AppDatabase
import com.deepzub.footify.data.room.FootballerDao
import com.deepzub.footify.domain.repository.FootballerRepository
import com.deepzub.footify.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "footballer_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideFootballerRepository(
        api: PlayerAPI,
        dao: FootballerDao
    ): FootballerRepository {
        return FootballerRepositoryImpl(api, dao)
    }

    @Provides
    @Singleton
    fun provideFootballerDao(db: AppDatabase): FootballerDao {
        return db.footballerDao()
    }
}