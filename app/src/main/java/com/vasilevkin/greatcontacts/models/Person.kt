package com.vasilevkin.greatcontacts.models

import java.io.Serializable


data class Person(
    private val firstName: String,
    private val lastName: String,
    private val phone: String,
    private val email: String
) : Serializable
