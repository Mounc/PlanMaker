package com.example.taehoon.planmaker_v2.Others;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.taehoon.planmaker_v2.Data.MyPlan;
import com.example.taehoon.planmaker_v2.R;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by TAEHOON on 2017-06-05.
 */

public class SearchListViewAdapter extends BaseAdapter {

    private ArrayList<MyPlan> list = new ArrayList<>();

    public SearchListViewAdapter(){

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_search_item, parent, false);
        }

        TextView starttime = (TextView)convertView.findViewById(R.id.search_item_starttime);
        TextView endtime = (TextView)convertView.findViewById(R.id.search_item_endtime);
        TextView title = (TextView)convertView.findViewById(R.id.search_item_title);

        MyPlan item = list.get(position);

        if (item.getAllday()==1) {
            endtime.setText("");
            starttime.setText("All day");
        } else {
            starttime.setText(item.getStartTimeText());
            endtime.setText(item.getEndTimeText());
        }


        title.setText(item.getTitle());

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    public void addItem(MyPlan item){
        list.add(item);
    }

    public void clear(){
        list.clear();
    }
}
