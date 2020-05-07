package com.vasilevkin.greatcontacts.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.vasilevkin.greatcontacts.models.Person
import com.vasilevkin.greatcontacts.repository.datasource.ILocalDataSource
import com.vasilevkin.greatcontacts.usecases.UseCase1MainThreadBlocking
import com.vasilevkin.greatcontacts.usecases.UseCase2KotlinThreadBackground
import com.vasilevkin.greatcontacts.usecases.UseCase3Handler
import com.vasilevkin.greatcontacts.usecases.UseCases
import javax.inject.Inject


class ContactRepository @Inject constructor(
    private val localDataSource: ILocalDataSource
) : IContactRepository {

    private val useCase1MainThreadBlocking = UseCase1MainThreadBlocking(localDataSource)
    private val useCase2KotlinThreadBackground = UseCase2KotlinThreadBackground(localDataSource)
    private val useCase3Handler = UseCase3Handler(localDataSource)

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
    }
}