package com.catail.lib_commons.utils.calendarselection;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.catail.lib_commons.R;
import com.catail.lib_commons.utils.Global;

import java.util.ArrayList;
import java.util.List;

public class CalendarSelectionActivity extends FragmentActivity {
	private ImageView iv_left;
	private ImageView iv_right;
	private TextView tv_date;
	private TextView tv_week;
	private MonthDateView monthDateView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		List<Integer> list = new ArrayList<Integer>();
		list.add(10);
		list.add(12);
		list.add(15);
		list.add(16);
		setContentView(R.layout.activity_calendarselection);
		iv_left =  findViewById(R.id.iv_left);
		iv_right =  findViewById(R.id.iv_right);
		monthDateView =  findViewById(R.id.monthDateView);
		tv_date =  findViewById(R.id.date_text);
		tv_week  = findViewById(R.id.week_text);
		monthDateView.setTextView(tv_date,tv_week);
		monthDateView.setDaysHasThingList(list);
		monthDateView.setDateClick(() -> {
			Log.e("日期", monthDateView.getmSelYear()+"-"+
					(monthDateView.getmSelMonth()+1)+"-"+monthDateView.getmSelDay());
			String mDay="";
			int day=monthDateView.getmSelDay();
			if (day < 10) {
				mDay = "0" + day;
			} else {
				mDay = day + "";
			}
			String mMon="";
			int mon=monthDateView.getmSelMonth()+1;
			if (mon < 10) {
				mMon = "0" + mon;
			} else {
				mMon = mon + "";
			}
			String selectionDay= monthDateView.getmSelYear()+"-"+mMon+"-"+mDay;
			Intent data=new Intent();
			data.putExtra("filterDate", selectionDay);
			setResult(Global.FilterCalendarDate, data);
			CalendarSelectionActivity.this.finish();
		});
		setOnlistener();
	}
	
	private void setOnlistener(){
		iv_left.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				monthDateView.onLeftClick();
			}
		});
		
		iv_right.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				monthDateView.onRightClick();
			}
		});
		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
			// 这样写可以让界面销毁的时候不闪烁.
			Intent data = new Intent();
			data.putExtra("filterDate","1");
			setResult(0, data);
			CalendarSelectionActivity.this.overridePendingTransition(0, R.anim.next_exit);

	}
	
}
