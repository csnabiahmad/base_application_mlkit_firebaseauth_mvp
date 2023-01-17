package co.tsw.mlkit.app.presenter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import android.widget.EditText
import co.tsw.mlkit.app.common.Constants
import co.tsw.mlkit.app.network.ApiInteractor
import co.tsw.mlkit.app.utils.FirebaseUtils
import co.tsw.mlkit.app.utils.toast
import co.tsw.mlkit.app.view.activity.MainActivity
import co.tsw.mlkit.app.view.fragment.*
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_create_account.mEditTextCPassword
import kotlinx.android.synthetic.main.activity_create_account.mEditTextPassword
import kotlinx.android.synthetic.main.activity_create_account.mEditTextUsername


open class SignUpPresenter (private val mInteractor: ApiInteractor?) {

    lateinit var userEmail: String
    lateinit var userPassword: String
    lateinit var userCPassword: String
    lateinit var createAccountInputsArray: Array<EditText>

    private fun notEmpty(): Boolean = userEmail.isNotEmpty() &&
            userPassword.isNotEmpty() &&
            userCPassword.isNotEmpty()

    private fun identicalPassword(): Boolean {
        var identical = false
        if (notEmpty() &&
            userPassword == userCPassword
        ) {
            identical = true
        } else if (!notEmpty()) {
            createAccountInputsArray.forEach { input ->
                if (input.text.toString().trim().isEmpty()) {
                    input.error = "${input.hint} is required"
                }
            }
        }
        return identical
    }

    fun signup(mEditTextUsername: EditText, mEditTextPassword: EditText, mEditTextCPassword: EditText) {
        userEmail = mEditTextUsername.text.toString().trim()
        userPassword = mEditTextPassword.text.toString().trim()
        userCPassword = mEditTextCPassword.text.toString().trim()
        createAccountInputsArray = arrayOf(mEditTextUsername, mEditTextPassword, mEditTextCPassword)

        if ( identicalPassword()) {
            firebaseApiRequest(userEmail, userPassword , Constants.TAG_SIGNUP)
        }
    }

    /* send verification email to the new user. This will only
    *  work if the firebase user is not null.
    */
    fun sendEmailVerification() {
        FirebaseUtils.firebaseUser?.let {
            it.sendEmailVerification()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        mInteractor?.onNextApi(Constants.TAG_SENDMAIL,task)
                    } else {
                        mInteractor?.onErrorApi(Constants.TAG_SENDMAIL,task.exception?.message!!)
                    }
                    mInteractor?.onFinishApi()
                }
                .addOnCanceledListener {
                    mInteractor?.onErrorApi(Constants.TAG_SENDMAIL,"Api cancelled, please retry!")
                    mInteractor?.onFinishApi()
                }
                .addOnFailureListener { task->
                    mInteractor?.onErrorApi(Constants.TAG_SENDMAIL,task.message!!)
                    mInteractor?.onFinishApi()
                }
            }
    }

    @SuppressLint("CheckResult")
    fun firebaseApiRequest(signInEmail: String, signInPassword: String, api: String) {
        mInteractor?.onStartApi()
        FirebaseUtils.firebaseAuth.createUserWithEmailAndPassword(signInEmail, signInPassword)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    mInteractor?.onNextApi(api,task)
                } else {
                    mInteractor?.onErrorApi(api,task.exception?.message!!)
                }
                mInteractor?.onFinishApi()
            }
            .addOnCanceledListener {
                mInteractor?.onErrorApi(api,"Api cancelled, please retry!")
                mInteractor?.onFinishApi()
            }
            .addOnFailureListener { task->
                mInteractor?.onErrorApi(api,task.message!!)
                mInteractor?.onFinishApi()
            }
    }
}