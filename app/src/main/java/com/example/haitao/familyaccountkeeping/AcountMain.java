package com.example.haitao.familyaccountkeeping;

/**
 * Created by haitao on 2019/12/13.
 */

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AcountMain extends AppCompatActivity {
    private ListView lv;
    private  MyDatabaseHelper myHelper;
    public static final String DB_NAME="AccountSystem.db";
    public static final String TABLE_NAME="Account";
    public static final String ID="id";
    public static final String ACCOUNT="account";
    public static final String INCOME_EXENDITURE="income_expenditure";
    public static final String BUDGET="budget";
    public static final String MONEY="money";
    public static final String PAY_WAY="pay_way";
    public static final String BOOKKEEPING_DATE="bookkeeping_date";
    public static final String REMARKS="remarks";
    private SQLiteDatabase db;
    private AccountAdapter adapter;
    private  ArrayList arrayList;
    private ArrayAdapter arrayAdapter;
    private String month;
    private List<AccountInfo> accountList=new ArrayList<>();
    public  static String now_account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview);
        final Context AppContext=this;
        now_account=getIntent().getStringExtra("account");

        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.hide();
        }

        lv=(ListView)findViewById(R.id.listView1);
        ImageView imageview_detail=(ImageView) findViewById(R.id.imageview_detail);
        ImageView imageview_search=(ImageView) findViewById(R.id.imageview_search);
        ImageView imageview_add=(ImageView) findViewById(R.id.imageview_add);
        ImageView imageview_data=(ImageView) findViewById(R.id.imageview_data);
        ImageView imageview_setting=(ImageView) findViewById(R.id.imageview_setting);
        TextView textView_souru=(TextView)findViewById(R.id.textview_souru);
        TextView textView_zhichu=(TextView)findViewById(R.id.textview_zhichu);
        Spinner time_spinner=(Spinner)findViewById(R.id.time_spinner);
        arrayList=new ArrayList<String>();
        arrayList.add("请选择月份");
        arrayList.add("一月");arrayList.add("二月");arrayList.add("三月");arrayList.add("四月");arrayList.add("五月");
        arrayList.add("六月");arrayList.add("七月");arrayList.add("八月");arrayList.add("九月");arrayList.add("十月");
        arrayList.add("十一月");arrayList.add("十二月");
        arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        time_spinner.setAdapter(arrayAdapter);

        time_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView textView_souru=(TextView)findViewById(R.id.textview_souru);
                TextView textView_zhichu=(TextView)findViewById(R.id.textview_zhichu);
                month=(String) arrayList.get(position);
                List<AccountInfo> list=getBasicInfo(month);
                adapter=new AccountAdapter(AppContext,list);
                lv.setAdapter(adapter);
                float souru1,zhichu1;
                souru1=findSumSouru(month);
                textView_souru.setText(""+souru1+"" );
                zhichu1=findSumzhuchu(month);
                textView_zhichu.setText(""+zhichu1+"" );
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        myHelper=new MyDatabaseHelper(this,DB_NAME,null,4);
        db=myHelper.getWritableDatabase();

        final List<AccountInfo> list=this.getBasicInfo("请选择月份");
        adapter=new AccountAdapter(this,list);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AccountInfo account= list.get(position);
                int ids=account.getID();
                Intent intent=new Intent(AcountMain.this,AccountOperation.class);
                intent.putExtra("account",now_account);
                intent.putExtra("id", ids);
                startActivity(intent);
            }
        });

        adapter.notifyDataSetChanged();
        float souru1,zhichu1;
        souru1=findSumSouru("请选择月份");
        textView_souru.setText(""+souru1+"" );
        zhichu1=findSumzhuchu("请选择月份");
        textView_zhichu.setText(""+zhichu1+"" );

        imageview_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AcountMain.this,AccountNew.class);
                startActivity(intent);
                finish();
            }
        });
        imageview_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AcountMain.this,AcountMain.class);
                intent.putExtra("account",now_account);
                startActivity(intent);
                finish();
            }
        });
        imageview_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AcountMain.this,AccountData.class);
                intent.putExtra("account",now_account);
                startActivity(intent);
                finish();
            }
        });
        imageview_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AcountMain.this,AccountSetting.class);
                intent.putExtra("account",now_account);
                startActivity(intent);
                finish();
            }
        });
        imageview_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AcountMain.this,AccountSearch.class);
                startActivity(intent);
            }
        });
    }

    public List<AccountInfo> getBasicInfo(String month) {
        Cursor c=null;
        if (month.equals("请选择月份")){
            c=db.query(TABLE_NAME, new String[]{},ACCOUNT+"="+now_account, null, null, null, null);
//            String sql="select * from "+TABLE_NAME+"where account"+"1"+" and bookkeeping_date like -12-";
//            c=db.rawQuery(sql,null);
//         c=db.query(TABLE_NAME,new String[]{},"bookkeeping_date like ? and account=?" ,new String[]{"%-12-%","1"},null,null,null);
        }
        else if (month.equals("一月")){
            c=db.query(TABLE_NAME, new String[]{},BOOKKEEPING_DATE+" like '%" +"-1-"+ "%'"+"and "+ACCOUNT+"="+now_account, null, null, null, null);
        }
        else if (month.equals("二月")){
            c=db.query(TABLE_NAME, new String[]{},BOOKKEEPING_DATE+" like '%" +"-2-"+ "%'"+"and "+ACCOUNT+"="+now_account, null, null, null, null);
        }
        else if (month.equals("三月")){
            c=db.query(TABLE_NAME, new String[]{},BOOKKEEPING_DATE+" like '%" +"-3-"+ "%'"+"and "+ACCOUNT+"="+now_account, null, null, null, null);
        }
        else if (month.equals("四月")){
            c=db.query(TABLE_NAME, new String[]{},BOOKKEEPING_DATE+" like '%" +"-4-"+ "%'"+"and "+ACCOUNT+"="+now_account, null, null, null, null);
        }
        else if (month.equals("五月")){
            c=db.query(TABLE_NAME, new String[]{},BOOKKEEPING_DATE+" like '%" +"-5-"+ "%'"+"and "+ACCOUNT+"="+now_account, null, null, null, null);
        }
        else if (month.equals("六月")){
            c=db.query(TABLE_NAME, new String[]{},BOOKKEEPING_DATE+" like '%" +"-6-"+ "%'"+"and "+ACCOUNT+"="+now_account, null, null, null, null);
        }
        else if (month.equals("七月")){
            c=db.query(TABLE_NAME, new String[]{},BOOKKEEPING_DATE+" like '%" +"-7-"+ "%'"+"and "+ACCOUNT+"="+now_account, null, null, null, null);
        }
        else if (month.equals("八月")){
            c=db.query(TABLE_NAME, new String[]{},BOOKKEEPING_DATE+" like '%" +"-8-"+ "%'"+"and "+ACCOUNT+"="+now_account, null, null, null, null);
        }
        else if (month.equals("九月")){
            c=db.query(TABLE_NAME, new String[]{},BOOKKEEPING_DATE+" like '%" +"-9-"+ "%'"+"and "+ACCOUNT+"="+now_account, null, null, null, null);
        }
        else if (month.equals("十月")){
            c=db.query(TABLE_NAME, new String[]{},BOOKKEEPING_DATE+" like '%" +"-10-"+ "%'"+"and "+ACCOUNT+"="+now_account, null, null, null, null);
        }
        else if (month.equals("十一月")){
            c=db.query(TABLE_NAME, new String[]{},BOOKKEEPING_DATE+" like '%" +"-11-"+ "%'"+"and "+ACCOUNT+"="+now_account, null, null, null, null);
        }
        else if (month.equals("十二月")){
            c=db.query(TABLE_NAME, new String[]{},BOOKKEEPING_DATE+" like '%" +"-12-"+ "%'"+"and "+ACCOUNT+"="+now_account, null, null, null, null);
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
    public  float findSumSouru(String month){
        float sum_souru=0;
        Cursor cursor = null ;
        if (month.equals("请选择月份")){
            cursor = db.rawQuery("select sum(money) from Account where income_expenditure=? and account=?",new String[]{"收入",now_account});
        }
        else if (month.equals("一月")){
            cursor = db.rawQuery("select sum(money) from Account where income_expenditure=? and account=? and bookkeeping_date like '%-1-%'",new String[]{"收入",now_account});
        }
        else if (month.equals("二月")){
            cursor = db.rawQuery("select sum(money) from Account where income_expenditure=? and account=? and bookkeeping_date like '%-2-%'",new String[]{"收入",now_account});
        }
        else if (month.equals("三月")){
            cursor = db.rawQuery("select sum(money) from Account where income_expenditure=? and account=? and bookkeeping_date like '%-3-%'",new String[]{"收入",now_account});
        }
        else if (month.equals("四月")){
            cursor = db.rawQuery("select sum(money) from Account where income_expenditure=? and account=? and bookkeeping_date like '%-4-%'",new String[]{"收入",now_account});
        }
        else if (month.equals("五月")){
            cursor = db.rawQuery("select sum(money) from Account where income_expenditure=? and account=? and bookkeeping_date like '%-5-%'",new String[]{"收入",now_account});
        }
        else if (month.equals("六月")){
            cursor = db.rawQuery("select sum(money) from Account where income_expenditure=? and account=? and bookkeeping_date like '%-6-%'",new String[]{"收入",now_account});
        }
        else if (month.equals("七月")){
            cursor = db.rawQuery("select sum(money) from Account where income_expenditure=? and account=? and bookkeeping_date like '%-7-%'",new String[]{"收入",now_account});
        }
        else if (month.equals("八月")){
            cursor = db.rawQuery("select sum(money) from Account where income_expenditure=? and account=? and bookkeeping_date like '%-8-%'",new String[]{"收入",now_account});
        }
        else if (month.equals("九月")){
            cursor = db.rawQuery("select sum(money) from Account where income_expenditure=? and account=? and bookkeeping_date like '%-9-%'",new String[]{"收入",now_account});
        }
        else if (month.equals("十月")){
            cursor = db.rawQuery("select sum(money) from Account where income_expenditure=? and account=? and bookkeeping_date like '%-10-%'",new String[]{"收入",now_account});
        }
        else if (month.equals("十一月")){
            cursor = db.rawQuery("select sum(money) from Account where income_expenditure=? and account=? and bookkeeping_date like '%-11-%'",new String[]{"收入",now_account});
        }
        else if (month.equals("十二月")){
            cursor = db.rawQuery("select sum(money) from Account where income_expenditure=? and account=? and bookkeeping_date like '%-12-%'",new String[]{"收入",now_account});
        }


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

    public  float findSumzhuchu(String month){
        float sum_zhichu=0;
        Cursor cursor = null ;
        db = myHelper.getWritableDatabase();
        if (month.equals("请选择月份")){
            cursor = db.rawQuery("select sum(money) from Account where income_expenditure=? and account=?",new String[]{"支出",now_account});
        }
        else if (month.equals("一月")){
            cursor = db.rawQuery("select sum(money) from Account where income_expenditure=? and account=? and bookkeeping_date like '%-1-%'",new String[]{"支出",now_account});
        }
        else if (month.equals("二月")){
            cursor = db.rawQuery("select sum(money) from Account where income_expenditure=? and account=? and bookkeeping_date like '%-2-%'",new String[]{"支出",now_account});
        }
        else if (month.equals("三月")){
            cursor = db.rawQuery("select sum(money) from Account where income_expenditure=? and account=? and bookkeeping_date like '%-3-%'",new String[]{"支出",now_account});
        }
        else if (month.equals("四月")){
            cursor = db.rawQuery("select sum(money) from Account where income_expenditure=? and account=? and bookkeeping_date like '%-4-%'",new String[]{"支出",now_account});
        }
        else if (month.equals("五月")){
            cursor = db.rawQuery("select sum(money) from Account where income_expenditure=? and account=? and bookkeeping_date like '%-5-%'",new String[]{"支出",now_account});
        }
        else if (month.equals("六月")){
            cursor = db.rawQuery("select sum(money) from Account where income_expenditure=? and account=? and bookkeeping_date like '%-6-%'",new String[]{"支出",now_account});
        }
        else if (month.equals("七月")){
            cursor = db.rawQuery("select sum(money) from Account where income_expenditure=? and account=? and bookkeeping_date like '%-7-%'",new String[]{"支出",now_account});
        }
        else if (month.equals("八月")){
            cursor = db.rawQuery("select sum(money) from Account where income_expenditure=? and account=? and bookkeeping_date like '%-8-%'",new String[]{"支出",now_account});
        }
        else if (month.equals("九月")){
            cursor = db.rawQuery("select sum(money) from Account where income_expenditure=? and account=? and bookkeeping_date like '%-9-%'",new String[]{"支出",now_account});
        }
        else if (month.equals("十月")){
            cursor = db.rawQuery("select sum(money) from Account where income_expenditure=? and account=? and bookkeeping_date like '%-10-%'",new String[]{"支出",now_account});
        }
        else if (month.equals("十一月")){
            cursor = db.rawQuery("select sum(money) from Account where income_expenditure=? and account=? and bookkeeping_date like '%-11-%'",new String[]{"支出",now_account});
        }
        else if (month.equals("十二月")){
            cursor = db.rawQuery("select sum(money) from Account where income_expenditure=? and account=? and bookkeeping_date like '%-12-%'",new String[]{"支出",now_account});
        }
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

