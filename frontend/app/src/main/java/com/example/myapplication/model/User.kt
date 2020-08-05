package com.example.myapplication.model

/**
 * User account data.
 *
 * A user has a [name], an associated [email] and a [password].
 */
data class User(
    var name: String? = null,
    var email: String? = null,
    var password: String? = null
)
