package hw.faceAlgorithm;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.features2d.DMatch;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class FaceDetection {
	
	public static Mat detectFace(String photo,boolean flag,String SQLFileName)  
    {  
		
		String str;
		if(flag) {
			str = "register\\"+SQLFileName;
			File dir = new File("D:\\picture\\"+str);
			dir.mkdir();
		}else {
			str = "login";
		}
        System.out.println("\nRunning DetectFaceDemo");  
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        // �������ļ�lbpcascade_frontalface.xml�д���һ������ʶ����
        CascadeClassifier faceDetector = new CascadeClassifier(  
                "D:\\temp_kaifa_java\\myFaceDetection\\WebContent\\haarcascade_frontalface_alt.xml");  
        // ��ȡһ��ͼƬ  
        Mat image = Highgui.imread(photo);  //Mat image = Imgcodecs.imread(photo);
        Highgui.imwrite("D:\\picture\\"+str+"\\image.jpg", image);
        // ��ͼƬ�м������ 
        MatOfRect faceDetections = new MatOfRect();  
        faceDetector.detectMultiScale(image, faceDetections);  

        System.out.println(String.format("Detected %s faces",faceDetections.toArray().length));  
        //�ж��ж��ٸ����������������ֻ��Ҫһ������
        Rect[] rects = faceDetections.toArray();  
        if(rects != null && rects.length > 1){  
            throw new RuntimeException("����һ����");  
        }  
        // ��ÿһ��ʶ�������������Χ����һ������  
        Rect rect = rects[0];  
        //Imgproc.rectangle(image, new Point(rect.x-2, rect.y-2), new Point(rect.x  
        //        + rect.width, rect.y + rect.height), new Scalar(0, 255, 0)); 
        Core.rectangle(image, new Point(rect.x-2, rect.y-2), new Point(rect.x  
                + rect.width, rect.y + rect.height), new Scalar(0, 255, 0)); 
        
        Highgui.imwrite("D:\\picture\\"+str+"\\image_first.jpg", image);
       
        //��ȡ�����е�����
        Mat sub = image.submat(rect);//���ݷ����ȡ��ͼ��-����      
        Mat mat = new Mat();  
        Size size = new Size(300, 300);  
        Imgproc.resize(sub, mat, size);//����������ָ����С���浽mat��
        // ��������浽�ļ�
/*        String filename = System.currentTimeMillis()+".jpg";	
        System.out.println(String.format("д������ͼƬ��%s", filename));*/
        //Imgcodecs.imwrite(filename, mat);  
        Highgui.imwrite("D:\\picture\\"+str+"\\image_seconed.jpg", mat);    
        
        mat = prepImage(mat);
        Highgui.imwrite("D:\\picture\\"+str+"\\image_final.jpg", mat);        
        return mat;
        
        //Mat src = Highgui.imread("D:\\picture\\2.jpg");   
        //Highgui.imwrite("D:\\picture\\Test.jpg", FeatureOrbLannbased(sub, sub)); 
        //prepImage(mat);
    }  
	
    public static Mat prepImage(Mat image) { 
        // �ҶȻ�����
        Mat gray = new Mat();
        Imgproc.cvtColor(image, gray, Imgproc.COLOR_BGR2GRAY);
        //ֱ��ͼ���⻯
        Mat balance = new Mat();
        Imgproc.equalizeHist(gray,balance);
       
/*        String filename = "D:\\picture\\facePrepare.png";  
        System.out.println(String.format("Writing %s", filename));  
        Highgui.imwrite(filename, balance);  */
        return balance;
    }
    
    //
    public static Mat FeatureOrbLannbased(Mat src, Mat dst){  
    	long start = System.currentTimeMillis();
        FeatureDetector fd = FeatureDetector.create(FeatureDetector.ORB);  
        DescriptorExtractor de = DescriptorExtractor.create(DescriptorExtractor.ORB);  
        DescriptorMatcher Matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_L1);  
        MatOfKeyPoint mkp = new MatOfKeyPoint();
        
        fd.detect(src, mkp);
        Mat desc = new Mat();  
        de.compute(src, mkp, desc); 
        Features2d.drawKeypoints(src, mkp, src);  
        Highgui.imwrite("D:\\picture\\orb_src_drawKeypoints.jpg", src);  
        
        MatOfKeyPoint mkp2 = new MatOfKeyPoint();  
        fd.detect(dst, mkp2); 
        Mat desc2 = new Mat();  
        de.compute(dst, mkp2, desc2);
        Features2d.drawKeypoints(dst, mkp2, dst); 
        Highgui.imwrite("D:\\picture\\orb_dst_drawKeypoints.jpg", dst); 
        
        MatOfDMatch Matches = new MatOfDMatch();  
        Matcher.match(desc, desc2, Matches);
        
/*        double maxDist = Double.MIN_VALUE;  
        double minDist = Double.MAX_VALUE;  
        System.out.println("��ȡ���������"+Matches.rows());
        DMatch[] mats = Matches.toArray();  
        for (int i = 0; i < mats.length; i++) {  
            double dist = mats[i].distance;  
            if (dist < minDist) { minDist = dist;}  
            if (dist > maxDist) { maxDist = dist;}}  
        List<DMatch> goodMatch = new LinkedList<>();  
        for (int i = 0; i < mats.length; i++) {  
            double dist = mats[i].distance;  
            if (dist < 3 * minDist && dist < 0.2f) {goodMatch.add(mats[i]);}}  
        Matches.fromList(goodMatch);  */
        
        MatOfDMatch goodMatch = new MatOfDMatch();  
        System.out.println("��ȡ���������"+Matches.rows());
        int num = (int) Matches.total();  
        int channel = Matches.channels();  
        // TODO ����һ��create��ô��Ļ���  
        float buff[] = new float[num * channel];  
        Matches.get(0, 0, buff);  
        for (int i = 0; i < num; i++) {  
            // ÿһ��channel�ĵ���������Ϊdistance  
            if (buff[channel * i + 3] < 1100) {  
                DMatch dMatch = new DMatch((int) buff[channel * i + 0], (int) buff[channel * i + 1],  
                        (int) buff[channel * i + 2], buff[channel * i + 3]);  
                goodMatch.push_back(new MatOfDMatch(dMatch));  
            }  
        }  
        
        System.out.println("��Ч���������"+goodMatch.rows());
        Mat OutImage = new Mat();  
        Features2d.drawMatches(src, mkp, dst, mkp2, goodMatch, OutImage);   
        Highgui.imwrite("D:\\picture\\result_orb.jpg", OutImage); 
        System.out.println("ʶ����ʱ��"+(System.currentTimeMillis() - start)+"ms");
        return OutImage;  
    }
    
    
    
    public static Mat FeatureSurfBruteforce(Mat src, Mat dst){  
    	long start = System.currentTimeMillis();
        FeatureDetector fd = FeatureDetector.create(FeatureDetector.SURF);  
        DescriptorExtractor de = DescriptorExtractor.create(DescriptorExtractor.SURF);  
        DescriptorMatcher Matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_L1);         
        MatOfKeyPoint mkp = new MatOfKeyPoint();  
        
        fd.detect(src, mkp);  
        Mat desc = new Mat();  
        de.compute(src, mkp, desc);  
        Features2d.drawKeypoints(src, mkp, src); 
        Highgui.imwrite("D:\\picture\\surf_src_drawKeypoints.jpg", src);
        
        MatOfKeyPoint mkp2 = new MatOfKeyPoint();  
        fd.detect(dst, mkp2);  Mat desc2 = new Mat();  
        de.compute(dst, mkp2, desc2);  
        Features2d.drawKeypoints(dst, mkp2, dst); 
        Highgui.imwrite("D:\\picture\\surf_dst_drawKeypoints.jpg", dst);
        
        MatOfDMatch Matches = new MatOfDMatch();  
        Matcher.match(desc, desc2, Matches);          
        double maxDist = Double.MIN_VALUE;  
        double minDist = Double.MAX_VALUE;  
        System.out.println("��ȡ���������"+Matches.rows());
        DMatch[] mats = Matches.toArray();  
        for (int i = 0; i < mats.length; i++) {  
            double dist = mats[i].distance;  
            if (dist < minDist) {minDist = dist;}                 
            if (dist > maxDist) {maxDist = dist;}}                  
        List<DMatch> goodMatch = new LinkedList<>();  
        for (int i = 0; i < mats.length; i++) {  
            double dist = mats[i].distance;  
            if (dist < 3 * minDist && dist < 0.2f) {goodMatch.add(mats[i]);}}       
        Matches.fromList(goodMatch);  
        
        System.out.println("��Ч���������"+Matches.rows());
        Mat OutImage = new Mat();  
        Features2d.drawMatches(src, mkp, dst, mkp2, Matches, OutImage);       
        Highgui.imwrite("D:\\picture\\result_surf.jpg", OutImage);
        System.out.println("ʶ����ʱ��"+(System.currentTimeMillis() - start)+"ms");
        return OutImage;  
    } 
    
    
    
    
    public static Mat FeatureSiftLannbased(Mat src, Mat dst){ 
    	long start = System.currentTimeMillis();
        FeatureDetector fd = FeatureDetector.create(FeatureDetector.SIFT);  
        DescriptorExtractor de = DescriptorExtractor.create(DescriptorExtractor.SIFT);  
        DescriptorMatcher Matcher = DescriptorMatcher.create(DescriptorMatcher.FLANNBASED);           
        MatOfKeyPoint mkp = new MatOfKeyPoint();  
        
        fd.detect(src, mkp);  
        Mat desc = new Mat();  
        de.compute(src, mkp, desc);  
        Features2d.drawKeypoints(src, mkp, src); 
        Highgui.imwrite("D:\\picture\\sift_src_drawKeypoints.jpg", src);
        
        MatOfKeyPoint mkp2 = new MatOfKeyPoint();  
        fd.detect(dst, mkp2);  
        Mat desc2 = new Mat();  
        de.compute(dst, mkp2, desc2);  
        Features2d.drawKeypoints(dst, mkp2, dst);  
        Highgui.imwrite("D:\\picture\\sift_dst_drawKeypoints.jpg", dst);
        
        MatOfDMatch Matches = new MatOfDMatch();  
        Matcher.match(desc, desc2, Matches);  
        System.out.println("��ȡ���������"+Matches.rows());  
        List<DMatch> l = Matches.toList();  
        List<DMatch> goodMatch = new ArrayList<DMatch>();  
        for (int i = 0; i < l.size(); i++) {  
            DMatch dmatch = l.get(i);  
            if (Math.abs(dmatch.queryIdx - dmatch.trainIdx) < 10f) {  
                goodMatch.add(dmatch);  
            }        
        }          
        Matches.fromList(goodMatch);  
        
        System.out.println("��Ч���������"+Matches.rows());  
        Mat OutImage = new Mat();  
        Features2d.drawMatches(src, mkp, dst, mkp2, Matches, OutImage);           
        Highgui.imwrite("D:\\picture\\result_sift.jpg", OutImage);
        long end = System.currentTimeMillis();
        System.out.println("ʶ����ʱ��"+(end - start)+"ms");
        return OutImage;  
    }  
}
