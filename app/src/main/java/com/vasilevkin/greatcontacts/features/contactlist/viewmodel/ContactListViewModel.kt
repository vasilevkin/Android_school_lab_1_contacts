package com.vasilevkin.greatcontacts.features.contactlist.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.vasilevkin.greatcontacts.delegateadapter.diff.IComparableItem
import com.vasilevkin.greatcontacts.models.Person
import com.vasilevkin.greatcontacts.models.localmodels.ContactLocalModel
import com.vasilevkin.greatcontacts.repository.IContactRepository
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject


class ContactListViewModel
@Inject constructor(private val contactRepository: IContactRepository)
    : ViewModel() {

    private var disposable: Disposable? = null

    var contactList: BehaviorSubject<List<IComparableItem>> = BehaviorSubject.create()
    lateinit var view: Context




    fun onViewCreated() {
        generateNewData()
        loadContacts()
    }

    // Private methods

    private fun loadContacts() {

    }


    private fun generateNewData() {
        val list = prepareData()

        contactList.onNext(list)
    }

    private fun prepareData(): List<IComparableItem> {
        val objects = ArrayList<IComparableItem>(20)
        for (i in 0 until 20) {
            val item = ContactLocalModel(
                view,
                Person(
                    "FirstName$i",
                    "LastName$i",
                    "($i$i$i) $i$i$i-$i$i-$i$i",
                    "s$i@df.com"
                )
            )
            objects.add(item)
        }
        return objects
    }
}
