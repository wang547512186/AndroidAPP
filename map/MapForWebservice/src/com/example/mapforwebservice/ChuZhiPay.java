package com.example.mapforwebservice;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Nickname;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ChuZhiPay extends Activity {
	private Button payconfirmBtn;
	private String hotelId;
	private String hotelNameStr;
	//private String hotelChuzhiStr;
	private String samesystemmoney;
	private String hotelAddressStr;
	private String hoteltotalid;
	private Double chuzhi = 0.0;
	private EditText requestChuzhi;
	private Double requestChuzhiNumber;
	private SharedPreferences sp;
	private String userPhone = "";
	private String hoteltotalId = "";
	private String uid = "";
	private MapData mapdata;
	private List<String> hotelInfoList;
	private static final int RESULT_FAIL = 1;
	private static final int RESULT_SUCCESS = 2;
	private ArrayAdapter<String> adapter;
    private List<String> namehotel;
    private List<String> hotelnametotal;
    private TextView hoteladdress;
    private int site=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chu_zhi_pay);
		Intent intent = getIntent();
		hotelId = intent.getStringExtra("hotelId");
		sp = getSharedPreferences("login_state", Context.MODE_PRIVATE);
		userPhone = sp.getString("userPhone", "");
		uid = sp.getString("uid", "");
		mapdata = new MapData();
		hotelInfoList = mapdata.hotelshopInfo(hotelId);
		if (!hotelInfoList.isEmpty()) {
			hoteltotalId= hotelInfoList.get(1).toString().trim();
		}

		
		
		// Toast.makeText(ChuZhiPay.this,hotelId, Toast.LENGTH_LONG).show();
		hotelNameStr = intent.getStringExtra("hotelname");
		//hotelChuzhiStr = intent.getStringExtra("hotelchuzhi");
		hotelAddressStr = intent.getStringExtra("hoteladdress");
		hoteltotalid = intent.getStringExtra("hoteltotalid");
		Spinner hotelname = (Spinner) findViewById(R.id.hotelname);
		TextView hotelchuzhi = (TextView) findViewById(R.id.hotelchuzhi);
		hoteladdress = (TextView) findViewById(R.id.hoteladdress);
		hotelnametotal=new ArrayList<String>();
		namehotel=new ArrayList<String>();
		hotelnametotal=mapdata.getSamesytemname(hoteltotalid);
		
		for(int i=0;i<hotelnametotal.size()/3;i++)
		{
			namehotel.add(hotelnametotal.get(i*3+1));
			if(hotelNameStr.equals( hotelnametotal.get(i*3+1)))
				site=i;
		}
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,namehotel);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        //将adapter 添加到spinner中
		hotelname.setAdapter(adapter);
		
		hotelname.setOnItemSelectedListener(new SpinnerSelectedListener());
        
        //设置默认值
		hotelname.setVisibility(View.VISIBLE);
		//hotelname.setText(hotelNameStr);
		hotelname.setSelection(site, true);
		hoteladdress.setText(hotelAddressStr);

		requestChuzhi = (EditText) findViewById(R.id.requestChuzhi);
		samesystemmoney=mapdata.getUserSamesytemchuzhi(uid,hoteltotalid);
		chuzhi = Double.valueOf(samesystemmoney);
		hotelchuzhi.setText(samesystemmoney);
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

		payconfirmBtn = (Button) findViewById(R.id.payconfirmBtn);
		payconfirmBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				requestChuzhiNumber = Double.valueOf(requestChuzhi.getText()
						.toString());
				if (requestChuzhi.getText().toString().equals("")) {
					Dialog dialog = new AlertDialog.Builder(ChuZhiPay.this)
							.setTitle("提示").setMessage("请输入金额!")
							.setPositiveButton("确定", null).create();
					dialog.show();
				} else if (requestChuzhiNumber > chuzhi) {
					Dialog dialog = new AlertDialog.Builder(ChuZhiPay.this)
							.setTitle("提示").setMessage("余额不足,无法支付!")
							.setPositiveButton("确定", null).create();
					dialog.show();
				} else {
					Intent intent = new Intent(ChuZhiPay.this, PayConfirm.class);
					intent.putExtra("userPhone", userPhone);
					intent.putExtra("ActivityName", "ChuZhiPay");
					startActivityForResult(intent, 3);
				}

			}

		});
		
		
		
	}
	
	class SpinnerSelectedListener implements OnItemSelectedListener{
		 
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                long arg3) {
        	
        	hoteladdress.setText(hotelnametotal.get(arg2*3+2));
        	hotelId=hotelnametotal.get(arg2*3);
        }
 
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

	protected void onActivityResult(int requestCode, int resuluCode, Intent data) {
		if (requestCode == 3 && resuluCode == RESULT_FAIL) {
		}
		if (requestCode == 3 && resuluCode == RESULT_SUCCESS) {
			mapdata = new MapData();
			String payResult = mapdata.chuzhiConsume(uid, hotelId, hoteltotalId, String.valueOf(requestChuzhiNumber), uid);

			if (payResult.equals("true")) {
				Dialog dialog2 = new AlertDialog.Builder(ChuZhiPay.this)
						.setTitle("支付结果")
						.setMessage("储值支付成功!")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										Intent intent = new Intent(
												ChuZhiPay.this,
												ChuZhiSelect.class);
										startActivity(intent);
										ChuZhiPay.this.finish();
									}
								}).create();
				dialog2.show();
			} else {
				Dialog dialog2 = new AlertDialog.Builder(ChuZhiPay.this)
						.setTitle("支付结果").setMessage("支付失败!")
						.setPositiveButton("确定", null).create();
				dialog2.show();
			}
		}

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent(ChuZhiPay.this, ChuZhiSelect.class);
			startActivity(intent);
			ChuZhiPay.this.finish();
		}
		return false;
	}

}
