package com.vasilevkin.greatcontacts.usecases

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vasilevkin.greatcontacts.models.Person
import com.vasilevkin.greatcontacts.repository.datasource.ILocalDataSource
import java.util.concurrent.Executor
import java.util.concurrent.Executors


class UseCase7Executor(private val localDataSource: ILocalDataSource) : IUseCase {

    private val mutableLiveData = MutableLiveData<List<Person>>()

    override var context: Context? = null

    // interface IUseCase

    override fun getPersons(): LiveData<List<Person>> {
        localDataSource.context = context

        val executor = BackgroundThreadExecutor()
        val runnable = Runnable {
            val list = localDataSource.getContacts()

            mutableLiveData.postValue(list)
        }

        executor.execute(runnable)

        return mutableLiveData
    }
}


private class BackgroundThreadExecutor : Executor {

    private val backgroundExecutor: Executor

    override fun execute(command: Runnable) {
        backgroundExecutor.execute(command)
    }

    init {
        backgroundExecutor = Executors.newSingleThreadExecutor()
    }
}
