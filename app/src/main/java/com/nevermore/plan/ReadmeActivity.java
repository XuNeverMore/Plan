package com.nevermore.plan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nevermore.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ReadmeActivity extends BaseActivity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tool_bar)
    Toolbar toolBar;
    @Bind(R.id.tv_readme)
    TextView tvReadme;
    @Bind(R.id.activity_readme)
    LinearLayout activityReadme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readme);
        ButterKnife.bind(this);
        setTitle("说明");
        tvReadme.setText("未经本人同意，不得用于商业用途。\n徐影魔\nQQ:1045530120");
    }
}
