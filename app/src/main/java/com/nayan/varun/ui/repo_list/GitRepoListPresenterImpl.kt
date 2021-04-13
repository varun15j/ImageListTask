package com.nayan.varun.ui.repo_list

import javax.inject.Inject

class GitRepoListPresenterImpl(var gitRepoListView: GitRepoListView?) : GitRepoListPresenter {

    @Inject
    lateinit var repoListRepository: ItemsListRepository

    override fun fetchRepos() {
        gitRepoListView?.showLoader()

        repoListRepository.fetchRepos({
            gitRepoListView?.hideLoader()
            gitRepoListView?.onRepoFetchSuccess(it)

        },{
            gitRepoListView?.hideLoader()
            gitRepoListView?.onRepoFetchFailure(it)

        })
    }

    override fun destroyView() {
        gitRepoListView = null
    }

}