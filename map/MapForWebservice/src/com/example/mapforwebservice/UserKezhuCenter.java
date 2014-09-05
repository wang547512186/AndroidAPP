package com.example.mapforwebservice;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UserKezhuCenter extends Activity {
	private LinearLayout kezhuDetailLayout;
	private LinearLayout kezhuHistoryLayout;
	private String userPhone;
	private String uid;
	private List<String> detail;
	private MapData mapdata;
	
	private TextView returnKezhuText;
	private TextView rewardKezhuText;
	private TextView totalKezhuText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_kezhu_center);
		Intent intent = getIntent();
		userPhone = intent.getStringExtra("userPhone");
		uid = intent.getStringExtra("uid");
		returnKezhuText = (TextView)findViewById(R.id.returnKezhuText);
		rewardKezhuText = (TextView) findViewById(R.id.rewardKezhuText);
		totalKezhuText = (TextView)findViewById(R.id.totalKezhuText);
		mapdata = new MapData();
		detail = mapdata.userInfor(userPhone);
		List<String> kezhudetail = mapdata.getUserKezhu(uid);
		if (!detail.isEmpty()&&!kezhudetail.isEmpty()) {
			Double totalkezhu= Double.valueOf(kezhudetail.get(0).toString());
			totalKezhuText.setText(totalkezhu+"");
			returnKezhuText.setText(totalkezhu+"");
			rewardKezhuText.setText("0");
			// ykezhu = Double.valueOf(detail.get(8).toString().trim());
		}else{
			totalKezhuText.setText("0");
			returnKezhuText.setText("0");
			rewardKezhuText.setText("0");
		}
//		Double totalkezhu=Double.valueOf(detail.get(7).toString())+Double.valueOf(detail.get(8).toString());
		
		
		
		kezhuDetailLayout = (LinearLayout) findViewById(R.id.kezhuDetailLayout);
		User_information.changeBackground(kezhuDetailLayout);
		kezhuDetailLayout.setOnClickListener(new OnClickListener() {
			// 积分明细
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(UserKezhuCenter.this,
						UserKezhubiDetail.class);
				intent.putExtra("userPhone", userPhone);
				intent.putExtra("uid", uid);
				startActivity(intent);
			}
		});
		kezhuHistoryLayout = (LinearLayout) findViewById(R.id.kezhuHistoryLayout);
		changeBackground2(kezhuHistoryLayout);
		kezhuHistoryLayout.setOnClickListener(new OnClickListener() {
			// 消费记录
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(UserKezhuCenter.this,
						KezhuPayHistoryList.class);
				intent.putExtra("userPhone", userPhone);
				intent.putExtra("uid", uid);
				startActivity(intent);
			}
		});

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
	
	public static void changeBackground2(final View view) {
		view.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch (event.getAction()) {

				case MotionEvent.ACTION_DOWN: {
					view.setBackgroundResource(R.color.boder);
					// 按下时触发
					break;
				}
				case MotionEvent.ACTION_UP: {
					view.setBackgroundResource(R.drawable.bg_kezhu);
					// 触摸后触发
					break;
				}//
				case MotionEvent.ACTION_CANCEL: {
					view.setBackgroundResource(R.drawable.bg_kezhu);
					// 触摸后触发
					break;
				}
				}

				return false;
			}

		});
	}
}
