package co.tsw.mlkit.app.view.activity

import android.content.Intent
import android.os.Bundle
import co.tsw.mlkit.app.R
import co.tsw.mlkit.app.common.Constants
import co.tsw.mlkit.app.common.ProgressDlg
import co.tsw.mlkit.app.presenter.SignInPresenter
import co.tsw.mlkit.app.utils.SharedPreference
import co.tsw.mlkit.app.utils.toast
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.activity_login.mButtonLogin
import kotlinx.android.synthetic.main.activity_login.mEditTextPassword
import kotlinx.android.synthetic.main.activity_login.mEditTextUsername
import kotlinx.android.synthetic.main.activity_login.textViewCreateAccount

class SignInActivity : BaseActivity() {

    private var mSignInPresenter: SignInPresenter? = null
    override fun onStart() {
        super.onStart()
        mSignInPresenter?.currentUser?.let {
            startActivity(Intent(this, MainActivity::class.java))
            toast("Welcome back!")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //init data
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        mLocalData = SharedPreference.getInstance(applicationContext)
        mProgressDlg = ProgressDlg(this)
        mSignInPresenter = SignInPresenter(this)
        init()

    }

    fun init() {
        textViewCreateAccount.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }

        mButtonLogin.setOnClickListener {
            mSignInPresenter?.signInUser(mEditTextUsername,mEditTextPassword)
        }
    }

    override fun onStartApi() {
        mProgressDlg?.show("Signing you in...")
    }

    override fun onNextApi(api: String, jsonObject: Any?) {
        if (api == Constants.TAG_SIGNIN){
            startActivity(Intent(this, MainActivity::class.java))
            toast("signed in successfully")
            finish()
        }
    }

    override fun onErrorApi(api: String, e: Any) {
        if (api == Constants.TAG_SIGNIN){
            toast(e.toString())
        }
    }

    override fun onFinishApi() {
        mProgressDlg?.hide()
    }
}