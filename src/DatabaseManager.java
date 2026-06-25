import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    // TODO: Sesuaikan tiga baris ini dengan konfigurasi database kamu
    private static final String URL      = "jdbc:mysql://localhost:3306/fpp";
    private static final String USER     = "root";
    private static final String PASSWORD = ""; // isi password MySQL kamu

    // Untuk PostgreSQL, ganti URL jadi:
    // jdbc:postgresql://localhost:5432/game_project

    // Untuk SQL Server, ganti URL jadi:
    // jdbc:sqlserver://localhost:1433;databaseName=game_project;encrypt=true;trustServerCertificate=true;

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
