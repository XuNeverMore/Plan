package com.nevermore.plan;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nevermore.base.BaseActivity;
import com.nevermore.bean.Record;
import com.nevermore.common.MyApp;
import com.vincent.filepicker.Constant;
import com.vincent.filepicker.activity.ImagePickActivity;
import com.vincent.filepicker.filter.entity.ImageFile;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditActivity extends BaseActivity {

    private static final String TAG = "EditActivity";
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tool_bar)
    Toolbar toolBar;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.btn_save)
    Button btnSave;
    @Bind(R.id.edit)
    EditText edit;
    @Bind(R.id.ll_img_container)
    LinearLayout llImgContainer;
    @Bind(R.id.btn_img)
    Button btnImg;
    @Bind(R.id.iv_text)
    ImageView ivText;
    @Bind(R.id.activity_edit)
    LinearLayout activityEdit;
    private int type;
    private String color = "#ffffff";
    private ArrayList<String> images = new ArrayList<>();
    private Ringtone ringtone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ButterKnife.bind(this);
        setTitle("记录生活");

        type = getIntent().getIntExtra("type_record", 0);

        init();

        Uri uri = RingtoneManager.getActualDefaultRingtoneUri(this, RingtoneManager.TYPE_ALL);
        Uri actualDefaultRingtoneUri = RingtoneManager.getActualDefaultRingtoneUri(this, RingtoneManager.TYPE_NOTIFICATION);
        ringtone = RingtoneManager.getRingtone(this, actualDefaultRingtoneUri);
    }

    private void init() {
        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvContent.setText(edit.getText().toString());
            }
        });





        Drawable d;

    }

    @OnClick({R.id.btn_img, R.id.tv_title, R.id.btn_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_title:
                break;
            case R.id.btn_img:
                if (images.size() > 0) {
                    Toast.makeText(this, "一张就够了,不能再多", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(this, ImagePickActivity.class);
                intent.putExtra(ImagePickActivity.IS_NEED_CAMERA, true);
                intent.putExtra(Constant.MAX_NUMBER, 1);
                startActivityForResult(intent, Constant.REQUEST_CODE_PICK_IMAGE);
                break;

            case R.id.btn_save:
                String imgpath = null;
                if (images.size() > 0) {
                    type = 1;
                    imgpath = images.get(0);
                }


                Record record = new Record(System.currentTimeMillis(), System.currentTimeMillis(), tvContent.getText().toString(), type, color, imgpath);

                long insert = MyApp.getInstance().getRecordDao().insert(record);

                if (insert > 0) {
                    String title = ringtone.getTitle(this);
                    ringtone.play();
//                    Toast.makeText(this, title, Toast.LENGTH_SHORT).show();
                    finish();
                }

                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_option, menu);

        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK) {
            ArrayList<ImageFile> list = data.getParcelableArrayListExtra(Constant.RESULT_PICK_IMAGE);
            for (int i = 0; i < list.size(); i++) {

                ImageView imageView = new ImageView(this);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
                ImageFile imageFile = list.get(i);
                String path = imageFile.getPath();
                Log.i(TAG, "onActivityResult: " + path);
                Glide.with(this).load("file://" + path).into(imageView);
                llImgContainer.addView(imageView);
                images.add(path);
            }

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_white:
                color = "#ffffff";
                tvContent.setBackgroundResource(R.drawable.rect_white);
                break;
            case R.id.menu_green:
                color = "#4caf65";
                tvContent.setBackgroundResource(R.drawable.rect_green);
                break;
            case R.id.menu_blue:
                color = "#26a5e1";
                tvContent.setBackgroundResource(R.drawable.rect_blue);
                break;
        }

        return true;
    }
}
