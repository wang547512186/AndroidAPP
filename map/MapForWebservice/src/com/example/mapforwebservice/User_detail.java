package com.example.mapforwebservice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

public class User_detail extends Activity{
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_detail);
    }
    
    public boolean onKeyDown(int KeyCode,KeyEvent event){
    	if(KeyCode == KeyEvent.KEYCODE_BACK)  
        {    
            Intent intent=new Intent();
            intent.setClass(User_detail.this, User_information.class);
            startActivity(intent);
            User_detail.this.finish();
        }  
     return false;  
    }
}
