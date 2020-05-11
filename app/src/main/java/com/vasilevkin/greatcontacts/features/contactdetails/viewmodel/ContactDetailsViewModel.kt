package com.vasilevkin.greatcontacts.features.contactdetails.viewmodel

import androidx.lifecycle.ViewModel
import com.vasilevkin.greatcontacts.models.Person
import com.vasilevkin.greatcontacts.repository.IContactRepository
import javax.inject.Inject


class ContactDetailsViewModel @Inject constructor(
    private val contactRepository: IContactRepository
) : ViewModel() {

    var selectedContact: Person? = null

    // Public methods

    fun onSavePressed(person: Person) {
        contactRepository.addNewContact(person)
    }

    fun onContactEdited(updatedContact: Person) {
        val selectedContact = selectedContact

        if (selectedContact != null)
            contactRepository.updateContact(selectedContact, updatedContact)
    }
}
