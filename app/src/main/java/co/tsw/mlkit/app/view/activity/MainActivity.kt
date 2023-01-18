package co.tsw.mlkit.app.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import co.tsw.mlkit.app.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() , View.OnClickListener {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        cardViewTextDetection.setOnClickListener(this)


    }


    override fun onDetachedFromWindow() {
        mProgressDlg?.hide()
        super.onDetachedFromWindow()
    }


    override fun onClick(v: View?) {
        if (v!!.id == R.id.cardViewTextDetection){
            startActivity(Intent(this@MainActivity,TextDetectionActivity::class.java))
        }
    }


}