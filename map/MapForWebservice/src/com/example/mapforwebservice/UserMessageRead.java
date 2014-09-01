package com.example.mapforwebservice;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

public class UserMessageRead extends Activity {
	private String msgTitleStr = "";
	private String msgTextStr = "";
	private TextView msgTitle;
	private TextView msgText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		
		setContentView(R.layout.user_message_read);
		
		
		msgTitle = (TextView) findViewById(R.id.msgTitle);
		msgText = (TextView) findViewById(R.id.msgText);
		Bundle bundle = this.getIntent().getExtras();
		msgTitleStr = bundle.getString("msgTitleStr");
		msgTextStr = bundle.getString("msgTextStr");
		msgTitle.setText(msgTitleStr);
		msgText.setText(msgTextStr);
		
		ImageView closeBtn = (ImageView) findViewById(R.id.closeBtn);
		closeBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// Intent intent = new Intent();
				// intent.setClass(detail_information.this, MainActivity.class);
				// startActivity(intent);
				finish();
			}

		});
		
	}
}
