package com.example.zhiyongjin.weather2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyListViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<WeatherGson2.DataBean> mList = new ArrayList<WeatherGson2.DataBean>();

    public MyListViewAdapter(Context context, List<WeatherGson2.DataBean> list) {
        mContext = context;
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.futre_item, null, false);
            viewHolder.week = convertView.findViewById(R.id.week);
            viewHolder.temp = convertView.findViewById(R.id.temp_tv);
            viewHolder.weather = convertView.findViewById(R.id.weather);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();


        }

        viewHolder.week.setText(mList.get(position).getWeek());
        viewHolder.temp.setText(mList.get(position).getTem());
        viewHolder.weather.setText(mList.get(position).getWea());

        return convertView;
    }

    static class ViewHolder {
        TextView week;
        TextView weather;
        TextView temp;
    }
}
