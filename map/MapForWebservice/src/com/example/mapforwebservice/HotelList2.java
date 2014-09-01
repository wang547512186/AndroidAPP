package com.example.mapforwebservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class HotelList2 extends ListFragment {
	    private ListView listview;
	    private List<Map<String,Object>> list;
        public void onCreate(Bundle savedInstanceState){
        	super.onCreate(savedInstanceState);      	
        }

		private List<Map<String,Object>> getData() {
			// TODO Auto-generated method stub
			list=new ArrayList<Map<String,Object>>();
			Map<String,Object> map=new HashMap<String,Object>();
			map = new HashMap<String, Object>();
			map.put("title", "咖啡厅");
			map.put("address", "AAAA路");
			map.put("img", R.drawable.hotel3);
			list.add(map);
			
			map = new HashMap<String, Object>();
			map.put("title", "文印店");
			map.put("address", "AAAA路");
			map.put("img", R.drawable.hotel3);
			list.add(map);
			
			map = new HashMap<String, Object>();
			map.put("title", "超市");
			map.put("address", "AAAA路");
			map.put("img", R.drawable.hotel2);
			list.add(map);
			
			map = new HashMap<String, Object>();
			map.put("title", "餐饮店");
			map.put("address", "AAAA路");
			map.put("img", R.drawable.hotel3);
			list.add(map);
			
			map = new HashMap<String, Object>();
			map.put("title", "汽车快速服务店");
			map.put("address", "AAAA路");
			map.put("img", R.drawable.hotel1);
			list.add(map);
			return list;
			
		}
		
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {  
	        // TODO Auto-generated method stub  
	        View view = inflater.inflate(R.layout.hotellist, null); 
	        listview=(ListView)view.findViewById(android.R.id.list);
	        SimpleAdapter adapter = new SimpleAdapter(this.getActivity().getApplicationContext(),getData(),R.layout.hotellist_item,
    				new String[]{"title","address","img"},
    				new int[]{R.id.title,R.id.address,R.id.img});
	        listview.setAdapter(adapter);
	        return view;  
	    }  
	  
	    public void onListItemClick(ListView l, View v, int position, long id) {
	    	Map<String,Object> clicked=new HashMap<String,Object>();
	    	clicked=list.get(position);
	    	String name=clicked.get("title").toString();
	    	String address=clicked.get("address").toString();
	    	Intent intent=new Intent();
	    	intent.setClass(getActivity(), detail_information.class);
	    	Bundle bundle=new Bundle();
        	bundle.putString("name", name);
        	bundle.putString("address", address);
        	intent.putExtras(bundle);
        	startActivity(intent);
	    }
	    
	    
	    @Override  
	    public void onPause() {  
	        // TODO Auto-generated method stub  
	        super.onPause();  
	    }
}
