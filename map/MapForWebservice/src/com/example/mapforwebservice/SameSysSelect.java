package com.example.mapforwebservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SameSysSelect extends Activity {
	private List<String> hotelList;
	private ListView chuzhiList;
	private String hoteltotalId;
	private List<Map<String, Object>> list;
	private SimpleAdapter adapter;
	private String hotelId;
	private String hotelName;
	private String hotelAddress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.same_sys_select);
		Intent intent = getIntent();
		hoteltotalId = intent.getStringExtra("hoteltotalId");
		MapData mapData = new MapData();

		hotelList = mapData.getSamesytemname(hoteltotalId);

		chuzhiList = (ListView) findViewById(R.id.chuzhiList);
		SimpleAdapter adapter = new SimpleAdapter(this, getData(),
				R.layout.same_chuzhi_item, new String[] { "hotelName",
						"hotelAddress" }, new int[] { R.id.hotelname,
						R.id.hoteladdress });
		chuzhiList.setAdapter(adapter);
		chuzhiList.setDividerHeight(0);

		chuzhiList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(SameSysSelect.this, ChuZhiPay.class);
				
				HashMap<String, String> map = (HashMap<String, String>) chuzhiList
						.getItemAtPosition(position);
				String hotelname = map.get("hotelId");
				String hoteladdress = map.get("hotelName");
				String hotelId = map.get("hotelAddress");
				intent.putExtra("strHotelId", hotelId);
				intent.putExtra("strHotelName", hotelname);
				intent.putExtra("strHotelAddress", hoteladdress);
				SameSysSelect.this.setResult(RESULT_OK, intent);
				SameSysSelect.this.finish();
			}

		});

	}
	
	private List<Map<String, Object>> getData() {
		// TODO Auto-generated method stub
		int m = hotelList.size() / 3;
		list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;

		try {
			for (int i = 0; i < m; i++) {
				map = new HashMap<String, Object>();
				map.put("hotelId", hotelList.get(i * 3 + 0).toString());
				map.put("hotelName", hotelList.get(i * 3 + 1).toString());
				map.put("hotelAddress", hotelList.get(i * 3 + 2).toString());
				list.add(map);

			}
		} catch (Exception e) {
			Toast.makeText(this, "Êý¾ÝÒì³£", Toast.LENGTH_LONG).show();
		}
		return list;
	}
	
}
