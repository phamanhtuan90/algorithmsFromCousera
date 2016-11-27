package kdtree;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tuanpa on 11/26/16.
 */
public class PointSET {
    private SET<Point2D> points;

    // construct an empty set of points
    public PointSET() {
        points = new SET<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // number of points in the set
    public int size() {
        return points.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        points.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        return points.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D point : points) {
            point.draw();
        }
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        List<Point2D> listRange = new ArrayList<Point2D>();
        for (Point2D point : points) {

            if (rect.contains(point)) {
                listRange.add(point);
            }
        }

        return listRange;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        Point2D pointNearest = null;
        for (Point2D point : points) {
            double distanceToPoint = p.distanceTo(point);
            if(distanceToPoint == 0){
                continue;
            }
            if(pointNearest == null){
                pointNearest = point;
            }else{
                if(distanceToPoint < p.distanceTo(pointNearest)){
                    pointNearest = point;
                }
            }
        }
        return pointNearest;
    }
}
