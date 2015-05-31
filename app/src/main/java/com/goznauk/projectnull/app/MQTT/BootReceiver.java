package com.goznauk.projectnull.app.MQTT;

/**
 * Created by Henry on 2015. 5. 30..
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Log.d(getClass().getCanonicalName(), "onReceive");
        context.startService(new Intent(context, MQTTservice.class));
    }
}