package com.vasilevkin.greatcontacts.usecases

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vasilevkin.greatcontacts.models.Person
import com.vasilevkin.greatcontacts.repository.datasource.ILocalDataSource


class UseCase4AsyncTask(private val localDataSource: ILocalDataSource) : IUseCase {

    private val mutableLiveData = MutableLiveData<List<Person>>()

    override var context: Context? = null

    // interface IUseCase

    override fun getPersons(): LiveData<List<Person>> {
        localDataSource.context = context

        val backgroundTask = BackgroundTask()

        backgroundTask.execute()

        return mutableLiveData
    }

    override fun addNewContactInList(contact: Person, list: List<Person>) {
        localDataSource.context = context

        val addNewContactBackgroundTask = AddNewContactBackgroundTask(contact, list)

        addNewContactBackgroundTask.execute()
    }

    override fun updateContactInList(contact: Person, updatedContact: Person, list: List<Person>) {
        localDataSource.context = context

        val updateContactBackgroundTask = UpdateContactBackgroundTask(contact, updatedContact, list)

        updateContactBackgroundTask.execute()
    }

    // Private

    private inner class BackgroundTask() : AsyncTask<Void, Void, List<Person>>() {

        override fun doInBackground(vararg params: Void?): List<Person>? {
            return localDataSource.getContacts()
        }

        override fun onPostExecute(result: List<Person>?) {
            super.onPostExecute(result)

            mutableLiveData.postValue(result)
        }
    }

    private inner class AddNewContactBackgroundTask(val contact: Person, val list: List<Person>) :
        AsyncTask<Void, Void, List<Person>>() {

        override fun doInBackground(vararg params: Void?): List<Person>? {
            return localDataSource.addNewPersonInList(contact, list)
        }

        override fun onPostExecute(result: List<Person>?) {
            super.onPostExecute(result)

            mutableLiveData.postValue(result)
        }
    }

    private inner class UpdateContactBackgroundTask(
        val contact: Person,
        val updatedContact: Person,
        val list: List<Person>
    ) : AsyncTask<Void, Void, List<Person>>() {

        override fun doInBackground(vararg params: Void?): List<Person>? {
            return localDataSource.updatePersonInList(contact, updatedContact, list)
        }

        override fun onPostExecute(result: List<Person>?) {
            super.onPostExecute(result)

            mutableLiveData.postValue(result)
        }
    }
}
