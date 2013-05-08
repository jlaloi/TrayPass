package traypass.tools;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import traypass.syntax.Interpreter;

public class ToolJDBC {

	private static final Logger logger = LoggerFactory.getLogger(ToolJDBC.class);

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
			Interpreter.showError(e.getMessage());
			logger.error("Error", e);
		}
		return connection;
	}

	public void close() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (Exception e) {
			logger.error("Error", e);
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
			Interpreter.showError(e.getMessage());
			logger.error("Error", e);
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
				logger.error("Error", e);
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
			Interpreter.showError(e.getMessage());
			logger.error("Error", e);
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
				logger.error("Error", e);
			}
		}
	}

	public List<String> executeScript(String sqlStatement) {
		List<String> result = new ArrayList<String>();
		CallableStatement c = null;
		try {
			c = getConnection().prepareCall(sqlStatement);
			c.execute();
			ResultSet results = c.getResultSet();
			int nbColumn = results.getMetaData().getColumnCount();
			while (results.next()) {
				String row = results.getObject(1) + "";
				for (int i = 2; i <= nbColumn; i++) {
					row += colSeparator + results.getObject(i);
				}
				result.add(row);
			}
		} catch (Exception e) {
			Interpreter.showError(e.getMessage());
			logger.error("Error", e);
		} finally {
			try {
				if (c != null) {
					c.close();
				}
			} catch (Exception e) {
				logger.error("Error", e);
			}
		}
		return result;
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
