package com.casataiwan.android.lotto.fragment;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.casataiwan.android.lotto.NumberChooserActivity;
import com.casataiwan.android.lotto.R;
import com.casataiwan.android.lotto.utils.Utility;
import com.casataiwan.android.lotto.utils.Utility.LottoTicket;

public class TabNumberFragment extends SherlockFragment implements
		OnClickListener {
	final static int LOTTO_NUMBER_REQUET = 33456;
	private SherlockFragmentActivity ctx;
	private View view;
	private TextView mDate;
	private TextView mTicketCouter;
	private TextView mTicketWin;
	private ViewGroup mTicketWrapperWinNormal;
	private ViewGroup mTicketWrapperWinSpecial;
	private ViewGroup mTicketWrapper;

	private int ticketCouter = 0;
	private int ticketWin = 0;
	private LottoTicket lottoTicketPrize;
	private ArrayList<LottoTicket> myLottoTickets;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		lottoTicketPrize = Utility.createLottoTicket();
		myLottoTickets = new ArrayList<Utility.LottoTicket>(0);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// init attributes
		ctx = (SherlockFragmentActivity) getActivity();
		view = inflater.inflate(R.layout.lotto_fragment_tab_number, container,
				false);
		mTicketCouter = (TextView) view.findViewById(R.id.textViewCounter);
		mTicketWin = (TextView) view.findViewById(R.id.textViewWin);
		mTicketWrapper = (LinearLayout) view.findViewById(R.id.lottoWrapper);
		mTicketWrapperWinNormal = (ViewGroup) view
				.findViewById(R.id.lottoTicket_number_normal);
		mTicketWrapperWinSpecial = (ViewGroup) view
				.findViewById(R.id.lottoTicket_number_special);
		mDate = (TextView) view.findViewById(R.id.textViewDate);

		// bind button listener
		view.findViewById(R.id.lottoButton).setOnClickListener(this);

		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		// update status
		mDate.setText(Utility.getFormattedDate());
		mTicketCouter.setText(ticketCouter + "");
		mTicketWin.setText(ticketWin + "");

		// update Lotto prize
		Utility.renderTicketIntoViewGroup(lottoTicketPrize,
				mTicketWrapperWinNormal, mTicketWrapperWinSpecial);

		// refresh Lotto buyed
		mTicketWrapper.removeAllViews();
		for (int i = 0; i < myLottoTickets.size(); i++) {
			renderTicket(myLottoTickets.get(i));
		}
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	private void renderTicket(LottoTicket ticket) {
		ViewGroup ticketWrapper = null;

		// get inflater
		LayoutInflater layoutInflater = (LayoutInflater) getActivity()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// create ticket wrapper
		ticketWrapper = (ViewGroup) layoutInflater.inflate(
				R.layout.lotto_component_ticket, mTicketWrapper, false);
		// render ticket
		Utility.renderTicketIntoViewGroup(lottoTicketPrize, ticket,
				(ViewGroup) ticketWrapper
						.findViewById(R.id.lottoTicket_component_normal),
				(ViewGroup) ticketWrapper
						.findViewById(R.id.lottoTicket_component_special));
		// append ticket
		mTicketWrapper.addView(ticketWrapper);
	}

	/**
	 * onActivityResult
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == LOTTO_NUMBER_REQUET) {
			if (resultCode == ctx.RESULT_OK) {
				// processing return string
				String lottoString = data.getStringExtra("lotto");
				ArrayList<Integer> lottoList = new ArrayList<Integer>(7);
				String[] lottoStringSplitted = lottoString.split(",");
				for (int i = 0; i < lottoStringSplitted.length; i++) {
					lottoList.add(i, new Integer(lottoStringSplitted[i]));
				}
				Integer special = lottoList.remove(lottoList.size() - 1);
				// proccessing end

				LottoTicket newLottoTicket = new Utility.LottoTicket(lottoList,
						special);
				renderTicket(newLottoTicket);

				// increase ticket counter
				ticketCouter++;
				mTicketCouter.setText(ticketCouter + "");
				// increase ticket win if bingo
				if (lottoTicketPrize.isBingo(newLottoTicket)) {
					ticketWin++;
				}
				mTicketWin.setText(ticketWin + "");

				// push new ticket
				myLottoTickets.add(newLottoTicket);
			} else {

			}
		}
	}

	/**
	 * onClickListener
	 */
	@Override
	public void onClick(View v) {
		// start number picker activity
		Intent it = new Intent(ctx, NumberChooserActivity.class);
		startActivityForResult(it, LOTTO_NUMBER_REQUET);
	}

}
