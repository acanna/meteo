package Filters;

import Data.Pixel;
import Data.PixelMap;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DataFilter {
    private Pixel zeroPixel;
    private BufferedImage img;
    private LinkedList<Pair<Integer, Double>> dataList;
    private double firstY, scaleY;
    private Finder finder = new Finder();

    public LinkedList<Pair<Integer, Double>> getData(PixelMap map, BufferedImage img) {
        this.img = img;
        dataList = new LinkedList<>();
        finder.createDataList(map);
        return dataList;
    }

    private class Finder {
        private final double CONST_X = 60;
        private Pixel bgPixel;
        private Tess4J tess4J = new Tess4J();
        private Pixel firstX;
        private Pixel dataPixel = new Pixel(0, 0, new int[]{0, 0, 0});
        private double pixelLengthX;
        private java.util.List<Pixel> pixelTimeList = new ArrayList<>();

        private void findPoints(PixelMap map) {
            bgPixel = new Pixel(0, 0, new int[]{255, 255, 255});
            findZero(map);
            findScaleX(map);
            findScaleY(map);
        }

        private void findZero(PixelMap map) {
            Pixel[][] mas = map.getPixels();
            Pixel curr;
            for (int x = map.getWidth() - 1; x > 0; --x)
                for (int y = map.getHeight() - 1; y > 5; --y) {
                    curr = mas[y][x];
                    boolean ok = true;
                    for (int k = 0; k < 5; ++k) {
                        if (curr.equals(bgPixel, 80) || !((mas[y - k][x].equals(curr, 100)) && (mas[y][x - k].equals(curr, 100)))) {
                            ok = false;
                            break;
                        }
                    }
                    if (ok) {
                        for (int m = curr.getX() - 1; m > 0; --m)
                            if (!mas[curr.getY()][m].equals(bgPixel, 80) && (mas[curr.getY() - 1][m].equals(curr, 100))) {
                                zeroPixel = mas[curr.getY()][m];
                                break;
                            }
                        return;
                    }
                }
            zeroPixel = null;
        }

        private void findScaleX(PixelMap map) {
            Pixel[][] mas = map.getPixels();
            Pixel zeroY = zeroPixel;
            Pixel secondX = null;

            for (int k = zeroPixel.getY() + 1; k < map.getHeight(); ++k)
                if (!mas[k][zeroPixel.getX()].equals(bgPixel)) {
                    zeroY = mas[k][zeroPixel.getX()];
                    zeroY.setPixel(mas[k][zeroPixel.getX() + 4]);
                    break;
                }

            for (int k = zeroY.getX() + 1; k < map.getWidth(); ++k)
                if (mas[zeroY.getY() + 1][k].equals(zeroY, 60)) {
                    pixelTimeList.add(mas[zeroY.getY()][k]);
                }

            firstX = pixelTimeList.get(0);
            secondX = pixelTimeList.get(1);
            //pixelLengthX = secondX.getX() - firstX.getX();
            // scaleX = CONST_X / pixelLengthX;
        }

        private void findScaleY(PixelMap map) {
            Pixel[][] mas = map.getPixels();
            Pixel zeroX = zeroPixel;

            for (int k = zeroPixel.getX() - 1; k > 0; --k)
                if (!mas[zeroPixel.getY() - 1][k].equals(bgPixel, 60)) {
                    zeroX = mas[zeroPixel.getY()][k];
                    zeroX.setPixel(mas[zeroPixel.getY() - 4][k]);
                    break;
                }

            List<Pixel> list = new ArrayList<>();
            for (int k = zeroX.getY() - 1; k > 0; --k)
                if (mas[k][zeroX.getX() - 1].equals(zeroX, 100)) {
                    list.add(mas[k][zeroX.getX()]);
                }

            firstY = tess4J.makePicture(list.get(0), img, "buf1.tif");
            double secondY = tess4J.makePicture(list.get(1), img, "buf2.tif");
            scaleY = (secondY - firstY) / (list.get(0).getY() - list.get(1).getY());
        }

        private void createDataList(PixelMap map) {

            Pixel[][] mas = map.getPixels();
            findPoints(map);
            int pixelY;
            int hour = 0;

            for (Pixel px : pixelTimeList) {
                int x = px.getX();

                for (int y = (zeroPixel.getY() - 1); y > 0; --y)
                    if (mas[y][x].equals(dataPixel, 150)) {
                        pixelY = zeroPixel.getY() - mas[y][x].getY();
                        dataList.addLast(new Pair<>((hour++) % 24, pixelY * scaleY + firstY));
                        break;
                    }
            }
        }
    }
}
