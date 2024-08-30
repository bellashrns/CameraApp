package com.bella.camera_w7.ui.di

import com.bella.camera_w7.domain.usecase.ImageUseCase
import com.bella.camera_w7.domain.usecase.ImageUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {

    @Binds
    @ViewModelScoped
    abstract fun provideImageUseCase(
        useCaseImplementation: ImageUseCaseImpl
    ): ImageUseCase
}