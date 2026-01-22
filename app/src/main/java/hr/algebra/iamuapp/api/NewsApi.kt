package hr.algebra.iamuapp.api

import retrofit2.Call
import retrofit2.http.GET

const val API_URL = "https://api.jsonbin.io/v3/b/"

interface NewsApi {
    @GET("696d51d9ae596e708fe53ed5")
    fun fetchItems() : Call<Record>
}