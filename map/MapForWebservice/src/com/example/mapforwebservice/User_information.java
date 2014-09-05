package com.example.mapforwebservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.example.mapforwebservice.MainActivity.MyThread;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class User_information extends Activity {
	private Timer timer;
	private ListView listview;
	private List<Map<String, Object>> list;
	private List<String> detail;
	private List<String> testList;
	private List<String> kezhudetail;
	private MapData mapdata;
	private String userPhone;
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
	private ProgressDialog dialog;

	private SharedPreferences sp;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_information);

		dialog = new ProgressDialog(this);
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
		dialog.setMessage("正在加载");// 进度条
		dialog.show();

		ImageView returnbtn = (ImageView) findViewById(R.id.returnbtn);
		returnbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(User_information.this, MainActivity.class);
				startActivity(intent);
				finish();
			}

		});

		sp = getSharedPreferences("login_state", Context.MODE_PRIVATE);
		userPhone = sp.getString("userPhone", "");
		uid = sp.getString("uid", "");
		exitBtn = (Button) findViewById(R.id.exitBtn);
		// if (UserName.equals("") || uid.equals("")) {
		// exitBtn.setVisibility(View.GONE);
		// }

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

		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				checkThread();
			}
		}, 6000);

		MyThread thread = new MyThread();
		Thread mThread = new Thread(thread);
		mThread.start();

		// 判断是否登录
		// if (UserName.equals("")) {
		// userLayout.setVisibility(View.INVISIBLE);
		// } else {
		// userLayout.setVisibility(View.VISIBLE);
		// detail = mapdata.userInfor(UserName);
		// kezhudetail = mapdata.getUserKezhu(uid);
		// try {
		//
		// // double totalkezhu = Double.valueOf(detail.get(7).toString())
		// // + Double.valueOf(detail.get(8).toString());
		// if (kezhudetail.size() != 0) {
		// totalkezhu = Double.valueOf(kezhudetail.get(0).toString());
		// kezhuNum.setText(totalkezhu + "");
		// } else {
		// totalkezhu = 0.00;
		// kezhuNum.setText(totalkezhu + "");
		// }
		//
		// curUserName.setText(detail.get(1).toString());
		// } catch (Exception e) {
		// Editor editor = sp.edit();
		// editor.putString("userName", "");
		// editor.commit();
		// UserName = sp.getString("userName", "");
		// uid = sp.getString("uid", "");
		// userLayout.setVisibility(View.INVISIBLE);
		// exitBtn.setVisibility(View.GONE);
		// Log.e("catch~~~~~~~~UserName~~", UserName);
		// }
		//
		// }

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
				intent.putExtra("userPhone", userPhone);
				startActivity(intent);
				User_information.this.finish();
			}
		});
		orderLayout.setOnClickListener(new OnClickListener() {
			// /订单
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (userPhone.equals("")) {
					turnToLogin();
				} else {
					Intent intent = new Intent(User_information.this,
							User_order.class);
					intent.putExtra("userPhone", userPhone);
					startActivity(intent);
				}
			}
		});
		chuzhiLayout.setOnClickListener(new OnClickListener() {
			// /储值
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (userPhone.equals("")) {
					turnToLogin();

				} else {
					Intent intent = new Intent(User_information.this,
							UserChuzhiCenter.class);
					intent.putExtra("userPhone", userPhone);
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
				if (userPhone.equals("")) {
					turnToLogin();

				} else {
					Intent intent = new Intent(User_information.this,
							User_message.class);
					intent.putExtra("userPhone", userPhone);
					startActivity(intent);
				}
			}
		});

		kezhubiLayout.setOnClickListener(new OnClickListener() {
			// /客主币
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (userPhone.equals("")) {
					turnToLogin();
				} else {
					Intent intent = new Intent(User_information.this,
							UserKezhuCenter.class);
					intent.putExtra("userPhone", userPhone);
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
				if (userPhone.equals("")) {
					turnToLogin();

				} else {
					mapdata = new MapData();
					detail = mapdata.userInfor(userPhone);
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
				if (userPhone.equals("")) {
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
				if (userPhone.equals("")) {
					turnToLogin();

				} else {
					Intent intent = new Intent(User_information.this,
							MyInvited.class);
					intent.putExtra("userPhone", userPhone);
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

	// /线程
	protected void checkThread() {
		Message msg = new Message();
		msg.what = 2;
		mHandler.sendMessage(msg);
	}

	class MyThread implements Runnable {

		public void run() {
			// 执行数据操作，不涉及到UI
			mapdata = new MapData();
			Message msg = new Message();
			try {
				mapdata = new MapData();
				testList = mapdata.selectProvince();
				if (testList.isEmpty()) { // 无法连接数据库
					msg.what = 3;
				} else {
					detail = mapdata.userInfor(userPhone);
					kezhudetail = mapdata.getUserKezhu(uid);
					if (!detail.isEmpty() && !kezhudetail.isEmpty()) { // 成功读取数据
						msg.what = 1;
					} else {
						msg.what = 4; // 数据库无数据(未登录)
					}

				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				msg.what = 2;
			}
			mHandler.sendMessage(msg); // 向Handler发送消息,更新UI
		}
	}

	public Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {

			switch (msg.what) {
			case 1: // 成功
				timer.cancel();
				dialog.dismiss();
				userLayout.setVisibility(View.VISIBLE);
				totalkezhu = Double.valueOf(kezhudetail.get(0).toString());
				kezhuNum.setText(totalkezhu + "");
				curUserName.setText(detail.get(1).toString());
				break;
			case 2: // 网络异常
				timer.cancel();
				Dialog fail = new AlertDialog.Builder(User_information.this)
						.setTitle("提示")
						.setMessage("网络异常")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										Intent intent = new Intent(
												User_information.this,
												MainActivity.class);
										startActivity(intent);
										User_information.this.finish();
									}
								}).create();
				fail.show();
				dialog.dismiss();
				break;
			case 3: // 无法连接服务器
				timer.cancel();
				Dialog fail2 = new AlertDialog.Builder(User_information.this)
						.setTitle("提示")
						.setMessage("无法连接服务器")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										Intent intent = new Intent(
												User_information.this,
												MainActivity.class);
										startActivity(intent);
										User_information.this.finish();
									}
								}).create();
				fail2.show();
				dialog.dismiss();
				break;
			case 4: // 无数据
				timer.cancel();
				Editor editor = sp.edit();
				editor.putString("userPhone", "");
				editor.commit();
				userPhone = sp.getString("userPhone", "");
				uid = sp.getString("uid", "");
				userLayout.setVisibility(View.INVISIBLE);
				exitBtn.setVisibility(View.GONE);
				dialog.dismiss();
				break;

			}
		};
	};

}
