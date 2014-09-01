package com.example.mapforwebservice;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class AboutContactUs extends Activity {
	private LinearLayout phoneLayout;
	String phoneStr = "4008853948";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_contact_us);
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
		phoneLayout = (LinearLayout) findViewById(R.id.phoneLayout);

		User_information.changeBackground(phoneLayout);
		phoneLayout.setOnClickListener(new OnClickListener() {
			// /个人信息
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent phoneIntent = new Intent(Intent.ACTION_DIAL,
						Uri.parse("tel:" + phoneStr));
				startActivity(phoneIntent);
			}
		});
	}
}
