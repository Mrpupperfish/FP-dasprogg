import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MainMenuFrame extends JFrame {

    private Player currentPlayer;
    private JButton btnStartGame;
    private JButton btnStatistics;
    private JButton btnTopScorers;
    private JButton btnExit;
    private JLabel  lblWelcome;

    public MainMenuFrame(Player player) {
        this.currentPlayer = player;

        //diEdit
        // ── Pengaturan JFrame ──────────────────────────────
        setTitle("Main Menu - " + player.getUsername());
        setSize(350, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // ── Layout ────────────────────────────────────────
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        lblWelcome = new JLabel("Selamat datang, " + player.getUsername() + "!", SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 14));
        mainPanel.add(lblWelcome, BorderLayout.NORTH);

        // Panel tombol menu
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10));

        btnStartGame   = new JButton("Mulai Game");
        btnStatistics  = new JButton("Statistik Saya");
        btnTopScorers  = new JButton("Top 5 Scorer");
        btnExit        = new JButton("Keluar");

        btnStartGame.setFont(new Font("Arial", Font.PLAIN, 13));
        btnStatistics.setFont(new Font("Arial", Font.PLAIN, 13));
        btnTopScorers.setFont(new Font("Arial", Font.PLAIN, 13));
        btnExit.setFont(new Font("Arial", Font.PLAIN, 13));

        buttonPanel.add(btnStartGame);
        buttonPanel.add(btnStatistics);
        buttonPanel.add(btnTopScorers);
        buttonPanel.add(btnExit);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        add(mainPanel);

        // ── Action Listeners ───────────────────────────────

        btnStartGame.addActionListener(e -> {
            GameFrame gameFrame = new GameFrame(currentPlayer);
            gameFrame.setVisible(true);
            MainMenuFrame.this.dispose();
        });

        btnStatistics.addActionListener(e -> {
            StatisticsFrame statsFrame = new StatisticsFrame(currentPlayer);
            statsFrame.setVisible(true);
            // tidak dispose, biar user bisa balik ke menu
        });

        btnTopScorers.addActionListener(e -> {
            TopScorersFrame topFrame = new TopScorersFrame();
            topFrame.setVisible(true);
        });

        btnExit.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                MainMenuFrame.this,
                "Yakin mau keluar?",
                "Konfirmasi Keluar",
                JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }
}
