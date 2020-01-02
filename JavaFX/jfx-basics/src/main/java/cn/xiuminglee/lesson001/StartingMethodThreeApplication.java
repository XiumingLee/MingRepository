package cn.xiuminglee.lesson001;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * @Author Xiuming Lee
 * @Description
 */
public class StartingMethodThreeApplication extends Application {

    @Override
    public void init() throws Exception {
        System.out.println("我是init()方法--" + Thread.currentThread().getName());
    }

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("我是start()方法--" + Thread.currentThread().getName());
        stage.setTitle("Hello World!");
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        System.out.println("我是stop()方法--" + Thread.currentThread().getName());
    }
}
