package hw.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * ���÷���linking()<P/>
 * �������Ӷ���Connection con1<P/>
 * ����ʱҪ�����쳣
 */
public class FunctionBase {
	public Connection linking() throws ClassNotFoundException, SQLException{
		long start = System.currentTimeMillis();
		Class.forName("com.mysql.jdbc.Driver");
		Connection con1 = DriverManager.getConnection
					("jdbc:mysql://localhost:3306/facedb?useSSL=false", "root", "admin");
		long end = System.currentTimeMillis();
		System.out.println("�ɹ��������ݿ�");
		System.out.println("�������Ӻ�ʱ��"+(end - start)+"ms");
		return con1;
	}
}
