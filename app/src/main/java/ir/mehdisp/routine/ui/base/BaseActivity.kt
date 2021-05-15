package ir.mehdisp.routine.ui.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL



    }

    protected fun startActivity(clazz: Class<out Activity>, action: Intent.() -> Unit = {}) {
        startActivity(Intent(this, clazz).apply(action))
    }

}