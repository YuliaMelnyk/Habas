package com.yuliiamelnyk.habas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.yuliiamelnyk.habas.model.User
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    private val TAG: String = "Add to collection"

    //Firebase
    var mFirebaseAuth: FirebaseAuth? = null
    var mValidateUser: ValidateUser? = null

    // Access a Cloud Firestore instance
    var db: FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        mFirebaseAuth = FirebaseAuth.getInstance()
        //initialize cloud firestore
        db = FirebaseFirestore.getInstance()

        //click on Register button
        btnRegister?.setOnClickListener(View.OnClickListener {
            val email = regEmail!!.text.toString().trim { it <= ' ' }
            val password = regPassword!!.text.toString().trim { it <= ' ' }
            val repassword = regRePassword!!.text.toString().trim { it <= ' ' }
            mValidateUser = ValidateUser()
            // Check for a valid email address.
            if (TextUtils.isEmpty(email)) {
                regEmail!!.error = getString(R.string.error_field_required)
                regEmail!!.requestFocus()
                return@OnClickListener
            } else if (!mValidateUser!!.isEmailValid(email)) {
                regEmail!!.error = getString(R.string.error_invalid_email)
                regEmail!!.requestFocus()
                return@OnClickListener
            } else if (TextUtils.isEmpty(password)) {
                regPassword!!.error = getString(R.string.error_field_required)
                regPassword!!.requestFocus()
                return@OnClickListener
            } else if (!mValidateUser!!.isPasswordValid(password)) {
                regPassword!!.error = getString(R.string.error_invalid_password)
                regPassword!!.requestFocus()
                return@OnClickListener
            } else if (repassword != password) {
                regRePassword!!.error = getString(R.string.error_repeat_password)
                regRePassword!!.requestFocus()
            }
            //register the user in firebase
            mFirebaseAuth!!.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this@SignUpActivity, "User created", Toast.LENGTH_SHORT)
                            .show()
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName(regName!!.text.toString()).build()
                        mFirebaseAuth!!.currentUser!!.updateProfile(profileUpdates)
                        updateUser()
                    } else {
                        Toast.makeText(
                            this@SignUpActivity,
                            "Error! " + task.exception!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        })
    }

    //function for update users fields and add user en FirebaseStore
    fun updateUser() {
        var user = User()
        val userId = mFirebaseAuth!!.currentUser!!.uid
        user.id = userId
        user.email = regEmail!!.text.toString()
        user.name = regName!!.text.toString()
        user.password = regPassword!!.text.toString()
        LoginActivity.currentUser = user;

        var userDB = hashMapOf(
            "id" to userId,
            "email" to user.email,
            "name" to user.name,
            "password" to user.password
        )
        //Add a new document with a generated ID
        db!!.collection("users")
            .add(userDB)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                val intent = Intent(this@SignUpActivity, SplashActivity::class.java)
                startActivity(intent)
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }
}