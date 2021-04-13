package com.nayan.varun.di.component

import com.nayan.varun.di.module.AppModule
import com.nayan.varun.di.module.DependencyModule
import com.nayan.varun.ui.repo_detail.ItemDetailActivity
import com.nayan.varun.ui.repo_list.GitRepoListPresenterImpl
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class), (DependencyModule::class)])
interface DependencyComponent {
    fun inject(gitRepoListPresenterImpl: GitRepoListPresenterImpl)
    fun inject(itemDetailActivity: ItemDetailActivity)
}