package com.example.taehoon.planmaker_v2.Newbook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.taehoon.planmaker_v2.R;
import com.nhn.android.maps.NMapContext;
import com.nhn.android.maps.NMapView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static com.example.taehoon.planmaker_v2.Newbook.MakePlanActivity.place;
import static com.example.taehoon.planmaker_v2.Newbook.MakePlanActivity.response;
import static com.example.taehoon.planmaker_v2.Newbook.MakingFragment01.getUsers;

/**
 * Created by TAEHOON on 2017-05-26.
 */

public class MakingFragment04 extends Fragment {
    private ViewGroup view;

    private TextView title, start, end, place;
    private ListView userListView;
    public static ArrayAdapter<String> adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (ViewGroup)inflater.inflate(R.layout.makingplanfragment04, container,false);

        title = (TextView)view.findViewById(R.id.result_title);
        start = (TextView)view.findViewById(R.id.result_starttime);
        end = (TextView)view.findViewById(R.id.result_endtime);
        place = (TextView)view.findViewById(R.id.result_place);
        userListView = (ListView)view.findViewById(R.id.result_userlistview);

        ArrayList<String> users = getUsers();
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_single_choice, users);
        userListView.setAdapter(adapter);

        title.setText(MakePlanActivity.title);
        start.setText(new SimpleDateFormat("yyyy. MM. dd. HH:mm").format(response.getStartTime()));
        end.setText(new SimpleDateFormat("yyyy. MM. dd. HH:mm").format(response.getEndTime()));
        place.setText(MakePlanActivity.place);


        return view;
    }

}
