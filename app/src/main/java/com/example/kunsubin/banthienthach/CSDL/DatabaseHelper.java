package com.example.kunsubin.banthienthach.CSDL;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by kunsubin on 5/21/2017.
 */

public class DatabaseHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "game.sqlite";
    private static final int DATABASE_VERSION = 1;
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        setForcedUpgrade();
    }
}
