package com.example.myfootballmatchschedule.ModelData

import com.google.gson.annotations.SerializedName

data class PlayersResponse(
    @SerializedName("player") val player: List<Players>
)