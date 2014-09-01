package com.example.mapforwebservice;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class UserEquity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_equity);

		TextView textView6 = (TextView) findViewById(R.id.textView6);
		SpannableStringBuilder builder = new SpannableStringBuilder(textView6
				.getText().toString());
		// ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
		ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.BLUE);
		builder.setSpan(redSpan, 11, 15, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		textView6.setText(builder);
		
		TextView textView7 = (TextView) findViewById(R.id.textView7);
		SpannableStringBuilder builder2 = new SpannableStringBuilder(textView7
				.getText().toString());
		// ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
		ForegroundColorSpan redSpan2 = new ForegroundColorSpan(Color.BLUE);
		builder2.setSpan(redSpan2, 13, 17, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		textView7.setText(builder2);
		
		

		textView6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(UserEquity.this, UserRules.class);
				startActivity(intent);
			}
		});
		textView7.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(UserEquity.this, UserRules.class);
				startActivity(intent);
			}
		});

		ImageView returnbtn = (ImageView) findViewById(R.id.returnbtn);
		returnbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// Intent intent = new Intent();
				// intent.setClass(UserRules.this, User_information.class);
				// startActivity(intent);
				finish();
			}

		});
	}
}
