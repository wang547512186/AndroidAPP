package com.example.mapforwebservice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class more_information extends FragmentActivity {
	private ImageButton imgbt;
	private View indicator;
	private FragmentTabHost mTabHost = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more_information);
		imgbt = (ImageButton) findViewById(R.id.more_button);
		imgbt.setOnClickListener(new ImgBtOnClickListener());

		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		// mTabHost.getCurrentTabView().setBackgroundColor(Color.RED);

		indicator = getIndicatorView("全部", R.layout.near);
		mTabHost.addTab(mTabHost.newTabSpec("0").setIndicator(indicator),
				HotelList.class, null);
		indicator = getIndicatorView("酒店", R.layout.classify);
		mTabHost.addTab(mTabHost.newTabSpec("1").setIndicator(indicator),
				HotelList1.class, null);
		indicator = getIndicatorView("其他", R.layout.pay);
		mTabHost.addTab(mTabHost.newTabSpec("2").setIndicator(indicator),
				HotelList2.class, null);
		indicator = getIndicatorView("个人中心", R.layout.user);
		mTabHost.addTab(mTabHost.newTabSpec("3").setIndicator(indicator),
				NewsFragment.class, null);
		mTabHost.getTabWidget().getChildAt(3)
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Intent intent = new Intent();
						intent.setClass(more_information.this,
								User_information.class);
						startActivity(intent);
						more_information.this.finish();
					}

				});
	}

	class ImgBtOnClickListener implements OnClickListener {
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(more_information.this, MainActivity.class);
			startActivity(intent);
			more_information.this.finish();
		}
	}

	private View getIndicatorView(String name, int layoutId) {
		View v = getLayoutInflater().inflate(layoutId, null);
		TextView tv = (TextView) v.findViewById(R.id.hotelname);
		tv.setText(name);   
		return v;
	}

	public boolean onKeyDown(int KeyCode, KeyEvent event) {
		if (KeyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent();
			intent.setClass(more_information.this, MainActivity.class);
			startActivity(intent);
			more_information.this.finish();
		}
		return false;
	}

}
