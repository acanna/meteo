package Filters;

import Data.Pixel;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class Tess4J {
    private BufferedImage buf;

    double makePicture(Pixel startPixel, BufferedImage img, String fName) {

        int scale = 2;

        buf = img.getSubimage(0, startPixel.getY() - 10,
                startPixel.getX() - 4, 20);

        BufferedImage scaledImg = new BufferedImage(buf.getWidth() * scale, buf.getHeight() * scale,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D gA = scaledImg.createGraphics();
        gA.drawImage(buf, 0, 0, buf.getWidth() * scale, buf.getHeight() * scale, null);
        gA.dispose();


        buf = scaledImg;
        try {
            ImageIO.write(scaledImg, "tif", new File(fName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Double.parseDouble(identify());
    }

    private String identify() {
        ITesseract instance = new Tesseract();
        instance.setDatapath("Tess4J/tessdata");
        instance.setTessVariable("tessedit_char_whitelist", "0123456789.,");
        String result = "";
        try {
            result = instance.doOCR(buf);
            System.out.println(result);
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }
        return result;
    }
}
