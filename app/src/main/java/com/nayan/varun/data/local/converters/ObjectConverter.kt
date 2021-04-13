package com.nayan.varun.data.local.converters

import android.arch.persistence.room.TypeConverter
import com.nayan.varun.data.model.Owner
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class ObjectConverter {

    @TypeConverter
    fun toOwnerObject(ownerString: String): Owner {
        val objectType = object : TypeToken<Owner>() {}.type
        return Gson().fromJson(ownerString, objectType)
    }

    @TypeConverter
    fun fromOwnerObject(ownerObject: Owner): String {
        val gson = Gson()
        return gson.toJson (ownerObject)
    }
}