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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class User_infor_detail extends Activity {
	private ListView listview;
	private List<Map<String, Object>> list;
	private List<String> detail;
	private MapData mapdata;
	private String UserName;
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
		UserName = intent.getStringExtra("UserName");
		mapdata = new MapData();
		detail = mapdata.userInfor(UserName);

		passwordLayout = (LinearLayout) findViewById(R.id.passwordLayout);
		passwordLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(User_infor_detail.this,
						User_password.class);
				Bundle bundle = new Bundle();
				bundle.putString("UserName", UserName);
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
				usersexStr = "��";
			}
			if (detail.get(4).toString().equals("0")) {
				usersexStr = "Ů";
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
					.setTitle("��ʾ").setMessage("�����쳣,���Ժ�����")
					.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
						
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
				bundle.putString("UserName", UserName);
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

	}

	// private List<Map<String, Object>> getData(){
	// list=new ArrayList<Map<String,Object>>();
	// Map<String,Object> map=new HashMap<String,Object>();
	// map.put("title", "�Ա�");
	// if(detail.get(9).toString().equals("1"))
	// {
	// map.put("value", "��");
	// }
	// if(detail.get(9).toString().equals("0"))
	// {
	// map.put("value", "Ů");
	// }
	// list.add(map);
	//
	// map=new HashMap<String,Object>();
	// map.put("title", "�ù�����");
	// map.put("value", detail.get(7).toString());
	// list.add(map);
	//
	// map=new HashMap<String,Object>();
	// map.put("title", "�ҵ�����");
	// map.put("value", detail.get(6).toString());
	// list.add(map);
	//
	// map=new HashMap<String,Object>();
	// map.put("title", "�ҵĵ�ַ");
	// map.put("value", detail.get(10).toString());
	// list.add(map);
	//
	// map=new HashMap<String,Object>();
	// map.put("title", "�ҵĵ绰");
	// map.put("value", detail.get(12).toString());
	// list.add(map);
	//
	// map=new HashMap<String,Object>();
	// map.put("title", "����¼");
	// map.put("value", detail.get(3).toString());
	// list.add(map);
	//
	// map=new HashMap<String,Object>();
	// map.put("title", "����Ծ");
	// map.put("value", detail.get(4).toString());
	// list.add(map);
	//
	// return list;
	// }
	// class user_itemOnClickListener implements OnItemClickListener{
	// public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
	// long arg3) {
	// // TODO Auto-generated method stub
	// }
	// }

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
