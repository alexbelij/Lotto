package com.casataiwan.android.lotto;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;

@SuppressLint("NewApi")
public class NumberChooserActivity extends SherlockFragmentActivity implements
		OnItemClickListener, OnClickListener {
	private GridView mNumberGridView;
	private ArrayList<Integer> chosedNumbers = new ArrayList();

	public class ImageAdapter extends BaseAdapter {
		private SherlockFragmentActivity mContext;
		private final Integer[] mThumbsIds = { R.drawable.n1, R.drawable.n2,
				R.drawable.n3, R.drawable.n4, R.drawable.n5, R.drawable.n6,
				R.drawable.n7, R.drawable.n8, R.drawable.n9, R.drawable.n10,
				R.drawable.n11, R.drawable.n12, R.drawable.n13, R.drawable.n14,
				R.drawable.n15, R.drawable.n16, R.drawable.n17, R.drawable.n18,
				R.drawable.n19, R.drawable.n20, R.drawable.n21, R.drawable.n22,
				R.drawable.n23, R.drawable.n24, R.drawable.n25, R.drawable.n26,
				R.drawable.n27, R.drawable.n28, R.drawable.n29, R.drawable.n30,
				R.drawable.n31, R.drawable.n32, R.drawable.n33, R.drawable.n34,
				R.drawable.n35, R.drawable.n36, R.drawable.n37, R.drawable.n38,
				R.drawable.n39, R.drawable.n40, R.drawable.n41, R.drawable.n42,
				R.drawable.n43, R.drawable.n44, R.drawable.n45, R.drawable.n46,
				R.drawable.n47, R.drawable.n48, R.drawable.n49 };

		public ImageAdapter(SherlockFragmentActivity c) {
			mContext = c;
		}

		@Override
		public int getCount() {
			return mThumbsIds.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView;
			if (convertView == null) {
				imageView = new ImageView(mContext);
				imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
				imageView.setPadding(10, 10, 10, 10);
			} else {
				imageView = (ImageView) convertView;
			}
			imageView.setImageResource(mThumbsIds[position]);
			return imageView;
		}
	}

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.lotto_activity_number_chooser);
		// init ivars
		mNumberGridView = (GridView) findViewById(R.id.gridViewLottoNumber);
		((Button) findViewById(R.id.lottoButtonSubmit))
				.setOnClickListener(this);

		// Prepare ActionBar
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setIcon(R.drawable.nav_logo);

		// set grid view adapter
		mNumberGridView.setAdapter(new ImageAdapter(this));
		mNumberGridView.setOnItemClickListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	private void sendLottoResult() {
		// get current selected number
		Intent it = new Intent();
		if (chosedNumbers.size() == 7) {
			// have select 7 digit
			String returnString = "";
			for (int j = 0; j < chosedNumbers.size(); j++) {
				returnString += chosedNumbers.get(j).toString();

				if ((j + 1) == chosedNumbers.size()) {
					break;
				}
				returnString += ",";
			}
			it.putExtra("lotto", returnString);
			setResult(RESULT_OK, it);
		} else {
			// not yet
			setResult(RESULT_CANCELED, it);
		}
		finish();
	}

	@Override
	public void onBackPressed() {
		sendLottoResult();
		super.onBackPressed();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		sendLottoResult();
		return super.onOptionsItemSelected(item);
	}

	/**
	 * OnItemClickListener
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View numberView,
			int position, long arg3) {
		Integer number = new Integer(position + 1);
		// check if this ball have been pressed or not
		if (chosedNumbers.contains(number)) {
			// Toast.makeText(this, R.string.string_number_chooser_warn_repeate,
			// Toast.LENGTH_SHORT).show();
			return;
		}

		if (chosedNumbers.size() < 7) {
			chosedNumbers.add(number);
			((ImageView) numberView).setAlpha(30);
		} else {
			Toast.makeText(this, R.string.string_number_chooser_warn, 500)
					.show();
		}
	}

	/**
	 * OnClickListener
	 */
	@Override
	public void onClick(View v) {
		sendLottoResult();
	}

}
