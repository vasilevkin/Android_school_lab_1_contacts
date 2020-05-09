package com.vasilevkin.greatcontacts.repository

import android.content.Context
import com.vasilevkin.greatcontacts.usecases.UseCases


interface IUseCaseStorage {

    var context: Context?

    fun getSelectedUseCase(): UseCases

    fun setSelectedUseCase(useCase: UseCases)
}
