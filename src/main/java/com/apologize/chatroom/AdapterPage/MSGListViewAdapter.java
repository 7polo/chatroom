package com.apologize.chatroom.AdapterPage;


import android.content.ClipboardManager;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.apologize.chatroom.MSG.Message;
import com.apologize.chatroom.R;

import java.util.List;

/**
 * Created by apologize on 2016/9/24.
 */

public class MSGListViewAdapter extends BaseAdapter{
    private int layoutID;
    private List<Message> dataList;
    private Context context;
    ViewHolder viewHolder;

    public MSGListViewAdapter(Context context,int layoutID, List<Message> dataList) {
        this.layoutID = layoutID;
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Message msg = dataList.get(position);

        if (convertView==null) {
            convertView = View.inflate(context, layoutID, null);
            viewHolder = new ViewHolder();
            viewHolder.right_layout = (LinearLayout) convertView.findViewById(R.id.right_layout);
            viewHolder.right_textView =  (TextView) convertView.findViewById(R.id.right_textView);
            viewHolder.left_layout = (LinearLayout) convertView.findViewById(R.id.left_layout);
            viewHolder.left_textView =  (TextView) convertView.findViewById(R.id.left_textView);
            convertView.setTag(viewHolder); //保存
        }
        else
            viewHolder = (ViewHolder) convertView.getTag(); //读取


        /**
         * 重新设置 layout的 隐藏显示属性，因为这次重用的View可能是隐藏的
         */
        if (msg.getDir()==Message.MSG_LEFT){

            viewHolder.right_layout.setVisibility(View.GONE);
            viewHolder.left_layout.setVisibility(View.VISIBLE);
            viewHolder.left_textView.setText(msg.getContent());
        }
        else if(msg.getDir()==Message.MSG_RIGHT){
            viewHolder.left_layout.setVisibility(View.GONE);
            viewHolder.right_layout.setVisibility(View.VISIBLE);
            viewHolder.right_textView.setText(msg.getContent());
            //事件监听
            //点击事件的监听
            viewHolder.right_textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    Toast.makeText(context,viewHolder.right_textView.getText(),Toast.LENGTH_LONG).show();
                }
            });

            //长按事件
            viewHolder.right_textView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //长按复制
                    ClipboardManager manager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    manager.setText(viewHolder.right_textView.getText());
                    Toast.makeText(context,"已复制",Toast.LENGTH_LONG).show();
                    //TODO 粘贴
                    return true;
                }
            });
        }
        return convertView;
    }

    /**
     * 优化
     */
    class ViewHolder{
        LinearLayout right_layout;
        LinearLayout left_layout;
        TextView right_textView;
        TextView left_textView;
    }
}
