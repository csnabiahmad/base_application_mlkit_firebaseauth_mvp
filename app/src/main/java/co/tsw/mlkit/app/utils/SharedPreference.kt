/**
 * SharedPreference
 * A class insert and update db on app.
 *
 * @author Briswell - Nguyen Van Tu
 * @version 1.0 2016-08-12
 */
package co.tsw.mlkit.app.utils


import android.content.Context
import android.content.SharedPreferences
import co.tsw.mlkit.app.common.Constants


class SharedPreference
/**
 * Private constructor, force user to call getInstance
 */
private constructor() {

    companion object {

        private val mInstance = SharedPreference()
        private var mSharedPreferences: SharedPreferences? = null
        private var mEditor: SharedPreferences.Editor? = null
        private var mContext: Context? = null

        /**
         * The only way to get the only one instance
         *
         * @param context
         * @return
         */
        @Synchronized
        fun getInstance(context: Context): SharedPreference {
            if (mContext == null) {
                mContext = context
            }
            if (null == mSharedPreferences) {
                mSharedPreferences = mContext!!.getSharedPreferences(
                    Constants.SHARED_PREFERENCES_KEY,
                        Context.MODE_PRIVATE)
            }
            return mInstance
        }

        fun clearSharedPreferences() {
            if (mSharedPreferences != null) {
                mEditor = mSharedPreferences!!.edit()
                mEditor!!.clear()
                mEditor!!.commit()
            }
            mSharedPreferences = null
            mContext = null
        }
    }

    fun removeAllData() {
        if (mSharedPreferences != null) {
            mEditor = mSharedPreferences!!.edit()
            mEditor!!.clear()
            mEditor!!.commit()
        }
    }

    fun saveData(key: String, data: String) {
        mEditor = mSharedPreferences!!.edit()
        mEditor!!.putString(key, data)
        mEditor!!.commit()
    }

    fun saveData(key: String, data: Boolean) {
        mEditor = mSharedPreferences!!.edit()
        mEditor!!.putBoolean(key, data)
        mEditor!!.commit()
    }

    fun getData(key: String): String? {
        return mSharedPreferences!!.getString(key, null)
    }

    fun getDataBoolean(key: String): Boolean? {
        return mSharedPreferences!!.getBoolean(key, false)
    }

    fun removeData(key: String) {
        mEditor = mSharedPreferences!!.edit()
        mEditor!!.remove(key)
        mEditor!!.commit()
    }


}