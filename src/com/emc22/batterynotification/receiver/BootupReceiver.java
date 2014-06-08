package com.emc22.batterynotification.receiver;

import com.emc22.batterynotification.service.BatteryNotificationService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BootupReceiver extends BroadcastReceiver {
	private static String sTag = "BootupReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		Toast.makeText(context, "Bootup complete", Toast.LENGTH_SHORT).show();
		Log.d(sTag, "Bootup complete");
		Intent i = new Intent(context, BatteryNotificationService.class);
		context.startService(i);

	}

}
