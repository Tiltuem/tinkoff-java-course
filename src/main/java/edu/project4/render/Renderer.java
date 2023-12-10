package edu.project4.render;

import edu.project4.models.FractalImage;
import edu.project4.models.Point;
import edu.project4.models.Rect;
import edu.project4.transformation.Transformation;
import java.util.List;
import java.util.Random;

public interface Renderer {
    double GAMMA = 2.5;

    FractalImage render(
        FractalImage canvas,
        Rect world,
        List<Transformation> variations,
        int samples,
        short iterPerSample,
        long seed,
        int symmetry
    );

    FractalImage gammaCorrect(FractalImage image);

    Point getRandomPoint(Random random, double maxX, double maxY);

    void tryAddColor(FractalImage canvas, Transformation variation, Point pwr);
}
