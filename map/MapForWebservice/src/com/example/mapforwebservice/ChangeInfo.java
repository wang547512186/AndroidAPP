package com.example.mapforwebservice;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ChangeInfo extends Activity {
	private MapData mapdata;
	private String usernameStr;
	private String usersexStr;
	private String useremailStr;
	private String useraddressStr;
	private String userphoneStr;
	private String UserName;
	private EditText user_name;
	private EditText user_email;
	private EditText user_phone;
	private TextView user_sex;
	private Button changeBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_info);
		Bundle bundle = getIntent().getExtras();
		UserName = bundle.getString("UserName");
		usernameStr = bundle.getString("usernameStr");
		usersexStr = bundle.getString("usersexStr");
		useremailStr = bundle.getString("useremailStr");
		useraddressStr = bundle.getString("useraddressStr");
		userphoneStr = bundle.getString("userphoneStr");
		user_name = (EditText) findViewById(R.id.user_name);
		user_sex = (TextView) findViewById(R.id.user_sex);
		user_email = (EditText) findViewById(R.id.user_email);
		user_phone = (EditText) findViewById(R.id.user_phone);
		Button changeBtn = (Button) findViewById(R.id.changeBtn);

		user_name.setText(usernameStr);
		user_sex.setText(usersexStr);
		user_email.setText(useremailStr);
		user_phone.setText(userphoneStr);

		user_sex.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(ChangeInfo.this)
						.setTitle("��ѡ��")
						.setSingleChoiceItems(new String[] { "��", "Ů" }, 0,
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int which) {
										if (which == 0) {
											usersexStr = "��";
											user_sex.setText(usersexStr);
											dialog.dismiss();
										} else {
											usersexStr = "Ů";
											user_sex.setText(usersexStr);
											dialog.dismiss();
										}

									}
								}).setNegativeButton("ȡ��", null).show();
			}

		});
		changeBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				usernameStr = user_name.getText().toString();
				usersexStr = user_sex.getText().toString();
				useremailStr = user_email.getText().toString();
				userphoneStr = user_phone.getText().toString();

				final String intSex;
				if (usersexStr.equals("��")) {
					intSex = "1";
				} else {
					intSex = "0";
				}

				AlertDialog exitConfirm = new AlertDialog.Builder(
						ChangeInfo.this)
						.setTitle("��ʾ")
						.setMessage("ȷ���޸ģ�")
						.setPositiveButton("ȷ��",
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int whichButton) {
										mapdata = new MapData();
										mapdata.updateUser("nickname",
												usernameStr, UserName);
										mapdata.updateUser("email",
												useremailStr, UserName);
										mapdata.updateUser("sexy", intSex,
												UserName);
										mapdata.updateUser("mobilephone",
												userphoneStr, UserName);

										Intent intent = new Intent(
												ChangeInfo.this,
												User_infor_detail.class);
										intent.putExtra("UserName", UserName);
										startActivity(intent);
										ChangeInfo.this.finish();

									}
								})
						.setNegativeButton("ȡ��",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										// ȡ����ť�¼�
									}
								}).show();

			}
		});

		ImageView returnbtn = (ImageView) findViewById(R.id.returnbtn);
		returnbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ChangeInfo.this, User_infor_detail.class);
				intent.putExtra("UserName", UserName);
				startActivity(intent);
				ChangeInfo.this.finish();
			}

		});
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent(ChangeInfo.this, User_infor_detail.class);
			intent.putExtra("UserName", UserName);
			startActivity(intent);
			ChangeInfo.this.finish();
		}
		return false;
	}
}
