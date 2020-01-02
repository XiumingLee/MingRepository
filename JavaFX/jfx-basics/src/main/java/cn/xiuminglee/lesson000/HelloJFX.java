package cn.xiuminglee.lesson000;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * @Author Xiuming Lee
 * @Description
 */
public class HelloJFX extends Application {
    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Hello JavaFX!");
        stage.show();
    }
}
