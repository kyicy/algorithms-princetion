
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    final private WeightedQuickUnionUF weightedQuickUnionUF, auxGrid;
    final private int n;
    private boolean[] state; // true for open, false for block
    private int opendnum = 0;
    final private int total;

    // create n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        total = n * n + 2;

        state = new boolean[total];
        for (int i = 0; i < state.length; i++) {
            state[i] = false;
        }
        state[0] = true;
        state[state.length - 1] = true;

        weightedQuickUnionUF = new WeightedQuickUnionUF(total);
        auxGrid = new WeightedQuickUnionUF(total - 1);
    }

    private int toIndex(int row, int col) {
        if (row == 0)
            return 0;
        if (row == this.n + 1)
            return total - 1;
        return (row - 1) * this.n + col;
    }

    private boolean checkRange(int row_or_col) {
        return row_or_col > 0 && row_or_col <= this.n;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!checkRange(row) || !checkRange(col)) {
            throw new IllegalArgumentException();
        }

        if (isOpen(row, col)) {
            return;
        }

        int index = toIndex(row, col);
        opendnum += 1;
        state[index] = true;

        // upper
        int upperIndex = toIndex(row - 1, col);
        if (state[upperIndex]) {
            weightedQuickUnionUF.union(upperIndex, index);
            auxGrid.union(upperIndex, index);
        }

        // left
        if (col > 1 && state[index - 1]) {
            weightedQuickUnionUF.union(index, index - 1);
            auxGrid.union(index, index - 1);
        }

        // right
        if (col < n && state[index + 1]) {
            weightedQuickUnionUF.union(index, index + 1);
            auxGrid.union(index, index + 1);
        }

        // down
        int downIndex = toIndex(row + 1, col);
        if (state[downIndex]) {
            weightedQuickUnionUF.union(index, downIndex);
            if (row + 1 <= this.n)
                auxGrid.union(index, downIndex);
        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!checkRange(row) || !checkRange(col)) {
            throw new IllegalArgumentException();
        }
        return state[toIndex(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!checkRange(row) || !checkRange(col)) {
            throw new IllegalArgumentException();
        }
        int index = toIndex(row, col);
        return weightedQuickUnionUF.connected(0, index) && auxGrid.connected(0, index);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return opendnum;
    }

    // does the system percolate?
    public boolean percolates() {
        return weightedQuickUnionUF.connected(0, total - 1);
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation p = new Percolation(3);
        p.open(1, 1);
        p.open(2, 1);
        p.open(3, 1);
        System.out.println(p.numberOfOpenSites());
        System.out.println(p.isFull(1, 1));
        System.out.println(p.percolates());
    }
}
