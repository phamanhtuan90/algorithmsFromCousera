package kdtree;

import edu.princeton.cs.algs4.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tuanpa on 11/26/16.
 */
public class KdTree {
    private int size;
    private Node root;
    private Point2D nearestPoint = null;
    private Point2D pointParam = null;
    private double nearestDistance = Double.POSITIVE_INFINITY;
    private static final boolean VERTICAL = true;

    private static class Node {
        public Point2D p;      // the point
        public RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree

        public Node(Point2D point, RectHV rect) {
            this.p = point;
            this.rect = rect;
            this.lb = null;
            this.rt = null;
        }
    }

    public KdTree() {

        root = null;
        size = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        root = insert(root, p, VERTICAL, 0, 0, 1, 1);
    }

    private Node insert(Node parent, Point2D point, boolean isVertical, double xmin, double ymin, double xmax, double ymax) {
        boolean isLeftBottom;
        if (parent == null) {
            size++;
            return new Node(point, new RectHV(xmin, ymin, xmax, ymax));
        }
        if (parent.p.equals(point)) {
            return parent;
        }

        if (isVertical) {
            isLeftBottom = parent.p.x() > point.x();
            if (isLeftBottom) {
                parent.lb = insert(parent.lb, point, !isVertical,
                        parent.rect.xmin(), parent.rect.ymin(), parent.p.x(), parent.rect.ymax());

            } else {
                parent.rt = insert(parent.rt, point, !isVertical,
                        parent.p.x(), parent.rect.ymin(), parent.rect.xmax(), parent.rect.ymax());
            }
        } else {
            isLeftBottom = parent.p.y() > point.y();
            if (isLeftBottom) {
                parent.lb = insert(parent.lb, point, !isVertical,
                        parent.rect.xmin(), parent.rect.ymin(), parent.rect.xmax(), parent.p.y());
            } else {
                parent.rt = insert(parent.rt, point, !isVertical,
                        parent.rect.xmin(), parent.p.y(), parent.rect.xmax(), parent.rect.ymax());
            }

        }
        return parent;
    }

    // does the set contain point p?
    public boolean contains(Point2D point) {
        return contains(root, point, VERTICAL);
    }

    private boolean contains(Node parent, Point2D point, boolean isVertical) {
        if (parent == null) {
            return false;
        }
        if (parent.p.equals(point)) {
            return true;
        }
        boolean isLeftBottom = (isVertical && parent.p.x() > point.x())
                || (!isVertical && parent.p.y() > point.y());
        if (isLeftBottom) {
            return contains(parent.lb, point, !isVertical);
        } else {
            return contains(parent.rt, point, !isVertical);
        }
    }

    // draw all points to standard draw
    public void draw() {
        draw(root, VERTICAL);
    }

    private void draw(Node node, boolean direction) {
        if (direction) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
        }
        if (node.lb != null) {
            draw(node.lb, !direction);
        }
        if (node.rt != null) {
            draw(node.rt, !direction);
        }

        StdDraw.setPenColor(StdDraw.BLACK);
        node.p.draw();
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        ArrayList<Point2D> listRange = new ArrayList<Point2D>();
        range(root, rect, listRange);
        return listRange;
    }

    private void range(Node node, RectHV rect, ArrayList<Point2D> listRange) {
        if (node == null || !node.rect.intersects(rect)) {
            return;
        }
        if (rect.contains(node.p)) {
            listRange.add(node.p);
        }
        range(node.lb, rect, listRange);
        range(node.rt, rect, listRange);
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D point) {
        nearestPoint = null;
        nearestDistance = Double.POSITIVE_INFINITY;
        pointParam = point;
        return nearest(root);
    }

    private Point2D nearest(Node parent) {
        if (parent == null) {
            return null;
        }

        if (parent.rect.distanceTo(pointParam) >= nearestDistance) {
            return null;
        }

        Node fisrtNode = parent.lb;
        Node lastNode = parent.rt;
        checkNearestPoint(parent.p);

        if (fisrtNode != null && lastNode != null
                && fisrtNode.rect.distanceTo(pointParam) > lastNode.rect.distanceTo(pointParam)) {
            fisrtNode = parent.rt;
            lastNode = parent.lb;
        }

        Point2D nearestPointFirst = nearest(fisrtNode);
        if (nearestPointFirst != null) {
            checkNearestPoint(nearestPointFirst);

        }
        Point2D nearestPointLast = nearest(lastNode);
        if (nearestPointLast != null) {
            checkNearestPoint(nearestPointLast);
        }
        return nearestPoint;
    }

    private void checkNearestPoint(Point2D neighborPoint) {
        double distanceFlag = pointParam.distanceTo(neighborPoint);
        if (distanceFlag < nearestDistance) {
            nearestPoint = neighborPoint;
            nearestDistance = distanceFlag;
        }
    }
}