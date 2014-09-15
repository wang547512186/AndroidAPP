package com.example.mapforwebservice;

import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FastPay extends Activity {
	private MapData mapdata;
	private List<String> detail;
	private SharedPreferences sp;
	private String userPhone = "";
	private Button matchingCodeBtn;
	private TextView matchingCodeText;
	private TextView confirmText;
	private String matchCodeString = "";
	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fast_pay);

		dialog = new ProgressDialog(this);
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// ���ý���������ʽΪԲ��ת���Ľ�����
		dialog.setCancelable(true);// �����Ƿ����ͨ�����Back��ȡ��
		dialog.setMessage("�����ϴ���֤��");

		sp = getSharedPreferences("login_state", Context.MODE_PRIVATE);
		mapdata = new MapData();
		userPhone = sp.getString("userPhone", "");
		if (userPhone.equals("")) {
			Intent intent = new Intent(FastPay.this, MainActivity.class);
			startActivity(intent);
			finish();
		} else {
			detail = mapdata.userInfor(userPhone);
		}
		confirmText = (TextView) findViewById(R.id.confirmText);
		matchingCodeText = (TextView) findViewById(R.id.matchingCodeText);
		matchingCodeText.setText(matchCodeString);
		matchingCodeBtn = (Button) findViewById(R.id.matchingCodeBtn);
		if (!matchCodeString.equals("")) {
			matchingCodeBtn.setText("ƥ����������");
			matchingCodeBtn.setEnabled(false);
			matchingCodeBtn.setBackgroundColor(Color.GRAY);
		}

		matchingCodeBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog.show();
				// /�����߳�
				MyThread thread = new MyThread();
				Thread mThread = new Thread(thread);
				mThread.start();

			}

		});

		ImageView returnbtn = (ImageView) findViewById(R.id.returnbtn);
		returnbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(FastPay.this, MainActivity.class);
				startActivity(intent);
				finish();
			}

		});

	}

	class MyThread implements Runnable {
		public void run() {
			// ִ�����ݲ��������漰��UI
			Message msg = new Message();
			matchCodeString = "";

			try {
				userPhone = detail.get(5).toString().trim();
				char[] phone = userPhone.toCharArray();
				for (int i = userPhone.length() - 4; i < userPhone.length(); i++) {
					matchCodeString = matchCodeString + phone[i];
				}
				matchCodeString = matchCodeString + randomStr(2);
				mapdata = new MapData();
				int i = 0;
				while (true) {
					if (mapdata.AddFastPayCode(userPhone,matchCodeString).equals("true")){
						msg.what = 1;
						break;
					}
					i++;
					Thread.sleep(1000);
					if(i==5){
						msg.what = 2;
						break;
					}

				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				msg.what = 2;
			}

			Bundle data = new Bundle();
			data.putString("str", matchCodeString);// COUNT�Ǳ�ǩ,handleMessage��ʹ��
			msg.setData(data);

			mHandler.sendMessage(msg); // ��Handler������Ϣ,����UI
		}
	}

	private Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				Bundle data = msg.getData();
				String str = data.getString("str");
				matchingCodeText.setText(str);
				matchingCodeBtn.setText("ƥ����������");
				matchingCodeBtn.setEnabled(false);
				matchingCodeBtn.setBackgroundColor(Color.GRAY);
				dialog.dismiss();
				break;
			case 2:
				dialog.dismiss();
				Toast.makeText(FastPay.this, "��������ʧ��,���Ժ�����", Toast.LENGTH_LONG).show();
				break;

			}
		};
	};

	private String randomStr(int length) {
		String string = "";
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			string = string + string.valueOf(random.nextInt(10));
		}
		return string;
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// Intent intent = new Intent(FastPay.this, MainActivity.class);
			// startActivity(intent);
			FastPay.this.finish();
		}
		return false;
	}
}
