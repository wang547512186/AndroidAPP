package com.example.mapforwebservice;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class User_password extends Activity {
	private EditText oldpwds;
	private List<String> detail;
	private EditText newpwds;
	private EditText twonewpwds;
	private Button pwdtrue;
	private Button pwdfalse;
	private MapData mapdata;
	private String userPhone;
//	private String UserPwd;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_password);
		Intent intent = getIntent();
		userPhone = intent.getStringExtra("userPhone");
		mapdata = new MapData();
		detail = mapdata.userInfor(userPhone);
//		UserPwd = detail.get(2).toString();

		oldpwds = (EditText) findViewById(R.id.oldpwds);
		newpwds = (EditText) findViewById(R.id.newpwds);
		twonewpwds = (EditText) findViewById(R.id.twonewpwds);
		pwdtrue = (Button) findViewById(R.id.pwdtrue);
		pwdfalse = (Button) findViewById(R.id.pwdfalse);

		pwdtrue.setOnClickListener(new pwdtrueOnClickListener());

		pwdfalse.setOnClickListener(new pwdfalseOnClickListener());

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

	class pwdtrueOnClickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if (oldpwds.getText().toString().equals("")
					|| newpwds.getText().toString().equals("")
					|| twonewpwds.getText().toString().equals("")) {
				new AlertDialog.Builder(User_password.this).setTitle("失败")
						.setMessage("信息未填写完全").setPositiveButton("确定", null)
						.show();
				return;
			}

			if (!(newpwds.getText().toString().equals(twonewpwds.getText()
					.toString()))) {
				new AlertDialog.Builder(User_password.this).setTitle("失败")
						.setMessage("两次密码输入不一致").setPositiveButton("确定", null)
						.show();
			} else {
				if (!mapdata.userLogin(userPhone, oldpwds.getText().toString()).equals("true")) {
					new AlertDialog.Builder(User_password.this).setTitle("失败")
							.setMessage("原密码输入错误")
							.setPositiveButton("确定", null).show();
				} else {
					mapdata.userPwdChange(userPhone, newpwds.getText()
							.toString());
					new AlertDialog.Builder(User_password.this)
							.setTitle("成功")
							.setMessage("修改密码成功")
							.setPositiveButton("确定",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int whichButton) {
											finish();
										}
									}).show();

				}

			}
		}

	}

	class pwdfalseOnClickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			// Intent intent=new Intent();
			// intent.setClass(User_password.this, User_information.class);
			// startActivity(intent);
			User_password.this.finish();
		}

	}

	public boolean onKeyDown(int KeyCode, KeyEvent event) {
		if (KeyCode == KeyEvent.KEYCODE_BACK) {
			// Intent intent=new Intent();
			// intent.setClass(User_password.this, User_information.class);
			// startActivity(intent);
			User_password.this.finish();
		}
		return false;
	}
}
