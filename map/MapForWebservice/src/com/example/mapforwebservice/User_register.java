package com.example.mapforwebservice;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class User_register extends Activity {

	private Button registerBtn;
	private MapData mapdata;

	private EditText inviteNumber;
	private EditText registerName;
	private EditText registerPwd;
	private EditText registerPwd2;
	private EditText userNickname;
	private EditText userEmail;
	private LinearLayout checkLayout;
	private LinearLayout nocheckLayout;
	private LinearLayout phoneCheckLayout;
	private String inviteNumberString;
	private String registerNameString;
	private String registerPwdString;
	private String registerPwdString2;
	private String userNicknameString;
	private String phoneNumberString;
	private String userEmailString;
	private String SexString;
	private RadioGroup radioGroup1;
	private RadioButton radio0;
	private RadioButton radio1;
	private SharedPreferences sp;
	private List<String> userinfor = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_register);
		sp = getSharedPreferences("login_state", Context.MODE_PRIVATE);

		checkLayout = (LinearLayout) findViewById(R.id.checkLayout);
		checkLayout.setVisibility(View.GONE);
		nocheckLayout = (LinearLayout) findViewById(R.id.nocheckLayout);
		nocheckLayout.setVisibility(View.GONE);
		phoneCheckLayout = (LinearLayout) findViewById(R.id.phoneCheckLayout);
		phoneCheckLayout.setVisibility(View.GONE);
		
		registerName = (EditText) findViewById(R.id.registerName);
		registerPwd = (EditText) findViewById(R.id.registerPwd);
		registerPwd2 = (EditText) findViewById(R.id.registerPwd2);
		userNickname = (EditText) findViewById(R.id.userNickname);
		userEmail = (EditText) findViewById(R.id.userEmail);
		radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
		radio0 = (RadioButton) findViewById(R.id.radio0);
		radio1 = (RadioButton) findViewById(R.id.radio1);

		mapdata = new MapData();

		inviteNumber = (EditText) findViewById(R.id.inviteNumber);
		inviteNumber.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (s.length() >= 11) {
					if (mapdata.findMobilePhone(
							inviteNumber.getText().toString()).equals("false")) {
						checkLayout.setVisibility(View.GONE);
						nocheckLayout.setVisibility(View.VISIBLE);

					} else {
						checkLayout.setVisibility(View.VISIBLE);
						nocheckLayout.setVisibility(View.GONE);
					}
				} else {
					checkLayout.setVisibility(View.GONE);
					nocheckLayout.setVisibility(View.GONE);
				}
			}
		});
		
		registerName.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
			}
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (s.length() >= 11) {
					if (mapdata.findMobilePhone(
							registerName.getText().toString()).equals("true")) {
						phoneCheckLayout.setVisibility(View.VISIBLE);
					} else {
						phoneCheckLayout.setVisibility(View.GONE);
					}
				} else {
					phoneCheckLayout.setVisibility(View.GONE);
				}
			}
		});

		registerBtn = (Button) findViewById(R.id.registerBtn);
		registerBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				registerNameString = registerName.getText().toString();
				userNicknameString = userNickname.getText().toString();
				registerPwdString = registerPwd.getText().toString();
				registerPwdString2 = registerPwd2.getText().toString();
				phoneNumberString = registerNameString;
				userEmailString = userEmail.getText().toString();
				inviteNumberString = inviteNumber.getText().toString();
				if (radio0.isChecked()) {
					SexString = "1";
				} else {
					SexString = "0";
				}
				if (mapdata.findMobilePhone(inviteNumberString).equals("false")) {
					Dialog fail = new AlertDialog.Builder(User_register.this)
							.setTitle("提示").setMessage("邀请人不是已注册会员")
							.setPositiveButton("确定", null).create();
					fail.show();
				} else {
					if (registerNameString.trim().equals("")
							|| userNicknameString.trim().equals("")
							|| registerPwdString.trim().equals("")
							|| registerPwdString2.trim().equals("")
							|| phoneNumberString.trim().equals("")
							|| inviteNumberString.trim().equals("")) {
						Dialog fail2 = new AlertDialog.Builder(
								User_register.this).setTitle("提示")
								.setMessage("请填写完整!")
								.setPositiveButton("确定", null).create();
						fail2.show();
					} else if (!registerPwdString2.equals(registerPwdString)) {
						Dialog fail2 = new AlertDialog.Builder(
								User_register.this).setTitle("提示")
								.setMessage("两次密码输入不相同!")
								.setPositiveButton("确定", null).create();
						fail2.show();
					} else if(mapdata.findMobilePhone(
							registerName.getText().toString()).equals("true")) {
						Dialog fail2 = new AlertDialog.Builder(
								User_register.this).setTitle("提示")
								.setMessage("该号码已注册!")
								.setPositiveButton("确定", null).create();
						fail2.show();
					}else {
						String resultString = mapdata.userRegister(
								registerNameString, userNicknameString,
								registerPwdString, userEmailString, SexString,inviteNumberString,"0","0","0");

						Log.e("SexString", SexString);
						if (resultString.equals("true")) {
							Dialog dialog = new AlertDialog.Builder(
									User_register.this)
									.setTitle("提示")
									.setMessage("注册成功!")
									.setPositiveButton(
											"确定",
											new DialogInterface.OnClickListener() {
												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {
													// TODO Auto-generated
													// method
													// stub
													Editor editor = sp.edit();
													editor.putString(
															"userPhone",
															registerName
																	.getText()
																	.toString()
																	.trim());
													userinfor = mapdata
															.userInfor(registerName
																	.getText()
																	.toString()
																	.trim());
													editor.putString("uid",
															userinfor.get(0)
																	.toString());

													editor.commit();

													Intent intent = new Intent(
															User_register.this,
															User_information.class);
													startActivity(intent);
													User_register.this.finish();
												}
											}).create();
							dialog.show();
						}// 注册成功
						else {
							Dialog fail2 = new AlertDialog.Builder(
									User_register.this).setTitle("提示")
									.setMessage("注册失败!")
									.setPositiveButton("确定", null).create();
							fail2.show();
						}

					}
				}

			}
		});

		ImageView invitePhoneImage = (ImageView) findViewById(R.id.invitePhoneImage);

		invitePhoneImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_PICK,
						ContactsContract.Contacts.CONTENT_URI);
				User_register.this.startActivityForResult(intent, 1);
			}

		});

		ImageView returnbtn = (ImageView) findViewById(R.id.returnbtn);
		returnbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(User_register.this, User_login.class);
				startActivity(intent);
				finish();
			}

		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {

		case (1): {

			if (resultCode == Activity.RESULT_OK) {

				Uri contactData = data.getData();

				Cursor c = managedQuery(contactData, null, null, null, null);

				c.moveToFirst();

				String phoneResult[] = this.getContactPhone(c);
				inviteNumber.setText(phoneResult[0]);
			}
			break;
		}
		}

	}

	private String[] getContactPhone(Cursor cursor) {
		// TODO Auto-generated method stub

		int phoneColumn = cursor
				.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
		int phoneNum = cursor.getInt(phoneColumn);
		String[] phoneResult = { "", "" };

		// System.out.print(phoneNum);
		if (phoneNum > 0) {
			// 获得联系人的ID号
			int idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID);
			String contactId = cursor.getString(idColumn);
			// 获得联系人的电话号码的cursor;
			Cursor phones = getContentResolver().query(
					ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
					null,
					ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "
							+ contactId, null, null);
			// int phoneCount = phones.getCount();
			// allPhoneNum = new ArrayList<String>(phoneCount);
			if (phones.moveToFirst()) {
				// 遍历所有的电话号码
				for (; !phones.isAfterLast(); phones.moveToNext()) {
					int index2 = phones
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
					int typeindex = phones
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE);
					int phone_type = phones.getInt(typeindex);
					String phoneNumber = phones.getString(index2);
					switch (phone_type) {
					case 2:
						phoneResult[0] = phoneNumber;
						break;
					}
					// allPhoneNum.add(phoneNumber);
				}
				if (!phones.isClosed()) {
					phones.close();
				}
			}
		}
		return phoneResult;
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent();
			intent.setClass(User_register.this, User_login.class);
			startActivity(intent);
			finish();
		}
		return false;
	}

}
