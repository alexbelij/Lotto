package com.casataiwan.android.lotto;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class SplashActivity extends SherlockFragmentActivity {
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);

		// Set view
		setContentView(R.layout.lotto_activity_splash);
	}

	@Override
	protected void onResume() {
		super.onResume();

		// Create Handler to postpone the entry time, currently 2s
		Handler handler = new Handler();
		handler.postDelayed(new SplashHandler(), 2000);
	}

	// A Runnable thread which determine the next activity to go.
	private class SplashHandler implements Runnable {
		@Override
		public void run() {
			// Go to Main Tab Activity
			Intent intent = new Intent(SplashActivity.this,
					TabMainActivity.class);
			startActivity(intent);
			finish();
		}
	}

}
