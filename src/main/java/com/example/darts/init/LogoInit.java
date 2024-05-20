package com.example.darts.init;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class LogoInit implements CommandLineRunner {
    private static final String LOGO_PATH = "src/main/resources/static/img/logo/preloader_logo.png";
    private static final String OUTPUT_LOGO_PATH = "src/main/resources/static/img/logo/logo.png";

    @Override
    public void run(String... args) throws Exception {
        // Read the original image
        BufferedImage bufferedImage = ImageIO.read(new File(LOGO_PATH));

        // Resize the image
        BufferedImage resizedImage = resizeImage(bufferedImage, 300, 56);

        // Save the resized image
        saveResizedImage(resizedImage, OUTPUT_LOGO_PATH);
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
        Image tempImage = originalImage.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(tempImage, 0, 0, null);
        g2d.dispose();

        return resizedImage;
    }

    private void saveResizedImage(BufferedImage resizedImage, String outputPath) throws IOException {
        File outputFile = new File(outputPath);
        ImageIO.write(resizedImage, "png", outputFile);
    }
}
