import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {

    private JTextField     txtUsername;
    private JPasswordField txtPassword;
    private JButton        btnLogin;
    private JLabel         lblTitle;
    private JLabel         lblUsername;
    private JLabel         lblPassword;
    private PlayerService  playerService;

    public LoginFrame() {
        playerService = new PlayerService();

        //diEDIT


        // Pengaturan JFrame
        setTitle("Tic-Tac-Toe - Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);           // muncul di tengah layar
        setResizable(false);

        // Layout utama
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Judul
        lblTitle = new JLabel("Tic-Tac-Toe Game", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        // Panel form
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        lblUsername = new JLabel("Username:");
        txtUsername = new JTextField();

        lblPassword = new JLabel("Password:");
        txtPassword = new JPasswordField();

        formPanel.add(lblUsername);
        formPanel.add(txtUsername);
        formPanel.add(lblPassword);
        formPanel.add(txtPassword);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Tombol login
        btnLogin = new JButton("Login");
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        mainPanel.add(btnLogin, BorderLayout.SOUTH);

        add(mainPanel);




        

        // Action Listener Tombol Login 
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //TODO

                String username = txtUsername.getText().trim();
                String password = new String(txtPassword.getPassword()).trim();

                // Validasi: field tidak boleh kosong
                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(
                        LoginFrame.this,
                        "Username dan password tidak boleh kosong!",
                        "Input Error",
                        JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                // Cek ke database
                Player player = playerService.login(username, password);

                if (player != null) {
                    // Login berhasil → buka MainMenuFrame
                    MainMenuFrame menuFrame = new MainMenuFrame(player);
                    menuFrame.setVisible(true);
                    LoginFrame.this.dispose(); // tutup LoginFrame
                } else {
                    // Login gagal → tampilkan error
                    JOptionPane.showMessageDialog(
                        LoginFrame.this,
                        "Username atau password salah!\nSilakan coba lagi.",
                        "Login Gagal",
                        JOptionPane.ERROR_MESSAGE
                    );
                    txtPassword.setText(""); // bersihkan field password
                }
            }
        });

        // Enter key juga bisa trigger login
        txtPassword.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnLogin.doClick();
            }
        });
    }
}
