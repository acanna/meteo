package Data;

public class PixelMap {
    private final int width, height;
    private Pixel[][] pixelMap;

    PixelMap(int height, int width) {
        this.width = width;
        this.height = height;
        pixelMap = new Pixel[height][width];
    }

    public Pixel[][] getPixels() {
        return pixelMap;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
