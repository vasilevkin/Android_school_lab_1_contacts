package com.vasilevkin.greatcontacts.di

import com.vasilevkin.greatcontacts.repository.ContactRepository
import com.vasilevkin.greatcontacts.repository.IContactRepository
import com.vasilevkin.greatcontacts.repository.datasource.ILocalDataSource
import com.vasilevkin.greatcontacts.repository.datasource.LocalDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RepoModule {

    @Singleton
    @Provides
    fun provideLocalDataSource(): ILocalDataSource {
        return LocalDataSource()
    }

    @Singleton
    @Provides
    fun provideRepository(localDataSource: ILocalDataSource): IContactRepository {
        return ContactRepository(localDataSource)
    }

}
