import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdminDB {

	private String username, password;
	private Connection con = null;
	private Statement statement = null;
	private ResultSet rs;

	public AdminDB() {

	}

	public AdminDB(String username, String password) {

		this.username = username;
		this.password = password;

		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection("jdbc:sqlite:HealthcareDB.db");
			statement = con.createStatement();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

	public boolean isExists() {
		boolean exists = false;

		String sql = "SELECT * FROM Admin WHERE Username = '" + username + "' AND Password = '" + password + "'";
		try {
			rs = statement.executeQuery(sql);
			if (rs.next())
				exists = true;
			else
				exists = false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exists;
	}

	public void closeDB() {
		try {
			statement.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
