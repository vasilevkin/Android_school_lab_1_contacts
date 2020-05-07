package com.vasilevkin.greatcontacts.usecases

import android.content.Context
import androidx.lifecycle.LiveData
import com.vasilevkin.greatcontacts.models.Person


interface IUseCase {

    var context: Context?

    fun getPersons(): LiveData<List<Person>>
}