package utils;

import com.google.gson.Gson;
import java.util.ArrayList;

import utils.VoiceBean.WS;


public class JSONParser {
	
	
	public static String parserString(String str){
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
	
}
