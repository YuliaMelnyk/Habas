package com.yuliiamelnyk.habas

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.yuliiamelnyk.habas.model.Article
import com.yuliiamelnyk.habas.model.User
import com.yuliiamelnyk.habas.recyclerArticle.ArticleAdapter
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //set the toolbar
        setSupportActionBar(toolbar)

        nav_view.setNavigationItemSelectedListener(this)

        var list = ArrayList<Article>()
        var article =  Article( "Kotlin Program to Get Current Date/TIme", "Cloud Firestore is a cloud-hosted, " +
                "NoSQL database that your iOS, Android, and web apps can access directly via native SDKs. Cloud Firestore is " +
                "also available in native Node.js, Java, Python, Unity, C++ and Go SDKs, in addition to REST and RPC APIs.\n" +
                "Following Cloud Firestore’s NoSQL data model, you store data in documents that contain fields mapping to values." +
                " These documents are stored in collections, which are containers for your documents that you can use to " +
                "organize your data and build queries. Documents support many different data types, from simple strings and " +
                "numbers, to complex, nested objects. You can also create subcollections within documents and build " +
                "hierarchical data structures that scale as your database grows. The Cloud Firestore data model supports " +
                "whatever data structure works best for your app.", listOf("Android", "fireBase"),
            LocalDate.now(), User("Yuliia", "", ""), R.drawable.heart, 2, R.drawable.bookmark, 0, R.drawable.bookmark,
            null, null)
        list.add(article)
        list.add(article)
        list.add(article)
        recycler_articles.hasFixedSize()
        recycler_articles.layoutManager = LinearLayoutManager(this)
        recycler_articles.adapter = ArticleAdapter(list, this)

        //toolbar.title = "Ultimas publicaciones"
        // Close the soft keyboard when you open or close the Drawer
        val toggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this, drawer_layout,
            toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        ) {
            override fun onDrawerClosed(drawerView: View) {
                // Triggered once the drawer closes
                super.onDrawerClosed(drawerView)
                try {
                    val inputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                } catch (e: Exception) {
                    e.stackTrace
                }
            }

            override fun onDrawerOpened(drawerView: View) {
                // Triggered once the drawer opens
                super.onDrawerOpened(drawerView)
                try {
                    val inputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
                } catch (e: Exception) {
                    e.stackTrace
                }
            }
        }
        drawer_layout.addDrawerListener(toggle)

        toggle.syncState()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_home -> startActivity(Intent(this@HomeActivity, HomeActivity::class.java))
            R.id.nav_favorites -> startActivity(Intent(this@HomeActivity, HomeActivity::class.java))
            R.id.nav_bookmarks -> startActivity(Intent(this@HomeActivity, HomeActivity::class.java))
            R.id.nav_post -> startActivity(Intent(this@HomeActivity, NewPostActivity::class.java))
        }
        return true
    }
}