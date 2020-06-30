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
 * Created by haitao on 2019/12/18.
 */

public class AccountData extends AppCompatActivity {
    private TextView textview_canyin,textview_fushi,textview_shopping,textview_jiaotong,textview_yule,textview_riyong,textview_qita;
    private ProgressBar progressbar_canyin,progressbar_fushi,progressbar_shopping,progressbar_jiaotong,progressbar_yule,progressbar_riyong,progressbar_qita;
    private String month;
    private Calendar cal;
    private SQLiteDatabase db;
    private  MyDatabaseHelper myHelper;
    public static final String DB_NAME="AccountSystem.db";
    public Button button_data_zhichu1,button_data_shouru1,button_back_data1;
    public  static String now_account;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        now_account=getIntent().getStringExtra("account");

        textview_canyin=(TextView)findViewById(R.id.textview_canyin);
        textview_fushi=(TextView)findViewById(R.id.textview_fushi);
        textview_shopping=(TextView)findViewById(R.id.textview_shopping);
        textview_jiaotong=(TextView)findViewById(R.id.textview_jiaotong);
        textview_yule=(TextView)findViewById(R.id.textview_yule);
        textview_riyong=(TextView)findViewById(R.id.textview_riyong);
        textview_qita=(TextView)findViewById(R.id.textview_qita);

        progressbar_canyin=(ProgressBar)findViewById(R.id.progressbar_canyin);
        progressbar_fushi=(ProgressBar)findViewById(R.id.progressbar_fushi);
        progressbar_shopping=(ProgressBar)findViewById(R.id.progressbar_shopping);
        progressbar_jiaotong=(ProgressBar)findViewById(R.id.progressbar_jiaotong);
        progressbar_yule=(ProgressBar)findViewById(R.id.progressbar_yule);
        progressbar_riyong=(ProgressBar)findViewById(R.id.progressbar_riyong);
        progressbar_qita=(ProgressBar)findViewById(R.id.progressbar_qita);

        button_data_zhichu1=(Button)findViewById(R.id.button_data_zhichu1);
        button_data_shouru1=(Button)findViewById(R.id.button_data_shouru1);
        button_back_data1=(Button)findViewById(R.id.button_back_data1);
        button_data_zhichu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AccountData.this,AccountData.class);
                intent.putExtra("account",now_account);
                startActivity(intent);
                finish();
            }
        });
        button_data_shouru1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AccountData.this,AccountDataPay.class);
                intent.putExtra("account",now_account);
                startActivity(intent);
                finish();
            }
        });
        button_back_data1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AccountData.this,AcountMain.class);
                intent.putExtra("account",now_account);
                startActivity(intent);
                finish();
            }
        });

        cal= Calendar.getInstance();
        month=(cal.get(Calendar.MONTH)+1)+"";
        Log.d("MONTH",month);
        //餐饮比例
        float monthall=0;
        float canyin=0;
        float canyin_data=0;
        myHelper=new MyDatabaseHelper(this,DB_NAME,null,4);
        db = myHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select sum(money) from Account where income_expenditure=? and account=? and bookkeeping_date like '%" +"-"+month+"-"+ "%'",new String[]{"支出",now_account});
        Cursor cursor1 = db.rawQuery("select sum(money) from Account where income_expenditure=? and account=? and budget=? and bookkeeping_date like '%" +"-"+month+"-"+ "%'",new String[]{"支出",now_account,"餐饮"});
        if (cursor!=null)
        {
            if (cursor.moveToFirst())
            {
                do
                {
                    monthall=cursor.getInt(0);
                } while (cursor.moveToNext());
            }
        }
        if (cursor1!=null)
        {
            if (cursor1.moveToFirst())
            {
                do
                {
                    canyin=cursor1.getInt(0);
                } while (cursor1.moveToNext());
            }
        }
        canyin_data=(canyin/monthall)*100;
        int progress1=0;
        progress1=(int)canyin_data;
        progressbar_canyin.setProgress(progress1);
        textview_canyin.setText(progress1+"%");
        //服饰比例
        float fushi=0;
        float fushi_data=0;
        myHelper=new MyDatabaseHelper(this,DB_NAME,null,4);
        db = myHelper.getWritableDatabase();
        Cursor cursor2 = db.rawQuery("select sum(money) from Account where income_expenditure=? and account=? and budget=? and bookkeeping_date like '%" +"-"+month+"-"+ "%'",new String[]{"支出",now_account,"服饰"});
        if (cursor2!=null)
        {
            if (cursor2.moveToFirst())
            {
                do
                {
                    fushi=cursor2.getInt(0);
                } while (cursor2.moveToNext());
            }
        }
        fushi_data=(fushi/monthall)*100;
        int progress2=0;
        progress2=(int)fushi_data;
        progressbar_fushi.setProgress(progress2);
        textview_fushi.setText(progress2+"%");
        //购物比例
        float gouwu=0;
        float gouwu_data=0;
        myHelper=new MyDatabaseHelper(this,DB_NAME,null,4);
        db = myHelper.getWritableDatabase();
        Cursor cursor3 = db.rawQuery("select sum(money) from Account where income_expenditure=? and account=? and budget=? and bookkeeping_date like '%" +"-"+month+"-"+ "%'",new String[]{"支出",now_account,"购物"});
        if (cursor3!=null)
        {
            if (cursor3.moveToFirst())
            {
                do
                {
                    gouwu=cursor3.getInt(0);
                } while (cursor3.moveToNext());
            }
        }
        gouwu_data=(gouwu/monthall)*100;
        int progress3=0;
        progress3=(int)gouwu_data;
        progressbar_shopping.setProgress(progress3);
        textview_shopping.setText(progress3+"%");

        //交通比例
        float jiaotong=0;
        float jiaotong_data=0;
        myHelper=new MyDatabaseHelper(this,DB_NAME,null,4);
        db = myHelper.getWritableDatabase();
        Cursor cursor4 = db.rawQuery("select sum(money) from Account where income_expenditure=? and account=? and budget=? and bookkeeping_date like '%" +"-"+month+"-"+ "%'",new String[]{"支出",now_account,"交通"});
        if (cursor4!=null)
        {
            if (cursor4.moveToFirst())
            {
                do
                {
                    jiaotong=cursor4.getInt(0);
                } while (cursor4.moveToNext());
            }
        }
        jiaotong_data=(jiaotong/monthall)*100;
        int progress4=0;
        progress4=(int)jiaotong_data;
        progressbar_jiaotong.setProgress(progress4);
        textview_jiaotong.setText(progress4+"%");

        //娱乐比例
        float yule=0;
        float yule_data=0;
        myHelper=new MyDatabaseHelper(this,DB_NAME,null,4);
        db = myHelper.getWritableDatabase();
        Cursor cursor5 = db.rawQuery("select sum(money) from Account where income_expenditure=? and account=? and budget=? and bookkeeping_date like '%" +"-"+month+"-"+ "%'",new String[]{"支出",now_account,"娱乐"});
        if (cursor5!=null)
        {
            if (cursor5.moveToFirst())
            {
                do
                {
                    yule=cursor5.getInt(0);
                } while (cursor5.moveToNext());
            }
        }
        yule_data=(yule/monthall)*100;
        int progress5=0;
        progress5=(int)yule_data;
        progressbar_yule.setProgress(progress5);
        textview_yule.setText(progress5+"%");

        //日用比例
        float riyong=0;
        float riyong_data=0;
        myHelper=new MyDatabaseHelper(this,DB_NAME,null,4);
        db = myHelper.getWritableDatabase();
        Cursor cursor6 = db.rawQuery("select sum(money) from Account where income_expenditure=? and account=? and budget=? and bookkeeping_date like '%" +"-"+month+"-"+ "%'",new String[]{"支出",now_account,"日用"});
        if (cursor6!=null)
        {
            if (cursor6.moveToFirst())
            {
                do
                {
                    riyong=cursor6.getInt(0);
                } while (cursor6.moveToNext());
            }
        }
        riyong_data=(riyong/monthall)*100;
        int progress6=0;
        progress6=(int)riyong_data;
        progressbar_riyong.setProgress(progress6);
        textview_riyong.setText(progress6+"%");

        //其他比例
        float rita=0;
        float rita_data=0;
        myHelper=new MyDatabaseHelper(this,DB_NAME,null,4);
        db = myHelper.getWritableDatabase();
        Cursor cursor7 = db.rawQuery("select sum(money) from Account where income_expenditure=? and account=? and budget=? and bookkeeping_date like '%" +"-"+month+"-"+ "%'",new String[]{"支出",now_account,"其他"});
        if (cursor7!=null)
        {
            if (cursor7.moveToFirst())
            {
                do
                {
                    rita=cursor7.getInt(0);
                } while (cursor7.moveToNext());
            }
        }
        rita_data=(rita/monthall)*100;
        int progress7=0;
        progress7=(int)rita_data;
        progressbar_qita.setProgress(progress7);
        textview_qita.setText(progress7+"%");
    }
}
