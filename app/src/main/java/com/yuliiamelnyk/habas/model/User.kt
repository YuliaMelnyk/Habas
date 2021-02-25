package com.yuliiamelnyk.habas.model

import java.io.Serializable

/**
 * @project Habas
 * @author yuliiamelnyk on 05/10/2020
 */

// Class User for DB - model
class User : Serializable {
    lateinit var id: String
    lateinit var name: String
    lateinit var email: String
    lateinit var password: String
    lateinit var article: List<Article>
    lateinit var comment: List<Comment>
    lateinit var foto: String

    constructor(name: String, email: String, password: String) {
        this.name = name
        this.email = email
        this.password = password
    }

    constructor()
}