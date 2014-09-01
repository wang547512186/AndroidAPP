package com.example.mapforwebservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class HotelList extends ListFragment {
	private ListView listview;
	private List<Map<String, Object>> list;
	private List<String> orderlist;
	private MapData mapdata;
	private String curCityString;
	private SimpleAdapter searchadapter;
	private String hotelId;
	private String shopStyle;
	private EditText searchText;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mapdata = new MapData();
		
//		Log.e("getCityText", MainActivity.getCityText());
		getActivity().getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
						| WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		
		curCityString=MainActivity.getCityText();
		orderlist = mapdata.findShops(curCityString.substring(0, curCityString.length()-1), "");
//		Toast.makeText(getActivity(), curCityString, Toast.LENGTH_SHORT).show();
		
		
		
	}

	private List<Map<String, Object>> getData() {
		// TODO Auto-generated method stub
		int m = orderlist.size() / 5;
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

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.hotellist, null);
		listview = (ListView) view.findViewById(android.R.id.list);
		SimpleAdapter adapter = new SimpleAdapter(this.getActivity()
				.getApplicationContext(), getData(), R.layout.hotellist_item,
				new String[] { "title", "address", "img" }, new int[] {
						R.id.title, R.id.address, R.id.img });
		listview.setAdapter(adapter);
		
		searchText = (EditText)view.findViewById(R.id.searchText);
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
				listview.setAdapter(searchadapter);
				listview.setDividerHeight(0);

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}

		});
		
		return view;
	}

	public void onListItemClick(ListView l, View v, int position, long id) {

		// TODO Auto-generated method stub
		HashMap<String, String> map = (HashMap<String, String>) listview
				.getItemAtPosition(position);

		String strHotelId = map.get("hotelId");
		String strShopStyle = map.get("shopStyle");
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		if (strShopStyle.equals("1")) {
			intent.setClass(getActivity(), detail_information.class);
			bundle.putString("hotelId", strHotelId);
		} else {
			intent.setClass(getActivity(), RestaurantInformation.class);
			bundle.putString("hotelId", strHotelId);
		}
		intent.putExtras(bundle);
		startActivity(intent);
	
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	
	private void NormalResult() {
		searchadapter = new SimpleAdapter(getActivity(), getData(), R.layout.hotellist_item,
				new String[] { "img", "title", "address" }, new int[] { R.id.img,
						R.id.title, R.id.address });

	}

	private void SearchResult(String str) {
		searchadapter = new SimpleAdapter(getActivity(), searchData(str, getData()),
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
}
