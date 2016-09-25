package utils;

import java.util.ArrayList;

public class VoiceBean {
	public boolean ls;
	public ArrayList<WS> ws;
	
	class WS{
		public ArrayList<CW> cw;
	}
	class CW{
		public String w;
	}
}
