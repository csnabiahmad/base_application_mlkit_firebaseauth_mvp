package co.tsw.mlkit.app.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import co.tsw.mlkit.app.R
import co.tsw.mlkit.app.utils.FirebaseUtils.firebaseAuth
import co.tsw.mlkit.app.utils.FirebaseUtils.firebaseUser
import co.tsw.mlkit.app.utils.toast
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_create_account.imageViewBack
import kotlinx.android.synthetic.main.activity_create_account.mButtonLogin
import kotlinx.android.synthetic.main.activity_create_account.mEditTextCPassword
import kotlinx.android.synthetic.main.activity_create_account.mEditTextPassword
import kotlinx.android.synthetic.main.activity_create_account.mEditTextUsername

class CreateAccountActivity : AppCompatActivity() {
    lateinit var userEmail: String
    lateinit var userPassword: String
    lateinit var createAccountInputsArray: Array<EditText>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        createAccountInputsArray = arrayOf(mEditTextUsername, mEditTextPassword, mEditTextCPassword)
        mButtonLogin.setOnClickListener {
            signIn()
        }

        imageViewBack.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            toast("please sign into your account")
            finish()
        }

    }
    /* check if there's a signed-in user*/

    override fun onStart() {
        super.onStart()
        val user: FirebaseUser? = firebaseAuth.currentUser
        user?.let {
            startActivity(Intent(this, MainActivity::class.java))
            toast("welcome back")
        }
    }

    private fun notEmpty(): Boolean = mEditTextUsername.text.toString().trim().isNotEmpty() &&
            mEditTextPassword.text.toString().trim().isNotEmpty() &&
            mEditTextCPassword.text.toString().trim().isNotEmpty()

    private fun identicalPassword(): Boolean {
        var identical = false
        if (notEmpty() &&
            mEditTextPassword.text.toString().trim() == mEditTextCPassword.text.toString().trim()
        ) {
            identical = true
        } else if (!notEmpty()) {
            createAccountInputsArray.forEach { input ->
                if (input.text.toString().trim().isEmpty()) {
                    input.error = "${input.hint} is required"
                }
            }
        } else {
            toast("passwords are not matching !")
        }
        return identical
    }

    private fun signIn() {
        if (identicalPassword()) {
            // identicalPassword() returns true only  when inputs are not empty and passwords are identical
            userEmail = mEditTextUsername.text.toString().trim()
            userPassword = mEditTextPassword.text.toString().trim()

            /*create a user*/
            firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener { task ->
                    if (task.isComplete) {
                        toast("created account successfully !")
                        sendEmailVerification()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                }
                .addOnFailureListener { task ->
                    toast(task.message!!)
                }
        }
    }

    /* send verification email to the new user. This will only
    *  work if the firebase user is not null.
    */

    private fun sendEmailVerification() {
        firebaseUser?.let {
            it.sendEmailVerification().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    toast("email sent to $userEmail")
                }
            }
        }
    }
}