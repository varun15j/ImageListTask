package com.nayan.varun.ui.repo_list

import com.nayan.varun.data.local.RepoDatabase
import com.nayan.varun.data.model.Repo
import com.nayan.varun.data.model.response.RepoResponse
import com.nayan.varun.data.remote.ApiService
import com.nayan.varun.util.NetworkUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch


class ItemsListRepository(var apiService: ApiService, var repoDatabase: RepoDatabase, var networkUtil: NetworkUtil) {

    fun fetchRepos(successHandler: (RepoResponse) -> Unit, failureHandler: (Throwable?) -> Unit) {
        if (networkUtil.isOnline()) {
            fetchOnline(successHandler,failureHandler)
        } else {
            fetchLocally(successHandler)
        }
    }

    private fun fetchOnline(successHandler: (RepoResponse) -> Unit, failureHandler: (Throwable?) -> Unit) {
        apiService.fetchRepos()
                .enqueue(object : Callback<RepoResponse> {
                    override fun onFailure(call: Call<RepoResponse>?, t: Throwable?) {
                        failureHandler(t)
                    }

                    override fun onResponse(call: Call<RepoResponse>?, response: Response<RepoResponse>?) {
                        response?.let {
                            val repos = it.body()!!.data
                            val heading = it.body()!!
//                            val sharedPref = ?.getSharedPreferences(
//                                    getString(R.string.preference_file_key), Context.MODE_PRIVATE)
                            saveIntoDatabase(repos)
                            successHandler(heading)
                        }
                    }

                })
    }

    private fun saveIntoDatabase(repos: ArrayList<Repo>) {
        launch {
            if (!repos.isEmpty()) {
                repoDatabase.clearAllTables() //Clear the table first
                repoDatabase.repoDao().insertRepos(repos)
            }
        }
    }

    private fun fetchLocally(successHandler: (RepoResponse) -> Unit) {
        launch(UI) {
            val repos: ArrayList<Repo> = async(CommonPool) {
                repoDatabase.repoDao().getRepos() as ArrayList<Repo>
            }.await()
           var repoResponse: RepoResponse = RepoResponse()
            repoResponse.data = repos
            repoResponse.title = "About Canada"
            successHandler(repoResponse)
        }
    }
}