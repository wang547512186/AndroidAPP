package com.example.mapforwebservice;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class UserChangeMobile extends Activity {
	private EditText fillMobilephone;
	private EditText fillCardnum;
	private EditText fillIdcard;
	private Button fillBtn;
	private ImageView fillreturnBtn;
	private MapData mapdata;

	public void onCreate(Bundle SavedInsanceState) {
		super.onCreate(SavedInsanceState);
		setContentView(R.layout.user_chang_mobile);
		fillMobilephone = (EditText) findViewById(R.id.fillMobilephone);
		fillCardnum = (EditText) findViewById(R.id.fillCardnum);
		fillIdcard = (EditText) findViewById(R.id.fillIdcard);
		fillBtn = (Button) findViewById(R.id.fillBtn);
		fillBtn.setOnClickListener(new FillBtnOnClickListener());
		fillreturnBtn = (ImageView) findViewById(R.id.fillreturnbtn);
		fillreturnBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(UserChangeMobile.this, User_login.class);
				startActivity(intent);
				UserChangeMobile.this.finish();
			}

		});

		mapdata = new MapData();
	}

	class FillBtnOnClickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if (fillMobilephone.getText().toString().length() == 11) {
				String result = mapdata.userMobileSet(fillIdcard.getText()
						.toString(), fillMobilephone.getText().toString(),
						fillCardnum.getText().toString());
				if (result.equals("true")) {
					Dialog dialog = new AlertDialog.Builder(
							UserChangeMobile.this)
							.setTitle("提示")
							.setMessage("修改成功!")
							.setPositiveButton("确定",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated
											// method
											// stub
											Intent intent = new Intent(
													UserChangeMobile.this,
													User_login.class);
											startActivity(intent);
											UserChangeMobile.this.finish();
										}
									}).create();
					dialog.show();
				} else {
					if (mapdata.findMobilePhone(
							fillMobilephone.getText().toString())
							.equals("true")) {
						Dialog dialog = new AlertDialog.Builder(
								UserChangeMobile.this).setTitle("提示")
								.setMessage("手机号码已经存在请重新填写")
								.setPositiveButton("确定", null).create();
						dialog.show();
					} else {
						Dialog dialog = new AlertDialog.Builder(
								UserChangeMobile.this).setTitle("提示")
								.setMessage("信息填写不正确")
								.setPositiveButton("确定", null).create();
						dialog.show();
					}
				}
			} else {
				Dialog dialog = new AlertDialog.Builder(UserChangeMobile.this)
						.setTitle("提示").setMessage("手机号码填写不正确")
						.setPositiveButton("确定", null).create();
				dialog.show();
			}
		}

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent();
			intent.setClass(UserChangeMobile.this, User_login.class);
			startActivity(intent);
			UserChangeMobile.this.finish();
		}
		return false;
	}

}
