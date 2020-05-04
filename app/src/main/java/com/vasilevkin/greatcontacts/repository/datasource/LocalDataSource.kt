package com.vasilevkin.greatcontacts.repository.datasource

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vasilevkin.greatcontacts.models.Person
import com.vasilevkin.greatcontacts.utils.LOCAL_CONTACTS_JSON_FILE_NAME
import java.io.IOException


class LocalDataSource : ILocalDataSource {

    override var context: Context? = null

    override fun getContacts(): List<Person> {
        val localContext = context
        var persons: List<Person> = emptyList()

        if (localContext != null) {
            val jsonFileString = getJsonDataFromAsset(localContext, LOCAL_CONTACTS_JSON_FILE_NAME)

            val gson = Gson()
            val listPersonType = object : TypeToken<List<Person>>() {}.type

            persons = gson.fromJson(jsonFileString, listPersonType)
        }

        return persons
    }

    private fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }
}