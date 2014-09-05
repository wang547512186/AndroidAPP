package com.example.mapforwebservice;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class User_infor_detail extends Activity {
	private ListView listview;
	private List<Map<String, Object>> list;
	private List<String> detail;
	private MapData mapdata;
	private String userPhone;
	private LinearLayout passwordLayout;
	private TextView btn_changeInfo;
	private String usernameStr;
	private String usersexStr;
	private String useremailStr;
	private String userphoneStr;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_infor_detail);
		// listview=(ListView)findViewById(R.id.userinfor);
		Intent intent = getIntent();
		userPhone = intent.getStringExtra("userPhone");
		mapdata = new MapData();
		detail = mapdata.userInfor(userPhone);

		passwordLayout = (LinearLayout) findViewById(R.id.passwordLayout);
		passwordLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(User_infor_detail.this,
						User_password.class);
				Bundle bundle = new Bundle();
				bundle.putString("userPhone", userPhone);
				intent.putExtras(bundle);
				startActivity(intent);
				// User_infor_detail.this.finish();
			}

		});
		User_information.changeBackground(passwordLayout);

		TextView user_name = (TextView) findViewById(R.id.user_name);
		TextView user_sex = (TextView) findViewById(R.id.user_sex);
		TextView user_email = (TextView) findViewById(R.id.user_email);
		TextView user_phone = (TextView) findViewById(R.id.user_phone);
		try {
			usernameStr = detail.get(1).toString().trim();
			if (detail.get(4).toString().equals("1")) {
				usersexStr = "男";
			}
			if (detail.get(4).toString().equals("0")) {
				usersexStr = "女";
			}
			useremailStr = detail.get(3).toString().trim();
			// useraddressStr = detail.get(4).toString().trim();
			userphoneStr = detail.get(5).toString().trim();

			user_name.setText(usernameStr);
			user_sex.setText(usersexStr);
			user_email.setText(useremailStr);
			// user_address.setText(useraddressStr);
			user_phone.setText(userphoneStr);

		} catch (Exception e) {
			Dialog fail = new AlertDialog.Builder(User_infor_detail.this)
					.setTitle("提示").setMessage("网络异常,请稍后再试")
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Intent intent = new Intent();
							intent.setClass(User_infor_detail.this, User_information.class);
							startActivity(intent);
							finish();
						}
					}).create();
			fail.show();

		}

		// listview.setAdapter(new ArrayAdapter<String>(this,
		// android.R.layout.simple_expandable_list_item_1,getData()));
		// SimpleAdapter adapter=new
		// SimpleAdapter(this,getData(),R.layout.user_detail_item,
		// new String[] {"title","value"},
		// new int[] {R.id.title,R.id.values});
		// listview.setAdapter(adapter);
		// listview.setOnItemClickListener(new user_itemOnClickListener());

		btn_changeInfo = (TextView) findViewById(R.id.btn_changeInfo);
		btn_changeInfo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(User_infor_detail.this,
						ChangeInfo.class);
				Bundle bundle = new Bundle();
				bundle.putString("userPhone", userPhone);
				bundle.putString("usernameStr", usernameStr);
				bundle.putString("usersexStr", usersexStr);
				bundle.putString("useremailStr", useremailStr);
				bundle.putString("userphoneStr", userphoneStr);
				intent.putExtras(bundle);
				startActivity(intent);
				User_infor_detail.this.finish();
			}

		});
		User_information.changeBackground(btn_changeInfo);

		ImageView returnbtn = (ImageView) findViewById(R.id.returnbtn);
		returnbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(User_infor_detail.this, User_information.class);
				startActivity(intent);
				User_infor_detail.this.finish();
			}

		});
		
	}

	
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent();
			intent.setClass(User_infor_detail.this, User_information.class);
			startActivity(intent);
			User_infor_detail.this.finish();
		}
		return false;
	}
}
