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
	private String UserName;
	private String uid;
	private String nickName;
	private String fkezhu;
	private String ykezhu;
	private Boolean isLogin = false; // ��¼״̬
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
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// ���ý���������ʽΪԲ��ת���Ľ�����
		dialog.setMessage("���ڼ���");// ������
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
		UserName = sp.getString("userName", "");
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

		// �ж��Ƿ��¼
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
			// δ��¼ʱת����¼����
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
			// /������Ϣ
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
			// /����
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
			// /��ֵ
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
			// /��������
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(User_information.this,
						UserInvite.class);
				startActivity(intent);

			}
		});
		messageLayout.setOnClickListener(new OnClickListener() {
			// /�ҵ���Ϣ
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
			// /������
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
			// /�˳�
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				AlertDialog exitConfirm = new AlertDialog.Builder(
						User_information.this)
						.setTitle("��ʾ")
						.setMessage("ȷ���˳����˺��룿")
						.setPositiveButton("ȷ��",
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
						.setNegativeButton("ȡ��",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										// ȡ����ť�¼�
									}
								}).show();
			}
		});
		kezhupayLayout.setOnClickListener(new OnClickListener() {
			// /����������
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
			// /��ֵ����
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
			// /�ҵĻ�Ա
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
			// /������Ϣ
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(User_information.this,
						UserEquity.class);
				startActivity(intent);
			}
		});
		rulesLayout.setOnClickListener(new OnClickListener() {
			// /������Ϣ
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(User_information.this,
						UserRules.class);
				startActivity(intent);
			}
		});
		aboutLayout.setOnClickListener(new OnClickListener() {
			// ���ڿ���
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(User_information.this,
						AboutKeZhu.class);
				startActivity(intent);
			}
		});

		settingLayout.setOnClickListener(new OnClickListener() {
			// /����
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
					// ����ʱ����
					break;
				}
				case MotionEvent.ACTION_UP: {
					view.setBackgroundResource(R.color.white);
					// �����󴥷�
					break;
				}//
				case MotionEvent.ACTION_CANCEL: {
					view.setBackgroundResource(R.color.white);
					// �����󴥷�
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

	// /�߳�
	protected void checkThread() {
		Message msg = new Message();
		msg.what = 2;
		mHandler.sendMessage(msg);
	}

	class MyThread implements Runnable {

		public void run() {
			// ִ�����ݲ��������漰��UI
			mapdata = new MapData();
			Message msg = new Message();
			try {
				mapdata = new MapData();
				testList = mapdata.selectProvince();
				if (testList.isEmpty()) { // �޷��������ݿ�
					msg.what = 3;
				} else {
					detail = mapdata.userInfor(UserName);
					kezhudetail = mapdata.getUserKezhu(uid);
					if (!detail.isEmpty() && !kezhudetail.isEmpty()) { // �ɹ���ȡ����
						msg.what = 1;
					} else {
						msg.what = 4; // ���ݿ�������(δ��¼)
					}

				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				msg.what = 2;
			}
			mHandler.sendMessage(msg); // ��Handler������Ϣ,����UI
		}
	}

	public Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {

			switch (msg.what) {
			case 1: // �ɹ�
				timer.cancel();
				dialog.dismiss();
				userLayout.setVisibility(View.VISIBLE);
				totalkezhu = Double.valueOf(kezhudetail.get(0).toString());
				kezhuNum.setText(totalkezhu + "");
				curUserName.setText(detail.get(1).toString());
				break;
			case 2: // �����쳣
				timer.cancel();
				Dialog fail = new AlertDialog.Builder(User_information.this)
						.setTitle("��ʾ")
						.setMessage("�����쳣")
						.setPositiveButton("ȷ��",
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
			case 3: // �޷����ӷ�����
				timer.cancel();
				Dialog fail2 = new AlertDialog.Builder(User_information.this)
						.setTitle("��ʾ")
						.setMessage("�޷����ӷ�����")
						.setPositiveButton("ȷ��",
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
			case 4: // ������
				timer.cancel();
				Editor editor = sp.edit();
				editor.putString("userName", "");
				editor.commit();
				UserName = sp.getString("userName", "");
				uid = sp.getString("uid", "");
				userLayout.setVisibility(View.INVISIBLE);
				exitBtn.setVisibility(View.GONE);
				dialog.dismiss();
				break;

			}
		};
	};

}
