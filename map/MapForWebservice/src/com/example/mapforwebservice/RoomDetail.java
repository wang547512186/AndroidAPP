package com.example.mapforwebservice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RoomDetail extends Activity {

	private String roomTitleStr;
	private String priceStr;
	private TextView roomTitle;
	private TextView priceText;
	private TextView confirm_btn;
	private String hotelName;
	private String hotelAddress;
	private int startYear = 0;
	private int startMonth = 0;
	private int startDay = 0;
	private int endYear = 0;
	private int endMonth = 0;
	private int endDay = 0;
	private int dayNum = 0;
	private TextView dayNumText;
	private ImageView roomImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.room_detail);
		roomTitle = (TextView) findViewById(R.id.roomtitle);
		priceText = (TextView) findViewById(R.id.pricetext);
		dayNumText = (TextView) findViewById(R.id.dayNumText);// 天数
		Bundle bundle = this.getIntent().getExtras();
		roomTitleStr = bundle.getString("roomtype");
		hotelName = bundle.getString("hotelName");
		hotelAddress = bundle.getString("hotelAddress");
		priceStr = bundle.getString("price");
		dayNum = bundle.getInt("dayNum");
		startYear = bundle.getInt("startYear");
		startMonth = bundle.getInt("startMonth");
		startDay = bundle.getInt("startDay");
		endYear = bundle.getInt("endYear");
		endMonth = bundle.getInt("endMonth");
		endDay = bundle.getInt("endDay");

		roomTitle.setText(roomTitleStr);
		priceText.setText("￥" + priceStr);
		dayNumText.setText(dayNum + "天");
		ImageView close = (ImageView) findViewById(R.id.roomdetial_close);
		close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}

		});

		confirm_btn = (TextView) findViewById(R.id.confirm_btn);
		confirm_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (startYear == 0 || endYear == 0) {
					Toast.makeText(RoomDetail.this, "请选择日期", Toast.LENGTH_LONG)
							.show();
					RoomDetail.this.finish();
				} else {
					Intent intent = new Intent(RoomDetail.this, OrderPage.class);
					Bundle bundle = new Bundle();
					bundle.putString("hotelName", hotelName);
					bundle.putString("hotelAddress", hotelAddress);
					bundle.putString("hoteltype", roomTitleStr);
					bundle.putInt("dayNum", dayNum);
					bundle.putInt("startYear", startYear);
					bundle.putInt("startMonth", startMonth);
					bundle.putInt("startDay", startDay);
					bundle.putInt("endYear", endYear);
					bundle.putInt("endMonth", endMonth);
					bundle.putInt("endDay", endDay);
					bundle.putString("priceStr", priceStr);
					intent.putExtras(bundle);

					startActivity(intent);
					RoomDetail.this.finish();
				}

			}

		});

	}
}
