package edu.project4.transformation;

import edu.project4.models.Point;

public class TrigonometricTransformation extends Transformation {
    @Override
    public Point apply(Point point) {
        return new Point(Math.cos(point.x()), Math.sin(point.y()));
    }
}
