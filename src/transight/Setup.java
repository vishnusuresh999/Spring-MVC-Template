/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package transight;

import com.vancrafts.transight.common.CommonUtil;
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
public class Setup extends Application
{    
    @Override
    public void start(final Stage stage)
    {
        WebView webview = new WebView();
        webview.getEngine().load(Setup.class.getResource("html/setup.html").toExternalForm());
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
                    window.setMember("transight", "");                    
                }
            }
        });
        stage.setScene(new Scene(webview));
        stage.setTitle("Transight Setup");
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
