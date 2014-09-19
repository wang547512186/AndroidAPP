using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;

namespace WebService1
{
    /// <summary>
    /// Service1 的摘要说明
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // 若要允许使用 ASP.NET AJAX 从脚本中调用此 Web 服务，请取消对下行的注释。
    // [System.Web.Script.Services.ScriptService]
    public class Service1 : System.Web.Services.WebService
    {
        DBOperation dbOperation = new DBOperation();

        [WebMethod]
        public string HelloWorld()
        {
            return "Hello World";
        }

        [WebMethod(Description = "根据城市查询地址信息")]
        public string[] selectAddressByCity(string city)
        {
            return dbOperation.selectAddressByCity(city).ToArray();
        }

        [WebMethod(Description = "查询地址信息")]
        public string[] findAddress(string hotelid)
        {
            return dbOperation.findAddress(hotelid).ToArray();
        }


        [WebMethod(Description = "获取所有地址")]
        public string[] selectAllAddress(string city)
        {
            return dbOperation.selectAllAddress(city).ToArray();
        }

        [WebMethod(Description = "获取所有省")]
        public string[] selectProvince()
        {
            return dbOperation.selectProvince().ToArray();
        }

        [WebMethod(Description = "获取省的城市")]
        public string[] selectCity(string province)
        {
            return dbOperation.selectCity(province).ToArray();
        }

        [WebMethod(Description = "查看是否有城市")]
        public bool findCity(string province)
        {
            return dbOperation.findCity(province);
        }


        [WebMethod(Description = "根据类别选择其他类型的设施")]
        public string[] selectOhters(string hotelkind, string city)
        {
            return dbOperation.selectOhters(hotelkind, city).ToArray();
        }


        [WebMethod(Description = "根据城市得到坐标")]
        public string[] getpointvalue(string cityname)
        {
            return dbOperation.pointvalues(cityname).ToArray();
        }



        [WebMethod(Description = "加入个人订单信息")]
        public bool addOrder(string username, string nickename, string stayperson, string roomtype, string price, string telephone, string hotelname, string hoteladdress, string begindate, string enddate)
        {
            return dbOperation.addOrder(username, nickename, stayperson, roomtype, price, telephone, hotelname, hoteladdress, begindate, enddate);
        }


        [WebMethod(Description = "查看个人订单信息")]
        public string[] getUserOrder(string username)
        {
            return dbOperation.getUserOrder(username).ToArray();
        }

        [WebMethod(Description = "注册用户")]
        public bool userRegister(string mobilephone, string nickename, string password, string email, int sexy, string hotelid, string addressidcard, string idcard, string birthdate)
        {
            return dbOperation.userRegister(mobilephone, nickename, password, email, sexy, hotelid, addressidcard, idcard, birthdate);
        }


        [WebMethod(Description = "个人登录信息")]
        public bool userLogin(string username, string userpwd)
        {
            return dbOperation.userLogin(username, userpwd);
        }

        [WebMethod(Description = "商家登录信息")]
        public string[] managerLogin(string username, string password)
        {
            return dbOperation.managerLogin(username, password).ToArray();
        }

        [WebMethod(Description = "更改个人密码")]
        public bool userPwdChange(string username, string userpwd)
        {
            return dbOperation.userPwdChange(username, userpwd);
        }

        [WebMethod(Description = "用户信息获取")]
        public string[] userInfor(string mobilephone)
        {
            return dbOperation.userInfor(mobilephone).ToArray();
        }
        [WebMethod(Description = "酒店名和totalid")]    //new  
        public string[] hotelshopInfo(string hotelid)
        {
            return dbOperation.hotelshopInfo(hotelid).ToArray();
        }

        [WebMethod(Description = "获取其他商店信息")]
        public string[] getShopInfo()
        {
            return dbOperation.getShopInfo().ToArray();
        }

        [WebMethod(Description = "用户现金消费")]
        public bool getCashconsume(string uid, string hotelid, string money, string serviceuserid)
        {
            return dbOperation.getCashconsume(uid, hotelid, money, serviceuserid);
        }

        [WebMethod(Description = "添加用户储值")]
        public bool addUserChuzhi(string uid, string hotelid, string hoteltotalid, string scorechuzhi)
        {
            return dbOperation.addUserChuzhi(uid, hotelid, hoteltotalid, scorechuzhi);
        }

        [WebMethod(Description = "获取匹配码用户信息")]
        public string[] getPayCodeUid(string code, string mobilephone)
        {
            return dbOperation.getPayCodeUid(code, mobilephone).ToArray();
        }

        [WebMethod(Description = "修改匹配码")]
        public bool updatePayCode(string code)
        {
            return dbOperation.updatePayCode(code);
        }


        [WebMethod(Description = "获取酒店房型")]
        public string[] getRoomInfo(string hotelid)
        {
            return dbOperation.getRoomInfo(hotelid).ToArray();
        }


        [WebMethod(Description = "通过ID获取其他商店信息")]
        public string[] getShopInfoById(string hotelid)
        {
            return dbOperation.getShopInfoById(hotelid).ToArray();
        }

        [WebMethod(Description = "查找用户邀请的会员")]
        public string[] findInvitePhone(string mobilephone)
        {
            return dbOperation.findInvitePhone(mobilephone).ToArray();
        }


        [WebMethod(Description = "通过ID获取酒店信息")]
        public string[] getHotelInfoById(string hotelid)
        {
            return dbOperation.getHotelInfoById(hotelid).ToArray();
        }


        [WebMethod(Description = "添加消息")]
        public bool addMessage(string username, string title, string message)
        {
            return dbOperation.addMessage(username, title, message);
        }

        [WebMethod(Description = "查找消息")]
        public string[] findMessage(string username)
        {
            return dbOperation.findMessage(username).ToArray();
        }

        [WebMethod(Description = "查找是否有该手机号")]
        public bool findMobilePhone(string phone)
        {
            return dbOperation.findMobilePhone(phone);
        }

        [WebMethod(Description = "查找用户储值")]
        public string[] getUserChuzhi(string uid)
        {
            return dbOperation.getUserChuzhi(uid).ToArray();
        }

        [WebMethod(Description = "获取同系统的店")]
        public string[] getSamesytemname(string hoteltotalid)
        {
            return dbOperation.getSamesytemname(hoteltotalid).ToArray();
        }


        [WebMethod(Description = "查找用户客主币")]
        public string[] getUserKezhu(string uid)
        {
            return dbOperation.getUserKezhu(uid).ToArray();
        }

        [WebMethod(Description = "增加客主币")]
        public bool addUserKezhu(string uid)
        {
            return dbOperation.addUserKezhu(uid);
        }

        [WebMethod(Description = "充值")]
        public bool chuzhiRecharge(string uid, string hotelid, string hoteltotalid, string money, string serviceuserid)
        {
            return dbOperation.chuzhiRecharge(uid, hotelid, hoteltotalid, money, serviceuserid);
        }

        [WebMethod(Description = "搜索商店")]
        public string[] findShops(string city, string shopname)
        {
            return dbOperation.findShops(city, shopname).ToArray();
        }

        [WebMethod(Description = "根据类型找商家")]
        public string[] findShopsByKind(string city, string hotelkind)
        {
            return dbOperation.findShopsByKind(city, hotelkind).ToArray();
        }

        [WebMethod(Description = "添加储值记录")]
        public bool addChuzhihistory(string uid, string hotelid, string hoteltotalid, string chuzhihotelid, string 实收金额, string 会员储值账户, string 会员已消费储值账户, string 接待商家储值账户, string 储值商家储值账户, string 接待商家清算账户, string 储值商家清算账户, string serviceuserid, string consumetype)
        {
            return dbOperation.addChuzhihistory(uid, hotelid, hoteltotalid, chuzhihotelid, 实收金额, 会员储值账户, 会员已消费储值账户, 接待商家储值账户, 储值商家储值账户, 接待商家清算账户, 储值商家清算账户, serviceuserid, consumetype);
        }

        [WebMethod(Description = "查找储值记录")]
        public string[] getChuzhihistory(string uid, string hotelid)
        {
            return dbOperation.getChuzhihistory(uid, hotelid).ToArray();
        }

        [WebMethod(Description = "添加支付码")]
        public bool AddFastPayCode(string username, string code)
        {
            return dbOperation.AddFastPayCode(username, code);
        }

        [WebMethod(Description = "查询最小价格")]
        public string[] getHotelMinPrice(string hotelid)
        {
            return dbOperation.getHotelMinPrice(hotelid).ToArray();
        }

        [WebMethod(Description = "查询客主币历史")]
        public string[] getKezhuhistory(string uid)
        {
            return dbOperation.getKezhuhistory(uid).ToArray();
        }

        [WebMethod(Description = "同系统外店储值排序")]    //new  
        public string[] getChuzhiSort(string uid, string hoteltotalid)
        {
            return dbOperation.getChuzhiSort(uid, hoteltotalid).ToArray();
        }

        [WebMethod(Description = "测试添加")]
        public bool test(string id, string num)
        {
            return dbOperation.test(id, num);
        }

        [WebMethod(Description = "判断当前电话号码是否存在,或者号码存在两个一样的")]
        public string hasMobilephone(string mobile)
        {
            return dbOperation.hasMobilephone(mobile);
        }

        [WebMethod(Description = "根据卡号身份证号更改手机号")]
        public bool userMobileSet(string idcard, string mobilephone, string cardnumber)
        {
            return dbOperation.userMobileSet(idcard, mobilephone, cardnumber);
        }

        [WebMethod(Description = "储值消费")]
        public bool chuzhiConsume(string uid, string hotelid, string hoteltotalid, string money, string serviceuserid)
        {
            return dbOperation.chuzhiConsume(uid, hotelid, hoteltotalid, money, serviceuserid);
        }


        [WebMethod(Description = "商家同系统总值")]
        public string getUserSamesytemchuzhi(string uid, string hoteltotalid)
        {
            return dbOperation.getUserSamesytemchuzhi(uid, hoteltotalid);
        }

        [WebMethod(Description = "更新同系统外店储值清算")]
        public bool updateChuzhiSamesystem(string hotelid, string hoteltotalid, string clearingmoney)
        {
            return dbOperation.updateChuzhiSamesystem(hotelid, hoteltotalid, clearingmoney);
        }
        [WebMethod(Description = "客主币消费")]
        public bool getKezhuconsum(string uid, string hotelid, string money, string serviceuserid)
        {
            return dbOperation.getKezhuconsum(uid, hotelid, money, serviceuserid);
        }


        [WebMethod(Description = "查找酒店邀请码")]
        public bool findHotelInvite(string inviteCode)
        {
            return dbOperation.findHotelInvite(inviteCode);
        }

        [WebMethod(Description = "获得客主币各项信息")]
        public string[] getUserKezhuItem(string uid)
        {
            return dbOperation.getUserKezhuItem(uid).ToArray();
        }
    }
}