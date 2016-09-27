package com.apologize.chatroom.savedMessageUtil;

import android.content.Context;
import android.util.Xml;

import com.apologize.chatroom.MSG.Message;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * 信息的保存
 * Created by apologize on 2016/9/27.
 */

public class MeesageHelper {
    private static final String fileName = "message.xml";
    public static void messageWriter(Context context,List<Message> dataList){
        if (dataList!=null){
            /**
             * 初始化pull
            */
            try {
                XmlSerializer serializer = Xml.newSerializer();
                FileOutputStream fileOutputStream = context.getApplicationContext().openFileOutput(fileName,Context.MODE_PRIVATE);
                serializer.setOutput(fileOutputStream,"utf-8");
                serializer.startDocument("utf-8",true);
                serializer.startTag(null,"messages");
                for (Message msg:dataList) {
                    serializer.startTag(null, "message");
                    serializer.attribute(null,"dir",msg.getDir()+"");
                    serializer.text(msg.getContent());
                    serializer.endTag(null,"message");
                }
                serializer.endTag(null,"messages");
                serializer.endDocument();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<Message> messageReader(Context context){
        List<Message> dataList = new ArrayList<Message>();

        try {
            XmlPullParser parser = Xml.newPullParser();
            FileInputStream fileInputStream = context.getApplicationContext().openFileInput(fileName);
            parser.setInput(fileInputStream,"utf-8");

            int type = parser.getEventType();
            while(type!=XmlPullParser.END_DOCUMENT){
                if (type==XmlPullParser.START_TAG && "message".equals(parser.getName())){
                    //读取message
                    int dir = Integer.parseInt(parser.getAttributeValue(0));
                    Message msg = new Message(dir,parser.nextText(),null);
                    dataList.add(msg);
                }
                type = parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataList;
    }
}
