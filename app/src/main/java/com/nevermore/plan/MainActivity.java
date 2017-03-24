package com.nevermore.plan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nevermore.base.BaseActivity;
import com.vincent.filepicker.Constant;
import com.vincent.filepicker.activity.ImagePickActivity;
import com.vincent.filepicker.filter.entity.ImageFile;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {


    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tool_bar)
    Toolbar toolBar;
    @Bind(R.id.design_navigation_view)
    NavigationView designNavigationView;
    @Bind(R.id.iv_background)
    ImageView ivBackground;
    @Bind(R.id.slidingPaneLayout)
    SlidingPaneLayout slidingPaneLayout;
    @Bind(R.id.activity_main)
    LinearLayout activityMain;
    private SharedPreferences systeminfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setTitle("主页");

        systeminfo = getSharedPreferences("systeminfo", MODE_PRIVATE);
        String background = systeminfo.getString("background", "");
        if (!TextUtils.isEmpty(background)) {
            setBackground(background);
        }

        initView();

    }

    private void initView() {

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(slidingPaneLayout.isOpen()){
                    slidingPaneLayout.closePane();
                }else {
                    slidingPaneLayout.openPane();
                }
            }
        });

        slidingPaneLayout.setCoveredFadeColor(Color.YELLOW);

//        tvTitle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ProgressDialog progressDialog = new ProgressDialog(MainActivity.this, android.R.style.Theme_Holo_Light_Dialog);
//                Window window = progressDialog.getWindow();
//                if(window!=null){
//                    window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
//                }
//                progressDialog.setMessage("加载中...");
//                progressDialog.show();
//
//
//            }
//        });
        designNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(final @NonNull MenuItem item) {
                slidingPaneLayout.closePane();

                final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this, android.R.style.Theme_Holo_Light_Dialog);
                Window window = progressDialog.getWindow();
                if (window != null) {
                    window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
                }
                progressDialog.setCancelable(false);
                progressDialog.setMessage("加载中...");
                progressDialog.show();
                tvTitle.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        go(item);

                    }
                },2000);



                return true;
            }
        });
    }

    private void go(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.select_broswer:
                startActivity(new Intent(MainActivity.this, ListActivity.class));
                break;
            case R.id.select_jilu:
                startActivity(new Intent(MainActivity.this, EditActivity.class));
                break;
            case R.id.select_readme:
                startActivity(new Intent(MainActivity.this,ReadmeActivity.class));
                break;
        }
    }

    private void setBackground(String background) {
        Glide.with(this).load("file://" + background).into(ivBackground);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_background, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.m_bg) {
            Intent intent = new Intent(this, ImagePickActivity.class);
            intent.putExtra(ImagePickActivity.IS_NEED_CAMERA, true);
            intent.putExtra(Constant.MAX_NUMBER, 1);
            startActivityForResult(intent, Constant.REQUEST_CODE_PICK_IMAGE);
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == Constant.REQUEST_CODE_PICK_IMAGE) {
            ArrayList<ImageFile> list = data.getParcelableArrayListExtra(Constant.RESULT_PICK_IMAGE);
            ImageFile imageFile = list.get(0);
            String path = imageFile.getPath();
            systeminfo.edit().putString("background", path).apply();
            setBackground(path);
        }
    }


}
