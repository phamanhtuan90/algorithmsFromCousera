

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.ArrayList;

/*
 * Created by tuanpa on 11/5/16.
 */
public class BruteCollinearPoints {
    private ArrayList<LineSegment> segments = new ArrayList<LineSegment>();

    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new java.lang.NullPointerException("Null Pointer");
        int pointLength = points.length;
        Arrays.sort(points);
        for (int first = 0; first < pointLength; first++) {
            Point pointFirst = points[first];
            for (int second = first + 1; second < pointLength; second++) {
                Point pointSecond = points[second];
                if (pointFirst.compareTo(pointSecond) == 1) {
                    continue;
                }
                for (int third = second + 1; third < pointLength; third++) {
                    Point pointThird = points[third];
                    double slope12 = pointFirst.slopeTo(pointSecond);
                    double slope23 = pointSecond.slopeTo(pointThird);
                    if (slope12 != slope23 || pointSecond.compareTo(pointThird) == 1) {
                        continue;
                    }
                    for (int fourth = third + 1; fourth < pointLength; fourth++) {
                        Point pointFourth = points[fourth];
                        double slope34 = pointThird.slopeTo(pointFourth);
                        if (slope23 == slope34 && pointThird.compareTo(pointFourth) < 1) {
                            LineSegment one = new LineSegment(pointFirst, pointFourth);
                            segments.add(one);

                        }
                    }

                }
            }
        }

    }


    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {

        return segments.toArray(new LineSegment[segments.size()]);
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }


        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
