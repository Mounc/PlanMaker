package com.example.taehoon.planmaker_v2.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.taehoon.planmaker_v2.Data.MyPlan;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by TAEHOON on 2017-04-03.
 */

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String qry1 = "create table if not exists MYPLAN " +
                "(id integer, " +
                "title TEXT, " +
                "allday integer, " +
                "starttime integer, " +
                "endtime integer, " +
                "repeat integer, " +
                "repeatEnd integer," +
                "place TEXT, " +
                "noticetime integer, " +
                "memo TEXT, " +
                "weight integer);";
        db.execSQL(qry1);
        System.out.println(qry1);

        String qry2 = "create table if not exists ALLPLAN " +
                "(id integer , " +
                "title TEXT, " +
                "allday integer, " +
                "starttime integer, " +
                "endtime integer, " +
                "repeat integer, " +
                "repeatEnd integer," +
                "place TEXT, " +
                "noticetime integer, " +
                "memo TEXT, " +
                "weight integer," +
                "CONSTRAINT fk_id FOREIGN KEY(id) REFERENCES myplan(id) ON DELETE CASCADE);";
        db.execSQL(qry2);
        System.out.println(qry2);

        db.execSQL("PRAGMA foreign_keys=ON;");
        db.execSQL("CREATE TRIGGER Delete_B_trigger " +
                "AFTER DELETE ON myplan " +
                "FOR EACH ROW " +
                "BEGIN " +
                "    DELETE FROM allplan WHERE id = OLD.id; " +
                "END");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS myplan;");
        onCreate(db);
    }

    public String select() {
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        Cursor cursor = db.rawQuery("select * from MYPLAN;", null);
        while (cursor.moveToNext()) {
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                result += cursor.getString(i) + ", ";
            }
            result += "\n\n";
//            result += cursor.getString(0) + "\n";
        }
        return result;
    }
    public String selectAllPlan2() {
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        Cursor cursor = db.rawQuery("select * from allplan;", null);
        while (cursor.moveToNext()) {
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                result += cursor.getString(i) + ", ";
            }
            result += "\n\n";
//            result += cursor.getString(0) + "\n";
        }
        return result;
    }
    public ArrayList<MyPlan> selectMyPlan() throws ParseException {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<MyPlan> myplan = new ArrayList<>();


        Cursor cursor = db.rawQuery("select * from myplan;", null);
        while (cursor.moveToNext()) {
            MyPlan aPlan = new MyPlan();
            aPlan.setId(cursor.getInt(0));
            aPlan.setTitle(cursor.getString(1));
            aPlan.setAllday(cursor.getInt(2));
            aPlan.setStartTime(cursor.getString(3));
            aPlan.setEndTime(cursor.getString(4));
            aPlan.setRepeat(cursor.getInt(5));
            aPlan.setRepeatEnd(cursor.getString(6));
            aPlan.setPlace(cursor.getString(7));
            aPlan.setNoticeTime(cursor.getInt(8));
            aPlan.setMemo(cursor.getString(9));
            aPlan.setWeight(cursor.getInt(10));

            myplan.add(aPlan);
        }
        return myplan;
    }
    public ArrayList<MyPlan> selectAllPlan() throws ParseException {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<MyPlan> allPlan = new ArrayList<>();


        Cursor cursor = db.rawQuery("select * from allplan;", null);
        while (cursor.moveToNext()) {
            MyPlan aPlan = new MyPlan();
            aPlan.setId(cursor.getInt(0));
            aPlan.setTitle(cursor.getString(1));
            aPlan.setAllday(cursor.getInt(2));
            aPlan.setStartTime(cursor.getString(3));
            aPlan.setEndTime(cursor.getString(4));
            aPlan.setRepeat(cursor.getInt(5));
            aPlan.setRepeatEnd(cursor.getString(6));
            aPlan.setPlace(cursor.getString(7));
            aPlan.setNoticeTime(cursor.getInt(8));
            aPlan.setMemo(cursor.getString(9));
            aPlan.setWeight(cursor.getInt(10));

            allPlan.add(aPlan);
        }
        return allPlan;
    }

    public ArrayList<MyPlan> selectAllPlanOnSearch(String title) throws ParseException {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<MyPlan> list = new ArrayList<>();

        String sql;
        if("".equals(title)) {
            sql = "select * from allplan order by starttime";
        }else{
            sql = "select * from allplan where title like '%" + title + "%' order by starttime;";
        }


        Cursor cursor = db.rawQuery(sql , null);
        while (cursor.moveToNext()) {
            MyPlan aPlan = new MyPlan();
            aPlan.setId(cursor.getInt(0));
            aPlan.setTitle(cursor.getString(1));
            aPlan.setAllday(cursor.getInt(2));
            aPlan.setStartTime(cursor.getString(3));
            aPlan.setEndTime(cursor.getString(4));
            aPlan.setRepeat(cursor.getInt(5));
            aPlan.setRepeatEnd(cursor.getString(6));
            aPlan.setPlace(cursor.getString(7));
            aPlan.setNoticeTime(cursor.getInt(8));
            aPlan.setMemo(cursor.getString(9));
            aPlan.setWeight(cursor.getInt(10));

            list.add(aPlan);
        }

        return list;
    }

    public String select(String item) {
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        if (item == "") return null;

        Cursor cursor = db.rawQuery(item, null);
        System.out.println("Select Query : " + item);
        while (cursor.moveToNext()) {
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                result += cursor.getString(i) + ", ";
            }
            result += "\n\n";
        }
        return result;
    }

    public ArrayList<MyPlan> selectOnToday(String today) throws ParseException {
        ArrayList<MyPlan> myPlan = new ArrayList<MyPlan>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from allplan where date('" +
                today + "') between date(starttime) and date(endtime);", null);
        while (cursor.moveToNext()) {
            myPlan.add(new MyPlan(
                    cursor.getInt(0),
                    cursor.getString(1),    //title
                    cursor.getInt(2),       //allday
                    cursor.getString(3),       //starttime
                    cursor.getString(4),       //endtime
                    cursor.getInt(5),       //repeat
                    cursor.getString(6),    //repeatEnd
                    cursor.getString(7),    //place
                    cursor.getInt(8),       //noticeTime
                    cursor.getString(9),    //memo
                    cursor.getInt(10)    //weight
            ));
        }

        return myPlan;
    }

    public MyPlan selectByIdOfSelected(int id) throws ParseException {
        MyPlan myPlan = null;
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from myplan where id = " + id + ";", null);
        while (cursor.moveToNext()) {
            myPlan = new MyPlan(
                    cursor.getInt(0),
                    cursor.getString(1),    //title
                    cursor.getInt(2),       //allday
                    cursor.getString(3),       //starttime
                    cursor.getString(4),       //endtime
                    cursor.getInt(5),       //repeat
                    cursor.getString(6),    //repeatEnd
                    cursor.getString(7),    //place
                    cursor.getInt(8),       //noticeTime
                    cursor.getString(9),    //memo
                    cursor.getInt(10)    //weight
            );
        }

        return myPlan;
    }

    public void insert(MyPlan myPlan, int id) {
        SQLiteDatabase db = getWritableDatabase();

        String query = "insert into myplan values ("
                + id + ", '" + myPlan.getTitle() + "', " + myPlan.getAllday() + ", "
                + "datetime('" + myPlan.getStartTimeforInsertDB() + "'), " + "datetime('" + myPlan.getEndTimeforInsertDB() + "'), "
                + myPlan.getRepeat() + ", " + "datetime('" + myPlan.getRepeatEndforInsertDB() + "'), '" + myPlan.getPlace() + "', " + myPlan.getNoticeTime() + ", '" + myPlan.getMemo() + "', " + myPlan.getWeight() + ");";
        System.out.println(query);
        db.execSQL(query);


        db.close();
    }

    public void insertRepeat(MyPlan myPlan) throws CloneNotSupportedException {
        int id = getPrimaryKey();
        insert(myPlan, id);
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<MyPlan> allPlan = new ArrayList<>();
        int day = 1000 * 60 * 60 * 24;
        int week = day * 7;

        switch (myPlan.getRepeat()) {
            case 0: // 반복없음
                allPlan.add(myPlan);
                break;
            case 1: // 매일
                while (myPlan.getStartTime().before(myPlan.getRepeatEnd())) {
                    MyPlan aPlan = myPlan;
                    allPlan.add((MyPlan)aPlan.clone());
                    myPlan.setStartTime(new Date(myPlan.getStartTime().getTime() + day));
                    myPlan.setEndTime(new Date(myPlan.getEndTime().getTime() + day));
                }
                break;
            case 2: // 매주
                while (myPlan.getStartTime().before(myPlan.getRepeatEnd())) {
                    MyPlan aPlan = myPlan;
                    allPlan.add((MyPlan)aPlan.clone());
                    myPlan.setStartTime(new Date(myPlan.getStartTime().getTime() + week));
                    myPlan.setEndTime(new Date(myPlan.getEndTime().getTime() + week));
                }
                break;
            case 3: // 매월
                while (myPlan.getStartTime().before(myPlan.getRepeatEnd())) {
                    MyPlan aPlan = myPlan;
                    allPlan.add((MyPlan)aPlan.clone());
                    myPlan.setStartTime(new Date(myPlan.getStartTime().getYear(), myPlan.getStartTime().getMonth() + 1, myPlan.getStartTime().getDate(), myPlan.getStartTime().getHours(), myPlan.getStartTime().getMinutes()));
                    myPlan.setEndTime(new Date(myPlan.getEndTime().getYear(), myPlan.getEndTime().getMonth() + 1, myPlan.getEndTime().getDate(), myPlan.getEndTime().getHours(), myPlan.getEndTime().getMinutes()));
                }
                break;
            case 4: // 매년
                while (myPlan.getStartTime().before(myPlan.getRepeatEnd())) {
                    MyPlan aPlan = myPlan;
                    allPlan.add((MyPlan)aPlan.clone());
                    myPlan.setStartTime(new Date(myPlan.getStartTime().getYear() + 1, myPlan.getStartTime().getMonth(), myPlan.getStartTime().getDate(), myPlan.getStartTime().getHours(), myPlan.getStartTime().getMinutes()));
                    myPlan.setEndTime(new Date(myPlan.getEndTime().getYear() + 1, myPlan.getEndTime().getMonth(), myPlan.getEndTime().getDate(), myPlan.getEndTime().getHours(), myPlan.getEndTime().getMinutes()));
                }
                break;
        }

        for(int i=0, n=allPlan.size() ; i < n ; i++){
            MyPlan ap = allPlan.get(i);
            String query = "insert into allplan values ("+ id +", '" + ap.getTitle() + "', " + ap.getAllday() + ", "
                    + "datetime('" + ap.getStartTimeforInsertDB() + "'), " + "datetime('" + ap.getEndTimeforInsertDB() + "'), "
                    + ap.getRepeat() + ", " + "datetime('" + ap.getRepeatEndforInsertDB() + "'), '" + ap.getPlace() + "', " + ap.getNoticeTime() + ", '" + ap.getMemo() + "', " + ap.getWeight() + ");";
            System.out.println(query);
            db.execSQL(query);
        }
        db.close();
    }

    public void insert(String title, Boolean allday, Date startTime, Date endTime, int repeat, String place, int noticeTime, String memo, int weight) {
        SQLiteDatabase db = getWritableDatabase();
        int ad;
        if (allday) ad = 1;
        else ad = 0;

        String query = "insert into myplan values (null, '" + title + "', " + ad + ", " + startTime.getTime() + ", " + endTime.getTime() + ", " + repeat + ", '" + place + "', " + noticeTime + ", '" + memo + "', " + weight + ");";
        System.out.println(query);
        db.execSQL(query);
    }

    public void insert(String item) {
        SQLiteDatabase db = getWritableDatabase();

        System.out.println(item);
        db.execSQL(item);
    }

    public int getPrimaryKey(){
        SQLiteDatabase db = getReadableDatabase();
        int newId = 1;
        while (true){
            Cursor cursor = db.rawQuery("select id from myplan where id="+newId, null);
            if(cursor.getCount() == 0) break;
            newId++;
        }
        System.out.println("Select Id : " + newId);
        return newId;
    }

    public void deleteMyPlan(int id){
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("Delete from Myplan where id = " + id);

        db.close();
    }
    public void deleteOneofAllPlan(int id){
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("Delete from allplan where id = " + id);

        db.close();
    }

    public void delete_plan(){
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("Delete from Myplan;");
        db.execSQL("Delete from allplan;");

        db.close();
    }

    public void insertSyncMyPlan(ArrayList<MyPlan> myplan, ArrayList<MyPlan> allplan) {
        SQLiteDatabase db = getWritableDatabase();


        for (int i = 0; i < myplan.size(); i++) {
            MyPlan aplan = myplan.get(i);
            String query = "insert into myplan values ("
                    + getPrimaryKey() + ", '" + aplan.getTitle() + "', " + aplan.getAllday() + ", "
                    + "datetime('" + aplan.getStartTimeforInsertDB() + "'), " + "datetime('" + aplan.getEndTimeforInsertDB() + "'), "
                    + aplan.getRepeat() + ", " + "datetime('" + aplan.getRepeatEndforInsertDB() + "'), '" + aplan.getPlace() + "', " + aplan.getNoticeTime() + ", '" + aplan.getMemo() + "', " + aplan.getWeight() + ");";
            System.out.println(query);
            db.execSQL(query);
        }

        for (int i = 0; i < allplan.size(); i++) {
            MyPlan aplan = allplan.get(i);
            String query = "insert into allplan values ("
                    + getPrimaryKey() + ", '" + aplan.getTitle() + "', " + aplan.getAllday() + ", "
                    + "datetime('" + aplan.getStartTimeforInsertDB() + "'), " + "datetime('" + aplan.getEndTimeforInsertDB() + "'), "
                    + aplan.getRepeat() + ", " + "datetime('" + aplan.getRepeatEndforInsertDB() + "'), '" + aplan.getPlace() + "', " + aplan.getNoticeTime() + ", '" + aplan.getMemo() + "', " + aplan.getWeight() + ");";
            System.out.println(query);
            db.execSQL(query);
        }

        db.close();

    }
}
