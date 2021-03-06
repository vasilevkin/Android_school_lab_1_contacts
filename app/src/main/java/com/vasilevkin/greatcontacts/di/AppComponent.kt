package com.vasilevkin.greatcontacts.di

import android.content.Context
import com.vasilevkin.greatcontacts.features.contactdetails.view.ContactDetailsFragment
import com.vasilevkin.greatcontacts.features.contactlist.view.ContactListFragment
import com.vasilevkin.greatcontacts.features.contactlist.view.MainActivity
import com.vasilevkin.greatcontacts.features.splash.view.SplashActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class, RepoModule::class])
interface AppComponent {

    // Factory to create instances of the AppComponent
    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(activity: SplashActivity)
    fun inject(activity: MainActivity)

    fun inject(fragment: ContactListFragment)
    fun inject(fragment: ContactDetailsFragment)
}
