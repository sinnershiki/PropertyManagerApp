package com.example.sugano.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Sugano on 2014/09/05.
 */
public class CustomAdapter extends ArrayAdapter<CustomData> {
    private LayoutInflater layoutInflater_;

    public CustomAdapter(Context context, int textViewResourceId, List<CustomData> objects) {
        super(context, textViewResourceId, objects);
        layoutInflater_ = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 特定の行(position)のデータを得る
        CustomData item = (CustomData)getItem(position);

        // convertViewは使い回しされている可能性があるのでnullの時だけ新しく作る
        if (null == convertView) {
            convertView = layoutInflater_.inflate(R.layout.custom_listview, null);
        }
        // CustomDataのデータをViewの各Widgetにセットする
        TextView tvPerson;
        tvPerson = (TextView)convertView.findViewById(R.id.person);
        tvPerson.setText(item.getPerson());

        // CustomDataのデータをViewの各Widgetにセットする
        TextView tvProperty;
        tvProperty = (TextView)convertView.findViewById(R.id.property);
        tvProperty.setText(item.getProperty());

        // CustomDataのデータをViewの各Widgetにセットする
        TextView tvFromDate;
        tvFromDate = (TextView)convertView.findViewById(R.id.from_date);
        tvFromDate.setText(item.getFromDate());

        // CustomDataのデータをViewの各Widgetにセットする
        TextView tvPeriodDate;
        tvPeriodDate = (TextView)convertView.findViewById(R.id.period_date);
        tvPeriodDate.setText(item.getPeriodDate());

        //背景色設定

        if(item.getIsLending() == 1){
            convertView.setBackgroundColor(Color.rgb(231, 232,226));
        }else{
            convertView.setBackgroundColor(Color.rgb(255,255,255));
        }



        return convertView;
    }
}
