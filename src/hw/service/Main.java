package hw.service;  

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
  
import javax.servlet.ServletException;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  

import hw.dao.Function;
import hw.model.Users;
 
  
public class Main extends HttpServlet {  
    public void doGet(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {doPost(request, response);}  
    public void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
        //防止乱码  
        response.setContentType("text/html;charset=utf-8");  
        request.setCharacterEncoding("utf-8");  
        response.setCharacterEncoding("utf-8");  
          
        String img       = request.getParameter("img");            //图像数据  
        String username  = request.getParameter("username");       //用户名  
        String tag       = request.getParameter("tag");  
        String password  = request.getParameter("password");

        if(tag.equals("reg")){  
        	System.out.println("进入用户注册程序");
        	register(username, img, password);
        }else if(tag.endsWith("login")){  
        	System.out.println("进入用户登录程序");
            loginNew(img, response, username);
        }       
    }  
    
    /**
     * 用户登录  
     * @param img
     * @param response
     * @param username
     */
	public void loginNew(String img, HttpServletResponse response, String username) {
		PrintWriter out = null;
		Double result = 50.00;
        try {  
            out = response.getWriter();  
            String photo = Function.getPhoto(username);			
			if(photo.equals("null")) {
				out.print("用户未注册");
			}else {
            //result = Face.faceMarchByBaidu(img,photo);  //百度云人脸识别接口
            result = Face.faceMarchByOpenCV(img,photo);   //OpenCV人脸识别
	            if (result > 80) {  
	                out.print("登陆成功");    
	            }else{  
	                out.print("登陆失败");  
	            }  
			}
        } catch (IOException | ClassNotFoundException | SQLException e) {  
            e.printStackTrace();  
        }		
	}
	
	/**
	 * 用户注册
	 * @param username
	 * @param img
	 * @param password
	 */
	private void register(String username, String img, String password) {
		Users user = new Users();		
		String fileName = System.currentTimeMillis()+".jpg";		
		Face.SaveImageByOpenCV(img, "D:\\picture\\database\\opencv_img\\"+fileName);
		Face.SaveImageByBaidu(img, "D:\\picture\\database\\baidu_img\\"+fileName);

		//写入数据到实体类中
        user.setId(((Long)System.currentTimeMillis()).intValue());  
        user.setUsername(username);  
        user.setHeadphoto(fileName); 
        user.setPassword(password);
		try {
			Function.regist(user);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}  