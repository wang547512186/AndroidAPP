package com.example.mapforwebservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.mapforwebservice.User_order.order_itemOnClickListener;

import android.R.string;
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
import android.widget.Toast;

public class UserKezhubiDetail extends Activity {
	private ListView listview;
	private List<Map<String, Object>> list;
	private List<String> orderlist;
	private List<String> hotellist;
	private MapData mapdata;

	private String UserName = "";
	private String uid = "";
	private String hotelid = "";
	private LinearLayout historyEmpty;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_kezhubi_detail);

		historyEmpty = (LinearLayout) findViewById(R.id.historyEmpty);
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
						"kezhubi2", "ordertype" }, new int[] { R.id.hotelname,
						R.id.orderdate, R.id.orderprice, R.id.orderid,
						R.id.kezhubi, R.id.kezhubi2, R.id.typeid });
		listview.setAdapter(adapter);
		listview.setDividerHeight(0);
	}

	private List<Map<String, Object>> getData() {
		// TODO Auto-generated method stub
		int m = orderlist.size() / 6;
		list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		double kezhuNum;
		String typeStr = "";
		mapdata = new MapData();
		try {
			for (int i = 0; i < m; i++) {
				kezhuNum = Double.valueOf(orderlist.get(i * 6 + 3).toString());
				hotelid = orderlist.get(i * 6 + 1).toString();

				if (kezhuNum > 0) {
					hotellist = mapdata.hotelshopInfo(hotelid);
					map = new HashMap<String, Object>();
					map.put("orderid", orderlist.get(i * 6 + 0).toString());
					map.put("hotelname", hotellist.get(0).toString());
					map.put("orderPrice", orderlist.get(i * 6 + 2).toString()
							+ "  元");
					map.put("kezhubi", orderlist.get(i * 6 + 3).toString());
					map.put("kezhubi2", orderlist.get(i * 6 + 3).toString());
					map.put("beginDate", orderlist.get(i * 6 + 4).toString());
					if (orderlist.get(i * 6 + 5).toString().equals("1")) {
						typeStr = "现金消费";
					} else {
						typeStr = "积分消费";
					}
					map.put("ordertype", typeStr);

					list.add(map);
				}
			}
		} catch (Exception e) {
			Toast.makeText(UserKezhubiDetail.this, "数据异常", Toast.LENGTH_LONG)
					.show();
		}

		if (!list.isEmpty()) {
			historyEmpty.setVisibility(View.GONE);
		}
		return list;
	}

}
