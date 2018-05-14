package hw.service;

import java.util.ArrayList;

import org.json.JSONObject;

import com.baidu.aip.face.AipFace;
import com.baidu.aip.face.MatchRequest;


public class FaceDetection {
	
	public Double faceMarchByBaidu(String img, String photo) {
        String APP_ID = "11184535";  
        String API_KEY = "EPZ2ySr21bcZEfbfe0LattGu";  
        String SECRET_KEY = "ziGfg6aYbnw3vAjMOBpFCHMp88pYMWCd";  
        AipFace client = new AipFace(APP_ID, API_KEY, SECRET_KEY);  
        
		System.out.println("进入百度云人脸识别模式");
	
	    MatchRequest req1 = new MatchRequest(photo, "BASE64");
	    MatchRequest req2 = new MatchRequest(img, "BASE64");
	    ArrayList<MatchRequest> requests = new ArrayList<MatchRequest>();
	    requests.add(req1);
	    requests.add(req2);

	    JSONObject res = client.match(requests);
	    JSONObject jos = (JSONObject) res.get("result");
	    System.out.println(jos.get("score"));
	    
	    return jos.optDouble("score");
		
	}
}
