package com.apologize.chatroom;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.apologize.chatroom.AdapterPage.MSGListViewAdapter;
import com.apologize.chatroom.MSG.Message;

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
        String text = editText.getText().toString();
        if (text!=null&&!text.equals("")){

            dataList.add(new Message(Message.MSG_RIGHT,text,null));
            adapter.notifyDataSetChanged();  //更新
            listView.setSelection(listView.getBottom());
            editText.setText("");
        }
    }
}
