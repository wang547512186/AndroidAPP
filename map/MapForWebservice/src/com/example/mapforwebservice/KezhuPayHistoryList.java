package com.example.mapforwebservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class KezhuPayHistoryList extends Activity {
	private ListView listview;
	private List<Map<String, Object>> list;
	private List<String> orderlist;
	private List<String> hotellist;
	private String hotelid = "";

	private MapData mapdata;
	private String UserName = "";
	private String uid = "";
	private LinearLayout historyEmpty;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kezhu_pay_history);
		historyEmpty = (LinearLayout) findViewById(R.id.historyEmpty);
		Intent intent = getIntent();
		UserName = intent.getStringExtra("UserName");
		uid = intent.getStringExtra("uid");
		mapdata = new MapData();
		orderlist = mapdata.getKezhuhistory(uid);
		listview = (ListView) findViewById(R.id.kezhuPayHistorylist);
		SimpleAdapter adapter = new SimpleAdapter(this, getData(),
				R.layout.kezhu_pay_history_item, new String[] {
						"kezhuHotelName", "orderid",
						"kezhuHotelDate", "kezhuHotelNumber" }, new int[] {
						R.id.kezhuHotelName, R.id.orderid,
						R.id.kezhuHotelDate, R.id.kezhuHotelNumber });
		listview.setAdapter(adapter);
		listview.setDividerHeight(0);

		// ·µ»Ø°´Å¥
		ImageView returnbtn = (ImageView) findViewById(R.id.returnbtn);
		returnbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// Intent intent = new Intent();
				// intent.setClass(detail_information.this, MainActivity.class);
				// startActivity(intent);
				finish();
			}

		});
	}

	private List<Map<String, Object>> getData() {
		// TODO Auto-generated method stub
		int m = orderlist.size() / 6;
		list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		double kezhuNum;
		String typeStr = "";
		mapdata = new MapData();
		for (int i = 0; i < m; i++) {
			kezhuNum = Double.valueOf(orderlist.get(i * 6 + 3).toString());
			hotelid = orderlist.get(i * 6 + 1).toString();
			
			if (kezhuNum < 0) {
				hotellist = mapdata.hotelshopInfo(hotelid);
				if(!hotellist.isEmpty()){
					map = new HashMap<String, Object>();
					map.put("kezhuHotelName", hotellist.get(0).toString());
					map.put("orderid", orderlist.get(i * 6 + 0).toString());
					map.put("kezhuHotelDate", orderlist.get(i * 6 + 4).toString());
					map.put("kezhuHotelNumber",  orderlist.get(i * 6 + 3).toString());
					list.add(map);
				}
			}

		}
		if (!list.isEmpty()) {
			historyEmpty.setVisibility(View.GONE);
		}
		return list;
	}
}
