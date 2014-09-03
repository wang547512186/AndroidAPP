package com.example.mapforwebservice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;
import org.xmlpull.v1.XmlPullParserException;

public class MapData {
	private static final String serviceNameSpace = "http://tempuri.org/";// 调用方法(获得支持的城市)
	private static final String selectAllAddress = "selectAllAddress";
	private static final String findAddress = "findAddress";
	private static final String selectProvince = "selectProvince";
	private static final String selectCity = "selectCity";
	private static final String findCity = "findCity";
	private static final String selectOhters = "selectOhters";
	private static final String getPointValue = "getpointvalue";
	private static final String userInfor = "userInfor";
	private static final String addOrder = "addOrder";
	private static final String getUserOrder = "getUserOrder";
	private static final String userPwdChange = "userPwdChange";
	private static final String userLogin = "userLogin";
	private static final String getHotelInfo = "getHotelInfo";
	private static final String getHotelInfoById = "getHotelInfoById";
	private static final String getRoomInfo = "getRoomInfo";
	private static final String getShopInfo = "getShopInfo";
	private static final String getShopInfoById = "getShopInfoById";
	private static final String getConsume = "getConsume";
	private static final String addHotelhistory = "addHotelhistory";
	private static final String addHistory = "addHistory";
	private static final String updateUser = "updateUser";
	private static final String userRegister = "userRegister";
	private static final String findMobilePhone = "findMobilePhone";
	private static final String findInvitePhone = "findInvitePhone";
	private static final String addMessage = "addMessage";
	private static final String findMessage = "findMessage";
	private static final String getUserChuzhi = "getUserChuzhi";
	private static final String getUserKezhu = "getUserKezhu";
	private static final String addUserKezhu = "addUserKezhu";
	private static final String findShops = "findShops";
	private static final String updateUserChuzhi = "updateUserChuzhi";
	private static final String updateUserKezhu = "updateUserKezhu";
	private static final String addChuzhihistory = "addChuzhihistory";
	private static final String getChuzhihistory = "getChuzhihistory";
	private static final String getKezhuhistory = "getKezhuhistory";
	private static final String AddFastPayCode = "AddFastPayCode";
	private static final String getHotelMinPrice = "getHotelMinPrice";
	// private static final String
	// WSDL="http://10.0.2.2:55718/Service1.asmx?WSDL";

	private static final String WSDL = "http://114.215.196.123:8080/Service1.asmx?WSDL"; // 阿里云

	// private static final String WSDL
	// ="http://192.168.10.119:180/Service1.asmx?WSDL"; //公司
	// private static final String WSDL
	// ="http://192.168.137.1:8081/Service1.asmx?WSDL";
//	 private static final String WSDL =
//	 "http://192.168.10.37:8081/Service1.asmx?WSDL";   

//	 private static final String WSDL
//	 ="http://192.168.1.143:8081/Service1.asmx?WSDL"; // 个人电脑

	public String updateUser(String typeStr, String valueStr, String usernameStr) {
		String value = "";
		SoapObject soapObject = new SoapObject(serviceNameSpace, updateUser);
		soapObject.addProperty("type", typeStr);
		soapObject.addProperty("value", valueStr);
		soapObject.addProperty("username", usernameStr);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(soapObject);

		AndroidHttpTransport httpTransport = new AndroidHttpTransport(WSDL);
		try {
			httpTransport.call(serviceNameSpace + updateUser, envelope);
			// SoapObject result=(SoapObject)envelope.getResponse();
			SoapObject result = (SoapObject) envelope.bodyIn;
			// citys.add(result.toString());
			value = result.getProperty(0).toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}

	public String updateUserChuzhi(String uid, String hotelid, String money) {
		String value = "";
		SoapObject soapObject = new SoapObject(serviceNameSpace,
				updateUserChuzhi);
		soapObject.addProperty("uid", uid);
		soapObject.addProperty("hotelid", hotelid);
		soapObject.addProperty("money", money);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(soapObject);

		AndroidHttpTransport httpTransport = new AndroidHttpTransport(WSDL);
		try {
			httpTransport.call(serviceNameSpace + updateUserChuzhi, envelope);
			// SoapObject result=(SoapObject)envelope.getResponse();
			SoapObject result = (SoapObject) envelope.bodyIn;
			// citys.add(result.toString());
			value = result.getProperty(0).toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	public String updateUserKezhu(String uid,String money) {
		String value = "";
		SoapObject soapObject = new SoapObject(serviceNameSpace,
				updateUserKezhu);
		soapObject.addProperty("uid", uid);
		soapObject.addProperty("money", money);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(soapObject);

		AndroidHttpTransport httpTransport = new AndroidHttpTransport(WSDL);
		try {
			httpTransport.call(serviceNameSpace + updateUserKezhu, envelope);
			// SoapObject result=(SoapObject)envelope.getResponse();
			SoapObject result = (SoapObject) envelope.bodyIn;
			// citys.add(result.toString());
			value = result.getProperty(0).toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}

	public String userPwdChange(String username, String userpwd) {
		String value = "";
		SoapObject soapObject = new SoapObject(serviceNameSpace, userPwdChange);
		soapObject.addProperty("username", username);
		soapObject.addProperty("userpwd", userpwd);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(soapObject);

		AndroidHttpTransport httpTransport = new AndroidHttpTransport(WSDL);
		try {
			httpTransport.call(serviceNameSpace + userPwdChange, envelope);
			// SoapObject result=(SoapObject)envelope.getResponse();
			SoapObject result = (SoapObject) envelope.bodyIn;
			// citys.add(result.toString());
			value = result.getProperty(0).toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}

	public List<String> findAddress(String hotelid) {
		// TODO Auto-generated method stub
		List<String> value = new ArrayList<String>();
		SoapObject soapObject = new SoapObject(serviceNameSpace, findAddress);
		soapObject.addProperty("hotelid", hotelid);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(soapObject);

		AndroidHttpTransport httpTransport = new AndroidHttpTransport(WSDL);
		try {
			httpTransport.call(serviceNameSpace + findAddress, envelope);
			SoapObject result = (SoapObject) envelope.getResponse();
			int count = result.getPropertyCount();
			for (int index = 0; index < count; index++) {
				value.add(result.getProperty(index).toString());
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}

	public String findCity(String Name) {
		// TODO Auto-generated method stub
		String value = "";
		SoapObject soapObject = new SoapObject(serviceNameSpace, findCity);
		soapObject.addProperty("province", Name);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(soapObject);

		AndroidHttpTransport httpTransport = new AndroidHttpTransport(WSDL);
		try {
			httpTransport.call(serviceNameSpace + findCity, envelope);
			SoapObject result = (SoapObject) envelope.bodyIn;
			value = result.getProperty(0).toString();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value.toString();
	}

	public List<String> selectProvince() {
		// TODO Auto-generated method stub
		List<String> value = new ArrayList<String>();
		SoapObject soapObject = new SoapObject(serviceNameSpace, selectProvince);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(soapObject);

		AndroidHttpTransport httpTransport = new AndroidHttpTransport(WSDL);
		try {
			httpTransport.call(serviceNameSpace + selectProvince, envelope);
			SoapObject result = (SoapObject) envelope.getResponse();
			int count = result.getPropertyCount();
			for (int index = 0; index < count; index++) {
				value.add(result.getProperty(index).toString());
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}

	public List<String> selectCity(String Province) {
		// TODO Auto-generated method stub
		List<String> value = new ArrayList<String>();
		SoapObject soapObject = new SoapObject(serviceNameSpace, selectCity);
		soapObject.addProperty("province", Province);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(soapObject);

		AndroidHttpTransport httpTransport = new AndroidHttpTransport(WSDL);
		try {
			httpTransport.call(serviceNameSpace + selectCity, envelope);
			SoapObject result = (SoapObject) envelope.getResponse();
			int count = result.getPropertyCount();
			for (int index = 0; index < count; index++) {
				value.add(result.getProperty(index).toString());
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}

	public List<String> findInvitePhone(String username) {
		// TODO Auto-generated method stub
		List<String> value = new ArrayList<String>();
		SoapObject soapObject = new SoapObject(serviceNameSpace,
				findInvitePhone);
		soapObject.addProperty("username", username);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(soapObject);

		AndroidHttpTransport httpTransport = new AndroidHttpTransport(WSDL);
		try {
			httpTransport.call(serviceNameSpace + findInvitePhone, envelope);
			SoapObject result = (SoapObject) envelope.getResponse();
			int count = result.getPropertyCount();
			for (int index = 0; index < count; index++) {
				value.add(result.getProperty(index).toString());
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}

	public List<String> selectall(String city) {
		// TODO Auto-generated method stub
		List<String> value = new ArrayList<String>();
		SoapObject soapObject = new SoapObject(serviceNameSpace,
				selectAllAddress);
		soapObject.addProperty("city", city);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(soapObject);

		AndroidHttpTransport httpTransport = new AndroidHttpTransport(WSDL);
		try {
			httpTransport.call(serviceNameSpace + selectAllAddress, envelope);
			SoapObject result = (SoapObject) envelope.getResponse();
			int count = result.getPropertyCount();
			for (int index = 0; index < count; index++) {
				value.add(result.getProperty(index).toString());
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}

	public List<String> selectOhters(String type, String city) {
		// TODO Auto-generated method stub
		List<String> value = new ArrayList<String>();
		SoapObject soapObject = new SoapObject(serviceNameSpace, selectOhters);
		soapObject.addProperty("type", type);
		soapObject.addProperty("city", city);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(soapObject);

		AndroidHttpTransport httpTransport = new AndroidHttpTransport(WSDL);
		try {
			httpTransport.call(serviceNameSpace + selectOhters, envelope);
			SoapObject result = (SoapObject) envelope.getResponse();
			int count = result.getPropertyCount();
			for (int index = 0; index < count; index++) {
				value.add(result.getProperty(index).toString());
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}

	public List<String> getPointValue(String type) {
		// TODO Auto-generated method stub
		List<String> value = new ArrayList<String>();
		SoapObject soapObject = new SoapObject(serviceNameSpace, getPointValue);
		soapObject.addProperty("cityname", type);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(soapObject);

		AndroidHttpTransport httpTransport = new AndroidHttpTransport(WSDL);
		try {
			httpTransport.call(serviceNameSpace + getPointValue, envelope);
			SoapObject result = (SoapObject) envelope.getResponse();
			int count = result.getPropertyCount();
			for (int index = 0; index < count; index++) {
				value.add(result.getProperty(index).toString());
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}

	public List<String> userInfor(String username) {
		// TODO Auto-generated method stub
		List<String> value = new ArrayList<String>();
		SoapObject soapObject = new SoapObject(serviceNameSpace, userInfor);
		soapObject.addProperty("username", username);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(soapObject);

		AndroidHttpTransport httpTransport = new AndroidHttpTransport(WSDL);
		try {
			httpTransport.call(serviceNameSpace + userInfor, envelope);
			SoapObject result = (SoapObject) envelope.getResponse();
			int count = result.getPropertyCount();
			for (int index = 0; index < count; index++) {
				value.add(result.getProperty(index).toString());
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}

	public String userRegister(String username, String nickename,
			String password, String email, String sexy) {
		String value = "";
		SoapObject soapObject = new SoapObject(serviceNameSpace, userRegister);
		soapObject.addProperty("username", username);
		soapObject.addProperty("nickename", nickename);
		soapObject.addProperty("password", password);
		soapObject.addProperty("email", email);
		soapObject.addProperty("sexy", sexy);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(soapObject);

		AndroidHttpTransport httpTransport = new AndroidHttpTransport(WSDL);
		try {
			httpTransport.call(serviceNameSpace + userRegister, envelope);
			SoapObject result = (SoapObject) envelope.bodyIn;
			value = result.getProperty(0).toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value.toString();
	}

	public String addMessage(String username, String title, String message) {
		String value = "";
		SoapObject soapObject = new SoapObject(serviceNameSpace, addMessage);
		soapObject.addProperty("username", username);
		soapObject.addProperty("title", title);
		soapObject.addProperty("message", message);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(soapObject);

		AndroidHttpTransport httpTransport = new AndroidHttpTransport(WSDL);
		try {
			httpTransport.call(serviceNameSpace + addMessage, envelope);
			SoapObject result = (SoapObject) envelope.bodyIn;
			value = result.getProperty(0).toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value.toString();
	}

	public String addHotelhistory(String customeruserid, String customername,
			String address, String hotelname, String money, String moneykezhu,
			String returnkezhu, String chuzhi) {
		String value = "";
		SoapObject soapObject = new SoapObject(serviceNameSpace,
				addHotelhistory);
		soapObject.addProperty("customeruserid", customeruserid);
		soapObject.addProperty("customername", customername);
		soapObject.addProperty("address", address);
		soapObject.addProperty("hotelname", hotelname);
		soapObject.addProperty("money", money);
		soapObject.addProperty("moneykezhu", moneykezhu);
		soapObject.addProperty("returnkezhu", returnkezhu);
		soapObject.addProperty("chuzhi", chuzhi);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(soapObject);

		AndroidHttpTransport httpTransport = new AndroidHttpTransport(WSDL);
		try {
			httpTransport.call(serviceNameSpace + addHotelhistory, envelope);
			SoapObject result = (SoapObject) envelope.bodyIn;
			value = result.getProperty(0).toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value.toString();
	}

	public String addHistory(String customeruserid, String customername,
			String hotelid, String hotelname, String money,
			String scorecustomer, String storedmoneycustomer) {
		String value = "";
		SoapObject soapObject = new SoapObject(serviceNameSpace, addHistory);
		soapObject.addProperty("customeruserid", customeruserid);
		soapObject.addProperty("customername", customername);
		soapObject.addProperty("hotelid", hotelid);
		soapObject.addProperty("hotelname", hotelname);
		soapObject.addProperty("money", money);
		soapObject.addProperty("scorecustomer", scorecustomer);
		soapObject.addProperty("storedmoneycustomer", storedmoneycustomer);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(soapObject);

		AndroidHttpTransport httpTransport = new AndroidHttpTransport(WSDL);
		try {
			httpTransport.call(serviceNameSpace + addHistory, envelope);
			SoapObject result = (SoapObject) envelope.bodyIn;
			value = result.getProperty(0).toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value.toString();
	}

	public String findMobilePhone(String phone) {
		String value = "";
		SoapObject soapObject = new SoapObject(serviceNameSpace,
				findMobilePhone);
		soapObject.addProperty("phone", phone);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(soapObject);

		AndroidHttpTransport httpTransport = new AndroidHttpTransport(WSDL);
		try {
			httpTransport.call(serviceNameSpace + findMobilePhone, envelope);
			SoapObject result = (SoapObject) envelope.bodyIn;
			value = result.getProperty(0).toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value.toString();
	}

	public List<String> findMessage(String username) {
		// TODO Auto-generated method stub
		List<String> value = new ArrayList<String>();
		SoapObject soapObject = new SoapObject(serviceNameSpace, findMessage);
		soapObject.addProperty("username", username);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(soapObject);

		AndroidHttpTransport httpTransport = new AndroidHttpTransport(WSDL);
		try {
			httpTransport.call(serviceNameSpace + findMessage, envelope);
			SoapObject result = (SoapObject) envelope.getResponse();
			int count = result.getPropertyCount();
			for (int index = 0; index < count; index++) {
				value.add(result.getProperty(index).toString());
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}

	public String addOrder(String username, String nickename,
			String stayperson, String roomtype, String price, String telephone,
			String hotelname, String hoteladdress, String begindate,
			String enddate) {
		String value = "";
		SoapObject soapObject = new SoapObject(serviceNameSpace, addOrder);
		soapObject.addProperty("username", username);
		soapObject.addProperty("nickename", nickename);
		soapObject.addProperty("stayperson", stayperson);
		soapObject.addProperty("roomtype", roomtype);
		soapObject.addProperty("price", price);
		soapObject.addProperty("telephone", telephone);
		soapObject.addProperty("hotelname", hotelname);
		soapObject.addProperty("hoteladdress", hoteladdress);
		soapObject.addProperty("begindate", begindate);
		soapObject.addProperty("enddate", enddate);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(soapObject);

		AndroidHttpTransport httpTransport = new AndroidHttpTransport(WSDL);
		try {
			httpTransport.call(serviceNameSpace + addOrder, envelope);
			SoapObject result = (SoapObject) envelope.bodyIn;
			value = result.getProperty(0).toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value.toString();
	}

	public String addChuzhihistory(String username, String hotelid,
			String number) {
		String value = "";
		SoapObject soapObject = new SoapObject(serviceNameSpace,
				addChuzhihistory);
		soapObject.addProperty("username", username);
		soapObject.addProperty("hotelid", hotelid);
		soapObject.addProperty("number", number);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(soapObject);

		AndroidHttpTransport httpTransport = new AndroidHttpTransport(WSDL);
		try {
			httpTransport.call(serviceNameSpace + addChuzhihistory, envelope);
			SoapObject result = (SoapObject) envelope.bodyIn;
			value = result.getProperty(0).toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value.toString();
	}

	public List<String> getShopInfo() {
		// TODO Auto-generated method stub
		List<String> value = new ArrayList<String>();
		SoapObject soapObject = new SoapObject(serviceNameSpace, getShopInfo);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(soapObject);

		AndroidHttpTransport httpTransport = new AndroidHttpTransport(WSDL);
		try {
			httpTransport.call(serviceNameSpace + getShopInfo, envelope);
			SoapObject result = (SoapObject) envelope.getResponse();
			int count = result.getPropertyCount();
			for (int index = 0; index < count; index++) {
				value.add(result.getProperty(index).toString());
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}

	public List<String> getShopInfoById(String hotelid) {
		// TODO Auto-generated method stub
		List<String> value = new ArrayList<String>();
		SoapObject soapObject = new SoapObject(serviceNameSpace,
				getShopInfoById);
		soapObject.addProperty("hotelid", hotelid);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(soapObject);

		AndroidHttpTransport httpTransport = new AndroidHttpTransport(WSDL);
		try {
			httpTransport.call(serviceNameSpace + getShopInfoById, envelope);
			SoapObject result = (SoapObject) envelope.getResponse();
			int count = result.getPropertyCount();
			for (int index = 0; index < count; index++) {
				value.add(result.getProperty(index).toString());
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}

	public List<String> getHotelInfo() {
		// TODO Auto-generated method stub
		List<String> value = new ArrayList<String>();
		SoapObject soapObject = new SoapObject(serviceNameSpace, getHotelInfo);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(soapObject);

		AndroidHttpTransport httpTransport = new AndroidHttpTransport(WSDL);
		try {
			httpTransport.call(serviceNameSpace + getHotelInfo, envelope);
			SoapObject result = (SoapObject) envelope.getResponse();
			int count = result.getPropertyCount();
			for (int index = 0; index < count; index++) {
				value.add(result.getProperty(index).toString());
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}

	public List<String> getUserOrder(String username) {
		// TODO Auto-generated method stub
		List<String> value = new ArrayList<String>();
		SoapObject soapObject = new SoapObject(serviceNameSpace, getUserOrder);
		soapObject.addProperty("username", username);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(soapObject);

		AndroidHttpTransport httpTransport = new AndroidHttpTransport(WSDL);
		try {
			httpTransport.call(serviceNameSpace + getUserOrder, envelope);
			SoapObject result = (SoapObject) envelope.getResponse();
			int count = result.getPropertyCount();
			for (int index = 0; index < count; index++) {
				value.add(result.getProperty(index).toString());
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}

	public List<String> getHotelInfoById(String hotelid) {
		// TODO Auto-generated method stub
		List<String> value = new ArrayList<String>();
		SoapObject soapObject = new SoapObject(serviceNameSpace,
				getHotelInfoById);
		soapObject.addProperty("hotelid", hotelid);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(soapObject);

		AndroidHttpTransport httpTransport = new AndroidHttpTransport(WSDL);
		try {
			httpTransport.call(serviceNameSpace + getHotelInfoById, envelope);
			SoapObject result = (SoapObject) envelope.getResponse();
			int count = result.getPropertyCount();
			for (int index = 0; index < count; index++) {
				value.add(result.getProperty(index).toString());
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}

	public List<String> getRoomInfo(String hotelid) {
		// TODO Auto-generated method stub
		List<String> value = new ArrayList<String>();
		SoapObject soapObject = new SoapObject(serviceNameSpace, getRoomInfo);
		soapObject.addProperty("hotelid", hotelid);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(soapObject);

		AndroidHttpTransport httpTransport = new AndroidHttpTransport(WSDL);
		try {
			httpTransport.call(serviceNameSpace + getRoomInfo, envelope);
			SoapObject result = (SoapObject) envelope.getResponse();
			int count = result.getPropertyCount();
			for (int index = 0; index < count; index++) {
				value.add(result.getProperty(index).toString());
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}

	public List<String> getUserChuzhi(String uid) {
		// TODO Auto-generated method stub
		List<String> value = new ArrayList<String>();
		SoapObject soapObject = new SoapObject(serviceNameSpace, getUserChuzhi);
		soapObject.addProperty("uid", uid);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(soapObject);

		AndroidHttpTransport httpTransport = new AndroidHttpTransport(WSDL);
		try {
			httpTransport.call(serviceNameSpace + getUserChuzhi, envelope);
			SoapObject result = (SoapObject) envelope.getResponse();
			int count = result.getPropertyCount();
			for (int index = 0; index < count; index++) {
				value.add(result.getProperty(index).toString());
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}

	public List<String> getUserKezhu(String uid) {
		// TODO Auto-generated method stub
		List<String> value = new ArrayList<String>();
		SoapObject soapObject = new SoapObject(serviceNameSpace, getUserKezhu);
		soapObject.addProperty("uid", uid);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(soapObject);

		AndroidHttpTransport httpTransport = new AndroidHttpTransport(WSDL);
		try {
			httpTransport.call(serviceNameSpace + getUserKezhu, envelope);
			SoapObject result = (SoapObject) envelope.getResponse();
			int count = result.getPropertyCount();
			for (int index = 0; index < count; index++) {
				value.add(result.getProperty(index).toString());
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}

	public List<String> getChuzhihistory(String uid, String hotelid) {
		// TODO Auto-generated method stub
		List<String> value = new ArrayList<String>();
		SoapObject soapObject = new SoapObject(serviceNameSpace,
				getChuzhihistory);
		soapObject.addProperty("uid", uid);
		soapObject.addProperty("hotelid", hotelid);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(soapObject);

		AndroidHttpTransport httpTransport = new AndroidHttpTransport(WSDL);
		try {
			httpTransport.call(serviceNameSpace + getChuzhihistory, envelope);
			SoapObject result = (SoapObject) envelope.getResponse();
			int count = result.getPropertyCount();
			for (int index = 0; index < count; index++) {
				value.add(result.getProperty(index).toString());
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}

	public List<String> getKezhuhistory(String uid) {
		// TODO Auto-generated method stub
		List<String> value = new ArrayList<String>();
		SoapObject soapObject = new SoapObject(serviceNameSpace,
				getKezhuhistory);
		soapObject.addProperty("uid", uid);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(soapObject);

		AndroidHttpTransport httpTransport = new AndroidHttpTransport(WSDL);
		try {
			httpTransport.call(serviceNameSpace + getKezhuhistory, envelope);
			SoapObject result = (SoapObject) envelope.getResponse();
			int count = result.getPropertyCount();
			for (int index = 0; index < count; index++) {
				value.add(result.getProperty(index).toString());
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}

	public List<String> getConsume(String customeruserid) {
		// TODO Auto-generated method stub
		List<String> value = new ArrayList<String>();
		SoapObject soapObject = new SoapObject(serviceNameSpace, getConsume);
		soapObject.addProperty("customeruserid", customeruserid);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(soapObject);

		AndroidHttpTransport httpTransport = new AndroidHttpTransport(WSDL);
		try {
			httpTransport.call(serviceNameSpace + getConsume, envelope);
			SoapObject result = (SoapObject) envelope.getResponse();
			int count = result.getPropertyCount();
			for (int index = 0; index < count; index++) {
				value.add(result.getProperty(index).toString());
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}

	public String userLogin(String username, String userpwd) {
		String value = "";
		SoapObject soapObject = new SoapObject(serviceNameSpace, userLogin);
		soapObject.addProperty("username", username);
		soapObject.addProperty("userpwd", userpwd);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(soapObject);

		AndroidHttpTransport httpTransport = new AndroidHttpTransport(WSDL);
		try {
			httpTransport.call(serviceNameSpace + userLogin, envelope);
			SoapObject result = (SoapObject) envelope.bodyIn;
			value = result.getProperty(0).toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value.toString();
	}

	public List<String> findShops(String city, String shopname) {
		// TODO Auto-generated method stub
		List<String> value = new ArrayList<String>();
		SoapObject soapObject = new SoapObject(serviceNameSpace, findShops);
		soapObject.addProperty("city", city);
		soapObject.addProperty("shopname", shopname);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(soapObject);

		AndroidHttpTransport httpTransport = new AndroidHttpTransport(WSDL);
		try {
			httpTransport.call(serviceNameSpace + findShops, envelope);
			SoapObject result = (SoapObject) envelope.getResponse();
			int count = result.getPropertyCount();
			for (int index = 0; index < count; index++) {
				value.add(result.getProperty(index).toString());
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}

	public String AddFastPayCode(String username, String code) {
		String value = "";
		SoapObject soapObject = new SoapObject(serviceNameSpace, AddFastPayCode);
		soapObject.addProperty("username", username);
		soapObject.addProperty("code", code);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(soapObject);

		AndroidHttpTransport httpTransport = new AndroidHttpTransport(WSDL);
		try {
			httpTransport.call(serviceNameSpace + AddFastPayCode, envelope);
			SoapObject result = (SoapObject) envelope.bodyIn;
			value = result.getProperty(0).toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value.toString();
	}

	public String addUserKezhu(String uid) {
		String value = "";
		SoapObject soapObject = new SoapObject(serviceNameSpace, addUserKezhu);
		soapObject.addProperty("uid", uid);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(soapObject);

		AndroidHttpTransport httpTransport = new AndroidHttpTransport(WSDL);
		try {
			httpTransport.call(serviceNameSpace + addUserKezhu, envelope);
			SoapObject result = (SoapObject) envelope.bodyIn;
			value = result.getProperty(0).toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value.toString();
	}

	public List<String> getHotelMinPrice(String hotelid) {
		// TODO Auto-generated method stub
		List<String> value = new ArrayList<String>();
		SoapObject soapObject = new SoapObject(serviceNameSpace,
				getHotelMinPrice);
		soapObject.addProperty("hotelid", hotelid);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(soapObject);

		AndroidHttpTransport httpTransport = new AndroidHttpTransport(WSDL);
		try {
			httpTransport.call(serviceNameSpace + getHotelMinPrice, envelope);
			SoapObject result = (SoapObject) envelope.getResponse();
			int count = result.getPropertyCount();
			for (int index = 0; index < count; index++) {
				value.add(result.getProperty(index).toString());
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}

}
