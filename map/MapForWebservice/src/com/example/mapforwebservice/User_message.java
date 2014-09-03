package com.example.mapforwebservice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class User_message extends Activity {
	private SharedPreferences sharedPreferences;
	private ListView listview;
	private List<Map<String, Object>> list;
	private List<String> messagelist;
	private MapData mapdata;
	private boolean switch1_state;
	private boolean switch2_state;
	private boolean switch3_state;
	public Vibrator vibrator;
	private SoundPool soundPool;
	private String UserName;
	private LinearLayout historyEmpty;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_message);
		Intent intent = getIntent();
		UserName = intent.getStringExtra("UserName");
		mapdata = new MapData();
		messagelist = mapdata.findMessage(UserName);
		historyEmpty = (LinearLayout) findViewById(R.id.historyEmpty);
		vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		// 测试
		sharedPreferences = getSharedPreferences("setting_state",
				Context.MODE_PRIVATE);
		// 读取数据
		switch1_state = sharedPreferences.getBoolean("state1", true);
		switch2_state = sharedPreferences.getBoolean("state2", true);
		switch3_state = sharedPreferences.getBoolean("state3", true);
		// 测试

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

		listview = (ListView) findViewById(R.id.usermessage);
		SimpleAdapter adapter = new SimpleAdapter(this, getData(),
				R.layout.user_message_item, new String[] { "messagetitle",
						"messagedate", "messagetext" }, new int[] {
						R.id.messagetitle, R.id.messagedate, R.id.messagetext });
		listview.setAdapter(adapter);
		listview.setDividerHeight(0);

		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				TextView messagetitle = (TextView) listview
						.getChildAt(position).findViewById(R.id.messagetitle);
				TextView messagetext = (TextView) listview.getChildAt(position)
						.findViewById(R.id.messagetext);
				String msgTitleStr = messagetitle.getText().toString();
				String msgTextStr = messagetext.getText().toString();
				Intent intent = new Intent(User_message.this,
						UserMessageRead.class);
				Bundle bundle = new Bundle();
				bundle.putString("msgTitleStr", msgTitleStr);
				bundle.putString("msgTextStr", msgTextStr);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});

		// 测试
		// Button testBtn = (Button) findViewById(R.id.testBtn);
		// testBtn.setOnClickListener(new OnClickListener() {

		// @Override
		// public void onClick(View arg0) {
		// if (switch1_state) { // 通知栏提示
		// // 定义NotificationManager
		// String ns = Context.NOTIFICATION_SERVICE;
		// NotificationManager mNotificationManager = (NotificationManager)
		// getSystemService(ns);
		// // 定义通知栏展现的内容信息
		// int icon = R.drawable.app_icon;
		// CharSequence tickerText = "您有新消息";
		// long when = System.currentTimeMillis();
		// Notification notification = new Notification(icon,
		// tickerText, when);
		// notification.flags |= Notification.FLAG_AUTO_CANCEL;
		// Context context = getApplicationContext();
		//
		// CharSequence contentTitle = "新消息";
		//
		// CharSequence contentText = "点击查看详情";
		//
		// Intent notificationIntent = new Intent(User_message.this,
		// User_message.class);
		// PendingIntent contentIntent = PendingIntent.getActivity(
		// User_message.this, 0, notificationIntent, 0);
		//
		// notification.setLatestEventInfo(context, contentTitle,
		// contentText, contentIntent);
		//
		// // 用mNotificationManager的notify方法通知用户生成标题栏消息通知
		//
		// mNotificationManager.notify(1, notification);
		// } else {
		//
		// }
		//
		// if (switch2_state) { // 声音
		// soundPool = new SoundPool(10, AudioManager.STREAM_RING, 5);
		// soundPool.load(User_message.this, R.raw.sound_msg, 1);
		// soundPool.play(1, 1, 1, 0, 0, 1);
		// } else {
		//
		// }
		//
		// if (switch3_state) {// / 震动
		//
		// vibrator.vibrate(new long[] { 100, 200, 100, 200 }, -1);
		// } else {
		//
		// vibrator.vibrate(new long[] { 100, 0, 100, 0 }, -1);
		// }
		// }
		//
		// });

	}

	private List<Map<String, Object>> getData() {
		// TODO Auto-generated method stub
		int m = messagelist.size() / 4;
		list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		for (int i = 0; i < m; i++) {
			map = new HashMap<String, Object>();
			map.put("img", R.drawable.hotel1);
			map.put("messagetitle", messagelist.get(i * 4 + 1).toString());
			map.put("messagetext", messagelist.get(i * 4 + 2).toString());
			map.put("messagedate", messagelist.get(i * 4 + 3).toString());
			list.add(map);

		}
		if (!list.isEmpty()) {
			historyEmpty.setVisibility(View.GONE);
		}

		return list;
	}

	public boolean onKeyDown(int KeyCode, KeyEvent event) {
		if (KeyCode == KeyEvent.KEYCODE_BACK) {

			User_message.this.finish();
		}
		return false;
	}
}
