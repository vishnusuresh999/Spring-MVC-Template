/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vancrafts.transight.dao;

import com.vancrafts.transight.common.CommonUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author zimr
 */
public class UserDao
{
    JdbcTemplate jdbcTemplate;
    
    public UserDao()
    {
        jdbcTemplate = CommonUtil.getInstance();
    }
    
    public String getMenuString()
    {
        String menuString = "";
        try
        {
            String query = CommonUtil.getQuery("user.getmainmenus");
            List menuList = new ArrayList();
            menuList = jdbcTemplate.queryForList(query,1);
            Iterator iterator = menuList.iterator();
            while(iterator.hasNext())
            {
                String submenu = "";
                Map row = (Map)iterator.next();
                Integer menuid = (Integer)row.get("menuid");
                String menutext = String.valueOf(row.get("menutext"));
                //submenu = "<li><span>" + menutext +"</span>";
                query = CommonUtil.getQuery("user.getsubmenus");
                List subMenuList = new ArrayList();
                subMenuList = jdbcTemplate.queryForList(query,menuid);
                Iterator iterator1 = subMenuList.iterator();
                while(iterator1.hasNext())
                {
                    Map row1 = (Map)iterator1.next();
                    String menuText = String.valueOf(row1.get("menutext"));
                    String menuUrl = String.valueOf(row1.get("menuurl"));
                    submenu += "<li><a href=\" "+ menuUrl +" \">" + menuText + "</a></li>";
                }
                if(subMenuList!=null && !subMenuList.isEmpty())
                {
                    submenu = "<li><span>" + menutext +"</span><ul>" + submenu + "</ul>";
                }
                else
                {
                    submenu = "<li><span>" + menutext +"</span>";
                }
                submenu += "</li>";
                menuString += submenu;
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return menuString;
    }
    
    public Boolean checkLogin(String company,String username,String password)
    {
        Boolean check = false;
        try
        {
            String query = CommonUtil.getQuery("user.logincheck");
            List users = new ArrayList();
            users = jdbcTemplate.queryForList(query);
            if(users!=null && users.isEmpty())
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
}
