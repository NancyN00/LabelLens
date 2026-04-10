package com.nancy.labellens.di

import android.app.Application
import androidx.room.Room
import com.nancy.labellens.data.AppDatabase
import com.nancy.labellens.data.ScanHistoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "label_lens_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideScanHistoryDao(db: AppDatabase): ScanHistoryDao {
        return db.scanHistoryDao
    }
}
