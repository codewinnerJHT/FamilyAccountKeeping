package com.example.haitao.familyaccountkeeping;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText editText_account;
    private EditText editText_password;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private CheckBox rememberPass;
    private MyDatabaseHelper dbHelper;
    public String current_user;
    public AccountAdapter adapter;
    private SQLiteDatabase db;
    public static final String TABLE_NAME="Account";
    public static final String ID="ID";
    public static final String ACCOUNT="account";
    public static final String INCOME_EXENDITURE="income_expenditure";
    public static final String BUDGET="budget";
    public static final String MONEY="money";
    public static final String PAY_WAY="pay_way";
    public static final String BOOKKEEPING_DATE="bookkeeping_date";
    public static final String REMARKS="remarks";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new MyDatabaseHelper(this,"AccountSystem.db", null,4);
        dbHelper.getWritableDatabase();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        Button button_login = (Button) findViewById(R.id.button_login);
        Button button_forget=(Button)findViewById(R.id.button_forget);
        editText_account = (EditText) findViewById(R.id.edittext_account);
        editText_password = (EditText) findViewById(R.id.edittext_password);
        rememberPass = (CheckBox) findViewById(R.id.remember_pass);
        Button button_exit = (Button) findViewById(R.id.button_exit);
        Button button_register = (Button) findViewById(R.id.button_register);
        boolean isRemember=pref.getBoolean("remember_password",false);
        if (isRemember){
            String account=pref.getString("account","");
            String password=pref.getString("password","");
            editText_account.setText(account);
            editText_password.setText(password);
            rememberPass.setChecked(true);
        }
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = editText_account.getText().toString();
                String password = editText_password.getText().toString();
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Cursor cursor = db.query("User", null, "account=? and password=?", new String[]{account, password}, null, null, null);
                int n = cursor.getCount();
                if (n > 0) {
//                    current_user = account;
                    editor=pref.edit();
                    if (rememberPass.isChecked()){
                            editor.putBoolean("remember_password",true);
                            editor.putString("account",account);
                            editor.putString("password",password);
                            } else {
                                editor.clear();
                            }
                     editor.apply();
                    Intent intent = new Intent(MainActivity.this, AcountMain.class);
                    intent.putExtra("account",account);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "密码或账号错误！请重新输入", Toast.LENGTH_SHORT).show();
                }
            }
        });
        button_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        button_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AccountForget.class);
                startActivity(intent);
            }
        });
    }
//    private List<AccountInfo>getInfo(){
//        Cursor c=db.query(TABLE_NAME,new String[]{},null,null,null,null,ID);
//        List<AccountInfo>list=new ArrayList<AccountInfo>();
//        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
//        {
//            String account=c.getString(c.getColumnIndex(ACCOUNT));
//            String income_expenditure=c.getString(c.getColumnIndex(INCOME_EXENDITURE));
//            String budget=c.getString(c.getColumnIndex(BUDGET));
//            String money=c.getString(c.getColumnIndex(MONEY));
//            String pay_way=c.getString(c.getColumnIndex(PAY_WAY));
//            String bookkeeping_date=c.getString(c.getColumnIndex(BOOKKEEPING_DATE));
//            String remarks=c.getString(c.getColumnIndex(REMARKS));
//            AccountInfo ai=new AccountInfo(account,income_expenditure,budget,money,pay_way,bookkeeping_date,remarks);
//            ai.setID(c.getInt(c.getColumnIndex(ID)));
//            list.add(ai);
//        }
//        c.close();
//        return list;
//    }
    }

