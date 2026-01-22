package hr.algebra.iamuapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import hr.algebra.iamuapp.framework.setBooleanPreference
import hr.algebra.iamuapp.framework.startActivity

class NewsReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        context.setBooleanPreference(DATA_IMPORTED)
        context.startActivity<HostActivity>()
    }
}