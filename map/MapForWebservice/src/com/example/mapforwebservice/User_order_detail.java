package com.example.mapforwebservice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class User_order_detail extends Activity {
	private TextView hotelNameText;
	private TextView roomStyleText;
	private TextView addressHotelText;
	private TextView beginDateText;
	private TextView endDateText;
	private TextView customerNameText;
	private TextView mobilePhoneText;
	private TextView orderPriceText;
	private TextView orderId;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_detail);
		hotelNameText = (TextView) findViewById(R.id.hotelNameText);
		roomStyleText = (TextView) findViewById(R.id.roomStyleText);
		addressHotelText = (TextView) findViewById(R.id.addressHotelText);
		beginDateText = (TextView) findViewById(R.id.beginDateText);
		endDateText = (TextView) findViewById(R.id.endDateText);
		customerNameText = (TextView) findViewById(R.id.customerNameText);
		mobilePhoneText = (TextView) findViewById(R.id.mobilePhoneText);
		orderPriceText = (TextView) findViewById(R.id.orderPriceText);
		orderId = (TextView) findViewById(R.id.orderId);
		
		Bundle bundle = this.getIntent().getExtras();
		hotelNameText.setText(bundle.getString("hotelName"));
		orderId.setText(bundle.getString("orderId"));
		roomStyleText.setText(bundle.getString("roomStyle"));
		addressHotelText.setText(bundle.getString("addressHotel"));
		beginDateText.setText(bundle.getString("beginDate"));
		endDateText.setText(bundle.getString("endDate"));
		customerNameText.setText(bundle.getString("customerName"));
		mobilePhoneText.setText(bundle.getString("mobilePhone"));
		orderPriceText.setText(bundle.getString("orderPrice"));
		ImageView returnbtn = (ImageView) findViewById(R.id.returnbtn);
    	returnbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// Intent intent = new Intent();
				// intent.setClass(detail_information.this, MainActivity.class);
				// startActivity(intent);
				finish();
			}

		});
	
	}

	public boolean onKeyDown(int KeyCode, KeyEvent event) {
		if (KeyCode == KeyEvent.KEYCODE_BACK) {
//			Intent intent = new Intent();
//			intent.setClass(User_order_detail.this, User_order.class);
//			startActivity(intent);
			User_order_detail.this.finish();
		}
		return false;
	}
}
