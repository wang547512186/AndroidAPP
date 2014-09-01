package com.example.mapforwebservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.mapforwebservice.detail_information.hotel_itemOnClickListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class NearbyActivity extends Activity {
	private boolean searchState = false;
	private ListView listview;
	private List<Map<String, Object>> list;
	private List<String> orderlist;
	private SimpleAdapter adapter;
	private MapData mapdata;
	private String curCityString;
	private String hotelId;
	private String shopStyle;
	private TextView moreLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nearby_activity);
		moreLayout = (TextView)findViewById(R.id.moreLayout);

		TextView topTextView = (TextView) findViewById(R.id.topTextView);
		topTextView.setFocusable(true);
		topTextView.setFocusableInTouchMode(true);
		topTextView.requestFocus();

		mapdata = new MapData();
		curCityString = MainActivity.getCityText();
		orderlist = mapdata.findShops(
				curCityString.substring(0, curCityString.length() - 1), "");

		listview = (ListView) findViewById(R.id.nearList);
		adapter = new SimpleAdapter(this, getData(), R.layout.hotellist_item,
				new String[] { "img", "title", "address" }, new int[] {
						R.id.img, R.id.title, R.id.address });
		listview.setAdapter(adapter);
		Utility.setListViewHeightBasedOnChildren(listview);
		listview.setOnItemClickListener(new hotel_itemOnClickListener());

		moreLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(NearbyActivity.this, more_information.class);
				startActivity(intent);
				finish();
			}
		});
		
		ImageView returnbtn = (ImageView) findViewById(R.id.returnbtn);
		returnbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}

		});
		ImageView searchbtn = (ImageView) findViewById(R.id.searchbtn);
		searchbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			}

		});
	}

	private List<Map<String, Object>> getData() {
		// TODO Auto-generated method stub
		int m = orderlist.size() / 5;
		if (m > 10)
			m = 10;
		list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;

		for (int i = 0; i < m; i++) {
			map = new HashMap<String, Object>();
			map.put("img", R.drawable.hotel1);
			map.put("hotelId", orderlist.get(i * 5 + 0).toString().trim());
			map.put("title", orderlist.get(i * 5 + 1).toString().trim());
			map.put("address", orderlist.get(i * 5 + 3).toString().trim());
			map.put("shopStyle", orderlist.get(i * 5 + 2).toString().trim());
			list.add(map);

		}
		return list;

	}

	class hotel_itemOnClickListener implements OnItemClickListener {
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			Map<String, Object> clicked = new HashMap<String, Object>();
			clicked = list.get(position);

			hotelId = clicked.get("hotelId").toString();
			shopStyle = clicked.get("shopStyle").toString();
			Intent intent = new Intent();
			Bundle bundle = new Bundle();
			if (shopStyle.equals("1")) {
				intent.setClass(NearbyActivity.this, detail_information.class);
				bundle.putString("hotelId", hotelId);
			} else {
				intent.setClass(NearbyActivity.this,
						RestaurantInformation.class);
				bundle.putString("hotelId", hotelId);
			}
			intent.putExtras(bundle);
			startActivity(intent);

		}

	}
}
