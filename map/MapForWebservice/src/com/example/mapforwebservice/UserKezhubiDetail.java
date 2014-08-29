package com.example.mapforwebservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.mapforwebservice.User_order.order_itemOnClickListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class UserKezhubiDetail extends Activity {
	private ListView listview;
	private List<Map<String, Object>> list;
	private List<String> orderlist;
	private MapData mapdata;

	private String UserName = "";
	private String uid = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_kezhubi_detail);
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

		Intent intent = getIntent();
		UserName = intent.getStringExtra("UserName");
		uid = intent.getStringExtra("uid");
		mapdata = new MapData();
		orderlist = mapdata.getKezhuhistory(uid);
		listview = (ListView) findViewById(R.id.kezhubiList);
		SimpleAdapter adapter = new SimpleAdapter(this, getData(),
				R.layout.user_kezhubi_detail_item, new String[] { "hotelname",
						"beginDate", "orderPrice", "orderid", "kezhubi",
						"kezhubi2" }, new int[] { R.id.hotelname,
						R.id.orderdate, R.id.orderprice, R.id.orderid,
						R.id.kezhubi, R.id.kezhubi2 });
		listview.setAdapter(adapter);
		listview.setDividerHeight(0);
	}

	private List<Map<String, Object>> getData() {
		// TODO Auto-generated method stub
		int m = orderlist.size() / 5;
		list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		double kezhuNum;
		for (int i = 0; i < m; i++) {
			kezhuNum = Double.valueOf(orderlist.get(i * 5 + 3).toString());
			if (kezhuNum > 0) {
				map = new HashMap<String, Object>();
				map.put("hotelname", orderlist.get(i * 5 + 1).toString());
				map.put("beginDate", orderlist.get(i * 5 + 4).toString());
				map.put("orderPrice", orderlist.get(i * 5 + 2).toString()
						+ "  Ԫ");
				map.put("orderid", "");
				map.put("kezhubi", orderlist.get(i * 5 + 3).toString());
				map.put("kezhubi2", orderlist.get(i * 5 + 3).toString());
				list.add(map);
			}

		}
		return list;
	}

}
