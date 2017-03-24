package com.nevermore.dialog;

import android.content.Context;
import android.view.View;

import com.nevermore.plan.R;

/**
 * Created by Administrator on 2017/3/11.
 */

public class ItemDialog extends XDialog implements View.OnClickListener{
    public ItemDialog(Context context) {
        super(context, R.layout.option);
        init();
    }

    private void init() {
        setOnClickListener(R.id.tv_cancel,this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_cancel:
                dismiss();
                break;

        }
    }
}
