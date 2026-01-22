package hr.algebra.iamuapp

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.appcompat.app.AppCompatActivity
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import hr.algebra.iamuapp.api.NewsWorker
import hr.algebra.iamuapp.databinding.ActivitySplashScreenBinding
import hr.algebra.iamuapp.framework.applyAnimationWithNext
import hr.algebra.iamuapp.framework.applyTypewriterEffect
import hr.algebra.iamuapp.framework.callDelayed
import hr.algebra.iamuapp.framework.getBooleanPreference
import hr.algebra.iamuapp.framework.isOnline
import hr.algebra.iamuapp.framework.startActivity

private const val DELAY = 3000L
const val DATA_IMPORTED = "hr.algebra.iamuapp.data_imported"

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hideNavigationBar()
        redirect()
    }

    private fun hideNavigationBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.apply {
                hide(WindowInsets.Type.navigationBars())
                systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    )
        }
    }

    private fun redirect() {
        if(getBooleanPreference(DATA_IMPORTED)){
            startAnimations()
            callDelayed(DELAY) {startActivity<HostActivity>()}
        } else{
            if(isOnline()){
                startAnimations()
                WorkManager.getInstance(this).apply {
                        enqueueUniqueWork(
                            DATA_IMPORTED,
                            ExistingWorkPolicy.KEEP,
                            OneTimeWorkRequest.from(
                                NewsWorker::class.java
                            )
                        )
                    }
            } else{
                binding.tvSplash.text = getString(R.string.no_internet)
                startAnimations()
                callDelayed(DELAY) { finish() }
            }
        }
    }

    private fun startAnimations() {
        binding.ivSplash.applyAnimationWithNext(R.anim.fade_scale_in,R.anim.logo_pulse)
        binding.tvSplash.applyTypewriterEffect(100)
    }
}