import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class PlayerService {

    // LOGIN
    // Cek username dan password ke database.
    // Return: objek Player kalau cocok, null kalau gagal.

    public Player login(String username, String password) {
        String sql = "SELECT * FROM players WHERE username = ? AND password = ?";
        try {
            Connection conn        = DatabaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Player(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getInt("wins"),
                    rs.getInt("losses"),
                    rs.getInt("draws"),
                    rs.getInt("score")
                );
            }
        } catch (Exception e) {
            System.out.println("Login error: " + e.getMessage());
        }
        return null;
    }

    // UPDATE STATISTIK
    // Dipanggil setelah game selesai.
    // result harus "WIN", "LOSE", atau "DRAW"

    
    public void updateStatistics(Player player, String result) {
        int additionalScore = 0;
        String sql = "";

        if (result.equalsIgnoreCase("WIN")) {
            additionalScore = 10;
            sql = "UPDATE players SET wins = wins + 1, score = score + ? WHERE id = ?";
        } else if (result.equalsIgnoreCase("LOSE")) {
            additionalScore = 0;
            sql = "UPDATE players SET losses = losses + 1, score = score + ? WHERE id = ?";
        } else if (result.equalsIgnoreCase("DRAW")) {
            additionalScore = 3;
            sql = "UPDATE players SET draws = draws + 1, score = score + ? WHERE id = ?";
        } else {
            return; // result tidak dikenal, tidak update
        }

        try {
            Connection conn        = DatabaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, additionalScore);
            stmt.setInt(2, player.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Update statistics error: " + e.getMessage());
        }
    }

    // =========================================================
    // TOP 5 SCORERS
    // Ambil 5 pemain teratas dari database.
    // Urutan: score tertinggi dulu, kalau sama → wins lebih banyak duluan.
    // =========================================================
    public ArrayList<Player> getTopFiveScorers() {
        ArrayList<Player> list = new ArrayList<Player>();

        // Kalau pakai SQL Server, ganti query ini jadi:
        // SELECT TOP 5 * FROM players ORDER BY score DESC, wins DESC
        String sql = "SELECT * FROM players ORDER BY score DESC, wins DESC LIMIT 5";

        try {
            Connection conn = DatabaseManager.getConnection();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            while (rs.next()) {
                list.add(new Player(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getInt("wins"),
                    rs.getInt("losses"),
                    rs.getInt("draws"),
                    rs.getInt("score")
                ));
            }
        } catch (Exception e) {
            System.out.println("Top 5 error: " + e.getMessage());
        }
        return list;
    }

    // =========================================================
    // GET PLAYER BY ID
    // Dipakai untuk refresh data player dari DB
    // (contoh: sebelum tampilkan StatisticsFrame)
    // =========================================================
    public Player getPlayerById(int id) {
        String sql = "SELECT * FROM players WHERE id = ?";
        try {
            Connection conn        = DatabaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Player(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getInt("wins"),
                    rs.getInt("losses"),
                    rs.getInt("draws"),
                    rs.getInt("score")
                );
            }
        } catch (Exception e) {
            System.out.println("Get player error: " + e.getMessage());
        }
        return null;
    }
}
