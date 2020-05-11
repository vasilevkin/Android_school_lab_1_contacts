package com.vasilevkin.greatcontacts.models.localmodels

import android.content.Context
import com.vasilevkin.greatcontacts.delegateadapter.diff.IComparableItem
import com.vasilevkin.greatcontacts.models.Person

class ContactLocalModel(
    val context: Context,
    val contact: Person
) : IComparableItem {
    override fun id(): Any = contact.phone
    override fun content(): Any =
        contact.firstName + contact.lastName + contact.phone + contact.email
}
