package puzzle;



import java.util.ArrayList;
import java.util.List;

/**
 * Created by tuanpa on 11/19/16.
 */

public class Board {
    private int[][] board;
    private int n = 0;
    private int manhattanNumber, hammingNumber;
    private int rowBlack, colBlack;
    private String stringBoard;
    private static int[] NEIGHBORS_X = new int[]{0, 1, 0, -1};
    private static int[] NEIGHBORS_Y = new int[]{1, 0, -1, 0};

     public Board(int[][] blocks) {
        // construct a board from an n-by-n array of board
        n = blocks.length;
        manhattanNumber = 0;
        hammingNumber = 0;
        this.board = new int[n][n];
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                this.board[row][col] = blocks[row][col];
                if (this.board[row][col] == 0) {
                    rowBlack = row;
                    colBlack = col;
                }else{
                    int deltaX = Math.abs(row - (board[row][col] - 1) / n);
                    int deltaY = Math.abs(col - (board[row][col] - 1) % n);
                    manhattanNumber += deltaX + deltaY;
                    if (board[row][col] != row * n + col + 1)
                        hammingNumber++;
                }

            }
        }
    }

    public int dimension() {
        return n;
    }

    public int hamming() {
        return hammingNumber;
    }

    // sum of Manhattan distances between board and goal
    public int manhattan() {
        return manhattanNumber;
    }
    // is this board the goal board?
    public boolean isGoal() {

        return manhattan() == 0;
    }

    // a board that is obtained by exchanging any pair of board
    public Board twin() {
        int[][] twinBlock = cloneBoard(board);
        int row = (rowBlack + 1) % n;
        exchange(twinBlock, row, 0, row, 1);
        return new Board(twinBlock);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> listNeighbors = new ArrayList<Board>();
        int[][] boardTmp = cloneBoard(board);
        for (int i = 0; i <= 3; i++) {
            int neighborRow = rowBlack + NEIGHBORS_X[i];
            int neighborCol = colBlack + NEIGHBORS_Y[i];
            if (neighborRow >= 0 && neighborRow < n && neighborCol >= 0 && neighborCol < n) {
                exchange(boardTmp, rowBlack, colBlack, neighborRow, neighborCol);
                listNeighbors.add(new Board(boardTmp));
                exchange(boardTmp, rowBlack, colBlack, neighborRow, neighborCol);
            }
        }
        return listNeighbors;
    }

    private void exchange(int[][] blocks, int row1, int col1, int row2, int col2) {
        int tmp = blocks[row1][col1];
        blocks[row1][col1] = blocks[row2][col2];
        blocks[row2][col2] = tmp;
    }

    private int[][] cloneBoard(int[][] board) {
        int[][] cloned = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                cloned[i][j] = board[i][j];
        return cloned;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null || y.getClass() != this.getClass())
            return false;
        if (y == this)
            return true;
        return toString().equals(y.toString());
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        if (stringBoard != null)
            return stringBoard;

        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", board[i][j]));
            }
            s.append("\n");
        }
        stringBoard = s.toString();
        return stringBoard;
    }

    public static void main(String[] args) {

    }
}
