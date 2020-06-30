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
 * Created by haitao on 2019/12/12.
 */

public class RegisterActivity extends AppCompatActivity {
    private EditText ed_account,ed_password,ed_number;
    private Button button_register,button_back;
    private MyDatabaseHelper dbHelper;
    public static final String TABLE_NAME="User";
    public static final String ACCOUNT="account";
    public static final String PASSWORD="password";
    public static final String NUMBER="number";
    private SQLiteDatabase db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper=new MyDatabaseHelper(this,"AccountSystem.db",null,4);
        db=dbHelper.getWritableDatabase();
        ed_account=(EditText)findViewById(R.id.edittext_register_account);
        ed_password=(EditText)findViewById(R.id.edittext_register_pw);
        ed_number=(EditText)findViewById(R.id.edittext_number);
        button_register=(Button)findViewById(R.id.button_register);
        button_back=(Button)findViewById(R.id.button_register_exit);

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values=new ContentValues();
                values.put(ACCOUNT,ed_account.getText().toString());
                values.put(PASSWORD,ed_password.getText().toString());
                values.put(NUMBER,ed_number.getText().toString());
                long result;
                result=db.insert(TABLE_NAME,null,values);
                if(result>0)
                {
                    Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_LONG).show();
                }
            }
        });
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
            }
        });
    }
}
