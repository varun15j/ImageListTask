package com.nayan.varun.ui.repo_list.adapter

import android.app.ActionBar
import android.content.Context
import android.content.res.Resources
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.nayan.varun.R
import com.nayan.varun.data.model.Repo
import com.nayan.varun.util.ImageUtil


class RepoListAdapter(val context: Context, val repoListListener: RepoListListener):
        RecyclerView.Adapter<RepoListAdapter.RepoListVH>() {

    private var repos: ArrayList<Repo> = ArrayList()

    val Int.dp: Int
        get() = (this / Resources.getSystem().displayMetrics.density).toInt()
    val Int.px: Int
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()


    interface RepoListListener {
        fun onRepoClicked(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoListVH {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_repo_item, parent, false)
        return RepoListVH(view)
    }

    override fun getItemCount(): Int {
        return repos.size
    }

    override fun onBindViewHolder(holder: RepoListVH, position: Int) {
        holder.txtRepoName.text = repos[position].name
        holder.txtRepoStars.text = repos[position].description
        var url = repos[position].url.toString()

        if(url != null && url.length > 0 && !url.contentEquals("null")) {
            var imageUtil: ImageUtil = ImageUtil(context)

            imageUtil.loadImage(repos[position].url.toString(), holder.imgBanner)
            val layoutParams = ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200.px)
            holder.imgBanner.setLayoutParams(layoutParams)
            holder.imgBanner.requestLayout()
        }else{
           // holder.imgBanner.getLayoutParams().height = 0
            val layoutParams = ConstraintLayout.LayoutParams(0, 0)
            holder.imgBanner.setLayoutParams(layoutParams)
            holder.imgBanner.requestLayout()
        }


    }

    inner class RepoListVH(itemView: View?) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var txtRepoName: TextView = itemView?.findViewById(R.id.txtRepoName)!!
        var txtRepoStars: TextView = itemView?.findViewById(R.id.txtRepoStars)!!
        var imgBanner: ImageView = itemView?.findViewById(R.id.imageView)!!

        init {
            itemView?.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            repoListListener.onRepoClicked(adapterPosition)
        }
    }

    fun setRepoListAndRefresh(repos: ArrayList<Repo>) {
        this.repos = repos
        notifyDataSetChanged()
    }
}