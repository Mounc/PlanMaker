package com.example.taehoon.planmaker_v2.Newbook;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.taehoon.planmaker_v2.Data.MyPlan;
import com.example.taehoon.planmaker_v2.Database.DBHelper;
import com.example.taehoon.planmaker_v2.R;
import com.wefika.horizontalpicker.HorizontalPicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.Inflater;

import static com.example.taehoon.planmaker_v2.MainBase.MainActivity.onPlan;
import static com.example.taehoon.planmaker_v2.MainBase.MainActivity.selectDay;

/**
 * Created by TAEHOON on 2017-03-27.
 */

public class NewPlanActivity extends AppCompatActivity {
    private EditText title;
    private Switch alldaySwitch;
    private TextView startTime;
    private TextView endTime;
    private TextView repeat;
    private TextView place;
    private TextView noticeTime;
    private EditText memo;
    private TextView weight;
    private ImageButton bookButton, bookButtonBack;
    private TimePicker startTimePicker;
    private DatePicker startDatePicker;
    private TimePicker endTimePicker;
    private DatePicker endDatePicker;
    private Animation aniShow, aniHide;
    private String tmpstartDate, tmpstartTime;
    private Intent intent;
    private NumberPicker repeatPicker;
    private NumberPicker noticeTimePicker;
    private NumberPicker weightPicker;
    private DatePicker repeatEndPicker;
    private boolean isEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newplan);
        intent = getIntent();
        System.out.println("Book Activity Parameter Select Day : " + selectDay.getYear());

        isEdit = intent.getBooleanExtra("isEdit", false);
//        Toast.makeText(getApplicationContext(), "isEdit : " + isEdit, Toast.LENGTH_SHORT).show();

        // Book 액티비티에서 사용하는 레이아웃 변수 설정
        constructor();

        // 액티비티 활성시 초기화
        init();


        //리스너
        startTime.setOnClickListener(startTimeListener);
        endTime.setOnClickListener(endTimeListener);
        alldaySwitch.setOnClickListener(allDayListener);
        repeatPicker.setOnValueChangedListener(repeatPickerValueChangeListener);
        noticeTime.setOnClickListener(noticeTimeListener);
        noticeTimePicker.setOnValueChangedListener(noticeTimePickerChangeListener);
        weightPicker.setOnValueChangedListener(weightPickerListener);

        // 최종적으로 버튼을 누르면 모든 일정 정보가 DB에 Insert된다.
        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(title.getText().length()==0){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(NewPlanActivity.this);
                    dialog.setTitle("Title is required.");
                    dialog.setNegativeButton("OK", null);
                    dialog.show();
                }else {
                    confirm();
                    finish();
                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                }
            }
        });

        bookButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });
//
//
//        final View toptime = getLayoutInflater().inflate(R.layout.dialog_newplan_setstarttime, (ViewGroup)findViewById(R.id.starttime_view));
//        final View bottomtime = getLayoutInflater().inflate(R.layout.dialog_newplan_setendtime, (ViewGroup)findViewById(R.id.endtime_view));
//
//        final AlertDialog.Builder dialogStarttime, dialogEndtime;
//
//        dialogStarttime = new AlertDialog.Builder(NewPlanActivity.this);
//        dialogEndtime = new AlertDialog.Builder(NewPlanActivity.this);
//
//        dialogStarttime.setView(toptime);
//        dialogStarttime.setTitle("Set StartTime");
//        dialogEndtime.setView(bottomtime);
//        dialogEndtime.setTitle("Set EndTime");
//        startDatePicker = (DatePicker)toptime.findViewById(R.id.make_request_toptime_datapicker);
//        startTimePicker = (TimePicker)toptime.findViewById(R.id.make_request_toptime_timepicker);
//        endDatePicker = (DatePicker)bottomtime.findViewById(R.id.make_request_bottomtime_datapicker);
//        endTimePicker = (TimePicker)bottomtime.findViewById(R.id.make_request_bottomtime_timepicker);
//
//
//
//
//
//
//        dialogStarttime.setPositiveButton("Next", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                ((ViewGroup)toptime.getParent()).removeView(toptime);
//                dialog.cancel();
//                dialogEndtime.show();
//            }
//        });
//        dialogStarttime.setNegativeButton("Cencel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                ((ViewGroup)toptime.getParent()).removeView(toptime);
//                dialog.cancel();
//            }
//        });
//
//
//        dialogEndtime.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//                startTime.setText(new SimpleDateFormat("yyyy. MM. dd. HH:mm").format(new Date(startDatePicker.getYear()-1900, startDatePicker.getMonth(), startDatePicker.getDayOfMonth(), startTimePicker.getHour(), startTimePicker.getMinute())));
//                endTime.setText(new SimpleDateFormat("yyyy. MM. dd. HH:mm").format(new Date(endDatePicker.getYear()-1900, endDatePicker.getMonth(), endDatePicker.getDayOfMonth(), endTimePicker.getHour(), endTimePicker.getMinute())));
//                ((ViewGroup)bottomtime.getParent()).removeView(bottomtime);
//            }
//        });
//        dialogEndtime.setNegativeButton("Previous", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                ((ViewGroup)bottomtime.getParent()).removeView(bottomtime);
//                dialog.cancel();
//                dialogStarttime.show();
//            }
//        });
//
//        startTime.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialogStarttime.show();
//            }
//        });
//        endTime.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialogStarttime.show();
//            }
//        });
//
//        dialogStarttime.create();
//        dialogEndtime.create();



    }

    private void constructor() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.bookPlanToolbar);
        toolbar.setTitle("ADD PLAN");
        title = (EditText) findViewById(R.id.bookTitle);
        alldaySwitch = (Switch) findViewById(R.id.alldaySwitch);
        startTime = (TextView) findViewById(R.id.bookStartTime);
        endTime = (TextView) findViewById(R.id.bookEndTime);
        repeat = (TextView) findViewById(R.id.repeat);
        place = (TextView) findViewById(R.id.place);
        noticeTime = (TextView) findViewById(R.id.noticeTime);
        memo = (EditText) findViewById(R.id.memo);
        weight = (TextView) findViewById(R.id.weight);
        startDatePicker = (DatePicker) findViewById(R.id.startdatePicker);
        startTimePicker = (TimePicker) findViewById(R.id.starttimePicker);
        endDatePicker = (DatePicker) findViewById(R.id.enddatePicker);
        endTimePicker = (TimePicker) findViewById(R.id.endtimePicker);
        bookButton = (ImageButton) findViewById(R.id.bookButton);
        bookButtonBack = (ImageButton) findViewById(R.id.bookButtonBack);
        repeatPicker = (NumberPicker) findViewById(R.id.repeatPicker);
        repeatEndPicker = (DatePicker)findViewById(R.id.repeatEndPicker);
        noticeTimePicker = (NumberPicker) findViewById(R.id.noticeTimePicker);
        weightPicker = (NumberPicker) findViewById(R.id.weightPicker);
    }

    private void confirm(){
        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "MYPLAN.db", null, 1);
        if(isEdit){
            dbHelper.deleteMyPlan(onPlan.getId());
        }

        Date startTimetmp = new Date(startDatePicker.getYear() - 1900, startDatePicker.getMonth(), startDatePicker.getDayOfMonth(), startTimePicker.getHour(), startTimePicker.getMinute());
        Date endTimetmp = new Date(endDatePicker.getYear() - 1900, endDatePicker.getMonth(), endDatePicker.getDayOfMonth(), endTimePicker.getHour(), endTimePicker.getMinute());
        Date repeatEndtmp = new Date(repeatEndPicker.getYear() - 1900, repeatEndPicker.getMonth(), repeatEndPicker.getDayOfMonth(), 23, 59, 59);
        MyPlan newPlan = null;
        try {
            newPlan = new MyPlan(0, title.getText().toString(), (alldaySwitch.isChecked()) ? 1 : 0, startTimetmp, endTimetmp, repeatPicker.getValue(), repeatEndtmp,
                    place.getText().toString(), noticeTimePicker.getValue(), memo.getText().toString(), weightPicker.getValue());
        } catch (ParseException e) {
            e.printStackTrace();
        }
//                dbHelper.insert(newPlan);
        try {
            dbHelper.insertRepeat(newPlan);
            onPlan = newPlan;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        dbHelper.close();
    }

    private void init(){
        if(isEdit){
            bookButton.setImageDrawable(getDrawable(R.drawable.edit));
            title.setText(onPlan.getTitle());
            alldaySwitch.setChecked(onPlan.getAllday()==1);
            startDatePicker.init(onPlan.getStartTime().getYear() + 1900, onPlan.getStartTime().getMonth(), onPlan.getStartTime().getDate(), startDatePickerListener);
            startTimePicker.setHour(onPlan.getStartTime().getHours());
            startTimePicker.setMinute(onPlan.getStartTime().getMinutes());
            startTimePicker.setOnTimeChangedListener(startTimePickerListener);
            endDatePicker.init(onPlan.getEndTime().getYear() + 1900, onPlan.getEndTime().getMonth(), onPlan.getEndTime().getDate(), endDatePickerListener);
            endTimePicker.setHour(onPlan.getEndTime().getHours());
            endTimePicker.setMinute(onPlan.getEndTime().getMinutes());
            endTimePicker.setOnTimeChangedListener(endTimePickerListener);
            startTimePicker.setIs24HourView(true);
            endTimePicker.setIs24HourView(true);
            if(alldaySwitch.isChecked()) {
                startTime.setText(String.valueOf(startDatePicker.getYear()) + ". " + String.valueOf(startDatePicker.getMonth() + 1) + ". " + String.valueOf(startDatePicker.getDayOfMonth()) + ".");
                endTime.setText(String.valueOf(endDatePicker.getYear()) + ". " + String.valueOf(endDatePicker.getMonth() + 1) + ". " + String.valueOf(endDatePicker.getDayOfMonth()) + ".");
            }else {
                startTime.setText(String.valueOf(startDatePicker.getYear()) + ". " + String.valueOf(startDatePicker.getMonth() + 1) + ". " + String.valueOf(startDatePicker.getDayOfMonth()) + "." + "  " + String.valueOf(startTimePicker.getHour()) + ":00");
                endTime.setText(String.valueOf(endDatePicker.getYear()) + ". " + String.valueOf(endDatePicker.getMonth() + 1) + ". " + String.valueOf(endDatePicker.getDayOfMonth()) + "." + "  " + String.valueOf(endTimePicker.getHour()) + ":00");
            }

            repeat.setText(getRepeatText(onPlan.getRepeat()));
            repeatPicker.setMinValue(0);
            repeatPicker.setMaxValue(4);
            repeatPicker.setWrapSelectorWheel(false);
            repeatPicker.setDisplayedValues(new String[]{"No Repeat", "EveryDay", "EveryWeek", "EveryMonth", "EveryYear"});
            repeatPicker.setValue(onPlan.getRepeat());
            repeatEndPicker.init(onPlan.getRepeatEnd().getYear()+1900, onPlan.getRepeatEnd().getMonth(), onPlan.getRepeatEnd().getDate(), null);

            place.setText(onPlan.getPlace());

            noticeTimePicker.setMinValue(0);
            noticeTimePicker.setMaxValue(9);
            noticeTimePicker.setDisplayedValues(new String[]
                    {"No Alert", "On Time", "5 Minutes Left", "10 Minutes Left", "30 Minutes Left", "1 Hour Left", "3 Hours Left", "6 Hours Left", "12 Hours Left", "1 Day Left"});
            noticeTimePicker.setWrapSelectorWheel(false);
            noticeTimePicker.setValue(onPlan.getNoticeTime());
            noticeTime.setText(getNoticeTimeText());

            weightPicker.setMinValue(0);
            weightPicker.setMaxValue(4);
            weightPicker.setDisplayedValues(new String[]
                    {"Level 1", "Level 2", "Level 3", "Level 4", "Level 5"});
            weightPicker.setWrapSelectorWheel(false);
            weightPicker.setValue(onPlan.getWeight());
            weight.setText(getWeightText());

        }
        else {
            startDatePicker.init(selectDay.getYear() + 1900, selectDay.getMonth(), selectDay.getDate(), startDatePickerListener);
            startTimePicker.setOnTimeChangedListener(startTimePickerListener);
            endDatePicker.init(selectDay.getYear() + 1900, selectDay.getMonth(), selectDay.getDate(), endDatePickerListener);
            endTimePicker.setOnTimeChangedListener(endTimePickerListener);
            startTimePicker.setIs24HourView(true);
            endTimePicker.setIs24HourView(true);
            endTimePicker.setHour(startTimePicker.getHour() + 1);
            startTimePicker.setMinute(0);
            endTimePicker.setMinute(0);
            startTime.setText(String.valueOf(startDatePicker.getYear()) + ". " + String.valueOf(startDatePicker.getMonth() + 1) + ". " + String.valueOf(startDatePicker.getDayOfMonth()) + "." + "  " + String.valueOf(startTimePicker.getHour()) + ":00");
            endTime.setText(String.valueOf(endDatePicker.getYear()) + ". " + String.valueOf(endDatePicker.getMonth() + 1) + ". " + String.valueOf(endDatePicker.getDayOfMonth()) + "." + "  " + String.valueOf(endTimePicker.getHour()) + ":00");
            repeat.setText(getRepeatText(repeatPicker.getValue()));
            repeatPicker.setMinValue(0);
            repeatPicker.setMaxValue(4);
            repeatPicker.setWrapSelectorWheel(false);
            repeatPicker.setDisplayedValues(new String[]{"No Repeat", "EveryDay", "EveryWeek", "EveryMonth", "EveryYear"});
            repeatEndPicker.init(endDatePicker.getYear(), endDatePicker.getMonth(), endDatePicker.getDayOfMonth(), null);
            noticeTimePicker.setMinValue(0);
            noticeTimePicker.setMaxValue(9);
            noticeTimePicker.setDisplayedValues(new String[]
                    {"No Alert", "On Time", "5 Minutes Left", "10 Minutes Left", "30 Minutes Left", "1 Hour Left", "3 Hours Left", "6 Hours Left", "12 Hours Left", "1 Day Left"});
            noticeTimePicker.setWrapSelectorWheel(false);
            noticeTime.setText(getNoticeTimeText());
            weightPicker.setMinValue(0);
            weightPicker.setMaxValue(4);
            weightPicker.setDisplayedValues(new String[]
                    {"Level 1", "Level 2", "Level 3", "Level 4", "Level 5"});
            weightPicker.setWrapSelectorWheel(false);
            weight.setText(getWeightText());
        }
    }

    private NumberPicker.OnValueChangeListener weightPickerListener = new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            weight.setText(getWeightText());
        }
    };

    private String getWeightText() {
        switch (weightPicker.getValue()) {
            case 0: return "Level 1";
            case 1: return "Level 2";
            case 2: return "Level 3";
            case 3: return "Level 4";
            case 4: return "Level 5";
            default: return "Level 5";
        }
    };

    private NumberPicker.OnValueChangeListener noticeTimePickerChangeListener = new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            noticeTime.setText(getNoticeTimeText());
        }
    };

    private String getNoticeTimeText() {
        switch (noticeTimePicker.getValue()){
            case 0: return "No Alert";
            case 1: return "On Time";
            case 2: return "5 Minutes Left";
            case 3: return "10 Minutes Left";
            case 4: return "30 Minutes Left";
            case 5: return "1 Hour Left";
            case 6: return "3 Hour Left";
            case 7: return "6 Hour Left";
            case 8: return "12 Hour Left";
            case 9: return "1 Day Left";
            default: return "None";
        }
    };

    private NumberPicker.OnValueChangeListener repeatPickerValueChangeListener = new NumberPicker.OnValueChangeListener(){
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            repeat.setText(getRepeatText(repeatPicker.getValue()));
        }
    };

    private String getRepeatText(int value){
        switch (value){
            case 0: return "No Repeat";
            case 1: return "EveryDay";
            case 2: return "EveryWeek";
            case 3: return "EveryMonth";
            case 4: return "EveryYear";
            default: return "None";
        }
    }

    private View.OnClickListener noticeTimeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            aniShow = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.show);
            aniHide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.hide);

            if(noticeTimePicker.getVisibility() == View.GONE){
                noticeTimePicker.setVisibility(View.VISIBLE);
                noticeTimePicker.setAnimation(aniShow);
            }
            else{
                Animation.AnimationListener animationListener = new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        noticeTimePicker.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                };
                aniHide.setAnimationListener(animationListener);
                noticeTimePicker.startAnimation(aniHide);

            }
        }
    };

    private void pickerAnimation(final DatePicker mDatePicker, final TimePicker mTimePicker){
        aniShow = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.show);
        aniHide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.hide);
        if (alldaySwitch.isChecked()) {//하루종일일때 TimePicker 미사용
            if (mDatePicker.getVisibility() == View.GONE) {
                mDatePicker.setVisibility(View.VISIBLE);
                mTimePicker.setVisibility(View.GONE);
                mDatePicker.setAnimation(aniShow);
            } else if (mDatePicker.getVisibility() == View.VISIBLE) {
                Animation.AnimationListener aniHideListener = new Animation.AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mDatePicker.setVisibility(View.GONE);
                        mTimePicker.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                };
                aniHide.setAnimationListener(aniHideListener);
                mDatePicker.startAnimation(aniHide);
            }
        } else {//하루종일이 아닐때 TimePicker 사용
            if (mDatePicker.getVisibility() == View.GONE) {
                mDatePicker.setVisibility(View.VISIBLE);
                mTimePicker.setVisibility(View.VISIBLE);
                mDatePicker.setAnimation(aniShow);
                mTimePicker.setAnimation(aniShow);
            } else if (mDatePicker.getVisibility() == View.VISIBLE) {
                Animation.AnimationListener aniHideListener = new Animation.AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mDatePicker.setVisibility(View.GONE);
                        mTimePicker.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                };
                aniHide.setAnimationListener(aniHideListener);
                mDatePicker.startAnimation(aniHide);
                mTimePicker.startAnimation(aniHide);
            }
        }
    }

    //시작 날짜를 누르면 픽커가 나오고 들어가는 애니메이션
    private View.OnClickListener startTimeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            pickerAnimation(startDatePicker, startTimePicker);
        }

    };
    //종료 날짜를 누르면 픽커가 나오고 들어가는 애니메이션
    private View.OnClickListener endTimeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            pickerAnimation(endDatePicker, endTimePicker);
        }
    };

    private View.OnClickListener allDayListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (alldaySwitch.isChecked()) {
                startTimePicker.setVisibility(View.GONE);
                endTimePicker.setVisibility(View.GONE);
                setTime(startTime, startDatePicker, startTimePicker);
                setTime(endTime, endDatePicker, endTimePicker);
            } else {
                if (startDatePicker.getVisibility() == View.VISIBLE)
                    startTimePicker.setVisibility(View.VISIBLE);
                if (endDatePicker.getVisibility() == View.VISIBLE)
                    endTimePicker.setVisibility(View.VISIBLE);
                setTime(startTime, startDatePicker, startTimePicker);
                setTime(endTime, endDatePicker, endTimePicker);
            }
        }
    };

    private String timeString(int minute){
        switch(minute){
            case 0: return "00";
            case 1: return "01";
            case 2: return "02";
            case 3: return "03";
            case 4: return "04";
            case 5: return "05";
            case 6: return "06";
            case 7: return "07";
            case 8: return "08";
            case 9: return "09";
            default: return String.valueOf(minute);
        }
    }
    private void setTime(TextView mtime, DatePicker mDatePicker, TimePicker mTimePicker){
        if (alldaySwitch.isChecked()) {//하루종일일때
            tmpstartDate = String.valueOf(mDatePicker.getYear()) + ". " + String.valueOf(mDatePicker.getMonth() + 1) + ". " + String.valueOf(mDatePicker.getDayOfMonth()) + ".";
            mtime.setText(tmpstartDate);
        } else {//하루종일이 아닐때
            tmpstartDate = String.valueOf(mDatePicker.getYear()) + ". " + String.valueOf(mDatePicker.getMonth() + 1) + ". " + String.valueOf(mDatePicker.getDayOfMonth()) + ".";
            tmpstartTime = String.valueOf(mTimePicker.getHour()) + ":" + String.valueOf(timeString(mTimePicker.getMinute()));
            mtime.setText(tmpstartDate + " " + tmpstartTime);
        }
    }
    private DatePicker.OnDateChangedListener startDatePickerListener = new DatePicker.OnDateChangedListener() {
        @Override
        public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            setTime(startTime, startDatePicker, startTimePicker);
        }
    };
    private  TimePicker.OnTimeChangedListener startTimePickerListener = new TimePicker.OnTimeChangedListener() {
        @Override
        public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
            setTime(startTime, startDatePicker, startTimePicker);
        }
    };
    private DatePicker.OnDateChangedListener endDatePickerListener = new DatePicker.OnDateChangedListener() {
        @Override
        public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            setTime(endTime, endDatePicker, endTimePicker);
        }
    };
    private  TimePicker.OnTimeChangedListener endTimePickerListener = new TimePicker.OnTimeChangedListener() {
        @Override
        public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
            setTime(endTime, endDatePicker, endTimePicker);
        }
    };

}
