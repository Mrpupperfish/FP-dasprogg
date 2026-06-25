import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class TopScorersFrame extends JFrame {

    private JTable        table;
    private PlayerService playerService;

    public TopScorersFrame() {
        playerService = new PlayerService();

        // ── Pengaturan JFrame ──────────────────────────────
        setTitle("Top 5 Scorer");
        setSize(520, 230);
        setLocationRelativeTo(null);
        setResizable(false);

        // ── Layout ────────────────────────────────────────
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitle = new JLabel("Top 5 Scorer", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        // ── JTable ────────────────────────────────────────
        String[] columns = {"Rank", "Username", "Score", "Wins", "Losses", "Draws"};

        // isCellEditable return false → tabel read-only
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        // Ambil Top 5 dari database
        ArrayList<Player> topPlayers = playerService.getTopFiveScorers();

        // Masukkan ke model tabel
        for (int i = 0; i < topPlayers.size(); i++) {
            Player p = topPlayers.get(i);
            model.addRow(new Object[]{
                i + 1,           // nomor rank
                p.getUsername(),
                p.getScore(),
                p.getWins(),
                p.getLosses(),
                p.getDraws()
            });
        }

        table = new JTable(model);
        table.setRowHeight(24);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        // Tombol tutup
        JButton btnClose = new JButton("Tutup");
        btnClose.addActionListener(e -> TopScorersFrame.this.dispose());
        mainPanel.add(btnClose, BorderLayout.SOUTH);

        add(mainPanel);
    }
}
