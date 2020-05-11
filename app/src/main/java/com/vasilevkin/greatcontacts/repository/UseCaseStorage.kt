package com.vasilevkin.greatcontacts.repository

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.vasilevkin.greatcontacts.usecases.UseCases
import com.vasilevkin.greatcontacts.utils.SHARED_PREFERENCES_NAME
import com.vasilevkin.greatcontacts.utils.SHARED_PREFERENCES_SELECTED_USECASE_KEY


class UseCaseStorage : IUseCaseStorage {

    override var context: Context? = null

    override fun getSelectedUseCase(): UseCases {
        val sharedPreferences: SharedPreferences =
            context!!.getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE)
        val useCaseNumber = sharedPreferences.getInt(SHARED_PREFERENCES_SELECTED_USECASE_KEY, 0)

        return UseCases.values()[useCaseNumber]
    }

    override fun setSelectedUseCase(useCase: UseCases) {
        val sharedPreferences: SharedPreferences =
            context!!.getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val useCaseNumber = useCase.ordinal

        editor.putInt(SHARED_PREFERENCES_SELECTED_USECASE_KEY, useCaseNumber)
        editor.apply()
    }
}
