package cadSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class DataBase {

	final static String DRIVER = "com.mysql.cj.jdbc.Driver";
	final static String URL_DATABASE = "jdbc:mysql://localhost:3306/cad-system";
	public static Connection connection;
	public static Statement statement;
	public static ResultSet resultSet;

	public static boolean getConnection() {
		try {
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(URL_DATABASE, "root", "");
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			return true;
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Driver não encontrado", "Status da conexão",
					JOptionPane.WARNING_MESSAGE);
			return false;

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Problemas na conexão com a fonte de dados", "Status da conexão",
					JOptionPane.WARNING_MESSAGE);
			return false;

		}
	}

	/*
	 * This method run sql statement for INSER, DELETE and UPDATE
	 * 
	 * @param sql to be executed
	 * 
	 * @return rows affected
	 */
	public static int runSQL(String sql) {
		int count = 0;
		try {
			count = statement.executeUpdate(sql);
			System.out.println(count);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	/*
	 * @param sql width select command to populate the resultSet property
	 */
	public static void setResultSet(String sql) {
		try {
			resultSet = statement.executeQuery(sql);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void closeAll() {
		closeResultSet();
		closeStatement();
		closeConnection();
	}

	private static void closeConnection() {
		try {
			if (connection != null)
				connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void closeStatement() {
		try {
			if (statement != null)
				statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void closeResultSet() {
		try {
			if (resultSet != null)
				resultSet.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
