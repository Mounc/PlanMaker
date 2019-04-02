package com.example.taehoon.planmaker_v2.Socket;

import android.content.Context;
import android.widget.Toast;

import com.example.taehoon.planmaker_v2.Data.MyPlan;
import com.example.taehoon.planmaker_v2.Data.Request;
import com.example.taehoon.planmaker_v2.Data.Response;
import com.example.taehoon.planmaker_v2.Data.UserData;
import com.example.taehoon.planmaker_v2.Database.DBHelper;
import com.example.taehoon.planmaker_v2.MainBase.MainActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.text.ParseException;
import java.util.ArrayList;

import static com.example.taehoon.planmaker_v2.MainBase.MainActivity.userid;


/**
 * Created by TAEHOON on 2017-05-25.
 */

public class Clienton extends Thread {
    private Socket client;
    private Context context;

    private BufferedWriter writer;
    private BufferedReader reader;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private DataInputStream dis;
    private DataOutputStream dos;

    public Clienton(Socket client, Context context) {
        this.client = client;
        this.context = context;
        try {
            reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            oos = new ObjectOutputStream(client.getOutputStream());
            ois = new ObjectInputStream(client.getInputStream());
            dis = new DataInputStream(client.getInputStream());
            dos = new DataOutputStream(client.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        super.run();
    }

    public boolean login(String id, String password) {
        try {
            writer.write("login/login");
            writer.newLine();
            writer.flush();
            System.out.println(reader.readLine());

            System.out.println("Send UserData : " + id);
            UserData user = new UserData(0, id, password);
            oos.writeObject(user);
            oos.flush();

            System.out.println("Receive Login Result");
            boolean result = false;
            String temp = reader.readLine();
            System.out.println(temp);
            switch (temp) {
                case "true":
                    result = true;
                    break;
                default:
                    break;
            }

            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean signin(String id, String password) {
        try {
            writer.write("login/sign");
            writer.newLine();
            writer.flush();
            System.out.println(reader.readLine());

            UserData signUser = new UserData(0, id, password);
            oos.writeObject(signUser);
            oos.flush();

            boolean result = false;
            String temp = reader.readLine();
            System.out.println(temp);
            switch (temp) {
                case "true":
                    result = true;
                    break;
                default:
                    break;
            }

            return result;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public ArrayList<String> loadUserList() {
        ArrayList<String> user_list = new ArrayList<>();

        try {
            writer.write("search/user");
            writer.newLine();
            writer.flush();
            System.out.println(reader.readLine());

            user_list = (ArrayList<String>) ois.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return user_list;
    }

    public void syncToServer() {
        try {
            writer.write(userid + "/synctoserver");
            writer.newLine();
            writer.flush();
            System.out.println(reader.readLine());

            final DBHelper dbHelper = new DBHelper(context, "MYPLAN.db", null, 1);
            ArrayList<MyPlan> myplan = dbHelper.selectMyPlan();
            ArrayList<MyPlan> allplan = dbHelper.selectAllPlan();

            oos.writeObject(myplan);
            oos.flush();
            oos.writeObject(allplan);
            oos.flush();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public void syncFromServer() {
        try {
            writer.write(userid + "/syncfromserver");
            writer.newLine();
            writer.flush();
            System.out.println(reader.readLine());

            final DBHelper dbHelper = new DBHelper(context, "MYPLAN.db", null, 1);
            ArrayList<MyPlan> myplan = (ArrayList<MyPlan>) ois.readObject();
            ArrayList<MyPlan> allplan = (ArrayList<MyPlan>) ois.readObject();

            dbHelper.delete_plan();
            dbHelper.insertSyncMyPlan(myplan, allplan);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Response> getResponseList(Request r) {
        ArrayList<Response> responseList = null;
        try {
            writer.write(userid + "/reco");
            writer.newLine();
            writer.flush();
            System.out.println(reader.readLine());

            oos.writeObject(r);
            oos.flush();

            responseList = (ArrayList<Response>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return responseList;
    }

    public String getPlace(){
        try {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "No Place";
    }

    public void sendPoint(double latitude, double longitude) {
        try {
            writer.write(userid + "/point");
            writer.newLine();
            writer.flush();
            System.out.println(reader.readLine());

            dos.writeDouble(latitude); dos.flush();
            dos.writeDouble(longitude); dos.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void makePlan(MyPlan myPlan, ArrayList<String> selected_item) {
        try {
            writer.write(userid + "/makeplan");
            writer.newLine();
            writer.flush();
            System.out.println(reader.readLine());

            oos.writeObject(myPlan);
            oos.flush();
            oos.writeObject(selected_item);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
