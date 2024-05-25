package bd.emon.notes.di.module

import bd.emon.notes.data.NoteDBRepository
import bd.emon.notes.domain.usecase.CreateNoteUseCase
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
    fun provideCoroutineDispatcher() = Dispatchers.IO
}