package com.example.mapforwebservice;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MyInvited extends Activity {
	private MapData mapdata;
	List<String> dataList = new ArrayList<String>();
	private String userPhone;
	private String myInvitedNum = "0";
	private TextView myInvitedTotal;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_invited);
		myInvitedTotal = (TextView) findViewById(R.id.myInvitedTotal);
		mapdata = new MapData();
		Bundle bundle = this.getIntent().getExtras();
		userPhone = bundle.getString("userPhone");
		try {
			dataList = mapdata.findInvitePhone(userPhone);
			myInvitedNum = dataList.get(0).toString();
		} catch (Exception e) {
		}

		myInvitedTotal.setText(myInvitedNum);

		ImageView returnbtn = (ImageView) findViewById(R.id.returnbtn);
		returnbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MyInvited.this, User_information.class);
				startActivity(intent);
				finish();
			}

		});
	}

}
