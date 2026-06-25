import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class StatisticsFrame extends JFrame {

    public StatisticsFrame(Player player) {

        // Refresh data dari database supaya selalu up-to-date
        PlayerService ps    = new PlayerService();
        Player fresh        = ps.getPlayerById(player.getId());
        if (fresh != null) {
            player = fresh; // pakai data terbaru
        }

        // ── Pengaturan JFrame ──────────────────────────────
        setTitle("Statistik - " + player.getUsername());
        setSize(320, 280);
        setLocationRelativeTo(null);
        setResizable(false);

        // ── Layout ────────────────────────────────────────
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel lblTitle = new JLabel("Statistik Saya", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        // Panel data statistik
        JPanel dataPanel = new JPanel(new GridLayout(5, 2, 10, 8));

        dataPanel.add(new JLabel("Username :"));
        dataPanel.add(new JLabel(player.getUsername()));

        dataPanel.add(new JLabel("Menang   :"));
        dataPanel.add(new JLabel(String.valueOf(player.getWins())));

        dataPanel.add(new JLabel("Kalah    :"));
        dataPanel.add(new JLabel(String.valueOf(player.getLosses())));

        dataPanel.add(new JLabel("Seri     :"));
        dataPanel.add(new JLabel(String.valueOf(player.getDraws())));

        dataPanel.add(new JLabel("Total Score :"));
        JLabel lblScore = new JLabel(String.valueOf(player.getScore()));
        lblScore.setFont(new Font("Arial", Font.BOLD, 14));
        lblScore.setForeground(Color.BLUE);
        dataPanel.add(lblScore);

        mainPanel.add(dataPanel, BorderLayout.CENTER);

        // Tombol tutup
        JButton btnClose = new JButton("Tutup");
        btnClose.addActionListener(e -> StatisticsFrame.this.dispose());
        mainPanel.add(btnClose, BorderLayout.SOUTH);

        add(mainPanel);
    }
}
