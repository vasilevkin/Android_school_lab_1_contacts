package com.vasilevkin.greatcontacts.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.vasilevkin.greatcontacts.models.Person
import com.vasilevkin.greatcontacts.repository.datasource.ILocalDataSource
import com.vasilevkin.greatcontacts.usecases.*
import javax.inject.Inject


class ContactRepository @Inject constructor(
    private val localDataSource: ILocalDataSource
) : IContactRepository {

    private val useCase1MainThreadBlocking = UseCase1MainThreadBlocking(localDataSource)
    private val useCase2KotlinThreadBackground = UseCase2KotlinThreadBackground(localDataSource)
    private val useCase3Handler = UseCase3Handler(localDataSource)
    private val useCase4AsyncTask = UseCase4AsyncTask(localDataSource)
    private val useCase5RxJava = UseCase5RxJava(localDataSource)
    private val useCase6ThreadPoolExecutor = UseCase6ThreadPoolExecutor(localDataSource)
    private val useCase7Executor = UseCase7Executor(localDataSource)
    private val useCase8Loader = UseCase8Loader(localDataSource)

    override var context: Context? = null

    // interface IContactRepository

    override fun getAllContacts(): LiveData<List<Person>> {
        setContextForAllUseCases(context)

        val useCase = UseCases.UseCase1MainThreadBlocking
//        val useCase = UseCases.UseCase1MainThreadBlocking

        return when (useCase) {
            UseCases.UseCase1MainThreadBlocking -> useCase1MainThreadBlocking.getPersons()
            UseCases.UseCase2KotlinThreadBackground -> useCase2KotlinThreadBackground.getPersons()
            UseCases.UseCase3Handler -> useCase3Handler.getPersons()
            UseCases.UseCase4AsyncTask -> useCase4AsyncTask.getPersons()
            UseCases.UseCase5RxJava -> useCase5RxJava.getPersons()
            UseCases.UseCase6ThreadPoolExecutor -> useCase6ThreadPoolExecutor.getPersons()
            UseCases.UseCase7Executor -> useCase7Executor.getPersons()
            UseCases.UseCase8Loader -> useCase8Loader.getPersons()
        }
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
    }
}