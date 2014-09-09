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

public class User_oldregister extends Activity {
	private EditText entityMobilephone;
	private EditText entityCardnum;
	private EditText entityIdcard;
	private Button entityBtn;
	private ImageView entityreturnBtn;
	private MapData mapdata;

	public void onCreate(Bundle SavedInsanceState) {
		super.onCreate(SavedInsanceState);
		setContentView(R.layout.user_old_register);
		entityMobilephone = (EditText) findViewById(R.id.entityMobilephone);
		entityCardnum = (EditText) findViewById(R.id.entityCardnum);
		entityIdcard = (EditText) findViewById(R.id.entityIdcard);
		entityBtn = (Button) findViewById(R.id.entityBtn);
		entityBtn.setOnClickListener(new FillBtnOnClickListener());
		entityreturnBtn = (ImageView) findViewById(R.id.entityreturnBtn);
		entityreturnBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(User_oldregister.this, User_login.class);
				startActivity(intent);
				User_oldregister.this.finish();
			}

		});

		mapdata = new MapData();
	}

	class FillBtnOnClickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub

			if (entityMobilephone.getText().toString().equals("")
					|| entityCardnum.getText().toString().equals("")
					|| entityIdcard.getText().toString().equals("")) {
				Dialog fail = new AlertDialog.Builder(User_oldregister.this)
						.setTitle("�޸�").setMessage("����д����!")
						.setPositiveButton("ȷ��", null).create();
				fail.show();
			} else {
				Dialog submit = new AlertDialog.Builder(User_oldregister.this)
						.setTitle("ȷ���ύ")
						.setMessage("����ϸ����ֻ����ǲ�����д����,һ����д�󲻿��޸ģ�")
						.setPositiveButton("ȷ��",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										if (entityMobilephone.getText()
												.toString().length() >= 11) {
											if (mapdata.findMobilePhone(
													entityMobilephone.getText()
															.toString())
													.equals("true")) {
												Dialog dialog1 = new AlertDialog.Builder(
														User_oldregister.this)
														.setTitle("��ʾ")
														.setMessage(
																"�ֻ������Ѿ�������������д")
														.setPositiveButton(
																"ȷ��", null)
														.create();
												dialog1.show();

											} else {
												if (mapdata.userMobileSet(
														entityIdcard.getText()
																.toString(),
														entityMobilephone
																.getText()
																.toString(),
														entityCardnum.getText()
																.toString())
														.equals("true")) {
													Dialog dialog2 = new AlertDialog.Builder(
															User_oldregister.this)
															.setTitle("��ʾ")
															.setMessage("�޸ĳɹ�")
															.setPositiveButton(
																	"ȷ��",
																	new DialogInterface.OnClickListener() {
																		@Override
																		public void onClick(
																				DialogInterface dialog,
																				int which) {
																			// TODO
																			// Auto-generated
																			// method
																			// stub
																			Intent intent = new Intent(
																					User_oldregister.this,
																					User_login.class);
																			startActivity(intent);
																			User_oldregister.this
																					.finish();
																		}
																	}).create();
													dialog2.show();
												} else {
													Dialog dialog3 = new AlertDialog.Builder(
															User_oldregister.this)
															.setTitle("��ʾ")
															.setMessage(
																	"��Ϣ��д����ȷ")
															.setPositiveButton(
																	"ȷ��", null)
															.create();
													dialog3.show();
												}
											}
										} else {
											Dialog dialog4 = new AlertDialog.Builder(
													User_oldregister.this)
													.setTitle("��ʾ")
													.setMessage("�ֻ�������д����ȷ")
													.setPositiveButton("ȷ��",
															null).create();
											dialog4.show();
										}
									}
								}).setNegativeButton("ȡ��", null).create();
				submit.show();
			}
		}

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent();
			intent.setClass(User_oldregister.this, User_login.class);
			startActivity(intent);
			User_oldregister.this.finish();
		}
		return false;
	}

}
