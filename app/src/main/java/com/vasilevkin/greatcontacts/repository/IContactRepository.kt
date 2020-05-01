package com.vasilevkin.greatcontacts.repository

import androidx.lifecycle.LiveData
import com.vasilevkin.greatcontacts.models.Person


interface IContactRepository {

    fun getAllContacts(): LiveData<List<Person>>

    fun addNewContact(contact: Person)

    fun updateContact(contact: Person)

    fun deleteContact(contact: Person)
}