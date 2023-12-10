package edu.project4.render;

import edu.project4.models.FractalImage;
import edu.project4.models.Pixel;
import edu.project4.models.Point;
import edu.project4.models.Rect;
import edu.project4.transformation.Transformation;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class MultiThreadRenderer implements Renderer {
    private final ExecutorService pool;
    private final int threadsCount;

    public MultiThreadRenderer(int threadsCount) {
        if (threadsCount < 1) {
            throw new IllegalArgumentException("Threads count must be more than 0");
        }

        this.threadsCount = threadsCount;
        this.pool = Executors.newFixedThreadPool(this.threadsCount);
    }

    @Override
    public FractalImage render(
        FractalImage canvas,
        Rect world,
        List<Transformation> variations,
        int samples,
        short iterPerSample,
        long seed,
        int symmetry
    ) {
        Random random = new Random(seed);
        final double maxX = world.width() / world.height();
        final double maxY = 1;
        Point center = new Point(world.x() + world.width() / 2, world.y() + world.height() / 2);

        CompletableFuture.allOf(IntStream.range(0, threadsCount)
                .mapToObj(i -> CompletableFuture.runAsync(() -> IntStream.range(0, samples / threadsCount)
                    .forEach(num -> {
                        Point normalizedPoint = getRandomPoint(random, maxX, maxY);

                        for (short step = 0; step < iterPerSample; step++) {
                            Transformation variation = variations.get(random.nextInt(variations.size()));
                            normalizedPoint = variation.apply(normalizedPoint);

                            double theta2 = 0.0;

                            Point originalPoint = new Point(
                                (int) (world.width() - (((maxX - normalizedPoint.x()) / (2 * maxX)) * world.width())),
                                (int) (world.height() - (((maxY - normalizedPoint.y()) / (2 * maxY)) * world.height()))
                            );
                            double deltaXToCenter = originalPoint.x() - center.x();
                            double deltaYToCenter = originalPoint.y() - center.y();
                            double distanceToCenter =
                                Math.sqrt(deltaXToCenter * deltaXToCenter + deltaYToCenter * deltaYToCenter);

                            for (int s = 0; s < symmetry; theta2 += Math.PI * 2 / symmetry, s++) {
                                Point originalPointReversed = new Point(
                                    center.x() + distanceToCenter
                                        * Math.cos(Math.acos(deltaXToCenter / distanceToCenter) + theta2),
                                    center.y() + distanceToCenter
                                        * Math.sin(Math.asin(deltaYToCenter / distanceToCenter) + theta2)
                                );

                                if (world.contains(originalPointReversed)) {
                                    tryAddColor(canvas, variation, originalPointReversed);
                                }
                            }
                        }
                    }), pool))
                .toArray(CompletableFuture[]::new))
            .join();

        return canvas;
    }

    @Override
    public FractalImage gammaCorrect(FractalImage image) {
        AtomicReference<Double> max = new AtomicReference<>(0.0);

        makeActionOnPixels(image, pixel -> {
            if (pixel.getHitCount() != 0) {
                max.set(Math.max(max.get(), Math.log10(pixel.getHitCount())));
            }
        });

        makeActionOnPixels(image, pixel -> {
            if (pixel.getHitCount() != 0) {
                pixel.correctColor(Math.pow(Math.log10(pixel.getHitCount()) / max.get(), 1 / GAMMA));
            }
        });

        return image;
    }

    @Override
    public Point getRandomPoint(Random random, double maxX, double maxY) {
        return new Point(random.nextDouble(-maxX, maxX), random.nextDouble(-maxY, maxY));
    }

    @Override
    public void tryAddColor(FractalImage canvas, Transformation variation, Point pwr) {
        Pixel pixel = canvas.data()[((int) pwr.y()) * canvas.width() + (int) pwr.x()];

        if (pixel == null) {
            return;
        }

        pixel.addColor(variation.getR(), variation.getG(), variation.getB());
    }

    private void makeActionOnPixels(FractalImage image, Consumer<Pixel> action) {
        final int iterations = image.data().length / threadsCount;
        final int width = image.width();

        CompletableFuture.allOf(IntStream.range(0, threadsCount)
                .mapToObj(i -> CompletableFuture.runAsync(() -> IntStream.range(iterations * i, iterations * (i + 1))
                    .forEach(pixelIndex -> {
                        if (pixelIndex < image.data().length) {
                            Pixel pixel = image.pixel(pixelIndex % width, pixelIndex / width);

                            action.accept(pixel);
                        }
                    }), pool))
                .toArray(CompletableFuture[]::new))
            .join();
    }

}
