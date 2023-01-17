package co.tsw.mlkit.app.view.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import co.tsw.mlkit.app.R
import co.tsw.mlkit.app.adapter.AbstractAdapter
import co.tsw.mlkit.app.common.ProgressDlg
import co.tsw.mlkit.app.presenter.MainInteractor
import co.tsw.mlkit.app.presenter.MainPresenter
import co.tsw.mlkit.app.utils.FirebaseUtils.firebaseAuth
import co.tsw.mlkit.app.utils.SharedPreference
import co.tsw.mlkit.app.utils.Utils
import co.tsw.mlkit.app.utils.toast
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_main.mDrawerLayout
import kotlinx.android.synthetic.main.activity_main.mToolbarMain


class MainActivity :  AppCompatActivity(), MainInteractor, AbstractAdapter.ListItemInteractionListener{

    private var mMainPresenter: MainPresenter? = null
    private var mFragment: Fragment? = null
    private var doubleBackToExitPressedOnce = false
    private var mLocalData: SharedPreference? = null
    private var mProgressDlg: ProgressDlg? = null
    private var mFirebaseAnalytics: FirebaseAnalytics? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)

        setSupportActionBar(mToolbarMain)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        val toggle = ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbarMain, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        mDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        mMainPresenter = MainPresenter(this)
        mLocalData = SharedPreference.getInstance(applicationContext)
        mProgressDlg = ProgressDlg(this)


        //toke firebase
        FirebaseMessaging.getInstance().isAutoInitEnabled = true
    }

    override fun onResume() {
        super.onResume()
        /* dismiss keyboard */
        Utils.hideSoftKeyboard(this@MainActivity)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        for (fragment in supportFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onDetachedFromWindow() {
        mProgressDlg?.hide()
        super.onDetachedFromWindow()
    }

    override fun onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START)
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed()
                return
            }
            doubleBackToExitPressedOnce = true
            Toast.makeText(applicationContext, getString(R.string.confirm_logout), Toast.LENGTH_SHORT).show()
            Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
        }
    }
    fun signout(){
        firebaseAuth.signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        toast("Signed out")
        finishAffinity()
    }
    override fun onInteraction(view: View, model: Any, position: Int) {
        TODO("Not yet implemented")
    }

    override fun onStartApi() {
        TODO("Not yet implemented")
    }

    override fun onNextApi(api: String, jsonObject: Any?) {
        TODO("Not yet implemented")
    }

    override fun onErrorApi(api: String, e: Any) {
        TODO("Not yet implemented")
    }

    override fun onFinishApi() {
        TODO("Not yet implemented")
    }


}