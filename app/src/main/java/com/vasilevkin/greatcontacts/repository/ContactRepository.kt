package com.vasilevkin.greatcontacts.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vasilevkin.greatcontacts.models.Person
import com.vasilevkin.greatcontacts.repository.datasource.ILocalDataSource
import javax.inject.Inject


class ContactRepository @Inject constructor(
    private val localDataSource: ILocalDataSource
) : IContactRepository {

    override var context: Context? = null

    private val mutableLiveData: MutableLiveData<List<Person>> = MutableLiveData<List<Person>>()

    override fun getAllContacts(): LiveData<List<Person>> {
//    override fun getAllContacts(): List<Person> {

        localDataSource.context = context

        val list = localDataSource.getContacts()
        mutableLiveData.postValue(list)
        return mutableLiveData
    }

    override fun addNewContact(contact: Person) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateContact(contact: Person) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteContact(contact: Person) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}