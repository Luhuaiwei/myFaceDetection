package hw.faceAlgorithm;

import org.opencv.core.MatOfDMatch;
import org.opencv.features2d.DMatch;

/** 
 * ƥ��㼯�������㷨  
 */  
public class Test {  
      
//  private static final int GOOD_DISTANCE_THREASHOLD = 2200;  
    private static final int GOOD_DISTANCE_THREASHOLD = 2200;  
    private static final float ITEM_VALUE_BASE = 0.6f;  
    private static final int MIN_VALUE_LINE = 25;  
      
    /** 
     * match�㼯�������㷨 
     * ÿ�����distanceԽСԽƥ�� 
     * �㼯������Խ��Խƥ�� 
     * @return Խ��Խ����Խ�� 
     */  
    public static int calcValueFromGoodMatch(MatOfDMatch goodMatch) {  
          
        //System.out.println("total:" + goodMatch.total());  
        if (goodMatch.total() == 0) {  
            return 0;  
        }  
        DMatch[] dMatchArr = goodMatch.toArray();  
        float totalValue = 0f;  
        for (DMatch dMatch : dMatchArr) {  
            float distance = dMatch.distance <= 1 ? 1 : dMatch.distance;  
            totalValue += GOOD_DISTANCE_THREASHOLD / distance + ITEM_VALUE_BASE;  
        }  
          
        //System.out.println("totalValue:" + totalValue);  
        int value = (int) (totalValue * 10);  
        //System.out.println("value:" + value);  
        return value > MIN_VALUE_LINE ? value : 0;  
    }  
      
    public static MatOfDMatch getGoodMatch(MatOfDMatch allDMatch) {  
  
//      double max_dist = 0;  
//      double min_dist = 800;  
//      DMatch[] dMatchArr = matOfDMatch.toArray();  
//      for (int i = 0; i < dMatchArr.length; i++) {  
//          double dist = dMatchArr[i].distance;  
//          if (dist < min_dist)  
//              min_dist = dist;  
//          if (dist > max_dist)  
//              max_dist = dist;  
//      }  
          
        MatOfDMatch goodMatch = new MatOfDMatch();  
  
        int num = (int) allDMatch.total();  
        int channel = allDMatch.channels();  
        // TODO ����һ��create��ô��Ļ���  
        float buff[] = new float[num * channel];  
        allDMatch.get(0, 0, buff);  
        for (int i = 0; i < num; i++) {  
            // ÿһ��channel�ĵ���������Ϊdistance  
            if (buff[channel * i + 3] < GOOD_DISTANCE_THREASHOLD) {  
                DMatch dMatch = new DMatch((int) buff[channel * i + 0], (int) buff[channel * i + 1],  
                        (int) buff[channel * i + 2], buff[channel * i + 3]);  
                goodMatch.push_back(new MatOfDMatch(dMatch));  
            }  
        }  
              
        return goodMatch;  
    }
}