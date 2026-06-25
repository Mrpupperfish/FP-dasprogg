import java.util.Random;

public class GameLogic {

    private char[] board;   // 9 cell: index 0-8, isi ' ', 'X', atau 'O'
    private Random random;

    public GameLogic() {
        board  = new char[9];
        random = new Random();
        resetBoard();
    }

    // Kosongkan semua cell (dipakai saat mulai game baru)
    public void resetBoard() {
        for (int i = 0; i < board.length; i++) {
            board[i] = ' ';
        }
    }

    // =========================================================
    // MAKE MOVE
    // Coba isi cell di index tertentu dengan symbol ('X' atau 'O').
    // Return true kalau berhasil, false kalau cell sudah terisi.
    // =========================================================
    public boolean makeMove(int index, char symbol) {
        if (index < 0 || index >= 9) {
            return false; // index di luar jangkauan
        }
        if (board[index] != ' ') {
            return false; // cell sudah terisi, move tidak valid
        }
        board[index] = symbol;
        return true;
    }

    // =========================================================
    // CHECK WINNER
    // Cek apakah symbol tertentu membentuk 3 berjajar.
    // Ada 8 kombinasi: 3 baris, 3 kolom, 2 diagonal.
    // =========================================================
    public boolean checkWinner(char symbol) {
        int[][] patterns = {
            {0, 1, 2}, // baris atas
            {3, 4, 5}, // baris tengah
            {6, 7, 8}, // baris bawah
            {0, 3, 6}, // kolom kiri
            {1, 4, 7}, // kolom tengah
            {2, 5, 8}, // kolom kanan
            {0, 4, 8}, // diagonal kiri-atas ke kanan-bawah
            {2, 4, 6}  // diagonal kanan-atas ke kiri-bawah
        };

        for (int i = 0; i < patterns.length; i++) {
            int a = patterns[i][0];
            int b = patterns[i][1];
            int c = patterns[i][2];
            if (board[a] == symbol && board[b] == symbol && board[c] == symbol) {
                return true;
            }
        }
        return false;
    }

    // =========================================================
    // IS DRAW
    // Return true kalau semua cell sudah terisi (tidak ada ' ').
    // Dipanggil setelah checkWinner, jadi kalau ini true = seri.
    // =========================================================
    public boolean isDraw() {
        for (int i = 0; i < board.length; i++) {
            if (board[i] == ' ') {
                return false; // masih ada cell kosong, belum seri
            }
        }
        return true; // semua cell penuh
    }

    // =========================================================
    // COMPUTER MOVE
    // Pilih cell kosong secara random, return index cell-nya.
    // =========================================================
    public int computerMove() {
        int index;
        do {
            index = random.nextInt(9); // random 0-8
        } while (board[index] != ' '); // ulangi sampai dapat cell kosong
        return index;
    }

    public char[] getBoard() {
        return board;
    }
}
