/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vancrafts.transight.common;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 *
 * @author zimr
 */
public class DBTransactionManager
{
    JdbcTemplate localJdbcTemplate;
    DataSourceTransactionManager transactionManager = null;
    TransactionStatus transactionStatus = null;
    TransactionDefinition transactionDefinition = null;
    
    public DBTransactionManager()
    {
        
    }
    
    public DBTransactionManager(JdbcTemplate jdbcTemplate)
    {
        this.localJdbcTemplate = jdbcTemplate;
        this.transactionManager = new DataSourceTransactionManager(jdbcTemplate.getDataSource());
        this.transactionDefinition = new DefaultTransactionDefinition();
        this.transactionStatus = transactionManager.getTransaction(transactionDefinition);
    }
    
    public Boolean commit()
    {
        Boolean commit = false;
        try
        {
            transactionManager.commit(transactionStatus);
            commit = true;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return commit;
    }
    
    public Boolean rollback()
    {
        Boolean rollback = false;
        try
        {
            transactionManager.rollback(transactionStatus);
            rollback = true;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return rollback;
    }
}
