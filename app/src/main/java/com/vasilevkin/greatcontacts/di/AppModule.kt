package com.vasilevkin.greatcontacts.di

import com.vasilevkin.greatcontacts.features.contactlist.viewmodel.ContactListViewModel
import com.vasilevkin.greatcontacts.repository.IContactRepository
import dagger.Module
import dagger.Provides


@Module
class AppModule {

    @Provides
    fun provideContactListViewModel(repo: IContactRepository): ContactListViewModel {
        return ContactListViewModel(repo)
    }

}
