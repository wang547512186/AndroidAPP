package com.example.mapforwebservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class Select_city extends Activity {
	private ImageView img;
	private ListView listview;
	private MapData mapdata;
	List<String> data = new ArrayList<String>();
	List<String> pointvalue = new ArrayList<String>();
	private List<Map<String, Object>> list;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_city);
		mapdata = new MapData();
		data = mapdata.selectProvince();
		listview = (ListView) findViewById(R.id.listcitys);
		// listview.setAdapter(new ArrayAdapter<String>(this,
		// android.R.layout.simple_expandable_list_item_1,getData()));

		SimpleAdapter adapter = new SimpleAdapter(this, getData(),
				R.layout.select_provice_item, new String[] { "title" },
				new int[] { R.id.provincename });
		// ArrayAdapter<String>adapter = new ArrayAdapter<String>(this,
		// R.layout.select_provice_item, R.id.provincename, data);

		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new cityClickListener());
		
		ImageView returnbtn = (ImageView) findViewById(R.id.returnbtn);
		returnbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(Select_city.this, MainActivity.class);
				startActivity(intent);
				finish();
			}

		});
	}

	
	class cityClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			String provicename = data.get(arg2);
			String hascity = mapdata.findCity(provicename);
			if (hascity.equals("true")) {
				try {
					Intent intent = new Intent();
					intent.setClass(Select_city.this, Select_forcity.class);
					Bundle bundle = new Bundle();
					bundle.putString("province", provicename);
					intent.putExtras(bundle);
					startActivity(intent);
					Select_city.this.finish();
				} catch (Exception e) {
					Toast.makeText(Select_city.this, "暂无数据", Toast.LENGTH_LONG).show();
				}
			}
			if (hascity.equals("false")) {
				try {
					pointvalue = mapdata.getPointValue(provicename);
					((PointValue) getApplication())
							.setPointXValue((int) (Double.valueOf(pointvalue
									.get(0)) * 1E6));
					((PointValue) getApplication())
							.setPointYValue((int) (Double.valueOf(pointvalue
									.get(1)) * 1E6));
					// ((PointValue)getApplication()).setPointXValue((int)(31.236071*
					// 1E6));
					// ((PointValue)getApplication()).setPointYValue((int)(121.481575*
					// 1E6));
					Intent intent = new Intent();
					intent.setClass(Select_city.this, MainActivity.class);
					// intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					// intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					startActivity(intent);
					MainActivity.setCityText(provicename);
					Select_city.this.finish();
				} catch (Exception e) {
					Toast.makeText(Select_city.this, "暂无数据", Toast.LENGTH_LONG);
				}

			}
		}

	}

	private List<Map<String, Object>> getData() {

		list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < data.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			// map.put("values", data.get(i));
			map.put("title", data.get(i));
			list.add(map);
		}
		return list;
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent();
			intent.setClass(Select_city.this, MainActivity.class);
			startActivity(intent);
			Select_city.this.finish();
		}
		return false;
	}
}
