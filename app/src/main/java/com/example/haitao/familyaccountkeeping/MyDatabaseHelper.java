package com.example.haitao.familyaccountkeeping;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by haitao on 2019/12/7.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {
    public  static final String CEARTE_USER="create table User("
            +"id integer primary key autoincrement,"
            +"account text,"
            +"password text,"
            +"number text unique)";
    public static final String CREATE_ACCOUNT="create table Account("
            +"id integer primary key autoincrement,"
            +"account text,"
            + "income_expenditure text,"
            +"budget text,"
            +"money real,"
            + "pay_way text,"
            +"remarks text,"
            +"bookkeeping_date date)";
    private Context mContext;
    public  MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,int version)
    {
        super(context,name,factory,version);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CEARTE_USER);
        db.execSQL(CREATE_ACCOUNT);
        Toast.makeText(mContext,"Create succeeded",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists User");
        db.execSQL("drop table if exists Account");
        onCreate(db);
    }
}
