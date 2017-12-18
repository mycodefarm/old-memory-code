package com.jimo.mvs;

import android.app.Activity;

import java.util.ArrayList;

/**
 * Created by dell on 2016/8/3.
 */
public class APPConst {

    public static String URL = "http://192.168.1.127:8080/";

    private static ArrayList<Activity> list = new ArrayList<>();

    public static void addOneActivity(Activity ac) {
        list.add(ac);
    }

    public static void removeOneActivity(Activity ac) {
        list.remove(ac);
    }

    public static void exitApp() {
        for (Activity c : list
                ) {
            c.finish();
        }
    }
}
