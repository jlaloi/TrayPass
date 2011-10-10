package traypass.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ToolJDBC {

	private String driver;

	private String url;

	private String login;

	private String password;

	private String colSeparator = " ";

	private Connection connection = null;

	public ToolJDBC(String driver, String url, String login, String password) {
		super();
		this.driver = driver;
		this.url = url;
		this.login = login;
		this.password = password;
	}

	public Connection connect() {
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, login, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

	public void close() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<String> executeQuery(String query) {
		List<String> result = new ArrayList<String>();
		Statement stmt = null;
		try {
			stmt = connection.createStatement();
			ResultSet results = stmt.executeQuery(query);
			int nbColumn = results.getMetaData().getColumnCount();
			while (results.next()) {
				String row = results.getObject(1) + "";
				for (int i = 2; i <= nbColumn; i++) {
					row += colSeparator + results.getObject(i);
				}
				result.add(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public void executeUpdate(String query) {
		Statement stmt = null;
		try {
			stmt = connection.createStatement();
			stmt.executeUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public Connection getConnection() {
		return connection;
	}

	public String getColSeparator() {
		return colSeparator;
	}

	public void setColSeparator(String colSeparator) {
		this.colSeparator = colSeparator;
	}

	public String getDriver() {
		return driver;
	}

	public String getLogin() {
		return login;
	}

	public String getUrl() {
		return url;
	}

}