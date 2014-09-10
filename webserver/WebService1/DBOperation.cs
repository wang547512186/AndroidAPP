using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Xml.Linq;
using System.Data.SqlClient;
using System.Text.RegularExpressions;
using System.Collections;
using System.Security.Cryptography;
using System.Text;
using Encoder = System.Text.Encoder;

namespace WebService1
{
    public class DBOperation : IDisposable
    {
        public static SqlConnection sqlCon;  //用于连接数据库  

        //将下面的引号之间的内容换成上面记录下的属性中的连接字符串  
        //private String ConServerStr = @"Data Source=192.168.10.119,1434;Initial Catalog=StockManage;UID=sa;Password=1234";


        //private String ConServerStr = @"Data Source=nengyu1234.xicp.net,1434;Initial Catalog=StockManage;Integrated Security=True";
        //private String ConServerStr = @"Data Source=nengyu1234.xicp.net,1434;Initial Catalog=MemberData;UID=sa;Password=1234";
        private String ConServerStr = @"Data Source=localhost;Initial Catalog=kezhu;Integrated Security=True";
        //private String ConServerStr = @"Data Source=localhost;Initial Catalog=MemberData;Integrated Security=True";
        //默认构造函数  
        public DBOperation()
        {
        }

        public void Open()
        {
            if (sqlCon == null)
            {
                sqlCon = new SqlConnection();
                sqlCon.ConnectionString = ConServerStr;
                sqlCon.Open();
            }
        }

        //关闭/销毁函数，相当于Close()  
        public void Dispose()
        {
            if (sqlCon != null)
            {
                sqlCon.Close();
                sqlCon = null;
            }
        }


        public List<string> findAddress(string hotelid)
        {
            List<string> list = new List<string>();
            Open();
            string sql = "select * from hotel where hotelid='" + hotelid + "'";
            SqlCommand cmd = new SqlCommand(sql, sqlCon);
            SqlDataReader read = cmd.ExecuteReader();
            try
            {
                while (read.Read())
                {
                    list.Add(read["hotelid"].ToString());
                    //将结果集信息添加到返回向量中  
                    list.Add(read["hotelname"].ToString());
                    list.Add(read["hotelkind"].ToString());
                }
            }
            catch (Exception)
            {
                list.Add("无地址");
            }
            finally
            {
                read.Close();
                Dispose();
            }
            return list;
        }

        public List<string> selectAddressByCity(string city)
        {
            List<string> list = new List<string>();
            Open();
            string sql = "select * from hotel where city='" + city + "'";
            SqlCommand cmd = new SqlCommand(sql, sqlCon);
            SqlDataReader read = cmd.ExecuteReader();
            try
            {
                while (read.Read())
                {
                    list.Add(read["hotelid"].ToString());
                    //将结果集信息添加到返回向量中  
                    list.Add(read["hotelname"].ToString());
                    list.Add(read["hotelkind"].ToString());
                }
            }
            catch (Exception ex)
            {
                list.Add("无法连接");
            }
            finally
            {
                read.Close();
                Dispose();
            }
            return list;
        }

        public List<string> selectAllAddress(string city)
        {
            List<string> list = new List<string>();
            Open();
            string sql = "select * from hotel where city like'" + '%' + city + '%' + "'";
            SqlCommand cmd = new SqlCommand(sql, sqlCon);
            SqlDataReader reader = cmd.ExecuteReader();
            try
            {
                while (reader.Read())
                {
                    list.Add(reader["hotelid"].ToString());
                    //将结果集信息添加到返回向量中  
                    list.Add(reader["hotelname"].ToString());
                    list.Add(reader["address"].ToString());
                    list.Add(reader["mapPosion"].ToString());
                    list.Add(reader["hotelkind"].ToString());
                }
            }
            catch (Exception ex)
            {
                list.Add("无法连接");
            }
            finally
            {
                reader.Close();
                Dispose();
            }
            return list;
        }


        public List<string> selectProvince()
        {
            List<string> list = new List<string>();
            Open();
            string sql = "select distinct province from Citymap";
            SqlCommand cmd = new SqlCommand(sql, sqlCon);
            SqlDataReader reader = cmd.ExecuteReader();
            try
            {
                while (reader.Read())
                {
                    //将结果集信息添加到返回向量中  
                    list.Add(reader[0].ToString());
                }
            }
            catch (Exception ex)
            {
                list.Add(ex.ToString());
            }
            finally
            {
                reader.Close();
                Dispose();
            }
            return list;
        }

        public bool findCity(string province)
        {
            int i = 0;
            Open();
            string sql = "select * from Citymap where province='" + province + "'";
            SqlCommand cmd = new SqlCommand(sql, sqlCon);
            SqlDataReader reader = cmd.ExecuteReader();
            try
            {

                if (reader.Read())
                {
                    i = Convert.ToInt32(reader["hascity"]);
                }

            }
            catch (Exception)
            {

            }
            finally
            {
                reader.Close();
                Dispose();
            }
            if (i == 1)
                return true;
            else
                return false;
        }

        public List<string> selectCity(string province)
        {
            List<string> list = new List<string>();
            Open();
            string sql = "select city from Citymap where province='" + province + "'";
            SqlCommand cmd = new SqlCommand(sql, sqlCon);
            SqlDataReader reader = cmd.ExecuteReader();
            try
            {


                while (reader.Read())
                {
                    //将结果集信息添加到返回向量中  
                    list.Add(reader[0].ToString());
                }



            }
            catch (Exception)
            {

            }
            finally
            {
                reader.Close();
                Dispose();
            }
            return list;
        }

        public List<string> selectOhters(string hotelkind, string city)
        {
            List<string> list = new List<string>();
            Open();
            string sql = "select * from hotel where city like'" + '%' + city + '%' + "'and hotelkind='" + hotelkind + "'";
            SqlCommand cmd = new SqlCommand(sql, sqlCon);
            SqlDataReader reader = cmd.ExecuteReader();
            try
            {
                while (reader.Read())
                {
                    //将结果集信息添加到返回向量中  
                    list.Add(reader["hotelid"].ToString());
                    //将结果集信息添加到返回向量中  
                    list.Add(reader["hotelname"].ToString());
                    list.Add(reader["address"].ToString());
                    list.Add(reader["mapPosion"].ToString());
                    list.Add(reader["hotelkind"].ToString());
                }
            }
            catch (Exception)
            {
                list.Add("无法连接");
            }
            finally
            {
                reader.Close();
                Dispose();
            }
            return list;
        }


        public List<string> pointvalues(string cityname)
        {
            List<string> list = new List<string>();
            Open();
            string sql = "select * from Citymap where city='" + cityname + "'";
            SqlCommand cmd = new SqlCommand(sql, sqlCon);
            SqlDataReader reader = cmd.ExecuteReader();
            try
            {
                while (reader.Read())
                {
                    //将结果集信息添加到返回向量中  
                    list.Add(reader["Mlat"].ToString());
                    list.Add(reader["Mlon"].ToString());
                }
            }
            catch (Exception)
            {

            }
            finally
            {
                reader.Close();
                Dispose();
            }
            return list;
        }



        public bool addOrder(string username, string nickename, string stayperson, string roomtype, string price, string telephone, string hotelname, string hoteladdress, string begindate, string enddate)
        {
            try
            {
                int count = 0;
                Open();
                string orderidsql = String.Format("select count(*) from UserOrder where orderid like '{0:yyyyMMdd}%'", DateTime.Now);
                SqlCommand orderidcmd = new SqlCommand(orderidsql, sqlCon);
                SqlDataReader reader = orderidcmd.ExecuteReader();
                if (reader.Read())
                {
                    count = Int32.Parse(reader[0].ToString());
                }
                reader.Close();
                Dispose();

                string num = "";
                int capacity = count.ToString().ToCharArray().Length;
                for (int i = 0; i < 2 - capacity; i++)
                {
                    num += "0";
                }
                num += count.ToString();

                //日期+编号

                Open();
                string sql = "insert into UserOrder values ('" + DateTime.Now.ToString("yyyyMMdd") + num + "','" + username + "','" + nickename + "','" + stayperson + "','" + roomtype + "','" + price + "','" + telephone + "','" + hotelname + "','" + hoteladdress + "','" + DateTime.Now.ToString("yyyy-MM-dd HH:mm") + "','" + begindate + "','" + enddate + "')";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                cmd.ExecuteNonQuery();
                Dispose();
                return true;
            }
            catch (Exception)
            {
                return false;
            }
        }


        public bool addMessage(string username, string title, string message)
        {
            Open();
            string sql = "insert into [message] values ('" + username + "','" + title + "','" + message + "','" + DateTime.Now.ToString("yyyy-MM-dd HH:mm") + "')";
            SqlCommand cmd = new SqlCommand(sql, sqlCon);
            try
            {

                cmd.ExecuteNonQuery();

                return true;
            }
            catch (Exception)
            {
                return false;
            }
            finally { Dispose(); }
        }

        public bool addUserChuzhi(string uid, string hotelid, string hoteltotalid, string scorechuzhi)
        {
            Open();
            string sql = "insert into [usermoneyhotel] (usermoneyhotelid,uid,groupid,totaluserid,hotelid,hoteltotalid,scorechuzhi,createtime) values ('" + Guid.NewGuid() + "','" + uid + "','200','00000000-0000-0000-0000-000000000000','" + hotelid + "','" + hoteltotalid + "','" + scorechuzhi + "','" + DateTime.Now + "')";
            SqlCommand cmd = new SqlCommand(sql, sqlCon);
            try
            {

                cmd.ExecuteNonQuery();

                return true;
            }
            catch (Exception)
            {
                return false;
            }
            finally { Dispose(); }
        }

        public List<string> findMessage(string username)
        {
            List<string> list = new List<string>();
            Open();
            string sql = "select * from [message] where [username]='" + username + "'";
            SqlCommand cmd = new SqlCommand(sql, sqlCon);
            SqlDataReader reader = cmd.ExecuteReader();
            try
            {


                while (reader.Read())
                {
                    //将结果集信息添加到返回向量中
                    list.Add(reader[1].ToString());
                    list.Add(reader[2].ToString());
                    list.Add(reader[3].ToString());
                    list.Add(reader[4].ToString());
                }



            }
            catch (Exception)
            {

            }
            finally
            {
                reader.Close();
                Dispose();
            }
            return list;
        }


        public List<string> getUserOrder(string username)
        {
            List<string> list = new List<string>();
            Open();
            string sql = "select * from UserOrder where username='" + username + "' order by ordertime desc";
            SqlCommand cmd = new SqlCommand(sql, sqlCon);
            SqlDataReader read = cmd.ExecuteReader();
            try
            {

                while (read.Read())
                {
                    list.Add(read["orderid"].ToString());
                    list.Add(read["username"].ToString());
                    list.Add(read["nickname"].ToString());
                    list.Add(read["入住人"].ToString());
                    list.Add(read["房型"].ToString());
                    list.Add(read["价格"].ToString());
                    list.Add(read["telephone"].ToString());
                    list.Add(read["hotelname"].ToString());
                    list.Add(read["hoteladdress"].ToString());
                    list.Add(read["ordertime"].ToString());
                    //list.Add(read["begindate"].ToString());
                    string[] begindate = read["begindate"].ToString().Split(' ');
                    list.Add(begindate[0]);
                    string[] enddate = read["enddate"].ToString().Split(' ');
                    list.Add(enddate[0]);

                }


            }
            catch (Exception)
            { }
            finally
            {
                read.Close();
                Dispose();
            }
            return list;
        }

        public bool findMobilePhone(string phone)
        {
            Open();
            string sql = "select mobilephone from users where mobilephone='" + phone + "'";
            SqlCommand cmd = new SqlCommand(sql, sqlCon);
            object obj = cmd.ExecuteScalar();
            try
            {
                if (obj != null)
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
            catch (Exception)
            {
                return false;
            }
            finally
            {
                Dispose();
            }
        }
        //更改后的注册
        public bool userRegister(string mobilephone, string nickename, string password, string email, int sexy, Int64 hotelid, string addressidcard, string idcard, string birthdate)
        {
            Guid guid = new Guid();
            guid = Guid.NewGuid();
            password = MD5(password);
            try
            {
                Open();
                string sql = "insert into users values ('" + guid + "','" + mobilephone + "','" + nickename + "','" + hotelid + "','" + password + "','200','" + email + "','" + sexy + "','" + addressidcard + "','" + mobilephone + "','" + DateTime.Now + "','" + idcard + "','0','1','" + birthdate + "') ";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                cmd.ExecuteNonQuery();
                sql = "insert into usermoney values('" + guid + "','200','" + hotelid + "','0.00','0.00','" + DateTime.Now + "')";
                cmd.CommandText = sql;
                cmd.ExecuteNonQuery();
                Dispose();
                return true;
            }
            catch (Exception)
            {
                return false;
            }
        }


        public bool userLogin(string name, string pwd)
        {
            string userpwd = "";
            Open();
            string sql = "select [password] from users where username='" + name + "'";
            SqlCommand cmd = new SqlCommand(sql, sqlCon);
            SqlDataReader read = cmd.ExecuteReader();
            try
            {

                if (read.Read())
                {
                    userpwd = read["password"].ToString();
                }
                if (userpwd == MD5(pwd))
                    return true;
                else
                    return false;
            }
            catch (Exception)
            {
                return false;
            }
            finally
            {
                read.Close();
                Dispose();
            }

        }

        public bool userPwdChange(string name, string pwd)
        {
            Open();
            pwd = MD5(pwd);
            string sql = "update users set password='" + pwd + "' where username='" + name + "'";
            SqlCommand cmd = new SqlCommand(sql, sqlCon);
            try
            {

                cmd.ExecuteNonQuery();

                return true;
            }
            catch (Exception)
            {
                return false;
            }
            finally { Dispose(); }
        }

        public static string MD5(string str)
        {
            str = str + "jDs98Skd";
            byte[] b = Encoding.Default.GetBytes(str);
            b = new MD5CryptoServiceProvider().ComputeHash(b);
            string ret = "";
            for (int i = 0; i < b.Length; i++)
                ret += b[i].ToString("x").PadLeft(2, '0');
            return ret;
        }

        public List<string> userInfor(string mobilephone)
        {
            List<string> list = new List<string>();
            Open();
            string sql = "select * from users where mobilephone='" + mobilephone + "' and groupid='200'";
            SqlCommand cmd = new SqlCommand(sql, sqlCon);
            SqlDataReader read = cmd.ExecuteReader();
            try
            {
                if (!mobilephone.Equals(""))
                {
                    if (read.Read())
                    {
                        list.Add(read["uid"].ToString());
                        list.Add(read["nickname"].ToString());
                        list.Add(read["password"].ToString());
                        list.Add(read["email"].ToString());
                        list.Add(read["sexy"].ToString());
                        list.Add(read["mobilephone"].ToString());
                        list.Add(read["birthdate"].ToString());
                    }
                }

            }
            catch (Exception)
            {
            }
            finally
            {
                Dispose();
            }
            return list;

        }

        public List<string> hotelshopInfo(string hotelid)
        {
            List<string> list = new List<string>();
            Open();
            string sql = "select * from hotel where hotelid='" + hotelid + "'";
            SqlCommand cmd = new SqlCommand(sql, sqlCon);
            SqlDataReader read = cmd.ExecuteReader();
            try
            {
                if (read.Read())
                {
                    list.Add(read["hotelname"].ToString());
                    list.Add(read["hoteltotalid"].ToString());
                }
            }
            catch (Exception)
            {
            }
            finally
            {
                Dispose();
            }
            return list;

        }


        //3
        public List<string> getConsume(string customeruserid)
        {
            List<string> list = new List<string>();
            Open();
            string sql = "select * from hotelhistory where customeruserid='" + customeruserid + "'";
            SqlCommand cmd = new SqlCommand(sql, sqlCon);
            SqlDataReader read = cmd.ExecuteReader();
            try
            {

                while (read.Read())
                {
                    list.Add(read["hotelhistoryid"].ToString());
                    list.Add(read["customeruserid"].ToString());
                    list.Add(read["customername"].ToString());

                    list.Add(read["address"].ToString());
                    list.Add(read["hotelname"].ToString());
                    list.Add(read["money"].ToString());
                    list.Add(read["moneykezhu"].ToString());
                    list.Add(read["returnkezhu"].ToString());
                    list.Add(read["chuzhi"].ToString());
                    list.Add(read["begindate"].ToString());
                    //list.Add(read["促销活动"].ToString());
                    //list.Add(read["价格说明"].ToString());

                }

            }
            catch (Exception)
            { }
            finally
            {
                read.Close();
                Dispose();
            }
            return list;
        }


        public bool updateUser(string type, string value, string username)
        {
            Open();
            string sql = "update users set  " + type + "='" + value + "' where username='" + username + "'";
            SqlCommand cmd = new SqlCommand(sql, sqlCon);
            try
            {

                cmd.ExecuteNonQuery();

                return true;
            }
            catch
            {
                return false;
            }
            finally
            {
                Dispose();
            }
        }
        //4
        public bool addHistory(string customeruserid, string customername, string hotelid, string hotelname, string money, string scorecustomer, string storedmoneycustomer)
        {
            Open();
            string sql = "insert into hotelhistory (customeruserid,customername,hotelid,hotelname,money,scorecustomer,storedmoneycustomer,createtime) values('" + customeruserid + "','" + customername + "','" + hotelid + "','" + hotelname + "','" + money + "','" + scorecustomer + "','" + storedmoneycustomer + "','" + DateTime.Now + "')";
            SqlCommand cmd = new SqlCommand(sql, sqlCon);
            try
            {
                cmd.ExecuteNonQuery();
                return true;
            }
            catch
            {
                return false;
            }
            finally
            {
                Dispose();
            }
        }

        //5
        public bool addHotelhistory(string customeruserid, string customername, string address, string hotelname, string money, string moneykezhu, string returnkezhu, string chuzhi)
        {
            Open();
            string sql = "insert into hotelhistory values('" + customeruserid + "','" + customername + "','" + address + "','" + hotelname + "','" + money + "','" + moneykezhu + "','" + returnkezhu + "','" + chuzhi + "','" + DateTime.Now + "')";
            SqlCommand cmd = new SqlCommand(sql, sqlCon);
            try
            {

                cmd.ExecuteNonQuery();

                return true;
            }
            catch
            {
                return false;
            }
            finally
            {
                Dispose();
            }
        }


        public List<string> getRoomInfo(int hotelid)
        {
            List<string> list = new List<string>();
            Open();
            string sql = "select * from room where hotelinfoid ='" + hotelid + "'";
            SqlCommand cmd = new SqlCommand(sql, sqlCon);
            SqlDataReader read = cmd.ExecuteReader();
            try
            {

                while (read.Read())
                {
                    list.Add(read["hotelinfoid"].ToString());
                    list.Add(read["房型"].ToString());
                    list.Add(read["会员价"].ToString());
                    list.Add(read["床型"].ToString());
                    list.Add(read["房型特点"].ToString());
                    list.Add(read["早餐"].ToString());
                    list.Add(read["宽带"].ToString());
                    list.Add(read["酒店名称"].ToString());
                    list.Add(read["门市价"].ToString());
                    //list.Add(read["begindate"].ToString());
                    //list.Add(read["促销活动"].ToString());
                    //list.Add(read["价格说明"].ToString());

                }

            }
            catch (Exception)
            { }
            finally
            {
                read.Close();
                Dispose();
            }
            return list;
        }
        ////没有用到
        //public List<string> getHotelInfo()
        //{
        //    List<string> list = new List<string>();
        //    Open();
        //    string sql = "select * from hotel";
        //    SqlCommand cmd = new SqlCommand(sql, sqlCon);
        //    SqlDataReader read = cmd.ExecuteReader();
        //    try
        //    {

        //        while (read.Read())
        //        {

        //            list.Add(read["hotelid"].ToString());
        //            list.Add(read["hotelname"].ToString());
        //            list.Add(read["province"].ToString());
        //            list.Add(read["city"].ToString());
        //            list.Add(read["area"].ToString());
        //            list.Add(read["telephone"].ToString());
        //            list.Add(read["address"].ToString());
        //            list.Add(read["简介"].ToString());
        //            list.Add(read["email"].ToString());
        //            list.Add(read["星级"].ToString());
        //            list.Add(read["邮编"].ToString());
        //            list.Add(read["传真"].ToString());
        //            list.Add(read["餐饮设施"].ToString());
        //            list.Add(read["会议设施"].ToString());
        //            list.Add(read["休闲设施"].ToString());
        //            list.Add(read["服务设施"].ToString());
        //            list.Add(read["周围景观"].ToString());
        //            list.Add(read["上网情况"].ToString());
        //            list.Add(read["信用卡"].ToString());
        //            list.Add(read["交通"].ToString());
        //            list.Add(read["优惠说明"].ToString());
        //            list.Add(read["比例会员积分"].ToString());
        //            list.Add(read["mapPosion"].ToString());
        //            list.Add(read["促销活动"].ToString());
        //            list.Add(read["价格说明"].ToString());
        //        }

        //    }
        //    catch (Exception)
        //    { }
        //    finally
        //    {
        //        read.Close();
        //        Dispose();
        //    }
        //    return list;
        //}


        public List<string> getHotelInfoById(int hotelid)
        {
            List<string> list = new List<string>();
            Open();
            string sql = "select * from hotel where hotelid='" + hotelid + "'";
            SqlCommand cmd = new SqlCommand(sql, sqlCon);
            SqlDataReader read = cmd.ExecuteReader();
            try
            {

                while (read.Read())
                {

                    list.Add(read["hotelid"].ToString());
                    list.Add(read["hotelname"].ToString());
                    list.Add(read["province"].ToString());
                    list.Add(read["city"].ToString());
                    list.Add(read["area"].ToString());
                    list.Add(read["telephone"].ToString());
                    list.Add(read["address"].ToString());
                    list.Add(read["简介"].ToString());
                    list.Add(read["email"].ToString());
                    list.Add(read["星级"].ToString());
                    list.Add(read["邮编"].ToString());
                    list.Add(read["传真"].ToString());
                    list.Add(read["餐饮设施"].ToString());
                    list.Add(read["会议设施"].ToString());
                    list.Add(read["休闲设施"].ToString());
                    list.Add(read["服务设施"].ToString());
                    list.Add(read["周围景观"].ToString());
                    list.Add(read["上网情况"].ToString());
                    list.Add(read["信用卡"].ToString());
                    list.Add(read["交通"].ToString());
                    list.Add(read["优惠说明"].ToString());
                    list.Add(read["比例会员积分"].ToString());
                    list.Add(read["mapPosion"].ToString());
                    list.Add(read["促销活动"].ToString());
                    list.Add(read["价格说明"].ToString());
                }

            }
            catch (Exception)
            { }
            finally
            {
                read.Close();
                Dispose();
            }
            return list;
        }

        public List<string> getShopInfo()
        {
            List<string> list = new List<string>();
            Open();
            string sql = "select * from hotel where hotelkind!='1' ";
            SqlCommand cmd = new SqlCommand(sql, sqlCon);
            SqlDataReader read = cmd.ExecuteReader();
            try
            {

                while (read.Read())
                {
                    list.Add(read["hotelid"].ToString());
                    list.Add(read["hotelname"].ToString());
                    list.Add(read["province"].ToString());
                    list.Add(read["city"].ToString());
                    list.Add(read["area"].ToString());
                    list.Add(read["hotelkind"].ToString());
                    list.Add(read["telephone"].ToString());
                    list.Add(read["address"].ToString());
                    list.Add(read["email"].ToString());
                    list.Add(read["邮编"].ToString());
                    list.Add(read["菜系"].ToString());
                    list.Add(read["简介"].ToString());
                    list.Add(read["交通"].ToString());
                    list.Add(read["优惠说明"].ToString());
                    list.Add(read["比例会员积分"].ToString());
                    list.Add(read["mapPosion"].ToString());
                    list.Add(read["促销活动"].ToString());
                    list.Add(read["价格说明"].ToString());

                }

            }
            catch (Exception)
            { }
            finally
            {
                read.Close();
                Dispose();
            }
            return list;
        }

        public List<string> getShopInfoById(string hotelid)
        {
            List<string> list = new List<string>();
            Open();
            string sql = "select * from shop where hotelid='" + hotelid + "'";
            SqlCommand cmd = new SqlCommand(sql, sqlCon);
            SqlDataReader read = cmd.ExecuteReader();
            try
            {

                while (read.Read())
                {
                    list.Add(read["hotelid"].ToString());
                    list.Add(read["hotelname"].ToString());
                    list.Add(read["province"].ToString());
                    list.Add(read["city"].ToString());
                    list.Add(read["area"].ToString());
                    list.Add(read["hotelkind"].ToString());
                    list.Add(read["telephone"].ToString());
                    list.Add(read["address"].ToString());
                    list.Add(read["email"].ToString());
                    list.Add(read["邮编"].ToString());
                    list.Add(read["菜系"].ToString());
                    list.Add(read["简介"].ToString());
                    list.Add(read["交通"].ToString());
                    list.Add(read["优惠说明"].ToString());
                    list.Add(read["比例会员积分"].ToString());
                    list.Add(read["mapPosion"].ToString());
                    list.Add(read["促销活动"].ToString());
                    list.Add(read["价格说明"].ToString());

                }

            }
            catch (Exception)
            { }
            finally
            {
                read.Close();
                Dispose();
            }
            return list;
        }
        //6
        public List<string> getUsemoney(int id)
        {
            List<string> list = new List<string>();
            Open();
            string sql = "select * from usermoney where id='" + id + "'";
            SqlCommand cmd = new SqlCommand(sql, sqlCon);
            SqlDataReader read = cmd.ExecuteReader();
            try
            {

                while (read.Read())
                {

                    list.Add(read["id"].ToString());
                    list.Add(read["hotelid"].ToString());
                    list.Add(read["scorechuzhi"].ToString());
                    list.Add(read["joindate"].ToString());

                }

            }
            catch (Exception)
            { }
            finally
            {
                read.Close();
                Dispose();
            }
            return list;
        }

        //7
        public List<string> getUsemoneyhistory(int id)
        {
            List<string> list = new List<string>();
            Open();
            string sql = "select * from usermoneyhotel where uid='" + id + "' ";
            SqlCommand cmd = new SqlCommand(sql, sqlCon);
            SqlDataReader read = cmd.ExecuteReader();
            try
            {

                while (read.Read())
                {

                    list.Add(read["uid"].ToString());
                    list.Add(read["hotelid"].ToString());
                    list.Add(read["outchuzhi"].ToString());
                    list.Add(read["inchuzhi"].ToString());

                }

            }
            catch (Exception)
            { }
            finally
            {
                read.Close();
                Dispose();
            }
            return list;
        }
        //8
        public bool updateUserChuzhi(string uid, string hotelid, string money)
        {
            Open();
            string sql = "update usermoneyhotel set scorechuzhi ='" + money + "' where hotelid='" + hotelid + "' and uid='" + uid + "'";
            SqlCommand cmd = new SqlCommand(sql, sqlCon);
            try
            {
                if ((int)cmd.ExecuteNonQuery() != 0)
                    return true;
                else
                    return false;
            }
            catch
            {
                return false;
            }
            finally
            {
                Dispose();
            }
        }

        //9
        public bool updateUserKezhu(string uid, string money)
        {
            Open();
            string sql = "update usermoney set scorexiaofei ='" + money + "' where uid='" + uid + "'";
            SqlCommand cmd = new SqlCommand(sql, sqlCon);
            try
            {

                cmd.ExecuteNonQuery();
                return true;
            }
            catch
            {
                return false;
            }
            finally
            {
                Dispose();
            }
        }

        //更改后的获得用户各个商家的总储值分条
        public List<string> getUserChuzhi(string uid)
        {
            List<string> list = new List<string>();
            List<string> listresult = new List<string>();
            Open();
            string sql = "select * from ChuzhiHistory where uid='" + uid + "'";
            SqlCommand cmd = new SqlCommand(sql, sqlCon);
            SqlDataReader read = cmd.ExecuteReader();
            try
            {
                while (read.Read())
                {
                    list.Add(read["uid"].ToString());
                    list.Add(read["hotelid"].ToString());
                    list.Add(read["会员储值账户"].ToString());
                }
            }
            catch (Exception)
            { }
            finally
            {
                read.Close();
                Dispose();
            }
            for (int i = 0; i < list.Count / 3; i++)
            {
                if (i == 0)
                {
                    listresult.Add(list[i * 3]);
                    listresult.Add(list[i * 3 + 1]);
                    listresult.Add(list[i * 3 + 2]);
                }
                else
                {
                    bool isexit = false;
                    for (int m = 0; m < listresult.Count / 3; m++)
                    {
                        if (listresult[m * 3 + 1] == list[i * 3 + 1])
                        {
                            double a = Convert.ToDouble(listresult[m * 3 + 2]);
                            double b = Convert.ToDouble(list[i * 3 + 2]);
                            double c = a + b;
                            listresult[m * 3 + 2] = c.ToString();
                            isexit = true;
                        }
                    }
                    if (!isexit)
                    {
                        listresult.Add(list[i * 3]);
                        listresult.Add(list[i * 3 + 1]);
                        listresult.Add(list[i * 3 + 2]);
                    }
                }
            }
            return listresult;
        }


        //更改后的得到某个店的储值消费记录
        public List<string> getChuzhihistory(string uid, string hotelid)
        {
            List<string> list = new List<string>();
            try
            {
                Open();
                string sql = "select * from ChuzhiHistory where uid='" + uid + "' and hotelid='" + hotelid + "' order by createtime desc";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader read = cmd.ExecuteReader();
                while (read.Read())
                {
                    list.Add(read["会员储值账户"].ToString());
                    list.Add(read["createtime"].ToString());
                }
                read.Close();
                Dispose();
            }
            catch (Exception)
            { }
            return list;
        }

        //12
        public bool addUserKezhu(string uid)
        {
            Open();
            string sql = "insert into usermoney ( ) values('" + new Guid(uid) + "','100','00000000-0000-0000-0000-000000000000','0.00','0.00','0.00','" + DateTime.Now + "')";
            SqlCommand cmd = new SqlCommand(sql, sqlCon);
            try
            {
                cmd.ExecuteNonQuery();
                return true;
            }
            catch
            {
                return false;
            }
            finally
            {
                Dispose();
            }
        }
        //更改后的
        public List<string> getUserKezhu(string uid)
        {
            List<string> list = new List<string>();
            Open();
            //string sqlSel = string.Format("select * from usermoneyhotel,UserInfo where usermoneyhotel.uid=UserInfo.Id and UserInfo.username='{0}'",username);
            string sql = "select kezhumoney from usermoney where uid='" + uid + "'";
            SqlCommand cmd = new SqlCommand(sql, sqlCon);
            SqlDataReader read = cmd.ExecuteReader();
            try
            {

                if (read.Read())
                {
                    list.Add(read[0].ToString());
                    //  list.Add(read["userid"].ToString());

                }

            }
            catch (Exception)
            { list.Add("0.00"); }
            finally
            {
                read.Close();
                Dispose();
            }
            return list;
        }

        public List<string> findInvitePhone(string mobilephone)
        {

            List<string> list = new List<string>();
            Open();
            try
            {

                string sql = "select * from users where hotelid='" + mobilephone + "'";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                cmd.ExecuteNonQuery();
                sql = "select @@rowcount";
                cmd.CommandText = sql;
                int count = (int)cmd.ExecuteScalar();
                list.Add(count.ToString());

            }
            catch (Exception)
            { }
            Dispose();
            return list;
        }


        public List<string> findShops(string city, string shopname)
        {
            List<string> list = new List<string>();
            Open();
            string sql = "select * from hotel where city like'" + '%' + city + '%' + "' and hotelname like '" + '%' + shopname + '%' + "'";
            SqlCommand cmd = new SqlCommand(sql, sqlCon);
            SqlDataReader read = cmd.ExecuteReader();
            try
            {
                while (read.Read())
                {
                    list.Add(read["hotelid"].ToString().Trim());
                    list.Add(read["hotelname"].ToString().Trim());
                    list.Add(read["hotelkind"].ToString());
                    list.Add(read["address"].ToString().Trim());
                    list.Add(read["mapPosion"].ToString());

                }
            }
            catch (Exception)
            { }
            finally
            {
                read.Close();
                Dispose();
            }
            return list;
        }
        //更改后的
        public bool addChuzhihistory(string uid, string hotelid, string money, string serviceuserid)
        {
            try
            {
                Open();
                string sql = "insert into ChuzhiHistory values('" + uid + "','" + hotelid + "','" + money + "','" + money + "','" + 0 + "','" + 0 + "','" + "-" + money + "','" + 0 + "','" + 0 + "','" + DateTime.Now + "','" + serviceuserid + "')";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                cmd.ExecuteNonQuery();
                Dispose();
                return true;
            }
            catch
            {
                return false;
            }
        }




        //16
        public List<string> getKezhuhistory(string uid)
        {
            List<string> list = new List<string>();
            try
            {
                Open();
                string sql = "select * from hotelhistory where customeruserid='" + uid + "'order by createtime desc";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader read = cmd.ExecuteReader();
                while (read.Read())
                {
                    list.Add(read["hotelid"].ToString());
                    list.Add(read["hotelname"].ToString());
                    list.Add(read["money"].ToString());
                    list.Add(read["scorecustomer"].ToString());
                    list.Add(read["createtime"].ToString());
                }
                read.Close();
                Dispose();
            }
            catch (Exception)
            { }
            return list;
        }

        public bool AddFastPayCode(string username, string code)
        {
            try
            {
                Open();
                string sql = "insert into Fastpay values ('" + username + "','" + code + "','" + DateTime.Now + "','1')";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                cmd.ExecuteNonQuery();
                Dispose();
                return true;
            }
            catch (Exception)
            {
                return false;
            }
        }

        public List<string> getHotelMinPrice(string hotelid)
        {
            List<string> list = new List<string>();
            try
            {
                Open();

                string sql = "select top 1 会员价 from room,hotelinfo where room.hotelinfoid=hotelinfo.hotelinfoid and hotelinfo.hotelid='" + hotelid + "' order by 会员价";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader read = cmd.ExecuteReader();
                while (read.Read())
                {
                    list.Add(read["会员价"].ToString());

                }
                read.Close();
                Dispose();
            }
            catch (Exception)
            { }
            return list;
        }


        public bool test(string id, string num)
        {
            List<string> list = new List<string>();
            try
            {
                Open();
                string sql = "insert into test values ('" + id + "','" + num + "')";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                cmd.ExecuteNonQuery();
                Dispose();
                return true;
            }
            catch (Exception)
            {
                return false;
            }
        }
        //更改后的
        public List<string> managerLogin(string username, string password)
        {
            List<string> list = new List<string>();
            Open();
            string sql = "select * from users where username='" + username + "' and password='" + MD5(password) + "' and groupid=100 ";
            SqlCommand cmd = new SqlCommand(sql, sqlCon);
            SqlDataReader read = cmd.ExecuteReader();
            try
            {
                if (read.Read())
                {
                    list.Add(read["uid"].ToString());
                    list.Add(read["hotelid"].ToString());
                }
                else
                {
                    list.Add("false");
                }

            }
            catch (Exception)
            {
                list.Add("false");
            }
            finally
            {
                read.Close();
                Dispose();
            }
            return list;
        }

        public string hasMobilephone(string mobilephone)
        {
            string results;
            Open();
            string sql = "select mobilephone from users where mobilephone='" + mobilephone + "'";
            SqlCommand cmd = new SqlCommand(sql, sqlCon);
            try
            {
                cmd.ExecuteNonQuery();
                sql = "select @@rowcount ";
                cmd.CommandText = sql;
                int count = (int)cmd.ExecuteScalar();
                if (count == 0)
                    results = "0";
                else if (count == 1)
                    results = "1";
                else
                    results = "2";
            }
            catch
            {
                results = "9";
            }
            Dispose();
            return results;
        }




        public bool userMobileSet(string idcard, string mobilephone, string cardnumber)
        {
            Open();
            string sql = "update users set mobile='" + mobilephone + "' where idcard='" + idcard + "' and cardnumber='" + cardnumber + "'";
            SqlCommand cmd = new SqlCommand(sql, sqlCon);
            try
            {
                cmd.ExecuteNonQuery();
                return true;
            }
            catch
            {
                return false;
            }
            finally
            {
                Dispose();
            }
        }



    }
}