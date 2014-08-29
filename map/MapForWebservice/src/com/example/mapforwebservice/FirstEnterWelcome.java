package com.example.mapforwebservice;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class FirstEnterWelcome extends Activity {
	private SharedPreferences sp;
	private List<View> pageViews;
	private ImagePagerAdapter adapter;
	private ImageView[] imageViews;
	private ImageView imageView;
	private LinearLayout pagerLayout;
	private ViewPager imageViewPager;
	private LayoutInflater inflater;
	private AtomicInteger atomicInteger = new AtomicInteger(0);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.first_enter_welcome);
		inflater = getLayoutInflater();
		sp = getSharedPreferences("setting_state", Context.MODE_PRIVATE);
		initViewPager();
	}

	private void initViewPager() {
		pagerLayout = (LinearLayout) findViewById(R.id.iamge_viewpager);
		imageViewPager = new ViewPager(this);
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);

		imageViewPager.setLayoutParams(new LayoutParams(dm.widthPixels,
				dm.heightPixels));

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
			if (position == 2) {
				Button btn = (Button) container.findViewById(R.id.enterBtn);
				btn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(FirstEnterWelcome.this,
								MainActivity.class);
						Editor editor = sp.edit();
						editor.putBoolean("isEnter", true);
						editor.commit();
						startActivity(intent);
						finish();
					}

				});
			}

			return views.get(position);
		}

	}

	private void initPageAdapter() {
		pageViews = new ArrayList<View>();

		ImageView img1 = new ImageView(this);
		img1.setImageResource(R.drawable.g1);

		pageViews.add(img1);

		ImageView img2 = new ImageView(this);
		img2.setImageResource(R.drawable.g2);
		pageViews.add(img2);


		pageViews.add(inflater.inflate(R.layout.enter_layout, null));

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
