package com.nevermore.plan;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @Bind(R.id.tv_second)
    TextView tvSecond;
    @Bind(R.id.activity_splash)
    RelativeLayout activitySplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        TimeCount timeCount = new TimeCount(6000, 1000);
        timeCount.start();
    }


    class TimeCount extends CountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tvSecond.setText(millisUntilFinished/1000+"s");
        }

        @Override
        public void onFinish() {
            startActivity(new Intent(SplashActivity.this,MainActivity.class));
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            finish();
        }
    }
}
