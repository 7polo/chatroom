package com.apologize.chatroom.voicefunction;

import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.apologize.chatroom.AdapterPage.MSGListViewAdapter;
import com.apologize.chatroom.MSG.Message;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.TextUnderstander;
import com.iflytek.cloud.TextUnderstanderListener;
import com.iflytek.cloud.UnderstanderResult;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import utils.JSONParser;

//import android.nfc.Tag;

/**
 * Created by apologize on 2016/9/25.
 */

public class VoiveUtils {

    private static VoiveUtils instance;
    private String voiceMsg=""; //信息

    private VoiveUtils(){

    }

    public static synchronized VoiveUtils getInstance(){
        if (instance==null){
            instance = new VoiveUtils();
        }
        return instance;
    }


    /**
     * 语音说话部分
     */
    public void makeVoice(Context context,String voice){
        //1.创建SpeechSynthesizer 对象, 第二个参数：本地合成时传InitListener
        SpeechSynthesizer mTts= SpeechSynthesizer.createSynthesizer(context, new InitListener() {
            @Override
            public void onInit(int i) {
            }
        });
        //2.合成参数设置，详见《MSC Reference Manual》SpeechSynthesizer 类
        //设置发音人（更多在线发音人，用户可参见 附录13.2
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");
        //设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "40");
        //设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "80");
        //设置音量，范围 0~100
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        //设置云端 //设置合成音频保存位置（可自定义保存位置），保存在“./sdcard/iflytek.pcm”
        //保存在 SD 卡需要在 AndroidManifest.xml 添加写 SD 卡权限
        //仅支持保存为 pcm 和 wav 格式，如果不需要保存合成音频，注释该行代码
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./sdcard/iflytek.pcm");
        //3.开始合成
        mTts.startSpeaking(voice, null);
    }

    /**
     * 语音识别部分
     */
    public void startVoice(final Context context, final TextView view) {

        // 1.创建RecognizerDialog对象
        RecognizerDialog mDialog = new RecognizerDialog(context, null);
        // 2.设置accent、language等参数
        mDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mDialog.setParameter(SpeechConstant.ACCENT, "mandarin");
        // 若要将UI控件用于语义理解，必须添加以下参数设置，设置之后onResult回调返回将是语义理解 //结果 //
        //mDialog.setParameter("asr_sch", "1"); //
        //mDialog.setParameter("nlp_version", "2.0");
        // 3.设置回调接口
        mDialog.setListener(new RecognizerDialogListener() {

            @Override
            public void onResult(RecognizerResult arg0, boolean arg1) {

                voiceMsg += JSONParser.parserVoiceString(arg0.getResultString());
                if (arg1) {
                    view.setText(voiceMsg);
                    voiceMsg = "";
                }
            }

            @Override
            public void onError(SpeechError arg0) {

            }
        });
        // 4.显示dialog，接收语音输入
        mDialog.show();
    }

    /**
     * 文本语义识别
     */
    public void textUnderstand(final Context context, String str, final BaseAdapter adapter){
        TextUnderstander mTextUnderstander = TextUnderstander.createTextUnderstander(context, null);
        //开始语义理解
        mTextUnderstander.understandText(str, new TextUnderstanderListener() {
            // 初始化监听器

            @Override
            public void onResult(UnderstanderResult understanderResult) {
                String str = JSONParser.parserUnderstand(understanderResult.getResultString());
                makeVoice(context, str); //回复并朗读
                ((MSGListViewAdapter)adapter).addDataList(new Message(Message.MSG_LEFT, str, null));

            }

            @Override
            public void onError(SpeechError speechError) {

            }
        });


    }
}
