package com.example.mapforwebservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class User_ChuzhiDetail extends Activity {
	private ListView listview;
	private List<Map<String, Object>> list;
	private List<String> historylist;
	private MapData mapdata;
	private String hotelId;
	private String hotelNameStr;
	private String hotelChuzhiStr;
	private String hotelAddressStr;
	private String userName;
	private String uid;
	private Double chuzhi;
	private LinearLayout historyEmpty;
	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user__chuzhi_detail);

		sp = getSharedPreferences("login_state", Context.MODE_PRIVATE);
		uid = sp.getString("uid", "");
		Intent intent = getIntent();
		userName = intent.getStringExtra("userName");
		hotelId = intent.getStringExtra("hotelId");
		hotelNameStr = intent.getStringExtra("hotelname");
		hotelChuzhiStr = intent.getStringExtra("hotelchuzhi");
		hotelAddressStr = intent.getStringExtra("hoteladdress");
		TextView hotelname = (TextView) findViewById(R.id.hotelname);
		TextView hotelchuzhi = (TextView) findViewById(R.id.hotelchuzhi);
		TextView hoteladdress = (TextView) findViewById(R.id.hoteladdress);
		historyEmpty = (LinearLayout) findViewById(R.id.historyEmpty);

		hotelname.setText(hotelNameStr);
		hotelchuzhi.setText(hotelChuzhiStr);
		hoteladdress.setText(hotelAddressStr);

		mapdata = new MapData();
		historylist = mapdata.getChuzhihistory(uid, hotelId);

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

		listview = (ListView) findViewById(R.id.chuzhi_detailList);
		MyAdapter mAdapter = new MyAdapter(this);
		// SimpleAdapter adapter = new SimpleAdapter(this, getDate(),
		// R.layout.user_chuzhi_detail_item, new String[] { "chuzhi_type",
		// "chuzhi_number", "chuzhi_date" }, new int[] {
		// R.id.chuzhi_type, R.id.chuzhi_number, R.id.orderid });
		listview.setAdapter(mAdapter);
		listview.setDividerHeight(0);

	}

	private List<Map<String, Object>> getDate() {
		// TODO Auto-generated method stub
		list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		int m = historylist.size() / 2;

		try {
			for (int i = 0; i < m; i++) {
				map = new HashMap<String, Object>();

				chuzhi = Double.valueOf(historylist.get(i * 2 + 0).toString());
				if (chuzhi < 0) {
					map.put("chuzhi_type", "消费");
				} else if (chuzhi > 0) {
					map.put("chuzhi_type", "充值");
				} else {
					continue;
				}
				map.put("chuzhi_number", historylist.get(i * 2 + 0).toString());
				// map.put("orderid", "");
				map.put("chuzhi_date", historylist.get(i * 2 + 1).toString());
				list.add(map);
			}
		} catch (Exception e) {
			Toast.makeText(User_ChuzhiDetail.this, "数据读取失败", Toast.LENGTH_LONG)
					.show();
		}
		if (!list.isEmpty()) {
			historyEmpty.setVisibility(View.GONE);
		}

		return list;
	}

	private class MyAdapter extends BaseAdapter {

		private LayoutInflater mInflater;// 得到一个LayoutInfalter对象用来导入布局

		/** 构造函数 */
		public MyAdapter(Context context) {
			this.mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return getDate().size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return getDate().get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder;
			if (convertView == null) {
				convertView = mInflater.inflate(
						R.layout.user_chuzhi_detail_item, null);
				holder = new ViewHolder();
				/** 得到各个控件的对象 */
				holder.chuzhi_type = (TextView) convertView
						.findViewById(R.id.chuzhi_type);
				holder.chuzhi_number = (TextView) convertView
						.findViewById(R.id.chuzhi_number);
				holder.chuzhi_date = (TextView) convertView
						.findViewById(R.id.chuzhi_date);
				convertView.setTag(holder);// 绑定ViewHolder对象

			} else {
				holder = (ViewHolder) convertView.getTag();// 取出ViewHolder对象
			}
			holder.chuzhi_type.setText(getDate().get(position)
					.get("chuzhi_type").toString());
			holder.chuzhi_number.setText(getDate().get(position)
					.get("chuzhi_number").toString());
			double number = Double.valueOf(holder.chuzhi_number.getText()
					.toString());
			if (number < 0) {
				holder.chuzhi_number.setTextColor(getResources().getColor(R.color.blue));
			} else {
				holder.chuzhi_number.setTextColor(getResources().getColor(R.color.moneycolor));
			}
			holder.chuzhi_date.setText(getDate().get(position)
					.get("chuzhi_date").toString());

			return convertView;
		}

	}

	public final class ViewHolder {
		public TextView chuzhi_type;
		public TextView chuzhi_number;
		public TextView chuzhi_date;
	}
}
