package com.vasilevkin.greatcontacts.repository.datasource

import android.content.Context
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vasilevkin.greatcontacts.models.Person
import com.vasilevkin.greatcontacts.utils.INTERNAL_DB_LOCAL_CONTACTS_JSON_FILE_NAME
import com.vasilevkin.greatcontacts.utils.LOCAL_CONTACTS_JSON_FILE_NAME
import java.io.*


class LocalDataSource : ILocalDataSource {

    override var context: Context? = null

    // interface ILocalDataSource

    override fun getContacts(): List<Person> {
        val localContext = context
        var persons: List<Person> = emptyList()

        checkIfLocalFileExistsAndCopyIfNeeded()

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

    override fun addNewPersonInList(person: Person, list: List<Person>) {
        val newList = list.toMutableList()

        newList.add(person)

        val status = saveContacts(newList)
        val textMessage = if (status) {
            "New contact is saved successfully"
        } else {
            "Unknown error when save a new contact"
        }

        Toast.makeText(context, textMessage, Toast.LENGTH_LONG)
            .show()
    }

    override fun updatePersonInList(person: Person, updatedPerson: Person, list: List<Person>) {
        val index = list.indexOf(person)
        val updatedPersons = list.toMutableList().apply {
            this[index] = updatedPerson
        }
        val status = saveContacts(updatedPersons)
        val textMessage = if (status) {
            "Updated contact is saved successfully"
        } else {
            "Unknown error when save an updated contact"
        }

        Toast.makeText(context, textMessage, Toast.LENGTH_LONG)
            .show()
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

    private fun checkIfLocalFileExistsAndCopyIfNeeded() {
        val localContext = context

        if (localContext != null) {
            val localFilePath =
                context?.filesDir.toString() + "/" + INTERNAL_DB_LOCAL_CONTACTS_JSON_FILE_NAME
            val file = File(localFilePath)

            if (!(file.exists())) {
                copyFileFromAssetToInternalStorage(
                    localContext,
                    LOCAL_CONTACTS_JSON_FILE_NAME,
                    INTERNAL_DB_LOCAL_CONTACTS_JSON_FILE_NAME
                )
            }
        }
    }

    private fun copyFileFromAssetToInternalStorage(context: Context?, asset: String, file: String) {
        val localContext = context
        var inputStream: InputStream? = null
        var outputStream: OutputStream? = null

        try {
            if (localContext != null) {
                inputStream = localContext.assets.open(asset)
                outputStream = localContext.openFileOutput(file, Context.MODE_PRIVATE)

                copyFile(inputStream, outputStream)
                inputStream.close()

                inputStream = null

                outputStream.flush()
                outputStream.close()

                outputStream = null
            }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }

    @Throws(IOException::class)
    private fun copyFile(inputStream: InputStream, outputStream: OutputStream) {
        val buffer = ByteArray(1024)
        var read: Int
        while (inputStream.read(buffer).also { read = it } != -1) {
            outputStream.write(buffer, 0, read)
        }
    }
}