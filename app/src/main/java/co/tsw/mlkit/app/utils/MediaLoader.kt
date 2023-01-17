package co.tsw.mlkit.app.utils

import android.widget.ImageView
import co.tsw.mlkit.app.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.AlbumLoader

class MediaLoader : AlbumLoader {
    override fun load(imageView: ImageView?, albumFile: AlbumFile?) {
        load(imageView, albumFile?.path)

    }

    override fun load(imageView: ImageView?, url: String?) {
        Glide.with(imageView!!.context)
                .load(url)
                .error(R.drawable.icon_placeholder)
                .placeholder(R.drawable.icon_placeholder)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView)
    }
}