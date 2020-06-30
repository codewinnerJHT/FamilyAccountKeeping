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

public class AccountSearchAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<AccountInfo> list;
    private Context context;
    private MyDatabaseHelper myHelper;
    private SQLiteDatabase db;
    public static final String DB_NAME="AccountSystem.db";

    public AccountSearchAdapter(Context context, List<AccountInfo> list) {
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
            convertView = inflater.inflate(R.layout.account_search_item, null);
            viewHolder = new ViewHolder();
            viewHolder.textview_search_xuhao = (TextView) convertView.findViewById(R.id.textview_search_xuhao);
            viewHolder.textview_search_leibie = (TextView) convertView.findViewById(R.id.textview_search_leibie);
            viewHolder.textview_search_shouzhi = (TextView) convertView.findViewById(R.id.textview_search_shouzhi);
            viewHolder.textview_search_jine = (TextView) convertView.findViewById(R.id.textview_search_jine);
            viewHolder.textview_search_fangshi = (TextView) convertView.findViewById(R.id.textview_search_fangshi);
            viewHolder.textview_search_shijian = (TextView) convertView.findViewById(R.id.textview_search_shijian);
            viewHolder.textview_search_beizhu = (TextView) convertView.findViewById(R.id.textview_search_beizhu);
            viewHolder.listView = (ListView) convertView.findViewById(R.id.listView2);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.textview_search_xuhao.setText(list.get(position).getID()+"");
        viewHolder.textview_search_leibie.setText(list.get(position).getBudget());
        viewHolder.textview_search_shouzhi.setText(list.get(position).getIncome_expenditure());
        viewHolder.textview_search_jine.setText(list.get(position).getMoney());
        viewHolder.textview_search_fangshi.setText(list.get(position).getPay_way());
        viewHolder.textview_search_shijian.setText(list.get(position).getBookkeeping_date());
        viewHolder.textview_search_beizhu.setText(list.get(position).getRemarks());

        return convertView;
    }

    public static class ViewHolder {
        TextView textview_search_xuhao,textview_search_leibie,textview_search_shouzhi,textview_search_jine,textview_search_fangshi,textview_search_shijian,textview_search_beizhu;
        ListView listView;
    }
}
