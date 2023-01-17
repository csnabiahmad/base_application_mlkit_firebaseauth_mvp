package co.tsw.mlkit.app.view.activity

import android.content.Intent
import android.os.Bundle
import co.tsw.mlkit.app.R
import co.tsw.mlkit.app.common.Constants
import co.tsw.mlkit.app.common.ProgressDlg
import co.tsw.mlkit.app.presenter.SignUpPresenter
import co.tsw.mlkit.app.utils.SharedPreference
import co.tsw.mlkit.app.utils.Utils
import co.tsw.mlkit.app.utils.toast
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.activity_create_account.*

class SignUpActivity : BaseActivity() {

    private var mSignUpPresenter: SignUpPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        /* init data */
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        mLocalData = SharedPreference.getInstance(applicationContext)
        mProgressDlg = ProgressDlg(this)
        mSignUpPresenter = SignUpPresenter(this@SignUpActivity)
        mButtonLogin.setOnClickListener {
            mSignUpPresenter?.signup(mEditTextUsername,mEditTextPassword,mEditTextCPassword)
            /* dismiss keyboard */
            Utils.hideSoftKeyboard(this@SignUpActivity)
        }

        imageViewBack.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
            toast("please sign into your account")
            finish()
        }

    }

    override fun onStartApi() {
        mProgressDlg?.show("Signing you in...")
    }

    override fun onNextApi(api: String, jsonObject: Any?) {
        if (api == Constants.TAG_SIGNUP){
            toast("Account created successfully!")
            mSignUpPresenter?.sendEmailVerification()
        }
        else if (api == Constants.TAG_SENDMAIL){
            toast("Verification mail is send to your email address successfully!")
            startActivity(Intent(this, SignInActivity::class.java)).also { finishAffinity() }
        }

    }

    override fun onErrorApi(api: String, e: Any) {
        if (api == Constants.TAG_SIGNUP){
            toast(e.toString())
        }
    }

    override fun onFinishApi() {
        mProgressDlg?.hide()
    }

}