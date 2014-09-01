package com.example.mapforwebservice;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;

public class TabActivity extends FragmentActivity {

	private FragmentTabHost mTabHost;
	private LayoutInflater layoutInflater;
	private Class fragmentArray[] = { NearbyActivity.class, NearbyActivity.class,
			NearbyActivity.class,NearbyActivity.class };

	private int mImageViewArray[] = { R.drawable.button,
			R.drawable.near, R.drawable.icon_chuzhi_white,
			R.drawable.user };

	// Tab选项卡的文字
	private String mTextviewArray[] = { "首页", "附近", "支付", "个人中心" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab);
		initView();
	}
	
	
	
	private void initView(){  
		layoutInflater = LayoutInflater.from(this);  
		mTabHost=(FragmentTabHost)findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);   
		int count = fragmentArray.length;
		for(int i=0;i<count;i++){
			TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i]).setIndicator(getTabItemView(i));  
            //将Tab按钮添加进Tab选项卡中  
            mTabHost.addTab(tabSpec, fragmentArray[i], null);  
            //设置Tab按钮的背景  
            mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.selector_tab_background);  
		}
	}
	
	private View getTabItemView(int index){  
        View view = layoutInflater.inflate(R.layout.tab_item_view, null);  
        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);  
        imageView.setImageResource(mImageViewArray[index]);  
          
        TextView textView = (TextView) view.findViewById(R.id.textview);          
        textView.setText(mTextviewArray[index]);  
      
        return view;  
    }  
	
}
