/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package transight;

import com.vancrafts.transight.common.CommonDao;
import com.vancrafts.transight.dao.ClientSetupDao;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 *
 * @author zimr
 */
public class Splash extends Application
{
    private ProgressBar progressBar;
    private static final Integer SPLASH_WIDTH = 750;
    private static final Integer SPLASH_HEIGHT = 550;
    private Pane splashLayout1,splashLayout2,splashLayout3,splashLayout4;
    private Label progressBarText;
    ClientSetupDao clientSetupDao = new ClientSetupDao();
    CommonDao commonDao = new CommonDao();
    
    @Override
    public void init()
    {
        ImageView splash = new ImageView(new Image(Dashboard.class.getResourceAsStream("html/images/transight_splash.jpg")));
        progressBar = new ProgressBar();
        progressBar.setPrefHeight(20);
        progressBar.setPrefWidth(720);
        progressBar.setBlendMode(BlendMode.ADD);
        progressBarText = new Label("Transight - See Everything Across");
        splashLayout1 = new VBox();
        splashLayout2 = new VBox();
        splashLayout3 = new VBox();
        splashLayout4 = new VBox();
        splashLayout1.getChildren().addAll(splash);
        splashLayout2.getChildren().addAll(progressBar);
        splashLayout3.getChildren().add(progressBarText);
        splashLayout4.getChildren().addAll(splashLayout1,splashLayout2,splashLayout3);
        splashLayout4.setEffect(new DropShadow());
    }
    
    @Override
    public void start(final Stage stage) throws Exception
    {
        Image icon = new Image(Dashboard.class.getResourceAsStream("html/images/trans-logo.png"));
        stage.getIcons().add(icon);
        Scene splashScene = new Scene(splashLayout4);
        stage.initStyle(StageStyle.UNDECORATED);
        final Rectangle2D bounds = Screen.getPrimary().getBounds();
        stage.setScene(splashScene);
        stage.setX(bounds.getMinX() + bounds.getWidth() / 2 - SPLASH_WIDTH / 2);
        stage.setY(bounds.getMinY() + bounds.getHeight() / 2 - SPLASH_HEIGHT / 2);
        stage.show();
        final Task<ObservableList<String>> task = new Task()
        {
            @Override
            protected ObservableList<String> call() throws InterruptedException
            {
                ObservableList<String> availableMessages = FXCollections.observableArrayList("Checking for License details", "Checking for License details.","Checking for License details..","Checking for License details...","Checking network.","Checking network..","Checking network...", "Checking the user.","Checking the user..","Checking the user...", "Checking the settings.","Checking the settings..","Checking the settings...", "Starting up synchronise.", "Starting up synchronise..", "Starting up synchronise...", "Loading dashboard.","Loading dashboard..","Loading dashboard...");
                
                updateMessage(availableMessages.get(0));
                updateMessage(availableMessages.get(1));
                //Boolean licenseCheck = clientSetupDao.checkLicence();
                updateProgress(1, availableMessages.size());
                updateMessage(availableMessages.get(2));
                updateMessage(availableMessages.get(3));
                Boolean networkCheck = commonDao.checkNetworkConnection();
                updateMessage(availableMessages.get(4));
                updateMessage(availableMessages.get(5));
                if(!networkCheck)
                    updateMessage("Network not connected");
                else
                    updateMessage("Network available");
                updateMessage("Loading . . .");
                Thread.sleep(1000);
                for (int i = 6; i < availableMessages.size(); i++)
                {
                    Thread.sleep(1000);
                    updateProgress(i + 1,availableMessages.size());
                    updateMessage(availableMessages.get(i));
                }
                updateMessage(availableMessages.get(availableMessages.size()));
                return availableMessages;
            }
        };
        /*stage.setOnCloseRequest(new EventHandler<WindowEvent>()
        {
            @Override
            public void handle(WindowEvent t)
            {
                System.out.println("Closing.....");
            }
        });*/
        task.stateProperty().addListener(new ChangeListener<Worker.State>()
        {
            @Override
            public void changed(ObservableValue<? extends Worker.State> observableValue, Worker.State oldState, Worker.State newState)
            {
                if (newState == Worker.State.SUCCEEDED)
                {
                    stage.close();
                    new Login().start(new Stage());
                    /*if(clientSetupDao.checkDatabase())
                    {
                        Boolean check = clientSetupDao.checkLicence();
                        if(!check)
                        {
                            new Setup().start(new Stage());
                        }
                        else
                        {
                            new Login().start(new Stage());
                        }
                    }*/
                }                
            }
        });
        progressBarText.textProperty().bind(task.messageProperty());
        progressBar.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
    }
    
    public static void main(String[] args)
    {
        launch(args);
    }
}
