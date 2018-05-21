package hw.service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Decoder;

import org.json.JSONObject;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

import com.baidu.aip.face.AipFace;
import com.baidu.aip.face.MatchRequest;

import hw.faceAlgorithm.FaceDetection;


public class Face {
	
	public Double faceMarchByBaidu(String img, String photo) {
		System.out.println("����ٶ�������ʶ��ģʽ");
        String APP_ID = "11184535";  
        String API_KEY = "EPZ2ySr21bcZEfbfe0LattGu";  
        String SECRET_KEY = "ziGfg6aYbnw3vAjMOBpFCHMp88pYMWCd";  
        AipFace client = new AipFace(APP_ID, API_KEY, SECRET_KEY);  
        
        //��ȡphoto��ͼƬ�ļ�
		FileInputStream fis = null;
		BufferedReader bur = null;
		StringBuffer sb = null;
		try {
			fis = new FileInputStream("D:\\picture\\database\\baidu_img\\"+photo);
			bur = new BufferedReader(new InputStreamReader(fis));
			sb = new StringBuffer();		
			String temp = null;
			while((temp=bur.readLine())!=null) {
				sb.append(temp);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				bur.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        //��ʼ��������ʶ��
	    MatchRequest req1 = new MatchRequest(sb.toString(), "BASE64");
	    MatchRequest req2 = new MatchRequest(img, "BASE64");
	    ArrayList<MatchRequest> requests = new ArrayList<MatchRequest>();
	    requests.add(req1);
	    requests.add(req2);

	    JSONObject res = client.match(requests);
	    JSONObject jos = (JSONObject) res.get("result");
	    System.out.println(jos.get("score"));
	    
	    return jos.optDouble("score");		
	}
	
	public static void SaveImageByBaidu(String img, String imgFilePath) {
		System.out.println("BASE64��ʽ����ע��ͼƬ"); 
		ByteArrayInputStream bi = null;
		FileOutputStream out = null;
		try {
			bi = new ByteArrayInputStream(img.getBytes());
			out = new FileOutputStream(imgFilePath);  
			byte[] buf = new byte[1024];
			int len = 0;
			while((len = bi.read(buf))!= -1) {
				out.write(buf);
			}
			out.flush();  
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			} 
			try {
				bi.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}  
	

	public static Double faceMarchByOpenCV(String img, String photo) {
		System.out.println("����opencvʶ��ģʽ"); 
		//�������ͼƬ����
		SaveImageByOpenCV(img, "D:\\picture\\login\\getted_photo.jpg","",false);
		
		//��������������
		System.out.println("��Ⲣ�������ݿ��ȡ����Ƭ������"); 
		Mat src = Highgui.imread("D:\\picture\\register\\"+photo+"\\image_final.jpg"); 
		//Mat src = FaceDetection.detectFace("D:\\picture\\register\\"+photo+"\\image_final.jpg",false,"");
		System.out.println("��Ⲣ�����½�õ���Ƭ������"); 
		Mat dst = Highgui.imread("D:\\picture\\login\\image_final.jpg"); 
		//Mat dst = FaceDetection.detectFace("D:\\picture\\login\\image_final.jpg",false,"");
		
		//����ʶ��
		System.out.println("����orb������ʶ��");
		FaceDetection.FeatureOrbLannbased(src, dst);
		System.out.println("����sift������ʶ��");
		FaceDetection.FeatureSiftLannbased(src, dst);
		System.out.println("����surf������ʶ��");
		FaceDetection.FeatureSurfBruteforce(src, dst);
		return 90.00;
	}
	
    public static void SaveImageByOpenCV(String img, String imgFilePath, String SQLFileName, boolean flag) {  
    	System.out.println("��������ʽ����ע��ͼƬ"); 
        Decoder decoder = Base64.getDecoder(); 
        try {  
            // Base64����  
            byte[] bytes = decoder.decode(img);  
            for (int i = 0; i < bytes.length; ++i) {  
                if (bytes[i] < 0) { 
                    bytes[i] += 256;  
                }  
            }  
            // ����jpegͼƬ  
            OutputStream out = new FileOutputStream(imgFilePath);  
            out.write(bytes);  
            out.flush();  
            out.close();    
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        System.out.println("����Ƿ�����������");
        Mat src = FaceDetection.detectFace(imgFilePath,flag,SQLFileName);
    }  
}
