package com.sjl.exercise.ui.remote;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;

import com.sjl.exercise.R;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Implementation of App Widget functionality.
 */
public class MyAppWidget extends AppWidgetProvider {
    private static final String TAG = "MyAppWidget";
    public static final String ACTION_CLICK = "action_click";

    private static Set appWidgetIdSet = new HashSet();

    private static int cnt = 0;

    /**
     * 广播接收处理事件,我们的主要逻辑就需要在这里处理了
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (TextUtils.equals(intent.getAction(), ACTION_CLICK)) { //当intent不为空，且action匹配成功时，就接收广播，然后点击事件成功
            Log.e(TAG, "is clicked");
            cnt++;
            updateAllAppWidgets(context, AppWidgetManager.getInstance(context), appWidgetIdSet);
        }
    }

    private void updateAllAppWidgets(Context context, AppWidgetManager appWidgetManager, Set appWidgetIdSet) {
        Iterator it = appWidgetIdSet.iterator();
        while (it.hasNext()) {
            updateAppWidget(context, appWidgetManager, ((Integer) it.next()).intValue());
        }
    }

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        // Construct the RemoteViews object
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.my_app_widget);
        remoteViews.setTextViewText(R.id.tv_app_widget, "点击" + cnt);

        Intent intent = new Intent(ACTION_CLICK);
        intent.setPackage(context.getPackageName());
        PendingIntent clickIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        remoteViews.setOnClickPendingIntent(R.id.btn_click, clickIntent);

        PendingIntent inIntent = PendingIntent.getActivity(context, 0, new Intent(context, AppWidgetProviderActivity.class), 0);
        remoteViews.setOnClickPendingIntent(R.id.btn_in, inIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    /**
     * 当widget被初次添加或者当widget 的大小被改变时，被调用
     */
    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
        Log.e(TAG, "onAppWidgetOptionsChanged");
        updateAppWidget(context,appWidgetManager,appWidgetId);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.e(TAG, "onUpdate");
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            appWidgetIdSet.add(appWidgetId);
        }
    }

    /**
     * 第一个widget被创建时调用
     */
    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        Log.e(TAG, "onEnabled");
    }

    /**
     * 最后一个widget被删除时调用
     */
    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        Log.e(TAG, "onDisabled");
    }

    /**
     * widget被删除时调用
     */
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        Log.e(TAG, "onDeleted");
        for (int appWidgetId : appWidgetIds) {
            appWidgetIdSet.remove(appWidgetId);
        }
        super.onDeleted(context, appWidgetIds);
    }
}

