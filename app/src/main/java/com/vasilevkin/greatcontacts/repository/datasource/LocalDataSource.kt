package com.vasilevkin.greatcontacts.repository.datasource

import androidx.lifecycle.LiveData
import com.vasilevkin.greatcontacts.models.Person


class LocalDataSource : ILocalDataSource {
    override fun getContacts(): LiveData<List<Person>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}