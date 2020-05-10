package com.vasilevkin.greatcontacts.features.contactdetails.viewmodel

import androidx.lifecycle.ViewModel
import com.vasilevkin.greatcontacts.models.Person
import com.vasilevkin.greatcontacts.repository.IContactRepository
import javax.inject.Inject


class ContactDetailsViewModel @Inject constructor(
    private val contactRepository: IContactRepository
) : ViewModel() {

    // Public methods

    fun onSavePressed(person: Person) {
        contactRepository.addNewContact(person)
    }
}
