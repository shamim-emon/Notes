package bd.emon.notes.di.module

import android.content.Context
import bd.emon.notes.data.NoteDBRepository
import bd.emon.notes.data.NoteDBRepositoryImpl
import bd.emon.notes.data.NoteDataSource
import bd.emon.notes.data.NoteDataSourceImpl
import bd.emon.notes.data.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideNoteDatabase(@ApplicationContext appContext: Context): NoteDatabase =
        NoteDatabase(appContext)

    @Provides
    fun provideNoteDataSource(db: NoteDatabase) : NoteDataSource = NoteDataSourceImpl(db)

    @Provides
    fun provideNoteDBRepository(dataSource: NoteDataSource): NoteDBRepository =
        NoteDBRepositoryImpl(dataSource)

}