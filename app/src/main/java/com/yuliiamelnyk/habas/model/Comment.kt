package com.yuliiamelnyk.habas.model

/**
 * @project Habas
 * @author yuliiamelnyk on 10/11/2020
 */
data class Comment (
    private var user: User,
    private var text: String
)