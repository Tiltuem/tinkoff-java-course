package edu.project4.image;

import edu.project4.models.FractalImage;
import edu.project4.models.Pixel;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import javax.imageio.ImageIO;

public final class ImageUtils {
    private ImageUtils() {
    }

    public static void save(FractalImage image, Path filename, ImageFormat format) {
        BufferedImage bufferedImage = new BufferedImage(image.width(), image.height(), BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < image.height(); y++) {
            for (int x = 0; x < image.width(); x++) {
                Pixel pixel = image.pixel(x, y);
                int rgb = new Color(pixel.getR(), pixel.getG(), pixel.getB()).getRGB();
                bufferedImage.setRGB(x, y, rgb);
            }
        }

        try {
            File file = filename.toFile();
            ImageIO.write(bufferedImage, format.name().toLowerCase(), file);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
