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

    override fun addNewContactInList(contact: Person, list: List<Person>) {
        localDataSource.context = context

        val handlerThread = HandlerThread("addNewContactBackgroundThread")

        handlerThread.start()

        val looper = handlerThread.looper
        val handler = Handler(looper)

        handler.post {
            val updatedList = localDataSource.addNewPersonInList(contact, list)

            val mainHandler = Handler(Looper.getMainLooper())

            mainHandler.post {
                mutableLiveData.postValue(updatedList)
            }
        }
    }

    override fun updateContactInList(contact: Person, updatedContact: Person, list: List<Person>) {
        localDataSource.context = context

        val handlerThread = HandlerThread("updateContactBackgroundThread")

        handlerThread.start()

        val looper = handlerThread.looper
        val handler = Handler(looper)

        handler.post {
            val updatedList = localDataSource.updatePersonInList(contact, updatedContact, list)

            val mainHandler = Handler(Looper.getMainLooper())

            mainHandler.post {
                mutableLiveData.postValue(updatedList)
            }
        }
    }
}