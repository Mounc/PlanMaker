package com.example.taehoon.planmaker_v2.Newbook;

import android.content.Context;
import android.content.DialogInterface;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.taehoon.planmaker_v2.Data.Request;
import com.example.taehoon.planmaker_v2.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.taehoon.planmaker_v2.Newbook.MakePlanActivity.title;
import static com.example.taehoon.planmaker_v2.Newbook.MakingFragment01.getUsers;

/**
 * Created by TAEHOON on 2017-05-26.
 */

public class MakingFragment02 extends Fragment {
    private ViewGroup view;

    private static EditText r_title;
    public static TextView r_toptime, r_bottomtime;
    private static DatePicker r_toptime_datepicker, r_bottomtime_datepicker;
    private static TimePicker r_toptime_timepicker, r_bottomtime_timepicker;
    private static NumberPicker r_term_numberpicker;

    private AlertDialog.Builder dialogTop, dialogBottom;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (ViewGroup) inflater.inflate(R.layout.makingplanfragment02, container, false);

        final View toptime = inflater.inflate(R.layout.dialog_makeplan_settoptime, (ViewGroup) view.findViewById(R.id.toptime_view));
        final View bottomtime = inflater.inflate(R.layout.dialog_makeplan_setbottomtime, (ViewGroup) view.findViewById(R.id.bottomtime_view));

        r_title = (EditText) view.findViewById(R.id.make_title);
        r_toptime = (TextView) view.findViewById(R.id.make_request_toptime);
        r_bottomtime = (TextView) view.findViewById(R.id.make_request_bottomtime);
        r_term_numberpicker = (NumberPicker) view.findViewById(R.id.make_request_term_numberpicker);

        r_toptime.setText("시간을 선택하세요.");
        r_bottomtime.setText("시간을 선택하세요.");

        dialogTop = new AlertDialog.Builder(getContext());
        dialogTop.setView(toptime);
        dialogTop.setTitle("Top Time");
        dialogTop.setPositiveButton("Next", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((ViewGroup) toptime.getParent()).removeView(toptime);
                dialog.cancel();
                dialogBottom.show();
            }
        });
        dialogTop.setNegativeButton("Cencel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((ViewGroup) toptime.getParent()).removeView(toptime);
                dialog.cancel();
            }
        });

        dialogBottom = new AlertDialog.Builder(getContext());
        dialogBottom.setView(bottomtime);
        dialogBottom.setTitle("Bottom Time");
        dialogBottom.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                r_toptime_datepicker = (DatePicker) toptime.findViewById(R.id.make_request_toptime_datapicker);
                r_toptime_timepicker = (TimePicker) toptime.findViewById(R.id.make_request_toptime_timepicker);
                r_bottomtime_datepicker = (DatePicker) bottomtime.findViewById(R.id.make_request_bottomtime_datapicker);
                r_bottomtime_timepicker = (TimePicker) bottomtime.findViewById(R.id.make_request_bottomtime_timepicker);
                r_toptime.setText(new SimpleDateFormat("yyyy. MM. dd. HH:mm").format(new Date(r_toptime_datepicker.getYear() - 1900, r_toptime_datepicker.getMonth(), r_toptime_datepicker.getDayOfMonth(), r_toptime_timepicker.getHour(), r_toptime_timepicker.getMinute())));
                r_bottomtime.setText(new SimpleDateFormat("yyyy. MM. dd. HH:mm").format(new Date(r_bottomtime_datepicker.getYear() - 1900, r_bottomtime_datepicker.getMonth(), r_bottomtime_datepicker.getDayOfMonth(), r_bottomtime_timepicker.getHour(), r_bottomtime_timepicker.getMinute())));
                ((ViewGroup) bottomtime.getParent()).removeView(bottomtime);
            }
        });
        dialogBottom.setNegativeButton("Previous", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((ViewGroup) bottomtime.getParent()).removeView(bottomtime);
                dialog.cancel();
                dialogTop.show();
            }
        });

        r_toptime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogTop.show();
            }
        });
        r_bottomtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogTop.show();
            }
        });

        r_term_numberpicker.setMinValue(1);
        r_term_numberpicker.setMaxValue(10);
        r_term_numberpicker.setDisplayedValues(new String[]
                {"1시간", "2시간", "3시간", "4시간", "5시간", "6시간", "7시간", "8시간", "9시간", "10시간"});
        r_term_numberpicker.setWrapSelectorWheel(false);


        return view;
    }

    public static Request getRequestData() {
        Request r = null;

        Date toptime = new Date(r_toptime_datepicker.getYear() - 1900, r_toptime_datepicker.getMonth(), r_toptime_datepicker.getDayOfMonth(), r_toptime_timepicker.getHour(), r_toptime_timepicker.getMinute());
        Date bottomtime = new Date(r_bottomtime_datepicker.getYear() - 1900, r_bottomtime_datepicker.getMonth(), r_bottomtime_datepicker.getDayOfMonth(), r_bottomtime_timepicker.getHour(), r_bottomtime_timepicker.getMinute());
        int term = r_term_numberpicker.getValue();


        r = new Request(toptime, bottomtime, term, getUsers());
        title = r_title.getText().toString();

        return r;
    }
}
