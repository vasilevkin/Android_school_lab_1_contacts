package com.vasilevkin.greatcontacts.repository

import androidx.lifecycle.LiveData
import com.vasilevkin.greatcontacts.models.Person


class ContactRepository : IContactRepository {
    override fun getAllContacts(): LiveData<List<Person>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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