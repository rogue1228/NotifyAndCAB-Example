package kr.co.skoop.testapplication

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.RemoteInput
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast

/**
 * Created by Administrator on 2017-10-08.
 */
class ToastActivity : AppCompatActivity() {
    val notificationManager: NotificationManager by lazy { applicationContext.getSystemService(NotificationManager::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Toast.makeText(applicationContext, getMessageText(intent), Toast.LENGTH_SHORT).show()

        notificationManager.cancel(5656)

        finish()
    }

    private fun getMessageText(intent: Intent): CharSequence? {
        val remoteInput = RemoteInput.getResultsFromIntent(intent)
        if (remoteInput != null) {
            return remoteInput.getCharSequence(Constant.KEY_TEXT_REPLY)
        }
        return null
    }
}