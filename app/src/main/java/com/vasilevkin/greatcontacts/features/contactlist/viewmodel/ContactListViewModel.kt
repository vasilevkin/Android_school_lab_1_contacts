package com.vasilevkin.greatcontacts.features.contactlist.viewmodel

import android.content.Context
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.vasilevkin.greatcontacts.R
import com.vasilevkin.greatcontacts.delegateadapter.diff.IComparableItem
import com.vasilevkin.greatcontacts.features.contactlist.view.MainActivity
import com.vasilevkin.greatcontacts.models.Person
import com.vasilevkin.greatcontacts.models.localmodels.ContactLocalModel
import com.vasilevkin.greatcontacts.repository.IContactRepository
import com.vasilevkin.greatcontacts.repository.IUseCaseStorage
import com.vasilevkin.greatcontacts.usecases.UseCases
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject


class ContactListViewModel
@Inject constructor(
    private val contactRepository: IContactRepository,
    private val useCaseStorage: IUseCaseStorage
) : ViewModel() {

    private var disposable: Disposable? = null

    var contactList: BehaviorSubject<List<IComparableItem>> = BehaviorSubject.create()
    lateinit var view: Context

    // Public methods

    fun onViewCreated() {
        generateNewData()
        loadContacts()
    }

    fun onRadioButtonSelected(id: Int) {
        useCaseStorage.context = view

        val useCase = when (id) {
            R.id.radio_button_useCase1MainThreadBlocking -> UseCases.UseCase1MainThreadBlocking
            R.id.radio_button_useCase2KotlinThreadBackground -> UseCases.UseCase2KotlinThreadBackground
            R.id.radio_button_useCase3Handler -> UseCases.UseCase3Handler
            R.id.radio_button_useCase4AsyncTask -> UseCases.UseCase4AsyncTask
            R.id.radio_button_useCase5RxJava -> UseCases.UseCase5RxJava
            R.id.radio_button_useCase6ThreadPoolExecutor -> UseCases.UseCase6ThreadPoolExecutor
            R.id.radio_button_useCase7Executor -> UseCases.UseCase7Executor
            R.id.radio_button_useCase8Loader -> UseCases.UseCase8Loader
            R.id.radio_button_useCase9Coroutines -> UseCases.UseCase9Coroutines
            else -> UseCases.UseCase9Coroutines
        }

        useCaseStorage.setSelectedUseCase(useCase)
    }

    // Private methods

    private fun loadContacts() {

        contactRepository.context = view

        val contacts = contactRepository.getAllContacts()
        val listPersonObserver = Observer<List<Person>> { list ->
            val handlerThread = HandlerThread("newThread")
            handlerThread.start()
            val looper = handlerThread.looper
            val handler = Handler(looper)
            handler.post {
                val objects = ArrayList<IComparableItem>(20)
                for (i in list.indices) {
                    val item = ContactLocalModel(
                        view,
                        list[i]
                    )
                    objects.add(item)
                }
                val mainHandler = Handler(Looper.getMainLooper())
                mainHandler.post {
                    contactList.onNext(objects)
                }
            }
        }

        contacts.observe(view as MainActivity, listPersonObserver)

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
