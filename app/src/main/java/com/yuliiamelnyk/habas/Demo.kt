package com.yuliiamelnyk.habas

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.facebook.accountkit.AccountKit

class Demo : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AccountKit.initialize(applicationContext)
    }
}