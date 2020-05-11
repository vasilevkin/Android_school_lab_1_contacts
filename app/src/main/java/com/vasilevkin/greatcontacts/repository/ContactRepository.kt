package com.vasilevkin.greatcontacts.repository

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.vasilevkin.greatcontacts.features.contactlist.view.MainActivity
import com.vasilevkin.greatcontacts.models.Person
import com.vasilevkin.greatcontacts.repository.datasource.ILocalDataSource
import com.vasilevkin.greatcontacts.usecases.*
import javax.inject.Inject


class ContactRepository @Inject constructor(
    private val localDataSource: ILocalDataSource,
    private val useCaseStorage: IUseCaseStorage
) : IContactRepository {

    private val useCase1MainThreadBlocking = UseCase1MainThreadBlocking(localDataSource)
    private val useCase2KotlinThreadBackground = UseCase2KotlinThreadBackground(localDataSource)
    private val useCase3Handler = UseCase3Handler(localDataSource)
    private val useCase4AsyncTask = UseCase4AsyncTask(localDataSource)
    private val useCase5RxJava = UseCase5RxJava(localDataSource)
    private val useCase6ThreadPoolExecutor = UseCase6ThreadPoolExecutor(localDataSource)
    private val useCase7Executor = UseCase7Executor(localDataSource)
    private val useCase8Loader = UseCase8Loader(localDataSource)
    private val useCase9Coroutines = UseCase9Coroutines(localDataSource)

    override var context: Context? = null

    // interface IContactRepository

    override fun getAllContacts(): LiveData<List<Person>> {
        setContextForAllUseCases(context)

        useCaseStorage.context = context

        val useCase = useCaseStorage.getSelectedUseCase()

        return when (useCase) {
            UseCases.UseCase1MainThreadBlocking -> useCase1MainThreadBlocking.getPersons()
            UseCases.UseCase2KotlinThreadBackground -> useCase2KotlinThreadBackground.getPersons()
            UseCases.UseCase3Handler -> useCase3Handler.getPersons()
            UseCases.UseCase4AsyncTask -> useCase4AsyncTask.getPersons()
            UseCases.UseCase5RxJava -> useCase5RxJava.getPersons()
            UseCases.UseCase6ThreadPoolExecutor -> useCase6ThreadPoolExecutor.getPersons()
            UseCases.UseCase7Executor -> useCase7Executor.getPersons()
            UseCases.UseCase8Loader -> useCase8Loader.getPersons()
            UseCases.UseCase9Coroutines -> useCase9Coroutines.getPersons()
        }
    }

    override fun addNewContact(contact: Person) {
        val contacts = getAllContacts()

        contacts.observeOnce(context as MainActivity, Observer<List<Person>> { list ->
            if (list != null) {
                val useCase = useCaseStorage.getSelectedUseCase()

                when (useCase) {
                    UseCases.UseCase1MainThreadBlocking -> useCase1MainThreadBlocking.addNewContactInList(contact, list)
                    UseCases.UseCase2KotlinThreadBackground -> useCase2KotlinThreadBackground.addNewContactInList(contact, list)
                    UseCases.UseCase3Handler -> useCase3Handler.addNewContactInList(contact, list)
                    UseCases.UseCase4AsyncTask -> useCase4AsyncTask.addNewContactInList(contact, list)
                    UseCases.UseCase5RxJava -> useCase5RxJava.addNewContactInList(contact, list)
                    UseCases.UseCase6ThreadPoolExecutor -> useCase6ThreadPoolExecutor.addNewContactInList(contact, list)
                    UseCases.UseCase7Executor -> useCase7Executor.addNewContactInList(contact, list)
                    UseCases.UseCase8Loader -> useCase8Loader.addNewContactInList(contact, list)
                    UseCases.UseCase9Coroutines -> useCase9Coroutines.addNewContactInList(contact, list)
                }
            }
        })
    }

    override fun updateContact(contact: Person, updatedContact: Person) {
        val contacts = getAllContacts()

        contacts.observeOnce(context as MainActivity, Observer<List<Person>> {
            if (it != null) {
                val useCase = useCaseStorage.getSelectedUseCase()

                when (useCase) {
                    UseCases.UseCase1MainThreadBlocking -> useCase1MainThreadBlocking.updateContactInList(contact, updatedContact, it)
                    UseCases.UseCase2KotlinThreadBackground -> useCase2KotlinThreadBackground.updateContactInList(contact, updatedContact, it)
                    UseCases.UseCase3Handler -> useCase3Handler.updateContactInList(contact, updatedContact, it)
                    UseCases.UseCase4AsyncTask -> useCase4AsyncTask.updateContactInList(contact, updatedContact, it)
                    UseCases.UseCase5RxJava -> useCase5RxJava.updateContactInList(contact, updatedContact, it)
                    UseCases.UseCase6ThreadPoolExecutor -> useCase6ThreadPoolExecutor.updateContactInList(contact, updatedContact, it)
                    UseCases.UseCase7Executor -> useCase7Executor.updateContactInList(contact, updatedContact, it)
                    UseCases.UseCase8Loader -> useCase8Loader.updateContactInList(contact, updatedContact, it)
                    UseCases.UseCase9Coroutines -> useCase9Coroutines.updateContactInList(contact, updatedContact, it)
                }
            }
        })
    }

    override fun deleteContact(contact: Person) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    // Private methods

    private fun setContextForAllUseCases(context: Context?) {
        useCase1MainThreadBlocking.context = context
        useCase2KotlinThreadBackground.context = context
        useCase3Handler.context = context
        useCase4AsyncTask.context = context
        useCase5RxJava.context = context
        useCase6ThreadPoolExecutor.context = context
        useCase7Executor.context = context
        useCase8Loader.context = context
        useCase9Coroutines.context = context
    }

    private fun saveAllContacts(list: List<Person>): Boolean {
        return useCase1MainThreadBlocking.savePersons(list)
    }

    private fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
        observe(lifecycleOwner, object : Observer<T> {
            override fun onChanged(t: T?) {
                observer.onChanged(t)
                removeObserver(this)
            }
        })
    }
}
