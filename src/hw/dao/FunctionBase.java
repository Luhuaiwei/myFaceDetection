package hw.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * 调用方法linking()<P/>
 * 返回连接对象Connection con1<P/>
 * 调用时要处理异常
 */
public class FunctionBase {
	public Connection linking() throws ClassNotFoundException, SQLException{
		long start = System.currentTimeMillis();
		Class.forName("com.mysql.jdbc.Driver");
		Connection con1 = DriverManager.getConnection
					("jdbc:mysql://localhost:3306/facedb?useSSL=false", "root", "admin");
		long end = System.currentTimeMillis();
		System.out.println("成功连接数据库");
		System.out.println("建立连接耗时："+(end - start)+"ms");
		return con1;
	}
}
