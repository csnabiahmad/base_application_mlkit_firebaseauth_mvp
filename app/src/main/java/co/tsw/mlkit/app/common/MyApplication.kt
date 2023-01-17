package co.tsw.mlkit.app.common

import android.app.Application
import co.tsw.mlkit.app.network.ApiClient
import co.tsw.checklist.network.ApiServices
import co.tsw.mlkit.app.utils.MediaLoader
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumConfig
import java.util.Locale

class MyApplication : Application() {


    companion object {
        var mApplication: MyApplication? = null
        var mApiServices: ApiServices? = null

        fun getInstance(): MyApplication? {
            return mApplication
        }



        fun getApiServices(): ApiServices {
            if (mApiServices == null) {
                mApiServices = ApiClient.createServices()
            }
            return mApiServices as ApiServices
        }
    }


    override fun onCreate() {
        super.onCreate()
        mApplication = this

        Album.initialize(
            AlbumConfig.newBuilder(this)
                .setAlbumLoader(MediaLoader())
                .setLocale(Locale.getDefault())
                .build()
        )
    }
}