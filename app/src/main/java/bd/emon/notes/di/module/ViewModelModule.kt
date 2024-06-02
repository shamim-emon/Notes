package bd.emon.notes.di.module

import bd.emon.notes.data.NoteDBRepository
import bd.emon.notes.domain.usecase.CreateNoteUseCase
import bd.emon.notes.domain.usecase.DeleteNoteUseCase
import bd.emon.notes.domain.usecase.EditNoteUseCase
import bd.emon.notes.domain.usecase.GetNoteByIdUseCase
import bd.emon.notes.domain.usecase.GetNotesUseCase
import bd.emon.notes.domain.usecase.SearchNoteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {
    @Provides
    fun provideCreateNoteUseCase(repository: NoteDBRepository) = CreateNoteUseCase(repository)

    @Provides
    fun provideEditNoteUseCase(repository: NoteDBRepository) = EditNoteUseCase(repository)

    @Provides
    fun provideGetNoteByIdUseCase(repository: NoteDBRepository) = GetNoteByIdUseCase(repository)

    @Provides
    fun provideGetNotesUseCase(repository: NoteDBRepository) = GetNotesUseCase(repository)

    @Provides
    fun provideDeleteNoteUseCase(repository: NoteDBRepository) = DeleteNoteUseCase(repository)

    @Provides
    fun provideSearchNoteCase(repository: NoteDBRepository) = SearchNoteUseCase(repository)

    @Provides
    fun provideCoroutineDispatcher() = Dispatchers.IO
}