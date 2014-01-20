/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vancrafts.transight.bridge;

import com.vancrafts.transight.common.CommonUtil;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.web.WebEngine;
import javafx.stage.Stage;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author zimr
 */
public class SetupDao
{
    WebEngine webEngine;
    Stage stage;
    JdbcTemplate localJdbcTemplate;
    JdbcTemplate remoteJdbcTemplate;
    
    public SetupDao()
    {
        localJdbcTemplate = CommonUtil.getInstance();
        remoteJdbcTemplate = CommonUtil.getRemoteInstance();
        webEngine = CommonUtil.webEngine;
        stage = CommonUtil.stage;
    }
    
    public SetupDao(WebEngine webEngine,Stage stage)
    {
        this.webEngine = webEngine;
        this.stage = stage;
        this.localJdbcTemplate = CommonUtil.getInstance();
        this.remoteJdbcTemplate = CommonUtil.getRemoteInstance();
    }
    
    /**Check whether the license details entered is correct or not
     * @return 
     */
    public Boolean checkLicenseDetails()
    {
        Boolean check = false;
        try
        {
            String email = (String)webEngine.executeScript("$('#email').val()");
            String name = (String)webEngine.executeScript("$('#name').val()");
            String licensekey = (String)webEngine.executeScript("$('#licensekey').val()");
            String query = CommonUtil.getQuery("remoteLicenseDetails");
            List licenseDetails = new ArrayList();
            licenseDetails = remoteJdbcTemplate.queryForList(query,email,licensekey);
            if(licenseDetails!=null && !licenseDetails.isEmpty())
            {
                check = true;
                query = CommonUtil.getQuery("license.insert");
                localJdbcTemplate.update(query,name,email,licensekey);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return check;
    }
}
