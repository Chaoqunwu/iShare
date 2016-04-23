package com.example.saltwater.brandnewworld;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by ChaoQun on 2016/4/10.
 */
public class ProfilesFragment extends Fragment {

    private View change;
    private Button button;
    private Button buttonN;
    private LinearLayout linearLayout;
    private TextView pName;
    private TextView pUsername;
    private TextView pChildren;
    private TextView pSex;
    private TextView pClass;
    private LinearLayout childrenLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        change=inflater.inflate(R.layout.profiles_fragment, container, false);
        button=(Button)change.findViewById(R.id.btnP);
        buttonN=(Button)change.findViewById(R.id.btnQ);
        linearLayout=(LinearLayout)change.findViewById(R.id.linearLayout2) ;
        childrenLayout = (LinearLayout) change.findViewById(R.id.layout_children_gone);
        pName = (TextView) change.findViewById(R.id.profiles_name);
        pUsername = (TextView) change.findViewById(R.id.profiles_username);
        pChildren = (TextView) change.findViewById(R.id.profiles_children);
        pSex = (TextView) change.findViewById(R.id.profiles_sex);
        pClass = (TextView) change.findViewById(R.id.profiles_class);
        //初始化个人信息
        FirstActivity activity = (FirstActivity) getActivity();
        if(activity.getUserType().equals(UserType.PARENT)) {
            pName.setText(activity.getUserInfo().get("name").toString());
            pUsername.setText("账号:" + activity.getUserInfo().get("username").toString());
            pChildren.setText("子女:" + activity.getUserInfo().get("children").toString());
            pSex.setText("性别:" + activity.getUserInfo().get("sex").toString());
        } else {
            childrenLayout.setVisibility(View.GONE);
            pClass.setVisibility(View.VISIBLE);
            pName.setText(activity.getUserInfo().get("name").toString());
            pUsername.setText("账号:" + activity.getUserInfo().get("username").toString());
            pSex.setText("性别:" + activity.getUserInfo().get("sex").toString());
            pClass.setText("班级:" + activity.getUserInfo().get("class").toString());
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SweetAlertDialog(getActivity(), SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                        .setTitleText("确定编辑个人资料！")
                        .setContentText("下滑有惊喜！")
                        .setCustomImage(R.mipmap.test_people)
                        .setCancelText("手滑...")
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.cancel();
                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                                linearLayout.setVisibility(View.VISIBLE);
                            }
                        })

                        .show();
            }
        });

        buttonN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("更新个人信息")
                        .setConfirmText("Yes,I do!")
                        .setCancelText("No,原来的挺好！")
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.cancel();
                                linearLayout.setVisibility(View.GONE);
                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                // reuse previous dialog instance
                                sDialog.setTitleText("信息上传完毕")
                                        .setContentText("您的ishare个人信息更新成功！")
                                        .setConfirmText("OK")
                                        .showCancelButton(false)
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog)
                                            {
                                                sweetAlertDialog.dismissWithAnimation();
                                                linearLayout.setVisibility(View.GONE);
                                            }
                                        })
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                            }
                        })
                        .show();
            }
        });

        return change;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
