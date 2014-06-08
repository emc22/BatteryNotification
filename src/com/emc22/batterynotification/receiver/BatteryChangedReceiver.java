package com.emc22.batterynotification.receiver;

import com.emc22.batterynotification.model.BatteryModel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BatteryChangedReceiver extends BroadcastReceiver {

	private BatteryModel mBattery;

	public BatteryChangedReceiver(BatteryModel battery) {
		mBattery = battery;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		mBattery.update(intent);
	}
	
	

}
