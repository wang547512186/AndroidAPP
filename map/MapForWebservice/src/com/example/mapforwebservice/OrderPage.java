package com.example.mapforwebservice;

import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class OrderPage extends Activity {

	private EditText phoneName;
	private EditText phoneNumber;
	private List<String> detail;
	private String priceStr;
	private int startYear = 0;
	private int startMonth = 0;
	private int startDay = 0;
	private int endYear = 0;
	private int endMonth = 0;
	private int endDay = 0;
	private int totalPrice = 0;
	// private LinearLayout enterdate;
	// private LinearLayout leavedate;
	private TextView enterdate_text;
	private TextView leavedate_text;
	private MapData mapdata;
	// private String dateStr;
	private SharedPreferences sp;
	private String UserName;
	private String nickName;
	private String hotelName;
	private String hotelAddress;
	private String strHoteltype;
	private String enterDate;
	private String endDate;
	private String stayPerson;
	private String telephone;private TextView totalPriceText;

	private int dayNum = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_page);
		mapdata = new MapData();
		sp = getSharedPreferences("login_state", Context.MODE_PRIVATE);
		UserName = sp.getString("userName", "");
		phoneName = (EditText) findViewById(R.id.phoneName);
		phoneNumber = (EditText) findViewById(R.id.phoneNumber);
		
		if (!UserName.equals("")) {
			detail = mapdata.userInfor(UserName);
			nickName = detail.get(1).toString().trim();
			phoneName.setText(detail.get(1).toString().trim());
			phoneNumber.setText(detail.get(5).toString().trim());
		}

		Bundle bundle = this.getIntent().getExtras();
		dayNum = bundle.getInt("dayNum");
		hotelName = bundle.getString("hotelName");
		hotelAddress = bundle.getString("hotelAddress");
		strHoteltype = bundle.getString("hoteltype");
		priceStr = bundle.getString("priceStr");
		startYear = bundle.getInt("startYear");
		startMonth = bundle.getInt("startMonth");
		startDay = bundle.getInt("startDay");
		endYear = bundle.getInt("endYear");
		endMonth = bundle.getInt("endMonth");
		endDay = bundle.getInt("endDay");
		totalPriceText= (TextView) findViewById(R.id.totalPriceText);
		
		totalPrice=Integer.valueOf(priceStr)*dayNum;
		totalPriceText.setText(""+totalPrice+"  元 ");
//		Toast.makeText(OrderPage.this, priceStr+""+dayNum, Toast.LENGTH_LONG).show();
		// 日期选择
		// enterdate = (LinearLayout) findViewById(R.id.enterdate);
		// leavedate = (LinearLayout) findViewById(R.id.leavedate);
		enterdate_text = (TextView) findViewById(R.id.enterdate_text);
		leavedate_text = (TextView) findViewById(R.id.leavedate_text);
		
		enterdate_text.setText(startYear + "年" + startMonth + "月" + startDay
				+ "日");
		leavedate_text.setText(endYear + "年" + endMonth + "月" + endDay + "日");
		enterDate = startYear + "-" + startMonth + "-" + startDay;
		endDate = endYear + "-" + endMonth + "-" + endDay;

		// 读取联系人
		ImageView phone_imageview = (ImageView) findViewById(R.id.phone);

		phone_imageview.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_PICK,
						ContactsContract.Contacts.CONTENT_URI);
				OrderPage.this.startActivityForResult(intent, 1);
			}

		});

		TextView hotelname = (TextView) findViewById(R.id.orde_hotelname);
		TextView hoteladdress = (TextView) findViewById(R.id.orde_hoteladdress);
		TextView hoteltype = (TextView) findViewById(R.id.hoteltype);

		hoteltype.setText(strHoteltype);
		hotelname.setText(hotelName);
		hoteladdress.setText(hotelAddress);

		// 提交按钮
		final LinearLayout submit = (LinearLayout) findViewById(R.id.submit);
		submit.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					submit.setBackgroundResource(R.color.button_down);
					// 按下
					break;
				case MotionEvent.ACTION_UP:
					// 抬起
					submit.setBackgroundResource(R.color.button_up);
					break;
				}
				return false;
			}

		});

		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (UserName.equals("")) { // /未登录
					Dialog fail = new AlertDialog.Builder(OrderPage.this)
							.setTitle("提示").setMessage("请先登录")
							.setPositiveButton("确定", null).create();
					fail.show();
				} else { // 已登录
					stayPerson = phoneName.getText().toString();
					telephone = phoneNumber.getText().toString();
					Log.e("UserName", UserName);
					Log.e("nickName", nickName);
					Log.e("stayPerson", stayPerson);
					Log.e("strHoteltype", strHoteltype);
					Log.e("priceStr", priceStr);
					Log.e("telephone", telephone);
					Log.e("hotelName", hotelName);
					Log.e("hotelAddress", hotelAddress);
					Log.e("enterDate", enterDate);
					Log.e("endDate", endDate);

					if (mapdata.addOrder(UserName, nickName, stayPerson,
							strHoteltype, ""+totalPrice, telephone, hotelName,
							hotelAddress, enterDate, endDate).equals("true")) {
						Dialog success = new AlertDialog.Builder(OrderPage.this)
								.setTitle("提示")
								.setMessage("预定成功")
								.setPositiveButton("确定",
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												finish();
											}
										}).create();
						success.show();
					} else {
						Dialog fail = new AlertDialog.Builder(OrderPage.this)
								.setTitle("提示").setMessage("预定失败")
								.setPositiveButton("确定", null).create();
						fail.show();
					}

				}
			}

		});

		// 返回按钮
		ImageView returnbtn = (ImageView) findViewById(R.id.orderreturn);
		returnbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Intent intent = new Intent();
				// intent.setClass(OrderPage.this, detail_information.class);
				// Bundle bundle = new Bundle();
				// bundle.putString("name", Name);
				// bundle.putString("address", Address);
				// intent.putExtras(bundle);
				// startActivity(intent);
				OrderPage.this.finish();
			}

		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {

		case (1): {

			if (resultCode == Activity.RESULT_OK) {

				Uri contactData = data.getData();

				Cursor c = managedQuery(contactData, null, null, null, null);

				c.moveToFirst();

				String phoneResult[] = this.getContactPhone(c);
				phoneName.setText(phoneResult[0]);
				phoneNumber.setText(phoneResult[1]);
			}
			break;
		}
		}

	}

	private String[] getContactPhone(Cursor cursor) {
		// TODO Auto-generated method stub

		int phoneColumn = cursor
				.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
		int phoneNum = cursor.getInt(phoneColumn);
		String[] phoneResult = { "", "" };

		// System.out.print(phoneNum);
		if (phoneNum > 0) {
			// 获得联系人的ID号
			int idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID);
			String contactId = cursor.getString(idColumn);
			// 获得联系人的电话号码的cursor;
			Cursor phones = getContentResolver().query(
					ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
					null,
					ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "
							+ contactId, null, null);
			// int phoneCount = phones.getCount();
			// allPhoneNum = new ArrayList<String>(phoneCount);
			if (phones.moveToFirst()) {
				// 遍历所有的电话号码
				for (; !phones.isAfterLast(); phones.moveToNext()) {
					int index = phones
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
					int index2 = phones
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
					int typeindex = phones
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE);
					int phone_type = phones.getInt(typeindex);
					String phoneName = phones.getString(index);
					String phoneNumber = phones.getString(index2);
					switch (phone_type) {
					case 2:
						phoneResult[0] = phoneName;
						phoneResult[1] = phoneNumber;
						break;
					}
					// allPhoneNum.add(phoneNumber);
				}
				if (!phones.isClosed()) {
					phones.close();
				}
			}
		}
		return phoneResult;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// Intent intent = new Intent();
			// intent.setClass(OrderPage.this, detail_information.class);
			// Bundle bundle = new Bundle();
			// bundle.putString("name", Name);
			// bundle.putString("address", Address);
			// intent.putExtras(bundle);
			// startActivity(intent);
			OrderPage.this.finish();
		}
		return false;
	}

}
