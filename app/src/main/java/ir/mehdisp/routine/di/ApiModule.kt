package ir.mehdisp.routine.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.mehdisp.routine.api.ParsijooService
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideWeatherService(retrofit: Retrofit): ParsijooService {
        return retrofit.create(ParsijooService::class.java)
    }

}