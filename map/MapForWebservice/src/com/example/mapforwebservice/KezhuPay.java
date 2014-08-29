package com.example.mapforwebservice;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class KezhuPay extends Activity {
	private MapData mapdata;
	private List<String> detail;
	private Button payconfirmBtn;
	private LinearLayout kezhuhotelLayout;
	private String strHotelName;
	private int strHotelImg;
	private String strHotelAddress;
	private TextView hotelname;
	private TextView hoteladdress;
	private TextView kezhubiText;
	private String nickName = "";
	private String userName = "";
	private String uid = "";
	private String userPwd = "";
	private Double fkezhu = 0.0;
	private Double ykezhu = 0.0;
	private EditText requestKezhu;
	private Double requestKezhuNumber;
	private static final int RESULT_FAIL = 1;
	private static final int RESULT_SUCCESS = 2;
	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kezhu_pay);
		hotelname = (TextView) findViewById(R.id.hotelname);
		hoteladdress = (TextView) findViewById(R.id.hoteladdress);
		kezhubiText = (TextView) findViewById(R.id.kezhubiText);
		requestKezhu = (EditText) findViewById(R.id.requestKezhu);

		sp = getSharedPreferences("login_state", Context.MODE_PRIVATE);
		userName = sp.getString("userName", "");
		uid = sp.getString("uid", "");
		mapdata = new MapData();
		detail = mapdata.userInfor(userName);
		List<String> kezhudetail = mapdata.getUserKezhu(uid);
		if (!detail.isEmpty()&&!kezhudetail.isEmpty()) {
			fkezhu = Double.valueOf(kezhudetail.get(0).toString());
			// ykezhu = Double.valueOf(detail.get(8).toString().trim());
			nickName = detail.get(1).toString().trim();
		}
		// Intent intent = getIntent();
		//
		//
		// userName = intent.getStringExtra("UserName");
		// userPwd = intent.getStringExtra("UserPwd");
		// fkezhu = intent.getDoubleExtra("fkezhu", 0);
		// ykezhu = intent.getDoubleExtra("ykezhu", 0);
		// nickName = intent.getStringExtra("nickName");

		kezhubiText.setText(String.valueOf(fkezhu + ykezhu));

		ImageView returnbtn = (ImageView) findViewById(R.id.returnbtn);
		returnbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(KezhuPay.this,
						User_information.class);
				startActivity(intent);
				KezhuPay.this.finish();
			}

		});
		kezhuhotelLayout = (LinearLayout) findViewById(R.id.kezhuhotelLayout);
		User_information.changeBackground(kezhuhotelLayout);
		kezhuhotelLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(KezhuPay.this, SearchHotel.class);
				startActivityForResult(intent, 1);
			}

		});
		payconfirmBtn = (Button) findViewById(R.id.payconfirmBtn);
		payconfirmBtn.setOnClickListener(new OnClickListener() {
			// 支付
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (requestKezhu.getText().toString().equals("")) {
					Dialog dialog = new AlertDialog.Builder(KezhuPay.this)
							.setTitle("提示").setMessage("请填写金额!")
							.setPositiveButton("确定", null).create();
					dialog.show();
				} else if (hotelname.getText().toString().equals("")) {
					Dialog dialog = new AlertDialog.Builder(KezhuPay.this)
							.setTitle("提示").setMessage("请选择酒店!")
							.setPositiveButton("确定", null).create();
					dialog.show();
				} else {
					requestKezhuNumber = Double.valueOf(requestKezhu.getText()
							.toString());
					if (requestKezhuNumber > (fkezhu + ykezhu)) {// /支付不成功
						Dialog dialog = new AlertDialog.Builder(KezhuPay.this)
								.setTitle("提示").setMessage("余额不足,无法支付!")
								.setPositiveButton("确定", null).create();
						dialog.show();
					} else {
						Intent intent = new Intent(KezhuPay.this,
								PayConfirm.class);
						intent.putExtra("userName", userName);
						intent.putExtra("ActivityName", "KezhuPay");
						startActivityForResult(intent, 2);

					}

				}

			}

		});

	}

	protected void onActivityResult(int requestCode, int resuluCode, Intent data) {
		if (requestCode == 1 && resuluCode == RESULT_OK) {
			strHotelName = data.getExtras().getString("strHotelName");
			strHotelAddress = data.getExtras().getString("strHotelAddress");
			hotelname.setText(strHotelName);
			hoteladdress.setText(strHotelAddress);
		}
		if (requestCode == 2 && resuluCode == RESULT_FAIL) {

		}
		if (requestCode == 2 && resuluCode == RESULT_SUCCESS) {
			String customeruseridString = userName;
			String customernameString = nickName;
			String addressString = hoteladdress.getText().toString();
			String hotelnameString = hotelname.getText().toString();
			String moneyDouble = "0";
			String moneykezhuDouble = requestKezhu.getText().toString();
			String returnkezhuDouble = "0";
			String chuzhiDouble = "0";

			mapdata = new MapData();
			String isAddHotelhistory = mapdata.addHotelhistory(
					customeruseridString, customernameString, addressString,
					hotelnameString, moneyDouble, moneykezhuDouble,
					returnkezhuDouble, chuzhiDouble);
			// /////支付成功
			if (isAddHotelhistory.equals("true")) {
				String resultString;
				if (requestKezhuNumber < fkezhu) {
					resultString = mapdata.updateUser("fkezhu",
							String.valueOf(fkezhu - requestKezhuNumber),
							userName);
				} else {
					mapdata.updateUser("fkezhu", "0", userName);
					resultString = mapdata.updateUser("ykezhu", String
							.valueOf(ykezhu + fkezhu - requestKezhuNumber),
							userName);
				}
				if (resultString.equals("true")) {
					Dialog dialog2 = new AlertDialog.Builder(KezhuPay.this)
							.setTitle("支付结果")
							.setMessage("支付成功!")
							.setPositiveButton("确定",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated method stub
											Intent intent = new Intent(
													KezhuPay.this,
													User_information.class);
											startActivity(intent);
											KezhuPay.this.finish();
										}
									}).create();
					dialog2.show();
				} else {
					Dialog dialog2 = new AlertDialog.Builder(KezhuPay.this)
							.setTitle("支付结果").setMessage("支付失败!")
							.setPositiveButton("确定", null).create();
					dialog2.show();
				}

			} else {
				Dialog dialog2 = new AlertDialog.Builder(KezhuPay.this)
						.setTitle("支付结果").setMessage("支付失败!")
						.setPositiveButton("确定", null).create();
				dialog2.show();
			}

		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent(KezhuPay.this, User_information.class);
			startActivity(intent);
			KezhuPay.this.finish();
		}
		return false;
	}
}
