/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package transight;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author zimr
 */
public class Test
{
    public static void main(String[] args) throws IOException
    {
        /*Properties prop = new Properties();
        prop.load(Test.class.getClassLoader().getResourceAsStream("config.properties"));
        prop.remove("db_status");
        prop.setProperty("db_status", "true");
        prop.store(new FileOutputStream(new File(Test.class.getClassLoader().getResource("config.properties").toExternalForm())), "Write");
        prop.clear();
        prop = new Properties();
        String db_status = CommonUtil.readConfig("db_status");
        System.out.println(db_status);*/
        System.out.println(new File("app\\config.properties").getAbsolutePath());
    }
}
