package com.example.mapforwebservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class SearchHotel extends Activity {
	private ListView listview;
	private List<Map<String, Object>> list;
	private List<String> orderlist;
	private EditText searchText;
	private SimpleAdapter adapter;
	private MapData mapdata;
	private String curCityString;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_hotel);
		mapdata = new MapData();
		curCityString=MainActivity.getCityText();
		orderlist = mapdata.findShops(
				curCityString.substring(0, curCityString.length() - 1), "");
		
		searchText = (EditText) findViewById(R.id.searchText);
		// ËÑË÷À¸
		searchText.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String str = searchText.getText().toString();
				if (str.isEmpty()) {
					NormalResult();
				} else {
					SearchResult(str);
				}
				listview.setAdapter(adapter);
				listview.setDividerHeight(0);

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}

		});
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

		listview = (ListView) findViewById(R.id.searchList);
		adapter = new SimpleAdapter(this, getData(), R.layout.hotellist_item,
				new String[] { "img", "title", "address" }, new int[] { R.id.img,
						R.id.title, R.id.address });
		listview.setAdapter(adapter);
		listview.setDividerHeight(0);

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(SearchHotel.this, KezhuPay.class);
				HashMap<String, String> map = (HashMap<String, String>) listview
						.getItemAtPosition(position);
				String hotelname = map.get("title");
				String hoteladdress = map.get("address");
				String hotelId = map.get("hotelId");
				intent.putExtra("strHotelId", hotelId);
				intent.putExtra("strHotelName", hotelname);
				intent.putExtra("strHotelAddress", hoteladdress);
				SearchHotel.this.setResult(RESULT_OK, intent);
				SearchHotel.this.finish();
			}

		});

	}

	private void NormalResult() {
		adapter = new SimpleAdapter(this, getData(), R.layout.hotellist_item,
				new String[] { "img", "title", "address" }, new int[] { R.id.img,
						R.id.title, R.id.address });

	}

	private void SearchResult(String str) {
		adapter = new SimpleAdapter(this, searchData(str, getData()),
				R.layout.hotellist_item,
				new String[] { "img", "title", "address" }, new int[] { R.id.img,
						R.id.title, R.id.address });
	}

	private List<Map<String, Object>> searchData(String str,
			List<Map<String, Object>> oldlist) {
		int number = oldlist.size();
		List<Map<String, Object>> newlist  = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < number; i++) {
			Map<String, Object> map = oldlist.get(i);
			String hotelname = (String) map.get("title");
			if (hotelname.contains(str)) {
				newlist.add(map);
			}
		}
		return newlist;

	}

	private List<Map<String, Object>> getData() {
		// TODO Auto-generated method stub
		int m = orderlist.size() / 5;
		list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;

		for (int i = 0; i < m; i++) {
			map = new HashMap<String, Object>();
			map.put("img", R.drawable.hotel1);
			map.put("hotelId", orderlist.get(i * 5 + 0).toString());
			map.put("title", orderlist.get(i * 5 + 1).toString());
			map.put("address", orderlist.get(i * 5 + 3).toString());
			list.add(map);

		}
		return list;

	}
}
