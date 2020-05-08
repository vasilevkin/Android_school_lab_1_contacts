package com.vasilevkin.greatcontacts.usecases

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.loader.app.LoaderManager
import androidx.loader.content.AsyncTaskLoader
import androidx.loader.content.Loader
import com.vasilevkin.greatcontacts.features.contactlist.view.MainActivity
import com.vasilevkin.greatcontacts.models.Person
import com.vasilevkin.greatcontacts.repository.datasource.ILocalDataSource


class UseCase8Loader(private val localDataSource: ILocalDataSource) : IUseCase,
    LoaderManager.LoaderCallbacks<List<Person>> {

    private val mutableLiveData = MutableLiveData<List<Person>>()

    override var context: Context? = null

    // interface IUseCase

    override fun getPersons(): LiveData<List<Person>> {
        localDataSource.context = context

        LoaderManager.getInstance(context as MainActivity)
            .initLoader(
                0,
                null,
                this as LoaderManager.LoaderCallbacks<List<Person>>
            ).forceLoad()

        return mutableLiveData
    }

    // interface LoaderManager.LoaderCallbacks<List<Person>>

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<List<Person>> {
        return FetchData(context, localDataSource)
    }

    override fun onLoadFinished(loader: Loader<List<Person>>, data: List<Person>) {
        mutableLiveData.postValue(data)
    }

    override fun onLoaderReset(loader: Loader<List<Person>>) {}

    // Private

    private class FetchData(context: Context?, dataSource: ILocalDataSource) :
        AsyncTaskLoader<List<Person>>(context ?: MainActivity()) {

        val localDataSource = dataSource

        override fun loadInBackground(): List<Person> {
            return localDataSource.getContacts()
        }

        override fun deliverResult(data: List<Person>?) {
            super.deliverResult(data)
        }
    }
}