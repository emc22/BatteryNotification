package com.emc22.batterynotification.model;

import com.emc22.batterynotification.listener.OnChangeListener;
import com.emc22.batterynotification.listener.OnDropListener;
import com.emc22.batterynotification.listener.OnRiseListener;

import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;

public class BatteryModel {
	private static String sTag = "BatteryModel";
	private int currentLevel;
	private int previousLevel;
	private boolean isCharging;
	private OnChangeListener mOnChangeListener;
	private OnDropListener mOnDropListener;
	private OnRiseListener mOnRiseListener;

	public BatteryModel(Intent intent) {
		isCharging = updateChargingState(intent);
		currentLevel = updateLevel(intent);
	}

	public void update(Intent intent) {

		isCharging = updateChargingState(intent);
		previousLevel = currentLevel;
		currentLevel = updateLevel(intent);

		if (previousLevel != currentLevel) {

			if (mOnChangeListener != null) {
				mOnChangeListener.onChange();
			}

			if (previousLevel > currentLevel && mOnDropListener != null) {
				mOnDropListener.onDrop();
			} else if (previousLevel < currentLevel && mOnDropListener != null) {
				mOnRiseListener.onRise();
			}

		}

	}

	private boolean updateChargingState(Intent intent) {
		int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
		return (status == BatteryManager.BATTERY_STATUS_CHARGING)
				|| (status == BatteryManager.BATTERY_STATUS_FULL);
	}

	private int updateLevel(Intent intent) {
		int lvl = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
		int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
		return (int) ((lvl / (float) scale) * 100);
	}

	public boolean isCharging() {
		return isCharging;
	}

	public int getLevel() {
		return currentLevel;
	}

	public void setOnChangeListener(OnChangeListener listener) {
		mOnChangeListener = listener;
	}

	public void setOnDropListener(OnDropListener listener) {
		mOnDropListener = listener;
	}

	public void setOnRiseListener(OnRiseListener listener) {
		mOnRiseListener = listener;
	}
}
