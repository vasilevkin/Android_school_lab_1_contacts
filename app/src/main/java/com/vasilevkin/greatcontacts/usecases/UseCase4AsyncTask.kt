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
}

