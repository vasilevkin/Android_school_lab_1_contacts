package com.vasilevkin.greatcontacts.di

import com.vasilevkin.greatcontacts.features.contactlist.viewmodel.ContactListViewModel
import com.vasilevkin.greatcontacts.features.sharedviewmodel.SharedViewModel
import com.vasilevkin.greatcontacts.features.splash.ISplashContract
import com.vasilevkin.greatcontacts.features.splash.presenter.SplashPresenter
import com.vasilevkin.greatcontacts.repository.IContactRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule {

    @Provides
    fun provideContactListViewModel(repo: IContactRepository): ContactListViewModel {
        return ContactListViewModel(repo)
    }

    @Singleton
    @Provides
    fun provideSharedViewModel(): SharedViewModel {
        return SharedViewModel()
    }

    @Provides
    fun provideISplashContractPresenter(): ISplashContract.Presenter {
        return SplashPresenter()
    }

}
