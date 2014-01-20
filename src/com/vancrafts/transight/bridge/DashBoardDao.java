/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vancrafts.transight.bridge;

import com.vancrafts.transight.common.CommonUtil;
import javafx.scene.web.WebEngine;
import javafx.stage.Stage;
import org.springframework.jdbc.core.JdbcTemplate;
import transight.Login;

/**
 *
 * @author zimr
 */
public class DashBoardDao
{
    Stage stage;
    WebEngine webEngine;
    JdbcTemplate jdbcTemplate;
    
    public DashBoardDao()
    {
        jdbcTemplate = CommonUtil.getInstance();
        webEngine = CommonUtil.webEngine;
        stage = CommonUtil.stage;
    }
    
    public DashBoardDao(Stage stage,WebEngine webEngine)
    {
        this.stage = stage;
        this.webEngine = webEngine;
        this.jdbcTemplate = CommonUtil.getInstance();
    }
    
    public void getTransFenceList()
    {
        try
        {
            
        }
        catch(Exception ex)
        {
            
        }
    }
}
