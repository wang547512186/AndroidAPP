package com.example.mapforwebservice;
import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.CalendarView.OnDateChangeListener;

public class DateSelectActivity extends Activity {
	final Calendar c = Calendar.getInstance(); 

	private int year_data= c.get(Calendar.YEAR);
	private int month_data=c.get(Calendar.MONTH)+1;
	private int day_data=c.get(Calendar.DAY_OF_MONTH);
	private String dateString="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_date_select);
		
		CalendarView calendarView1 = (CalendarView) findViewById(R.id.calendarView1);
		
		calendarView1.setOnDateChangeListener(new OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
            	year_data=year;
            	month_data=month+1;
            	day_data=dayOfMonth;
            	dateString = year_data + "年" + month_data + "月" + day_data +"日";
                Toast.makeText(DateSelectActivity.this, dateString, Toast.LENGTH_LONG).show();
            }
        });
		
		
		//取消
		ImageView back=(ImageView)findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				DateSelectActivity.this.setResult(RESULT_CANCELED);
				DateSelectActivity.this.finish();
			}
			
		});
		
		//确定
		ImageView date_ok=(ImageView)findViewById(R.id.date_ok);
		date_ok.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(DateSelectActivity.this,detail_information.class);
				
				intent.putExtra("date", dateString);
				intent.putExtra("year_data", year_data);
				intent.putExtra("month_data", month_data);
				intent.putExtra("day_data", day_data);
				DateSelectActivity.this.setResult(RESULT_OK, intent);
				DateSelectActivity.this.finish();
			}
			
		});
	}  ///onCreat
	
	
	@Override 
	public boolean onKeyDown(int keyCode, KeyEvent event) { 
	    // 是否触发按键为back键    
	    if (keyCode == KeyEvent.KEYCODE_BACK) { 
	        setResult(RESULT_CANCELED); 
	        this.finish(); 
	        return true; 
	    }else { 
	        return super.onKeyDown(keyCode, event); 
	    } 
	}
}
