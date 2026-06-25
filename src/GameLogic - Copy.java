import java.util.ArrayList;
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
            {0, 1, 2}, 
            {3, 4, 5}, 
            {6, 7, 8}, 
            {0, 3, 6}, 
            {1, 4, 7}, 
            {2, 5, 8}, 
            {0, 4, 8}, 
            {2, 4, 6}  
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

    
    public boolean isDraw() {
        for (int i = 0; i < board.length; i++) {
            if (board[i] == ' ') {
                return false; // masih ada cell kosong, belum seri
            }
        }
        return true; // semua cell penuh
    }

    //TODO
    public int computerMove() {
        // Kumpulkan semua index yang masih kosong
        ArrayList<Integer> emptyCells = new ArrayList<Integer>();
        for (int i = 0; i < board.length; i++) {
            if (board[i] == ' ') {
                emptyCells.add(i);
            }
        }
 
        // Tidak ada cell kosong
        if (emptyCells.isEmpty()) {
            return -1;
        }
 
        // Pilih random dari cell yang kosong saja
        int randomPick = random.nextInt(emptyCells.size());
        int chosenIndex = emptyCells.get(randomPick);
 
        // Catat move komputer ke board
        board[chosenIndex] = 'O';
 
        return chosenIndex;
    }

    public char[] getBoard() {
        return board;
    }
}
