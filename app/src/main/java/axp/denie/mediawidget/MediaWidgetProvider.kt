package axp.denie.mediawidget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import java.net.URI
import java.util.*

class MediaWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        appWidgetIds.forEach { appWidgetId ->
            val pendingIntent = Intent(context, MainActivity::class.java)
                .let { intent ->
                    PendingIntent.getActivity(context, 0, intent, 0)
                }

            val view = RemoteViews(context?.packageName, R.layout.media_widget_layout)
                //.apply { setOnClickPendingIntent(R.id.center_image, pendingIntent) }
            setMusicIntent(context, view)
            setPhotoIntent(context, view)
            setVideoIntent(context, view)
            view.setInt(R.id.widget_layout, "setBackgroundResource", R.drawable.widget_plate)
            appWidgetManager.updateAppWidget(appWidgetId, view)
        }
     
    }

    private fun setMusicIntent(paramContext: Context?, paramRemoteViews: RemoteViews){
        paramRemoteViews.setOnClickPendingIntent(R.id.music_icon, PendingIntent.getActivity(paramContext, 0, Intent("android.intent.action.MUSIC_PLAYER"), 0))
    }

    private fun setPhotoIntent(paramContext: Context?, paramRemoteViews: RemoteViews){
        val localIntent = Intent(Intent.ACTION_MAIN)
                .apply{
                    addCategory(Intent.CATEGORY_LAUNCHER)
                    component = ComponentName("com.sonyericsson.album", "com.sonyericsson.album.MainActivity")
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK //how 335544320 turned to this flag: https://stackoverflow.com/questions/52390129/android-intent-setflags-issue
                }
        paramRemoteViews.setOnClickPendingIntent(R.id.center_image, PendingIntent.getActivity(paramContext, 0, localIntent, 0))
    }

    private fun setVideoIntent(paramContext: Context?, paramRemoteViews: RemoteViews){
        val localIntent = Intent(Intent.ACTION_VIEW)
                .apply {
                    addCategory(Intent.CATEGORY_DEFAULT)
                    putExtra("com.sonyericsson.album.intent.extra.SCREEN_NAME", "videos")
                    component = ComponentName("com.sonyericsson.album", "com.sonyericsson.album.MainActivity")
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
        paramRemoteViews.setOnClickPendingIntent(R.id.video_icon, PendingIntent.getActivity(paramContext, 0, localIntent, 0))
    }

}