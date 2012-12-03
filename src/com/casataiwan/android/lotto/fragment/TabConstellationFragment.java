package com.casataiwan.android.lotto.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.casataiwan.android.lotto.R;
import com.casataiwan.android.lotto.utils.Utility;
import com.casataiwan.android.lotto.utils.Utility.LottoTicket;

public class TabConstellationFragment extends SherlockFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.lotto_fragment_tab_constellation,
				container, false);
	}

	@Override
	public void onResume() {
		super.onResume();
		LottoTicket ticket = Utility.createLottoTicket();
		Activity ctx = getActivity();
		// every time switch to this fragment, refresh the lotto list.
		Utility.renderTicketIntoViewGroup(ticket, (ViewGroup) ctx
				.findViewById(R.id.lottoTicket_constellation_normal),
				(ViewGroup) ctx
						.findViewById(R.id.lottoTicket_constellation_special));
	}
}
