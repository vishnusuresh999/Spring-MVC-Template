/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vancrafts.transight.dao;

import com.vancrafts.transight.common.CommonUtil;
import static com.vancrafts.transight.common.CommonUtil.dbExists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author zimr
 */
public class ClientSetupDao
{
    JdbcTemplate remoteJdbcTemplate;
    JdbcTemplate localJdbcTemplate;
    
    public ClientSetupDao()
    {
        remoteJdbcTemplate = CommonUtil.getRemoteInstance();
        localJdbcTemplate = CommonUtil.getInstance();
    }
    
    /**Initialise the embedded database table creation
     */
    public void initialise()
    {
        checkDatabase();
        if(!dbExists)
        {
            ResourceBundle mailProperties = ResourceBundle.getBundle("database_tables");
            Enumeration enum1 = mailProperties.getKeys();
            List<Integer> keys = new ArrayList<>();
            while(enum1.hasMoreElements())
            {
                String key = String.valueOf(enum1.nextElement());
                keys.add(Integer.parseInt(key));
            }
            Integer[] keys1 = new Integer[keys.size()];
            keys.toArray(keys1);
            Arrays.sort(keys1);
            for(Integer i=0;i<keys1.length;++i)
            {
                localJdbcTemplate.update(mailProperties.getString(""+keys1[i]));
            }
        }
    }
    
    /**Check whether the database exists in the transight else needs to be created
     * @return True/false
     */
    public Boolean checkDatabase()
    {
        Boolean check = false;
        try
        {
            String query = CommonUtil.getQuery("dbcheck");
            List list = new ArrayList();
            list = localJdbcTemplate.queryForList(query);
            if(list!=null && !list.isEmpty())
            {
                check = true;
                CommonUtil.dbExists = true;
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return check;
    }
    
    /**After the successfull creation of the transight tables locally,
     * the client related data are downloaded to the local database.
     * 
     */
    public void loadClientData()
    {
        try
        {
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    /**Fetches the data from the remote Database and inserts into the local database
     * @param remoteQuery Query to fetch the remote Data
     * @param localQuery Query to fetch the local Data
     * @param remoteParameters Parameters in case conditional statements
     */
    public void synchroniseData(String remoteQuery,String localQuery,Object[] remoteParameters)
    {
        try
        {
            List remoteResults = remoteJdbcTemplate.queryForList(remoteQuery,remoteParameters);
            Iterator iterator = remoteResults.iterator();
            while(iterator.hasNext())
            {
                Map remoteData = (Map)iterator.next();
                Object[] localParameters = new Object[remoteData.size()];
                Integer i=0;
                for(Iterator it = remoteData.entrySet().iterator();it.hasNext();)
                {
                    Map.Entry entry = (Map.Entry)it.next();
                    localParameters[i] = entry.getValue();
                    i++;
                }
                localJdbcTemplate.update(localQuery,localParameters);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    /**Check whether the licence is registered or not
     * @return True/False
     */
    public Boolean checkLicence()
    {
        Boolean check = false;
        try
        {
            String query = CommonUtil.getQuery("licencedetails");
            List licenseDetails = localJdbcTemplate.queryForList(query);
            if(licenseDetails!=null && !licenseDetails.isEmpty())
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
