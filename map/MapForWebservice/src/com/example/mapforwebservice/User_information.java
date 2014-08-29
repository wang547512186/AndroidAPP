package com.example.mapforwebservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class User_information extends Activity {
	private ListView listview;
	private List<Map<String, Object>> list;
	private List<String> detail;
	private List<String> kezhudetail;
	private MapData mapdata;
	private String UserName;
	private String uid;
	private String nickName;
	private String fkezhu;
	private String ykezhu;
	private Boolean isLogin = false; // 登录状态
	private LinearLayout userLayout;
	private LinearLayout orderLayout;
	private LinearLayout chuzhiLayout;
	private LinearLayout messageLayout;
	private Button exitBtn;
	private LinearLayout kezhubiLayout;
	private LinearLayout kezhupayLayout;
	private LinearLayout chuzhipayLayout;
	private LinearLayout vipLayout;
	private LinearLayout inviteLayout;
	private LinearLayout aboutLayout;
	// private LinearLayout registerLayout;
	private LinearLayout settingLayout;
	private LinearLayout noLoginLayout;
	private double totalkezhu;
	private LinearLayout equityLayout;
	private LinearLayout rulesLayout;
	private TextView kezhuNum;
	private TextView curUserName;

	private SharedPreferences sp;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_information);
		mapdata = new MapData();
		sp = getSharedPreferences("login_state", Context.MODE_PRIVATE);
		UserName = sp.getString("userName", "");
		uid = sp.getString("uid", "");
		exitBtn = (Button) findViewById(R.id.exitBtn);

		if (UserName.equals("") || uid.equals("")) {
			exitBtn.setVisibility(View.GONE);
		}

		userLayout = (LinearLayout) findViewById(R.id.userInfoLayout);
		orderLayout = (LinearLayout) findViewById(R.id.orderLayout);
		chuzhiLayout = (LinearLayout) findViewById(R.id.chuzhiLayout);
		messageLayout = (LinearLayout) findViewById(R.id.messageLayout);
		kezhubiLayout = (LinearLayout) findViewById(R.id.kezhubiLayout);
		kezhupayLayout = (LinearLayout) findViewById(R.id.kezhupayLayout);
		chuzhipayLayout = (LinearLayout) findViewById(R.id.chuzhipayLayout);
		kezhupayLayout = (LinearLayout) findViewById(R.id.kezhupayLayout);
		chuzhipayLayout = (LinearLayout) findViewById(R.id.chuzhipayLayout);
		vipLayout = (LinearLayout) findViewById(R.id.vipLayout);
		inviteLayout = (LinearLayout) findViewById(R.id.inviteLayout);
		aboutLayout = (LinearLayout) findViewById(R.id.aboutLayout);
		// registerLayout = (LinearLayout) findViewById(R.id.registerLayout);
		settingLayout = (LinearLayout) findViewById(R.id.settingLayout);
		equityLayout = (LinearLayout) findViewById(R.id.equityLayout);
		rulesLayout = (LinearLayout) findViewById(R.id.rulesLayout);
		changeBackground(userLayout);
		changeBackground(orderLayout);
		changeBackground(chuzhiLayout);
		changeBackground(messageLayout);
		changeBackground(kezhubiLayout);
		changeBackground(kezhupayLayout);
		changeBackground(chuzhipayLayout);
		changeBackground(vipLayout);
		changeBackground(inviteLayout);
		changeBackground(aboutLayout);
		// changeBackground(registerLayout);
		changeBackground(settingLayout);
		changeBackground(equityLayout);
		changeBackground(rulesLayout);

		kezhuNum = (TextView) findViewById(R.id.kezhuNum);
		curUserName = (TextView) findViewById(R.id.UserName);
		// 判断是否登录
		if (UserName.equals("")) {
			userLayout.setVisibility(View.INVISIBLE);
		} else {
			userLayout.setVisibility(View.VISIBLE);
			detail = mapdata.userInfor(UserName);
			kezhudetail = mapdata.getUserKezhu(uid);
			try {

				// double totalkezhu = Double.valueOf(detail.get(7).toString())
				// + Double.valueOf(detail.get(8).toString());
				if (kezhudetail.size() != 0) {
					totalkezhu = Double.valueOf(kezhudetail.get(0).toString());
					kezhuNum.setText(totalkezhu + "");
				} else {
					totalkezhu = 0.00;
					kezhuNum.setText(totalkezhu + "");
				}

				curUserName.setText(detail.get(1).toString());
			} catch (Exception e) {
				Editor editor = sp.edit();
				editor.putString("userName", "");
				editor.commit();
				UserName = sp.getString("userName", "");
				uid = sp.getString("uid", "");
				userLayout.setVisibility(View.INVISIBLE);
				exitBtn.setVisibility(View.GONE);
				Log.e("catch~~~~~~~~UserName~~", UserName);
			}

		}

		noLoginLayout = (LinearLayout) findViewById(R.id.noLoginLayout);
		changeBackground(noLoginLayout);
		noLoginLayout.setOnClickListener(new OnClickListener() {
			// 未登录时转到登录界面
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(User_information.this,
						User_login.class);
				startActivity(intent);
				finish();
			}
		});

		userLayout.setOnClickListener(new OnClickListener() {
			// /个人信息
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(User_information.this,
						User_infor_detail.class);
				intent.putExtra("UserName", UserName);
				startActivity(intent);
				User_information.this.finish();
			}
		});
		orderLayout.setOnClickListener(new OnClickListener() {
			// /订单
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (UserName.equals("")) {
					turnToLogin();

				} else {
					Intent intent = new Intent(User_information.this,
							User_order.class);
					intent.putExtra("UserName", UserName);
					startActivity(intent);
				}
			}
		});
		chuzhiLayout.setOnClickListener(new OnClickListener() {
			// /储值
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (UserName.equals("")) {
					turnToLogin();

				} else {
					Intent intent = new Intent(User_information.this,
							UserChuzhiCenter.class);
					intent.putExtra("UserName", UserName);
					intent.putExtra("uid", uid);
					startActivity(intent);
				}
			}
		});
		inviteLayout.setOnClickListener(new OnClickListener() {
			// /邀请朋友
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(User_information.this,
						UserInvite.class);
				startActivity(intent);

			}
		});
		messageLayout.setOnClickListener(new OnClickListener() {
			// /我的消息
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (UserName.equals("")) {
					turnToLogin();

				} else {
					Intent intent = new Intent(User_information.this,
							User_message.class);
					intent.putExtra("UserName", UserName);
					startActivity(intent);
				}
			}
		});

		kezhubiLayout.setOnClickListener(new OnClickListener() {
			// /客主币
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (UserName.equals("")) {
					turnToLogin();
				} else {
					Intent intent = new Intent(User_information.this,
							UserKezhuCenter.class);
					intent.putExtra("UserName", UserName);
					intent.putExtra("uid", uid);
					startActivity(intent);
				}
			}
		});
		exitBtn.setOnClickListener(new OnClickListener() {
			// /退出
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				AlertDialog exitConfirm = new AlertDialog.Builder(
						User_information.this)
						.setTitle("提示")
						.setMessage("确认退出该账号码？")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int whichButton) {

										sp.edit().clear().commit();
										Intent intent = new Intent(
												User_information.this,
												User_information.class);
										startActivity(intent);
										finish();
									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										// 取消按钮事件
									}
								}).show();
			}
		});
		kezhupayLayout.setOnClickListener(new OnClickListener() {
			// /客主币消费
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (UserName.equals("")) {
					turnToLogin();

				} else {
					mapdata = new MapData();
					detail = mapdata.userInfor(UserName);
					// nickName = detail.get(1).toString();
					// Double kezhuNumber = Double.valueOf(detail.get(7)
					// .toString())
					// + Double.valueOf(detail.get(8).toString());
					Intent intent = new Intent(User_information.this,
							KezhuPay.class);
					// intent.putExtra("UserName", UserName);
					// intent.putExtra("UserPwd", detail.get(2).toString());
					// intent.putExtra("fkezhu",
					// Double.valueOf(detail.get(7).toString()));
					// intent.putExtra("ykezhu",
					// Double.valueOf(detail.get(8).toString()));
					// intent.putExtra("nickName", nickName);

					startActivity(intent);
					finish();
				}

			}
		});
		chuzhipayLayout.setOnClickListener(new OnClickListener() {
			// /储值消费
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (UserName.equals("")) {
					turnToLogin();
				} else {
					Intent intent = new Intent(User_information.this,
							ChuZhiSelect.class);
					startActivity(intent);
				}

			}
		});
		vipLayout.setOnClickListener(new OnClickListener() {
			// /我的会员
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (UserName.equals("")) {
					turnToLogin();

				} else {
					Intent intent = new Intent(User_information.this,
							MyInvited.class);
					intent.putExtra("UserName", UserName);
					startActivity(intent);
				}
			}
		});
		equityLayout.setOnClickListener(new OnClickListener() {
			// /个人信息
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(User_information.this,
						UserEquity.class);
				startActivity(intent);
			}
		});
		rulesLayout.setOnClickListener(new OnClickListener() {
			// /个人信息
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(User_information.this,
						UserRules.class);
				startActivity(intent);
			}
		});
		aboutLayout.setOnClickListener(new OnClickListener() {
			// 关于客主
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(User_information.this,
						AboutKeZhu.class);
				startActivity(intent);
			}
		});

		settingLayout.setOnClickListener(new OnClickListener() {
			// /设置
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(User_information.this,
						UserSetting.class);
				startActivity(intent);
			}
		});

	}

	public static void changeBackground(final View view) {
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
					view.setBackgroundResource(R.color.white);
					// 触摸后触发
					break;
				}//
				case MotionEvent.ACTION_CANCEL: {
					view.setBackgroundResource(R.color.white);
					// 触摸后触发
					break;
				}
				}

				return false;
			}

		});
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent();
			intent.setClass(User_information.this, MainActivity.class);
			startActivity(intent);
			User_information.this.finish();
		}
		return false;
	}

	public void turnToLogin() {
		Intent intent = new Intent(User_information.this, User_login.class);
		startActivity(intent);
		finish();
	}
}
