package com.example.mapforwebservice;

import android.app.Application;

public class PointValue extends Application{
	private int PointXValue=(int)(31.220* 1E6);
	private int PointYValue=(int)(121.527* 1E6);
	private int FinishValue=1;
	private String UserName="";
	
	public void setPointXValue(int Value)
	{
		this.PointXValue=Value;
	}
	
	public void setPointYValue(int Value)
	{
		this.PointYValue=Value;
	}
	
	public void setFinishValue(int Value){
		this.FinishValue=Value;
	}
	
	public void setUserName(String name){
		this.UserName=name;
	}
	
	public int getPointXValue(){
		return PointXValue;
	}
	
	public int getPointYValue(){
		return PointYValue;
	}
	
	public int getFinishValue(){
		return FinishValue;
	}
	
	public String getUserName(){
		return UserName;
	}
}
