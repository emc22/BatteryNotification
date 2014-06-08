package com.emc22.batterynotification.activity;

import com.emc22.batterynotification.R;
import com.emc22.batterynotification.R.layout;
import com.emc22.batterynotification.R.raw;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

public class AlarmActivity extends Activity {
	private MediaPlayer mediaPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm);
		mediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.alarm);
		mediaPlayer.setLooping(true);

	}

	@Override
	protected void onResume() {
		super.onResume();
		mediaPlayer.start();
	}

	public void onClick(View view) {
		finish();
	}

	@Override
	protected void onDestroy() {
		mediaPlayer.stop();
		mediaPlayer.release();
		super.onDestroy();
	}
}
