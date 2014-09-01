package com.example.mapforwebservice;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutQuestion extends Activity {
	private List<String> GroupData;// 定义组数据
	private List<List<String>> ChildrenData;// 定义组中的子数据

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_question);
		LoadListDate();
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

		ExpandableListView expandableListView1 = (ExpandableListView) findViewById(R.id.expandableListView1);
		expandableListView1.setAdapter(new ExpandableAdapter());
	}

	private class ExpandableAdapter extends BaseExpandableListAdapter {

		@Override
		public int getGroupCount() {
			// TODO Auto-generated method stub
			return GroupData.size();
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			// TODO Auto-generated method stub
			return ChildrenData.get(groupPosition).size();
		}

		@Override
		public Object getGroup(int groupPosition) {
			// TODO Auto-generated method stub
			return GroupData.get(groupPosition);
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return ChildrenData.get(groupPosition).get(childPosition);

		}

		@Override
		public long getGroupId(int groupPosition) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			TextView myText = null;
			if (convertView != null) {
				myText = (TextView) convertView;
				myText.setText(GroupData.get(groupPosition));
			} else {
				myText = createView(GroupData.get(groupPosition));
			}
			myText.setTextSize(17);
			myText.setTextColor(Color.rgb(70, 70, 70));
			return myText;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			TextView myText = null;
			if (convertView != null) {
				myText = (TextView) convertView;
				myText.setText(ChildrenData.get(groupPosition).get(
						childPosition));
			} else {
				myText = createView(ChildrenData.get(groupPosition).get(
						childPosition));
			}
			myText.setTextSize(16);
			myText.setBackgroundResource(R.color.bgcolor);
			return myText;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return false;
		}

		private TextView createView(String content) {
			AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(
					ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			TextView myText = new TextView(AboutQuestion.this);
			myText.setLayoutParams(layoutParams);
			myText.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
			myText.setPadding(30, 30, 30, 30);
			myText.setText(content);
			return myText;
		}
	}

	private void LoadListDate() {
		GroupData = new ArrayList<String>();
		GroupData.add("Q : 积分（客主币）或储值仅由给予积分（客主币）或储值的商家负责？");
		GroupData.add("Q : 积分和储值的差异是什么？");
		GroupData.add("Q : 如何支付？");
		GroupData.add("Q : 如何获得客主币？");
		GroupData.add("Q : 为何要退出登录？");

		ChildrenData = new ArrayList<List<String>>();
		List<String> Child1 = new ArrayList<String>();
		Child1.add("    给予会员积分（客主币）或者储值的商家脱盟，将导致这一商家给予会员的积分（客主币）不再能在其他商家使用。"
				+ '\n' + "    这一积分（客主币）将转化为该商家的本地储值。" + '\n'
				+ "    会员帐户里其他的积分（客主币）或者储值不受影响。");
		ChildrenData.add(Child1);

		List<String> Child2 = new ArrayList<String>();
		Child2.add("    客主系统内的积分设定为可以通积通兑，并且是货币化抵现使用。所以我们称之客主币。"
				+ '\n'
				+ "    储值是局域的，只能在商家设定的同系统单位使用。同系统单位往往是一个投资主体，会员一般在做储值的时候，就能获知这一储值的使用范围。"
				+ '\n' + "    会员在两个互不相关的商家分别储值的话，这两个储值是各自独立互不干扰的。" + '\n'
				+ "    会员在同系统商家的两个商家分别储值的话，这两个储值可以叠加使用。");
		ChildrenData.add(Child2);

		List<String> Child3 = new ArrayList<String>();
		Child3.add("    会员可以在“个人中心”选择商家进行客主币或储值支付，这时需要输入支付金额。"
				+ '\n'
				+ "    也可以在“快捷支付”生成动态匹配码，将手机号和动态匹配码提交给商家，告知商家使用客主币或储值，由商家输入消费金额来完成支付。");
		ChildrenData.add(Child3);

		List<String> Child4 = new ArrayList<String>();
		Child4.add("    按照联盟商家独立的会员计划，会员现金消费可按积分比例获得客主币。" + '\n'
				+ "    使用客主币视同使用现金，一样获得客主币。" + '\n' + "    使用储值额度不产生客主币。");
		ChildrenData.add(Child4);

		List<String> Child5 = new ArrayList<String>();
		Child5.add("    在登录状态下才能主动支付或生成动态匹配码。" + '\n' + "    退出登录可以增加安全性。"
				+ '\n' + "    可以在个人中心退出登录。");
		ChildrenData.add(Child5);

	}
}
