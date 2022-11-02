package com.dirzaaulia.allaboutvalorant.di

import com.dirzaaulia.allaboutvalorant.network.Service
import com.dirzaaulia.allaboutvalorant.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
class RepositoryModule {

  @Provides
  @ViewModelScoped
  fun provideRepository(
    service: Service
  ): Repository {
    return Repository(service)
  }
}