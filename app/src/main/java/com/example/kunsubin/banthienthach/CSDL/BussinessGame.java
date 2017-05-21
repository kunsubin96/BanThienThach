package com.example.kunsubin.banthienthach.CSDL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by kunsubin on 5/21/2017.
 */

public class BussinessGame  extends DataAccess{
    public BussinessGame(Context context) {
        super(context);
    }

    @Override
    public void open() {
        super.open();
    }

    @Override
    public void close() {
        super.close();
    }

    @Override
    public List<?> executeSelect(String... params) {
        return null;
    }

    public List<ThanhTich> getThanhTich(){
        this.open();
        List<ThanhTich> thanhTichs=new ArrayList<>();
        String  query = "SELECT * FROM thanhtich order by diem  desc";
        Cursor cursor = this.database.rawQuery(query, null);
        try {
            while (cursor.moveToNext()) {
                ThanhTich item = new ThanhTich();

                item.setUser(cursor.getString(0));
                item.setScore(String.valueOf(cursor.getInt(1)));
                item.setGold(String.valueOf(cursor.getInt(2)));
                thanhTichs.add(item);
            }
        } finally {
            cursor.close();
            this.close();
        }
        return thanhTichs;
    }
    public boolean insertUser(String user){
        this.open();
        //String  query = "INSERT INTO thanhtich (user,diem,gold) VALUES ('"+user+"', 0,0)";
        ContentValues newValues = new ContentValues();
        newValues.put("user", user);
        newValues.put("diem", 0);
        newValues.put("gold", 0);
        long value=this.database.insert("thanhtich",null,newValues);
        this.close();
        if(value!=-1){
            return true;
        }
        return false;
    }
    public boolean updateDiem(String user,int score,int gold){
        this.open();
        ContentValues newValues = new ContentValues();
        newValues.put("diem", score);
        newValues.put("gold", gold);
        long values=this.database.update("thanhtich", newValues, "user='"+user+"'", null);
        this.close();
        if(values!=-1){
            return true;
        }
        return false;
    }
}
