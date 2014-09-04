package com.example.mapforwebservice;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class User_login extends Activity {
	private EditText loginname;
	private EditText loginpwd;
	private Button logintrue;
	private Button registerBtn;
	// private Button loginfalse;
	private MapData mapdata;
	private SharedPreferences sp;
	private List<String> userinfor = new ArrayList<String>();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_login);

		sp = getSharedPreferences("login_state", Context.MODE_PRIVATE);
		// 保存登录信息

		loginname = (EditText) findViewById(R.id.loginname);
		loginpwd = (EditText) findViewById(R.id.loginpwd);
		logintrue = (Button) findViewById(R.id.logintrue);

		registerBtn = (Button) findViewById(R.id.registerBtn);
		// loginfalse=(Button)findViewById(R.id.loginfalse);
		mapdata = new MapData();

		registerBtn.setOnClickListener(new OnClickListener() {
			// 注册
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(User_login.this, User_register.class);
				startActivity(intent);
				finish();
			}
		});
		ImageView returnbtn = (ImageView) findViewById(R.id.returnbtn);
		returnbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(User_login.this,
						User_information.class);
				startActivity(intent);
				finish();
			}

		});
		logintrue.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (loginname.getText().toString().equals("")
						|| loginpwd.getText().toString().equals("")) {
					Dialog fail = new AlertDialog.Builder(User_login.this)
							.setTitle("登录").setMessage("请填写完整!")
							.setPositiveButton("确定", null).create();
					fail.show();
				} else if (loginname.getText().toString().length() != 11) {
					Dialog fail = new AlertDialog.Builder(User_login.this)
							.setTitle("登录").setMessage("手机号码格式不正确!")
							.setPositiveButton("确定", null).create();
					fail.show();
				} else {
					if (mapdata.hasMobilephone(loginname.getText().toString())
							.equals("0")) {
						Dialog fail = new AlertDialog.Builder(User_login.this)
								.setTitle("登录")
								.setMessage("手机号不存在，请重新填写手机号!")
								.setPositiveButton("确定",
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(
													DialogInterface arg0,
													int arg1) {
												// TODO Auto-generated method
												// stub
												Intent intent = new Intent();
												intent.setClass(
														User_login.this,
														UserChangeMobile.class);
												startActivity(intent);
												User_login.this.finish();
											}
										}).create();
						fail.show();
					} else if (mapdata.hasMobilephone(
							loginname.getText().toString()).equals("2")) {
						Dialog fail = new AlertDialog.Builder(User_login.this)
								.setTitle("登录")
								.setMessage("手机号重复,请修改手机号码!")
								.setPositiveButton("确定",
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(
													DialogInterface arg0,
													int arg1) {
												// TODO Auto-generated method
												// stub
												Intent intent = new Intent();
												intent.setClass(
														User_login.this,
														UserChangeMobile.class);
												startActivity(intent);
												User_login.this.finish();
											}
										}).create();
						fail.show();
					} else if (mapdata.hasMobilephone(     
							loginname.getText().toString()).equals("9")) {
						Dialog fail = new AlertDialog.Builder(User_login.this)
								.setTitle("登录").setMessage("网络有误,请重试!")
								.setPositiveButton("确定", null).create();
						fail.show();
					} else {
						if (mapdata.userLogin(loginname.getText().toString(),
								loginpwd.getText().toString()).equals("true")) {
							((PointValue) getApplication())
									.setUserName(loginname.getText().toString());
							Dialog fail = new AlertDialog.Builder(
									User_login.this)
									.setTitle("登录")
									.setMessage("登录成功!")
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
															"userName",
															loginname.getText()
																	.toString()
																	.trim());
													userinfor = mapdata
															.userInfor(loginname
																	.getText()
																	.toString()
																	.trim());
													editor.putString("uid",
															userinfor.get(0)
																	.toString());
													editor.commit();
													Intent intent = new Intent();
													intent.setClass(
															User_login.this,
															User_information.class);
													startActivity(intent);
													User_login.this.finish();
													// finish();
												}
											}).create();
							fail.show();
						} else {
							Dialog fail = new AlertDialog.Builder(
									User_login.this).setTitle("登录")
									.setMessage("用户名或密码不正确!")
									.setPositiveButton("确定", null).create();
							fail.show();
						}
					}
				}

			}

		});
		// loginfalse.setOnClickListener(new OnClickListener(){
		//
		// @Override
		// public void onClick(View arg0) {
		// // TODO Auto-generated method stub
		// Intent intent=new Intent(User_login.this,User_information.class);
		// startActivity(intent);
		// User_login.this.finish();
		// //android.os.Process.killProcess(android.os.Process.myPid());
		// }
		//
		// });

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent(User_login.this, User_information.class);
			startActivity(intent);
			User_login.this.finish();
		}
		return false;
	}
}
