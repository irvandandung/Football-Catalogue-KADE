package com.example.myfootballmatchschedule.ModelData

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelLeague(
    @SerializedName("idLeague")
    @Expose
    var leagueId: String? = null,

    @SerializedName("strLeague")
    @Expose
    var leagueName: String? = null,

    @SerializedName("strBadge")
    @Expose
    var leagueImage: String? = null,

    @SerializedName("strDescriptionEN")
    @Expose
    var leagueDescription: String? = null
) : Parcelable