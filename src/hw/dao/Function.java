package hw.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import hw.model.Users;

public class Function {
	
	/**
	 * ע��ʱ����,���������ݿ��в�������
	 * @param user
	 * @return ע��ɹ�����true��ע��ʧ�ܷ���false
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static boolean regist(Users user) throws ClassNotFoundException, SQLException{
		FunctionBase mbs = new FunctionBase();
		Connection con1 = mbs.linking();
		System.out.println("�ж��û��Ƿ���ע��");
		boolean flag = isExist(user.getUsername(),con1);//�û��Ѵ��ڷ���true
		if(flag) {
			con1.close();
			return false;
		}	
		System.out.println("��ʼ�����ݿ��в�������");
		String sql = "insert into user(username,password,id,headphoto)values(?,?,?,?)";
		PreparedStatement ps = con1.prepareCall(sql);
		ps.setString(1,user.getUsername());
		ps.setString(2,user.getPassword());
		ps.setString(3,user.getId().toString());
		ps.setString(4,user.getHeadphoto());
		ps.execute();
		System.out.println("ע��ɹ�");
		ps.close();
		con1.close();
		return true;
	}

	/**
	 * �����û�����ȡ�û���Ƭ��
	 * @param username
	 * @return �����û���Ƭ��������ȡʧ�ܣ��򷵻��ַ���null
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static String getPhoto(String username) throws ClassNotFoundException, SQLException {
		FunctionBase mbs = new FunctionBase();
		Connection con1 = mbs.linking();
		
		System.out.println("��ʼ��ȡ�û���Ƭ��");
		String sql = "select headphoto from user where username =?";
		PreparedStatement ps = con1.prepareCall(sql);
		ps.setString(1,username);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			System.out.println("���ݿ��л�ȡ����Ƭ����"+rs.getString(1));
			
		//	ps.close();
		//	con1.close();
			return rs.getString(1);
		}
		System.out.println("��ȡ�û���Ƭʧ��");
		
		ps.close();
		con1.close();
		return "null";
	}
	
	/**
	 * �����û�����ȡ�û�����
	 * @param username
	 * @return �����û����룬����ȡʧ�ܣ��򷵻��ַ���null
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static String getPassword(String username) throws ClassNotFoundException, SQLException {
		FunctionBase mbs = new FunctionBase();
		Connection con1 = mbs.linking();
		
		System.out.println("��ʼ��ȡ�û�����");
		String sql = "select headphoto from user where username =?";
		PreparedStatement ps = con1.prepareCall(sql);
		ps.setString(1,username);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			System.out.println("���ݿ��л�ȡ�����룺"+rs.getString(1));
			
			ps.close();
			con1.close();
			return rs.getString(1);
		}
		System.out.println("��ȡ�û�����ʧ��");
		
		ps.close();
		con1.close();
		return "null";
	}
	
	/**
	 * �ж��û��������ݿ��Ƿ����
	 * @param username
	 * @param con1
	 * @return ���û������ڷ���true�����򷵻�false
	 * @throws SQLException
	 */
	private static boolean isExist(String username, Connection con1) throws SQLException {
		String sql = "select username from user where username =?";
		PreparedStatement ps = con1.prepareCall(sql);
		ps.setString(1,username);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			System.out.println("�û����Ѵ��ڣ�"+rs.getString(1));
			ps.close();
			return true;
		}
		System.out.println("ȷ���û�������");
		
		ps.close();
		return false;
	}
}
