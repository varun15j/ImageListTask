package com.nayan.varun.data.model.response

import com.nayan.varun.data.model.Repo
import com.google.gson.annotations.SerializedName

class RepoResponse {

    @SerializedName("title")
    lateinit var title:String


    @SerializedName("rows")
    lateinit var data: ArrayList<Repo>


}