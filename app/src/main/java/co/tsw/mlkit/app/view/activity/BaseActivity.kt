package co.tsw.mlkit.app.view.activity

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import co.tsw.mlkit.app.adapter.AbstractAdapter
import co.tsw.mlkit.app.common.ProgressDlg
import co.tsw.mlkit.app.network.ApiInteractor
import co.tsw.mlkit.app.utils.FirebaseUtils
import co.tsw.mlkit.app.utils.SharedPreference
import co.tsw.mlkit.app.utils.toast
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.messaging.FirebaseMessaging

open class BaseActivity : AppCompatActivity(), ApiInteractor, AbstractAdapter.ListItemInteractionListener{


    protected var doubleBackToExitPressedOnce = false
    protected var mLocalData: SharedPreference? = null
    protected var mProgressDlg: ProgressDlg? = null
    protected var mFirebaseAnalytics: FirebaseAnalytics? = null


    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        //toke firebase
        FirebaseMessaging.getInstance().isAutoInitEnabled = true
    }


    fun signout(){
        FirebaseUtils.firebaseAuth.signOut()
        mLocalData?.removeAllData()
        startActivity(Intent(this, SignInActivity::class.java))
        toast("Signed out")
        finishAffinity()
    }

    override fun onInteraction(view: View, model: Any, position: Int) {}

    override fun onStartApi() {}

    override fun onNextApi(api: String, jsonObject: Any?) {}

    override fun onErrorApi(api: String, e: Any) {}

    override fun onFinishApi() {}

}