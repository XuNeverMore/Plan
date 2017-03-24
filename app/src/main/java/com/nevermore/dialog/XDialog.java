package com.nevermore.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;

/**
 * Created by Administrator on 2017/3/11.
 */

public class XDialog extends Dialog {

    public XDialog(Context context,int layoutid) {
        super(context);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(layoutid);
    }

    public void setOnClickListener(int viewid, View.OnClickListener listener){
        findViewById(viewid).setOnClickListener(listener);
    }
}
