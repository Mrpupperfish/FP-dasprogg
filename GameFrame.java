import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    private Player        currentPlayer;
    private PlayerService playerService;
    private GameLogic     gameLogic;

    private JButton[] buttons;   // 9 tombol papan, index 0-8
    private JLabel    lblStatus; // info giliran / hasil game
    private JButton   btnBack;   // kembali ke menu tanpa selesai game

    public GameFrame(Player player) {
        this.currentPlayer = player;
        this.playerService = new PlayerService();
        this.gameLogic     = new GameLogic();

        // ── Pengaturan JFrame ──────────────────────────────
        setTitle("Tic-Tac-Toe - " + player.getUsername() + " (X) vs Komputer (O)");
        setSize(420, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // ── Layout utama ───────────────────────────────────
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Label status di atas papan
        lblStatus = new JLabel("Giliran kamu! Klik cell mana saja.", SwingConstants.CENTER);
        lblStatus.setFont(new Font("Arial", Font.PLAIN, 13));
        mainPanel.add(lblStatus, BorderLayout.NORTH);

        // Panel papan 3x3
        JPanel boardPanel = new JPanel(new GridLayout(3, 3, 5, 5));
        buttons = new JButton[9];

        for (int i = 0; i < 9; i++) {
            final int index = i; // harus final untuk dipakai di lambda
            buttons[i] = new JButton("");
            buttons[i].setFont(new Font("Arial", Font.BOLD, 48));
            buttons[i].setFocusPainted(false);
            boardPanel.add(buttons[i]);

            // Action listener tiap tombol papan
            buttons[i].addActionListener(e -> handlePlayerMove(index));
        }

        mainPanel.add(boardPanel, BorderLayout.CENTER);

        // Tombol kembali ke menu
        btnBack = new JButton("Kembali ke Menu");
        btnBack.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                GameFrame.this,
                "Game akan dibatalkan. Tetap kembali ke menu?",
                "Konfirmasi",
                JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                MainMenuFrame menuFrame = new MainMenuFrame(currentPlayer);
                menuFrame.setVisible(true);
                GameFrame.this.dispose();
            }
        });

        mainPanel.add(btnBack, BorderLayout.SOUTH);
        add(mainPanel);
    }

    // =========================================================
    // HANDLE PLAYER MOVE
    // Dipanggil tiap kali pemain klik tombol papan.
    // =========================================================
    private void handlePlayerMove(int index) {

        // 1. Coba buat move pemain
        boolean moved = gameLogic.makeMove(index, 'X');
        if (!moved) return; // cell sudah terisi, abaikan klik

        // 2. Update tampilan tombol
        buttons[index].setText("X");
        buttons[index].setForeground(Color.BLUE);
        buttons[index].setEnabled(false);

        // 3. Cek apakah pemain menang
        if (gameLogic.checkWinner('X')) {
            finishGame("WIN");
            return;
        }

        // 4. Cek seri setelah move pemain
        if (gameLogic.isDraw()) {
            finishGame("DRAW");
            return;
        }

        // 5. Update status label
        lblStatus.setText("Komputer sedang berpikir...");

        // 6. Move komputer
        int compIndex = gameLogic.computerMove();
        buttons[compIndex].setText("O");
        buttons[compIndex].setForeground(Color.RED);
        buttons[compIndex].setEnabled(false);

        // 7. Cek apakah komputer menang
        if (gameLogic.checkWinner('O')) {
            finishGame("LOSE");
            return;
        }

        // 8. Cek seri setelah move komputer
        if (gameLogic.isDraw()) {
            finishGame("DRAW");
            return;
        }

        // 9. Lanjut main
        lblStatus.setText("Giliran kamu! Klik cell mana saja.");
    }

    // =========================================================
    // FINISH GAME
    // Dipanggil saat game selesai (WIN / LOSE / DRAW).
    // Update DB, tampilkan pesan, kembali ke menu.
    // =========================================================
    private void finishGame(String result) {

        // Disable semua tombol agar tidak bisa diklik lagi
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setEnabled(false);
        }
        btnBack.setEnabled(false);

        // Update statistik ke database
        playerService.updateStatistics(currentPlayer, result);

        // Tentukan pesan dan update label status
        String message;
        if (result.equals("WIN")) {
            message = "Selamat, kamu MENANG! +10 poin";
            lblStatus.setText("Kamu menang!");
        } else if (result.equals("DRAW")) {
            message = "SERI! Pertandingan berimbang. +3 poin";
            lblStatus.setText("Seri!");
        } else {
            message = "Kamu KALAH. Semangat lagi! +0 poin";
            lblStatus.setText("Kamu kalah.");
        }

        // Tampilkan hasil dan tanya mau main lagi atau tidak
        int choice = JOptionPane.showConfirmDialog(
            GameFrame.this,
            message + "\n\nMau main lagi?",
            "Game Selesai",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.INFORMATION_MESSAGE
        );

        if (choice == JOptionPane.YES_OPTION) {
            // Reset game tanpa tutup window
            gameLogic.resetBoard();
            for (int i = 0; i < buttons.length; i++) {
                buttons[i].setText("");
                buttons[i].setEnabled(true);
                buttons[i].setForeground(Color.BLACK);
            }
            btnBack.setEnabled(true);
            lblStatus.setText("Giliran kamu! Klik cell mana saja.");
        } else {
            // Kembali ke Main Menu
            MainMenuFrame menuFrame = new MainMenuFrame(currentPlayer);
            menuFrame.setVisible(true);
            GameFrame.this.dispose();
        }
    }
}
