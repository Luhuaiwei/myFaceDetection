package hw.service;  

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
        String password  = "123456";

        if(tag.equals("reg")){  
            //注册  
        	register(username, img, password);
        }else if(tag.endsWith("login")){  
            //登陆  
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
        Face facedet = new Face();
		FileInputStream fis = null;
		BufferedReader bur = null;
		StringBuffer sb = null;
		PrintWriter out = null;
		Double result = 50.00;
        try {  
            out = response.getWriter();  
            String photo = Function.getPhoto(username);
		
			fis = new FileInputStream("D:\\picture\\"+photo);
			bur = new BufferedReader(new InputStreamReader(fis));
			sb = new StringBuffer();
			
			String temp = null;
			while((temp=bur.readLine())!=null) {
				sb.append(temp);
			}
			
			if(photo.equals("null")) {
				System.out.println("GG");
			}else {
            result = facedet.faceMarchByBaidu(img,sb.toString());  //导入人脸识别算法
            //result = facedet.faceMarchByOpenCV(img,sb.toString());
			}
			
            if (result > 80) {  
                out.print("登陆成功");    
            }else{  
                out.print("登陆失败");  
            }  
        } catch (IOException | ClassNotFoundException | SQLException e) {  
            e.printStackTrace();  
        }finally {
        	try {
				bur.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
		String fileName = System.currentTimeMillis()+".png";
		ByteArrayInputStream bi = null;
		FileOutputStream out = null;
		
		//保存图片到本地
		try {
			bi = new ByteArrayInputStream(img.getBytes());
			out = new FileOutputStream("D:\\picture\\"+fileName);  
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