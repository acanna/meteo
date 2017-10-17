package Data;

public class Pixel implements Comparable<Pixel> {
    private int R, G, B;
    private int x, y;

    public Pixel(int x, int y, int[] RGB) {
        this.x = x;
        this.y = y;
        R = RGB[0];
        G = RGB[1];
        B = RGB[2];
    }

    public String toString() {
        return (" ( " + R + ", " + G + ", " + B + " ) ");
    }

    public void setPixel(int R, int G, int B) {
        this.R = R;
        this.G = G;
        this.B = B;
    }

    public void setPixel(Pixel obj) {
        this.R = obj.R;
        this.G = obj.G;
        this.B = obj.B;
    }

    public boolean equals(Pixel obj) {

        int k = 30;

        return (inRange(this.R, obj.R, k)) &&
                (inRange(this.G, obj.G, k)) &&
                (inRange(this.B, obj.B, k));
    }

    public boolean equals(Pixel obj, int k) {

        return (inRange(this.R, obj.R, k)) &&
                (inRange(this.G, obj.G, k)) &&
                (inRange(this.B, obj.B, k));
    }

    public boolean equals(int R, int G, int B) {
        return (this.R == R) && (this.G == G) && (this.B == B);
    }

    private boolean inRange(int A, int B, int k) {
        return ((A <= B + k) && (A >= B - k));
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int[] getColors() {
        return new int[]{R, G, B};
    }

    public boolean equals(Object obj) {
        Pixel pixel = (Pixel) obj;
        return this.equals(pixel);
    }

    public int compareTo(Pixel obj) {
        if (this.equals(obj))
            return 0;
        return 1;
    }
}
