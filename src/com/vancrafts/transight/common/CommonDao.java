/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vancrafts.transight.common;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javafx.scene.web.WebEngine;
import javafx.stage.Stage;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author zimr
 */
public class CommonDao
{
    WebEngine webEngine;
    Stage stage;
    JdbcTemplate localJdbcTemplate;
    
    public CommonDao()
    {
        localJdbcTemplate = CommonUtil.getInstance();
        webEngine = CommonUtil.webEngine;
        stage = CommonUtil.stage;
    }
    
    public CommonDao(WebEngine webEngine,Stage stage)
    {
        this.webEngine = webEngine;
        this.stage = stage;
        this.localJdbcTemplate = CommonUtil.getInstance();
    }
    
    /**Check whether the application is connected to the internet or not
     * @return True/false
     */
    public Boolean checkNetworkConnection()
    {
        Boolean check = false;
        try
        {
            URL url = new URL("http://www.google.com");
            HttpURLConnection urlConnect = (HttpURLConnection)url.openConnection();
            urlConnect.setConnectTimeout(10000);
            Object objData = urlConnect.getContent();
            if (objData!=null)
            	check = true;
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return check;
    }
    
    /**Draws the table data into the html table
     * @param tableid html table id
     * @param query query used to get the data
     * @param params query used to get the data
     */
    public void drawTable(String tableid,String query,Object...params)
    {
        String html = "";
        List data = new ArrayList();
        try
        {
            data = localJdbcTemplate.queryForList(query,params);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        Iterator iterator = data.iterator();
        while(iterator.hasNext())
        {
            Map row = (Map)iterator.next();
            html +="<tr>";
            for(Iterator it = row.entrySet().iterator();it.hasNext();)
            {
                Map.Entry entry = (Map.Entry)it.next();
                html += "<td>" + entry.getValue() + "</td>";
            }
            html +="</tr>";
        }
        webEngine.executeScript("$('" + tableid + "').html(\"" + html + "\"");
    }
}
