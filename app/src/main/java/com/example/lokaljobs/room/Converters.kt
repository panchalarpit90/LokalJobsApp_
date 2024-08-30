package com.example.lokaljobs.room

import androidx.room.TypeConverter
import com.example.lokaljobs.network.model.ContactPreference
import com.example.lokaljobs.network.model.ContentV3
import com.example.lokaljobs.network.model.Creative
import com.example.lokaljobs.network.model.JobTag
import com.example.lokaljobs.network.model.Location
import com.example.lokaljobs.network.model.PrimaryDetails
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromContactPreference(contactPreference: ContactPreference?): String? {
        return contactPreference?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toContactPreference(contactPreferenceString: String?): ContactPreference? {
        return contactPreferenceString?.let {
            val type = object : TypeToken<ContactPreference>() {}.type
            gson.fromJson(it, type)
        }
    }

    @TypeConverter
    fun fromContentV3(contentV3: ContentV3?): String? {
        return contentV3?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toContentV3(contentV3String: String?): ContentV3? {
        return contentV3String?.let {
            val type = object : TypeToken<ContentV3>() {}.type
            gson.fromJson(it, type)
        }
    }

    @TypeConverter
    fun fromCreative(creative: List<Creative>?): String? {
        return creative?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toCreative(creativeString: String?): List<Creative>? {
        return creativeString?.let {
            val type = object : TypeToken<List<Creative>>() {}.type
            gson.fromJson(it, type)
        }
    }

    @TypeConverter
    fun fromJobTag(jobTag: List<JobTag>?): String? {
        return jobTag?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toJobTag(jobTagString: String?): List<JobTag>? {
        return jobTagString?.let {
            val type = object : TypeToken<List<JobTag>>() {}.type
            gson.fromJson(it, type)
        }
    }

    @TypeConverter
    fun fromLocation(location: List<Location>?): String? {
        return location?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toLocation(locationString: String?): List<Location>? {
        return locationString?.let {
            val type = object : TypeToken<List<Location>>() {}.type
            gson.fromJson(it, type)
        }
    }

    @TypeConverter
    fun fromAny(any: List<Any>?): String? {
        return any?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toAny(anyString: String?): List<Any>? {
        return anyString?.let {
            val type = object : TypeToken<List<Any>>() {}.type
            gson.fromJson(it, type)
        }
    }


    @TypeConverter
    fun fromPrimaryDetails(primaryDetails: PrimaryDetails?): String? {
        return primaryDetails?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toPrimaryDetails(primaryDetailsString: String?): PrimaryDetails? {
        return primaryDetailsString?.let {
            val type = object : TypeToken<PrimaryDetails>() {}.type
            gson.fromJson(it, type)
        }
    }
}


