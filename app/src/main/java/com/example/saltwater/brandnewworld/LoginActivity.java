package com.example.saltwater.brandnewworld;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by ChaoQun on 2016/4/7.
 */

public class LoginActivity extends Activity {


    private Button button_log;
    private Button button_logs;
    private EditText editText1;
    private EditText editText2;
    private TextInputLayout textInputLayout1;
    private TextInputLayout textInputLayout2;
    private CheckBox checkBox2;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private InfoMap userInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean RP =pref.getBoolean("remember_password",false);
        checkBox2 = (CheckBox) findViewById(R.id.RPassword);
        textInputLayout1=(TextInputLayout)findViewById(R.id.usernameWrapper);
        textInputLayout2=(TextInputLayout)findViewById(R.id.passwordWrapper);
        button_log = (Button)findViewById(R.id.Button_log);
        button_logs = (Button)findViewById(R.id.Button_log2);
        editText1=(EditText)findViewById(R.id.username);
        editText2=(EditText)findViewById(R.id.password);

        if(RP)
        {
            String username = pref.getString("username","");
            String password = pref.getString("password","");
            editText1.setText(username );
            editText1.setSelection(username.length());
            editText2.setText(password);
            checkBox2.setChecked(true);
        }

        button_logs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
        button_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                String username = editText1.getText().toString();
                String password = editText2.getText().toString();

                if(!validataUsername(username))
                {
                    textInputLayout1.setError("用户名不正确！");
                }
                else if(!validatePassword(password))
                {
                    textInputLayout2.setError("密码至少需要6位");
                }
                else
                {
                    textInputLayout1.setErrorEnabled(false);
                    textInputLayout2.setErrorEnabled(false);

                    editor = pref.edit();
                    //测试用注释
                    if(checkBox2.isChecked())
                    {
                        editor.putBoolean("remember_password",true);
                        editor.putString("username",username);
                        editor.putString("password",password);
                    }
                    else
                        editor.clear();
                    editor.commit();

                    //测试用,固定家长为13958116070，教师为18958155002
                    if(username.equals("13958116070"))
                    {
                        Log.d("fuck", "aaa");
                        userInfo = new InfoMap();
                        userInfo.put("name","李狗蛋");
                        userInfo.put("sex","男");
                        userInfo.put("username",username);
                        userInfo.put("password",password);
                        userInfo.put("children","李二狗");
                        FirstActivity.actionStart(LoginActivity.this,UserType.PARENT,userInfo);
                    }
                    else
                    if(username.equals("18958155002"))
                    {
                        userInfo = new InfoMap();
                        userInfo.put("username",username);
                        userInfo.put("name","戴老师");
                        userInfo.put("sex","男");
                        userInfo.put("password",password);
                        userInfo.put("class","高三二班");
                        FirstActivity.actionStart(LoginActivity.this,UserType.TEACHER,userInfo);
                    }
                    //测试代码结束

//                    Intent intent = new Intent(LoginActivity.this,FirstActivity.class);
//                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public  boolean validataUsername(String username)
    {
        return username.length()==11;
    }

    public boolean validatePassword(String password) {
        return password.length() > 5;
    }


}
