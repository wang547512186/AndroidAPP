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

        //查找酒店邀请码
        public bool findHotelInvite(string inviteCode)
        {
            Open();
            string sql = "select right(cast(power(10,4) as varchar)+ hotelid,4)as A from hotel";
            SqlCommand cmd = new SqlCommand(sql, sqlCon);
            SqlDataReader read = cmd.ExecuteReader();
            String hotelid = "100";
            try
            {
                while (read.Read())
                {
                    if ((hotelid + read["A"].ToString()) == inviteCode)
                        return true;
                }
                return false;
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
        public bool userRegister(string mobilephone, string nickename, string password, string email, int sexy, string hotelid, string addressidcard, string idcard, string birthdate)
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
                sql = "insert into usermoney values('" + guid + "','200','" + hotelid + "','0.00','0.00','0.00','0.00','0.00','0.00','" + DateTime.Now + "')";
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




        ////3
        //public List<string> getConsume(string customeruserid)
        //{
        //    List<string> list = new List<string>();
        //    Open();
        //    string sql = "select * from hotelhistory where customeruserid='" + customeruserid + "'";
        //    SqlCommand cmd = new SqlCommand(sql, sqlCon);
        //    SqlDataReader read = cmd.ExecuteReader();
        //    try
        //    {

        //        while (read.Read())
        //        {
        //            list.Add(read["hotelhistoryid"].ToString());
        //            list.Add(read["customeruserid"].ToString());
        //            list.Add(read["customername"].ToString());

        //            list.Add(read["address"].ToString());
        //            list.Add(read["hotelname"].ToString());
        //            list.Add(read["money"].ToString());
        //            list.Add(read["moneykezhu"].ToString());
        //            list.Add(read["returnkezhu"].ToString());
        //            list.Add(read["chuzhi"].ToString());
        //            list.Add(read["begindate"].ToString());
        //            //list.Add(read["促销活动"].ToString());
        //            //list.Add(read["价格说明"].ToString());

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


        //public bool updateUser(string type, string value, string username)
        //{
        //    Open();
        //    string sql = "update users set  " + type + "='" + value + "' where username='" + username + "'";
        //    SqlCommand cmd = new SqlCommand(sql, sqlCon);
        //    try
        //    {

        //        cmd.ExecuteNonQuery();

        //        return true;
        //    }
        //    catch
        //    {
        //        return false;
        //    }
        //    finally
        //    {
        //        Dispose();
        //    }
        //}

        ////4
        //public bool addHistory(string customeruserid, string customername, string hotelid, string hotelname, string money, string scorecustomer, string storedmoneycustomer)
        //{
        //    Open();
        //    string sql = "insert into hotelhistory (customeruserid,customername,hotelid,hotelname,money,scorecustomer,storedmoneycustomer,createtime) values('" + customeruserid + "','" + customername + "','" + hotelid + "','" + hotelname + "','" + money + "','" + scorecustomer + "','" + storedmoneycustomer + "','" + DateTime.Now + "')";
        //    SqlCommand cmd = new SqlCommand(sql, sqlCon);
        //    try
        //    {
        //        cmd.ExecuteNonQuery();
        //        return true;
        //    }
        //    catch
        //    {
        //        return false;
        //    }
        //    finally
        //    {
        //        Dispose();
        //    }
        //}

        //5



        public List<string> getRoomInfo(string hotelid)
        {
            List<string> list = new List<string>();
            Open();
            string sql = "select * from room where hotelid ='" + hotelid + "'";
            SqlCommand cmd = new SqlCommand(sql, sqlCon);
            SqlDataReader read = cmd.ExecuteReader();
            try
            {

                while (read.Read())
                {
                    list.Add(read["hotelid"].ToString());
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


        public List<string> getHotelInfoById(string hotelid)
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
                    list.Add(read["简介"].ToString());//7
                    list.Add(read["email"].ToString());
                    list.Add(read["店外照片"].ToString());  //9
                    list.Add(read["大堂照片"].ToString());  //10
                    list.Add(read["房间照片"].ToString());  //11
                    list.Add(read["state"].ToString());  //12
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




        //8
        //找出各个同系统的商家名字
        public List<string> getSamesytemname(string hoteltotalid)
        {
            List<string> list = new List<string>();
            Open();
            string sql = "select * from hotel where hoteltotalid='" + hoteltotalid + "'";
            SqlCommand cmd = new SqlCommand(sql, sqlCon);
            SqlDataReader read = cmd.ExecuteReader();
            try
            {
                while (read.Read())
                {
                    list.Add(read["hotelid"].ToString());
                    list.Add(read["hotelname"].ToString());
                    list.Add(read["address"].ToString());
                    //list.Add(read["hoteltotalid"].ToString());
                    //list.Add(read["hotelkind"].ToString());
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

        ////9
        //public bool updateUserKezhu(string uid, string money)
        //{
        //    Open();
        //    string sql = "update usermoney set scorexiaofei ='" + money + "' where uid='" + uid + "'";
        //    SqlCommand cmd = new SqlCommand(sql, sqlCon);
        //    try
        //    {

        //        cmd.ExecuteNonQuery();
        //        return true;
        //    }
        //    catch
        //    {
        //        return false;
        //    }
        //    finally
        //    {
        //        Dispose();
        //    }
        //}



        //更改后的获得用户各个商家的总储值分条
        public List<string> getUserChuzhi(string uid)
        {
            List<string> list = new List<string>();
            Open();
            string sql = "select * from userhotelmoney where uid='" + uid + "'";
            SqlCommand cmd = new SqlCommand(sql, sqlCon);
            SqlDataReader read = cmd.ExecuteReader();
            try
            {
                while (read.Read())
                {
                    list.Add(read["uid"].ToString());
                    list.Add(read["hotelid"].ToString());
                    list.Add(read["chuzhimoney"].ToString());
                    list.Add(read["hoteltotalid"].ToString());
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




        //更改后的得到某个店的储值消费记录
        public List<string> getChuzhihistory(string uid, string hotelid)
        {
            List<string> list = new List<string>();
            Open();
            string sql = "select * from ChuzhiHistory,hotel where ChuzhiHistory.uid='" + uid + "' and ChuzhiHistory.chuzhihotelid='" + hotelid + "' and ChuzhiHistory.hotelid=hotel.hotelid order by ChuzhiHistory.createtime desc";
            SqlCommand cmd = new SqlCommand(sql, sqlCon);
            SqlDataReader read = cmd.ExecuteReader();
            try
            {
                while (read.Read())
                {
                    list.Add(read["会员储值账户"].ToString());
                    list.Add(read["createtime"].ToString());
                    list.Add(read["hotelname"].ToString());
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

        //同系统外店储值排序
        public List<string> getChuzhiSort(string uid, string hoteltotalid)
        {
            List<string> list = new List<string>();
            Open();
            string sql = "select hotelid,(会员储值账户-会员已消费储值账户)as A ,会员已消费储值账户,chuzhihistoryid from ChuzhiHistory where uid='" + uid + "' and hoteltotalid='" + hoteltotalid + "' and 会员储值账户>会员已消费储值账户 order by createtime";
            SqlCommand cmd = new SqlCommand(sql, sqlCon);
            SqlDataReader read = cmd.ExecuteReader();
            try
            {

                while (read.Read())
                {
                    list.Add(read["hotelid"].ToString());
                    list.Add(read["A"].ToString());
                    list.Add(read["会员已消费储值账户"].ToString());
                    list.Add(read["chuzhihistoryid"].ToString());
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

        //同系统本店储值排序
        public List<string> getSameChuzhiSort(string uid, string hotelid)
        {
            List<string> list = new List<string>();
            Open();
            string sql = "select hotelid,(会员储值账户-会员已消费储值账户)as A ,会员已消费储值账户,chuzhihistoryid from ChuzhiHistory where uid='" + uid + "' and hotelid='" + hotelid + "' and 会员储值账户>会员已消费储值账户 order by createtime";
            SqlCommand cmd = new SqlCommand(sql, sqlCon);
            SqlDataReader read = cmd.ExecuteReader();
            try
            {

                while (read.Read())
                {

                    list.Add(read["hotelid"].ToString());
                    list.Add(read["A"].ToString());
                    list.Add(read["会员已消费储值账户"].ToString());
                    list.Add(read["chuzhihistoryid"].ToString());
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
                    list.Add(read["店外照片"].ToString());
                    list.Add(read["大堂照片"].ToString());
                    list.Add(read["房间照片"].ToString());
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

        //根据类型找商家
        public List<string> findShopsByKind(string city, string hotelkind)
        {
            List<string> list = new List<string>();
            Open();
            string sql = "select * from hotel where city like'" + '%' + city + '%' + "' and hotelkind ='" + hotelkind + "'";
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
                    list.Add(read["店外照片"].ToString());
                    list.Add(read["大堂照片"].ToString());
                    list.Add(read["房间照片"].ToString());
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



        //new用户进行储值总值的更改
        public bool updateAddChuzhi(string uid, string hotelid, string money, string hoteltotalid)
        {
            bool result = false;
            Open();
            string sql = "select * from userhotelmoney where uid='" + uid + "' and hotelid='" + hotelid + "'";
            SqlCommand com = new SqlCommand(sql, sqlCon);
            //SqlDataReader read = com.ExecuteReader();
            try
            {
                com.ExecuteNonQuery();
                sql = "select @@rowcount";
                com.CommandText = sql;
                if ((int)com.ExecuteScalar() == 0)
                {
                    sql = "insert into userhotelmoney values ('" + uid + "','" + hotelid + "','" + hoteltotalid + "','" + money + "')";
                    com.CommandText = sql;
                    com.ExecuteNonQuery();
                    result = true;

                }
                else
                {
                    sql = "update userhotelmoney set chuzhimoney='" + money + "' where uid='" + uid + "' and hotelid='" + hotelid + "' and hoteltotalid='" + hoteltotalid + "'";
                    com.CommandText = sql;
                    com.ExecuteNonQuery();
                    result = true;
                }

            }
            catch
            {
                result = false;
            }
            finally
            {
                //read.Close();
                Dispose();
            }
            return result;
        }

        //new查找当前商家是否有储值并显示储值
        public double getUserhotelmoney(string uid, string hotelid)
        {
            double chuzhi = 0;
            Open();
            string sql = "select chuzhimoney from userhotelmoney where uid='" + uid + "' and hotelid='" + hotelid + "'";
            SqlCommand com = new SqlCommand(sql, sqlCon);
            SqlDataReader read = com.ExecuteReader();
            try
            {
                if (read.Read())
                {
                    chuzhi = Convert.ToDouble(read["chuzhimoney"].ToString());
                }
            }
            catch
            { }
            finally
            {
                read.Close();
                Dispose();
            }
            return chuzhi;
        }

        //new商家同系统总值
        public string getUserSamesytemchuzhi(string uid, string hoteltotalid)
        {
            double samesystem = 0;
            Open();
            string sql = "select sum(chuzhimoney) as samesystem from userhotelmoney where uid='" + uid + "' and hoteltotalid='" + hoteltotalid + "'";
            SqlCommand com = new SqlCommand(sql, sqlCon);
            SqlDataReader read = com.ExecuteReader();
            try
            {
                if (read.Read())
                {
                    samesystem = Convert.ToDouble(read["samesystem"].ToString());
                }
            }
            catch
            { }
            finally
            {
                read.Close();
                Dispose();
            }
            return samesystem.ToString();
        }



        //充值
        public bool chuzhiRecharge(string uid, string hotelid, string hoteltotalid, string money, string serviceuserid)
        {
            bool result = false;
            bool result2 = false;
            double updatechuzhi;
            try
            {
                result = addChuzhihistory(uid, hotelid, hoteltotalid, hotelid, money, money, "0", "0", "-" + money, "0", "0", serviceuserid, "1");
                double nowchuzhi = getUserhotelmoney(uid, hotelid);
                updatechuzhi = nowchuzhi + Convert.ToDouble(money);
                result2 = updateAddChuzhi(uid, hotelid, updatechuzhi.ToString(), hoteltotalid);

            }
            catch
            {
                result = false;
                result2 = false;
            }
            finally
            {
            }
            return result && result2;
        }

        //酒店hotelname
        public List<string> hotelshopInfo(string hotelid)
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

                    list.Add(read["hotelname"].ToString());
                    list.Add(read["hoteltotalid"].ToString());
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



        //获取匹配码用户信息
        public List<string> getPayCodeUid(string code, string mobilephone)
        {
            List<string> uidList = new List<string>();
            Open();
            string sql = "select * from Fastpay where  code='" + code + "' and usable='1' and DateDiff(MINUTE,time,GetDate())<30 and mobilephone='" + mobilephone + "'";
            SqlCommand com = new SqlCommand(sql, sqlCon);
            SqlDataReader read = com.ExecuteReader();
            try
            {
                if (read.Read())
                {
                    uidList.Add(read["mobilephone"].ToString());
                }
            }
            catch
            { }
            finally
            {
                read.Close();
                Dispose();
            }
            return uidList;
        }

        public bool updatePayCode(string code)
        {
            bool result = false;
            Open();
            string sql = "update FastPay set usable='0' where code='" + code + "'";
            SqlCommand cmd = new SqlCommand(sql, sqlCon);
            try
            {
                cmd.ExecuteNonQuery();
                result = true;
            }
            catch
            {
                result = false;
            }
            finally
            {
                Dispose();

            }
            return result;

        }



        //储值消费
        public bool chuzhiConsume(string uid, string hotelid, string hoteltotalid, string money, string serviceuserid)
        {
            bool result1 = false;
            bool result2 = false;
            bool result3 = false;
            bool result4 = false;
            double updatechuzhi;
            double sumsystem = getUserhotelmoney(uid, hotelid);
            List<string> sameshop = getSameChuzhiSort(uid, hotelid);
            for (int i = 0; i < sameshop.Count / 4; i++)
            {
                if (Convert.ToDouble(money) <= Convert.ToDouble(sameshop[i * 4 + 1]))
                {
                    result1 = addChuzhihistory(uid, hotelid, hoteltotalid, hotelid, "0", "-" + money, "0", "0", money, "0", "0", serviceuserid, "2");
                    double nowchuzhi = getUserhotelmoney(uid, hotelid);
                    updatechuzhi = nowchuzhi - Convert.ToDouble(money);
                    result2 = updateAddChuzhi(uid, hotelid, updatechuzhi.ToString(), hoteltotalid);

                    double hasconsume = Convert.ToDouble(money) + Convert.ToDouble(sameshop[i * 4 + 2]);
                    result3 = updateChuzhihistory(uid, hotelid, hasconsume.ToString(), sameshop[i * 4 + 3]);
                    money = "0";
                    break;
                }
                else
                {
                    //添加储值记录储值
                    result1 = addChuzhihistory(uid, hotelid, hoteltotalid, sameshop[i * 4], "0", "-" + sameshop[i * 4 + 1], "0", "0", sameshop[i * 4 + 1], "0", "0", serviceuserid, "2");

                    //更改该商家的用户总储值
                    double nowchuzhi = getUserhotelmoney(uid, sameshop[i * 4]);

                    updatechuzhi = nowchuzhi - Convert.ToDouble(sameshop[i * 4 + 1]);

                    result2 = updateAddChuzhi(uid, sameshop[i * 4], updatechuzhi.ToString(), hoteltotalid);


                    //更改充值记录中的已消费值
                    double hasconsume = Convert.ToDouble(sameshop[i * 4 + 1]) + Convert.ToDouble(sameshop[i * 4 + 2]);

                    result3 = updateChuzhihistory(uid, sameshop[i * 4], hasconsume.ToString(), sameshop[i * 4 + 3]);

                    money = (Convert.ToDouble(money) - Convert.ToDouble(sameshop[i * 4 + 1])).ToString();
                }
            }
            if (Convert.ToDouble(money) > 0)
            {
                result4 = chuzhiSamesystem(uid, hotelid, hoteltotalid, money, serviceuserid);
            }
            return (result1 && result2 && result3) || result4;
        }


        // 其中外店的储值消费
        public bool chuzhiSamesystem(string uid, string hotelid, string hoteltotalid, string money, string serviceuserid)
        {
            bool result1 = false;
            bool result2 = false;
            bool result3 = false;
            double updatechuzhi;
            List<string> chuzhisamesystem = getChuzhiSort(uid, hoteltotalid);
            for (int i = 0; i < chuzhisamesystem.Count / 4; i++)
            {
                if (Convert.ToDouble(money) < Convert.ToDouble(chuzhisamesystem[i * 4 + 1]))
                {
                    //添加储值记录储值
                    result1 = addChuzhihistory(uid, hotelid, hoteltotalid, chuzhisamesystem[i * 4], "0", "-" + money, "0", "0", money, money, "-" + money, serviceuserid, "3");

                    //同系统储值清算更改
                    double serviceqingsuan = getChuzhiSamesystem(hotelid);
                    serviceqingsuan = serviceqingsuan + Convert.ToDouble(money);
                    updateChuzhiSamesystem(hotelid, hoteltotalid, serviceqingsuan.ToString());

                    double chuzhiqinsuan = getChuzhiSamesystem(chuzhisamesystem[i * 4]);
                    chuzhiqinsuan = chuzhiqinsuan - Convert.ToDouble(money);
                    updateChuzhiSamesystem(chuzhisamesystem[i * 4], hoteltotalid, chuzhiqinsuan.ToString());


                    //更改该商家的用户总储值
                    double nowchuzhi = getUserhotelmoney(uid, chuzhisamesystem[i * 4]);
                    updatechuzhi = nowchuzhi - Convert.ToDouble(money);
                    result2 = updateAddChuzhi(uid, chuzhisamesystem[i * 4], updatechuzhi.ToString(), hoteltotalid);


                    //更改充值记录中的已消费值
                    double hasconsume = Convert.ToDouble(money) + Convert.ToDouble(chuzhisamesystem[i * 4 + 2]);
                    result3 = updateChuzhihistory(uid, chuzhisamesystem[i * 4], hasconsume.ToString(), chuzhisamesystem[i * 4 + 3]);
                    break;
                }
                else
                {
                    //添加储值记录储值
                    result1 = addChuzhihistory(uid, hotelid, hoteltotalid, chuzhisamesystem[i * 4], "0", "-" + chuzhisamesystem[i * 4 + 1], "0", "0", chuzhisamesystem[i * 4 + 1], chuzhisamesystem[i * 4 + 1], "-" + chuzhisamesystem[i * 4 + 1], serviceuserid, "3");


                    //同系统储值清算更改
                    double serviceqingsuan = getChuzhiSamesystem(hotelid);
                    serviceqingsuan = serviceqingsuan + Convert.ToDouble(chuzhisamesystem[i * 4 + 1]);
                    updateChuzhiSamesystem(hotelid, hoteltotalid, serviceqingsuan.ToString());

                    double chuzhiqinsuan = getChuzhiSamesystem(chuzhisamesystem[i * 4]);
                    chuzhiqinsuan = chuzhiqinsuan - Convert.ToDouble(chuzhisamesystem[i * 4 + 1]);
                    updateChuzhiSamesystem(chuzhisamesystem[i * 4], hoteltotalid, chuzhiqinsuan.ToString());


                    //更改该商家的用户总储值
                    double nowchuzhi = getUserhotelmoney(uid, chuzhisamesystem[i * 4]);

                    updatechuzhi = nowchuzhi - Convert.ToDouble(chuzhisamesystem[i * 4 + 1]);

                    result2 = updateAddChuzhi(uid, chuzhisamesystem[i * 4], updatechuzhi.ToString(), hoteltotalid);


                    //更改充值记录中的已消费值
                    double hasconsume = Convert.ToDouble(chuzhisamesystem[i * 4 + 1]) + Convert.ToDouble(chuzhisamesystem[i * 4 + 2]);

                    result3 = updateChuzhihistory(uid, chuzhisamesystem[i * 4], hasconsume.ToString(), chuzhisamesystem[i * 4 + 3]);

                    money = (Convert.ToDouble(money) - Convert.ToDouble(chuzhisamesystem[i * 4 + 1])).ToString();
                }
            }
            return result1 && result2 && result3;
        }

        //更新同系统外店储值清算
        public bool updateChuzhiSamesystem(string hotelid, string hoteltotalid, string clearingmoney)
        {
            bool result = false;
            Open();
            string sql = "select * from hotelclearingmoney where hotelid='" + hotelid + "' and hoteltotalid='" + hoteltotalid + "'";
            SqlCommand com = new SqlCommand(sql, sqlCon);
            //SqlDataReader read = com.ExecuteReader();
            try
            {
                com.ExecuteNonQuery();
                sql = "select @@rowcount";
                com.CommandText = sql;
                if ((int)com.ExecuteScalar() == 0)
                {
                    sql = "insert into hotelclearingmoney values ('" + hotelid + "','" + hoteltotalid + "','" + clearingmoney + "','" + DateTime.Now + "')";
                    com.CommandText = sql;
                    com.ExecuteNonQuery();
                    result = true;

                }
                else
                {
                    sql = "update hotelclearingmoney set clearingmoney='" + clearingmoney + "' , clearingtime='" + DateTime.Now + "' where hotelid='" + hotelid + "' and hoteltotalid='" + hoteltotalid + "'";
                    com.CommandText = sql;
                    com.ExecuteNonQuery();
                    result = true;
                }

            }
            catch
            {
                result = false;
            }
            finally
            {
                //read.Close();
                Dispose();
            }
            return result;
        }

        //读取同系统外店储值清算
        public double getChuzhiSamesystem(string hotelid)
        {
            double chuzhi = 0;
            Open();
            string sql = "select clearingmoney from hotelclearingmoney where hotelid='" + hotelid + "'";
            SqlCommand com = new SqlCommand(sql, sqlCon);
            SqlDataReader read = com.ExecuteReader();
            try
            {
                if (read.Read())
                {
                    chuzhi = Convert.ToDouble(read["clearingmoney"].ToString());
                }
            }
            catch
            { }
            finally
            {
                read.Close();
                Dispose();
            }
            return chuzhi;
        }

        //读取储值记录中已经消费的值
        //  public double getHasconsume(string uid, string hotelid,)

        //public bool chuzhiConsume(string uid, string hotelid, string chuzhihotelid, string hoteltotalid, string money, string serviceuserid)
        //{
        //    bool result = false;
        //    bool result2 = false;
        //    if(hotelid==chuzhihotelid)
        //    try
        //    {
        //        if (hotelid == chuzhihotelid)
        //        {
        //            double hotelchuzhi = getUserhotelmoney(uid, chuzhihotelid);
        //            if(hotelchuzhi > Convert.ToDouble( money))
        //            result = addChuzhihistory(uid, hotelid, hoteltotalid, hotelid, money, money, "0", "0", "-" + money, "0", "0", serviceuserid, "1");
        //            result2 = updateAddChuzhi(uid, hotelid, money, hoteltotalid);
        //        }

        //    }
        //    catch
        //    {
        //        result = false;
        //        result2 = false;
        //    }
        //    finally
        //    {
        //    }
        //    return result && result2;
        //}



        //new添加储值记录
        public bool addChuzhihistory(string uid, string hotelid, string hoteltotalid, string chuzhihotelid, string 实收金额, string 会员储值账户, string 会员已消费储值账户, string 接待商家储值账户, string 储值商家储值账户, string 接待商家清算账户, string 储值商家清算账户, string serviceuserid, string consumetype)
        {
            try
            {
                Open();
                string count = DateTime.Now.ToString("yyyyMMddmmss");
                //日期+编号
                string orderidsql = String.Format("select chuzhihistoryid from ChuzhiHistory where chuzhihistoryid like '%" + DateTime.Now.ToString("yyyyMMdd") + "%' order by chuzhihistoryid desc");
                SqlCommand orderidcmd = new SqlCommand(orderidsql, sqlCon);
                SqlDataReader reader = orderidcmd.ExecuteReader();
                if (reader.Read())
                {
                    count = reader["chuzhihistoryid"].ToString();
                    Int64 bianhao = Convert.ToInt64(count) + 1;
                    count = bianhao.ToString();
                }
                else
                {
                    count = DateTime.Now.ToString("yyyyMMdd") + "1001";
                }
                reader.Close();

                string sql = "insert into ChuzhiHistory values('" + count + "','" + uid + "','" + hotelid + "','" + hoteltotalid + "','" + chuzhihotelid + "','" + 实收金额 + "','" + 会员储值账户 + "','" + 会员已消费储值账户 + "','" + 接待商家储值账户 + "','" + 储值商家储值账户 + "','" + 接待商家清算账户 + "','" + 储值商家清算账户 + "','" + DateTime.Now + "','" + serviceuserid + "','" + consumetype + "')";
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

        //修改储值记录
        public bool updateChuzhihistory(string uid, string hotelid, string money, string chuzhihistoryid)
        {
            try
            {
                Open();
                string sql = "update ChuzhiHistory set 会员已消费储值账户='" + money + "' where uid ='" + uid + "' and hotelid='" + hotelid + "' and chuzhihistoryid='" + chuzhihistoryid + "' ";
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

        //查找当前用户的推荐人
        public string getUserrecommend(string uid)
        {
            string name = "";
            Open();
            string sql = "select * from users where uid='" + uid + "'";
            SqlCommand com = new SqlCommand(sql, sqlCon);
            SqlDataReader read = com.ExecuteReader();
            try
            {
                if (read.Read())
                {
                    name = read["hotelid"].ToString();
                }
            }
            catch
            { }
            finally
            {
                Dispose();
            }
            return name;
        }

        //获取酒店的各种比例
        public List<string> getHotelrate(string hotelid)
        {
            List<string> list = new List<string>();
            Open();
            string sql = "select * from [hotel] where [hotelid]='" + hotelid + "'";
            SqlCommand cmd = new SqlCommand(sql, sqlCon);
            SqlDataReader read = cmd.ExecuteReader();
            try
            {
                while (read.Read())
                {

                    list.Add(read["比例客主"].ToString());
                    list.Add(read["比例会员积分"].ToString());
                    list.Add(read["比例发卡商家"].ToString());
                    list.Add(read["比例代理"].ToString());
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

        //判断两点是否为同系统店
        public bool isSameSystem(string userhotelid, string hotelid)
        {
            Open();
            try
            {
                string userhoteltotalid = "";
                string hoteltotalid = "";
                string usersql = "select * from hotel where hotelid='" + userhotelid + "'";
                SqlCommand usercom = new SqlCommand(usersql, sqlCon);
                SqlDataReader userread = usercom.ExecuteReader();
                if (userread.Read())
                {
                    userhoteltotalid = userread["hoteltotalid"].ToString();
                }
                userread.Close();

                string sql = "select * from hotel where hotelid='" + hotelid + "'";
                SqlCommand com = new SqlCommand(sql, sqlCon);
                SqlDataReader read = com.ExecuteReader();
                if (read.Read())
                {
                    hoteltotalid = read["hoteltotalid"].ToString();
                }
                read.Close();

                if (userhoteltotalid == hoteltotalid)
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

        //客主币消费
        public bool getKezhuconsum(string uid, string hotelid, string money, string serviceuserid)
        {
            bool result = true;
            bool result1 = true;
            bool result2 = true;
            bool result3 = true;
            bool result4 = true;
            bool result5 = true;

            string fanhuimoney = money;

            List<bool> trueorfalse = new List<bool>();
            Int64 bianhao = Convert.ToInt64(getNumbermax());
            List<string> userkezhu = getUserKezhuSum(uid);
            List<string> rates = new List<string>();
            rates = getHotelrate(hotelid);


            List<string> userinfo = new List<string>();
            userinfo = getUserKezhu(uid);


            for (int i = 0; i < userkezhu.Count / 4; i++)
            {

                if (Convert.ToDouble(money) <= Convert.ToDouble(userkezhu[i * 4 + 1]))
                {
                    if (userkezhu[i * 4].Trim() == hotelid.Trim())
                    {
                        result1 = sameshopKezhu(bianhao.ToString(), uid, hotelid, money, serviceuserid);




                        trueorfalse.Add(result1);
                        trueorfalse.Add(result2);
                        trueorfalse.Add(result3);
                    }
                    else if (isSameSystem(userkezhu[i * 4].Trim(), hotelid.Trim()))
                    {
                        result1 = samesystemKezhu(bianhao.ToString(), uid, hotelid, money, userkezhu[i * 4], serviceuserid);

                        //同系统清算
                        double jiedaisamesystem = getChuzhiSamesystem(hotelid);
                        double jifensamesystem = getChuzhiSamesystem(userkezhu[i * 4]);

                        string tuijiantotalid = getHoteltotalid(userkezhu[i * 4]);
                        string hoteltotalid = getHoteltotalid(hotelid);

                        jiedaisamesystem = jiedaisamesystem + Convert.ToDouble(money);
                        jifensamesystem = jifensamesystem - Convert.ToDouble(money);

                        result4 = updateChuzhiSamesystem(hotelid, hoteltotalid, jiedaisamesystem.ToString());
                        result5 = updateChuzhiSamesystem(userkezhu[i * 4], tuijiantotalid, jifensamesystem.ToString());

                        trueorfalse.Add(result1);
                        trueorfalse.Add(result2);
                        trueorfalse.Add(result3);
                        trueorfalse.Add(result4);
                        trueorfalse.Add(result5);
                    }
                    else if (!isSameSystem(userkezhu[i * 4].Trim(), hotelid.Trim()))
                    {
                        result1 = ohtershopKezhu(bianhao.ToString(), uid, hotelid, money, userkezhu[i * 4], serviceuserid, rates[2], rates[0], rates[3]);

                        //商家客主清算
                        double jiedaikezhu = getKezhuSamesystem(hotelid);
                        double jifenkezhu = getKezhuSamesystem(userkezhu[i * 4]);

                        string tuijiantotalid = getHoteltotalid(userkezhu[i * 4]);
                        string hoteltotalid = getHoteltotalid(hotelid);

                        jiedaikezhu = jiedaikezhu + Convert.ToDouble(money) * (1 - Convert.ToDouble(rates[2]) - Convert.ToDouble(rates[0]) - Convert.ToDouble(rates[3]));
                        jifenkezhu = jifenkezhu - Convert.ToDouble(money);

                        result2 = updateKezhuSamesystem(hotelid, hoteltotalid, jiedaikezhu.ToString());
                        result3 = updateKezhuSamesystem(userkezhu[i * 4], tuijiantotalid, jifenkezhu.ToString());

                        //客主清算总值
                        double Kezhukezhu = getKezhuSamesystem("100");
                        Kezhukezhu = Kezhukezhu + Convert.ToDouble(money) * (Convert.ToDouble(rates[2]) + Convert.ToDouble(rates[0]) + Convert.ToDouble(rates[3]));
                        updateKezhuSamesystem("100", "100", Kezhukezhu.ToString());

                        trueorfalse.Add(result1);
                        trueorfalse.Add(result2);
                        trueorfalse.Add(result3);
                    }

                    //商家更新客主币总额
                    double jiedaiall = getSellerkezhu(userkezhu[i * 4]);
                    jiedaiall = jiedaiall + Convert.ToDouble(money);
                    result3 = updateSellerkezhu(userkezhu[i * 4], jiedaiall);

                    //更新以前的积分记录
                    double hasconsume = Convert.ToDouble(money) + Convert.ToDouble(userkezhu[i * 4 + 2]);
                    result1 = updateKezhuhistory(uid, userkezhu[i * 4], hasconsume.ToString(), userkezhu[i * 4 + 3]);
                    trueorfalse.Add(result1);
                    money = "0";
                    bianhao = bianhao + 1;
                    break;
                }
                else
                {
                    if (userkezhu[i * 4].Trim() == hotelid.Trim())
                    {
                        result1 = sameshopKezhu(bianhao.ToString(), uid, hotelid, userkezhu[i * 4 + 1], serviceuserid);

                        trueorfalse.Add(result1);
                    }
                    else if (isSameSystem(userkezhu[i * 4].Trim(), hotelid.Trim()))
                    {
                        result1 = samesystemKezhu(bianhao.ToString(), uid, hotelid, userkezhu[i * 4 + 1], userkezhu[i * 4], serviceuserid);

                        //同系统清算
                        double jiedaisamesystem = getChuzhiSamesystem(hotelid);
                        double jifensamesystem = getChuzhiSamesystem(userkezhu[i * 4]);

                        string tuijiantotalid = getHoteltotalid(userkezhu[i * 4]);
                        string hoteltotalid = getHoteltotalid(hotelid);

                        jiedaisamesystem = jiedaisamesystem + Convert.ToDouble(userkezhu[i * 4 + 1]);
                        jifensamesystem = jifensamesystem - Convert.ToDouble(userkezhu[i * 4 + 1]);

                        result3 = updateChuzhiSamesystem(hotelid, hoteltotalid, jiedaisamesystem.ToString());
                        result4 = updateChuzhiSamesystem(userkezhu[i * 4], tuijiantotalid, jifensamesystem.ToString());

                        trueorfalse.Add(result1);
                        trueorfalse.Add(result2);
                        trueorfalse.Add(result3);
                        trueorfalse.Add(result4);
                    }
                    else if (!isSameSystem(userkezhu[i * 4].Trim(), hotelid.Trim()))
                    {
                        result1 = ohtershopKezhu(bianhao.ToString(), uid, hotelid, userkezhu[i * 4 + 1], userkezhu[i * 4], serviceuserid, rates[2], rates[0], rates[3]);

                        //客主清算
                        double jiedaikezhu = getKezhuSamesystem(hotelid);
                        double jifenkezhu = getKezhuSamesystem(userkezhu[i * 4]);

                        string tuijiantotalid = getHoteltotalid(userkezhu[i * 4]);
                        string hoteltotalid = getHoteltotalid(hotelid);

                        jiedaikezhu = jiedaikezhu + Convert.ToDouble(userkezhu[i * 4 + 1]) * (1 - Convert.ToDouble(rates[2]) - Convert.ToDouble(rates[0]) - Convert.ToDouble(rates[3]));
                        jifenkezhu = jifenkezhu - Convert.ToDouble(userkezhu[i * 4 + 1]);

                        result2 = updateKezhuSamesystem(hotelid, hoteltotalid, jiedaikezhu.ToString());
                        result3 = updateKezhuSamesystem(userkezhu[i * 4], tuijiantotalid, jifenkezhu.ToString());

                        //客主清算总值
                        double Kezhukezhu = getKezhuSamesystem("100");
                        Kezhukezhu = Kezhukezhu + Convert.ToDouble(userkezhu[i * 4 + 1]) * (Convert.ToDouble(rates[2]) + Convert.ToDouble(rates[0]) + Convert.ToDouble(rates[3]));
                        updateKezhuSamesystem("100", "100", Kezhukezhu.ToString());


                        trueorfalse.Add(result1);
                        trueorfalse.Add(result2);
                        trueorfalse.Add(result3);
                    }
                    //商家更新客主币总额
                    double jiedaiall = getSellerkezhu(userkezhu[i * 4]);
                    jiedaiall = jiedaiall + Convert.ToDouble(userkezhu[i * 4 + 1]);
                    result3 = updateSellerkezhu(userkezhu[i * 4], jiedaiall);


                    //更新以前的积分记录
                    double hasconsume = Convert.ToDouble(userkezhu[i * 4 + 1]) + Convert.ToDouble(userkezhu[i * 4 + 2]);
                    result1 = updateKezhuhistory(uid, userkezhu[i * 4], hasconsume.ToString(), userkezhu[i * 4 + 3]);
                    trueorfalse.Add(result1);
                    bianhao = bianhao + 1;


                    money = (Convert.ToDouble(money) - Convert.ToDouble(userkezhu[i * 4 + 1])).ToString();

                }
            }
            double cashreturn = 0;
            for (int i = 0; i < userkezhu.Count / 4; i++)
            {
                cashreturn = cashreturn + Convert.ToDouble(userkezhu[i * 4 + 1]);
            }
            double remainkezhu = Convert.ToDouble(fanhuimoney) - cashreturn;
            //更新用户客主币
            List<double> userwholekezhu = new List<double>();
            userwholekezhu = getAllkezhu(uid);

            double nowkezhumoney = userwholekezhu[0];
            double 商家返客主币 = userwholekezhu[1];
            double 邀请返客主币 = userwholekezhu[2];
            double 已消费客主币 = userwholekezhu[3];
            double 已消耗邀请返客主币 = userwholekezhu[4];


            nowkezhumoney = nowkezhumoney - Convert.ToDouble(fanhuimoney);
            已消费客主币 = 已消费客主币 + Convert.ToDouble(fanhuimoney);
            if (remainkezhu > 0)
            {
                已消耗邀请返客主币 = 已消耗邀请返客主币 + Convert.ToDouble(remainkezhu);
            }
            result2 = updateAllkezhu(uid, nowkezhumoney, 商家返客主币, 邀请返客主币, 已消费客主币, 已消耗邀请返客主币);
            //List<double> userallkezhu = getAllkezhu(uid);

            if (remainkezhu > 0)
            {
                kezhushopKezhu(bianhao.ToString(), uid, hotelid, remainkezhu.ToString(), serviceuserid);
                
                bianhao=bianhao+1;

                //商家更新客主币总额
                double jiedaiall = getSellerkezhu("100");
                jiedaiall = jiedaiall + Convert.ToDouble(money);
                result3 = updateSellerkezhu("100", jiedaiall);


                //客主清算
                double jiedaikezhu = getKezhuSamesystem(hotelid);
                double kezhukezhu = getKezhuSamesystem("100");

                string hoteltotalid = getHoteltotalid(hotelid);

                jiedaikezhu = jiedaikezhu + Convert.ToDouble(money) * (1 - Convert.ToDouble(rates[2]) - Convert.ToDouble(rates[0]) - Convert.ToDouble(rates[3]));
                kezhukezhu = kezhukezhu - Convert.ToDouble(money);

                result2 = updateKezhuSamesystem(hotelid, hoteltotalid, jiedaikezhu.ToString());
                result3 = updateKezhuSamesystem("100", "100", kezhukezhu.ToString());
            }
            //返回客主币
            double returnkezhu = Convert.ToDouble(fanhuimoney) * Convert.ToDouble(rates[1]) * -1;
            result1 = returnKezhu(bianhao.ToString(), uid, hotelid, returnkezhu.ToString(), serviceuserid);

            bianhao = bianhao + 1;
            //更新用户客主币
            userwholekezhu = getAllkezhu(uid);

            nowkezhumoney = userwholekezhu[0];
            商家返客主币 = userwholekezhu[1];
            邀请返客主币 = userwholekezhu[2];
            已消费客主币 = userwholekezhu[3];
            已消耗邀请返客主币 = userwholekezhu[4];


            nowkezhumoney = nowkezhumoney - returnkezhu;
            商家返客主币 = 商家返客主币 - returnkezhu;

            result2 = updateAllkezhu(uid, nowkezhumoney, 商家返客主币, 邀请返客主币, 已消费客主币, 已消耗邀请返客主币);

            //记录此次客主币消费总值记录
            kezhuallKezhu(bianhao.ToString(), uid, hotelid, fanhuimoney, serviceuserid);

            trueorfalse.Add(result1);
            trueorfalse.Add(result2);

            for (int i = 0; i < trueorfalse.Count; i++)
            {
                if (!trueorfalse[i])
                    result = false;
            }

            return result;
        }


        //获得客主币各项信息
        public List<string> getUserKezhuItem(string uid)
        {
            List<string> list = new List<string>();
            Open();
            //string sqlSel = string.Format("select * from usermoneyhotel,UserInfo where usermoneyhotel.uid=UserInfo.Id and UserInfo.username='{0}'",username);
            string sql = "select * from usermoney where uid='" + uid + "'";
            SqlCommand cmd = new SqlCommand(sql, sqlCon);
            SqlDataReader read = cmd.ExecuteReader();
            try
            {

                if (read.Read())
                {
                    list.Add(read["kezhumoney"].ToString());
                    list.Add(read["商家返客主币"].ToString());
                    list.Add(read["邀请返客主币"].ToString());
                    list.Add(read["已消费客主币"].ToString());
                }
                else
                {
                    list.Add("0.00");
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


        //修改客主币记录
        public bool updateKezhuhistory(string uid, string hotelid, string money, string hotelhistoryid)
        {
            try
            {
                Open();
                string sql = "update HotelHistory set 已消费积分='" + money + "' where uid ='" + uid + "' and hotelid='" + hotelid + "' and hotelhistoryid='" + hotelhistoryid + "' ";
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


        //客主币消费返回客主币
        public bool returnKezhu(string hotelhistoryid, string uid, string hotelid, string money, string serviceuserid)
        {
            Open();
            double cash = Convert.ToDouble(money);
            string sql = "insert into HotelHistory values ('" + hotelhistoryid + "','" + uid + "','" + hotelid + "','0','" + (cash * -1) + "','0','" + cash + "','" + hotelid + "','0','0','0','0','0','0','0','0','0','0','" + serviceuserid + "','" + DateTime.Now + "','3')";
            SqlCommand com = new SqlCommand(sql, sqlCon);
            try
            {
                com.ExecuteNonQuery();
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

        //客主币同店消费
        public bool sameshopKezhu(string hotelhistoryid, string uid, string hotelid, string money, string serviceuserid)
        {
            Open();
            double cash = Convert.ToDouble(money);
            string sql = "insert into HotelHistory values ('" + hotelhistoryid + "','" + uid + "','" + hotelid + "','0','" + (cash * -1) + "','0','" + cash + "','" + hotelid + "','0','0','0','0','0','0','0','0','0','0','" + serviceuserid + "','" + DateTime.Now + "','2')";
            SqlCommand com = new SqlCommand(sql, sqlCon);
            try
            {
                com.ExecuteNonQuery();
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

        //客主币同系统消费
        public bool samesystemKezhu(string hotelhistoryid, string uid, string hotelid, string money, string jifenhotelid, string serviceuserid)
        {
            Open();
            double cash = Convert.ToDouble(money);
            string sql = "insert into HotelHistory values ('" + hotelhistoryid + "','" + uid + "','" + hotelid + "','0','" + (cash * -1) + "','0','0','" + jifenhotelid + "','" + cash + "','" + (cash * -1) + "','0','" + cash + "','0','0','0','0','0','0','" + serviceuserid + "','" + DateTime.Now + "','2')";
            SqlCommand com = new SqlCommand(sql, sqlCon);
            try
            {
                com.ExecuteNonQuery();
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

        //客主币外店消费
        public bool ohtershopKezhu(string hotelhistoryid, string uid, string hotelid, string money, string jifenhotelid, string serviceuserid, string fakarate, string kezhurate, string agentrate)
        {
            Open();
            double cash = Convert.ToDouble(money);
            double faka = Convert.ToDouble(fakarate);
            double kezhu = Convert.ToDouble(kezhurate);
            double agent = Convert.ToDouble(agentrate);
            string sql = "insert into HotelHistory values ('" + hotelhistoryid + "','" + uid + "','" + hotelid + "','0','" + (cash * -1) + "','0','0','" + jifenhotelid + "','" + cash + "','0','" + (cash * -1) + "','0','" + (cash * (1 - faka - kezhu - agent)) + "','0','0','0','" + (cash * (faka + kezhu + agent)) + "','0','" + serviceuserid + "','" + DateTime.Now + "','2')";
            SqlCommand com = new SqlCommand(sql, sqlCon);
            try
            {
                com.ExecuteNonQuery();
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

        //用户使用客主币给的积分进行消费
        public bool kezhushopKezhu(string hotelhistoryid, string uid, string hotelid, string money, string serviceuserid)
        {
            Open();
            double cash = Convert.ToDouble(money);
            string sql = "insert into HotelHistory values ('" + hotelhistoryid + "','" + uid + "','" + hotelid + "','0','" + (cash * -1) + "','0','0','100','" + cash + "','0','0','0','" + (cash * 0.97) + "','0','0','0','" + (cash * -0.97) + "','0','" + serviceuserid + "','" + DateTime.Now + "','2')";
            SqlCommand com = new SqlCommand(sql, sqlCon);
            try
            {
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

        //客主币消费总值记录信息
        public bool kezhuallKezhu(string hotelhistoryid, string uid, string hotelid, string money, string serviceuserid)
        {
            Open();
            double cash = Convert.ToDouble(money);
            string sql = "insert into HotelHistory values ('" + hotelhistoryid + "','" + uid + "','" + hotelid + "','"+cash+"','0','0','0','0','0','0','0','0','0','0','0','0','0','0','" + serviceuserid + "','" + DateTime.Now + "','9')";
            SqlCommand com = new SqlCommand(sql, sqlCon);
            try
            {
                com.ExecuteNonQuery();
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


        //现金消费
        public bool getCashconsume(string uid, string hotelid, string money, string serviceuserid)
        {
            bool result = false;
            Int64 bianhao = Convert.ToInt64(getNumbermax());
            List<string> rates = new List<string>();
            rates = getHotelrate(hotelid);
            string tuijian = getUserrecommend(uid);

            List<string> userinfo = new List<string>();
            userinfo = getUserKezhu(uid);

            string tuijiantotalid = getHoteltotalid(tuijian);
            string hoteltotalid = getHoteltotalid(hotelid);

            if (tuijian.Length == 11)
            {
                string tuijianuid = userInfor(tuijian)[0];
                result = recommendCash(bianhao.ToString(), uid, hotelid, money, rates[1], "0.01", rates[0], rates[3], serviceuserid);
                //更新用户客主币
                List<double> userwholekezhu = new List<double>();
                userwholekezhu = getAllkezhu(uid);

                double nowkezhumoney = userwholekezhu[0];
                double 商家返客主币 = userwholekezhu[1];
                double 邀请返客主币 = userwholekezhu[2];
                double 已消费客主币 = userwholekezhu[3];
                double 已消耗邀请返客主币 = userwholekezhu[4];


                nowkezhumoney = nowkezhumoney + Convert.ToDouble(money) * Convert.ToDouble(rates[1]);
                商家返客主币 = 商家返客主币 + Convert.ToDouble(money) * Convert.ToDouble(rates[1]);
                updateAllkezhu(uid, nowkezhumoney, 商家返客主币, 邀请返客主币, 已消费客主币, 已消耗邀请返客主币);
                //更新推荐人账号   

                List<double> tuijianwholekezhu = new List<double>();
                tuijianwholekezhu = getAllkezhu(tuijianuid);

                double Tnowkezhumoney = tuijianwholekezhu[0];
                double T商家返客主币 = tuijianwholekezhu[1];
                double T邀请返客主币 = tuijianwholekezhu[2];
                double T已消费客主币 = tuijianwholekezhu[3];
                double T已消耗邀请返客主币 = tuijianwholekezhu[4];

                Tnowkezhumoney = Tnowkezhumoney + Convert.ToDouble(money) * 0.01;
                T邀请返客主币 = T邀请返客主币 + Convert.ToDouble(money) * 0.01;
                updateAllkezhu(tuijianuid, Tnowkezhumoney, T商家返客主币, T邀请返客主币, T已消费客主币, T已消耗邀请返客主币);

                //商家以及客主更新客主币总额
                double jiedaiall = getSellerkezhu(hotelid);
                jiedaiall = jiedaiall - Convert.ToDouble(money) * Convert.ToDouble(rates[1]);
                updateSellerkezhu(hotelid, jiedaiall);

                double kezhuall = getSellerkezhu("100");
                kezhuall = kezhuall - Convert.ToDouble(money) * 0.01;
                updateSellerkezhu("100", kezhuall);

                //接待客主清算
                double jiedaikezhu = getKezhuSamesystem(hotelid);

                jiedaikezhu = jiedaikezhu - Convert.ToDouble(money) * (Convert.ToDouble(rates[2]) + Convert.ToDouble(rates[0]) + Convert.ToDouble(rates[3]));

                updateKezhuSamesystem(hotelid, hoteltotalid, jiedaikezhu.ToString());

                //客主清算总值
                double Kezhukezhu = getKezhuSamesystem("100");
                Kezhukezhu = Kezhukezhu + Convert.ToDouble(money) * (Convert.ToDouble(rates[0]) + 0.01);
                updateKezhuSamesystem("100", "100", Kezhukezhu.ToString());

                //代理清算
                //double agentsum = Convert.ToDouble(getHotelagent(hotelid));
                //agentsum = agentsum + Convert.ToDouble(money) * Convert.ToDouble(rates[3]);
                //updateHotelagent(hotelid, agentsum.ToString());

            }
            else if (tuijian.Trim() == hotelid.Trim())
            {
                result = sameshopCash(bianhao.ToString(), uid, hotelid, money, rates[1], serviceuserid);
                //更新用户客主币
                List<double> userwholekezhu = new List<double>();
                userwholekezhu = getAllkezhu(uid);

                double nowkezhumoney = userwholekezhu[0];
                double 商家返客主币 = userwholekezhu[1];
                double 邀请返客主币 = userwholekezhu[2];
                double 已消费客主币 = userwholekezhu[3];
                double 已消耗邀请返客主币 = userwholekezhu[4];


                nowkezhumoney = nowkezhumoney + Convert.ToDouble(money) * Convert.ToDouble(rates[1]);
                商家返客主币 = 商家返客主币 + Convert.ToDouble(money) * Convert.ToDouble(rates[1]);
                updateAllkezhu(uid, nowkezhumoney, 商家返客主币, 邀请返客主币, 已消费客主币, 已消耗邀请返客主币);


                //商家更新客主币总额
                double jiedaiall = getSellerkezhu(hotelid);
                jiedaiall = jiedaiall - Convert.ToDouble(money) * Convert.ToDouble(rates[1]);
                updateSellerkezhu(hotelid, jiedaiall);
            }
            else if (tuijiantotalid.Trim() == hoteltotalid.Trim())
            {
                result = samesystemCash(bianhao.ToString(), uid, hotelid, money, rates[1], rates[2], serviceuserid);
                //更新用户客主币
                List<double> userwholekezhu = new List<double>();
                userwholekezhu = getAllkezhu(uid);

                double nowkezhumoney = userwholekezhu[0];
                double 商家返客主币 = userwholekezhu[1];
                double 邀请返客主币 = userwholekezhu[2];
                double 已消费客主币 = userwholekezhu[3];
                double 已消耗邀请返客主币 = userwholekezhu[4];


                nowkezhumoney = nowkezhumoney + Convert.ToDouble(money) * Convert.ToDouble(rates[1]);
                商家返客主币 = 商家返客主币 + Convert.ToDouble(money) * Convert.ToDouble(rates[1]);
                updateAllkezhu(uid, nowkezhumoney, 商家返客主币, 邀请返客主币, 已消费客主币, 已消耗邀请返客主币);

                //商家更新客主币总额
                double jiedaiall = getSellerkezhu(hotelid);
                jiedaiall = jiedaiall - Convert.ToDouble(money) * Convert.ToDouble(rates[1]);
                updateSellerkezhu(hotelid, jiedaiall);

                //同系统清算
                double jiedaikezhu = getChuzhiSamesystem(hotelid);
                double tuijiankezhu = getChuzhiSamesystem(tuijian);

                jiedaikezhu = jiedaikezhu - Convert.ToDouble(money) * Convert.ToDouble(rates[2]);
                tuijiankezhu = tuijiankezhu + Convert.ToDouble(money) * Convert.ToDouble(rates[2]);

                updateChuzhiSamesystem(hotelid, hoteltotalid, jiedaikezhu.ToString());
                updateChuzhiSamesystem(tuijian, tuijiantotalid, tuijiankezhu.ToString());
            }
            else if (!(tuijiantotalid.Trim() == hoteltotalid.Trim()))
            {
                result = othershopCash(bianhao.ToString(), uid, hotelid, money, rates[1], rates[2], rates[0], rates[3], serviceuserid);
                //更新用户客主币
                List<double> userwholekezhu = new List<double>();
                userwholekezhu = getAllkezhu(uid);

                double nowkezhumoney = userwholekezhu[0];
                double 商家返客主币 = userwholekezhu[1];
                double 邀请返客主币 = userwholekezhu[2];
                double 已消费客主币 = userwholekezhu[3];
                double 已消耗邀请返客主币 = userwholekezhu[4];


                nowkezhumoney = nowkezhumoney + Convert.ToDouble(money) * Convert.ToDouble(rates[1]);
                商家返客主币 = 商家返客主币 + Convert.ToDouble(money) * Convert.ToDouble(rates[1]);
                updateAllkezhu(uid, nowkezhumoney, 商家返客主币, 邀请返客主币, 已消费客主币, 已消耗邀请返客主币);


                //商家更新客主币总额
                double jiedaiall = getSellerkezhu(hotelid);
                jiedaiall = jiedaiall - Convert.ToDouble(money) * Convert.ToDouble(rates[1]);
                updateSellerkezhu(hotelid, jiedaiall);

                //商家客主清算
                double jiedaikezhu = getKezhuSamesystem(hotelid);
                double fakakezhu = getKezhuSamesystem(tuijian);

                jiedaikezhu = jiedaikezhu - Convert.ToDouble(money) * (Convert.ToDouble(rates[2]) + Convert.ToDouble(rates[0]) + Convert.ToDouble(rates[3]));
                fakakezhu = fakakezhu + Convert.ToDouble(money) * Convert.ToDouble(rates[2]);

                updateKezhuSamesystem(hotelid, hoteltotalid, jiedaikezhu.ToString());
                updateKezhuSamesystem(tuijian, tuijiantotalid, fakakezhu.ToString());

                //客主清算总值
                double Kezhukezhu = getKezhuSamesystem("100");
                Kezhukezhu = Kezhukezhu + Convert.ToDouble(money) * Convert.ToDouble(rates[0]);
                updateKezhuSamesystem("100", "100", Kezhukezhu.ToString());

                //代理清算
                //double agentsum = Convert.ToDouble(getHotelagent(hotelid));
                //agentsum = agentsum + Convert.ToDouble(money) * Convert.ToDouble(rates[3]);
                //updateHotelagent(hotelid, agentsum.ToString());
            }
            return result;

        }

        //现金本店消费
        public bool sameshopCash(string hotelhistoryid, string uid, string hotelid, string money, string receiverate, string serviceuserid)
        {
            Open();
            double cash = Convert.ToDouble(money);
            double cashrate = Convert.ToDouble(receiverate);
            string sql = "insert into HotelHistory values ('" + hotelhistoryid + "','" + uid + "','" + hotelid + "','" + money + "','" + (cash * cashrate) + "','0','" + (cash * cashrate * -1) + "','" + hotelid + "','0','0','0','0','0','0','0','0','0','0','" + serviceuserid + "','" + DateTime.Now + "','1')";
            SqlCommand com = new SqlCommand(sql, sqlCon);
            try
            {
                com.ExecuteNonQuery();
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

        //现金同系统消费
        public bool samesystemCash(string hotelhistoryid, string uid, string hotelid, string money, string receiverate, string samesystemrate, string serviceuserid)
        {
            Open();
            double cash = Convert.ToDouble(money);
            double cashrate = Convert.ToDouble(receiverate);
            double samerate = Convert.ToDouble(samesystemrate);
            string sql = "insert into HotelHistory values('" + hotelhistoryid + "','" + uid + "','" + hotelid + "','" + money + "','" + (cash * cashrate) + "','0','" + (cash * cashrate * -1) + "','" + hotelid + "','0','0','0','" + (cash * samerate * -1) + "','0','" + (cash * samerate) + "','0','0','0','0','" + serviceuserid + "','" + DateTime.Now + "','1')";
            SqlCommand com = new SqlCommand(sql, sqlCon);
            try
            {
                com.ExecuteNonQuery();
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

        //现金外系统(店)
        public bool othershopCash(string hotelhistoryid, string uid, string hotelid, string money, string receiverate, string cardrate, string kezhurate, string argentrate, string serviceuserid)
        {
            Open();
            double cash = Convert.ToDouble(money);
            double cashrate = Convert.ToDouble(receiverate);
            double fakarate = Convert.ToDouble(cardrate);
            double kezhubirate = Convert.ToDouble(kezhurate);
            double dailirate = Convert.ToDouble(argentrate);
            double sumkezhu = (fakarate + kezhubirate + dailirate);
            string sql = "insert into HotelHistory values('" + hotelhistoryid + "','" + uid + "','" + hotelid + "','" + money + "','" + (cash * cashrate) + "','0','" + (cash * cashrate * -1) + "','" + hotelid + "','0','0','0','0','" + (cash * sumkezhu * -1) + "','0','" + (cash * fakarate) + "','0','" + (cash * kezhubirate) + "','" + (cash * dailirate) + "','" + serviceuserid + "','" + DateTime.Now + "','1')";
            SqlCommand com = new SqlCommand(sql, sqlCon);
            try
            {
                com.ExecuteNonQuery();
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

        //现金外系统推荐人
        public bool recommendCash(string hotelhistoryid, string uid, string hotelid, string money, string receiverate, string recommendrate, string kezhurate, string argentrate, string serviceuserid)
        {
            {
                Open();
                double cash = Convert.ToDouble(money);
                double cashrate = Convert.ToDouble(receiverate);
                double tuijianrate = Convert.ToDouble(recommendrate);
                double kezhubirate = Convert.ToDouble(kezhurate);
                double dailirate = Convert.ToDouble(argentrate);
                double sumkezhu = (tuijianrate + kezhubirate + dailirate);
                string sql = "insert into HotelHistory values('" + hotelhistoryid + "','" + uid + "','" + hotelid + "','" + money + "','" + (cash * cashrate) + "','0','" + (cash * cashrate * -1) + "','" + hotelid + "','0','0','0','0','" + (cash * sumkezhu * -1) + "','0','0','" + (cash * tuijianrate) + "','" + (cash * (kezhubirate + tuijianrate)) + "','" + (cash * dailirate) + "','" + serviceuserid + "','" + DateTime.Now + "','1')";
                SqlCommand com = new SqlCommand(sql, sqlCon);
                try
                {
                    com.ExecuteNonQuery();
                    //sql = "insert into HotelHistory values ('" + (Convert.ToDouble(hotelhistoryid) + 1).ToString() + "','" + recommenduid + "','100','0','" + (cash * tuijianrate) + "','0','" + (cash * tuijianrate * -1) + "','kezhu','0','0','0','0','0','0','0','0','0','0','" + serviceuserid + "','" + DateTime.Now + "','1')";
                    //com.CommandText = sql;
                    //com.ExecuteNonQuery();
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

        //获取商家的客主币账户总值
        public double getSellerkezhu(string hotelid)
        {
            double sellerkezhu = 0;
            Open();
            string sql = "select kezhumoney from hotel where hotelid='" + hotelid + "'";
            SqlCommand com = new SqlCommand(sql, sqlCon);
            SqlDataReader read = com.ExecuteReader();
            try
            {
                if (read.Read())
                {
                    sellerkezhu = Convert.ToDouble(read["kezhumoney"].ToString());
                }
            }
            catch
            { }
            finally
            {
                read.Close();
                Dispose();
            }
            return sellerkezhu;
        }

        //更新商家的客主币账户总值
        public bool updateSellerkezhu(string hotelid, double kezhumoney)
        {
            Open();
            string sql = "update hotel set kezhumoney='" + kezhumoney + "'  where hotelid='" + hotelid + "'";
            SqlCommand com = new SqlCommand(sql, sqlCon);
            try
            {
                com.ExecuteNonQuery();
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

        //获得用户的现金返客主币,已消费现金返客主币,邀请返客主币,已消费邀请返客主币等
        public List<double> getAllkezhu(string uid)
        {
            List<double> tuijianren = new List<double>();
            Open();
            string sql = "select * from usermoney where uid='" + uid + "'";
            SqlCommand com = new SqlCommand(sql, sqlCon);
            SqlDataReader read = com.ExecuteReader();
            try
            {
                if (read.Read())
                {
                    tuijianren.Add(Convert.ToDouble(read["kezhumoney"].ToString()));
                    tuijianren.Add(Convert.ToDouble(read["商家返客主币"].ToString()));
                    tuijianren.Add(Convert.ToDouble(read["邀请返客主币"].ToString()));
                    tuijianren.Add(Convert.ToDouble(read["已消费客主币"].ToString()));
                    tuijianren.Add(Convert.ToDouble(read["已消耗邀请返客主币"].ToString()));
                }
            }
            catch
            { }
            finally
            {
                read.Close();
                Dispose();
            }
            return tuijianren;
        }

        //更新用户的现金返客主币,已消费现金返客主币,邀请返客主币,已消费邀请返客主币等
        public bool updateAllkezhu(string uid, double kezhumoney, double 商家返客主币, double 邀请返客主币, double 已消费客主币, double 已消耗邀请返客主币)
        {
            Open();
            string sql = "update usermoney set kezhumoney='" + kezhumoney + "',商家返客主币='" + 商家返客主币 + "',已消费客主币='" + 已消费客主币 + "',邀请返客主币='" + 邀请返客主币 + "',已消耗邀请返客主币='" + 已消耗邀请返客主币 + "' where uid='" + uid + "'";
            SqlCommand com = new SqlCommand(sql, sqlCon);
            try
            {
                com.ExecuteNonQuery();
                return true;
            }
            catch
            {
                return false;
            }
        }

        //更新客主清算
        public bool updateKezhuSamesystem(string hotelid, string hoteltotalid, string clearingmoney)
        {
            bool result = false;
            Open();
            string sql = "select * from samesystemmoney where hotelid='" + hotelid + "' and hoteltotalid='" + hoteltotalid + "'";
            SqlCommand com = new SqlCommand(sql, sqlCon);
            //SqlDataReader read = com.ExecuteReader();
            try
            {
                com.ExecuteNonQuery();
                sql = "select @@rowcount";
                com.CommandText = sql;
                if ((int)com.ExecuteScalar() == 0)
                {
                    sql = "insert into samesystemmoney values ('" + hotelid + "','" + hoteltotalid + "','" + clearingmoney + "','" + DateTime.Now + "')";
                    com.CommandText = sql;
                    com.ExecuteNonQuery();
                    result = true;

                }
                else
                {
                    sql = "update samesystemmoney set clearingmoney='" + clearingmoney + "' , clearingtime='" + DateTime.Now + "' where hotelid='" + hotelid + "' and hoteltotalid='" + hoteltotalid + "'";
                    com.CommandText = sql;
                    com.ExecuteNonQuery();
                    result = true;
                }

            }
            catch
            {
                result = false;
            }
            finally
            {
                //read.Close();
                Dispose();
            }
            return result;
        }

        //读取客主清算
        public double getKezhuSamesystem(string hotelid)
        {
            double chuzhi = 0;

            Open();
            string sql = "select * from samesystemmoney where hotelid='" + hotelid + "'";
            SqlCommand com = new SqlCommand(sql, sqlCon);
            SqlDataReader read = com.ExecuteReader();
            try
            {
                if (read.Read())
                {
                    chuzhi = Convert.ToDouble(read["clearingmoney"].ToString());
                }
            }
            catch
            { }
            finally
            {
                read.Close();
                Dispose();
            }
            return chuzhi;
        }

        //获取hoteltotalid
        public string getHoteltotalid(string hotelid)
        {
            string id = "";
            Open();
            string sql = "select * from hotel where hotelid='" + hotelid + "'";
            SqlCommand cmd = new SqlCommand(sql, sqlCon);
            SqlDataReader read = cmd.ExecuteReader();
            try
            {
                if (read.Read())
                {
                    id = read["hoteltotalid"].ToString();
                }
            }
            catch (Exception)
            {
            }
            finally
            {
                Dispose();
            }
            return id;

        }

        //获取当前用户积分的值以及商户
        public List<string> getUserKezhuSum(string uid)
        {
            List<string> userkezhu = new List<string>();
            Open();
            string sql = "select [hotelhistoryid],[hotelid],([会员积分账户]-[已消费积分]) as A,[已消费积分] from [HotelHistory] where [uid]='" + uid + "' and [consumetype]!='2' and [consumetype]!='9' order by createtime";
            SqlCommand cmd = new SqlCommand(sql, sqlCon);
            SqlDataReader read = cmd.ExecuteReader();

            try
            {
                while (read.Read())
                {
                    if (Convert.ToDouble(read["A"].ToString().Trim()) != 0)
                    {
                        userkezhu.Add(read["hotelid"].ToString());
                        userkezhu.Add(read["A"].ToString());
                        userkezhu.Add(read["已消费积分"].ToString());
                        userkezhu.Add(read["hotelhistoryid"].ToString());
                    }
                }
            }
            catch
            { }
            finally
            {
                read.Close();
                Dispose();
            }
            return userkezhu;
        }

        //更新当前用户的客主币
        public bool updateKezhubi(string uid, string kezhumoney)
        {
            Open();
            string sql = "update usermoney set kezhumoney='" + kezhumoney + "' where uid='" + uid + "'";
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

        //获取当前用户的客主币
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
                else
                {
                    list.Add("0.00");
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


        //获取当前代理
        public string getHotelagent(string hotelid)
        {
            string agentid = "";
            Open();
            string sql = "select * from agentmoney where hotelid='" + hotelid + "'";
            SqlCommand com = new SqlCommand(sql, sqlCon);
            SqlDataReader read = com.ExecuteReader();
            try
            {
                if (read.Read())
                {
                    agentid = read["money"].ToString();
                }
            }
            catch
            { }
            finally
            {
                read.Close();
                Dispose();
            } return agentid;
        }


        //更新当前代理
        public bool updateHotelagent(string hotelid, string money)
        {
            Open();
            string sql = "update sagentmoney set money='" + money + "' where hotelid='" + hotelid + "'";
            SqlCommand com = new SqlCommand(sql, sqlCon);
            try
            {
                com.ExecuteNonQuery();
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

        //new获得当前记录编号的最大值
        public string getNumbermax()
        {
            Open();
            string count = DateTime.Now.ToString("yyyyMMddmmss");
            //日期+编号
            string orderidsql = String.Format("select hotelhistoryid from HotelHistory where hotelhistoryid like '%" + DateTime.Now.ToString("yyyyMMdd") + "%' order by hotelhistoryid desc");
            SqlCommand orderidcmd = new SqlCommand(orderidsql, sqlCon);
            SqlDataReader reader = orderidcmd.ExecuteReader();
            try
            {
                if (reader.Read())
                {
                    count = reader["hotelhistoryid"].ToString();
                    Int64 bianhao = Convert.ToInt64(count) + 1;
                    count = bianhao.ToString();
                }
                else
                {
                    count = DateTime.Now.ToString("yyyyMMdd") + "2001";
                }
            }
            catch
            {

            }
            finally
            {
                reader.Close();
                Dispose();
            }
            return count;
        }


        //new更新hotelhistory记录
        public bool updateHotelhistory(string uid, string hotelid, string 实收金额, string 会员积分账户, string 已消费积分, string 接待商家积分账户, string 积分商家ID, string 积分商家积分账户, string 积分商家同系统清算账户, string 积分商家客主清算账户, string 接待商家同系统清算, string 接待商家客主清算, string 发卡商家同系统清算账户, string 发卡商家客主清算账户, string 推荐人积分账户, string 客主账户, string 代理账户, string serviceuserid, string consumetype)
        {
            Open();
            try
            {
                string count = DateTime.Now.ToString("yyyyMMddmmss");
                //日期+编号
                string orderidsql = String.Format("select hotelhistoryid from HotelHistory where hotelhistoryid like '%" + DateTime.Now.ToString("yyyyMMdd") + "%' order by chuzhihistoryid desc");
                SqlCommand orderidcmd = new SqlCommand(orderidsql, sqlCon);
                SqlDataReader reader = orderidcmd.ExecuteReader();
                if (reader.Read())
                {
                    count = reader["hotelhistoryid"].ToString();
                    Int64 bianhao = Convert.ToInt64(count) + 1;
                    count = bianhao.ToString();
                }
                else
                {
                    count = DateTime.Now.ToString("yyyyMMdd") + "2001";
                }
                reader.Close();


                string sql = "insert into HotelHistory values('" + count + "','" + uid + "','" + hotelid + "','" + 实收金额 + "','" + 会员积分账户 + "','" + 已消费积分 + "','" + 接待商家积分账户 + "','" + 积分商家ID + "','" + 积分商家积分账户 + "','" + 积分商家同系统清算账户 + "','" + 积分商家客主清算账户 + "','" + 接待商家同系统清算 + "','" + 接待商家客主清算 + "','" + 发卡商家同系统清算账户 + "','" + 发卡商家客主清算账户 + "','" + 推荐人积分账户 + "','" + 客主账户 + "','" + 代理账户 + "','" + serviceuserid + "','" + DateTime.Now + "','" + consumetype + "')";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
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


        //读取客主币历史
        public List<string> getKezhuhistory(string uid)
        {
            List<string> list = new List<string>();
            try
            {
                Open();
                string sql = "select * from hotelhistory where uid='" + uid + "'order by createtime desc";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader read = cmd.ExecuteReader();
                while (read.Read())
                {
                    list.Add(read["hotelhistoryid"].ToString());
                    list.Add(read["hotelid"].ToString());
                    list.Add(read["实收金额"].ToString());
                    list.Add(read["会员积分账户"].ToString());
                    list.Add(read["createtime"].ToString());
                    list.Add(read["consumetype"].ToString());
                }
                read.Close();
                Dispose();
            }
            catch (Exception)
            { }
            return list;
        }

        //匹配码
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