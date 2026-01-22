package hr.algebra.iamuapp.api

import android.content.ContentValues
import android.content.Context
import android.util.Log
import hr.algebra.iamuapp.NEWS_PROVIDER_CONTENT_URI
import hr.algebra.iamuapp.NewsReceiver
import hr.algebra.iamuapp.framework.sendBroadcast
import hr.algebra.iamuapp.handler.download
import hr.algebra.iamuapp.model.Item
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class NewsFetcher(private val context: Context) {

    private val newsApi: NewsApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        newsApi = retrofit.create<NewsApi>()
    }

    fun fetchItems(count: Int = 10) {
        // bg process
        val request = newsApi.fetchItems()

        // kreira novu dretvu za dohvat podataka i vraca se u glavnu dretvu
        request.enqueue(object : Callback<Record> {
            override fun onResponse(
                call: Call<Record?>,
                response: Response<Record?>
            ) {
                response.body()?.record.let {
                    populate(it)
                }
            }

            override fun onFailure(
                call: Call<Record?>,
                t: Throwable
            ) {
                Log.e("ERROR", t.toString(), t)
            }
        })
    }

    private fun populate(newsItems: List<NewsItem>?) {
        // nova dretva za dohvat podataka i spremanje u bazu + spremanje slika
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            newsItems?.forEach {
                val picturePath = download(context, it.image_url)

                val values = ContentValues().apply {
                    put(Item::title.name, it.title)
                    put(Item::description.name, it.description ?: "")
                    put(Item::picturePath.name, picturePath ?: "")
                    put(Item::date.name, it.pubDate)
                    put(Item::read.name, false)
                }

                context.contentResolver.insert(
                    NEWS_PROVIDER_CONTENT_URI,
                    values
                )
            }

            // vraca se u fg dretvu
            context.sendBroadcast<NewsReceiver>()
        }
    }
}