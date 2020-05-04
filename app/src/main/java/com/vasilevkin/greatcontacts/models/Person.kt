package com.vasilevkin.greatcontacts.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Person(
    @SerializedName("firstname") val firstName: String,
    @SerializedName("lastname") val lastName: String,
    val phone: String,
    val email: String
) : Serializable
