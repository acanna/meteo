package Data;
//  Хранит данные пикселя изображения(Убрал лишнии функции)
public class Pixel   {
    //  Цвет
    private int R, G, B;
    //  Координаты
    private int x,y;
    public Pixel(int x, int y, int[] RGB){
        this.x = x;
        this.y = y;
        R = RGB[0];
        G = RGB[1];
        B = RGB[2];
    }
    //  Установка цвета пикселя, через числа и через цвет другого пикселя
    public void setPixel(int R,int G,int B){
        this.R=R;
        this.G=G;
        this.B=B;
    }
    //  Сравние двух пикселей, проверяет лежат ли все значения цвета рядом с цветами другого пикселя +- К
    public boolean equals(Pixel obj, int k){

        return  ( inRange(this.R, obj.R, k) )&&
                ( inRange(this.G, obj.G, k) )&&
                ( inRange(this.B, obj.B, k) );
    }
    //  Вспомогательная функция для сравнения
    private boolean inRange(int A, int B, int k){
        return ( (A <= B+k)&&(A >= B-k));
    }
    public int getX(){ return x;}
    public int getY(){return y;}

}
