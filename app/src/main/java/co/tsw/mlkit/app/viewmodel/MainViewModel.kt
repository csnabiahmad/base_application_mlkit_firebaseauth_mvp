package co.tsw.mlkit.app.viewmodel

import android.widget.EditText
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.tsw.mlkit.app.common.Constants
import co.tsw.mlkit.app.presenter.MainPresenter
import co.tsw.mlkit.app.utils.FirebaseUtils
import co.tsw.mlkit.app.utils.FirebaseUtils.firebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_login.mEditTextPassword
import kotlinx.android.synthetic.main.activity_login.mEditTextUsername


class MainViewModel : ViewModel() {

    private lateinit var signInEmail: String
    private  lateinit var signInPassword: String
    private lateinit var signInInputsArray: Array<EditText>
    val currentUser : FirebaseUser? = firebaseAuth.currentUser

    private fun notEmpty(): Boolean = signInEmail.isNotEmpty() && signInPassword.isNotEmpty()

    fun signInUser(mMainPresenter:MainPresenter, mEditTextUsername:EditText,mEditTextPassword:EditText) {
        signInEmail = mEditTextUsername.text.toString().trim()
        signInPassword = mEditTextPassword.text.toString().trim()
        signInInputsArray = arrayOf(mEditTextUsername, mEditTextPassword)

        if (notEmpty()) {
            mMainPresenter.firebaseApiRequest(signInEmail,signInPassword, Constants.AUTHENTICATE)
        } else {
            signInInputsArray.forEach { input ->
                if (input.text.toString().trim().isEmpty()) {
                    input.error = "${input.hint} is required"
                }
            }
        }
    }
}