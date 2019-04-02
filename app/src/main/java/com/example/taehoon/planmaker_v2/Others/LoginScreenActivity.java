package com.example.taehoon.planmaker_v2.Others;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.taehoon.planmaker_v2.R;

import static com.example.taehoon.planmaker_v2.MainBase.MainActivity.RESULT_CODE_FINISH;
import static com.example.taehoon.planmaker_v2.MainBase.MainActivity.RESULT_CODE_LOGIN;
import static com.example.taehoon.planmaker_v2.MainBase.MainActivity.client;
import static com.example.taehoon.planmaker_v2.MainBase.MainActivity.userid;

public class LoginScreenActivity extends AppCompatActivity {

    private EditText edit_login_id;
    private EditText edit_login_pw;
    private Button btn_login_login;
    private Button btn_login_signup;
    private BackPressCloseHandler bh;

    private AlertDialog.Builder dialog_signup;
    private AlertDialog.Builder dialog_fail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        bh = new BackPressCloseHandler(this);
        dialog_signup = new AlertDialog.Builder(LoginScreenActivity.this);
        dialog_fail = new AlertDialog.Builder(LoginScreenActivity.this);
        setAlertDialog();

        edit_login_id = (EditText)findViewById(R.id.edit_userid);
        edit_login_pw = (EditText)findViewById(R.id.edit_password);
        btn_login_login = (Button)findViewById(R.id.btn_login);
        btn_login_signup = (Button)findViewById(R.id.btn_sign);

        btn_login_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = edit_login_id.getText().toString();
                String password = edit_login_pw.getText().toString();
                doLogin(id, password);
                System.out.println("Login Failed.");
            }
        });

        btn_login_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_signup.show();
            }
        });
        checkLoginData();
        }

    private void checkLoginData() {
        System.out.println("check Login Data : 1 ");
        SharedPreferences pref_id = getSharedPreferences("PrefId", MODE_PRIVATE);
        SharedPreferences pref_pass = getSharedPreferences("PrefPassword", MODE_PRIVATE);
        String id = pref_id.getString("my_preference", "null");
        String password = pref_pass.getString("my_preference", "null");
        System.out.println("check Login Data : "+ id + "/" + password);
        doLogin(id, password);
    }


    private void doLogin(String id, String password) {
        if(client.login(id, password)){
//            Toast.makeText(getApplicationContext(), "Success Login, ID : " + id , Toast.LENGTH_SHORT).show();
            Intent intent = getIntent();
            intent.putExtra("id", id);
            setResult(RESULT_CODE_LOGIN, intent);
            setLoginData(id, password);
            userid = id;
            finish();
        }else if("logout".equals(id)){

        }
        else{
            dialog_fail.show();
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent intent = getIntent();
        intent.putExtra("finish", false);
        setResult(RESULT_CODE_FINISH, intent);

        bh.onBackPressed();

    }

    public void setAlertDialog(){
        Context mContext = getApplicationContext();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.dialog_signup,(ViewGroup) findViewById(R.id.layout_root));

        dialog_signup.setTitle("SIGN UP");



        dialog_signup.setView(layout);

        dialog_signup.setPositiveButton("Sign Up", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText signid = (EditText)layout.findViewById(R.id.sign_id);
                EditText signpassword = (EditText)layout.findViewById(R.id.sign_pass);

                boolean check = client.signin(signid.getText().toString(), signpassword.getText().toString());
                if(check){
                    ((ViewGroup)layout.getParent()).removeView(layout);
                    Toast.makeText(getApplicationContext(), "이미 존재하는 아이디 입니다.", Toast.LENGTH_SHORT).show();
                }else {
                    ((ViewGroup)layout.getParent()).removeView(layout);
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Success Sign in", Toast.LENGTH_SHORT).show();
                }

            }
        });

        dialog_signup.setNegativeButton("Cencel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((ViewGroup)layout.getParent()).removeView(layout);
                    dialog.dismiss();
            }
        });


        dialog_fail.setTitle("Login Fail");
        dialog_fail.setMessage("Incorrect User Data. ");
        dialog_fail.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }

    private void setLoginData(String id, String password){
        SharedPreferences pref_id = getSharedPreferences("PrefId", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref_id.edit();
        editor.putString("my_preference", id);
        editor.commit();

        SharedPreferences pref_pass = getSharedPreferences("PrefPassword", MODE_PRIVATE);
        SharedPreferences.Editor editor2 = pref_pass.edit();
        editor2.putString("my_preference", password);
        editor2.commit();
    }

}
