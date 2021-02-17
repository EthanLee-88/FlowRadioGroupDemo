package com.ethan.flowradiogroupdemo.ui;

import android.app.Application;

/*******
 * created by Ethan Lee
 * on 2021/2/17
 *******/
public class FlowApplication extends Application {

    public static FlowApplication mFlowApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mFlowApplication = this;
    }
}
