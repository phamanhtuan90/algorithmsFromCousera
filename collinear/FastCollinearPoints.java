

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by tuanpa on 11/5/16.
 */
public class FastCollinearPoints {
    private ArrayList<LineSegment> segments = new ArrayList<LineSegment>();
    private HashMap<Double, ArrayList<Point>> segmentsInfo = new HashMap<Double, ArrayList<Point>>();

    public FastCollinearPoints(Point[] points) {
        if (points == null)
            throw new NullPointerException("Null Pointer");
        Arrays.sort(points);
        for (int p = 0; p < points.length; p++) {
            Point[] tmpPoints = Arrays.copyOfRange(points, p, points.length);
            Arrays.sort(tmpPoints, tmpPoints[0].slopeOrder());
            int lastQ = 1;
            for (int q = 2; q < tmpPoints.length; q++) {
                double slope = tmpPoints[0].slopeTo(tmpPoints[lastQ]);
                while (q < tmpPoints.length && Double.compare(slope, tmpPoints[0].slopeTo(tmpPoints[q])) == 0) {
                    q++;
                }
                if (q - lastQ > 2 && checkOrUpdates(slope, tmpPoints, q - 1)) {
                    segments.add(new LineSegment(tmpPoints[0], tmpPoints[q - 1]));
                }
                lastQ = q;
            }
        }


    }

    private boolean checkOrUpdates(double slope, Point[] pts, int end) {
        ArrayList<Point> ends = this.segmentsInfo.get(slope);
        if (ends == null) {
            ends = new ArrayList<Point>();
            ends.add(pts[end]);
            this.segmentsInfo.put(slope, ends);
            return true;
        } else if (ends.contains(pts[end])) return false;
        else {
            ends.add(ends.size(), pts[end]);
            this.segmentsInfo.put(slope, ends);
            return true;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}
