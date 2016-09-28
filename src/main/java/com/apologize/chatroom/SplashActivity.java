package com.apologize.chatroom;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * Created by apologize on 2016/9/28.
 */

public class SplashActivity extends Activity {

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.arg1==1){
                Intent intent = new Intent("com.apologizebao.MainActivity");
                SplashActivity.this.startActivity(intent);
                SplashActivity.this.finish();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_layout);

        Message msg = Message.obtain();
        msg.arg1 = 1;
        handler.sendMessageDelayed(msg,2000);
    }
}
