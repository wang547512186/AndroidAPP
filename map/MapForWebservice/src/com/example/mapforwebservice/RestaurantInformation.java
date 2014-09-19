package com.example.mapforwebservice;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RestaurantInformation extends Activity {
	private List<String> detail;
	private MapData mapdata;

	// 图片信息
	private List<View> pageViews;
	private ImagePagerAdapter adapter;
	private ImageView[] imageViews;
	private ImageView imageView;
	private LinearLayout pagerLayout;
	private ViewPager imageViewPager;
	private AtomicInteger atomicInteger = new AtomicInteger(0);
	//
	private String shopId = "11";
	String phoneStr;
	private String restName;
	private String picUrl = "http://www.kezhu.com/hotelpic/b/";
	private String pic1 = "";
	private String pic2 = "";
	private String pic3 = "";
	private Bitmap bitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.restaurant_information);

		ImageView returnbtn = (ImageView) findViewById(R.id.returnbtn);
		returnbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}

		});

		

		Bundle bundle = this.getIntent().getExtras();
		restName = bundle.getString("name");
		shopId = bundle.getString("hotelId");
		TextView restName = (TextView) findViewById(R.id.restName);
		TextView restAddress = (TextView) findViewById(R.id.restAddress);
		TextView restPhone = (TextView) findViewById(R.id.restPhone);
		TextView restInfo = (TextView) findViewById(R.id.restInfo);
		TextView restTraffic = (TextView) findViewById(R.id.restTraffic);
		TextView restSale = (TextView) findViewById(R.id.restSale);
		TextView restPrice = (TextView) findViewById(R.id.restPrice);
		LinearLayout restInfoLayout = (LinearLayout) findViewById(R.id.restInfoLayout);
		LinearLayout restTrafficLayout = (LinearLayout) findViewById(R.id.restTrafficLayout);
		LinearLayout restSaleLayout = (LinearLayout) findViewById(R.id.restSaleLayout);
		LinearLayout restPriceLayout = (LinearLayout) findViewById(R.id.restPriceLayout);
		try {
			mapdata = new MapData();
			detail = mapdata.getHotelInfoById(shopId);
			restName.setText(detail.get(1).toString().trim());
			restPhone.setText(detail.get(5).toString().trim());
			restAddress.setText(detail.get(6).toString().trim());
			restInfo.setText(detail.get(7).toString().trim());
			pic1 = detail.get(9).toString().trim();
			pic2 = detail.get(10).toString().trim();
			pic3 = detail.get(11).toString().trim();
//			restTraffic.setText(detail.get(12).toString().trim());
//			restSale.setText(detail.get(16).toString().trim());
//			restPrice.setText(detail.get(17).toString().trim());
		} catch (Exception e) {
		}

		if (detail.get(7).toString().equals("anyType{}")) {
			restInfoLayout.setVisibility(View.GONE);
		}
//		if (detail.get(12).toString().equals("anyType{}")) {
			restTrafficLayout.setVisibility(View.GONE);
//		}
//		if (detail.get(16).toString().equals("anyType{}")) {
			restSaleLayout.setVisibility(View.GONE);
//		}
//		if (detail.get(17).toString().equals("anyType{}")) {
			restPriceLayout.setVisibility(View.GONE);
//		}

		LinearLayout restPhoneLayout = (LinearLayout) findViewById(R.id.restPhoneLayout);

		User_information.changeBackground(restPhoneLayout);
		restPhoneLayout.setOnClickListener(new OnClickListener() {
			// /个人信息
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				phoneStr = detail.get(6).toString().replace("-", "");
				Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri
						.parse("tel:" + phoneStr));
				startActivity(phoneIntent);
			}
		});
		initViewPager();

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
}
