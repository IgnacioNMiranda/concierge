@file:Suppress("ArrayInDataClass")

package com.example.myapplication.modelResponse

import com.example.myapplication.model.User
import java.util.*

/**
 * Response handler for the Auth information.
 *
 * Each response contains a server [message], a possible [error],
 * a login [user], its auth [token] and the expiration of this ([token_expires_at]).
 */
data class AuthResponse(

    var error: String? = null,
    var message: String? = null,
    var user: User? = null,
    var token: String? = null,
    var token_expires_at: Date? = null,
    var validation_errors: Array<Any>? = null
)
