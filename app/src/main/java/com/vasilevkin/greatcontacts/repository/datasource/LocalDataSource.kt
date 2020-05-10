package com.vasilevkin.greatcontacts.repository.datasource

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vasilevkin.greatcontacts.models.Person
import com.vasilevkin.greatcontacts.utils.INTERNAL_DB_LOCAL_CONTACTS_JSON_FILE_NAME
import java.io.*


class LocalDataSource : ILocalDataSource {

    override var context: Context? = null

    // interface ILocalDataSource

    override fun getContacts(): List<Person> {
        val localContext = context
        var persons: List<Person> = emptyList()



        if (localContext != null) {
            val jsonFileString = getJsonDataFromLocalFile(
                localContext,
                INTERNAL_DB_LOCAL_CONTACTS_JSON_FILE_NAME
            )
            val gson = Gson()
            val listPersonType = object : TypeToken<List<Person>>() {}.type

            persons = gson.fromJson(jsonFileString, listPersonType)
        }

        return persons
    }

    override fun saveContacts(contacts: List<Person>): Boolean {
        val localContext = context
        var isSavedSuccessfully = false

        if (localContext != null) {
            val gson = Gson()
            val listPersonType = object : TypeToken<List<Person>>() {}.type
            val jsonFileString = gson.toJson(contacts, listPersonType)

            isSavedSuccessfully = saveJsonDataToLocalFile(
                localContext,
                jsonFileString,
                INTERNAL_DB_LOCAL_CONTACTS_JSON_FILE_NAME
            )
        }

        return isSavedSuccessfully
    }

    // Private methods

    private fun getJsonDataFromLocalFile(context: Context, fileName: String): String? {
        val jsonString: String

        try {
            val fileInputStream = context.openFileInput(fileName)
            val inputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            val stringBuilder = StringBuilder()
            var line: String?

            while (bufferedReader.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }

            jsonString = stringBuilder.toString()
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
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
    private fun saveJsonDataToLocalFile(
        context: Context,
        jsonString: String,
        fileName: String
    ): Boolean {
        try {
            val outputStream: FileOutputStream =
                context.openFileOutput(fileName, Context.MODE_PRIVATE)
            val out = OutputStreamWriter(outputStream)

            out.write(jsonString)
            out.close()
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return false
        }

        return true
    }

}