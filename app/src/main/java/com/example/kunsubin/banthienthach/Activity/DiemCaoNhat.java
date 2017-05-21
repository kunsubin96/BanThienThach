package com.example.kunsubin.banthienthach.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.kunsubin.banthienthach.Adapter.AdapterThanhTich;
import com.example.kunsubin.banthienthach.CSDL.BussinessGame;
import com.example.kunsubin.banthienthach.CSDL.ThanhTich;
import com.example.kunsubin.banthienthach.R;

import java.util.List;

public class DiemCaoNhat extends AppCompatActivity {
    private Toolbar toolbar;
    private ListView listThanhTich;
    private BussinessGame bussinessGame;
    private List<ThanhTich> thanhTichList;
    private AdapterThanhTich adapterThanhTich;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diem_cao_nhat);
        init();
        //bắt sự kiện nhấn nút back <-- cho toolbar
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //show info
        bussinessGame=new BussinessGame(this);
        thanhTichList=bussinessGame.getThanhTich();
        Log.d("thanhtichsize",thanhTichList.size()+"");
        if(thanhTichList.size()>0){
            adapterThanhTich=new AdapterThanhTich(this,thanhTichList);
            listThanhTich.setAdapter(adapterThanhTich);
        }else{
            listThanhTich.setAdapter(null);
        }

    }
    public void init(){
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        listThanhTich=(ListView)findViewById(R.id.listThanhTich);
    }
}
