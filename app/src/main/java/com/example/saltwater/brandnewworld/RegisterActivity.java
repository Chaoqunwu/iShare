package com.example.saltwater.brandnewworld;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * Created by ChaoQun on 2016/4/7.
 */
public class RegisterActivity extends Activity {

    private Button button;
    private Button vCode;
    private EditText phoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_main);

        button = (Button) findViewById(R.id.button);
        vCode = (Button) findViewById(R.id.vCode);
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("确定?")
                        .setContentText("同意app的使用条件和隐私声明")
                        .setConfirmText("是的")
                        .setCancelText("不，我再考虑一下")
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.cancel();
                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                // reuse previous dialog instance
                                sDialog.setTitleText("快去登陆吧！")
                                        .setContentText("您的iShare账号注册成功！")
                                        .setConfirmText("好的")
                                        .showCancelButton(false)
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                finish();
                                            }
                                        })
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                            }
                        })
                        .show();
            }
        });

        vCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer code = (int) (100000 + Math.random() * 900000);
                String username = phoneNumber.getText().toString();
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(username,null,code.toString(),null,null);
                vCode.setText("验证码已发送到手机，请查收");
            }
        });
    }
}
