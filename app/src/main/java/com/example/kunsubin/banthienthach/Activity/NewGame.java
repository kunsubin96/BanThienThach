package com.example.kunsubin.banthienthach.Activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kunsubin.banthienthach.CSDL.BussinessGame;
import com.example.kunsubin.banthienthach.R;

public class NewGame extends AppCompatActivity {
    private Toolbar toolbar1;
    private EditText addUser;
    private Button button;
    private BussinessGame bussinessGame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        init();
        //
        //bắt sự kiện nhấn nút back <-- cho toolbar
        toolbar1.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //event buttom
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(addUser.getText().toString().trim().equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(NewGame.this);
                    builder.setMessage("Hãy điền tên vào!");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //do things
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }else{
                    bussinessGame=new BussinessGame(getApplicationContext());
                    boolean f=bussinessGame.insertUser(addUser.getText().toString().trim());
                    if(f){
                        Toast.makeText(getApplicationContext(),"Thành công",Toast.LENGTH_LONG).show();
                    }else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(NewGame.this);
                        builder.setMessage("Không tạo được. Hãy thử với tên khác!");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //do things
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                }
            }
        });
    }
    public void init(){
        toolbar1=(Toolbar)findViewById(R.id.toolbar1);
        addUser=(EditText)findViewById(R.id.addUser);
        button=(Button)findViewById(R.id.button);
    }
}
