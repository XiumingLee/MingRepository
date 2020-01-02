package cn.xiuminglee.lesson001;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * @Author Xiuming Lee
 * @Description
 */
public class StartingMethodTwo extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Hello World!");
        stage.show();
    }
}
