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
import android.widget.ListView;

public class MyInvited extends Activity {
	private ListView myInviteListView;
	private MapData mapdata;
	List<String> data = new ArrayList<String>();
	private String UserName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_invited);
		mapdata = new MapData();

		Bundle bundle = this.getIntent().getExtras();
		UserName = bundle.getString("UserName");
		try {
			data = mapdata.findInvitePhone(UserName);
		} catch (Exception e) {
			data = null;
		}
		myInviteListView = (ListView) findViewById(R.id.myInviteListView);
		myInviteListView.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_expandable_list_item_1, getData()));

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

	private List<String> getData() {

		return data;
	}
}
