package com.vasilevkin.greatcontacts.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.vasilevkin.greatcontacts.models.Person
import com.vasilevkin.greatcontacts.repository.datasource.ILocalDataSource
import com.vasilevkin.greatcontacts.usecases.UseCase1MainThreadBlocking
import com.vasilevkin.greatcontacts.usecases.UseCases
import javax.inject.Inject


class ContactRepository @Inject constructor(
    private val localDataSource: ILocalDataSource
) : IContactRepository {

    private val useCase1MainThreadBlocking = UseCase1MainThreadBlocking(localDataSource)

    override var context: Context? = null

    // interface IContactRepository

    override fun getAllContacts(): LiveData<List<Person>> {
        setContextForAllUseCases(context)

        val useCase = UseCases.UseCase1MainThreadBlocking

        when (useCase) {
            UseCases.UseCase1MainThreadBlocking ->
                return useCase1MainThreadBlocking.getPersons()
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
    }
}