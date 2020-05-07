package com.vasilevkin.greatcontacts.usecases

import android.content.Context
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vasilevkin.greatcontacts.models.Person
import com.vasilevkin.greatcontacts.repository.datasource.ILocalDataSource


class UseCase3Handler(private val localDataSource: ILocalDataSource) : IUseCase {

    private val mutableLiveData = MutableLiveData<List<Person>>()

    override var context: Context? = null

    // interface IUseCase

    override fun getPersons(): LiveData<List<Person>> {
        localDataSource.context = context

        val handlerThread = HandlerThread("someBackgroundThread")

        handlerThread.start()

        val looper = handlerThread.looper
        val handler = Handler(looper)

        handler.post {
            val list = localDataSource.getContacts()

            val mainHandler = Handler(Looper.getMainLooper())

            mainHandler.post {
                mutableLiveData.postValue(list)
            }
        }

        return mutableLiveData
    }
}