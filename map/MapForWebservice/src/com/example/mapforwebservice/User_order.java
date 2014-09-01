package com.example.mapforwebservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class User_order extends Activity{
	private ListView listview;
    private List<Map<String,Object>> list;
    private List<String> orderlist;
    private MapData mapdata;
    
    private String UserName="";
    private String orderId;
    private String hotelName;
    private String roomStyle;
    private String addressHotel;
    private String beginDate;
    private String endDate;
    private String customerName;
    private String mobilePhone;
    private String orderPrice;
    public void onCreate(Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);
    	
    	setContentView(R.layout.user_order_list);
    	ImageView returnbtn = (ImageView) findViewById(R.id.orderreturn);
    	returnbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// Intent intent = new Intent();
				// intent.setClass(detail_information.this, MainActivity.class);
				// startActivity(intent);
				User_order.this.finish();
			}

		});
    	Intent intent=getIntent();
    	UserName=intent.getStringExtra("UserName");
    	
    	mapdata=new MapData();
    	orderlist=mapdata.getUserOrder(UserName);
    	listview=(ListView)findViewById(R.id.userorderlist);
    	SimpleAdapter adapter=new SimpleAdapter(this,getData(),R.layout.user_order_list_item,
    			                 new String[] {"hotelName","addressHotel","orderimg","beginDate","orderPrice"},
    			                 new int[] {R.id.ordername,R.id.orderaddress,R.id.orderimg,R.id.orderdate,R.id.orderprice});
    	listview.setAdapter(adapter);
    	listview.setDividerHeight(0);
    	listview.setOnItemClickListener(new order_itemOnClickListener());
    	
    }
	private List< Map<String, Object>> getData() {
		// TODO Auto-generated method stub
		int m=orderlist.size()/12;
		list=new ArrayList<Map<String,Object>>();
		Map<String,Object> map;
		
		for(int i=0;i<m;i++)
		{
			map=new HashMap<String,Object>();
			map.put("orderId", orderlist.get(i*12+0).toString());   
		    map.put("hotelName", orderlist.get(i*12+7).toString());   
		    map.put("roomStyle", orderlist.get(i*12+4).toString());
		    map.put("addressHotel", orderlist.get(i*12+8).toString());
		    map.put("beginDate", orderlist.get(i*12+10).toString());
		    map.put("endDate", orderlist.get(i*12+11).toString());
		    map.put("customerName", orderlist.get(i*12+3).toString());
		    map.put("mobilePhone", orderlist.get(i*12+6).toString());
		    map.put("orderPrice", orderlist.get(i*12+5).toString());
		    map.put("orderimg", R.drawable.hotel1);
		    list.add(map);

		}
		return list;
	}
	
	class order_itemOnClickListener implements  OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			Map<String,Object> map=new HashMap<String,Object>();
			map=list.get(arg2);
			
			orderId=map.get("orderId").toString();
			hotelName=map.get("hotelName").toString();
		    roomStyle=map.get("roomStyle").toString();
		    addressHotel=map.get("addressHotel").toString();
		    beginDate=map.get("beginDate").toString();
		    endDate=map.get("endDate").toString();
		    customerName=map.get("customerName").toString();
		    mobilePhone=map.get("mobilePhone").toString();
		    orderPrice=map.get("orderPrice").toString();
			
			Intent intent=new Intent();
			intent.setClass(User_order.this, User_order_detail.class);
			Bundle bundle=new Bundle();
			bundle.putString("orderId", orderId);
			bundle.putString("hotelName", hotelName);
			bundle.putString("roomStyle", roomStyle);
			bundle.putString("addressHotel", addressHotel);
			bundle.putString("beginDate", beginDate);
			bundle.putString("endDate", endDate);
			bundle.putString("customerName", customerName);
			bundle.putString("mobilePhone", mobilePhone);
			bundle.putString("orderPrice", orderPrice);
			intent.putExtras(bundle);
			startActivity(intent);
		}
		
	}
	
	public boolean onKeyDown(int KeyCode, KeyEvent event){
		if(KeyCode==KeyEvent.KEYCODE_BACK){
//			Intent intent=new Intent();
//			intent.setClass(User_order.this, User_information.class);
//			startActivity(intent);
			User_order.this.finish();
		}
		return false;
	}
}
