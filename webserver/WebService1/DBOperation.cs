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
        private String ConServerStr = @"Data Source=localhost;Initial Catalog=StockManage;Integrated Security=True";
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
            try
            {
                Open();
                string sql = "select * from hotelinfo where hotelid='" + hotelid + "'";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader read = cmd.ExecuteReader();

                while (read.Read())
                {
                    list.Add(read["hotelid"].ToString());
                    //将结果集信息添加到返回向量中  
                    list.Add(read["hotelname"].ToString());
                    list.Add(read["hotelkind"].ToString());
                }
                read.Close();


                sql = "select * from shop where hotelid='" + hotelid + "'";
                cmd = new SqlCommand(sql, sqlCon);
                read = cmd.ExecuteReader();
                if (read.Read())
                {
                    list.Add(read["hotelid"].ToString());
                    list.Add(read["hotelname"].ToString());
                    list.Add(read["hotelkind"].ToString());
                }
                read.Close();
                Dispose();
                return list;
            }
            catch (Exception)
            {
                list.Add("无地址");
                return list;
            }
        }

        public List<string> selectAddressByCity(string city)
        {
            List<string> list = new List<string>();

            try
            {
                Open();
                string sql = "select * from hotelinfo where city='" + city + "'";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader read = cmd.ExecuteReader();

                while (read.Read())
                {
                    list.Add(read["hotelid"].ToString());
                    //将结果集信息添加到返回向量中  
                    list.Add(read["hotelname"].ToString());
                    list.Add(read["hotelkind"].ToString());
                }
                read.Close();


                sql = "select * from shop where city='" + city + "'";
                cmd = new SqlCommand(sql, sqlCon);
                read = cmd.ExecuteReader();
                if (read.Read())
                {
                    list.Add(read["hotelid"].ToString());
                    list.Add(read["hotelname"].ToString());
                    list.Add(read["hotelkind"].ToString());
                }
                read.Close();
                Dispose();

            }
            catch (Exception ex)
            {
                list.Add(ex.ToString());
            }
            return list;
        }

        public List<string> selectAllAddress(string city)
        {
            List<string> list = new List<string>();

            try
            {
                Open();
                string sql = "select * from hotelinfo where city like'" + '%' + city + '%' + "'";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader reader = cmd.ExecuteReader();

                while (reader.Read())
                {
                    list.Add(reader["hotelid"].ToString());
                    //将结果集信息添加到返回向量中  
                    list.Add(reader["hotelname"].ToString());
                    list.Add(reader["address"].ToString());
                    list.Add(reader["mapPosion"].ToString());
                    list.Add(reader["hotelkind"].ToString());
                }
                reader.Close();

                string sql2 = "select * from shop where city like'" + '%' + city + '%' + "'";
                SqlCommand cmd2 = new SqlCommand(sql2, sqlCon);
                SqlDataReader reader2 = cmd2.ExecuteReader();

                while (reader2.Read())
                {
                    list.Add(reader2["hotelid"].ToString());
                    //将结果集信息添加到返回向量中  
                    list.Add(reader2["hotelname"].ToString());
                    list.Add(reader2["address"].ToString());
                    list.Add(reader2["mapPosion"].ToString());
                    list.Add(reader2["hotelkind"].ToString());
                }
                reader2.Close();



                Dispose();

            }
            catch (Exception ex)
            {
                list.Add(ex.ToString());
            }
            return list;
        }
        //public List<string> selectAllAddress()
        //{
        //    List<string> list = new List<string>();

        //    try
        //    {
        //        Open();
        //        string sql = "select * from Information";
        //        SqlCommand cmd = new SqlCommand(sql, sqlCon);
        //        SqlDataReader reader = cmd.ExecuteReader();

        //        while (reader.Read())
        //        {
        //            list.Add(reader[0].ToString());
        //            //将结果集信息添加到返回向量中  
        //            list.Add(reader[1].ToString());
        //            list.Add(reader[2].ToString());
        //            list.Add(reader[3].ToString());
        //            list.Add(reader[4].ToString());
        //            list.Add(reader[5].ToString());
        //        }

        //        reader.Close();
        //        Dispose();

        //    }
        //    catch (Exception ex)
        //    {
        //        list.Add(ex.ToString());
        //    }
        //    return list;
        //}


        public List<string> selectProvince()
        {
            List<string> list = new List<string>();

            try
            {
                Open();
                string sql = "select distinct province from Citymap";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader reader = cmd.ExecuteReader();

                while (reader.Read())
                {
                    //将结果集信息添加到返回向量中  
                    list.Add(reader[0].ToString());
                }

                reader.Close();
                Dispose();

            }
            catch (Exception)
            {

            }
            return list;
        }

        public bool findCity(string province)
        {
            int i = 0;
            try
            {
                Open();
                string sql = "select * from Province where name='" + province + "'";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader reader = cmd.ExecuteReader();
                if (reader.Read())
                {
                    i = Convert.ToInt32(reader["hascity"]);
                }
                reader.Close();
                Dispose();

            }
            catch (Exception)
            {

            }
            if (i == 1)
                return true;
            else
                return false;
        }

        public List<string> selectCity(string province)
        {
            List<string> list = new List<string>();

            try
            {
                Open();
                string sql = "select city from Citymap where province='" + province + "'";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader reader = cmd.ExecuteReader();

                while (reader.Read())
                {
                    //将结果集信息添加到返回向量中  
                    list.Add(reader[0].ToString());
                }

                reader.Close();
                Dispose();

            }
            catch (Exception)
            {

            }
            return list;
        }

        public List<string> selectOhters(string hotelkind, string city)
        {
            List<string> list = new List<string>();

            try
            {
                Open();
                string sql = "select * from hotelinfo where city like'" + '%' + city + '%' + "'and hotelkind='" + hotelkind + "'";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader reader = cmd.ExecuteReader();

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
                reader.Close();
                string sql2 = "select * from shop where city like'" + '%' + city + '%' + "'and hotelkind='" + hotelkind + "'";
                SqlCommand cmd2 = new SqlCommand(sql2, sqlCon);
                SqlDataReader reader2 = cmd2.ExecuteReader();

                while (reader2.Read())
                {
                    list.Add(reader2["hotelid"].ToString());
                    //将结果集信息添加到返回向量中  
                    list.Add(reader2["hotelname"].ToString());
                    list.Add(reader2["address"].ToString());
                    list.Add(reader2["mapPosion"].ToString());
                    list.Add(reader2["hotelkind"].ToString());
                }
                reader2.Close();
                Dispose();

            }
            //try
            //{
            //    Open();
            //    string sql = "select * from Information where Type='" + type + "'";
            //    SqlCommand cmd = new SqlCommand(sql, sqlCon);
            //    SqlDataReader reader = cmd.ExecuteReader();

            //    while (reader.Read())
            //    {
            //        //将结果集信息添加到返回向量中  
            //        list.Add(reader[0].ToString());
            //        list.Add(reader[1].ToString());
            //        list.Add(reader[2].ToString());
            //        list.Add(reader[3].ToString());
            //        list.Add(reader[4].ToString());
            //        list.Add(reader[5].ToString());
            //    }

            //    reader.Close();
            //    Dispose();

            //}
            catch (Exception)
            {

            }
            return list;
        }


        public List<string> pointvalues(string cityname)
        {
            List<string> list = new List<string>();

            try
            {
                Open();
                string sql = "select * from City where city='" + cityname + "'";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader reader = cmd.ExecuteReader();

                while (reader.Read())
                {
                    //将结果集信息添加到返回向量中  
                    list.Add(reader[3].ToString());
                    list.Add(reader[4].ToString());
                }

                reader.Close();
                Dispose();

            }
            catch (Exception)
            {

            }
            return list;
        }


        public List<string> findUserInfor(string username)
        {
            List<string> list = new List<string>();
            //try
            //{
            //    Open();
            //    string sql = "select * from [Member_users] where [username]='" + username + "'";
            //    SqlCommand cmd = new SqlCommand(sql, sqlCon);
            //    SqlDataReader reader = cmd.ExecuteReader();

            //    if (reader.Read())
            //    {
            //        //将结果集信息添加到返回向量中  
            //        list.Add(reader["username"].ToString());
            //        list.Add(reader["nickname"].ToString());
            //        list.Add(reader["password"].ToString());
            //        list.Add(reader["mobilephone"].ToString());
            //        list.Add(reader["addresspost"].ToString());
            //        list.Add(reader["sexy"].ToString());
            //        list.Add(reader["email"].ToString());
            //    }

            //    reader.Close();
            //    Dispose();

            //}

            try
            {
                Open();
                string sql = "select * from [UserInfo] where [username]='" + username + "'";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader reader = cmd.ExecuteReader();

                if (reader.Read())
                {
                    //将结果集信息添加到返回向量中  
                    list.Add(reader["username"].ToString());
                    list.Add(reader["nickename"].ToString());
                    list.Add(reader["userpwd"].ToString());
                    list.Add(reader["mobilephone"].ToString());
                    list.Add(reader["addresspost"].ToString());
                    list.Add(reader["sexy"].ToString());
                    list.Add(reader["email"].ToString());
                    list.Add(reader["fkezhu"].ToString());
                    list.Add(reader["ykezhu"].ToString());
                }

                reader.Close();
                Dispose();

            }
            catch (Exception ex)
            {
                list.Add(ex.Message.ToString());
            }
            return list;
        }


        public bool AddOrder(string username, string nickename, string stayperson, string roomtype, string price, string telephone, string hotelname, string hoteladdress, string begindate, string enddate)
        {
            try
            {
                Open();
                string sql = "insert into [UserOrder] values ('" + username + "','" + nickename + "','" + stayperson + "','" + roomtype + "','" + price + "','" + telephone + "','" + hotelname + "','" + hoteladdress + "','" + DateTime.Now.ToString("yyyy-MM-dd HH:mm") + "','" + begindate + "','" + enddate + "')";
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

        public bool AddUser(string username, string nickename, string userpwd, string mobilephone, string addresspost, string sexy, string email, string fkezhu, string ykezhu, string invitephone)
        {
            try
            {
                Open();
                string sql = "insert into [UserInfo] values ('" + username + "','" + nickename + "','" + userpwd + "','" + mobilephone + "','" + addresspost + "','" + sexy + "','" + email + "','" + fkezhu + "','" + ykezhu + "','" + invitephone + "')";
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
            try
            {
                Open();
                string sql = "insert into [message] values ('" + username + "','" + title + "','" + message + "','" + DateTime.Now.ToString("yyyy-MM-dd HH:mm") + "')";
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

        public List<string> findMessage(string username)
        {
            List<string> list = new List<string>();

            try
            {
                Open();
                string sql = "select * from [message] where [username]='" + username + "'";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader reader = cmd.ExecuteReader();

                while (reader.Read())
                {
                    //将结果集信息添加到返回向量中
                    list.Add(reader[1].ToString());
                    list.Add(reader[2].ToString());
                    list.Add(reader[3].ToString());
                    list.Add(reader[4].ToString());
                }

                reader.Close();
                Dispose();

            }
            catch (Exception)
            {

            }
            return list;
        }


        public List<string> getUserOrder(string username)
        {
            List<string> list = new List<string>();
            try
            {
                Open();
                string sql = "select * from UserOrder where username='" + username + "' order by ordertime desc";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader read = cmd.ExecuteReader();
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
                read.Close();
                Dispose();
                //List<string> list = new List<string>();
                //try
                //{
                //    Open();
                //    string sql = "select * from roomorder where customername='" + username + "'";
                //    SqlCommand cmd = new SqlCommand(sql, sqlCon);
                //    SqlDataReader read = cmd.ExecuteReader();
                //    while (read.Read())
                //    {
                //        list.Add(read["商家名称"].ToString());       //0     
                //        list.Add(read["房型"].ToString());           //1
                //        list.Add(read["addressHotel"].ToString());   //2
                //        string[] begindate = read["begindate"].ToString().Split(' ');
                //        list.Add(begindate[0]);      //3
                //        string[] enddate = read["enddate"].ToString().Split(' ');
                //        list.Add(enddate[0]);        //4
                //        list.Add(read["入住人"].ToString().Trim());         //5
                //        list.Add(read["mobilephone"].ToString().Trim());    //6
                //        string[] price=read["价格"].ToString().Split(';');
                //        list.Add(price[0]);           //7
                //    }
                //    read.Close();
                //    Dispose();

            }
            catch (Exception)
            { }
            return list;
        }

        public bool findMobilePhone(string phone)
        {

            try
            {
                Open();
                string sql = "select mobilephone from UserInfo where mobilephone='" + phone + "'";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                object obj = cmd.ExecuteScalar();
                if (obj != null)
                {
                    cmd.Dispose();
                    return true;
                }
                else
                {
                    cmd.Dispose();
                    return false;
                }
            }
            catch (Exception)
            {
                return false;
            }
        }


        public bool userRegister(string username, string nickename, string password, string email, int sexy)
        {
            Guid guid = new Guid();
            guid = Guid.NewGuid();
            try
            {
                Open();
                string sql = "insert into users (uid,username,nickname,password,groupid,onlinestate,regip,lastip,lastvisit,lastactivity,oltime,email,newpm,newpmcount,state,sexy,mobilephone) values ('" + guid + "','" + username + "','" + nickename + "','" + password + "','200','0','127.0.0.1','127.0.0.1','" + DateTime.Now + "','" + DateTime.Now + "','0','" + email + "','0','0','1','1','" + username + "') ";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                cmd.ExecuteNonQuery();
                sql = "insert into usermoney (uid,groupid,hotelid,totaluserid,scorechuzhi,scorexiaofei,moneysystem,joindate) values('" + guid + "','500','97','00000000-0000-0000-0000-000000000000','0.00','0.00','0.00','" + DateTime.Now + "')";
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
            try
            {
                string sql = "select [password] from users where username='" + name + "'";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader read = cmd.ExecuteReader();
                if (read.Read())
                {
                    userpwd = read["password"].ToString();
                }
                read.Close();
                Dispose();
                if (userpwd ==MD5( pwd))
                    return true;
                else
                    return false;
            }
            catch (Exception)
            {
                Dispose();
                return false;
            }

        }

        public bool userPwdChange(string name, string pwd)
        {
            try
            {
                Open();
                pwd = MD5(pwd);
                string sql = "update users set password='" + pwd + "' where username='" + name + "'";
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

        public List<string> userInfor(string name)
        {
            List<string> list = new List<string>();
            try
            {
                Open();
                string sql = "select * from users where username='" + name + "'";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader read = cmd.ExecuteReader();
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
                Dispose();
               
            }
            catch (Exception ex)
            {
                list.Add(ex.Message.ToString());
            }
            return list;
            
        }



        public List<string> getConsume(string customeruserid)
        {
            List<string> list = new List<string>();
            try
            {
                Open();
                string sql = "select * from hotelhistory where customeruserid='" + customeruserid + "'";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader read = cmd.ExecuteReader();
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
                read.Close();
                Dispose();
            }
            catch (Exception)
            { }
            return list;
        }


        public bool updateUser(string type, string value, string username)
        {
            try
            {
                Open();
                string sql = "update users set  " + type + "='" + value + "' where username='" + username + "'";
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



        public bool addHotelhistory(string customeruserid, string customername, string hotelname, string money, string moneykezhu, string returnkezhu)
        {
            try
            {
                Open();
                string sql = "insert into hotelhistory (hotelhistoryid,customeruserid,customername,hotelname,money,scorecustomer,moneykezhu,createtime) values('" + Guid.NewGuid() + "','" + customeruserid + "','" + customername + "','" + hotelname + "','" + money + "','" + returnkezhu + "','" + moneykezhu + "','" + DateTime.Now + "')";
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


        public List<string> getRoomInfo(int hotelid)
        {
            List<string> list = new List<string>();
            try
            {
                Open();
                string sql = "select * from room,hotelinfo where room.hotelinfoid=hotelinfo.hotelinfoid  and hotelinfo.hotelid ='" + hotelid + "'";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader read = cmd.ExecuteReader();
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
                read.Close();
                Dispose();
            }
            catch (Exception)
            { }
            return list;
        }

        public List<string> getHotelInfo()
        {
            List<string> list = new List<string>();
            try
            {
                Open();
                string sql = "select * from hotelinfo";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader read = cmd.ExecuteReader();
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
                read.Close();
                Dispose();
            }
            catch (Exception)
            { }
            return list;
        }


        public List<string> getHotelInfoById(int hotelid)
        {
            List<string> list = new List<string>();
            try
            {
                Open();
                string sql = "select * from hotelinfo where hotelid='" + hotelid + "'";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader read = cmd.ExecuteReader();
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
                read.Close();
                Dispose();
            }
            catch (Exception)
            { }
            return list;
        }

        public List<string> getShopInfo()
        {
            List<string> list = new List<string>();
            try
            {
                Open();
                string sql = "select * from shop ";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader read = cmd.ExecuteReader();
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
                read.Close();
                Dispose();
            }
            catch (Exception)
            { }
            return list;
        }

        public List<string> getShopInfoById(string hotelid)
        {
            List<string> list = new List<string>();
            try
            {
                Open();
                string sql = "select * from shop where hotelid='" + hotelid + "'";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader read = cmd.ExecuteReader();
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
                read.Close();
                Dispose();
            }
            catch (Exception)
            { }
            return list;
        }

        public List<string> getUsemoney(int id)
        {
            List<string> list = new List<string>();
            try
            {
                Open();
                string sql = "select * from usermoney where id='" + id + "'";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader read = cmd.ExecuteReader();
                while (read.Read())
                {

                    list.Add(read["id"].ToString());
                    list.Add(read["hotelid"].ToString());
                    list.Add(read["scorechuzhi"].ToString());
                    list.Add(read["joindate"].ToString());

                }
                read.Close();
                Dispose();
            }
            catch (Exception)
            { }
            return list;
        }


        public List<string> getUsemoneyhistory(int id)
        {
            List<string> list = new List<string>();
            try
            {
                Open();
                string sql = "select * from usermoneyhotel where uid='" + id + "' ";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader read = cmd.ExecuteReader();
                while (read.Read())
                {

                    list.Add(read["uid"].ToString());
                    list.Add(read["hotelid"].ToString());
                    list.Add(read["outchuzhi"].ToString());
                    list.Add(read["inchuzhi"].ToString());

                }
                read.Close();
                Dispose();
            }
            catch (Exception)
            { }
            return list;
        }

        public bool updateUserChuzhi(string username, string hotelid, string money)
        {
            try
            {
                Open();
                string sql = "update usermoneyhotel set usermoneyhotel.scorechuzhi ='" + money + "' from usermoneyhotel,UserInfo  where usermoneyhotel.uid = UserInfo.Id and UserInfo.username='" + username + "' and  usermoneyhotel.hotelid ='" + hotelid + "'";
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

        public List<string> getUserChuzhi(string uid)
        {
            List<string> list = new List<string>();
            try
            {
                Open();
                
                //string sqlSel = string.Format("select * from usermoneyhotel,UserInfo where usermoneyhotel.uid=UserInfo.Id and UserInfo.username='{0}'",username);
                string sql = "select * from usermoneyhotel where uid='" + uid + "'";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader read = cmd.ExecuteReader();
                while (read.Read())
                {
                    list.Add(read["uid"].ToString());
                    list.Add(read["hotelid"].ToString());
                    list.Add(read["scorechuzhi"].ToString());
                    //  list.Add(read["userid"].ToString());

                }
                read.Close();
                Dispose();
               
            }
            catch (Exception)
            { }
            return list;
        }

        public bool addUserKezhu(string uid)
        {
            try
            {
                Open();
<<<<<<< HEAD
                string sql = "insert into usermoney (uid,groupid,hotelid,totaluserid,scorechuzhi,scorexiaofei,moneysystem,joindate) values('" + new Guid(uid) + "','500','97','00000000-0000-0000-0000-000000000000','0.00','0.00','0.00','" + DateTime.Now + "')";
=======
                string sql = "insert into usermoney ( ) values('200','100','00000000-0000-0000-0000-000000000000','0.00','0.00','0.00','" + DateTime.Now + "')";
>>>>>>> origin/master
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

        public List<string> getUserKezhu(string uid)
        {
            List<string> list = new List<string>();
            try
            {
                Open();
                //string sqlSel = string.Format("select * from usermoneyhotel,UserInfo where usermoneyhotel.uid=UserInfo.Id and UserInfo.username='{0}'",username);
                string sql = "select scorexiaofei from usermoney where uid='" + uid + "'";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader read = cmd.ExecuteReader();
                while (read.Read())
                {
                    list.Add(read[0].ToString());
                    //  list.Add(read["userid"].ToString());

                }
                read.Close();
                Dispose();
            }
            catch (Exception)
            { list.Add("0.00"); }
            return list;
        }

        public List<string> findInvitePhone(string username)
        {
            string mobilephone = "";
            List<string> list = new List<string>();
            try
            {
                Open();
                string sql = "select mobilephone from UserInfo where username='" + username + "'";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader read = cmd.ExecuteReader();
                if (read.Read())
                    mobilephone = read["mobilephone"].ToString();
                read.Close();
                sql = "select mobilephone from UserInfo where invitephone='" + mobilephone + "'";
                cmd = new SqlCommand(sql, sqlCon);
                read = cmd.ExecuteReader();
                while (read.Read())
                {
                    list.Add(read["mobilephone"].ToString());
                }
                read.Close();
                cmd.Dispose();
            }
            catch (Exception)
            { }
            return list;
        }
        public List<string> findShops(string city, string shopname)
        {
            List<string> list = new List<string>();
            Open();
            try
            {

                string sql = "select * from shop where city like'" + '%' + city + '%' + "' and hotelname like '" + '%' + shopname + '%' + "'";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader read = cmd.ExecuteReader();
                while (read.Read())
                {
                    list.Add(read["hotelid"].ToString().Trim());
                    list.Add(read["hotelname"].ToString().Trim());
                    list.Add(read["hotelkind"].ToString());
                    list.Add(read["address"].ToString().Trim());
                    list.Add(read["mapPosion"].ToString());

                }
                read.Close();

                sql = "select * from hotelinfo where city like'" + '%' + city + '%' + "' and hotelname like '" + '%' + shopname + '%' + "'";
                SqlCommand com = new SqlCommand(sql, sqlCon);
                SqlDataReader sqlread = com.ExecuteReader();
                while (sqlread.Read())
                {
                    list.Add(sqlread["hotelid"].ToString().Trim());
                    list.Add(sqlread["hotelname"].ToString().Trim());
                    list.Add("1");
                    list.Add(sqlread["address"].ToString().Trim());
                    list.Add(sqlread["mapPosion"].ToString());
                }
                sqlread.Close();

            }
            catch (Exception)
            { }
            Dispose();
            return list;
        }

        public bool addChuzhihistory(string username, string hotelid, string number)
        {
            try
            {
                Open();
                string sql = "insert into UserChuzhiHistory values('" + username + "','" + hotelid + "','" + number + "','" + DateTime.Now + "')";
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


        public List<string> getChuzhihistory(string uid, string hotelid)
        {
            List<string> list = new List<string>();
            try
            {
                Open();
                string sql = "select * from hotelhistory where customeruserid='" + uid + "' and hotelid='" + hotelid + "' order by createtime desc";
                SqlCommand cmd = new SqlCommand(sql, sqlCon);
                SqlDataReader read = cmd.ExecuteReader();
                while (read.Read())
                {
                    list.Add(read["storedmoneycustomer"].ToString());
                    list.Add(read["createtime"].ToString());
                }
                read.Close();
                Dispose();
            }
            catch (Exception)
            { }
            return list;
        }

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


    }
}