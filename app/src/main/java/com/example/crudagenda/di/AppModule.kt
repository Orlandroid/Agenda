package com.example.crudagenda.di

import android.content.Context
import androidx.room.Room
import com.example.crudagenda.modelo.ContactoDao
import com.example.crudagenda.modelo.ContactoDatabase
import com.example.crudagenda.repositorio.ContactoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): ContactoDatabase =
        Room.databaseBuilder(
            appContext,
            ContactoDatabase::
            class.java,
            "word_database"
        ).build()

    @Singleton
    @Provides
    fun provideYourDao(db: ContactoDatabase) = db.contactoDao()

    @Singleton
    @Provides
    fun provideContactoRepositoio(contactoDao: ContactoDao) = ContactoRepository(contactoDao)


}