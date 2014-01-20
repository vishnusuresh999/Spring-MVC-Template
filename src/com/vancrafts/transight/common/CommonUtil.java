
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vancrafts.transight.common;

import com.vancrafts.transight.bridge.DashBoardDao;
import com.vancrafts.transight.bridge.ExpenseManagerDao;
import com.vancrafts.transight.bridge.FeedbackDao;
import com.vancrafts.transight.bridge.LoginDao;
import com.vancrafts.transight.bridge.StatiticsDao;
import com.vancrafts.transight.bridge.TransightMapsDao;
import com.vancrafts.transight.bridge.VehicleDao;
import com.vancrafts.transight.dao.UserDao;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.web.WebEngine;
import javafx.stage.Stage;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
/**
 *
 * @author zimr
 */
public class CommonUtil 
{
    public static String localdbconurl = "jdbc:derby:transight;create=true";
    public static String localdbconuser = "root";
    public static String localdbconpassword = "root";
    public static String localdriverClassName = "org.apache.derby.jdbc.EmbeddedDriver";
    public static String remotedbconurl = "jdbc:mysql://ip/transight_gprs1";
    public static String remotedbconuser = "root";
    public static String remotedbconpassword = "root";
    public static String remotedriverClassName = "com.mysql.jdbc.Driver";
    public static Map<String,Class> urlClassMap = new LinkedHashMap<String,Class>();
    public static Boolean dbExists = false;    
    public static WebEngine webEngine;
    public static Stage stage;
    public static JdbcTemplate localJdbcTemplate;
    public static JdbcTemplate remoteJdbcTemplate;
    
    private static JdbcTemplate getConnection()
    {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(CommonUtil.localdriverClassName);
        dataSource.setUrl(CommonUtil.localdbconurl);
        dataSource.setUsername(CommonUtil.localdbconuser);
        dataSource.setPassword(CommonUtil.localdbconpassword);
        JdbcTemplate template = new JdbcTemplate();
        template.setDataSource(dataSource);
        return template;
    }
    
    private static JdbcTemplate getNetConnection()
    {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(CommonUtil.remotedriverClassName);
        dataSource.setUrl(CommonUtil.remotedbconurl);
        dataSource.setUsername(CommonUtil.remotedbconuser);
        dataSource.setPassword(CommonUtil.remotedbconpassword);
        JdbcTemplate template = new JdbcTemplate();
        template.setDataSource(dataSource);
        return template;
    }
    
    public static JdbcTemplate getInstance() 
    {
        if(localJdbcTemplate==null)
        {
            localJdbcTemplate = CommonUtil.getConnection();
            return localJdbcTemplate;
        }
        else
        {
            return localJdbcTemplate;
        }
    }
    
    public static JdbcTemplate getRemoteInstance() 
    {
        if(remoteJdbcTemplate==null)
        {
            remoteJdbcTemplate = CommonUtil.getConnection();
            return remoteJdbcTemplate;
        }
        else
        {
            return remoteJdbcTemplate;
        }
    }
    
    public static String readConfig(String property)
    {
        ResourceBundle properties = ResourceBundle.getBundle("config");
        return String.valueOf(properties.getObject(property));
    }
    
    public static String readMessage(String property)
    {
        ResourceBundle properties = ResourceBundle.getBundle("message");
        return String.valueOf(properties.getObject(property));
    }
    
    public static String getQuery(String queryName) 
    {
        ResourceBundle queries = ResourceBundle.getBundle("sqlqueries");
        return String.valueOf(queries.getObject(queryName));
    }
    
    
    
    static
    {
        CommonUtil.urlClassMap.put("login.html", LoginDao.class);
        CommonUtil.urlClassMap.put("dashboard.html", DashBoardDao.class);
        CommonUtil.urlClassMap.put("addcomplaint.html", FeedbackDao.class);
        CommonUtil.urlClassMap.put("addexpense.html", ExpenseManagerDao.class);
        CommonUtil.urlClassMap.put("addexpensetype.html", ExpenseManagerDao.class);
        CommonUtil.urlClassMap.put("adduser.html", UserDao.class);
        CommonUtil.urlClassMap.put("assignvehicle.html", VehicleDao.class);
        CommonUtil.urlClassMap.put("changepassword.html", UserDao.class);
        CommonUtil.urlClassMap.put("companyexpenditure.html", ExpenseManagerDao.class);
        CommonUtil.urlClassMap.put("engine.html", StatiticsDao.class);
        CommonUtil.urlClassMap.put("expensechart.html", ExpenseManagerDao.class);
        CommonUtil.urlClassMap.put("expensereport.html", ExpenseManagerDao.class);
        CommonUtil.urlClassMap.put("fuelalert.html", StatiticsDao.class);
        CommonUtil.urlClassMap.put("fuelrefill.html", StatiticsDao.class);
        CommonUtil.urlClassMap.put("generalreport.html", VehicleDao.class);
        CommonUtil.urlClassMap.put("immobilizer.html", VehicleDao.class);
        CommonUtil.urlClassMap.put("kmrun.html", StatiticsDao.class);
        CommonUtil.urlClassMap.put("mileage.html", StatiticsDao.class);
        CommonUtil.urlClassMap.put("profile.html", UserDao.class);
        CommonUtil.urlClassMap.put("notifications.html", UserDao.class);
        CommonUtil.urlClassMap.put("profileview.html", UserDao.class);
        CommonUtil.urlClassMap.put("settings.html", UserDao.class);
        CommonUtil.urlClassMap.put("subuserdetails.html", UserDao.class);
        CommonUtil.urlClassMap.put("totalexpensechart.html", ExpenseManagerDao.class);
        CommonUtil.urlClassMap.put("transmap.html", TransightMapsDao.class);
        CommonUtil.urlClassMap.put("tripreport.html", VehicleDao.class);
        CommonUtil.urlClassMap.put("userpermission.html", UserDao.class);
        CommonUtil.urlClassMap.put("vehiclerecords.html", VehicleDao.class);
        CommonUtil.urlClassMap.put("vehiclereport.html", VehicleDao.class);
        CommonUtil.urlClassMap.put("vehiclestatus.html", VehicleDao.class);
        CommonUtil.urlClassMap.put("viewcomplaint.html", FeedbackDao.class);
        /*CommonUtil.urlClassMap.put("login.html", LoginDao.class);
        CommonUtil.urlClassMap.put("dashboard.html", DashBoardDao.class);
        CommonUtil.urlClassMap.put("login.html", LoginDao.class);
        CommonUtil.urlClassMap.put("dashboard.html", DashBoardDao.class);
        CommonUtil.urlClassMap.put("login.html", LoginDao.class);
        CommonUtil.urlClassMap.put("dashboard.html", DashBoardDao.class);
        CommonUtil.urlClassMap.put("login.html", LoginDao.class);
        CommonUtil.urlClassMap.put("dashboard.html", DashBoardDao.class);
        CommonUtil.urlClassMap.put("login.html", LoginDao.class);
        CommonUtil.urlClassMap.put("dashboard.html", DashBoardDao.class);
        CommonUtil.urlClassMap.put("login.html", LoginDao.class);
        CommonUtil.urlClassMap.put("dashboard.html", DashBoardDao.class);
        CommonUtil.urlClassMap.put("login.html", LoginDao.class);
        CommonUtil.urlClassMap.put("dashboard.html", DashBoardDao.class);
        CommonUtil.urlClassMap.put("login.html", LoginDao.class);
        CommonUtil.urlClassMap.put("dashboard.html", DashBoardDao.class);
        CommonUtil.urlClassMap.put("login.html", LoginDao.class);
        CommonUtil.urlClassMap.put("dashboard.html", DashBoardDao.class);
        CommonUtil.urlClassMap.put("login.html", LoginDao.class);
        CommonUtil.urlClassMap.put("dashboard.html", DashBoardDao.class);
        CommonUtil.urlClassMap.put("login.html", LoginDao.class);
        CommonUtil.urlClassMap.put("dashboard.html", DashBoardDao.class);
        CommonUtil.urlClassMap.put("login.html", LoginDao.class);
        CommonUtil.urlClassMap.put("dashboard.html", DashBoardDao.class);
        CommonUtil.urlClassMap.put("login.html", LoginDao.class);
        CommonUtil.urlClassMap.put("dashboard.html", DashBoardDao.class);
        CommonUtil.urlClassMap.put("login.html", LoginDao.class);
        CommonUtil.urlClassMap.put("dashboard.html", DashBoardDao.class);
        CommonUtil.urlClassMap.put("login.html", LoginDao.class);
        CommonUtil.urlClassMap.put("dashboard.html", DashBoardDao.class);
        CommonUtil.urlClassMap.put("login.html", LoginDao.class);
        CommonUtil.urlClassMap.put("dashboard.html", DashBoardDao.class);*/
    }
}
