package com.example.mapforwebservice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class AboutKeZhu extends Activity {

	private LinearLayout questionLayout;
	private LinearLayout callLayout;
	private LinearLayout suggestLayout;
	private LinearLayout updateLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_kezhu);
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
		
		questionLayout = (LinearLayout) findViewById(R.id.questionLayout);
		callLayout = (LinearLayout) findViewById(R.id.callLayout);
		User_information.changeBackground(questionLayout);
		User_information.changeBackground(callLayout);
		suggestLayout = (LinearLayout) findViewById(R.id.suggestLayout);
		updateLayout = (LinearLayout) findViewById(R.id.updateLayout);
		User_information.changeBackground(suggestLayout);
		User_information.changeBackground(updateLayout);
		
		questionLayout.setOnClickListener(new OnClickListener() {
			// /常见问题
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(AboutKeZhu.this,
						AboutQuestion.class);
				startActivity(intent);
			}
		});
		callLayout.setOnClickListener(new OnClickListener() {
			// /联系我们
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(AboutKeZhu.this,
						AboutContactUs.class);
				startActivity(intent);
			}
		});
		suggestLayout.setOnClickListener(new OnClickListener() {
			// 意见反馈
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(AboutKeZhu.this,
						AboutSuggest.class);
				startActivity(intent);
			}
		});
		updateLayout.setOnClickListener(new OnClickListener() {
			//   版本升级
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			}
		});
	}
}
