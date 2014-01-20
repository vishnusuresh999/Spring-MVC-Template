/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package transight;

import com.vancrafts.transight.common.CommonUtil;
import java.awt.Dimension;
import java.awt.Toolkit;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

/**
 *
 * @author zimr
 */
public class Login extends Application
{    
    @Override
    public void start(final Stage stage)
    {
        WebView webview = new WebView();
        webview.getEngine().load(Login.class.getResource("html/login.html").toExternalForm());
        final WebEngine webEngine = webview.getEngine();
        webEngine.setJavaScriptEnabled(true);
        Image icon = new Image(Dashboard.class.getResourceAsStream("html/images/trans-logo.png"));
        stage.getIcons().add(icon);
        webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>()
        {
            @Override
            public void changed(ObservableValue<? extends Worker.State> ov, Worker.State t, Worker.State t1)
            {
                if (t1 == Worker.State.SUCCEEDED)
                {
                    JSObject window = (JSObject) webEngine.executeScript("window");
                    String url = (String) webEngine.executeScript("window.location.href");
                    url = url.substring(url.lastIndexOf("/")+1);
                    window.setMember("transight", CommonUtil.urlClassMap.get(t1));
                    CommonUtil.webEngine = webEngine;
                    CommonUtil.stage = stage;
                    try
                    {
                        window.setMember("transight", CommonUtil.urlClassMap.get(url).newInstance());
                    }
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }                    
                }
            }
        });
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Double width = screenSize.getWidth();
        Double height = screenSize.getHeight();
        stage.setHeight(height);
        stage.setWidth(width);
        stage.setScene(new Scene(webview));
        stage.setTitle("Transight | Login");
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }
}
