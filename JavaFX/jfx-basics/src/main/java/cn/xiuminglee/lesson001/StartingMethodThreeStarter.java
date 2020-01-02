package cn.xiuminglee.lesson001;

import javafx.application.Application;

/**
 * @Author Xiuming Lee
 * @Description
 */
public class StartingMethodThreeStarter {
    public static void main(String[] args) {
        System.out.println("我是main()方法--" + Thread.currentThread().getName());
        Application.launch(StartingMethodThreeApplication.class,args);
    }
}
