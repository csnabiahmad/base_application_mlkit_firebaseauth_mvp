package co.tsw.mlkit.app.utils

import android.app.Activity
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

fun Activity.toast(msg: String){
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}
object FirebaseUtils {
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val firebaseUser: FirebaseUser? = firebaseAuth.currentUser
}