package com.yuliiamelnyk.habas

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.irshulx.EditorListener
import com.github.irshulx.models.EditorTextStyle
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.yuliiamelnyk.habas.model.Article
import kotlinx.android.synthetic.main.activity_new_post.*
import kotlinx.android.synthetic.main.toolbar_new_post.*
import java.io.IOException


// class to create new Article with using WYSIWYG
class NewPostActivity : AppCompatActivity() {

    val firestore = Firebase.firestore
    val TAG: String = "NewPostActivity"
    lateinit var article: Article


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_post)


        // update TextStyles in Editor

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
        // open Gallery to choose image
        findViewById<View>(R.id.action_insert_image).setOnClickListener { editor.openImagePicker() }
        findViewById<View>(R.id.action_insert_link).setOnClickListener { editor.insertLink() }
        findViewById<View>(R.id.action_erase).setOnClickListener { editor.clearAllContents() }
        findViewById<View>(R.id.action_blockquote).setOnClickListener {
            editor.updateTextStyle(
                EditorTextStyle.BLOCKQUOTE
            )
        }
        editor.editorListener = object : EditorListener {
            override fun onTextChanged(editText: EditText, text: Editable) {
                // Toast.makeText(EditorTestActivity.this, text, Toast.LENGTH_SHORT).show();
            }

            override fun onUpload(image: Bitmap, uuid: String) {
                Toast.makeText(this@NewPostActivity, uuid, Toast.LENGTH_LONG).show()
                /**
                 * TODO do your upload here from the bitmap received and all onImageUploadComplete(String url); to insert the result url to
                 * let the editor know the upload has completed
                 */
                editor.onImageUploadComplete(
                    "http://www.videogamesblogger.com/wp-content/uploads/2015/08/metal-gear-solid-5-the-phantom-pain-cheats-640x325.jpg",
                    uuid
                )
                // editor.onImageUploadFailed(uuid);
            }

            override fun onRenderMacro(name: String, props: Map<String, Any>, index: Int): View {
                return layoutInflater.inflate(R.layout.activity_new_post, null)
            }
        }


        editor.render()


        //val headingTypeface: Map<Int, String> = getHeadingTypeface()
        //val contentTypeFace: Map<Int, String> = getContentface()

        btnRender.setOnClickListener {
            getEditorsValues()
            writeDataOnFirestore()
        }


    }



    //image picker to put image from gallery to editor
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == editor.PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            val uri = data.data
            try {
                uri?.let {
                        val bitmap = MediaStore.Images.Media.getBitmap(
                            this.contentResolver,
                            uri
                        )
                        editor.insertImage(bitmap)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else if (resultCode == RESULT_CANCELED) {
            // editor.RestoreState();
            Toast.makeText(applicationContext, "Cancelled", Toast.LENGTH_SHORT).show()
        }
    }


    // save article to database
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

    private fun getEditorsValues(): String {

        var string: String = editor.contentAsHTML
        return string
    }

}