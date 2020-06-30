package com.example.haitao.familyaccountkeeping;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by haitao on 2019/12/16.
 */

public class AccountSetting extends AppCompatActivity {
    private Button button_zhuxiao,button_setting_exit,setting_back;
    public  static String now_account;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        now_account=getIntent().getStringExtra("account");
        button_setting_exit=(Button)findViewById(R.id.button_setting_exit);
        button_zhuxiao=(Button)findViewById(R.id.button_zhuxiao);
        setting_back=(Button)findViewById(R.id.setting_back) ;
        button_zhuxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(AccountSetting.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        button_setting_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setting_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AccountSetting.this,AcountMain.class);
                intent.putExtra("account",now_account);
                startActivity(intent);
                finish();
            }
        });
    }
}
