package com.nevermore.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.nevermore.plan.R;

/**
 * Created by Administrator on 2017/3/11.
 */

public class BaseActivity extends AppCompatActivity {


    private TextView tvTitle;
    public Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    public void setTitle(String title) {
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        if (toolbar != null) {
            toolbar.setTitle("");
        }
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if(supportActionBar!=null){
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (tvTitle != null)
            tvTitle.setText(title);
        assert toolbar != null;
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
