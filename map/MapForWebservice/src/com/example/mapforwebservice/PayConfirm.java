package com.example.mapforwebservice;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class PayConfirm extends Activity {
	private static final int RESULT_FAIL = 1;
	private static final int RESULT_SUCCESS = 2;
	private String userPhone;
	private String ActivityName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.pay_confirm);
		Intent intent = getIntent();
		userPhone = intent.getStringExtra("userPhone");
		ActivityName = intent.getStringExtra("ActivityName");
		Button payConfirmBtn = (Button) findViewById(R.id.payConfirmBtn);
		payConfirmBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TextView payPwdText = (TextView) findViewById(R.id.payPwdText);
				String inputString = payPwdText.getText().toString();
				MapData mapData=new MapData();
				mapData.userLogin(userPhone, inputString);
				if (mapData.userLogin(userPhone, inputString).equals("true")) {
					returnSuccess();
				} else {
					Dialog dialog = new AlertDialog.Builder(PayConfirm.this)
							.setTitle("支付结果")
							.setMessage("密码不正确,支付失败!")
							.setPositiveButton("确定",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											returnFail();
										}
									}).create();
					dialog.show();
				}
			}
		});

	}

	public void returnFail() {
		if (ActivityName.equals("KezhuPay")) {
			Intent intent = new Intent();
			intent.setClass(PayConfirm.this, KezhuPay.class);
			PayConfirm.this.setResult(RESULT_FAIL, intent);
			finish();
		} else if (ActivityName.equals("ChuZhiPay")) {
			Intent intent = new Intent();
			intent.setClass(PayConfirm.this, ChuZhiPay.class);
			PayConfirm.this.setResult(RESULT_FAIL, intent);
			finish();
		} else {
			Dialog dialog = new AlertDialog.Builder(PayConfirm.this)
					.setTitle("提示").setMessage("数据传递异常")
					.setPositiveButton("确定", null).create();
			dialog.show();
		}
	}

	public void returnSuccess() {
		
		if (ActivityName.equals("KezhuPay")) {
			Intent intent = new Intent();
			intent.setClass(PayConfirm.this, KezhuPay.class);
			PayConfirm.this.setResult(RESULT_SUCCESS, intent);
			finish();
		} else if (ActivityName.equals("ChuZhiPay")) {
			Intent intent = new Intent();
			intent.setClass(PayConfirm.this, ChuZhiPay.class);
			PayConfirm.this.setResult(RESULT_SUCCESS, intent);
			finish();
		} else {
			Dialog dialog = new AlertDialog.Builder(PayConfirm.this)
					.setTitle("提示").setMessage("数据传递异常")
					.setPositiveButton("确定", null).create();
			dialog.show();
		}
	}

}
