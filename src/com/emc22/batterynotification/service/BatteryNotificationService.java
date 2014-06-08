package com.emc22.batterynotification.service;

import com.emc22.batterynotification.activity.AlarmActivity;
import com.emc22.batterynotification.listener.OnChangeListener;
import com.emc22.batterynotification.listener.OnRiseListener;
import com.emc22.batterynotification.model.BatteryModel;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class BatteryNotificationService extends Service {

	private static String sTag = "BatteryNotificationService";
	private static int sNotificationId = 12345;

	private BatteryModel mBattery;

	private BroadcastReceiver mBatteryLevelChangeReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			mBattery.update(intent);
			if (mBattery.isCharging() && mBattery.getLevel() > 90) {
				activateAlarm();
			}

		}

	};

	private OnChangeListener mOnChangeListener = new OnChangeListener() {

		@Override
		public void onChange() {
			Notification notification = createNotification(mBattery.getLevel(),
					mBattery.isCharging());
			NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			notificationManager.notify(sNotificationId, notification);
		}
	};

//	private OnRiseListener mOnRiseListener = new OnRiseListener() {
//
//		@Override
//		public void onRise() {
//			if (mBattery.isCharging() && mBattery.getLevel() > 90) {
//				activateAlarm();
//			}
//		}
//	};

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		// TODO: activate alarm if charging above 90%

		IntentFilter intentFilter = new IntentFilter(
				Intent.ACTION_BATTERY_CHANGED);
		Intent batteryChangeIntent = getApplicationContext().registerReceiver(
				mBatteryLevelChangeReceiver, intentFilter);
		Log.d(sTag, "Registered receiver");

		mBattery = new BatteryModel(batteryChangeIntent);
		mBattery.setOnChangeListener(mOnChangeListener);

		Notification notification = createNotification(mBattery.getLevel(),
				mBattery.isCharging());
		startForeground(sNotificationId, notification);

		if (mBattery.isCharging() && mBattery.getLevel() > 90) {
			activateAlarm();
		}

		return START_STICKY;
	}

	private Notification createNotification(int level, boolean isCharging) {

		int r = getResources().getIdentifier(
				"ic_stat_" + String.format("%02d", level), "drawable",
				getPackageName());

		String title = isCharging ? "Charging" : "Not charging";

		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				getBaseContext()).setSmallIcon(r).setContentTitle(title)
				.setOngoing(true);

		return builder.build();
	}

	private void activateAlarm() {
		Intent activityIntent = new Intent(getApplicationContext(),
				AlarmActivity.class);
		activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(activityIntent);
	}

}
