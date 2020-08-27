import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation
{
    private boolean[][] map;
    private int topIndex = 0;
    private int bottomIndex;
    private int size;
    private WeightedQuickUnionUF qf;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("n must be greater than 0");
        size = n;
        bottomIndex = size * size + 1;
        qf = new WeightedQuickUnionUF(size * size + 2);
        map = new boolean[size][size];
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        map[row - 1][col - 1] = true;
        if (row == 1) {
            qf.union(getQFIndex(row, col), topIndex);
        }
        if (row == size) {
            qf.union(getQFIndex(row, col), bottomIndex);
        }

        if (col > 1 && isOpen(row, col - 1)) {
            qf.union(getQFIndex(row, col), getQFIndex(row, col - 1));
        }
        if (col < size && isOpen(row, col + 1)) {
            qf.union(getQFIndex(row, col), getQFIndex(row, col + 1));
        }
        if (row > 1 && isOpen(row - 1, col)) {
            qf.union(getQFIndex(row, col), getQFIndex(row - 1, col));
        }
        if (row < size && isOpen(row + 1, col)) {
            qf.union(getQFIndex(row, col), getQFIndex(row + 1, col));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return map[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (0 < row && row <= size && 0 < col && col <= size) {
            return qf.connected(topIndex, getQFIndex(row , col));
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    // does the system percolate?
    public boolean percolates() {
        return qf.connected(topIndex, bottomIndex);
    }

    // returns the number of open sites
    private int getQFIndex(int i, int j) {
        return size * (i - 1) + j;
    }
}