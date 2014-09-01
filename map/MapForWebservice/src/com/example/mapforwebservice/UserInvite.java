package com.example.mapforwebservice;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class UserInvite extends Activity {
	private LinearLayout shortmsgLayout;
	private LinearLayout otherLayout;
	private String Str="客主会员卡联盟 - 会员卡的银联  www.kezhu.com";
	private TextView inviteCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_invite);

		//
		shortmsgLayout = (LinearLayout) findViewById(R.id.shortmsgLayout);
		User_information.changeBackground(shortmsgLayout);
		shortmsgLayout.setOnClickListener(new OnClickListener() {
			// 短信发送
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Uri smsToUri = Uri.parse("smsto:");
				Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
				intent.putExtra("sms_body", Str);
				startActivity(intent);
			}
		});
		
		otherLayout = (LinearLayout) findViewById(R.id.otherLayout);
		User_information.changeBackground(otherLayout);
		otherLayout.setOnClickListener(new OnClickListener() {
			// 其他方式分享
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showShare();
			}
		});

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
	private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // 分享时Notification的图标和文字
        oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.share));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://www.kezhu.com");
        // text是分享文本，所有平台都需要这个字段
        oks.setText(Str);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("/sdcard/test.jpg");
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://www.kezhu.com");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://www.kezhu.com");

        // 启动分享GUI
        oks.show(this);
   }
}
