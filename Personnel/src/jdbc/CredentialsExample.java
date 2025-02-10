package jdbc;

public class Credentials
{
	private static String driver = "mysql";
	private static String driverClassName = "com.mysql.cj.jdbc.Driver";
	private static String host = "172.16.0.52";
	private static String port = "3306";
	private static String database = "mkada";
	private static String user = "mkada";
	private static String password = "azerty";
	
	static String getUrl() 
	{
		return "jdbc:" + driver + "://" + host + ":" + port + "/" + database ;
	}
	
	static String getDriverClassName()
	{
		return driverClassName;
	}
	
	static String getUser() 
	{
		return user;
	}

	static String getPassword() 
	{
		return password;
	}
}