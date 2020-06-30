package com.example.haitao.familyaccountkeeping;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by haitao on 2019/12/14.
 */

public class AccountOperation extends AppCompatActivity implements View.OnClickListener{
    private int id;
    private SQLiteDatabase db;
    private MyDatabaseHelper myHelper;
    public static final String DB_NAME="AccountSystem.db";
    public static final String TABLE_NAME="Account";
    public static final String ID="id";
    public static final String ACCOUNT="account";
    public static final String INCOME_EXENDITURE="income_expenditure";
    public static final String BUDGET="budget";
    public static final String MONEY="money";
    public static final String PAY_WAY="pay_way";
    public static final String BOOKKEEPING_DATE="bookkeeping_date";
    public static final  String REMARKS ="remarks";
    public TextView edittext_operation_money,edittext_operation_date,edittext_operation_remarks;
    public Button btn_save,btn_del;
    private String edit_income,edit_budget,edit_way;;
    private ArrayAdapter arrayAdapter;
    private ArrayList data_list_shopping,data_list_budget,data_list_way;
    private  int year,month,day;
    private Calendar cal;
    public  static String now_account;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation);

        myHelper=new MyDatabaseHelper(this, DB_NAME, null,4);
        db=myHelper.getWritableDatabase();
        now_account=getIntent().getStringExtra("account");
        Spinner spinner_edit_shouru=(Spinner)findViewById(R.id.spinner_edit_shouru);
        Spinner spinner_edit_budget=(Spinner)findViewById(R.id.spinner_edit_budget);
        Spinner spinner_edit_way=(Spinner)findViewById(R.id.spinner_edit_way);
        edittext_operation_money=(TextView)findViewById(R.id.edittext_operation_money);
        edittext_operation_date=(TextView)findViewById(R.id.edittext_operation_date);
        edittext_operation_remarks=(TextView)findViewById(R.id.edittext_operation_remarks);
        btn_save=(Button)findViewById(R.id.btn_save);
        btn_del=(Button)findViewById(R.id.btn_del);
        id=getIntent().getIntExtra("id",0);


        edittext_operation_date.setFocusableInTouchMode(false);//设置为不可编辑模式
        edittext_operation_date.setOnClickListener(this);
        getDate();
        data_list_shopping=new ArrayList<String>();
        data_list_shopping.add("请选择");
        data_list_shopping.add("支出");
        data_list_shopping.add("收入");
        arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,data_list_shopping);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_edit_shouru.setAdapter(arrayAdapter);
        spinner_edit_shouru.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                edit_income=(String) data_list_shopping.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        data_list_budget=new ArrayList<String>();
        data_list_budget.add("请选择");
        data_list_budget.add("餐饮");
        data_list_budget.add("服饰");
        data_list_budget.add("购物");
        data_list_budget.add("交通");
        data_list_budget.add("娱乐");
        data_list_budget.add("日用");
        data_list_budget.add("工资");
        data_list_budget.add("兼职");
        data_list_budget.add("理财");
        data_list_budget.add("其他");
        arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,data_list_budget);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_edit_budget.setAdapter(arrayAdapter);
        spinner_edit_budget.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                edit_budget=(String) data_list_budget.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        data_list_way=new ArrayList<String>();
        data_list_way.add("请选择");
        data_list_way.add("微信");
        data_list_way.add("支付宝");
        data_list_way.add("银行卡");
        data_list_way.add("现金");
        data_list_way.add("其他");
        arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,data_list_way);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_edit_way.setAdapter(arrayAdapter);
        spinner_edit_way.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                edit_way=(String) data_list_way.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        getAccountInfo(id,spinner_edit_shouru,spinner_edit_budget,spinner_edit_way);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues value=new ContentValues();
                value.put(BUDGET, edit_budget);
//                value.put(INCOME_EXENDITURE, edittext_operation_income.getText().toString());
                value.put(INCOME_EXENDITURE,edit_income);
                value.put(MONEY, edittext_operation_money.getText().toString());
                value.put(PAY_WAY, edit_way);
                value.put(BOOKKEEPING_DATE, edittext_operation_date.getText().toString());
                value.put(REMARKS,edittext_operation_remarks.getText().toString());
                long result;
                result=db.update(TABLE_NAME, value, "id="+id, null);
                if(result>0)
                {
                    Intent intent=new Intent(AccountOperation.this,AcountMain.class);
                    intent.putExtra("account",now_account);
                    startActivity(intent);
                    Toast.makeText(AccountOperation.this, "修改成功！", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog=new AlertDialog.Builder(AccountOperation.this);
                dialog.setTitle("删除提示");
                dialog.setMessage("确认删除吗？");
                dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        long result;
                        result=db.delete(TABLE_NAME, "id="+id, null);
                        if(result>0)
                        {
                            Toast.makeText(AccountOperation.this, "删除成功！", Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(AccountOperation.this,AcountMain.class);
                            intent.putExtra("account",now_account);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();
            }
        });

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edittext_operation_date:
                DatePickerDialog.OnDateSetListener listener= new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        edittext_operation_date.setText(year+"-"+(++month)+"-"+dayOfMonth);
                    }
                };
                DatePickerDialog dialog=new DatePickerDialog(AccountOperation.this,listener,year,month,day);
                dialog.show();
                break;
            default:break;
        }
    }
    private void  getDate(){
        cal= Calendar.getInstance();
        year=cal.get(Calendar.YEAR);
        month=cal.get(Calendar.MONTH);
        day=cal.get(Calendar.DAY_OF_MONTH);
    }

    private void getAccountInfo(int id,Spinner spinner1,Spinner spinner2,Spinner spinner3) {
        Cursor c=db.query(TABLE_NAME,new String[]{},"id="+id,null,null,null,null);
        if(c.moveToFirst())
        {
            String account=c.getString(c.getColumnIndex(ACCOUNT));
            String income_expenditure=c.getString(c.getColumnIndex(INCOME_EXENDITURE));
            String budget=c.getString(c.getColumnIndex(BUDGET));
            String money=c.getString(c.getColumnIndex(MONEY));
            String pay_way=c.getString(c.getColumnIndex(PAY_WAY));
            String bookkeeping_date=c.getString(c.getColumnIndex(BOOKKEEPING_DATE));
            String remarks=c.getString(c.getColumnIndex(REMARKS));
            AccountInfo d=new AccountInfo(account,income_expenditure,budget,money,pay_way,bookkeeping_date,remarks);
            d.setID(c.getInt(c.getColumnIndex("id")));

            setSpinnerItemSelectedByValue(spinner1,d.getIncome_expenditure());
            setSpinnerItemSelectedByValue(spinner2,d.getBudget());
            setSpinnerItemSelectedByValue(spinner3,d.getPay_way());
            edittext_operation_money.setText(d.getMoney());
            edittext_operation_remarks.setText(d.getRemarks());
            edittext_operation_date.setText(d.getBookkeeping_date());
        }
        c.close();
    }
    public static void setSpinnerItemSelectedByValue(Spinner spinner,String value){
        SpinnerAdapter apsAdapter= spinner.getAdapter(); //得到SpinnerAdapter对象
        int k= apsAdapter.getCount();
        for(int i=0;i<k;i++){
            if(value.equals(apsAdapter.getItem(i).toString())){
                spinner.setSelection(i,true);// 默认选中项
                break;
            }
        }
    }
}
