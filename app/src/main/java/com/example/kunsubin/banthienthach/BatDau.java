package com.example.kunsubin.banthienthach;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.kunsubin.banthienthach.Activity.DiemCaoNhat;
import com.example.kunsubin.banthienthach.Activity.NewGame;

public class BatDau extends Activity {
    private Button start;
    private Button diemcaonhat;
    private Button thoat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellcom);
        init();
       /* BussinessGame bussinessGame=new BussinessGame(this);
        boolean f=bussinessGame.updateDiem("anhky",897,97);
        Toast.makeText(this,String.valueOf(f),Toast.LENGTH_LONG).show();*/
        //nhấn new game
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BatDau.this,NewGame.class);
                startActivity(intent);
            }
        });
        //nhấn điểm cao nhất
        diemcaonhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BatDau.this,DiemCaoNhat.class);
                startActivity(intent);
            }
        });
        //nhấn thoát
        thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });

    }
    private void init(){
        start=(Button)findViewById(R.id.start);
        diemcaonhat=(Button)findViewById(R.id.diemcaonhat);
        thoat=(Button)findViewById(R.id.thoat);
    }
}
