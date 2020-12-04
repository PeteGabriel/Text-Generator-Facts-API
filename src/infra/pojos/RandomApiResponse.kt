package com.mkt.task.infra.pojos

import com.google.gson.annotations.SerializedName

data class RandomApiResponse(val amount: Int,
                             val number: String,
                             @SerializedName("number_max")
                             val numberMax: String,
                             val format: String,
                             val type: String, val time: String,
                             @SerializedName("text_out") val textOut: String)