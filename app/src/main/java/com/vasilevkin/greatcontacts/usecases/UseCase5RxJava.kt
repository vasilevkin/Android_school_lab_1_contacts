package com.vasilevkin.greatcontacts.usecases

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vasilevkin.greatcontacts.models.Person
import com.vasilevkin.greatcontacts.repository.datasource.ILocalDataSource
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers


class UseCase5RxJava(private val localDataSource: ILocalDataSource) : IUseCase {

    private val mutableLiveData = MutableLiveData<List<Person>>()

    override var context: Context? = null

    // interface IUseCase

    override fun getPersons(): LiveData<List<Person>> {
        localDataSource.context = context

        val list = emptyList<Person>()
        val observable =
            Observable.just(list)
                .subscribeOn(Schedulers.computation())
                .map {
                    localDataSource.getContacts()
                }
                .subscribe {
                    mutableLiveData.postValue(it)
                }

        return mutableLiveData
    }
}
