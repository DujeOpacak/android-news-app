package hr.algebra.iamuapp.framework

import android.annotation.SuppressLint
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.core.content.edit
import androidx.core.content.getSystemService
import androidx.preference.PreferenceManager
import hr.algebra.iamuapp.NEWS_PROVIDER_CONTENT_URI
import hr.algebra.iamuapp.model.Item

fun View.applyAnimationWithNext(animationId: Int, nextAnimationId: Int) {
    val firstAnim = AnimationUtils.loadAnimation(context, animationId)
    val nextAnim = AnimationUtils.loadAnimation(context, nextAnimationId)

    firstAnim.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation?) {}
        override fun onAnimationRepeat(animation: Animation?) {}
        override fun onAnimationEnd(animation: Animation?) {
            startAnimation(nextAnim)
        }
    })

    startAnimation(firstAnim)
}

fun TextView.applyTypewriterEffect(delayMs: Long = 100) {
    val text = this.text.toString()
    this.text = ""
    val builder = StringBuilder()
    var index = 0

    val handler = Handler(Looper.getMainLooper())
    val runnable = object : Runnable {
        override fun run() {
            if (index < text.length) {
                builder.append(text[index].toString())
                this@applyTypewriterEffect.text = builder.toString()
                index++
                handler.postDelayed(this, delayMs)
            }
        }
    }
    handler.postDelayed(runnable, delayMs)
}

inline fun <reified T : Activity> Context.startActivity() {
    startActivity(Intent(this, T::class.java).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    })
}

inline fun <reified T : BroadcastReceiver> Context.sendBroadcast() {
    sendBroadcast(
        Intent(this, T::class.java)
    )
}

fun callDelayed(delay: Long, work: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed(
        work,
        delay
    )
}

fun Context.getBooleanPreference(key: String) =
    PreferenceManager.getDefaultSharedPreferences(this)
        .getBoolean(key, false)

fun Context.setBooleanPreference(key: String, value: Boolean = true) =
    PreferenceManager.getDefaultSharedPreferences(this)
        .edit {
            putBoolean(key, value)
        }

fun Context.isOnline(): Boolean {
    val connectivityManager = getSystemService<ConnectivityManager>()
    connectivityManager?.activeNetwork?.let { network ->
        connectivityManager.getNetworkCapabilities(network)?.let { capabilities ->
            return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                    || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        }
    }
    return false
}

@SuppressLint("Range")
fun Context.fetchItems(): MutableList<Item> {
    val items = mutableListOf<Item>()

    val rs = contentResolver.query(
        NEWS_PROVIDER_CONTENT_URI,
        null,
        null,
        null,
        null
    ).use { rs ->
        while (rs?.moveToNext() == true) {
            items.add(
                Item(
                    rs.getLong(rs.getColumnIndex(Item::_id.name)),
                    rs.getString(rs.getColumnIndex(Item::title.name)),
                    rs.getString(rs.getColumnIndex(Item::description.name)),
                    rs.getString(rs.getColumnIndex(Item::picturePath.name)),
                    rs.getString(rs.getColumnIndex(Item::date.name)),
                    rs.getInt(rs.getColumnIndex(Item::read.name)) == 1
                )
            )
        }
    }

    return items
}

@SuppressLint("Range")
fun Context.fetchItem(id: Long): Item? {
    val uri = ContentUris.withAppendedId(NEWS_PROVIDER_CONTENT_URI, id)

    contentResolver.query(
        uri,
        null,
        null,
        null,
        null
    )?.use { rs ->
        if (rs?.moveToFirst() == true) {
            return Item(
                rs.getLong(rs.getColumnIndex(Item::_id.name)),
                rs.getString(rs.getColumnIndex(Item::title.name)),
                rs.getString(rs.getColumnIndex(Item::description.name)),
                rs.getString(rs.getColumnIndex(Item::picturePath.name)),
                rs.getString(rs.getColumnIndex(Item::date.name)),
                rs.getInt(rs.getColumnIndex(Item::read.name)) == 1
            )
        }
    }

    return null
}