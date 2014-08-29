package com.example.mapforwebservice;

import java.util.ArrayList;
import java.util.List;

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
import android.widget.Toast;

public class Select_forcity extends Activity {
	private ListView listview;
	List<String> data = new ArrayList<String>();
	List<String> pointvalue = new ArrayList<String>();
	private MapData mapdata;
	String Name;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_forcity);
		mapdata = new MapData();
		Bundle bundle = this.getIntent().getExtras();
		Name = bundle.getString("province");
		data = mapdata.selectCity(Name);
		listview = (ListView) findViewById(R.id.listtruecity);

		listview.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_expandable_list_item_1, getData()));
		listview.setOnItemClickListener(new forcityClickListener());
		
		ImageView returnbtn = (ImageView) findViewById(R.id.returnbtn);
		returnbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(Select_forcity.this, Select_city.class);
				startActivity(intent);
				finish();
			}

		});
	}

	private List<String> getData() {

		return data;
	}

	class forcityClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			try {
				String cityname = data.get(arg2);
				pointvalue = mapdata.getPointValue(cityname);
				((PointValue) getApplication()).setPointXValue((int) (Double
						.valueOf(pointvalue.get(0)) * 1E6));
				((PointValue) getApplication()).setPointYValue((int) (Double
						.valueOf(pointvalue.get(1)) * 1E6));
				Intent intent = new Intent();
				intent.setClass(Select_forcity.this, MainActivity.class);
				startActivity(intent);
				MainActivity.setCityText(cityname);
				Select_forcity.this.finish();
			} catch (Exception e) {
				Toast.makeText(Select_forcity.this, "ÔÝÎÞÊý¾Ý", Toast.LENGTH_LONG).show();
			}
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent();
			intent.setClass(Select_forcity.this, Select_city.class);
			startActivity(intent);
			Select_forcity.this.finish();
		}
		return false;
	}

}
