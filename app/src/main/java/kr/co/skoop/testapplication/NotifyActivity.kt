package kr.co.skoop.testapplication

import android.app.Notification
import android.app.NotificationManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.BitmapDrawableResource
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import java.lang.Exception
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationManagerCompat
import android.support.v4.app.RemoteInput
import android.widget.RemoteViews
import android.widget.Toast


/**
 * Created by Administrator on 2017-10-08.
 */
class NotifyActivity : AppCompatActivity() {
    val notificationManager: NotificationManagerCompat by lazy { NotificationManagerCompat.from(applicationContext) }
    var id: Int = 0
    var groupId: Int = 0
    val NOTIFICATION_GROUP_KEY = "group"
    var inboxStyle = NotificationCompat.InboxStyle()
    val REPLY_INTENT_ID = 1222
    val LABEL_REPLY = "입력해 주세요."
    val REPLY_ACTION = "REPLY_ACTION"
    val CONVERSATION_LABEL = "CONVERSATION_LABEL"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acticity_notify)

    }

    fun defaultNotification(v: View) {
        Log.d("NotifyActivity", "click")

        val builder = NotificationCompat.Builder(applicationContext)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("ContentTitle")
                .setContentText("ContentText")
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
                .setAutoCancel(true)


        notificationManager.notify(1234, builder.build())

    }

    fun inboxNotification(v: View) {
        ++id

        val builder = NotificationCompat.Builder(applicationContext)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)

        if (id == 1) {
            builder.setContentTitle("ContentTitle")
                    .setContentText("ContentText - $id")
        } else {
            builder.setContentTitle("$id Messages")
                    .setContentText("Drag to below.")
        }

        inboxStyle.setBigContentTitle("ContentTitle - $id")
        inboxStyle.addLine("ContentText - $id")
        builder.setStyle(inboxStyle)

        notificationManager.notify(4321, builder.build())
    }

    fun actionNotification(v: View) {
        val replyIntent = PendingIntent.getActivity(applicationContext,
                REPLY_INTENT_ID,
                getDirectReplyIntent(this, LABEL_REPLY),
                PendingIntent.FLAG_UPDATE_CURRENT)

        val remoteInput = RemoteInput.Builder(Constant.KEY_TEXT_REPLY)
                .setLabel(LABEL_REPLY)
                .build()


        val builder = NotificationCompat.Builder(applicationContext)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("ContentTitle")
                .setContentText("ContentText - $id")
                .setAutoCancel(true)

        var replyAction = NotificationCompat.Action.Builder(R.mipmap.ic_launcher, "답장", replyIntent)
                .addRemoteInput(remoteInput)
                .build()

        builder.addAction(replyAction)

        notificationManager.notify(5656, builder.build())
    }

    fun groupNotification(v: View) {
        ++groupId

        val groupBuilder = NotificationCompat.Builder(applicationContext)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("GroupTitle")
                .setContentText("ContentText")
                .setAutoCancel(true)
                .setGroupSummary(true) // 그룹 파렌트 노티 필수
                .setGroup(NOTIFICATION_GROUP_KEY)
                .setStyle(NotificationCompat.BigTextStyle().bigText("ContentText"))

        notificationManager.notify(6543, groupBuilder.build())

        for (i in 1..groupId) {
            val builder = NotificationCompat.Builder(applicationContext)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("ContentTitle")
                    .setContentText("ContentText - $i")
                    .setAutoCancel(true)
                    .setGroup(NOTIFICATION_GROUP_KEY)
                    .setStyle(NotificationCompat.BigTextStyle().bigText("ContentText - $i"))

            notificationManager.notify(6544 + i, builder.build())
        }
    }

    fun expandNotification(v: View) {
        val remoteViews = RemoteViews(applicationContext.packageName, R.layout.notify_layout)
        remoteViews.setImageViewBitmap(R.id.ivImage, BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))

        val builder = NotificationCompat.Builder(applicationContext)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setCustomBigContentView(remoteViews)
                .setStyle(NotificationCompat.DecoratedCustomViewStyle()) // 노티 상단 표시용 스타일
                .setAutoCancel(true)

        notificationManager.notify(6789, builder.build())
    }

    fun headUpNotification(v: View) {
        val builder = NotificationCompat.Builder(applicationContext)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("ContentTitle")
                .setContentText("ContentText")
                .setAutoCancel(true)
                .setPriority(Int.MAX_VALUE)

        val push = Intent().apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            setClass(this@NotifyActivity, ToastActivity::class.java)
        }

        val fullScreenPending = PendingIntent.getActivity(this, 0, push, PendingIntent.FLAG_CANCEL_CURRENT)
        builder.setFullScreenIntent(fullScreenPending, true)

        notificationManager.notify(7890, builder.build())
    }


    private fun getDirectReplyIntent(context: Context, label: String): Intent {
        return Intent(context, ToastActivity::class.java).apply {
            addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
            setAction(REPLY_ACTION)
            putExtra(CONVERSATION_LABEL, label)
        }
    }
}