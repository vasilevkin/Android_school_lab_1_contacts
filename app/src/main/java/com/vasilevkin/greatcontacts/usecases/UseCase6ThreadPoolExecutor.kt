package com.vasilevkin.greatcontacts.usecases

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vasilevkin.greatcontacts.models.Person
import com.vasilevkin.greatcontacts.repository.datasource.ILocalDataSource
import java.util.concurrent.Executors


class UseCase6ThreadPoolExecutor(private val localDataSource: ILocalDataSource) : IUseCase {

    private val mutableLiveData = MutableLiveData<List<Person>>()

    override var context: Context? = null

    // interface IUseCase

    override fun getPersons(): LiveData<List<Person>> {
        localDataSource.context = context

        val executor = Executors.newFixedThreadPool(5)
        val worker = Runnable {
            val list = localDataSource.getContacts()

            mutableLiveData.postValue(list)
        }
        
        executor.execute(worker)
        executor.shutdown()

        return mutableLiveData
    }
}
