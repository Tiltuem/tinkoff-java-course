package edu.project4.transformation;

import edu.project4.models.Point;

public class PolarTransformation extends Transformation {
    @Override
    public Point apply(Point point) {
        double newX = Math.atan(point.y() / point.x()) / Math.PI;
        double newY = Math.sqrt(point.x() * point.x() + point.y() * point.y()) - 1;

        return new Point(newX, newY);
    }
}
