package com.example.mapforwebservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeoutException;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.map.PopupClickListener;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.mapapi.utils.DistanceUtil;
import com.baidu.platform.comapi.basestruct.GeoPoint;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class MainActivity extends Activity implements OnTabChangeListener {
	private Timer timer;
	BMapManager mBMapMan = null;
	MapView mMapView = null;
	private PopupWindow popupwindow;
	private View viewCache = null;
	private View popupInfo = null;
	private List<String> testList;
	// private View popupRight = null;
	private TextView popupText = null;
	private List<String> detail = null;
	private List<String> autoTextList = null;
	private ImageView myloc;
	private MapData mapdata;
	private List<String> searchlist = null;
	private List<String> shoppoints = null;
	private List<String> locations = null;
	private List<String> hotelpoints = null;
	private List<String> coffees = null;
	private List<String> foodpoints = null;
	private List<String> printshoppoints = null;
	private List<String> carpoints = null;
	private List<String> supermarketpoints = null;
	private List<String> searchpoints = null;
	private ImageButton imgbt;
	private ListView listview;
	private static TextView selectcity;
	private int PointXValue = (int) (31.220 * 1E6);
	private int PointYValue = (int) (121.527 * 1E6);
	private double latitude = 0.0;
	private double longitude = 0.0;
	// private TableLayout searchLayout;
	private AutoCompleteTextView searchtext;
	private Button search_btn;
	private GeoPoint MyLocation;
	public static String curCityString = "�Ϻ���";
	String SelectValue;
	String hotelId;
	String address;
	String name_address;
	String shopStyle;
	String price = "0";
	private SharedPreferences sp;
	private String userName;
	private PopupOverlay pop;
	private ProgressDialog dialog;
	public LocationClient mLocationClient = null; // ��λ
	public MyLocationListenner myListener = new MyLocationListenner();
	public Button ReLBSButton = null;
	public static String TAG = "msg";

	@Override
	@SuppressLint("NewApi")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		sp = getSharedPreferences("login_state", Context.MODE_PRIVATE);
		userName = sp.getString("userName", "");
		mBMapMan = new BMapManager(getApplication());
		mBMapMan.init(null);
		// ע�⣺��������setContentViewǰ��ʼ��BMapManager���󣬷���ᱨ��
		setContentView(R.layout.activity_main);
		// ������

		dialog = new ProgressDialog(this);
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// ���ý���������ʽΪԲ��ת���Ľ�����
		dialog.setMessage("���ڼ���");// ������
		dialog.show();
		// /�����߳�

		// ����
		search_btn = (Button) findViewById(R.id.search_btn);
		search_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(arg0.getWindowToken(), 0);

				search();
			}

		});

		JPushInterface.setDebugMode(true);
		JPushInterface.init(this);

		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		SelectValue = "ȫ��";
		// ///������Ч��
		// searchLayout = (TableLayout) findViewById(R.id.search_layout);

		final MapView bmapsView = (MapView) findViewById(R.id.bmapsView);
		bmapsView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				bmapsView.setFocusable(true);
				bmapsView.setFocusableInTouchMode(true);
				bmapsView.requestFocus();
				return false;
			}

		});
		// mylocation();

		PointXValue = ((PointValue) getApplicationContext()).getPointXValue();
		PointYValue = ((PointValue) getApplicationContext()).getPointYValue();
		mMapView = (MapView) findViewById(R.id.bmapsView);
		mMapView.setBuiltInZoomControls(true);
		// �����������õ����ſؼ�
		MapController mMapController = mMapView.getController();
		// �õ�mMapView�Ŀ���Ȩ,�����������ƺ�����ƽ�ƺ�����
		// GeoPoint point =new GeoPoint((int)(31.220* 1E6),(int)(121.527* 1E6));
		GeoPoint point = new GeoPoint(PointXValue, PointYValue); // ///////
		// �ø����ľ�γ�ȹ���һ��GeoPoint����λ��΢�� (�� * 1E6) MyLocation
		mMapController.setCenter(point);// ���õ�ͼ���ĵ�
		mMapController.setZoom(14);// ���õ�ͼzoom����
		mMapView.refresh();
		imgbt = (ImageButton) findViewById(R.id.main_button);
		imgbt.setOnClickListener(new ImgBtOnClickListener());
		selectcity = (TextView) findViewById(R.id.selectedcity);
		selectcity.setText(curCityString);
		LinearLayout selectedLayout = (LinearLayout) findViewById(R.id.selectedLayout);
		selectedLayout.setOnClickListener(new selectcityOnClickListener());
		View shop = (View) LayoutInflater.from(this).inflate(R.layout.near,
				null);
		TextView tv = (TextView) shop.findViewById(R.id.hotelname);
		tv.setText("����");

		View hotel = (View) LayoutInflater.from(this).inflate(
				R.layout.classify, null);
		TextView tv1 = (TextView) hotel.findViewById(R.id.hotelname);
		tv1.setText("����");

		View more = (View) LayoutInflater.from(this)
				.inflate(R.layout.pay, null);
		TextView tv2 = (TextView) more.findViewById(R.id.hotelname);
		tv2.setText("���֧��");

		View person = (View) LayoutInflater.from(this).inflate(R.layout.user,
				null);
		TextView tv3 = (TextView) person.findViewById(R.id.hotelname);
		tv3.setText("��������");
		final TabHost tabHost = (TabHost) findViewById(R.id.tabhost);
		tabHost.setup();
		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator(shop)
				.setContent(R.id.tab1));
		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator(hotel)
				.setContent(R.id.tab2));
		tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator(more)
				.setContent(R.id.tab3));
		tabHost.addTab(tabHost.newTabSpec("tab4").setIndicator(person)
				.setContent(R.id.tab4));
		tabHost.setCurrentTab(0);
		tabHost.setOnTabChangedListener(MainActivity.this);

		tabHost.getTabWidget().getChildAt(0)
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (tabHost.getCurrentTab() == 0) {
							SelectValue = "ȫ��";
							showalladdress();
						}
						tabHost.setCurrentTab(0);

					}
				});
		tabHost.getTabWidget().getChildAt(1)
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (tabHost.getCurrentTab() == 1) {
							Intent intent = new Intent(MainActivity.this,
									NearbyActivity.class);
							startActivity(intent);
						}
						tabHost.setCurrentTab(1);

					}
				});
		tabHost.getTabWidget().getChildAt(2)
				.setOnClickListener(new OnClickListener() {
					// ������֧��
					@Override
					public void onClick(View v) {
						tabHost.setCurrentTab(2);
						if (userName.equals("")) {
							Intent intent = new Intent(MainActivity.this,
									User_login.class);
							startActivity(intent);
							finish();

						} else {
							Intent intent = new Intent(MainActivity.this,
									FastPay.class);
							startActivity(intent);
							// finish();
						}

						// if (popupwindow != null && popupwindow.isShowing()) {
						// popupwindow.dismiss();
						// return;
						// } else {
						// initmPopupWindowView();
						// popupwindow.showAsDropDown(v, -10, 0);
						//
						// }

					}
				});

		searchtext = (AutoCompleteTextView) findViewById(R.id.search_text);
		searchtext.setDropDownWidth(dm.widthPixels * 3 / 4);

		// ��λ
		mLocationClient = new LocationClient(this);
		mLocationClient.registerLocationListener(myListener);
		setLocationOption();// �趨��λ����
		// mLocationClient.start();//��ʼ��λ

		myloc = (ImageView) findViewById(R.id.myloc);
		myloc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mLocationClient.start();// ��ʼ��λ

				SelectValue = "��λ";
				if (mLocationClient != null && mLocationClient.isStarted())
					mLocationClient.requestLocation();
				else
					Log.d("msg", "locClient is null or not started");

			}

		});

		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				checkThread();
			}
		}, 7000);

		MyThread thread = new MyThread();
		Thread mThread = new Thread(thread);
		mThread.start();
	}

	protected void checkThread() {
		Message msg = new Message();
		msg.what = 2;
		mHandler.sendMessage(msg);
	}

	class MyThread implements Runnable {

		public void run() {
			// ִ�����ݲ��������漰��UI

			Message msg = new Message();
			try {

				mapdata = new MapData();
				testList = mapdata.selectProvince();
				if (testList.isEmpty()) {
					msg.what = 3;
				} else {
					autoTextList = getName();
					locations = mapdata.selectall(curCityString.substring(0,
							curCityString.length() - 1));
					hotelpoints = mapdata.selectOhters("1", curCityString
							.substring(0, curCityString.length() - 1));
					foodpoints = mapdata.selectOhters("2", curCityString
							.substring(0, curCityString.length() - 1));
					printshoppoints = mapdata.selectOhters("��ӡ��", curCityString
							.substring(0, curCityString.length() - 1));
					carpoints = mapdata.selectOhters("����", curCityString
							.substring(0, curCityString.length() - 1));
					supermarketpoints = mapdata.selectOhters("����",
							curCityString.substring(0,
									curCityString.length() - 1));
					coffees = mapdata.selectOhters("���ȵ�", curCityString
							.substring(0, curCityString.length() - 1));
					shoppoints = mapdata.getShopInfo();
					showalladdress();
					msg.what = 1;
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				msg.what = 2;
			}
			mHandler.sendMessage(msg); // ��Handler������Ϣ,����UI
		}
	}

	public Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {

			switch (msg.what) {
			case 1:
				timer.cancel();
				ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
						MainActivity.this,
						android.R.layout.simple_dropdown_item_1line,
						autoTextList);
				searchtext.setAdapter(arrayAdapter);
				dialog.dismiss();
				break;
			case 2:
				timer.cancel();
				Toast.makeText(MainActivity.this, "�����쳣", Toast.LENGTH_LONG)
						.show();
				dialog.dismiss();
				break;
			case 3:
				timer.cancel();
				Toast.makeText(MainActivity.this, "�������쳣,��ȡ����ʧ��", Toast.LENGTH_LONG)
						.show();
				dialog.dismiss();
				break;

			}
		};
	};

	@Override
	protected void onDestroy() {
		mLocationClient.stop();
		mMapView.destroy();
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		Log.e("TAG", "start onPause~~~");
		mMapView.onPause();
		if (mBMapMan != null) {
			mBMapMan.stop();
			Log.e("TAG", "start onPause222~~~");
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		Log.e("TAG", "start onResume~~~");
		mMapView.onResume();
		mMapView.refresh();
		if (mBMapMan != null) {
			mBMapMan.start();
			Log.e("TAG", "start onResume222~~~");
		}
		super.onResume();
	}

	protected void onNewIntent() {
		MapController mMapController = mMapView.getController();
		// �õ�mMapView�Ŀ���Ȩ,�����������ƺ�����ƽ�ƺ�����
		// GeoPoint point =new GeoPoint((int)(31.220* 1E6),(int)(121.527* 1E6));
		GeoPoint point = new GeoPoint(PointXValue, PointYValue);
		// �ø����ľ�γ�ȹ���һ��GeoPoint����λ��΢�� (�� * 1E6)
		mMapController.setCenter(point);// ���õ�ͼ���ĵ�
		mMapController.setZoom(17);// ���õ�ͼzoom����
	}

	class ImgBtOnClickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, more_information.class);
			startActivity(intent);
			MainActivity.this.finish();
		}

	}

	class selectcityOnClickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, Select_city.class);
			startActivity(intent);
			MainActivity.this.finish();
		}

	}

	private void showaddress() {
		/**
		 * ����Ҫ���Overlay�ĵط�ʹ�����´��룬 ����Activity��onCreate()��
		 */
		// ׼��Ҫ��ӵ�Overlay

		// ׼��overlayͼ�����ݣ�����ʵ������޸�
		Drawable mark = getResources().getDrawable(R.drawable.icon_gcoding);
		// ����IteminizedOverlay
		OverlayTest itemOverlay = new OverlayTest(mark, mMapView);
		int sums = locations.size() / 5;
		for (int i = 0; i < sums; i++) {
			String[] strings = locations.get(i * 5 + 3).toString().trim()
					.split(",");
			if (strings[0].equals("0")) {
				continue;
			} else {
				double mLat = Double.valueOf(strings[0]);
				double mLon = Double.valueOf(strings[1]);
				GeoPoint p = new GeoPoint((int) (mLat * 1E6),
						(int) (mLon * 1E6));
				OverlayItem item = new OverlayItem(p, "item" + i, "item" + i);
				itemOverlay.addItem(item);
			}
		}
		mMapView.getOverlays().clear();
		mMapView.getOverlays().add(itemOverlay);
		mMapView.refresh();
	}

	// private void mylocation() {
	// LocationManager locationManager = (LocationManager)
	// getSystemService(Context.LOCATION_SERVICE);
	// if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
	// Location location = locationManager
	// .getLastKnownLocation(LocationManager.GPS_PROVIDER);
	// if (location != null) {
	// latitude = location.getLatitude();
	// longitude = location.getLongitude();
	// }
	// } else {
	// LocationListener locationListener = new LocationListener() {
	//
	// // Provider��״̬�ڿ��á���ʱ�����ú��޷�������״ֱ̬���л�ʱ�����˺���
	// @Override
	// public void onStatusChanged(String provider, int status,
	// Bundle extras) {
	//
	// }
	//
	// // Provider��enableʱ�����˺���������GPS����
	// @Override
	// public void onProviderEnabled(String provider) {
	//
	// }
	//
	// // Provider��disableʱ�����˺���������GPS���ر�
	// @Override
	// public void onProviderDisabled(String provider) {
	//
	// }
	//
	// // ������ı�ʱ�����˺��������Provider������ͬ�����꣬���Ͳ��ᱻ����
	// @Override
	// public void onLocationChanged(Location location) {
	// if (location != null) {
	// Log.e("Map",
	// "Location changed : Lat: "
	// + location.getLatitude() + " Lng: "
	// + location.getLongitude());
	// }
	// }
	// };
	// locationManager
	// .requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
	// 1000, 0, locationListener);
	// Location location = locationManager
	// .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
	// if (location != null) {
	// latitude = location.getLatitude(); // ����
	// longitude = location.getLongitude(); // γ��
	// }
	// }
	// System.out.println(latitude);
	// System.out.println(longitude);
	// MyLocation = new GeoPoint((int) (latitude * 1E6),
	// (int) (longitude * 1E6));
	// Toast.makeText(this, String.valueOf(latitude)+String.valueOf(longitude),
	// Toast.LENGTH_SHORT).show();
	// }

	private void addaddresspoint(List<String> points, Drawable Mark,
			OverlayTest ItemOverlay) {

		// ����IteminizedOverlay
		int sums = points.size() / 6;
		for (int i = 0; i < sums; i++) {
			double mLat = Double.valueOf(points.get(i * 6 + 3).toString());
			double mLon = Double.valueOf(points.get(i * 6 + 4).toString());
			GeoPoint p = new GeoPoint((int) (mLat * 1E6), (int) (mLon * 1E6));
			OverlayItem item = new OverlayItem(p, "item" + i, "item" + i);
			ItemOverlay.addItem(item);
		}

	}

	private void showalladdress() {
		/**
		 * ����Ҫ���Overlay�ĵط�ʹ�����´��룬 ����Activity��onCreate()��
		 */
		// ׼��Ҫ��ӵ�Overlay

		Drawable mark = getResources().getDrawable(R.drawable.icon_gcoding);
		// ����IteminizedOverlay
		OverlayTest itemOverlay = new OverlayTest(mark, mMapView);
		int sums = locations.size() / 5;
		double mLat = 0;
		double mLon = 0;
		for (int i = 0; i < sums; i++) {

			String[] strings = locations.get(i * 5 + 3).toString().trim()
					.split(",");
			if (strings[0].equals("0") || strings[0].equals("anyType{}")) {

			} else {
				try {
					mLat = Double.valueOf(strings[0]);
					mLon = Double.valueOf(strings[1]);
				} catch (Exception e) {
					continue;
				}

			}
			GeoPoint p = new GeoPoint((int) (mLat * 1E6), (int) (mLon * 1E6));
			OverlayItem item = new OverlayItem(p, "item" + i, "item" + i);
			if (locations.get(i * 5 + 4).toString().equals("1")) {
				mark = getResources().getDrawable(R.drawable.icon_gcoding);
			}
			if (locations.get(i * 5 + 4).toString().equals("���ȵ�")) {
				mark = getResources().getDrawable(R.drawable.coffee);
			}
			if (locations.get(i * 5 + 4).toString().equals("��ӡ��")) {
				mark = getResources().getDrawable(R.drawable.printshop);
			}
			if (locations.get(i * 5 + 4).toString().equals("����")) {
				mark = getResources().getDrawable(R.drawable.supermarket);
			}
			if (locations.get(i * 5 + 4).toString().equals("2")) {
				mark = getResources().getDrawable(R.drawable.coffee);
			}
			if (locations.get(i * 5 + 4).toString().equals("����")) {
				mark = getResources().getDrawable(R.drawable.cars);
			}
			item.setMarker(mark);
			itemOverlay.addItem(item);
		}
		mMapView.getOverlays().clear();
		mMapView.getOverlays().add(itemOverlay);
		mMapView.refresh();

	}

	private void showhoteladdress() {
		/**
		 * ����Ҫ���Overlay�ĵط�ʹ�����´��룬 ����Activity��onCreate()��
		 */
		// ׼��Ҫ��ӵ�Overlay

		// ׼��overlayͼ�����ݣ�����ʵ������޸�
		Drawable mark = getResources().getDrawable(R.drawable.icon_gcoding);
		// ����IteminizedOverlay
		OverlayTest itemOverlay = new OverlayTest(mark, mMapView);
		int sums = hotelpoints.size() / 6;
		for (int i = 0; i < sums; i++) {
			double mLat = Double.valueOf(hotelpoints.get(i * 6 + 3).toString());
			double mLon = Double.valueOf(hotelpoints.get(i * 6 + 4).toString());
			GeoPoint p = new GeoPoint((int) (mLat * 1E6), (int) (mLon * 1E6));
			OverlayItem item = new OverlayItem(p, "item" + i, "item" + i);
			itemOverlay.addItem(item);
		}

		mMapView.getOverlays().clear();
		mMapView.getOverlays().add(itemOverlay);
		mMapView.refresh();
	}

	private void showotheraddress(List<String> pointtype, String typename) {
		/**
		 * ����Ҫ���Overlay�ĵط�ʹ�����´��룬 ����Activity��onCreate()��
		 */
		// ׼��Ҫ��ӵ�Overlay

		// ׼��overlayͼ�����ݣ�����ʵ������޸�
		Drawable mark = getResources().getDrawable(R.drawable.icon_gcoding);
		if (typename.equals("���ȵ�")) {
			mark = getResources().getDrawable(R.drawable.coffee);
		}
		if (typename.equals("��ӡ��")) {
			mark = getResources().getDrawable(R.drawable.printshop);
		}
		if (typename.equals("����")) {
			mark = getResources().getDrawable(R.drawable.supermarket);
		}
		if (typename.equals("������")) {
			mark = getResources().getDrawable(R.drawable.coffee);
		}
		if (typename.equals("����")) {
			mark = getResources().getDrawable(R.drawable.cars);
		}
		// ����IteminizedOverlay
		OverlayTest itemOverlay = new OverlayTest(mark, mMapView);
		int sums = pointtype.size() / 6;
		for (int i = 0; i < sums; i++) {
			String[] strings = locations.get(i * 5 + 3).toString().trim()
					.split(",");
			double mLat = Double.valueOf(strings[0]);
			double mLon = Double.valueOf(strings[1]);
			GeoPoint p = new GeoPoint((int) (mLat * 1E6), (int) (mLon * 1E6));
			OverlayItem item = new OverlayItem(p, "item" + i, "item" + i);
			itemOverlay.addItem(item);
		}

		mMapView.getOverlays().clear();
		mMapView.getOverlays().add(itemOverlay);

		mMapView.refresh();
	}

	private void showalltip(String hotel_id, int index,
			List<String> locationdata) {

		// detail = mapdata.selectall();
		detail = mapdata.findAddress(hotel_id);
		name_address = detail.get(1).toString();
		hotelId = detail.get(0).toString();
		shopStyle = detail.get(2).toString();
		// Log.e("name", name);
		// Log.e("shopStyle", shopStyle);
		// ����pop����ע�����¼������ӿ�
		pop = new PopupOverlay(mMapView, new PopupClickListener() {
			@Override
			public void onClickedPopup(int index) {
				// �ڴ˴���pop����¼���indexΪ�����������,�����������������
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				if (shopStyle.equals("1")) {
					intent.setClass(MainActivity.this, detail_information.class);
					bundle.putString("hotelId", hotelId);
				} else {
					intent.setClass(MainActivity.this,
							RestaurantInformation.class);
					bundle.putString("hotelId", hotelId);
				}
				intent.putExtras(bundle);
				startActivity(intent);
				// MainActivity.this.finish();
			}
		});
		/**
		 * ׼��pop������Դ������ʵ��������� ������������ͼƬ�Ĵ��ڣ����Դ�������ͼƬ������ͼƬ��һ��ͼƬ��
		 * �����Ĵ��ڣ������ͼƬ�Ĵ���˳����ϳ�һ��ͼƬ��ʾ. �������ͬ��ͼƬ��ʱ���ص������᷵�ص�ǰ�������ͼƬ����index
		 */
		/*
		 * Bitmap[] bmps = new Bitmap[3]; try { bmps[0] =
		 * BitmapFactory.decodeStream(getAssets().open("ic_launcher.png"));
		 * bmps[1] =
		 * BitmapFactory.decodeStream(getAssets().open("ic_launcher.png"));
		 * bmps[2] =
		 * BitmapFactory.decodeStream(getAssets().open("ic_launcher.png")); }
		 * catch (IOException e) { e.printStackTrace(); }
		 */

		String[] strings = locations.get(index * 5 + 3).toString().trim()
				.split(",");
		double mLat = 0;
		double mLon = 0;
		if (strings[0].equals("0") || strings[0].equals("anyType{}")) {
			// continue;
		} else {
			mLat = Double.valueOf(strings[0]);
			mLon = Double.valueOf(strings[1]);
		}
		GeoPoint ptTAM = new GeoPoint((int) (mLat * 1E6), (int) (mLon * 1E6));
		double forlong = DistanceUtil.getDistance(MyLocation, ptTAM);

		viewCache = getLayoutInflater()
				.inflate(R.layout.custom_text_view, null);
		popupInfo = (View) viewCache.findViewById(R.id.popinfo);
		// popupRight = (View) viewCache.findViewById(R.id.popright);
		popupText = (TextView) viewCache.findViewById(R.id.textcache);
		MapData mapdata2 = new MapData();
		List<String> pricelist = mapdata2.getHotelMinPrice(hotelId);
		if (pricelist.isEmpty()) {
			popupText.setText(name_address.trim());
		} else {
			price = pricelist.get(0).toString();
			popupText.setText(name_address.trim() + "\t" + "��" + price);
		}

		Bitmap[] bitMaps = { BMapUtil.getBitmapFromView(popupInfo),
		// BMapUtil.getBitmapFromView(popupRight)
		};
		// ��������λ��

		// ����pop,����pop
		// pop.showPopup(bmps, ptTAM, 32);
		pop.showPopup(bitMaps, ptTAM, 32);

		// ���ص���
		// pop.hidePop();
	}

	private void showtip(String hotel_id, int index, List<String> locationdata) {
		detail = mapdata.findAddress(hotel_id);
		name_address = detail.get(1).toString().trim();
		hotelId = detail.get(0).toString();
		shopStyle = detail.get(2).toString();
		// ����pop����ע�����¼������ӿ�
		pop = new PopupOverlay(mMapView, new PopupClickListener() {
			@Override
			public void onClickedPopup(int index) {
				// �ڴ˴���pop����¼���indexΪ�����������,�����������������
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				if (shopStyle.equals("�Ƶ�")) {
					intent.setClass(MainActivity.this, detail_information.class);
					bundle.putString("hotelId", hotelId);
				} else {
					intent.setClass(MainActivity.this,
							RestaurantInformation.class);
					bundle.putString("hotelId", hotelId);
				}
				intent.putExtras(bundle);
				startActivity(intent);
				// MainActivity.this.finish();
				pop.hidePop();

			}
		});
		/**
		 * ׼��pop������Դ������ʵ��������� ������������ͼƬ�Ĵ��ڣ����Դ�������ͼƬ������ͼƬ��һ��ͼƬ��
		 * �����Ĵ��ڣ������ͼƬ�Ĵ���˳����ϳ�һ��ͼƬ��ʾ. �������ͬ��ͼƬ��ʱ���ص������᷵�ص�ǰ�������ͼƬ����index
		 */
		/*
		 * Bitmap[] bmps = new Bitmap[3]; try { bmps[0] =
		 * BitmapFactory.decodeStream(getAssets().open("ic_launcher.png"));
		 * bmps[1] =
		 * BitmapFactory.decodeStream(getAssets().open("ic_launcher.png"));
		 * bmps[2] =
		 * BitmapFactory.decodeStream(getAssets().open("ic_launcher.png")); }
		 * catch (IOException e) { e.printStackTrace(); }
		 */

		viewCache = getLayoutInflater()
				.inflate(R.layout.custom_text_view, null);
		popupInfo = (View) viewCache.findViewById(R.id.popinfo);
		// popupRight = (View) viewCache.findViewById(R.id.popright);
		popupText = (TextView) viewCache.findViewById(R.id.textcache);
		MapData mapdata2 = new MapData();
		List<String> pricelist = mapdata2.getHotelMinPrice(hotelId);
		if (pricelist.isEmpty()) {
			popupText.setText(name_address.trim());
		} else {
			price = pricelist.get(0).toString();
			popupText.setText(name_address.trim() + "\t" + "��" + price);
		}

		Bitmap[] bitMaps = { BMapUtil.getBitmapFromView(popupInfo),
		// BMapUtil.getBitmapFromView(popupRight)
		};
		// ��������λ��
		String[] strings = locations.get(index * 5 + 3).toString().trim()
				.split(",");
		double mLat = Double.valueOf(strings[0]);
		double mLon = Double.valueOf(strings[1]);
		GeoPoint ptTAM = new GeoPoint((int) (mLat * 1E6), (int) (mLon * 1E6));
		// ����pop,����pop
		// pop.showPopup(bmps, ptTAM, 32);
		pop.showPopup(bitMaps, ptTAM, 32);
		// ���ص���
		// pop.hidePop();
	}

	/*
	 * Ҫ����overlay����¼�ʱ��Ҫ�̳�ItemizedOverlay ���������¼�ʱ��ֱ������ItemizedOverlay.
	 */
	public class OverlayTest extends ItemizedOverlay<OverlayItem> {
		// ��MapView����ItemizedOverlay
		public OverlayTest(Drawable mark, MapView mapView) {
			super(mark, mapView);
		}

		protected boolean onTap(int index) {
			// �ڴ˴���item����¼�
			if (SelectValue.equals("ȫ��")) {
				showalltip(locations.get(index * 5 + 0).toString().trim(),
						index, locations);
			}
			if (SelectValue.equals("���ȵ�")) {
				showtip(coffees.get(index * 5 + 0).toString().trim(), index,
						coffees);
			}
			if (SelectValue.equals("��ӡ��")) {
				showtip(printshoppoints.get(index * 5 + 0).toString().trim(),
						index, printshoppoints);
			}
			if (SelectValue.equals("����")) {
				showtip(supermarketpoints.get(index * 5 + 0).toString().trim(),
						index, supermarketpoints);
			}
			if (SelectValue.equals("������")) {
				showtip(foodpoints.get(index * 5 + 0).toString().trim(), index,
						foodpoints);
			}
			if (SelectValue.equals("����")) {
				showtip(carpoints.get(index * 5 + 0).toString().trim(), index,
						carpoints);
			}
			if (SelectValue.equals("�Ƶ�")) {
				showtip(hotelpoints.get(index * 5 + 0).toString().trim(),
						index, hotelpoints);
			}

			if (SelectValue.equals("����")) {
				showsearchtip(index);
			}
			System.out.println("item onTap: " + index);
			return true;

		}

		public boolean onTap(GeoPoint pt, MapView mapView) {
			// �ڴ˴���MapView�ĵ���¼��������� trueʱ
			super.onTap(pt, mapView);
			return false;
		}
		// ��2.1.1 ��ʼ��ʹ�� add/remove ����overlay , ������д���½ӿ�
		/*
		 * @Override protected OverlayItem createItem(int i) { return
		 * mGeoList.get(i); }
		 * 
		 * @Override public int size() { return mGeoList.size(); }
		 */
	}

	/**
	 * �˵������ؼ���Ӧ
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitBy2Click(); // ����˫���˳�����
		}
		return false;
	}

	/**
	 * ˫���˳�����
	 */
	private static Boolean isExit = false;

	private void exitBy2Click() {
		Timer tExit = null;
		if (isExit == false) {
			isExit = true; // ׼���˳�
			Toast.makeText(this, "�ٰ�һ���˳�����", Toast.LENGTH_SHORT).show();
			tExit = new Timer();
			tExit.schedule(new TimerTask() {
				@Override
				public void run() {
					isExit = false; // ȡ���˳�
				}
			}, 2000); // ���2������û�а��·��ؼ�����������ʱ��ȡ�����ղ�ִ�е�����

		} else {

			// android.os.Process.killProcess(android.os.Process.myPid());
			finish();
			MainActivity.this.finish();
			System.exit(0);
		}
	}

	@Override
	public void onTabChanged(String arg0) {
		// TODO Auto-generated method stub
		if (arg0.equalsIgnoreCase("tab4")) {
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, User_information.class);
			startActivity(intent);
			MainActivity.this.finish();
		}
		if (arg0.equalsIgnoreCase("tab2")) {
			Intent intent = new Intent(MainActivity.this, NearbyActivity.class);
			startActivity(intent);
			// SelectValue = "�Ƶ�";
			// showhoteladdress();
			/*
			 * PointXValue=((PointValue)getApplicationContext()).getPointXValue()
			 * ;
			 * PointYValue=((PointValue)getApplicationContext()).getPointYValue
			 * (); MapController mMapController=mMapView.getController(); //
			 * �õ�mMapView�Ŀ���Ȩ,�����������ƺ�����ƽ�ƺ����� GeoPoint point =new
			 * GeoPoint(PointXValue,PointYValue); //GeoPoint point =new
			 * GeoPoint((int)(39.910683* 1E6),(int)(116.411348* 1E6));
			 * //�ø����ľ�γ�ȹ���һ��GeoPoint����λ��΢�� (�� * 1E6)
			 * mMapController.setCenter(point);//���õ�ͼ���ĵ�
			 * //mMapController.setZoom(17);//���õ�ͼzoom����
			 */}
		if (arg0.equalsIgnoreCase("tab3")) {
			/*
			 * Toast.makeText(this, "�ٰ�һ���˳�����", Toast.LENGTH_SHORT).show(); if
			 * (popupwindow != null&&popupwindow.isShowing()) {
			 * popupwindow.dismiss(); return; } else { initmPopupWindowView();
			 * View v = getLayoutInflater().inflate(R.layout.activity_main,
			 * null, false); popupwindow.showAsDropDown(v, 0, 800);
			 * 
			 * }
			 */
		}
		if (arg0.equalsIgnoreCase("tab1")) {
			SelectValue = "ȫ��";
			showalladdress();
		}
	}

	// Handler handler = new Handler() {
	// public void handleMessage(Message msg) {
	// PointXValue = ((PointValue) getApplicationContext())
	// .getPointXValue();
	// PointYValue = ((PointValue) getApplicationContext())
	// .getPointYValue();
	// MapController mMapController = mMapView.getController();
	// // �õ�mMapView�Ŀ���Ȩ,�����������ƺ�����ƽ�ƺ�����
	// GeoPoint point = new GeoPoint(PointXValue, PointYValue);
	// // GeoPoint point =new GeoPoint((int)(39.910683*
	// // 1E6),(int)(116.411348* 1E6));
	// // �ø����ľ�γ�ȹ���һ��GeoPoint����λ��΢�� (�� * 1E6)
	// mMapController.setCenter(point);// ���õ�ͼ���ĵ�
	// // mMapController.setZoom(17);//���õ�ͼzoom����
	// super.handleMessage(msg);
	// }
	// };

	// public class MyThread implements Runnable {
	// @Override
	// public void run() {
	// // TODO Auto-generated method stub
	// while (true) {
	// try {
	// Thread.sleep(10000);// �߳���ͣ10�룬��λ����
	// Message message = new Message();
	// message.what = 1;
	// handler.sendMessage(message);// ������Ϣ
	// } catch (InterruptedException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// }
	// }

	private void initmPopupWindowView() {
		// TODO Auto-generated method stub
		// // ��ȡ�Զ��岼���ļ�pop.xml����ͼ
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		// dm.widthPixels
		System.out.println(dm.widthPixels);
		System.out.println(dm.heightPixels);
		double yvlaue = dm.heightPixels / 3.5;
		View customView = getLayoutInflater().inflate(R.layout.other_item,
				null, false);
		customView.setBackgroundColor(Color.BLACK);
		// ���ñ߿�
		Resources res = getBaseContext().getResources();
		Drawable draw = res.getDrawable(R.drawable.view_yuan_morelist);
		customView.setBackgroundDrawable(draw);
		// ����PopupWindowʵ��,200,150�ֱ��ǿ�Ⱥ͸߶�
		popupwindow = new PopupWindow(customView, dm.widthPixels / 3,
				(int) yvlaue);
		// ���ö���Ч�� [R.style.AnimationFade ���Լ����ȶ���õ�]
		// �Զ���view��Ӵ����¼�
		popupwindow.setFocusable(true);
		// ����������������ʧ
		popupwindow.setOutsideTouchable(true);
		popupwindow.setBackgroundDrawable(new BitmapDrawable());
		ListView secondlist = (ListView) customView
				.findViewById(R.id.secondmenu);
		SimpleAdapter adapter = new SimpleAdapter(this, getData(),
				R.layout.other_infor_item, new String[] { "title", "value" },
				new int[] { R.id.title, R.id.values });
		secondlist.setAdapter(adapter);
		secondlist.setOnItemClickListener(new seconlistClick());
		/** ���������ʵ���Զ�����ͼ�Ĺ��� */
	}

	private List<String> getName() {
		// TODO Auto-generated method stub
		Log.e("cityname", curCityString);
		Log.e("cityname01",
				curCityString.substring(0, curCityString.length() - 1));
		mapdata = new MapData();

		try {
			searchpoints = mapdata.findShops(
					curCityString.substring(0, curCityString.length() - 1), "");
			int m = searchpoints.size() / 5;
			searchlist = new ArrayList<String>();

			for (int i = 0; i < m; i++) {
				searchlist.add(searchpoints.get(i * 5 + 1).toString().trim());
			}
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(this, "��Ϣ�б��ȡʧ��", Toast.LENGTH_SHORT).show();
		}

		return searchlist;
	}

	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list;
		list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", "������");
		map.put("value", "");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "��ӡ��");
		map.put("value", "");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "����");
		map.put("value", "");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "������");
		map.put("value", "");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "��������");
		map.put("value", "");
		list.add(map);

		return list;
	}

	class seconlistClick implements OnItemClickListener {
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			if (arg2 == 0) {
				SelectValue = "���ȵ�";
				showotheraddress(coffees, SelectValue);
				popupwindow.dismiss();
			}
			if (arg2 == 1) {

				SelectValue = "��ӡ��";
				showotheraddress(printshoppoints, SelectValue);
				popupwindow.dismiss();
			}
			if (arg2 == 2) {
				SelectValue = "����";
				showotheraddress(supermarketpoints, SelectValue);
				popupwindow.dismiss();
			}
			if (arg2 == 3) {
				SelectValue = "������";
				showotheraddress(foodpoints, SelectValue);
				popupwindow.dismiss();
			}
			if (arg2 == 4) {
				SelectValue = "����";
				showotheraddress(carpoints, SelectValue);
				popupwindow.dismiss();
			}
		}
	}

	public static void setCityText(String s) {
		curCityString = s;
	}

	public static String getCityText() {
		return curCityString;
	}

	// ����
	private void search() {
		String cityname = selectcity.getText().toString();
		String shopname = searchtext.getText().toString();
		SelectValue = "����";
		searchpoints = mapdata.findShops(cityname.substring(0, 1), shopname);
		if (searchpoints.isEmpty() == true) {
			Toast.makeText(this, "�޷��ҵ����̵�", Toast.LENGTH_SHORT).show();
		} else {
			showsearchaddress();
		}

	}

	private void showsearchaddress() {
		/**
		 * ����Ҫ���Overlay�ĵط�ʹ�����´��룬 ����Activity��onCreate()��
		 */
		// ׼��Ҫ��ӵ�Overlay

		// ׼��overlayͼ�����ݣ�����ʵ������޸�
		Drawable mark = getResources().getDrawable(R.drawable.icon_gcoding);
		// ����IteminizedOverlay
		OverlayTest itemOverlay = new OverlayTest(mark, mMapView);
		int sums = searchpoints.size() / 5;
		try {
			for (int i = 0; i < sums; i++) {
				//

				String LatLon = searchpoints.get(i * 5 + 4);
				if (LatLon.equals("") || LatLon.equals("anyType{}")
						|| LatLon.isEmpty()) {
					continue;
				}
				double mLat = Double.valueOf(LatLon.split(",")[0].toString());
				double mLon = Double.valueOf(LatLon.split(",")[1].toString());
				GeoPoint p = new GeoPoint((int) (mLat * 1E6),
						(int) (mLon * 1E6));
				OverlayItem item = new OverlayItem(p, "item" + i, "item" + i);
				itemOverlay.addItem(item);

				MapController mMapController = mMapView.getController();
				mMapController.setCenter(p);// ���õ�ͼ���ĵ�
				mMapController.setZoom(14);// ���õ�ͼzoom����

				mMapView.getOverlays().clear();
				mMapView.getOverlays().add(itemOverlay);
				mMapView.refresh();
				//
			}
		} catch (Exception e) {
			Toast.makeText(this, "����λ����Ϣ", Toast.LENGTH_SHORT).show();
			mMapView.getOverlays().clear();
			mMapView.refresh();
		}

	}

	protected boolean onTap(int index) {
		// �ڴ˴���item����¼�
		if (SelectValue.equals("ȫ��")) {
			showalltip(index);
		}
		if (SelectValue.equals("���ȵ�")) {
			// showtip(coffees.get(index * 4).toString(), index, coffees);
		}
		if (SelectValue.equals("��ӡ��")) {
			// showtip(printshoppoints.get(index * 4).toString(), index,
			// printshoppoints);
		}
		if (SelectValue.equals("����")) {
			// showtip(supermarketpoints.get(index * 4).toString(), index,
			// supermarketpoints);
		}
		if (SelectValue.equals("������")) {
			// showtip(foodpoints.get(index * 4).toString(), index, foodpoints);
		}
		if (SelectValue.equals("����")) {
			// showtip(carpoints.get(index * 4).toString(), index, carpoints);
		}
		if (SelectValue.equals("�Ƶ�")) {
			showhoteltip(index);
		}
		if (SelectValue.equals("����")) {
			showsearchtip(index);
		}
		System.out.println("item onTap: " + index);
		return true;

	}

	private void showalltip(int indexs) {
		final int num = indexs;
		int sum1 = hotelpoints.size() / 5; // //hotelpoints =
											// mapdata.selectOhters("�Ƶ�");
		if (indexs < hotelpoints.size() / 5) {
			// detail = mapdata.find(name);
			address = hotelpoints.get(indexs * 5 + 3).trim().toString();
			name_address = hotelpoints.get(indexs * 5 + 1).trim().toString();
		} else {
			// detail = mapdata.find(name);
			address = shoppoints.get((indexs - sum1) * 5 + 3).trim().toString();
			name_address = shoppoints.get((indexs - sum1) * 5 + 1).trim()
					.toString();
		}
		// ����pop����ע�����¼������ӿ�
		pop = new PopupOverlay(mMapView, new PopupClickListener() {
			@Override
			public void onClickedPopup(int index) {
				// �ڴ˴���pop����¼���indexΪ�����������,�����������������
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				if (num < hotelpoints.size() / 5) {
					intent.setClass(MainActivity.this, detail_information.class);
					// bundle.putString("hotelId", hotelId);
					bundle.putString("hotelId", hotelId);
				} else {
					intent.setClass(MainActivity.this,
							RestaurantInformation.class);

					// bundle.putString("hotelId", hotelId);
					bundle.putString("hotelId", hotelId);
				}
				intent.putExtras(bundle);
				startActivity(intent);
				MainActivity.this.finish();
			}
		});
		/**
		 * ׼��pop������Դ������ʵ��������� ������������ͼƬ�Ĵ��ڣ����Դ�������ͼƬ������ͼƬ��һ��ͼƬ��
		 * �����Ĵ��ڣ������ͼƬ�Ĵ���˳����ϳ�һ��ͼƬ��ʾ. �������ͬ��ͼƬ��ʱ���ص������᷵�ص�ǰ�������ͼƬ����index
		 */
		/*
		 * Bitmap[] bmps = new Bitmap[3]; try { bmps[0] =
		 * BitmapFactory.decodeStream(getAssets().open("ic_launcher.png"));
		 * bmps[1] =
		 * BitmapFactory.decodeStream(getAssets().open("ic_launcher.png"));
		 * bmps[2] =
		 * BitmapFactory.decodeStream(getAssets().open("ic_launcher.png")); }
		 * catch (IOException e) { e.printStackTrace(); }
		 */
		double mLat;
		double mLon;
		if (indexs < hotelpoints.size() / 5) {
			String LatLon = hotelpoints.get(indexs * 5 + 4);
			mLat = Double.valueOf(LatLon.split(",")[0].toString());
			mLon = Double.valueOf(LatLon.split(",")[1].toString());
		} else {
			String LatLon = shoppoints.get((indexs - sum1) * 5 + 4);
			mLat = Double.valueOf(LatLon.split(",")[0].toString());
			mLon = Double.valueOf(LatLon.split(",")[1].toString());
		}

		GeoPoint ptTAM = new GeoPoint((int) (mLat * 1E6), (int) (mLon * 1E6));
		double forlong = DistanceUtil.getDistance(MyLocation, ptTAM);

		viewCache = getLayoutInflater()
				.inflate(R.layout.custom_text_view, null);
		popupInfo = (View) viewCache.findViewById(R.id.popinfo);
		// popupRight = (View) viewCache.findViewById(R.id.popright);
		popupText = (TextView) viewCache.findViewById(R.id.textcache);
		MapData mapdata2 = new MapData();
		List<String> pricelist = mapdata2.getHotelMinPrice(hotelId);
		if (pricelist.isEmpty()) {
			popupText.setText(name_address.trim());
		} else {
			price = pricelist.get(0).toString();
			popupText.setText(name_address.trim() + "\t" + "��" + price);
		}

		Bitmap[] bitMaps = { BMapUtil.getBitmapFromView(popupInfo),
		// BMapUtil.getBitmapFromView(popupRight)
		};
		// ��������λ��

		// ����pop,����pop
		// pop.showPopup(bmps, ptTAM, 32);
		pop.showPopup(bitMaps, ptTAM, 32);

		// ���ص���
		// pop.hidePop();
	}

	private void showhoteltip(int index) {
		// detail = mapdata.find(name);
		address = hotelpoints.get(index * 5 + 3).trim().toString();
		name_address = hotelpoints.get(index * 5 + 1).trim().toString();
		// shopStyle=detail.get(1).toString();
		// ����pop����ע�����¼������ӿ�
		pop = new PopupOverlay(mMapView, new PopupClickListener() {
			@Override
			public void onClickedPopup(int index) {
				// �ڴ˴���pop����¼���indexΪ�����������,�����������������
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				intent.setClass(MainActivity.this, detail_information.class);
				bundle.putString("name", name_address);
				bundle.putString("address", address);
				intent.putExtras(bundle);
				startActivity(intent);
				MainActivity.this.finish();
			}
		});
		/**
		 * ׼��pop������Դ������ʵ��������� ������������ͼƬ�Ĵ��ڣ����Դ�������ͼƬ������ͼƬ��һ��ͼƬ��
		 * �����Ĵ��ڣ������ͼƬ�Ĵ���˳����ϳ�һ��ͼƬ��ʾ. �������ͬ��ͼƬ��ʱ���ص������᷵�ص�ǰ�������ͼƬ����index
		 */
		/*
		 * Bitmap[] bmps = new Bitmap[3]; try { bmps[0] =
		 * BitmapFactory.decodeStream(getAssets().open("ic_launcher.png"));
		 * bmps[1] =
		 * BitmapFactory.decodeStream(getAssets().open("ic_launcher.png"));
		 * bmps[2] =
		 * BitmapFactory.decodeStream(getAssets().open("ic_launcher.png")); }
		 * catch (IOException e) { e.printStackTrace(); }
		 */

		viewCache = getLayoutInflater()
				.inflate(R.layout.custom_text_view, null);
		popupInfo = (View) viewCache.findViewById(R.id.popinfo);
		popupText = (TextView) viewCache.findViewById(R.id.textcache);
		MapData mapdata2 = new MapData();
		List<String> pricelist = mapdata2.getHotelMinPrice(hotelId);
		if (pricelist.isEmpty()) {
			popupText.setText(name_address.trim());
		} else {
			price = pricelist.get(0).toString();
			popupText.setText(name_address.trim() + "\t" + "��" + price);
		}

		Bitmap[] bitMaps = { BMapUtil.getBitmapFromView(popupInfo),
		// BMapUtil.getBitmapFromView(popupRight)
		};
		// ��������λ��

		String LatLon = hotelpoints.get(index * 5 + 4);
		double mLat = Double.valueOf(LatLon.split(",")[0].toString());
		double mLon = Double.valueOf(LatLon.split(",")[1].toString());
		GeoPoint ptTAM = new GeoPoint((int) (mLat * 1E6), (int) (mLon * 1E6));
		// ����pop,����pop
		// pop.showPopup(bmps, ptTAM, 32);
		pop.showPopup(bitMaps, ptTAM, 32);
		// ���ص���
		// pop.hidePop();
	}

	// ��λ

	private void setLocationOption() {
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setAddrType("all");// ���صĶ�λ���������ַ��Ϣ
		option.setCoorType("bd09ll");// ���صĶ�λ����ǰٶȾ�γ��,Ĭ��ֵgcj02\
		option.disableCache(true);// ��ֹ���û��涨λ\
		mLocationClient.setLocOption(option);

	}

	public class MyLocationListenner implements BDLocationListener {
		@Override
		// ����λ����Ϣ
		public void onReceiveLocation(BDLocation location) {
			showaddress();
			if (location == null)
				return;
			Drawable mark = getResources().getDrawable(
					R.drawable.icon_mylocation);
			// ����IteminizedOverlay
			OverlayTest itemOverlay = new OverlayTest(mark, mMapView);
			double mLat = location.getLatitude();
			double mLon = location.getLongitude();
			GeoPoint p = new GeoPoint((int) (mLat * 1E6), (int) (mLon * 1E6));
			OverlayItem item = new OverlayItem(p, "item", "item");
			itemOverlay.addItem(item);

			MapController mMapController = mMapView.getController();
			mMapController.setCenter(p);// ���õ�ͼ���ĵ�
			mMapController.setZoom(14);// ���õ�ͼzoom����\
			mMapView.getOverlays().clear();
			showalladdress();
			mMapView.getOverlays().add(itemOverlay);
			mMapView.refresh();

		}

		// ����POI��Ϣ����
		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
		}
	}

	private void showsearchtip(int location) {
		address = searchpoints.get(location * 5 + 3).trim().toString();
		name_address = searchpoints.get(location * 5 + 1).trim().toString();
		hotelId = searchpoints.get(location * 5 + 0).toString().trim();
		final int num = location;
		// shopStyle=detail.get(1).toString();
		// ����pop����ע�����¼������ӿ�
		pop = new PopupOverlay(mMapView, new PopupClickListener() {
			@Override
			public void onClickedPopup(int index) {
				// �ڴ˴���pop����¼���indexΪ�����������,�����������������
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				if (searchpoints.get(num * 5 + 2).trim().toString().equals("1")) {
					intent.setClass(MainActivity.this, detail_information.class);
					bundle.putString("hotelId", hotelId);
				} else {
					intent.setClass(MainActivity.this,
							RestaurantInformation.class);
					bundle.putString("hotelId", hotelId);
				}
				intent.putExtras(bundle);
				startActivity(intent);
				// MainActivity.this.finish();
			}
		});
		/**
		 * ׼��pop������Դ������ʵ��������� ������������ͼƬ�Ĵ��ڣ����Դ�������ͼƬ������ͼƬ��һ��ͼƬ��
		 * �����Ĵ��ڣ������ͼƬ�Ĵ���˳����ϳ�һ��ͼƬ��ʾ. �������ͬ��ͼƬ��ʱ���ص������᷵�ص�ǰ�������ͼƬ����index
		 */
		/*
		 * Bitmap[] bmps = new Bitmap[3]; try { bmps[0] =
		 * BitmapFactory.decodeStream(getAssets().open("ic_launcher.png"));
		 * bmps[1] =
		 * BitmapFactory.decodeStream(getAssets().open("ic_launcher.png"));
		 * bmps[2] =
		 * BitmapFactory.decodeStream(getAssets().open("ic_launcher.png")); }
		 * catch (IOException e) { e.printStackTrace(); }
		 */

		viewCache = getLayoutInflater()
				.inflate(R.layout.custom_text_view, null);
		popupInfo = (View) viewCache.findViewById(R.id.popinfo);
		popupText = (TextView) viewCache.findViewById(R.id.textcache);
		MapData mapdata2 = new MapData();
		List<String> pricelist = mapdata2.getHotelMinPrice(hotelId);
		if (pricelist.isEmpty()) {
			popupText.setText(name_address.trim());
		} else {
			price = pricelist.get(0).toString();
			popupText.setText(name_address.trim() + "\t" + "��" + price);
		}

		Bitmap[] bitMaps = { BMapUtil.getBitmapFromView(popupInfo),
		// BMapUtil.getBitmapFromView(popupRight)
		};
		// ��������λ��

		String LatLon = searchpoints.get(location * 5 + 4);
		double mLat = Double.valueOf(LatLon.split(",")[0].toString());
		double mLon = Double.valueOf(LatLon.split(",")[1].toString());
		GeoPoint ptTAM = new GeoPoint((int) (mLat * 1E6), (int) (mLon * 1E6));
		// ����pop,����pop
		// pop.showPopup(bmps, ptTAM, 32);
		pop.showPopup(bitMaps, ptTAM, 32);
		// ���ص���
		// pop.hidePop();
	}

}
