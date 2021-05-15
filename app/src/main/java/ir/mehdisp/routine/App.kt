package ir.mehdisp.routine

import android.app.Application
import androidx.core.content.res.ResourcesCompat
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.SvgDecoder
import dagger.hilt.android.HiltAndroidApp
import es.dmoral.toasty.Toasty
import timber.log.Timber

@HiltAndroidApp
class App: Application(), ImageLoaderFactory {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        Toasty.Config.getInstance()
            .setToastTypeface(ResourcesCompat.getFont(this, R.font.dana)!!)
            .apply()

    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .crossfade(true)
            .componentRegistry { add(SvgDecoder(this@App)) }
            .build()
    }

}