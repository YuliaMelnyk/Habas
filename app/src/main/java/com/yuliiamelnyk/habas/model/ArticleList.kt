package com.yuliiamelnyk.habas.model

import java.io.Serializable

/**
 * @project Habas
 * @author yuliiamelnyk on 10/11/2020
 */
//class for save articles in document in FireStore DB
data class ArticleList(
    var articleList: ArrayList<Article> = arrayListOf()
) :Serializable