package com.vasilevkin.greatcontacts.repository.datasource

import androidx.lifecycle.LiveData
import com.vasilevkin.greatcontacts.models.Person


interface ILocalDataSource {

    fun getContacts(): LiveData<List<Person>>

}