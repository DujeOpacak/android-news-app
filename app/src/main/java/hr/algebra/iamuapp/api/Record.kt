package hr.algebra.iamuapp.api

import com.google.gson.annotations.SerializedName

data class Record(
    @SerializedName("record") val record : List<NewsItem>
)
