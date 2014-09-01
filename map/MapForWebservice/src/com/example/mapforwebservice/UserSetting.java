package com.example.mapforwebservice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class UserSetting extends Activity {
	
	private boolean switch1_state = true;
	private boolean switch2_state = true;
	private boolean switch3_state = true;
	private ImageView switch1;
	private ImageView switch2;
	private ImageView switch3;
	private SharedPreferences sharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_setting);
		
		sharedPreferences = getSharedPreferences("setting_state",
				Context.MODE_PRIVATE);
		// 读取数据
		switch1_state = sharedPreferences.getBoolean("state1", true);
		switch2_state = sharedPreferences.getBoolean("state2", true);
		switch3_state = sharedPreferences.getBoolean("state3", true);
		switch1 = (ImageView) findViewById(R.id.switch1);
		initState(switch1, switch1_state);
		switch2 = (ImageView) findViewById(R.id.switch2);
		initState(switch2, switch2_state);
		switch3 = (ImageView) findViewById(R.id.switch3);
		initState(switch3, switch3_state);
		LinearLayout clearLayout = (LinearLayout) findViewById(R.id.clearLayout);
		User_information.changeBackground(clearLayout);
		clearLayout.setOnClickListener(new OnClickListener() {
			// /清除缓存
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sharedPreferences.edit().clear().commit();
				initState(switch1, true);
				initState(switch2, true);
				initState(switch3, true);
			}
		});

		switch1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				switch1_state = changeState(switch1, switch1_state); // 私有数据
				Editor editor = sharedPreferences.edit();
				editor.putBoolean("state1", switch1_state);
				editor.commit();
				// Log.d("setting_state",
				// String.valueOf(sharedPreferences.getBoolean("state",
				// false)));
			}

		});
		switch2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				switch2_state = changeState(switch2, switch2_state);
				// 私有数据
				Editor editor = sharedPreferences.edit();
				editor.putBoolean("state2", switch2_state);
				editor.commit();
			}

		});
		switch3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				switch3_state = changeState(switch3, switch3_state);// 私有数据
				Editor editor = sharedPreferences.edit();
				editor.putBoolean("state3", switch3_state);
				editor.commit();
			}

		});

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
		
	}

	private void initState(ImageView view, Boolean bool) {
		if (bool) {
			view.setImageResource(R.drawable.switch_on);
		} else {
			view.setImageResource(R.drawable.switch_off);
		}

	}

	private Boolean changeState(ImageView view, Boolean state) {
		if (state) {
			view.setImageResource(R.drawable.switch_off);
			state = false;
		} else {
			view.setImageResource(R.drawable.switch_on);
			state = true;
		}
		return state;
	}

}
