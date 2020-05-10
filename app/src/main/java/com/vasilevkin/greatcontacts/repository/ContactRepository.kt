package com.vasilevkin.greatcontacts.repository

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
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
        val listPersonObserver = Observer<List<Person>> { list ->
            val newList = list.toMutableList()

            newList.add(contact)

            val status = saveAllContacts(newList)
            val textMessage = if (status) {
                "New contact is saved successfully"
            } else {
                "Unknown error when save a new contact"
            }

            Toast.makeText(context, textMessage, Toast.LENGTH_LONG)
                .show()
        }

        contacts.observeForever(listPersonObserver)
    }

    override fun updateContact(contact: Person) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
}