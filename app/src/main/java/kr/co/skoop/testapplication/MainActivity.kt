package kr.co.skoop.testapplication

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View

/**
 * Created by Administrator on 2017-10-08.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun notifyClick(v: View) {
        startActivity(Intent(this, NotifyActivity::class.java))
    }

    fun cabClick(v : View) {
        startActivity(Intent(this, CabActivity::class.java))
    }
}