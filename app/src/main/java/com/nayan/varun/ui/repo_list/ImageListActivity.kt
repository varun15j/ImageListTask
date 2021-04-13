package com.nayan.varun.ui.repo_list

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.nayan.varun.R
import com.nayan.varun.data.model.Repo
import com.nayan.varun.ui.repo_detail.ItemDetailActivity
import com.nayan.varun.ui.repo_list.adapter.RepoListAdapter
import com.nayan.varun.helper.Constants
import com.nayan.varun.helper.showLongToast
import com.emmanuelkehinde.shutdown.Shutdown
import com.nayan.varun.App
import com.nayan.varun.data.model.response.RepoResponse
import kotlinx.android.synthetic.main.activity_repo_list.*

class ImageListActivity : AppCompatActivity(), GitRepoListView, SwipeRefreshLayout.OnRefreshListener, RepoListAdapter.RepoListListener {

    private lateinit var gitRepoListPresenter: GitRepoListPresenter
    private lateinit var repoListAdapter: RepoListAdapter
    private var repos: ArrayList<Repo> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_list)
        supportActionBar?.title = getString(R.string.repoListTitle)
        swipeLayout.setColorSchemeColors(resources.getColor(R.color.colorAccent))
        swipeLayout.setOnRefreshListener(this)

        gitRepoListPresenter = GitRepoListPresenterImpl(this).apply {
            App.getInstance().getDependencyComponent().inject(this)
        }

        setUpRecyclerView()
        savedInstanceState?.getParcelableArrayList<Repo>(Constants.REPOS_KEY)?.let {
            this.repos = it
            displayRepos(it)
        } ?: gitRepoListPresenter.fetchRepos()
    }

    private fun setUpRecyclerView() {
        repoListAdapter = RepoListAdapter(this,this)
        recyclerRepoList.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        recyclerRepoList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerRepoList.adapter = repoListAdapter
    }

    private fun displayRepos(repos: ArrayList<Repo>) {
        repoListAdapter.setRepoListAndRefresh(repos)
    }

    override fun onRepoFetchSuccess(repoResponse: RepoResponse) {
        if (repoResponse.data.isEmpty()) {
            if (this.repos.isEmpty()) {
                showNoRepoLayout()
            }
        } else {
            this.repos = repoResponse.data
            displayRepos(this.repos)
            supportActionBar?.title = repoResponse.title
        }
    }

    override fun onRepoFetchFailure(throwable: Throwable?) {
        showLongToast(throwable?.localizedMessage!!)
        if (this.repos.isEmpty()) {
            showNoRepoLayout()
        }
    }

    override fun onRepoClicked(position: Int) {
        val intent = Intent(this, ItemDetailActivity::class.java).apply {
            putExtra(Constants.REPO_EXTRA,repos[position])
        }
        startActivity(intent)
    }

    private fun showNoRepoLayout() {
        layoutNoRepo.visibility = View.VISIBLE
        recyclerRepoList.visibility = View.GONE
    }

    private fun hideNoRepoLayout() {
        if (layoutNoRepo.visibility == View.VISIBLE) {
            layoutNoRepo.visibility = View.GONE
            recyclerRepoList.visibility = View.VISIBLE
        }
    }

    override fun showLoader() {
        swipeLayout.isRefreshing = true
    }

    override fun hideLoader() {
        swipeLayout.isRefreshing = false
    }

    override fun onRefresh() {
        hideNoRepoLayout()
        gitRepoListPresenter.fetchRepos()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelableArrayList(Constants.REPOS_KEY,repos)
    }

    override fun onDestroy() {
        super.onDestroy()
        gitRepoListPresenter.destroyView()
    }

    override fun onBackPressed() {
        Shutdown.now(this)
    }
}
