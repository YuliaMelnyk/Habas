package com.yuliiamelnyk.habas

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.irshulx.models.EditorContent
import com.github.irshulx.models.EditorTextStyle
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.yuliiamelnyk.habas.model.Article
import kotlinx.android.synthetic.main.activity_new_post.*
import kotlinx.android.synthetic.main.toolbar_new_post.*

class NewPostActivity : AppCompatActivity() {

    val firestore = Firebase.firestore
    val TAG: String = "NewPostActivity"
    lateinit var article: Article


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_post)



        //update TextStyles in Editor
        findViewById<View>(R.id.action_h1).setOnClickListener {
            editor.updateTextStyle(
                EditorTextStyle.H1
            )
        }
        findViewById<View>(R.id.action_h2).setOnClickListener {
            editor.updateTextStyle(
                EditorTextStyle.H2
            )
        }
        findViewById<View>(R.id.action_h3).setOnClickListener {
            editor.updateTextStyle(
                EditorTextStyle.H3
            )
        }
        findViewById<View>(R.id.action_bold).setOnClickListener {
            editor.updateTextStyle(
                EditorTextStyle.BOLD
            )
        }
        findViewById<View>(R.id.action_Italic).setOnClickListener {
            editor.updateTextStyle(
                EditorTextStyle.ITALIC
            )
        }
        findViewById<View>(R.id.action_indent).setOnClickListener {
            editor.updateTextStyle(
                EditorTextStyle.INDENT
            )
        }
        findViewById<View>(R.id.action_outdent).setOnClickListener {
            editor.updateTextStyle(
                EditorTextStyle.OUTDENT
            )
        }
        findViewById<View>(R.id.action_bulleted).setOnClickListener { editor.insertList(false) }
        findViewById<View>(R.id.action_color).setOnClickListener { editor.updateTextColor("#FF3333") }
        findViewById<View>(R.id.action_unordered_numbered).setOnClickListener {
            editor.insertList(
                true
            )
        }
        findViewById<View>(R.id.action_hr).setOnClickListener { editor.insertDivider() }
        findViewById<View>(R.id.action_insert_image).setOnClickListener { editor.openImagePicker() }
        findViewById<View>(R.id.action_insert_link).setOnClickListener { editor.insertLink() }
        findViewById<View>(R.id.action_erase).setOnClickListener { editor.clearAllContents() }
        findViewById<View>(R.id.action_blockquote).setOnClickListener {
            editor.updateTextStyle(
                EditorTextStyle.BLOCKQUOTE
            )
        }

        editor.render()

        btnRender.setOnClickListener { getEditorsValues() }


    }

    private fun writeDataOnFirestore() {
        firestore.collection("articles").document("articleList")
            .set(article)
            .addOnSuccessListener {
                Toast.makeText(
                    applicationContext,
                    "DocumentSnapshot successfully written!",
                    Toast.LENGTH_LONG
                ).show()
                Log.d(TAG, "DocumentSnapshot successfully written!")

                //initListView()
            }.addOnFailureListener { e ->
                Log.e(TAG, "Error writing document", e)
            }
    }

    private fun getEditorsValues():String {

        var string: String = editor.contentAsHTML
        return string
    }

}