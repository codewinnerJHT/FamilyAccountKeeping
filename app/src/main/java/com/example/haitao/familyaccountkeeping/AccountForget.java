package com.example.haitao.familyaccountkeeping;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by haitao on 2019/12/20.
 */

public class AccountForget extends AppCompatActivity {
    public Button button_register_sure,button_forget_exit;
    public EditText edittext_account_forget,edittext_new_pw,edittext_re_forget_pw;
    private String account,pw,repw;
    private MyDatabaseHelper dbHelper;
    private SQLiteDatabase db;
    public static final String ACCOUNT="account";
    public static final String PASSWORD="password";
    public static final String TABLE_NAME="User";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        button_register_sure=(Button)findViewById(R.id.button_register_sure);
        button_forget_exit=(Button)findViewById(R.id.button_forget_exit);

        edittext_account_forget=(EditText)findViewById(R.id.edittext_account_forget);
        edittext_new_pw=(EditText)findViewById(R.id.edittext_new_pw);
        edittext_re_forget_pw=(EditText)findViewById(R.id.edittext_re_forget_pw);
        dbHelper=new MyDatabaseHelper(this,"AccountSystem.db",null,4);
        db=dbHelper.getWritableDatabase();
        button_register_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                account=edittext_account_forget.getText().toString();
                pw=edittext_new_pw.getText().toString();
                repw=edittext_re_forget_pw.getText().toString();
                ContentValues values=new ContentValues();
                values.put(ACCOUNT,account);
                values.put(PASSWORD,repw);
                long result;
                result=db.update(TABLE_NAME, values, "account = ?", new String[] {account });
                if(result>0)
                {
                    Toast.makeText(AccountForget.this, "找回成功！", Toast.LENGTH_LONG).show();
                }
            }
        });
        button_forget_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(AccountForget.this,MainActivity.class));
            }
        });
    }
}
