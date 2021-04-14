package com.nayan.varun.ui.repo_detail

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.nayan.varun.App
import com.nayan.varun.R
import com.nayan.varun.data.model.Repo
import com.nayan.varun.helper.Constants
import kotlinx.android.synthetic.main.activity_repo_detail.*
import kotlinx.android.synthetic.main.content_repo_detail.*

class ItemDetailActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        App.getInstance().getDependencyComponent().inject(this)

        val repo: Repo? = intent.getParcelableExtra(Constants.REPO_EXTRA)
        setAllViews(repo)
    }

    private fun setAllViews(repo: Repo?) {
        repo?.let {
            collapsingToolbar.title = it.name
            txtRepoDescription.text = it.description

        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
