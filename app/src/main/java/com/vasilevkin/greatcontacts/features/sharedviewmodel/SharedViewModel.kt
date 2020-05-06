package com.vasilevkin.greatcontacts.features.sharedviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vasilevkin.greatcontacts.models.Person


class SharedViewModel : ViewModel() {

    private val selectedContact = MutableLiveData<Person>()
    var newContact: Boolean = false

    fun selectContact(contact: Person) {
        selectedContact.value = contact
    }

    fun getSelectedContact(): LiveData<Person> = selectedContact
}