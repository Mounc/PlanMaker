package com.example.taehoon.planmaker_v2.MainBase;

import android.app.Activity;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.taehoon.planmaker_v2.Database.DBHelper;
import com.example.taehoon.planmaker_v2.Newbook.NewPlanActivity;
import com.example.taehoon.planmaker_v2.R;

import java.text.SimpleDateFormat;

import static com.example.taehoon.planmaker_v2.MainBase.MainActivity.onPlan;


/**
 * Created by TAEHOON on 2017-03-16.
 */

public class MyTabFragment02 extends Fragment {
    private ViewGroup view;
    private TextView title;
    private TextView allday;
    private TextView starttime;
    private TextView endtime;
    private TextView repeat;
    private TextView place;
    private TextView noticetime;
    private TextView memo;
    private TextView weight;
    private Toolbar toolbar2;
    private ImageButton buttonDelete;
    private ImageButton buttonEdit;
    private ImageButton buttonBack;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("onCreate");
    }


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (ViewGroup) inflater.inflate(R.layout.mytabfragment02, container, false);

        title = (TextView) view.findViewById(R.id.etitle);
        allday = (TextView) view.findViewById(R.id.eallday);
        starttime = (TextView) view.findViewById(R.id.estarttime);
        endtime = (TextView) view.findViewById(R.id.eendtime);
        repeat = (TextView) view.findViewById(R.id.erepeat);
        place = (TextView) view.findViewById(R.id.eplace);
        noticetime = (TextView) view.findViewById(R.id.enoticetime);
        memo = (TextView) view.findViewById(R.id.ememo);
        weight = (TextView) view.findViewById(R.id.eweight);
        toolbar2 = (Toolbar) view.findViewById(R.id.secondTabToolbar);
        buttonDelete = (ImageButton) view.findViewById(R.id.buttonDelete);
        buttonEdit = (ImageButton) view.findViewById(R.id.buttonEdit);
        buttonBack = (ImageButton) view.findViewById(R.id.buttonBack);


        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).getViewPager().setCurrentItem(0);
            }
        });

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NewPlanActivity.class);
                intent.putExtra("isEdit", true);
                MyTabFragment02.this.startActivityForResult(intent, 0);
                getActivity().overridePendingTransition(R.anim.fadein, R.anim.fadeout);

                onResume();
            }
        });
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onPlan.getRepeat()==0){
                    DBHelper dbHelper = new DBHelper(getActivity().getApplicationContext(), "MYPLAN.db", null, 1);
                    dbHelper.deleteMyPlan(onPlan.getId());
                    ((MainActivity) getActivity()).getViewPager().setCurrentItem(0);
                }else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                    dialog.setTitle("Delete Option.");
                    dialog.setNeutralButton("Cencel", null);
                    dialog.setNegativeButton("Just This.", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            DBHelper dbHelper = new DBHelper(getActivity().getApplicationContext(), "MYPLAN.db", null, 1);
                            dbHelper.deleteOneofAllPlan(onPlan.getId());
                            ((MainActivity) getActivity()).getViewPager().setCurrentItem(0);
                        }
                    });
                    dialog.setPositiveButton("Delete All.", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            DBHelper dbHelper = new DBHelper(getActivity().getApplicationContext(), "MYPLAN.db", null, 1);
                            dbHelper.deleteMyPlan(onPlan.getId());
                            ((MainActivity) getActivity()).getViewPager().setCurrentItem(0);
                        }
                    });
                    dialog.show();
                }
            }
        });

        return view;
    }

    public void setFragment() {
        title.setText(onPlan.getTitle());
        allday.setText(onPlan.getAlldayText());
        starttime.setText(onPlan.getStartTimeText());
        endtime.setText(onPlan.getEndTimeText());
        repeat.setText(onPlan.getRepeatText() + " / " + new SimpleDateFormat("yyyy. MM. dd").format(onPlan.getRepeatEnd()));
        place.setText(onPlan.getPlace());
        noticetime.setText(onPlan.getNoticeTimeText());
        memo.setText(onPlan.getMemo());
        weight.setText(onPlan.getWeightText());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        setFragment();
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        setFragment();
        super.onResume();
    }
}
