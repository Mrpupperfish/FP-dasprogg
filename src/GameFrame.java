import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

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


    //TODO
   
    private void handlePlayerMove(int index) {

        boolean moved = gameLogic.makeMove(index, 'X');
        if (!moved) return;

        buttons[index].setText("X");
        buttons[index].setForeground(Color.BLUE);
        buttons[index].setEnabled(false);

        if (gameLogic.checkWinner('X')) { finishGame("WIN");  return; }
        if (gameLogic.isDraw())         { finishGame("DRAW"); return; }

        // Komputer move
        int compIndex = gameLogic.computerMove();
        
        // Tambahan: kalau return -1 berarti tidak ada cell kosong
        if (compIndex == -1) {
            finishGame("DRAW");
            return;
        }

        buttons[compIndex].setText("O");
        buttons[compIndex].setForeground(Color.RED);
        buttons[compIndex].setEnabled(false);

        if (gameLogic.checkWinner('O')) { finishGame("LOSE"); return; }
        if (gameLogic.isDraw())         { finishGame("DRAW"); }
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
