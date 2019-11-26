import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    final private double[] thresholds;

    private double _mean = -1;
    private double _stddev = -1;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        thresholds = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                p.open(StdRandom.uniform(n) + 1, StdRandom.uniform(n) + 1);
            }
            thresholds[i] = ((double) p.numberOfOpenSites()) / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        if (_mean < 0) {
            _mean = StdStats.mean(thresholds);
        }
        return _mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (_stddev < 0) {
            _stddev = StdStats.stddev(thresholds);
        }
        return _stddev;
    }

    private double halfInterval() {
        return 1.96 * stddev() / Math.sqrt(thresholds.length);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - halfInterval();
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + halfInterval();
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats pls = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));

        StdOut.printf("mean                     = %f\n", pls.mean());
        StdOut.printf("stddev                   = %f\n", pls.stddev());
        StdOut.printf("95%% confidence Interval  = [%f, %f]\n", pls.confidenceLo(), pls.confidenceHi());

    }

}