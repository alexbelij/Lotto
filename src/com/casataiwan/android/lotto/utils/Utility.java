package com.casataiwan.android.lotto.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import com.casataiwan.android.lotto.R;

import android.R.integer;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Utility {
	private static int lottoPool = 49;

	public static class LottoTicket {
		public final ArrayList<Integer> normalNumbers;
		public final Integer specialNember;

		public LottoTicket(ArrayList<Integer> n, Integer s) {
			normalNumbers = n;
			specialNember = s;
		}

		public Boolean isBingo(LottoTicket newTicket) {
			Boolean isMatch = false;
			for (Integer num : newTicket.normalNumbers) {
				if (normalNumbers.contains(num)) {
					isMatch = true;
				}
			}
			return ((newTicket.specialNember == specialNember) || isMatch);
		}

		public Boolean isMatchNormal(Integer num) {
			return normalNumbers.contains(num);
		}
		public Boolean isMatchSpecial(Integer num) {
			return (num == specialNember);
		}
	}

	public static String getFormattedDate() {
		// return YYY/MM/DD

		Calendar cal = Calendar.getInstance();
		// Get Mingguo year
		int year = cal.get(Calendar.YEAR) - 1911;
		int month = cal.get(Calendar.MONTH) + 1;
		int date = cal.get(Calendar.DATE);
		return year + "/" + month + "/" + date;
	}

	public static LottoTicket createLottoTicket() {
		// Create a empty millionaire list
		ArrayList<Integer> millionaire = new ArrayList<Integer>(7);
		// Define ArrayList to hold Integer objects
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		for (int i = 0; i < lottoPool; i++) {
			numbers.add(i + 1);
		}
		Collections.shuffle(numbers);
		for (int j = 0; j < 7; j++) {
			millionaire.add(numbers.get(j));
		}
		Integer specialNumber = (Integer) millionaire
				.remove(millionaire.size() - 1);
		return new LottoTicket(millionaire, specialNumber);
	}

	public static void renderTicketIntoViewGroup(LottoTicket prize,
			LottoTicket ticket, ViewGroup lottoNormal, ViewGroup lottoSpecial) {
		int count = 0;

		// render Lotto number normal into layout
		for (int i = 0; i < lottoNormal.getChildCount(); i++) {
			View child = lottoNormal.getChildAt(i);
			if (child instanceof TextView) {
				TextView tv = ((TextView) child);
				int num = ticket.normalNumbers.get(count).intValue();
				
				tv.setText(num + "");
				if (prize.isMatchNormal(num)) {
					tv.setBackgroundResource(R.drawable.choose_circle);
				}
				count++;
			}
		}
		// render Lotto number special into layout
		for (int i = 0; i < lottoSpecial.getChildCount(); i++) {
			View child = lottoSpecial.getChildAt(i);
			if (child instanceof TextView) {
				TextView tv = ((TextView) child);
				int num = ticket.specialNember.intValue();
				
				tv.setText(num + "");
				if (prize.isMatchSpecial(num)) {
					tv.setBackgroundResource(R.drawable.choose_circle);
				}
			}
		}
	}

	public static void renderTicketIntoViewGroup(LottoTicket ticket,
			ViewGroup lottoNormal, ViewGroup lottoSpecial) {
		int count = 0;

		// render Lotto number normal into layout
		for (int i = 0; i < lottoNormal.getChildCount(); i++) {
			View child = lottoNormal.getChildAt(i);
			if (child instanceof TextView) {
				((TextView) child).setText(ticket.normalNumbers.get(count)
						.intValue() + "");
				count++;
			}
		}
		// render Lotto number special into layout
		for (int i = 0; i < lottoSpecial.getChildCount(); i++) {
			View child = lottoSpecial.getChildAt(i);
			if (child instanceof TextView) {
				((TextView) child).setText(ticket.specialNember + "");
				count++;
			}
		}
	}
}
