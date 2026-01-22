package hr.algebra.iamuapp.api

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class NewsWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        // bg dretva
        NewsFetcher(context).fetchItems()
        return Result.success()
    }

}