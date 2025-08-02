package com.deepzub.footify.data.dependencyinjection

import android.content.Context
import androidx.room.Room
import com.deepzub.footify.data.remote.ClubAPI
import com.deepzub.footify.data.remote.CountryAPI
import com.deepzub.footify.data.remote.PlayerAPI
import com.deepzub.footify.data.repository.CareerPathRepositoryImpl
import com.deepzub.footify.data.repository.GuessClubRepositoryImpl
import com.deepzub.footify.data.repository.WhoAreYaRepositoryImpl
import com.deepzub.footify.data.room.AppDatabase
import com.deepzub.footify.data.room.ClubDao
import com.deepzub.footify.data.room.CountryDao
import com.deepzub.footify.data.room.FootballerDao
import com.deepzub.footify.domain.repository.CareerPathRepository
import com.deepzub.footify.domain.repository.GuessClubRepository
import com.deepzub.footify.domain.repository.WhoAreYaRepository
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
    fun provideRetrofit(): Retrofit {
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
    }

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "footify_db"
        )
            .fallbackToDestructiveMigration(false)
            .build()
    }

    @Provides
    @Singleton
    fun provideWhoAreYaRepository(
        playerAPI: PlayerAPI,
        countryAPI: CountryAPI,
        footballerDao: FootballerDao,
        countryDao: CountryDao
    ): WhoAreYaRepository {
        return WhoAreYaRepositoryImpl(playerAPI,countryAPI,footballerDao,countryDao)
    }

    @Provides
    @Singleton
    fun provideCareerPathRepository(
        playerAPI: PlayerAPI,
        footballerDao: FootballerDao,
    ): CareerPathRepository {
        return CareerPathRepositoryImpl(playerAPI,footballerDao)
    }

    @Provides
    @Singleton
    fun provideGuessClubRepository(
        clubAPI: ClubAPI,
        countryAPI: CountryAPI,
        clubDao: ClubDao,
        countryDao: CountryDao
    ): GuessClubRepository {
        return GuessClubRepositoryImpl(clubAPI,countryAPI,clubDao,countryDao)
    }

    @Provides
    @Singleton
    fun providePlayerApi(retrofit: Retrofit): PlayerAPI {
        return retrofit.create(PlayerAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideCountryApi(retrofit: Retrofit): CountryAPI {
        return retrofit.create(CountryAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideClubApi(retrofit: Retrofit): ClubAPI {
        return retrofit.create(ClubAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideFootballerDao(db: AppDatabase): FootballerDao {
        return db.footballerDao()
    }

    @Provides
    @Singleton
    fun provideCountryDao(db: AppDatabase): CountryDao {
        return db.countryDao()
    }

    @Provides
    @Singleton
    fun provideClubDao(db: AppDatabase): ClubDao {
        return db.clubDao()
    }

}