package com.example.mapforwebservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.example.mapforwebservice.User_information.MyThread;
import com.example.mapforwebservice.detail_information.hotel_itemOnClickListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter.ViewBinder;

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
	private String picUrl = "http://www.kezhu.com/hotelpic/b/";
	private String picname;
	private Bitmap bitmap;
	private Drawable drawable;
//	private ProgressDialog dialog;
	private Timer timer;
	private TextView processText;
	private LinearLayout hotelLayout;
	private LinearLayout foodLayout;
	private LinearLayout carLayout;
	private LinearLayout otherLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nearby_activity);
		
		hotelLayout = (LinearLayout) findViewById(R.id.hotelLayout);
		foodLayout = (LinearLayout) findViewById(R.id.foodLayout);
		carLayout = (LinearLayout) findViewById(R.id.carLayout);
		otherLayout = (LinearLayout) findViewById(R.id.otherLayout);
		changeBackground(hotelLayout, R.color.hotelup,  R.color.hoteldown);
		changeBackground(foodLayout, R.color.foodup,  R.color.fooddown);
		changeBackground(carLayout, R.color.carup,  R.color.cardown);
		changeBackground(otherLayout, R.color.otherup,  R.color.otherdown);
		
		
		hotelLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			}
		});
		foodLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			}
		});
		carLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			}
		});
		otherLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			}
		});
		
		
		moreLayout = (TextView) findViewById(R.id.moreLayout);
		moreLayout.setVisibility(View.INVISIBLE);
		listview = (ListView) findViewById(R.id.nearList);
		
		TextView topTextView = (TextView) findViewById(R.id.topTextView);
		processText = (TextView) findViewById(R.id.processText);
		processText.setText("载入中...");
		
		topTextView.setFocusable(true);
		topTextView.setFocusableInTouchMode(true);
		topTextView.requestFocus();

		curCityString = MainActivity.getCityText();

		
		User_information.changeBackground(moreLayout);
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

		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				checkThread();
			}
		}, 8000);

		MyThread thread = new MyThread();
		Thread mThread = new Thread(thread);
		mThread.start();
	}

	private List<Map<String, Object>> getData() {
		// TODO Auto-generated method stub
		int m = orderlist.size() / 8;
		if (m > 10)
			m = 10;
		list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;

		for (int i = 0; i < m; i++) {
			map = new HashMap<String, Object>();
			picname = "";
			picname = orderlist.get(i * 8 + 5).toString().trim();
			if (!picname.equals("") && !picname.equals("anyType{}")) {
				bitmap = HotelList.getPic(picUrl + picname);
			} else {
				bitmap = HotelList.getPic(picUrl + "kezhu.jpg");
			}
			map.put("img", bitmap);
			map.put("hotelId", orderlist.get(i * 8 + 0).toString().trim());
			map.put("title", orderlist.get(i * 8 + 1).toString().trim());
			map.put("address", orderlist.get(i * 8 + 3).toString().trim());
			map.put("shopStyle", orderlist.get(i * 8 + 2).toString().trim());
			list.add(map);

		}
		return list;

	}

	public void changeBackground( final View view,final int colorUp,final int colorDown) {
		view.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch (event.getAction()) {

				case MotionEvent.ACTION_DOWN: {
					view.setBackgroundResource(colorDown);
					// 按下时触发
					break;
				}
				case MotionEvent.ACTION_UP: {
					view.setBackgroundResource(colorUp);
					// 触摸后触发
					break;
				}//
				case MotionEvent.ACTION_CANCEL: {
					view.setBackgroundResource(colorUp);
					// 触摸后触发
					break;
				}
				}

				return false;
			}

		});
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

	protected void checkThread() {
		Message msg = new Message();
		msg.what = 2;
		mHandler.sendMessage(msg);
	}

	class MyThread implements Runnable {

		public void run() {
			// 执行数据操作，不涉及到UI
			mapdata = new MapData();
			Message msg = new Message();
			try {
				mapdata = new MapData();
				List<String> testList = mapdata.selectProvince();
				if (testList.isEmpty()) { // 无法连接数据库
					msg.what = 3;
				} else {
					orderlist = mapdata.findShops(curCityString.substring(0,
							curCityString.length() - 1), "");
					if (!orderlist.isEmpty()) { // 成功读取数据
						msg.what = 1;
					} else {
						msg.what = 4; // 数据库无数据(未登录)
					}

				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				msg.what = 2;
			}
			mHandler.sendMessage(msg); // 向Handler发送消息,更新UI
		}
	}

	public Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {

			switch (msg.what) {
			case 1: // 成功

				timer.cancel();
				adapter = new SimpleAdapter(NearbyActivity.this, getData(),
						R.layout.hotellist_item, new String[] { "img", "title",
								"address" }, new int[] { R.id.img, R.id.title,
								R.id.address });
				adapter.setViewBinder(new ViewBinder() {
					@Override
					public boolean setViewValue(View view, Object data,
							String textRepresentation) {
						// TODO Auto-generated method stub
						if (view instanceof ImageView && data instanceof Bitmap) {
							ImageView i = (ImageView) view;
							i.setImageBitmap((Bitmap) data);
							return true;
						}
						return false;
					}
				});
				
				processText.setText("附近商家");

				moreLayout.setVisibility(View.VISIBLE);
				listview.setAdapter(adapter);
				listview.setOnItemClickListener(new hotel_itemOnClickListener());
				Utility.setListViewHeightBasedOnChildren(listview);
//				dialog.dismiss();
				break;
			case 2: // 网络异常
				timer.cancel();
				Dialog fail = new AlertDialog.Builder(NearbyActivity.this)
						.setTitle("提示")
						.setMessage("网络异常")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										NearbyActivity.this.finish();
									}
								}).create();
				fail.show();
//				dialog.dismiss();
				processText.setText("网络异常");
				break;
			case 3: // 无法连接服务器
				timer.cancel();
				Dialog fail2 = new AlertDialog.Builder(NearbyActivity.this)
						.setTitle("提示")
						.setMessage("无法连接服务器")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										NearbyActivity.this.finish();
									}
								}).create();
				fail2.show();
//				dialog.dismiss();
				processText.setText("无法连接服务器");
				break;
			case 4: // 无数据
				timer.cancel();
//				dialog.dismiss();
				processText.setText("无商家信息");
				break;

			}
		};
	};

}
