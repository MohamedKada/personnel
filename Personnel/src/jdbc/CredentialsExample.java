package jdbc;

public class Credentials
{
	private static String driver = "mysql";
	private static String driverClassName = "com.mysql.cj.jdbc.Driver";
	private static String host = ""
	private static String port = ""
	private static String database = ""
	private static String user = ""
	private static String password = ""
	
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