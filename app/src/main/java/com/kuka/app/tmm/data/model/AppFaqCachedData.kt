package com.kuka.app.tmm.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "table_favorites")
data class AppFaqCachedData(

    @PrimaryKey(autoGenerate = true) val generatedId: Int = 0,

    @SerializedName("displayOrder")
    val displayOrder: Int? = null
) : Parcelable