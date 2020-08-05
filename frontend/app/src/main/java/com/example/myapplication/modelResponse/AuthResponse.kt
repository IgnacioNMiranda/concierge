package com.example.myapplication.modelResponse

import com.example.myapplication.model.User
import java.util.*

data class AuthResponse(

    var error: String? = null,

    var message: String? = null,

    var user: User? = null,

    var token: String? = null,

    var token_expires_at: Date? = null

)
