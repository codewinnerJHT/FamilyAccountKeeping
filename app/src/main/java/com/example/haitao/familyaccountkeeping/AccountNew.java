package com.example.haitao.familyaccountkeeping;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import static com.example.haitao.familyaccountkeeping.AcountMain.now_account;

/**
 * Created by haitao on 2019/12/13.
 */

public class AccountNew  extends AppCompatActivity implements View.OnClickListener {
    private EditText edittext_add_remarks,edittext_add_money,edittext_add_date;
    private Button button_add_sure,button_add_back;
    private Spinner spinner_shouru,spinner_add_budget,spinner_add_way;
    private MyDatabaseHelper dbHelper;
    public static final String TABLE_NAME="Account";
    public static final String ACCOUNT="account";
    public static final String INCOME_EXENDITURE="income_expenditure";
    public static final String BUDGET="budget";
    public static final String MONEY="money";
    public static final String PAY_WAY="pay_way";
    public static final String BOOKKEEPING_DATE="bookkeeping_date";
    public static final String REMARKS="remarks";
    private SQLiteDatabase db;
    private  ArrayAdapter arrayAdapter;
    private String add_income,add_budget,add_way;
    private  ArrayList data_list_shopping,data_list_budget,data_list_way;
    private Calendar cal;
    private  int year,month,day;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_new);
        getDate();//获取当前时间
        dbHelper=new MyDatabaseHelper(this,"AccountSystem.db",null,4);
        db=dbHelper.getWritableDatabase();
        edittext_add_money=(EditText)findViewById(R.id.edittext_add_money);
        edittext_add_date=(EditText)findViewById(R.id.edittext_add_date);
        button_add_sure=(Button) findViewById(R.id.button_add_sure);
        button_add_back=(Button) findViewById(R.id.button_add_back);
        spinner_shouru=(Spinner)findViewById(R.id.spinner_add_shouru);
        spinner_add_budget=(Spinner)findViewById(R.id.spinner_add_budget);
        spinner_add_budget=(Spinner)findViewById(R.id.spinner_add_budget);
        edittext_add_remarks=(EditText)findViewById(R.id.edittext_add_remarks);
        spinner_add_way=(Spinner)findViewById(R.id.spinner_add_way);


        //设置为不可编辑模式
        edittext_add_date.setFocusableInTouchMode(false);
        edittext_add_date.setOnClickListener(this);
        //收入还是支出下拉列表设置
        data_list_shopping=new ArrayList<String>();
        data_list_shopping.add("请选择");
        data_list_shopping.add("支出");
        data_list_shopping.add("收入");
        arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,data_list_shopping);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_shouru.setAdapter(arrayAdapter);
        spinner_shouru.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                add_income=(String) data_list_shopping.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //类别选择下拉列表
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
        spinner_add_budget.setAdapter(arrayAdapter);
        spinner_add_budget.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                add_budget=(String) data_list_budget.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //支付方式
        data_list_way=new ArrayList<String>();
        data_list_way.add("请选择");
        data_list_way.add("微信");
        data_list_way.add("支付宝");
        data_list_way.add("银行卡");
        data_list_way.add("现金");
        data_list_way.add("其他");
        arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,data_list_way);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_add_way.setAdapter(arrayAdapter);
        spinner_add_way.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                add_way=(String) data_list_way.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        button_add_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((add_income.equals("请选择"))||(add_budget.equals("请选择"))||(add_way.equals("请选择"))) {
                    Toast.makeText(AccountNew.this, "添加失败！请选择！", Toast.LENGTH_LONG).show();
                }
                else {
                    ContentValues values = new ContentValues();
                    values.put(ACCOUNT, now_account);
                    values.put(BUDGET, add_budget);
                    values.put(INCOME_EXENDITURE, add_income);
                    values.put(MONEY, edittext_add_money.getText().toString());
                    values.put(PAY_WAY, add_way);
                    values.put(REMARKS,edittext_add_remarks.getText().toString());
                    values.put(BOOKKEEPING_DATE, edittext_add_date.getText().toString());
                    long result;
                    result = db.insert(TABLE_NAME, null, values);
                    if (result > 0) {
                        Toast.makeText(AccountNew.this, "添加成功！", Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(AccountNew.this, AcountMain.class);
                        intent.putExtra("account",now_account);
                        startActivity(intent);
                        finish();
                    }

                }
            }
        });
        button_add_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AccountNew.this, AcountMain.class);
                intent.putExtra("account",now_account);
                startActivity(intent);
                finish();
            }
        });
    }

    private void  getDate(){
        cal= Calendar.getInstance();
        year=cal.get(Calendar.YEAR);
        month=cal.get(Calendar.MONTH);
        day=cal.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edittext_add_date:
                DatePickerDialog.OnDateSetListener listener= new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        edittext_add_date.setText(year+"-"+(++month)+"-"+dayOfMonth);
                    }
                };
                DatePickerDialog dialog=new DatePickerDialog(AccountNew.this,listener,year,month,day);
                dialog.show();
                break;
                default:break;
        }
    }
}
