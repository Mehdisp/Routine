package ir.mehdisp.routine.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import ir.mehdisp.routine.databinding.ActivitySplashBinding
import ir.mehdisp.routine.ui.base.BaseActivity
import ir.mehdisp.routine.ui.main.MainActivity

class SplashActivity : BaseActivity() {

    private val mHandler = Handler(Looper.getMainLooper())
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.appName.animate()
            .alpha(1f)
            .setDuration(500)
            .start()

        mHandler.postDelayed({
            startActivity(MainActivity::class.java)
            finish()
        }, 1000)

    }

    override fun finish() {
        overridePendingTransition(0, android.R.anim.fade_out)
        super.finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler.removeCallbacksAndMessages(null)
    }

}