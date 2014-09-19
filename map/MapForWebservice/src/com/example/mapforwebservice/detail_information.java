package com.example.mapforwebservice;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class detail_information extends Activity {

	private List<String> hotelDetail;
	private List<String> roomDetail;
	private MapData mapdata;

	private TextView nameText;
	private TextView addressText;
	private TextView phoneText;
	private TextView hotelInfo;
	private TextView hotelFood;
	private TextView hotelMeeting;
	private TextView hotelLeisure;
	private TextView hotelService;
	private TextView hotelSight;
	private TextView hotelNet;
	private TextView hotelCreditCard;
	private TextView hotelTraffic;
	private TextView hotelDiscount;
	private TextView hotelPer;
	private TextView hotelSales;
	private TextView hotelPrice;
	private LinearLayout hotelInfoLayout;
	private LinearLayout hotelFoodLayout;
	private LinearLayout hotelMeetingLayout;
	private LinearLayout hotelServiceLayout;
	private LinearLayout hotelSightLayout;
	private LinearLayout hotelNetLayout;
	private LinearLayout hotelCreditCardLayout;
	private LinearLayout hotelDiscountLayout;
	private LinearLayout hotelPerLayout;
	private LinearLayout hotelTrafficLayout;
	private LinearLayout hotelSalesLayout;
	private LinearLayout hotelPriceLayout;
	private LinearLayout hotelLeisureLayout;
	private Button reserveBtn;
	private ImageView returnbutton;
	private ListView listview;
	private String hotelId = "";
	private String hotelName;
	private String hotelAddress;
	private List<View> pageViews;
	private ImagePagerAdapter adapter;
	private ImageView[] imageViews;
	private ImageView imageView;
	private LinearLayout pagerLayout;
	private ViewPager imageViewPager;
	private AtomicInteger atomicInteger = new AtomicInteger(0);
	private SharedPreferences sp;
	private String userPhone;
	// 日期
	private String dateString = "";
	private TextView startText;
	private TextView endText;
	private int startYear = 0;
	private int startMonth = 0;
	private int startDay = 0;
	private int endYear = 0;
	private int endMonth = 0;
	private int endDay = 0;
	private int dayNum = 0;
	private String hotelState;
	private String picUrl = "http://www.kezhu.com/hotelpic/b/";
	private String pic1 = "";
	private String pic2 = "";
	private String pic3 = "";
	private Bitmap bitmap;

	//
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_information);

		sp = getSharedPreferences("login_state", Context.MODE_PRIVATE);
		userPhone = sp.getString("userPhone", "");

		Bundle bundle = this.getIntent().getExtras();
		hotelId = bundle.getString("hotelId");
		// Log.e("hotelId", hotelId);
		nameText = (TextView) findViewById(R.id.nameText);
		nameText.setFocusable(true);
		nameText.setFocusableInTouchMode(true);
		nameText.requestFocus();
		addressText = (TextView) findViewById(R.id.addressText);
		phoneText = (TextView) findViewById(R.id.phoneText);
		hotelInfo = (TextView) findViewById(R.id.hotelInfo);
		hotelFood = (TextView) findViewById(R.id.hotelFood);
		hotelMeeting = (TextView) findViewById(R.id.hotelMeeting);
		hotelLeisure = (TextView) findViewById(R.id.hotelLeisure);
		hotelService = (TextView) findViewById(R.id.hotelService);
		hotelSight = (TextView) findViewById(R.id.hotelSight);
		hotelNet = (TextView) findViewById(R.id.hotelNet);
		hotelCreditCard = (TextView) findViewById(R.id.hotelCreditCard);
		hotelDiscount = (TextView) findViewById(R.id.hotelDiscount);
		hotelPer = (TextView) findViewById(R.id.hotelPer);
		hotelTraffic = (TextView) findViewById(R.id.hotelTraffic);
		hotelSales = (TextView) findViewById(R.id.hotelSales);
		hotelPrice = (TextView) findViewById(R.id.hotelPrice);
		TextView noInfoText = (TextView) findViewById(R.id.noInfoText);
		LinearLayout timeLayout = (LinearLayout) findViewById(R.id.timeLayout);
		timeLayout.setVisibility(View.GONE);
		hotelInfoLayout = (LinearLayout) findViewById(R.id.hotelInfoLayout);
		hotelFoodLayout = (LinearLayout) findViewById(R.id.hotelFoodLayout);
		hotelMeetingLayout = (LinearLayout) findViewById(R.id.hotelMeetingLayout);
		hotelLeisureLayout = (LinearLayout) findViewById(R.id.hotelLeisureLayout);
		hotelServiceLayout = (LinearLayout) findViewById(R.id.hotelServiceLayout);
		hotelSightLayout = (LinearLayout) findViewById(R.id.hotelSightLayout);
		hotelNetLayout = (LinearLayout) findViewById(R.id.hotelNetLayout);
		hotelCreditCardLayout = (LinearLayout) findViewById(R.id.hotelCreditCardLayout);
		hotelDiscountLayout = (LinearLayout) findViewById(R.id.hotelDiscountLayout);
		hotelPerLayout = (LinearLayout) findViewById(R.id.hotelPerLayout);
		hotelTrafficLayout = (LinearLayout) findViewById(R.id.hotelTrafficLayout);
		hotelSalesLayout = (LinearLayout) findViewById(R.id.hotelSalesLayout);
		hotelPriceLayout = (LinearLayout) findViewById(R.id.hotelPriceLayout);
		reserveBtn = (Button) findViewById(R.id.reserveBtn);
		reserveBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Dialog fail2 = new AlertDialog.Builder(detail_information.this)
						.setTitle("提示")
						.setMessage("您的预定信息将会发送到酒店,请确认您的预定信息,确认后酒店将会第一时间联系您")
						.setPositiveButton("确认预定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										Toast.makeText(detail_information.this,
												"预定信息已发送到酒店", Toast.LENGTH_LONG)
												.show();
									}
								}).setNegativeButton("取消", null).create();
				fail2.show();
			}
		});

		returnbutton = (ImageView) findViewById(R.id.Hotelreturn);
		listview = (ListView) findViewById(R.id.hoteldetail);
		// introduce = (TextView) findViewById(R.id.Introducedetail);
		

		// introduce.setText(introducetext);
		mapdata = new MapData();
		roomDetail = mapdata.getRoomInfo(hotelId);
		SimpleAdapter adapter = new SimpleAdapter(this, getData(),
				R.layout.hotel_price_item, new String[] { "roomText",
						"breakfastText", "bedText", "price1", "price2" },
				new int[] { R.id.roomText, R.id.breakfastText, R.id.bedText,
						R.id.price1, R.id.price2 });
		if (roomDetail.isEmpty()) {
			noInfoText.setVisibility(View.VISIBLE);
			timeLayout.setVisibility(View.GONE);
		}
		listview.setAdapter(adapter);
		Utility.setListViewHeightBasedOnChildren(listview);
		listview.setOnItemClickListener(new hotel_itemOnClickListener());

		returnbutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// Intent intent = new Intent();
				// intent.setClass(detail_information.this, MainActivity.class);
				// startActivity(intent);
				detail_information.this.finish();
			}

		});

		// 日期选择
		LinearLayout startLayout = (LinearLayout) findViewById(R.id.startLayout);
		LinearLayout endLayout = (LinearLayout) findViewById(R.id.endLayout);
		startText = (TextView) findViewById(R.id.startText);
		endText = (TextView) findViewById(R.id.endText);
		startLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(detail_information.this,
						DateSelectActivity.class);
				startActivityForResult(intent, 1);
			}

		});
		endLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(detail_information.this,
						DateSelectActivity.class);
				startActivityForResult(intent, 2);
			}

		});

		try {
			mapdata = new MapData();
			// 读取数据
			hotelDetail = mapdata.getHotelInfoById(hotelId);
			nameText.setText(hotelDetail.get(1).toString().trim());
			addressText.setText(hotelDetail.get(6).toString().trim());
			phoneText.setText(hotelDetail.get(5).toString().trim());
			hotelInfo.setText(hotelDetail.get(7).toString().trim());
			pic1 = hotelDetail.get(9).toString().trim();
			pic2 = hotelDetail.get(10).toString().trim();
			pic3 = hotelDetail.get(11).toString().trim();
			hotelState= hotelDetail.get(12).toString().trim();
			if(!hotelState.equals("1")){
				reserveBtn.setText("该店暂不可预定");
				reserveBtn.setEnabled(false);
				reserveBtn.setBackgroundResource(R.color.unclick);
			}
			// hotelFood.setText(hotelDetail.get(12).toString().trim());
			// hotelMeeting.setText(hotelDetail.get(13).toString().trim());
			// hotelLeisure.setText(hotelDetail.get(14).toString().trim());
			// hotelService.setText(hotelDetail.get(15).toString().trim());
			// hotelSight.setText(hotelDetail.get(16).toString().trim());
			// hotelNet.setText(hotelDetail.get(17).toString().trim());
			// hotelCreditCard.setText(hotelDetail.get(18).toString().trim());
			// hotelTraffic.setText(hotelDetail.get(19).toString().trim());
			// hotelDiscount.setText(hotelDetail.get(20).toString().trim());
			// hotelPer.setText(hotelDetail.get(21).toString().trim());
			// hotelSales.setText(hotelDetail.get(23).toString().trim());
			// hotelPrice.setText(hotelDetail.get(24).toString().trim());

			hotelName = nameText.getText().toString();
			hotelAddress = addressText.getText().toString();
			if (hotelInfo.getText().toString().equals("anyType{}")
					|| hotelInfo.getText().toString().trim().equals("")) {
				hotelInfoLayout.setVisibility(View.GONE);
			}
			// if (hotelFood.getText().toString().equals("anyType{}")
			// || hotelFood.getText().toString().trim().equals("")) {
			hotelFoodLayout.setVisibility(View.GONE);
			// }
			// if (hotelMeeting.getText().toString().equals("anyType{}")
			// || hotelMeeting.getText().toString().trim().trim()
			// .equals("")) {
			hotelMeetingLayout.setVisibility(View.GONE);
			// }
			// if (hotelLeisure.getText().toString().equals("anyType{}")
			// || hotelLeisure.getText().toString().trim().equals("")) {
			hotelLeisureLayout.setVisibility(View.GONE);
			// }
			// if (hotelService.getText().toString().equals("anyType{}")
			// || hotelService.getText().toString().trim().equals("")) {
			hotelServiceLayout.setVisibility(View.GONE);
			// }
			// if (hotelSight.getText().toString().equals("anyType{}")
			// || hotelSight.getText().toString().trim().equals("")) {
			hotelSightLayout.setVisibility(View.GONE);
			// }
			// if (hotelNet.getText().toString().equals("anyType{}")
			// || hotelNet.getText().toString().trim().equals("")) {
			hotelNetLayout.setVisibility(View.GONE);
			// }
			// if (hotelCreditCard.getText().toString().equals("anyType{}")
			// || hotelCreditCard.getText().toString().trim().equals("")) {
			hotelCreditCardLayout.setVisibility(View.GONE);
			// }
			// if (hotelTraffic.getText().toString().equals("anyType{}")
			// || hotelTraffic.getText().toString().trim().equals("")) {
			hotelTrafficLayout.setVisibility(View.GONE);
			// }
			// if (hotelDiscount.getText().toString().equals("anyType{}")
			// || hotelDiscount.getText().toString().trim().equals("")) {
			hotelDiscountLayout.setVisibility(View.GONE);
			// }
			// if (hotelPer.getText().toString().equals("anyType{}")
			// || hotelPer.getText().toString().trim().equals("")) {
			hotelPerLayout.setVisibility(View.GONE);
			// }
			// if (hotelSales.getText().toString().equals("anyType{}")
			// || hotelSales.getText().toString().trim().equals("")) {
			hotelSalesLayout.setVisibility(View.GONE);
			// }
			// if (hotelPrice.getText().toString().equals("anyType{}")
			// || hotelPrice.getText().toString().trim().equals("")) {
			hotelPriceLayout.setVisibility(View.GONE);
			// }

			LinearLayout hotelPhoneLayout = (LinearLayout) findViewById(R.id.hotelPhoneLayout);

			User_information.changeBackground(hotelPhoneLayout);
			hotelPhoneLayout.setOnClickListener(new OnClickListener() {
				// /个人信息
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String phoneStr = hotelDetail.get(5).toString()
							.replace("-", "");
					Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri
							.parse("tel:" + phoneStr));
					startActivity(phoneIntent);
				}
			});

		} catch (Exception e) {

		}
		
		initViewPager();

	}

	// /////////////////////////////////////onCreat

	// /返回时间数据
	@Override
	protected void onActivityResult(int requestCode, int resuluCode, Intent data) {
		if (requestCode == 1 && resuluCode == RESULT_OK) {
			startYear = data.getExtras().getInt("year_data");
			startMonth = data.getExtras().getInt("month_data");
			startDay = data.getExtras().getInt("day_data");
			dateString = startYear + "年" + startMonth + "月" + startDay + "日";
			startText.setText(dateString);
		} else if (requestCode == 2 && resuluCode == RESULT_OK) {

			endYear = data.getExtras().getInt("year_data");
			endMonth = data.getExtras().getInt("month_data");
			endDay = data.getExtras().getInt("day_data");
			if (startYear != 0) {
				if ((endYear > startYear)
						|| (endYear == startYear && endMonth > startMonth)
						|| (endYear == startYear && endMonth == startMonth && endDay > startDay)) {
					dateString = endYear + "年" + endMonth + "月" + endDay + "日";
					endText.setText(dateString);
				} else {
					Toast.makeText(detail_information.this, "离店日期不得早于入店日期",
							Toast.LENGTH_LONG).show();
					endText.setText("离店日期");
				}
			} else {
				Toast.makeText(detail_information.this, "请先选择入住日期",
						Toast.LENGTH_LONG).show();
				endText.setText("离店日期");
			}

		}
	}

	// 图片显示
	private void initViewPager() {
		pagerLayout = (LinearLayout) findViewById(R.id.iamge_viewpager);
		imageViewPager = new ViewPager(this);

		DisplayMetrics dm = new DisplayMetrics();

		getWindowManager().getDefaultDisplay().getMetrics(dm);

		imageViewPager.setLayoutParams(new LayoutParams(dm.widthPixels,
				dm.heightPixels * 2 / 5));

		pagerLayout.addView(imageViewPager);

		initPageAdapter();

		initCirclePoint();

		imageViewPager.setAdapter(adapter);
		imageViewPager.setOnPageChangeListener(new AdPageChangeListener());
	}

	// ///图片显示栏 PagerAdapter
	class ImagePagerAdapter extends PagerAdapter {
		private List<View> views = null;

		public ImagePagerAdapter(List<View> views) {
			this.views = views;
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			((ViewPager) container).removeView(views.get(position));
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return views.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			// TODO Auto-generated method stub
			return view == object;
		}

		@Override
		public Object instantiateItem(View container, int position) {
			((ViewPager) container).addView(views.get(position), 0);
			return views.get(position);
		}

	}

	private void initPageAdapter() {
		pageViews = new ArrayList<View>();

		if (!pic1.equals("") && !pic1.equals("anyType{}")) {
			bitmap = HotelList.getPic(picUrl + pic1);
			ImageView img1 = new ImageView(this);
			img1.setImageBitmap(bitmap);
			pageViews.add(img1);
		}
		if (!pic2.equals("") && !pic2.equals("anyType{}")) {
			bitmap = HotelList.getPic(picUrl + pic2);
			ImageView img2 = new ImageView(this);
			img2.setImageBitmap(bitmap);
			pageViews.add(img2);
		}
		if (!pic3.equals("") && !pic3.equals("anyType{}")) {
			bitmap = HotelList.getPic(picUrl + pic3);
			ImageView img3 = new ImageView(this);
			img3.setImageBitmap(bitmap);
			pageViews.add(img3);
		}
		
		if(pageViews.size()==0){
			ImageView img4 = new ImageView(this);
			img4.setImageResource(R.drawable.no_pic);
			pageViews.add(img4);
		}

		adapter = new ImagePagerAdapter(pageViews);
	}

	// /小圆点
	private void initCirclePoint() {
		ViewGroup group = (ViewGroup) findViewById(R.id.dot_viewGroup);
		imageViews = new ImageView[pageViews.size()];
		// 广告栏的小圆点图标
		for (int i = 0; i < pageViews.size(); i++) {
			// 创建一个ImageView, 并设置宽高. 将该对象放入到数组中
			imageView = new ImageView(this);
			imageView.setLayoutParams(new LayoutParams(30, 30));
			imageViews[i] = imageView;

			// 初始值, 默认第0个选中
			if (i == 0) {
				imageViews[i].setBackgroundResource(R.drawable.point_focused);
			} else {
				imageViews[i].setBackgroundResource(R.drawable.point_unfocused);
			}
			// 将小圆点放入到布局中
			group.addView(imageViews[i]);
		}
	}

	private final class AdPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int arg0) {
			atomicInteger.getAndSet(arg0);
			for (int i = 0; i < imageViews.length; i++) {
				imageViews[arg0]
						.setBackgroundResource(R.drawable.point_focused);
				if (arg0 != i) {
					imageViews[i]
							.setBackgroundResource(R.drawable.point_unfocused);
				}
			}
		}
	}

	// //////////
	class hotel_itemOnClickListener implements OnItemClickListener {
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// if (userPhone.equals("")) {
			// Dialog fail = new AlertDialog.Builder(detail_information.this)
			// .setTitle("未登录")
			// .setMessage("尊敬的用户,请先登录")
			// .setPositiveButton("确定",
			// new DialogInterface.OnClickListener() {
			// @Override
			// public void onClick(DialogInterface dialog,
			// int which) {
			// Intent intent = new Intent(
			// detail_information.this,
			// User_login.class);
			// startActivity(intent);
			// finish();
			// }
			// })
			// .setNegativeButton("取消",
			// new DialogInterface.OnClickListener() {
			// public void onClick(DialogInterface dialog,
			// int whichButton) {
			// // 取消按钮事件
			// }
			// }).create();
			// fail.show();
			// } else if (startYear == 0 || endYear == 0) {
			// Toast.makeText(detail_information.this, "请选择日期",
			// Toast.LENGTH_LONG).show();
			// } else {
			// // TODO Auto-generated method stub
			// String startdateString=startYear + "-" + startMonth + "-" +
			// startDay + "-";
			// String enddateString=endYear + "-" + endMonth + "-" + endDay +
			// "-";
			// try {
			// dayNum=daysBetween(startdateString,enddateString);
			// } catch (ParseException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			//
			// TextView roomText = (TextView) listview.getChildAt(arg2)
			// .findViewById(R.id.roomText);
			// TextView price2 = (TextView) listview.getChildAt(arg2)
			// .findViewById(R.id.price2);
			// String roomtype = roomText.getText().toString();
			// String priceStr = price2.getText().toString();
			// Intent intent = new Intent(detail_information.this,
			// RoomDetail.class);
			// Bundle bundle = new Bundle();
			// bundle.putInt("dayNum", dayNum);
			// bundle.putInt("startYear", startYear);
			// bundle.putInt("startMonth", startMonth);
			// bundle.putInt("startDay", startDay);
			// bundle.putInt("endYear", endYear);
			// bundle.putInt("endMonth", endMonth);
			// bundle.putInt("endDay", endDay);
			//
			// bundle.putString("hotelName", hotelName);
			// bundle.putString("hotelAddress", hotelAddress);
			// bundle.putString("roomtype", roomtype);
			// bundle.putString("price", priceStr);
			// intent.putExtras(bundle);
			// startActivity(intent);
			// }

		}
	}

	// ////////////////
	// class hotel_itemOnClickListener implements OnItemClickListener {
	// public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
	// long arg3) {
	// // TODO Auto-generated method stub
	// TextView text = (TextView) listview.getChildAt(arg2).findViewById(
	// R.id.pricetitle);
	// String roomtype = text.getText().toString();
	// Intent intent = new Intent(detail_information.this, OrderPage.class);
	// Bundle bundle = new Bundle();
	// bundle.putString("name", Name);
	// bundle.putString("address", Address);
	// bundle.putString("hoteltype", roomtype);
	//
	// intent.putExtras(bundle);
	//
	// startActivity(intent);
	// detail_information.this.finish();
	// }
	// }

	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		int m = roomDetail.size() / 9;
		Map<String, Object> map;

		for (int i = 0; i < m; i++) {
			map = new HashMap<String, Object>();
			map.put("roomText", roomDetail.get(i * 9 + 1).toString().trim());
			map.put("breakfastText", roomDetail.get(i * 9 + 5).toString()
					.trim());
			map.put("bedText", roomDetail.get(i * 9 + 3).toString().trim());
			map.put("price2", roomDetail.get(i * 9 + 2).toString().trim());
			map.put("price1", roomDetail.get(i * 9 + 8).toString().trim());
			list.add(map);

		}
		return list;
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// Intent intent = new Intent();
			// intent.setClass(detail_information.this, MainActivity.class);
			// startActivity(intent);
			detail_information.this.finish();
			return false;
		}
		return false;
	}

	// 天数计算
	public int daysBetween(String smdate, String bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(smdate));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(bdate));
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(between_days));
	}

}
