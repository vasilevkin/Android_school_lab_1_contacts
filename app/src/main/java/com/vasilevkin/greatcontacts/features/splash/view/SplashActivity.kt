package com.vasilevkin.greatcontacts.features.splash.view

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.ProgressBar
import com.vasilevkin.greatcontacts.MainApplication
import com.vasilevkin.greatcontacts.R
import com.vasilevkin.greatcontacts.base.BaseActivity
import com.vasilevkin.greatcontacts.features.contactlist.view.MainActivity
import com.vasilevkin.greatcontacts.features.splash.ISplashContract
import com.vasilevkin.greatcontacts.utils.SPLASH_SCREEN_SHOW_DELAY
import io.reactivex.Completable
import java.util.concurrent.TimeUnit
import javax.inject.Inject


/**
 * Splash Screen with the app icon and name at the center, this is also the launch screen and
 * opens up in fullscreen mode. Once launched it waits for 2 seconds after which it opens the
 * MainActivity
 */
class SplashActivity : BaseActivity<ISplashContract.Presenter>(),
    ISplashContract.View {

    @Inject
    override lateinit var presenter: ISplashContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MainApplication).appComponent.inject(this)

        super.onCreate(savedInstanceState)

        makeFullScreen()

        setContentView(R.layout.activity_splash)

        Completable.complete()
            .delay(SPLASH_SCREEN_SHOW_DELAY, TimeUnit.SECONDS)
            .doOnComplete { startMainActivity() }
            .subscribe()
    }

    override fun finishView() {
        finish()
    }

    override fun showError(msg: String) {
        // show error
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter.onViewDestroyed()
    }

    private fun makeFullScreen() {
        // Remove Title
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        // Make Fullscreen
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // Hide the toolbar
        supportActionBar?.hide()
    }

    private fun startMainActivity() {
        // Start activity
        startActivity(Intent(this, MainActivity::class.java))

        // Animate the loading of new activity
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

        // Close this activity
        presenter.onViewCreated()
    }

    override val progressBar: ProgressBar
        get() = ProgressBar(this)
}
