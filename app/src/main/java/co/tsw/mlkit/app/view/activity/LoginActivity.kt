package co.tsw.mlkit.app.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.tsw.mlkit.app.R
import co.tsw.mlkit.app.common.Constants
import co.tsw.mlkit.app.common.ProgressDlg
import co.tsw.mlkit.app.presenter.MainInteractor
import co.tsw.mlkit.app.presenter.MainPresenter
import co.tsw.mlkit.app.utils.SharedPreference
import co.tsw.mlkit.app.utils.toast
import co.tsw.mlkit.app.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_login.mButtonLogin
import kotlinx.android.synthetic.main.activity_login.mEditTextPassword
import kotlinx.android.synthetic.main.activity_login.mEditTextUsername
import kotlinx.android.synthetic.main.activity_login.textViewCreateAccount

class LoginActivity : AppCompatActivity(), MainInteractor {

    private var mProgressDlg: ProgressDlg? = null
    private var mLocalData: SharedPreference? = null
    private var mMainPresenter: MainPresenter? = null
    private var mMainViewModel: MainViewModel? = null
    override fun onStart() {
        super.onStart()
        mMainViewModel?.currentUser?.let {
            startActivity(Intent(this, MainActivity::class.java))
            toast("Welcome back!")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //init data
        mProgressDlg = ProgressDlg(this)
        mLocalData = SharedPreference.getInstance(applicationContext)
        mMainPresenter = MainPresenter(this)
        mMainViewModel = MainViewModel()


        init()

    }

    fun init() {
        textViewCreateAccount.setOnClickListener {
            startActivity(Intent(this, CreateAccountActivity::class.java))
            finish()
        }

        mButtonLogin.setOnClickListener {
            mMainViewModel?.signInUser(mMainPresenter!!,mEditTextUsername,mEditTextPassword)
        }
    }

    override fun onStartApi() {
        mProgressDlg?.show("Signing you in...")
    }

    override fun onNextApi(api: String, jsonObject: Any?) {
        if (api == Constants.AUTHENTICATE){
            startActivity(Intent(this, MainActivity::class.java))
            toast("signed in successfully")
            finish()
        }
    }

    override fun onErrorApi(api: String, e: Any) {
        if (api == Constants.AUTHENTICATE){
            toast(e.toString())
        }
    }

    override fun onFinishApi() {
        mProgressDlg?.hide()
    }
}