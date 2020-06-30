package com.example.haitao.familyaccountkeeping;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.haitao.familyaccountkeeping.AcountMain.now_account;

/**
 * Created by haitao on 2019/12/16.
 */

public class AccountSearch extends AppCompatActivity {
    private  MyDatabaseHelper myHelper;
    public static final String DB_NAME="AccountSystem.db";
    public static final String TABLE_NAME="Account";
    private SQLiteDatabase db;
    private AccountSearchAdapter adapter;
    private ListView lv;
    public static final String ID="id";
    public static final String ACCOUNT="account";
    public static final String INCOME_EXENDITURE="income_expenditure";
    public static final String BUDGET="budget";
    public static final String MONEY="money";
    public static final String PAY_WAY="pay_way";
    public static final String BOOKKEEPING_DATE="bookkeeping_date";
    public static final String REMARKS="remarks";

    private  ArrayList data_list;
    private ArrayAdapter arrayAdapter;
    private String way;
    private String ways;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
         final  Context AppContext=this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_search);


        lv=(ListView)findViewById(R.id.listView2);
        TextView textview_allzhichu=(TextView)findViewById(R.id.textview_allzhichu);
        TextView textview_allsouru=(TextView)findViewById(R.id.textview_allsouru);
        TextView textview_shengyu=(TextView)findViewById(R.id.textview_shengyu);
        Spinner spinner_search_way=(Spinner)findViewById(R.id.spinner_search_way);
        Button button_search=(Button)findViewById(R.id.button_search);


        myHelper=new MyDatabaseHelper(this,DB_NAME,null,4);
        db=myHelper.getWritableDatabase();

        data_list=new ArrayList<String>();
        data_list.add("请选择");
        data_list.add("类型");
        data_list.add("收入支出");
        data_list.add("支付方式");
        data_list.add("创建时间");


        arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,data_list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_search_way.setAdapter(arrayAdapter);

        spinner_search_way.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                way=(String) data_list.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        float allsouru,allzhichu,shengyu;
        allsouru=findSumSouru1();
        textview_allsouru.setText(""+allsouru+"");
        allzhichu=findSumzhuchu1();
        textview_allzhichu.setText(""+allzhichu+"");
        shengyu=allsouru-allzhichu;
        textview_shengyu.setText(""+shengyu+"");

        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<AccountInfo> list=getAllInfo();
                adapter=new AccountSearchAdapter(AppContext,list);
                lv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
    }
    public List<AccountInfo> getAllInfo() {
        Cursor c=null;
        TextView textview_search=(TextView)findViewById(R.id.textview_search);
        ways =textview_search.getText().toString();
        if(way.equals("类型")) {
            c=db.query(TABLE_NAME, new String[]{},BUDGET+" like '%" +ways+ "%'"+"and "+ACCOUNT+"="+now_account, null, null, null, null);
        }
        else if (way.equals("收入支出")){
            c=db.query(TABLE_NAME, new String[]{},INCOME_EXENDITURE+" like '%" +ways+ "%'"+"and "+ACCOUNT+"="+now_account, null, null, null, null);
        }
        else if (way.equals("支付方式")){
            c=db.query(TABLE_NAME, new String[]{},PAY_WAY+" like '%" +ways+ "%'"+"and "+ACCOUNT+"="+now_account, null, null, null, null);
        }
        else if (way.equals("创建时间")){
            c=db.query(TABLE_NAME, new String[]{},BOOKKEEPING_DATE+" like '%" +ways+ "%'"+"and "+ACCOUNT+"="+now_account, null, null, null, null);
        }
        else {
            c=db.query(TABLE_NAME, new String[]{},ACCOUNT+"="+now_account, null, null, null, null);
            //应该使用提示框
            Toast.makeText(AccountSearch.this, "这是查询所有！请选择查询方式", Toast.LENGTH_LONG).show();
        }
        List<AccountInfo> list=new ArrayList<AccountInfo>();
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            String account=c.getString(c.getColumnIndex(ACCOUNT));
            String income_expenditure=c.getString(c.getColumnIndex(INCOME_EXENDITURE));
            String budget=c.getString(c.getColumnIndex(BUDGET));
            String money=c.getString(c.getColumnIndex(MONEY));
            String pay_way=c.getString(c.getColumnIndex(PAY_WAY));
            String bookkeeping_date=c.getString(c.getColumnIndex(BOOKKEEPING_DATE));
            String remarks=c.getString(c.getColumnIndex(REMARKS));
            AccountInfo d=new AccountInfo(account,income_expenditure,budget,money,pay_way,bookkeeping_date,remarks);
            d.setID(c.getInt(c.getColumnIndex(ID)));
            list.add(d);
        }
        c.close();
        return list;
    }
    public  float findSumSouru1(){
        float sum_souru=0;
        Cursor cursor = null ;
        db = myHelper.getWritableDatabase();
        cursor = db.rawQuery("select sum(money) from Account where income_expenditure=? and account=?",new String[]{"收入",now_account});
        if (cursor!=null)
        {
            if (cursor.moveToFirst())
            {
                do
                {
                    sum_souru=cursor.getInt(0);
                } while (cursor.moveToNext());
            }
        }
        return sum_souru;
    }

    public  float findSumzhuchu1(){
        float sum_zhichu=0;
        Cursor cursor = null ;
        db = myHelper.getWritableDatabase();
        cursor = db.rawQuery("select sum(money) from Account where income_expenditure=? and account=?",new String[]{"支出",now_account});
        if (cursor!=null)
        {
            if (cursor.moveToFirst())
            {
                do
                {
                    sum_zhichu=cursor.getInt(0);
                } while (cursor.moveToNext());
            }
        }
        return sum_zhichu;
    }
}
