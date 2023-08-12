package com.example.crudagenda.di

import android.content.Context
import androidx.room.Room
import com.example.crudagenda.db.db.NoteDao
import com.example.crudagenda.db.db.NoteDatabase
import com.example.crudagenda.repositorio.NotesRepository
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
    fun provideAppDatabase(@ApplicationContext appContext: Context): NoteDatabase =
        Room.databaseBuilder(
            appContext,
            NoteDatabase::
            class.java,
            "word_database"
        ).build()

    @Singleton
    @Provides
    fun provideYourDao(db: NoteDatabase) = db.noteDao()

    @Singleton
    @Provides
    fun provideContactoRepositoio(contactoDao: NoteDao) = NotesRepository(contactoDao)


}