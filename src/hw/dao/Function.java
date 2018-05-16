package hw.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import hw.model.Users;

public class Function {
	
	/**
	 * 注册时调用,用于向数据库中插入数据
	 * @param user
	 * @return 注册成功返回true，注册失败返回false
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static boolean regist(Users user) throws ClassNotFoundException, SQLException{
		FunctionBase mbs = new FunctionBase();
		Connection con1 = mbs.linking();
		System.out.println("判断用户是否已注册");
		boolean flag = isExist(user.getUsername(),con1);//用户已存在返回true
		if(flag) {
			con1.close();
			return false;
		}	
		System.out.println("开始在数据库中插入数据");
		String sql = "insert into user(username,password,id,headphoto)values(?,?,?,?)";
		PreparedStatement ps = con1.prepareCall(sql);
		ps.setString(1,user.getUsername());
		ps.setString(2,user.getPassword());
		ps.setString(3,user.getId().toString());
		ps.setString(4,user.getHeadphoto());
		ps.execute();
		System.out.println("注册成功");
		ps.close();
		con1.close();
		return true;
	}

	/**
	 * 根据用户名获取用户照片名
	 * @param username
	 * @return 返回用户照片名，若获取失败，则返回字符串null
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static String getPhoto(String username) throws ClassNotFoundException, SQLException {
		FunctionBase mbs = new FunctionBase();
		Connection con1 = mbs.linking();
		
		System.out.println("开始获取用户照片名");
		String sql = "select headphoto from user where username =?";
		PreparedStatement ps = con1.prepareCall(sql);
		ps.setString(1,username);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			System.out.println("数据库中获取的照片名："+rs.getString(1));
			
		//	ps.close();
		//	con1.close();
			return rs.getString(1);
		}
		System.out.println("获取用户照片失败");
		
		ps.close();
		con1.close();
		return "null";
	}
	
	/**
	 * 根据用户名获取用户密码
	 * @param username
	 * @return 返回用户密码，若获取失败，则返回字符串null
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static String getPassword(String username) throws ClassNotFoundException, SQLException {
		FunctionBase mbs = new FunctionBase();
		Connection con1 = mbs.linking();
		
		System.out.println("开始获取用户密码");
		String sql = "select headphoto from user where username =?";
		PreparedStatement ps = con1.prepareCall(sql);
		ps.setString(1,username);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			System.out.println("数据库中获取的密码："+rs.getString(1));
			
			ps.close();
			con1.close();
			return rs.getString(1);
		}
		System.out.println("获取用户密码失败");
		
		ps.close();
		con1.close();
		return "null";
	}
	
	/**
	 * 判断用户名在数据库是否存在
	 * @param username
	 * @param con1
	 * @return 若用户名存在返回true，否则返回false
	 * @throws SQLException
	 */
	private static boolean isExist(String username, Connection con1) throws SQLException {
		String sql = "select username from user where username =?";
		PreparedStatement ps = con1.prepareCall(sql);
		ps.setString(1,username);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			System.out.println("用户名已存在："+rs.getString(1));
			ps.close();
			return true;
		}
		System.out.println("确认用户不存在");
		
		ps.close();
		return false;
	}
}
