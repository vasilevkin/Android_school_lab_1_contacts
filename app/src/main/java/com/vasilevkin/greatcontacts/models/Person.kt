package com.vasilevkin.greatcontacts.models

import java.io.Serializable


data class Person(
    val firstName: String,
    val lastName: String,
    val phone: String,
    val email: String
) : Serializable
