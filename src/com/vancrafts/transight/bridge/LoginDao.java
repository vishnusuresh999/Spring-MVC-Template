/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vancrafts.transight.bridge;

import com.vancrafts.transight.common.CommonUtil;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.scene.web.WebEngine;
import javafx.stage.Stage;
import org.springframework.jdbc.core.JdbcTemplate;
import transight.Dashboard;
import transight.Login;

/**
 *
 * @author zimr
 */
public class LoginDao
{
    WebEngine webEngine;
    Stage stage;
    JdbcTemplate localJdbcTemplate;
    
    public LoginDao()
    {
        localJdbcTemplate = CommonUtil.getInstance();
        webEngine = CommonUtil.webEngine;
        stage = CommonUtil.stage;
    }
    
    public LoginDao(WebEngine webEngine,Stage stage)
    {
        this.webEngine = webEngine;
        this.stage = stage;
        this.localJdbcTemplate = CommonUtil.getInstance();
    }
    
    public Boolean checkLogin()
    {
        Boolean loginCheck = false;
        try
        {
            String company = (String) webEngine.executeScript("$('#company_name').val()");
            String username = (String) webEngine.executeScript("$('#username').val()");
            String password = (String) webEngine.executeScript("$('#password').val()");
            String query = CommonUtil.getQuery("user.logincheck");
            List users = new ArrayList();
            users = localJdbcTemplate.queryForList(query,username,password,company);
            if(users!=null && !users.isEmpty())
            {
                loginCheck = true;
                query = CommonUtil.getQuery("user_login.insert");
                localJdbcTemplate.update(query,username);
                stage.close();
                new Dashboard().start(new Stage());
            }
            else
            {
                webEngine.executeScript("$('#invalid-user').show()");
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return loginCheck;
    }
    
    public void redirect()
    {
        stage.close();
        new Dashboard().start(new Stage());
    }
    
    /**Checks a user is logined to this version locally
     * if yes return true else return false
     * @return true/false
     */
    public Boolean checkUserLogin()
    {
        Boolean check = false;
        try
        {
            Timestamp timestamp = localJdbcTemplate.queryForObject(CommonUtil.getQuery("user.getmaxlogintime"), Timestamp.class);
            String query = CommonUtil.getQuery("user.getmaxlogindetails");
            Map loginDetails =  localJdbcTemplate.queryForMap(query,timestamp);
            Integer status = (Integer)loginDetails.get("status");
            if(status.equals(1))
            {
                check = true;
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return check;
    }
    
    public void logout()
    {
        try
        {
            String username = localJdbcTemplate.queryForObject(CommonUtil.getQuery("user_login.getlogineduser"), String.class);
            String query = CommonUtil.getQuery("user_login.userlogout");
            localJdbcTemplate.update(query);
            stage.close();
            new Login().start(new Stage());
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
