package com.nayan.varun.ui.repo_list

import com.nayan.varun.data.model.Repo
import com.nayan.varun.data.model.response.RepoResponse

interface GitRepoListView {

    fun showLoader()
    fun hideLoader()
    fun onRepoFetchSuccess(repos: RepoResponse)
    fun onRepoFetchFailure(throwable: Throwable?)

}