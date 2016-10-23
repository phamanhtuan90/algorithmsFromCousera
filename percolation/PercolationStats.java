
/**
 * Created by tuanpa on 10/22/16.
 */


import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;


public class PercolationStats {
    private double[] fractions;
    private Percolation percolation;
    private int trialsNumber;

    /*
     perform trials independent experiments on an n-by-n grid
      */
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n <= 0 || trials <= 0");
        }
        trialsNumber = trials;
        fractions = new double[trials];
        for (int num = 0; num < trials; num++) {
            percolation = new Percolation(n);
            int openedSites = 0;
            while (!percolation.percolates()) {
                int i = StdRandom.uniform(1, n + 1);
                int j = StdRandom.uniform(1, n + 1);
                if (!percolation.isOpen(i, j)) {
                    percolation.open(i, j);
                    openedSites++;
                }
            }
            fractions[num] = (double) openedSites / (n * n);
        }
    }

    /*
     sample mean of percolation threshold
      */
    public double mean() {
        return StdStats.mean(fractions);
    }

    /*
     perform trials independent experiments on an n-by-n grid
      */
    public double stddev() {
        return StdStats.stddev(fractions);
    }

    /*
    low  endpoint of 95% confidence interval
     */
    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(trialsNumber));
    }

    /*
    high endpoint of 95% confidence interval
     */
    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(trialsNumber));
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(n, t);

        String confidence = ps.confidenceLo() + ", " + ps.confidenceHi();
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = " + confidence);
    }
}
