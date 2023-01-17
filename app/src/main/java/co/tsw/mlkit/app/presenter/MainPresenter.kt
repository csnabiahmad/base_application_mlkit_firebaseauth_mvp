package co.tsw.mlkit.app.presenter

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import co.tsw.mlkit.app.R
import co.tsw.mlkit.app.common.Constants
import co.tsw.mlkit.app.common.MyApplication
import co.tsw.mlkit.app.utils.FirebaseUtils
import co.tsw.mlkit.app.utils.toast
import co.tsw.mlkit.app.view.activity.MainActivity
import co.tsw.mlkit.app.view.fragment.*
import com.google.gson.JsonObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class MainPresenter (private val mInteractor: MainInteractor?) {



    var mFragment: Fragment? = null

    /**
     * handler menu tab
     *
     * @param position
     * @param activity
     */
    fun handlerMenuTabs(position: Int, activity: MainActivity, value: Any?) {
        var fragmentClass: Class<*>? = null
        var bundle: Bundle? = null
        mFragment = null
        when (position) {
            Constants.SCREEN_CUSTOMER -> {
                bundle = Bundle()
                fragmentClass = BlankFragment::class.java
            }

            else -> fragmentClass = null
        }
        if (fragmentClass != null) {
            try {
                val myFragment = activity.supportFragmentManager.findFragmentByTag(fragmentClass.name)
                if (myFragment != null && myFragment.isVisible) {
                    return
                }
                mFragment = fragmentClass.newInstance() as Fragment
                if (bundle != null) {
                    mFragment?.arguments = bundle
                }
                val fragmentManager = activity.supportFragmentManager
                fragmentManager.beginTransaction().replace(R.id.mContainer, mFragment!!, fragmentClass.name).commit()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
    @SuppressLint("CheckResult")
    fun getApiRequest(api: String) {
        mInteractor?.onStartApi()
        MyApplication.getApiServices()
            ._GET_REQUEST()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ json: Response<JsonObject>? ->
                when (json?.body() == null) {
                    true -> {
                        mInteractor?.onErrorApi(api, "error get checklist")
                    }
                    false -> {
                        mInteractor?.onNextApi(api, json?.body()!!)
                    }
                }
            }, { error ->
                mInteractor?.onErrorApi(api, error.toString())
            }, {
                mInteractor?.onFinishApi()
            })
    }


    @SuppressLint("CheckResult")
    fun postApiRequest(api: String, body: JsonObject) {
        mInteractor?.onStartApi()
        MyApplication.getApiServices()
            ._POST_REQUEST(body)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ json: Response<JsonObject>? ->
                when (json?.body() == null && json?.code() != 200) {
                    true -> {
                        mInteractor?.onErrorApi(api, "error get checklist")
                    }
                    false -> {
                        mInteractor?.onNextApi(api, json?.body()!!)
                    }
                }
            }, { error ->
                mInteractor?.onErrorApi(api, error.toString())
            }, {
                mInteractor?.onFinishApi()
            })
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
            .addOnSuccessListener { task->
                mInteractor?.onNextApi(api,task)
                mInteractor?.onFinishApi()
            }
            .addOnCanceledListener {
                mInteractor?.onFinishApi()
            }
            .addOnFailureListener { task->
                mInteractor?.onErrorApi(api,task.message!!)
                mInteractor?.onFinishApi()

            }
    }


}