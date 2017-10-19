package Data;

public class Pixel   { // TODO: Хранит данные пикселя изображения(Убрал лишнии функции)
    private int R, G, B; // TODO: Цвет
    private int x,y; // TODO: Координаты
    public Pixel(int x, int y, int[] RGB){
        this.x = x;
        this.y = y;
        R = RGB[0];
        G = RGB[1];
        B = RGB[2];
    }
    // TODO: Установка цвета пикселя, через числа и через цвет другого пикселя
    public void setPixel(int R,int G,int B){
        this.R=R;
        this.G=G;
        this.B=B;
    }
    public void setPixel(Pixel obj){
        this.R=obj.R;
        this.G=obj.G;
        this.B=obj.B;
    }

    // TODO: Сравние двух пикселей, проверяет лежат ли все значения цвета рядом с цветами другого пикселя +- К
    public boolean equals(Pixel obj, int k){

        return  ( inRange(this.R, obj.R, k) )&&
                ( inRange(this.G, obj.G, k) )&&
                ( inRange(this.B, obj.B, k) );
    }
    // TODO: Вспомогательная функция для сравнения
    private boolean inRange(int A, int B, int k){
        return ( (A <= B+k)&&(A >= B-k));
    }

    public int getX(){ return x;}
    public int getY(){return y;}

}
