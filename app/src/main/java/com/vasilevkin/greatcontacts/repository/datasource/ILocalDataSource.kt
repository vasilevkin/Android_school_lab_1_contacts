package com.vasilevkin.greatcontacts.repository.datasource

import android.content.Context
import com.vasilevkin.greatcontacts.models.Person


interface ILocalDataSource {

    var context: Context?

    fun getContacts(): List<Person>

    fun saveContacts(contacts: List<Person>): Boolean

}