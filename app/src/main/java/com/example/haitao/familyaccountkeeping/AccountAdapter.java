package com.example.haitao.familyaccountkeeping;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by haitao on 2019/11/26.
 */

public class AccountAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<AccountInfo> list;
    private Context context;
    private MyDatabaseHelper myHelper;
    private SQLiteDatabase db;
    public static final String DB_NAME="AccountSystem.db";

    public AccountAdapter(Context context, List<AccountInfo> list) {
        inflater = LayoutInflater.from(context);
        this.list = list;
        this.context=context;
        myHelper=new MyDatabaseHelper(context, DB_NAME, null, 4);
        db=myHelper.getWritableDatabase();
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.account_item, null);
            viewHolder = new ViewHolder();

            viewHolder.textview_budget1 = (TextView) convertView.findViewById(R.id.textview_budget1);
            viewHolder.textview_income = (TextView) convertView.findViewById(R.id.textview_income);
            viewHolder.textview_money = (TextView) convertView.findViewById(R.id.textview_money);
            viewHolder.listView = (ListView) convertView.findViewById(R.id.listView1);
            viewHolder.account_image=(ImageView)convertView.findViewById(R.id.account_image) ;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textview_budget1.setText(list.get(position).getBudget());
        if (list.get(position).getBudget().equals("餐饮"))
        {
            viewHolder.account_image.setImageResource(R.drawable.canyin);
        }
        else if (list.get(position).getBudget().equals("服饰")){
            viewHolder.account_image.setImageResource(R.drawable.fushi);
        } else if (list.get(position).getBudget().equals("购物")){
            viewHolder.account_image.setImageResource(R.drawable.gouwu);
        }
        else if (list.get(position).getBudget().equals("交通")){
            viewHolder.account_image.setImageResource(R.drawable.jiaotong);
        }
        else if (list.get(position).getBudget().equals("娱乐")){
            viewHolder.account_image.setImageResource(R.drawable.yule);
        }
        else if (list.get(position).getBudget().equals("日用")){
            viewHolder.account_image.setImageResource(R.drawable.riyong);
        }
        else if (list.get(position).getBudget().equals("工资")){
            viewHolder.account_image.setImageResource(R.drawable.gongzi);
        }
        else if (list.get(position).getBudget().equals("兼职")){
            viewHolder.account_image.setImageResource(R.drawable.jianzhi);
        }
        else if (list.get(position).getBudget().equals("理财")){
            viewHolder.account_image.setImageResource(R.drawable.licai);
        }
        else if (list.get(position).getBudget().equals("其他")){
            viewHolder.account_image.setImageResource(R.drawable.qita);
        }

        if (list.get(position).getIncome_expenditure().equals("支出")){
            viewHolder.textview_income.setText("-");
        }
        else {
            viewHolder.textview_income.setText("+");
        }
        viewHolder.textview_money.setText(list.get(position).getMoney());
        return convertView;
    }

    public static class ViewHolder {
        TextView textview_budget1,textview_income,textview_money;
        ImageView account_image;
        ListView listView;
    }
}
