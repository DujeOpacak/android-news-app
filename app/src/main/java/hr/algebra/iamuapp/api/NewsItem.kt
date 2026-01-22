package hr.algebra.iamuapp.api

import com.google.gson.annotations.SerializedName

data class NewsItem(
    @SerializedName("article_id") val article_id : String,
    @SerializedName("link") val link : String,
    @SerializedName("title") val title : String,
    @SerializedName("description") val description : String?,
    @SerializedName("content") val content : String,
    @SerializedName("keywords") val keywordswords : List<String>,
    @SerializedName("creator") val creator : List<String>,
    @SerializedName("language") val language : String,
    @SerializedName("country") val country : List<String>,
    @SerializedName("category") val category : List<String>,
    @SerializedName("datatype") val datatype : String,
    @SerializedName("pubDate") val pubDate : String,
    @SerializedName("pubDateTZ") val pubDateTZ : String,
    @SerializedName("fetched_at") val fetched_at : String,
    @SerializedName("image_url") val image_url : String,
    @SerializedName("video_url") val video_url : String,
    @SerializedName("source_id") val source_id : String,
    @SerializedName("source_name") val source_name : String,
    @SerializedName("source_priority") val source_priority : Int,
    @SerializedName("source_url") val source_url : String,
    @SerializedName("source_icon") val source_icon : String,
    @SerializedName("sentiment") val sentiment : String,
    @SerializedName("sentiment_stats") val sentiment_stats : String,
    @SerializedName("ai_tag") val ai_tag : String,
    @SerializedName("ai_region") val ai_region : String,
    @SerializedName("ai_org") val ai_org : String,
    @SerializedName("ai_summary") val ai_summary : String,
    @SerializedName("duplicate") val duplicate : Boolean
)
