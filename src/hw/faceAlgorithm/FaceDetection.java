package hw.faceAlgorithm;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class FaceDetection {
	
	public static void detectFace()  
    {  
        System.out.println("\nRunning DetectFaceDemo");  
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        // 从配置文件lbpcascade_frontalface.xml中创建一个人脸识别器
        CascadeClassifier faceDetector = new CascadeClassifier(  
                "D:\\temp_kaifa_java\\myFaceDetection\\WebContent\\haarcascade_frontalface_alt.xml");  
        // 读取一张图片 
        Mat image = Imgcodecs.imread("D:\\picture\\1.jpg");
        // 在图片中检测人脸  
        MatOfRect faceDetections = new MatOfRect();  
        faceDetector.detectMultiScale(image, faceDetections);  
        System.out.println(String.format("Detected %s faces",  
                faceDetections.toArray().length));  
        //判断有多少个人脸，正常情况下只需要一个人脸
        Rect[] rects = faceDetections.toArray();  
        if(rects != null && rects.length > 1){  
            throw new RuntimeException("超过一个脸");  
        }  
        // 在每一个识别出来的人脸周围画出一个方框  
        Rect rect = rects[0];  
        Imgproc.rectangle(image, new Point(rect.x-2, rect.y-2), new Point(rect.x  
                + rect.width, rect.y + rect.height), new Scalar(0, 255, 0)); 
        //截取方框中的人脸
        Mat sub = image.submat(rect);//根据方框获取新图像-人脸
        Mat mat = new Mat();  
        Size size = new Size(300, 300);  
        Imgproc.resize(sub, mat, size);//将人脸进行截图并保存到mat中
  
        // 将结果保存到文件
        String filename = "D:\\picture\\faceDetection.png";  
        System.out.println(String.format("Writing %s", filename));  
        Imgcodecs.imwrite(filename, mat);  
    }  
	
	
	
    /** - 反色处理 - */
    public Mat inverse(Mat image) {
        int width = image.cols();
        int height = image.rows();
        int dims = image.channels();
        byte[] data = new byte[width*height*dims];
        image.get(0, 0, data);

        int index = 0;
        int r=0, g=0, b=0;
        for(int row=0; row<height; row++) {
            for(int col=0; col<width*dims; col+=dims) {
                index = row*width*dims + col;
                b = data[index]&0xff;
                g = data[index+1]&0xff;
                r = data[index+2]&0xff;

                r = 255 - r;
                g = 255 - g;
                b = 255 - b;

                data[index] = (byte)b;
                data[index+1] = (byte)g;
                data[index+2] = (byte)r;
            }
        }

        image.put(0, 0, data);
        return image;
    }

    public Mat brightness(Mat image) {
        // 亮度提升
        Mat dst = new Mat();
        Mat black = Mat.zeros(image.size(), image.type());
        Core.addWeighted(image, 1.2, black, 0.5, 0, dst);
        return dst;
    }

    public Mat darkness(Mat image) {
        // 亮度降低
        Mat dst = new Mat();
        Mat black = Mat.zeros(image.size(), image.type());
        Core.addWeighted(image, 0.5, black, 0.5, 0, dst);
        return dst;
    }

    public Mat gray(Mat image) {
        // 灰度
        Mat gray = new Mat();
        Imgproc.cvtColor(image, gray, Imgproc.COLOR_BGR2GRAY);
        return gray;
    }

    public Mat sharpen(Mat image) {
        // 锐化
        Mat dst = new Mat();
        float[] sharper = new float[]{0, -1, 0, -1, 5, -1, 0, -1, 0};
        Mat operator = new Mat(3, 3, CvType.CV_32FC1);
        operator.put(0, 0, sharper);
        Imgproc.filter2D(image, dst, -1, operator);
        return dst;
    }

    public Mat blur(Mat image) {
        // 高斯模糊
        Mat dst = new Mat();
        Imgproc.GaussianBlur(image, dst, new Size(15, 15), 0);
        return dst;
    }


    public Mat gradient(Mat image) {
        // 梯度
        Mat grad_x = new Mat();
        Mat grad_y = new Mat();
        Mat abs_grad_x = new Mat();
        Mat abs_grad_y = new Mat();

        Imgproc.Sobel(image, grad_x, CvType.CV_32F, 1, 0);
        Imgproc.Sobel(image, grad_y, CvType.CV_32F, 0, 1);
        Core.convertScaleAbs(grad_x, abs_grad_x);
        Core.convertScaleAbs(grad_y, abs_grad_y);
        grad_x.release();
        grad_y.release();
        Mat gradxy = new Mat();
        Core.addWeighted(abs_grad_x, 0.5, abs_grad_y, 0.5, 10, gradxy);
        return gradxy;
    }
}
