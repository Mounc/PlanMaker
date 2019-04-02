package com.example.taehoon.planmaker_v2.Data;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by TAEHOON on 2017-03-24.
 */

public class MyPlan implements Cloneable, Serializable{

    private static final long serialVersionUID = 1L;

    private int id;
    private String title;   // 제목
    private int allday; // 하루종일 유무
    private Date startTime; // 시작시간
    private Date endTime;   // 종료시간
    private int repeat;     // 일정반복
    private Date repeatEnd;
    private String place;   // 장소
    private int noticeTime; // 알림시간(몇분전)
    private String memo;    // 메모
    private int weight;     // 우선순위(무게감)

    public MyPlan() {
        this.id = 1;
        this.title = "TestTitle";
        this.allday = 0;
        this.startTime = new Date(Calendar.DATE);
        this.endTime = new Date(Calendar.DATE);
        this.repeat = 0;
        this.repeatEnd = new Date();
        this.place = "TestPlace";
        this.noticeTime = 0;
        this.memo = "Test Comments";
        this.weight = 0;
    }

    public MyPlan(int id, String title, int allday, Date startTime, Date endTime, int repeat, Date repeatEnd, String place, int noticeTime, String memo, int weight) throws ParseException {
        this.id = id;
        this.title = title;
        this.allday = allday;
        this.startTime = startTime;
        this.endTime = endTime;
        this.repeat = repeat;
        this.repeatEnd = repeatEnd;
        this.place = place;
        this.noticeTime = noticeTime;
        this.memo = memo;
        this.weight = weight;
    }
    public MyPlan(int id, String title, int allday, String startTime, String endTime, int repeat, String repeatEnd, String place, int noticeTime, String memo, int weight) throws ParseException {
        this.id = id;
        this.title = title;
        this.allday = allday;
        this.startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime);
        this.endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTime);
        this.repeat = repeat;
        this.repeatEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(repeatEnd);
        this.place = place;
        this.noticeTime = noticeTime;
        this.memo = memo;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public int getAllday() {
        return allday;
    }
    public String getAlldayText() {
        switch (allday){
            case 0: return "No Allday";
            default: return "On Allday";
        }
    }
    public void setAllday(int allday) {
        this.allday = allday;
    }
    public Date getStartTime() {
        return startTime;
    }
    public String getStartTimeforInsertDB(){
        if (allday==0)  return new SimpleDateFormat("yyyy-MM-dd HH:mm:00").format(startTime);
        else return new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(startTime);
    }
    public String getStartTimeText(){
        switch (allday) {
            case 0:
                return new SimpleDateFormat("yyyy. MM. dd. HH:mm ").format(startTime);
            default:
                return new SimpleDateFormat("yyyy. MM. dd. ").format(startTime);
        }
    }
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    public void setStartTime(String startTime) throws ParseException {
        this.startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime);
    }
    public Date getEndTime() {
        return endTime;
    }
    public String getEndTimeforInsertDB(){
        if (allday==0) return new SimpleDateFormat("yyyy-MM-dd HH:mm:00").format(endTime);
        else return new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(endTime);
    }
    public String getEndTimeText(){
        switch (allday) {
            case 0:
                return new SimpleDateFormat("yyyy. MM. dd. HH:mm ").format(endTime);
            default:
                return new SimpleDateFormat("yyyy. MM. dd. ").format(endTime);
        }
    }
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    public  void setEndTime(String endTime) throws ParseException {
        this.endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTime);
    }
    public int getRepeat() {
        return repeat;
    }
    public String getRepeatText(){
        switch(repeat){
            case 0 : return "No Repeat";
            case 1 : return "Every Day";
            case 2 : return "Every Week";
            case 3 : return "Every Month";
            case 4 : return "Every Year";
            default: return "Repeat Data Missed";
        }
    }
    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }
    public Date getRepeatEnd() {
        return repeatEnd;
    }
    public String getRepeatEndforInsertDB(){
        return new SimpleDateFormat("yyyy-MM-dd").format(repeatEnd);
    }
    public void setRepeatEnd(Date repeatEnd) {
        this.repeatEnd = repeatEnd;
    }
    public  void setRepeatEnd(String repeatEnd) throws ParseException {
        this.repeatEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(repeatEnd);
    }
    public String getPlace() {
        return place;
    }
    public void setPlace(String place) {
        this.place = place;
    }
    public int getNoticeTime() {
        return noticeTime;
    }
    public String getNoticeTimeText(){
        switch(noticeTime){
            case 0: return "No Alert";
            case 1: return "On Time";
            case 2: return "5 Minutes Left";
            case 3: return "10 Minutes Left";
            case 4: return "30 Minutes Left";
            case 5: return "1 Hour Left";
            case 6: return "3 Hours Left";
            case 7: return "6 Hours Left";
            case 8: return "12 Hours Left";
            case 9: return "1 Day Left";
            default: return "NoticeTime Data Missed";
        }
    }
    public void setNoticeTime(int noticeTime) {
        this.noticeTime = noticeTime;
    }
    public String getMemo() {
        return memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }
    public int getWeight() {
        return weight;
    }
    public String getWeightText(){
        switch(weight){
            case 0: return "Level 1";
            case 1: return "Level 2";
            case 2: return "Level 3";
            case 3: return "Level 4";
            case 4: return "Level 5";
            default: return "Weight Data Missed";
        }
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
