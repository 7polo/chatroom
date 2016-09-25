package com.apologize.chatroom;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.apologize.chatroom.AdapterPage.MSGListViewAdapter;
import com.apologize.chatroom.MSG.Message;
import com.apologize.chatroom.voicefunction.VoiveUtils;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    List<Message> dataList;
    MSGListViewAdapter adapter;
    //
    private ListView listView;
    private EditText editText;
    private Button sendButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findView();
        dataList = new ArrayList<Message>();
        adapter = new MSGListViewAdapter(this,R.layout.msg_layout,dataList);

        dataList.add(new Message(Message.MSG_LEFT,"hahah",null));
        listView.setAdapter(adapter);

        // 将“12345678”替换成您申请的 APPID，申请地址：http://www.xfyun.cn
        // 请勿在“=”与 appid 之间添加任务空字符或者转义符
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=57e67abd");

    }


    /**
     * 绑定控件
     */
    private void findView(){
        listView = (ListView) findViewById(R.id.listView);
        editText = (EditText) findViewById(R.id.editText);
        sendButton = (Button) findViewById(R.id.sendButton);
    }

    public void click(View v){

        int id = v.getId();
        switch (id){
            case R.id.voiceButton:{
                voiceToString();
                break;
            }
            case R.id.sendButton:{
                sendButton();
                break;
            }
        }
    }

    private void sendButton(){
        String text = editText.getText().toString();
        if (text != null && !text.equals("")) {


            adapter.addDataList(new Message(Message.MSG_RIGHT, text, null));
            listView.setSelection(listView.getBottom());
            editText.setText("");
            VoiveUtils.getInstance().textUnderstand(this,text,adapter);
        }
    }

    private void voiceToString(){
        //将识别内容放置输入框
        VoiveUtils.getInstance().startVoice(this,editText);
    }




}
