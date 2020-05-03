package com.vasilevkin.greatcontacts

import android.app.Application
import com.vasilevkin.greatcontacts.di.AppComponent
import com.vasilevkin.greatcontacts.di.DaggerAppComponent


class MainApplication : Application() {

    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    open fun initializeComponent(): AppComponent {
        return DaggerAppComponent.factory().create(applicationContext)
    }

}