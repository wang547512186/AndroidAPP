package com.example.mapforwebservice;


import cn.jpush.android.api.JPushInterface;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class Welcome extends Activity {
	private SharedPreferences sp;
	private Boolean isEnter = false;
	private ImageView welcomeImage;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		sp = getSharedPreferences("setting_state", Context.MODE_PRIVATE);
		isEnter = sp.getBoolean("isEnter", false);
		
		View view = View.inflate(Welcome.this, R.layout.welcome, null);
		setContentView(view);
		Animation animation = new AlphaAnimation(0.1f, 1.0f);
		animation.setDuration(2000);
		view.setAnimation(animation);

		// welcomeImage=(ImageView)findViewById(R.id.welcome_imageView);
		// TransitionDrawable
		// drawable=(TransitionDrawable)welcomeImage.getDrawable();
		// drawable.startTransition(1000);
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub

						Intent intent=new Intent();
						if(isEnter){
							intent.setClass(Welcome.this,MainActivity.class);
						}else{
							intent.setClass(Welcome.this,FirstEnterWelcome.class);
						}
						startActivity(intent);
						finish();
					}

				}, 100);
			}
		});

	}
	
	
	@Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }
}
