package com.nayan.varun.di.module

import com.nayan.varun.data.local.RepoDatabase
import com.nayan.varun.data.remote.ApiService
import com.nayan.varun.ui.repo_list.ItemsListRepository
import com.nayan.varun.util.NetworkUtil
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DependencyModule {

    @Provides
    @Singleton
    fun provideRepoListRepository(apiService: ApiService, repoDatabase: RepoDatabase, networkUtil: NetworkUtil): ItemsListRepository {
        return ItemsListRepository(apiService, repoDatabase, networkUtil)
    }

}