package com.example.haitao.familyaccountkeeping;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Calendar;

import static com.example.haitao.familyaccountkeeping.AcountMain.now_account;

/**
 * Created by haitao on 2019/12/19.
 */

public class AccountDataPay extends AppCompatActivity {
    private TextView textview_gongzi,textview_jianzhi,textview_licai,textview_other;
    private ProgressBar progressbar_gongzi,progressbar_jianzhi,progressbar_licai,progressbar_other;
    private Button button_data_zhichu2,button_data_shouru2,button_back_data2;
    private String month;
    private Calendar cal;
    private SQLiteDatabase db;
    private  MyDatabaseHelper myHelper;
    public static final String DB_NAME="AccountSystem.db";
    public  static String now_account;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_data_pay);
        now_account=getIntent().getStringExtra("account");
        textview_gongzi=(TextView)findViewById(R.id.textview_gongzi);
        textview_jianzhi=(TextView)findViewById(R.id.textview_jianzhi);
        textview_licai=(TextView)findViewById(R.id.textview_licai);
        textview_other=(TextView)findViewById(R.id.textview_other);

        button_data_zhichu2=(Button)findViewById(R.id.button_data_zhichu2);
        button_data_shouru2=(Button)findViewById(R.id.button_data_shouru2);
        button_back_data2=(Button)findViewById(R.id.button_back_data2);

        progressbar_gongzi=(ProgressBar)findViewById(R.id.progressbar_gongzi);
        progressbar_jianzhi=(ProgressBar)findViewById(R.id.progressbar_jianzhi);
        progressbar_licai=(ProgressBar)findViewById(R.id.progressbar_licai);
        progressbar_other=(ProgressBar)findViewById(R.id.progressbar_other);

        button_data_zhichu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AccountDataPay.this,AccountData.class);
                intent.putExtra("account",now_account);
                startActivity(intent);
                finish();
            }
        });

        button_data_shouru2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AccountDataPay.this,AccountDataPay.class);
                intent.putExtra("account",now_account);
                startActivity(intent);
                finish();
            }
        });
        button_back_data2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AccountDataPay.this,AcountMain.class);
                intent.putExtra("account",now_account);
                startActivity(intent);
                finish();
            }
        });
        cal= Calendar.getInstance();
        month=(cal.get(Calendar.MONTH)+1)+"";
        Log.d("MONTH",month);
        //工资数据
        float monthpayall=0;
        float gongzi=0;
        float gongzi_data=0;
        myHelper=new MyDatabaseHelper(this,DB_NAME,null,4);
        db = myHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select sum(money) from Account where income_expenditure=? and account=? and bookkeeping_date like '%" +"-"+month+"-"+ "%'",new String[]{"收入",now_account});
        Cursor cursor11 = db.rawQuery("select sum(money) from Account where income_expenditure=? and account=? and budget=? and bookkeeping_date like '%" +"-"+month+"-"+ "%'",new String[]{"收入",now_account,"工资"});
        if (cursor!=null)
        {
            if (cursor.moveToFirst())
            {
                do
                {
                    monthpayall=cursor.getInt(0);
                } while (cursor.moveToNext());
            }
        }
        if (cursor11!=null)
        {
            if (cursor11.moveToFirst())
            {
                do
                {
                    gongzi=cursor11.getInt(0);
                } while (cursor11.moveToNext());
            }
        }
        gongzi_data=(gongzi/monthpayall)*100;
        int progress11=0;
        progress11=(int)gongzi_data;
        progressbar_gongzi.setProgress(progress11);
        textview_gongzi.setText(progress11+"%");


        //兼职数据
        float jianzhi=0;
        float jianzhi_data=0;
        myHelper=new MyDatabaseHelper(this,DB_NAME,null,4);
        db = myHelper.getWritableDatabase();
        Cursor cursor22 = db.rawQuery("select sum(money) from Account where income_expenditure=? and account=? and budget=? and bookkeeping_date like '%" +"-"+month+"-"+ "%'",new String[]{"收入",now_account,"兼职"});
        if (cursor22!=null)
        {
            if (cursor22.moveToFirst())
            {
                do
                {
                    jianzhi=cursor22.getInt(0);
                } while (cursor22.moveToNext());
            }
        }
        jianzhi_data=(jianzhi/monthpayall)*100;
        int progress22=0;
        progress22=(int)jianzhi_data;
        progressbar_jianzhi.setProgress(progress22);
        textview_jianzhi.setText(progress22+"%");


        //理财数据
        float licai=0;
        float licai_data=0;
        myHelper=new MyDatabaseHelper(this,DB_NAME,null,4);
        db = myHelper.getWritableDatabase();
        Cursor cursor33 = db.rawQuery("select sum(money) from Account where income_expenditure=? and account=? and budget=? and bookkeeping_date like '%" +"-"+month+"-"+ "%'",new String[]{"收入",now_account,"理财"});
        if (cursor33!=null)
        {
            if (cursor33.moveToFirst())
            {
                do
                {
                    licai=cursor33.getInt(0);
                } while (cursor33.moveToNext());
            }
        }
        licai_data=(licai/monthpayall)*100;
        int progress33=0;
        progress33=(int)licai_data;
        progressbar_licai.setProgress(progress33);
        textview_licai.setText(progress33+"%");

        //其他数据
        float other=0;
        float other_data=0;
        myHelper=new MyDatabaseHelper(this,DB_NAME,null,4);
        db = myHelper.getWritableDatabase();
        Cursor cursor44 = db.rawQuery("select sum(money) from Account where income_expenditure=? and account=? and budget=? and bookkeeping_date like '%" +"-"+month+"-"+ "%'",new String[]{"收入",now_account,"其他"});
        if (cursor44!=null)
        {
            if (cursor44.moveToFirst())
            {
                do
                {
                    other=cursor44.getInt(0);
                } while (cursor44.moveToNext());
            }
        }
        other_data=(other/monthpayall)*100;
        int progress44=0;
        progress44=(int)other_data;
        progressbar_other.setProgress(progress44);
        textview_other.setText(progress44+"%");
    }
}
