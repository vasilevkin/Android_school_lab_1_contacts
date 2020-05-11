package com.vasilevkin.greatcontacts

import android.app.Application
import com.facebook.stetho.Stetho
import com.vasilevkin.greatcontacts.di.AppComponent
import com.vasilevkin.greatcontacts.di.DaggerAppComponent


class MainApplication : Application() {

    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    open fun initializeComponent(): AppComponent {
        return DaggerAppComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()

        // Create an InitializerBuilder
        val initializerBuilder = Stetho.newInitializerBuilder(this).also {

            // Enable Chrome DevTools
            it.enableWebKitInspector(
                Stetho.defaultInspectorModulesProvider(this)
            )

            // Enable command line interface
            it.enableDumpapp(
                Stetho.defaultDumperPluginsProvider(this)
            )
        }

        // Use the InitializerBuilder to generate an Initializer
        val initializer = initializerBuilder.build()

        // Initialize Stetho with the Initializer
        Stetho.initialize(initializer)
    }
}