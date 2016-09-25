package utils;

import com.google.gson.Gson;
import java.util.ArrayList;

import utils.VoiceBean.WS;


public class JSONParser {

	public static String parserVoiceString(String str){
		Gson gson = new Gson();
		StringBuffer stringBuffer = new StringBuffer();
		VoiceBean voiceBean = gson.fromJson(str, VoiceBean.class);
		ArrayList<WS> wsList = voiceBean.ws;
		for (WS ws : wsList) {
			if (ws.cw.get(0).w!=null)
				stringBuffer.append(ws.cw.get(0).w);
		}
		return stringBuffer.toString();
	}

	/**
	 * 解析语义识别的结果
	 * @return
     */
	public static String parserUnderstand(String json){
		Gson gson = new Gson();
		UnderstandBean understandBean = gson.fromJson(json,UnderstandBean.class);
		if (understandBean.rc==4)
			return "说什么啊,我听不懂";
		else
			return understandBean.answer.text;
	}
	
}
