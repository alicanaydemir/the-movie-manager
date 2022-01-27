package com.kuka.app.tmm.di

import android.app.Application
import androidx.room.Room
import com.kuka.app.tmm.data.source.local.database.TmmDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application) = Room
            .databaseBuilder(app, TmmDatabase::class.java, "tmm_db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideAppDao(db: TmmDatabase) = db.appDao()
}