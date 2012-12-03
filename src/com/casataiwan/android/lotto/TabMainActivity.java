package com.casataiwan.android.lotto;

import android.os.Bundle;
import android.provider.SyncStateContract.Constants;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.casataiwan.android.lotto.fragment.TabAnalyticsFragment;
import com.casataiwan.android.lotto.fragment.TabConstellationFragment;
import com.casataiwan.android.lotto.fragment.TabNumberFragment;

public class TabMainActivity extends SherlockFragmentActivity {
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);

		// Prepare ActionBar
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		// Remove Activity Title
		actionBar.setDisplayShowTitleEnabled(false);
		// Modify icon from system icon to our activity specific icon
		actionBar.setIcon(R.drawable.nav_logo);

		// Prepare Generate Tab Bar
		Tab tab = null;
		String title = null;

		// Tab Number
		tab = actionBar.newTab();
		title = (String) this.getResources()
				.getText(R.string.string_tab_number);
		tab.setCustomView(R.layout.lotto_tab_number);
		tab.setTabListener(new TabListener<TabNumberFragment>(this, title,
				TabNumberFragment.class));
		actionBar.addTab(tab);

		// Tab Analytics
		tab = actionBar.newTab();
		title = (String) this.getResources().getText(
				R.string.string_tab_analytics);
		tab.setCustomView(R.layout.lotto_tab_analytics);
		tab.setTabListener(new TabListener<TabAnalyticsFragment>(this, title,
				TabAnalyticsFragment.class));
		actionBar.addTab(tab);

		// Tab Constellation
		tab = actionBar.newTab();
		title = (String) this.getResources().getText(
				R.string.string_tab_constellation);
		tab.setCustomView(R.layout.lotto_tab_constellation);
		tab.setTabListener(new TabListener<TabConstellationFragment>(this,
				title, TabConstellationFragment.class));
		actionBar.addTab(tab);
	}

	private class TabListener<T extends SherlockFragment> implements
			ActionBar.TabListener {
		private SherlockFragment mFragment;
		private final SherlockFragmentActivity mActivity;
		private final String mTag;
		private final Class<T> mClass;

		/**
		 * Constructor used each time a new tab is created.
		 * 
		 * @param activity
		 *            The host Activity, used to instantiate the fragment
		 * @param tag
		 *            The identifier tag for the fragment
		 * @param clz
		 *            The fragment's Class, used to instantiate the fragment
		 */
		public TabListener(SherlockFragmentActivity activity, String tag,
				Class<T> clz) {
			mActivity = activity;
			mTag = tag;
			mClass = clz;
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			// Check if the fragment is already initialized
			if (mFragment == null) {
				// If not, instantiate and add it to the activity
				mFragment = (SherlockFragment) SherlockFragment.instantiate(
						mActivity, mClass.getName());
				ft.add(android.R.id.content, mFragment, mTag);
			} else {
				// If it exists, simply attach it in order to show it
				ft.attach(mFragment);
			}
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			if (mFragment != null) {
				// Detach the fragment, because another one is being attached
				ft.detach(mFragment);
			}
		}

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// User selected the already selected tab. Usually do nothing.
		}
	}

}
