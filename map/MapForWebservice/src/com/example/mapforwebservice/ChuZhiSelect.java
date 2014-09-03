package com.example.mapforwebservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ChuZhiSelect extends Activity {

	private String userName;
	private ListView chuzhiList;
	private List<Map<String, Object>> list;
	private MapData mapdata;
	private List<String> chuzhidetail;
	private List<String> hoteldetail;
	private String hotelId;
	private String hotelName;
	private String hotelAddress;
	private String hotelChuzhi;
	private String uid;
	private SharedPreferences sp;
	private LinearLayout historyEmpty;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chu_zhi_select);
		
		historyEmpty = (LinearLayout) findViewById(R.id.historyEmpty);
		chuzhiList = (ListView) findViewById(R.id.chuzhiList);

		sp = getSharedPreferences("login_state", Context.MODE_PRIVATE);
		userName = sp.getString("userName", "");
		uid = sp.getString("uid", "");
		mapdata = new MapData();
		chuzhidetail = mapdata.getUserChuzhi(uid);
		chuzhiList = (ListView) findViewById(R.id.chuzhiList);
		SimpleAdapter adapter = new SimpleAdapter(this, getData(),
				R.layout.user_chuzhi_item, new String[] { "hotelname",
						"hotelchuzhi", "hoteladdress" }, new int[] {
						R.id.hotelname, R.id.hotelchuzhi, R.id.hoteladdress });
		chuzhiList.setAdapter(adapter);
		chuzhiList.setDividerHeight(0);

		chuzhiList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(ChuZhiSelect.this, ChuZhiPay.class);
				Bundle bundle = new Bundle();
				HashMap<String, String> map = (HashMap<String, String>) chuzhiList
						.getItemAtPosition(position);
				String hotelId = map.get("hotelId");
				String hotelname = map.get("hotelname");
				String hotelchuzhi = map.get("hotelchuzhi");
				String hoteladdress = map.get("hoteladdress");
				bundle.putString("hotelId", hotelId);
				bundle.putString("hotelname", hotelname);
				bundle.putString("hotelchuzhi", hotelchuzhi);
				bundle.putString("hoteladdress", hoteladdress);
				intent.putExtras(bundle);
				startActivity(intent);
				finish();
			}

		});

		ImageView btn_return = (ImageView) findViewById(R.id.btn_return);
		btn_return.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}

		});

	}

	private List<Map<String, Object>> getData() {
		// TODO Auto-generated method stub
		int m = chuzhidetail.size() / 3;
		list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;

		try {
			for (int i = 0; i < m; i++) {
				map = new HashMap<String, Object>();

				hotelId = chuzhidetail.get(i * 3 + 1).toString();
				hoteldetail = mapdata.getHotelInfoById(hotelId);
				if (!hoteldetail.isEmpty()) {
					map.put("hotelname", hoteldetail.get(1).toString());
					map.put("hoteladdress", hoteldetail.get(6).toString());
				} else {
					continue;
				}
				map.put("hotelId", chuzhidetail.get(i * 3 + 1).toString());
				map.put("hotelchuzhi", chuzhidetail.get(i * 3 + 2).toString());
				list.add(map);

			}
		} catch (Exception e) {
			Toast.makeText(this, "Êý¾ÝÒì³£", Toast.LENGTH_LONG).show();
		}
		if (!list.isEmpty()) {
			historyEmpty.setVisibility(View.GONE);
		}
		return list;
	}

}
