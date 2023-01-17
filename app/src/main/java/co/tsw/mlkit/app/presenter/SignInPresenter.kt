package co.tsw.mlkit.app.presenter

import android.annotation.SuppressLint
import android.view.View
import android.widget.EditText
import co.tsw.mlkit.app.common.Constants
import co.tsw.mlkit.app.network.ApiInteractor
import co.tsw.mlkit.app.utils.FirebaseUtils
import co.tsw.mlkit.app.utils.FirebaseUtils.firebaseAuth
import co.tsw.mlkit.app.view.fragment.*
import com.google.firebase.auth.FirebaseUser


open class SignInPresenter (private val mInteractor: ApiInteractor?) {

    private lateinit var signInEmail: String
    private  lateinit var signInPassword: String
    private lateinit var signInInputsArray: Array<EditText>
    var currentUser : FirebaseUser? = firebaseAuth.currentUser

    private fun notEmpty(): Boolean = signInEmail.isNotEmpty() && signInPassword.isNotEmpty()

    fun signInUser(mEditTextUsername: EditText, mEditTextPassword: EditText) {
        signInEmail = mEditTextUsername.text.toString().trim()
        signInPassword = mEditTextPassword.text.toString().trim()
        signInInputsArray = arrayOf(mEditTextUsername, mEditTextPassword)

        if (notEmpty()) {
            firebaseApiRequest(signInEmail,signInPassword, Constants.TAG_SIGNIN)
        } else {
            signInInputsArray.forEach { input ->
                if (input.text.toString().trim().isEmpty()) {
                    input.error = "${input.hint} is required"
                }
            }
        }
    }


    @SuppressLint("CheckResult")
    fun firebaseApiRequest(signInEmail: String, signInPassword: String, api: String) {
        mInteractor?.onStartApi()
        FirebaseUtils.firebaseAuth.signInWithEmailAndPassword(signInEmail, signInPassword)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    mInteractor?.onNextApi(api,task)
                } else {
                    mInteractor?.onErrorApi(api,task.exception?.message!!)
                }
                mInteractor?.onFinishApi()
            }
            .addOnCanceledListener {
                mInteractor?.onErrorApi(api,"Api cancelled!")
                mInteractor?.onFinishApi()
            }
            .addOnFailureListener { task->
                mInteractor?.onErrorApi(api,task.message!!)
                mInteractor?.onFinishApi()

            }
    }
}