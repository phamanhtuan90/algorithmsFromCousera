
/**
 * Created by tuanpa on 10/22/16.
 */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF weightedQuickUnionUF;
    private int numberInput;
    /*
       list node open
     */
    private boolean[][] openInfo;
    /*
      list node full (color BOOK_LIGHT_BLUE)
    */
    private int top;
    private int bottom;
    private  boolean isPercolates;

    /*
      create n-by-n grid, with all sites blocked
    */
    public Percolation(int n) {
        if (n <= 0) {
            throw new java.lang.IllegalArgumentException("Error");
        }
        numberInput = n;
        weightedQuickUnionUF = new WeightedQuickUnionUF(n * n + 2);
        openInfo = new boolean[n][n];
        top = 0;
        bottom = n * n + 1;
    }

    /*
      open site (row, col) if it is not open already
    */
    public void open(int row, int col) {
        if (!validate(row, col)) {
            throw new java.lang.IllegalArgumentException("Error");
        }
        int nodeNumber = getNodeNumber(row, col);
        openInfo[row - 1][col - 1] = true;
        if (row == 1) {
            weightedQuickUnionUF.union(nodeNumber, top);
        }

        if (validate(row + 1, col) && this.isOpen(row + 1, col)) {
            weightedQuickUnionUF.union(nodeNumber, nodeNumber + numberInput);

        }
        if (validate(row - 1, col) && this.isOpen(row - 1, col)) {
            weightedQuickUnionUF.union(nodeNumber, nodeNumber - numberInput);
        }
        if (validate(row, col + 1) && this.isOpen(row, col + 1)) {
            weightedQuickUnionUF.union(nodeNumber, nodeNumber + 1);
        }
        if (validate(row, col - 1) && this.isOpen(row, col - 1)) {
            weightedQuickUnionUF.union(nodeNumber, nodeNumber - 1);
        }
        if(weightedQuickUnionUF.connected(nodeNumber, top)){
            unionButtom();
        }

    }

    private void unionButtom() {
        for (int col = 1; col <= numberInput; col++) {
            if (!this.isOpen(numberInput, col)) {
                continue;
            }
            if (weightedQuickUnionUF.connected(getNodeNumber(numberInput, col), top)) {
                weightedQuickUnionUF.union(getNodeNumber(numberInput, col), bottom);
                break;
            }
        }
    }

    private boolean validate(int row, int col) {
        if (row <= 0 || row > numberInput || col <= 0 || col > numberInput) {
            return false;
        }
        return true;
    }

    /*
      is site (row, col) open?
    */
    public boolean isOpen(int row, int col) {
        if (!validate(row, col)) {
            throw new  java.lang.IllegalArgumentException("Error");
        }
        return openInfo[row - 1][col - 1];
    }

    /*
      is site (row, col) full?
    */
    public boolean isFull(int row, int col) {
        if (!validate(row, col)) {
            throw new java.lang.IllegalArgumentException("Error");
        }
        if (!this.isOpen(row, col)) {
            return false;
        }
        return weightedQuickUnionUF.connected(getNodeNumber(row, col), top);
    }

    /*
      does the system percolate?
    */
    public boolean percolates() {
        if(isPercolates) return isPercolates;
        isPercolates = weightedQuickUnionUF.connected(top, bottom);
        return isPercolates;
    }

    /*
      get node number from row, col
    */
    private int getNodeNumber(int row, int col) {
        return (row - 1) * numberInput + col;

    }

    public static void main(String[] args) {

    }
}

