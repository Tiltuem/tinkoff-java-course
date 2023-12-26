package edu.project4;

import edu.project4.image.ImageFormat;
import edu.project4.image.ImageUtils;
import edu.project4.models.FractalImage;
import edu.project4.models.Rect;
import edu.project4.render.MultiThreadRenderer;
import edu.project4.render.Renderer;
import edu.project4.render.SingleThreadRenderer;
import edu.project4.transformation.PolarTransformation;
import edu.project4.transformation.SphereTransformation;
import edu.project4.transformation.Transformation;
import edu.project4.transformation.TrigonometricTransformation;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Project4Test {
    private static final List<Transformation> VARIATIONS =
        List.of(new TrigonometricTransformation(), new PolarTransformation(), new SphereTransformation());
    private static final Rect WORLD = new Rect(0, 0, 2560, 1440);
    private static final int SAMPLES = 500_000;
    private static final short ITER_PER_SAMPLE = (short) 15;
    private static final long SEED = 12345;
    private static final int SYMMETRY = 3;

    @Test
    public void assertThatMultiThreadWorksFasterThanSingleThread() {
        Renderer singleThreadRenderer = new SingleThreadRenderer();
        Renderer multiThreadRenderer = new MultiThreadRenderer(4);

        long singleThreadStart = System.nanoTime();
        singleThreadRenderer.gammaCorrect(singleThreadRenderer.render(
            FractalImage.create((int) WORLD.width(), (int) WORLD.height()),
            WORLD, VARIATIONS, SAMPLES, ITER_PER_SAMPLE, SEED, SYMMETRY
        ));
        long singleThreadWorkTime = System.nanoTime() - singleThreadStart;

        long multiThreadStart = System.nanoTime();
        multiThreadRenderer.gammaCorrect(multiThreadRenderer.render(
            FractalImage.create((int) WORLD.width(), (int) WORLD.height()),
            WORLD, VARIATIONS, SAMPLES, ITER_PER_SAMPLE, SEED, SYMMETRY
        ));
        long multiThreadWorkTime = System.nanoTime() - multiThreadStart;


        assertTrue(multiThreadWorkTime < singleThreadWorkTime);
    }

    @Test
    public void assertThatFractalImageSavesSuccessfully() {
        Renderer renderer = new MultiThreadRenderer(4);

        FractalImage image = renderer.render(
            FractalImage.create((int) WORLD.width(), (int) WORLD.height()),
            WORLD, VARIATIONS, SAMPLES, ITER_PER_SAMPLE, SEED, SYMMETRY
        );

        Path path = Path.of("fractal.png");
        ImageUtils.save(renderer.gammaCorrect(image), path, ImageFormat.PNG);
        assertTrue(path.toFile().exists());

        path.toFile().delete();
    }
}

