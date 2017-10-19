package Data;
//  Хранит в себе матрицу с пикселями
public class PixelMap {
    private Pixel[][] pixelMap;
    private final int width,height;
    PixelMap(int height, int width){
        this.width = width;
        this.height = height;
        pixelMap = new Pixel[height][width];
    }
    public Pixel[][] getPixels(){
        return pixelMap;
    }
    public int getWidth(){ return width;}
    public int getHeight(){ return height;}
}
