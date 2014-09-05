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
import android.provider.ContactsContract.CommonDataKinds.Nickname;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ChuZhiPay extends Activity {
	private Button payconfirmBtn;
	private String hotelId;
	private String hotelNameStr;
	private String hotelChuzhiStr;
	private String hotelAddressStr;
	private Double chuzhi = 0.0;
	private EditText requestChuzhi;
	private Double requestChuzhiNumber;
	private SharedPreferences sp;
	private String userPhone = "";
	private String nickName = "";
	private String userPwd = "";
	private String uid = "";
	private MapData mapdata;
	private List<String> detail;
	private static final int RESULT_FAIL = 1;
	private static final int RESULT_SUCCESS = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chu_zhi_pay);

		sp = getSharedPreferences("login_state", Context.MODE_PRIVATE);
		userPhone = sp.getString("userPhone", "");
		uid = sp.getString("uid", "");
		mapdata = new MapData();
		detail = mapdata.userInfor(userPhone);
		if (!detail.isEmpty()) {
			nickName = detail.get(1).toString().trim();
		}

		Intent intent = getIntent();
		hotelId = intent.getStringExtra("hotelId");
		
		// Toast.makeText(ChuZhiPay.this,hotelId, Toast.LENGTH_LONG).show();
		hotelNameStr = intent.getStringExtra("hotelname");
		hotelChuzhiStr = intent.getStringExtra("hotelchuzhi");
		hotelAddressStr = intent.getStringExtra("hoteladdress");
		TextView hotelname = (TextView) findViewById(R.id.hotelname);
		TextView hotelchuzhi = (TextView) findViewById(R.id.hotelchuzhi);
		TextView hoteladdress = (TextView) findViewById(R.id.hoteladdress);
		hotelname.setText(hotelNameStr);
		hotelchuzhi.setText(hotelChuzhiStr);
		hoteladdress.setText(hotelAddressStr);

		requestChuzhi = (EditText) findViewById(R.id.requestChuzhi);
		chuzhi = Double.valueOf(hotelChuzhiStr);

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

		payconfirmBtn = (Button) findViewById(R.id.payconfirmBtn);
		payconfirmBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				requestChuzhiNumber = Double.valueOf(requestChuzhi.getText()
						.toString());
				if (requestChuzhi.getText().toString().equals("")) {
					Dialog dialog = new AlertDialog.Builder(ChuZhiPay.this)
							.setTitle("提示").setMessage("请输入金额!")
							.setPositiveButton("确定", null).create();
					dialog.show();
				} else if (requestChuzhiNumber > chuzhi) {
					Dialog dialog = new AlertDialog.Builder(ChuZhiPay.this)
							.setTitle("提示").setMessage("余额不足,无法支付!")
							.setPositiveButton("确定", null).create();
					dialog.show();
				} else {
					Intent intent = new Intent(ChuZhiPay.this, PayConfirm.class);
					intent.putExtra("userPhone", userPhone);
					intent.putExtra("ActivityName", "ChuZhiPay");
					startActivityForResult(intent, 3);
				}

			}

		});
	}

	protected void onActivityResult(int requestCode, int resuluCode, Intent data) {
		if (requestCode == 3 && resuluCode == RESULT_FAIL) {
		}
		if (requestCode == 3 && resuluCode == RESULT_SUCCESS) {
			mapdata = new MapData();
			requestChuzhiNumber = Double.valueOf('-'+requestChuzhi.getText()
					.toString());
			String isUpdateChuzhi = mapdata.updateUserChuzhi(uid, hotelId,
					String.valueOf(chuzhi + requestChuzhiNumber));
//			Toast.makeText(ChuZhiPay.this,uid+"|"+hotelId+"|"+ String.valueOf(chuzhi + requestChuzhiNumber), Toast.LENGTH_LONG).show();
			if (isUpdateChuzhi.equals("true")) {

				mapdata = new MapData();
				try {
					mapdata.addHistory(uid, nickName, hotelId, hotelNameStr, "0","0", String.valueOf(requestChuzhiNumber));
				} catch (Exception e) {
//					Toast.makeText(ChuZhiPay.this,userName+hotelId+String.valueOf(requestChuzhiNumber), Toast.LENGTH_LONG).show();
				}

				Dialog dialog2 = new AlertDialog.Builder(ChuZhiPay.this)
						.setTitle("支付结果")
						.setMessage("储值支付成功!")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										Intent intent = new Intent(
												ChuZhiPay.this,
												ChuZhiSelect.class);
										startActivity(intent);
										ChuZhiPay.this.finish();
									}
								}).create();
				dialog2.show();
			} else {
				Dialog dialog2 = new AlertDialog.Builder(ChuZhiPay.this)
						.setTitle("支付结果").setMessage("支付失败!")
						.setPositiveButton("确定", null).create();
				dialog2.show();
			}
		}

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent(ChuZhiPay.this, ChuZhiSelect.class);
			startActivity(intent);
			ChuZhiPay.this.finish();
		}
		return false;
	}

}
