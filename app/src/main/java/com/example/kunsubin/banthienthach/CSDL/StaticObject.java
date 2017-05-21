package com.example.kunsubin.banthienthach.CSDL;

/**
 * Created by kunsubin on 5/21/2017.
 */

public class StaticObject {
    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        StaticObject.user = user;
    }

    static String user;
}
