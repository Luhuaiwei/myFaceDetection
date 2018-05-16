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
        //��ֹ����  
        response.setContentType("text/html;charset=utf-8");  
        request.setCharacterEncoding("utf-8");  
        response.setCharacterEncoding("utf-8");  
          
        String img       = request.getParameter("img");            //ͼ������  
        String username  = request.getParameter("username");       //�û���  
        String tag       = request.getParameter("tag");  
        String password  = request.getParameter("password");

        if(tag.equals("reg")){  
        	System.out.println("�����û�ע�����");
        	register(username, img, password);
        }else if(tag.endsWith("login")){  
        	System.out.println("�����û���¼����");
            loginNew(img, response, username);
        }       
    }  
    
    /**
     * �û���¼  
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
				out.print("�û�δע��");
			}else {
            //result = Face.faceMarchByBaidu(img,photo);  //�ٶ�������ʶ��ӿ�
            result = Face.faceMarchByOpenCV(img,photo);   //OpenCV����ʶ��
	            if (result > 80) {  
	                out.print("��½�ɹ�");    
	            }else{  
	                out.print("��½ʧ��");  
	            }  
			}
        } catch (IOException | ClassNotFoundException | SQLException e) {  
            e.printStackTrace();  
        }		
	}
	
	/**
	 * �û�ע��
	 * @param username
	 * @param img
	 * @param password
	 */
	private void register(String username, String img, String password) {
		Users user = new Users();		
		String fileName = System.currentTimeMillis()+".jpg";		
		Face.SaveImageByOpenCV(img, "D:\\picture\\database\\opencv_img\\"+fileName);
		Face.SaveImageByBaidu(img, "D:\\picture\\database\\baidu_img\\"+fileName);

		//д�����ݵ�ʵ������
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