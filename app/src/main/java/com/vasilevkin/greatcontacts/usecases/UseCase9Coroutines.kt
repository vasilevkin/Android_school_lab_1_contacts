package com.vasilevkin.greatcontacts.usecases

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vasilevkin.greatcontacts.models.Person
import com.vasilevkin.greatcontacts.repository.datasource.ILocalDataSource
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async


class UseCase9Coroutines(private val localDataSource: ILocalDataSource) : IUseCase {

    private val mutableLiveData = MutableLiveData<List<Person>>()

    override var context: Context? = null

    // interface IUseCase

    override fun getPersons(): LiveData<List<Person>> {
        localDataSource.context = context

        val deferredResult: Deferred<List<Person>> = GlobalScope.async {
            localDataSource.getContacts()
        }

        GlobalScope.async {
            mutableLiveData.postValue(deferredResult.await())
        }

        return mutableLiveData
    }
}
