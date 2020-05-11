package com.vasilevkin.greatcontacts.usecases

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vasilevkin.greatcontacts.models.Person
import com.vasilevkin.greatcontacts.repository.datasource.ILocalDataSource
import kotlin.concurrent.thread


class UseCase2KotlinThreadBackground(private val localDataSource: ILocalDataSource) : IUseCase {

    private val mutableLiveData = MutableLiveData<List<Person>>()

    override var context: Context? = null

    // interface IUseCase

    override fun getPersons(): LiveData<List<Person>> {
        localDataSource.context = context

        thread {
            val list = localDataSource.getContacts()

            mutableLiveData.postValue(list)
        }

        return mutableLiveData
    }

    override fun addNewContactInList(contact: Person, list: List<Person>) {
        localDataSource.context = context

        thread {
            val updatedList = localDataSource.addNewPersonInList(contact, list)

            mutableLiveData.postValue(updatedList)
        }
    }

    override fun updateContactInList(contact: Person, updatedContact: Person, list: List<Person>) {
        localDataSource.context = context

        thread {
            val updatedList = localDataSource.updatePersonInList(contact, updatedContact, list)

            mutableLiveData.postValue(updatedList)
        }
    }
}