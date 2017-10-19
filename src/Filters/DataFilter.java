package Filters;

import Data.Pixel;
import Data.PixelMap;

import java.awt.image.BufferedImage;
import java.util.*;

//   Получает данные с графика
public class DataFilter {
    //  Ссылка на открытую картинку графика
    private BufferedImage img;
    //  Выходные данные с графика
    private LinkedList<Pair<Integer,Double>> dataList;
    //  Подкласс, делает всю работу
    private class Finder{
        //  Неизменяемые координаты осей: для Y оси(xLine), для Xоси (yLine)
        private int yLine = -1, xLine = -1;
        //  Ссылка на карту с пикселями
        private PixelMap map;
        //  Ссылка на сам массив с пикселями
        private Pixel[][] mas;
        //  Белый пиксель(для сравнения)
        private Pixel bg = new Pixel(0, 0, new int[]{255, 255, 255});
        //  Чёрный пиксель(для сравнения)
        private Pixel bl = new Pixel(0, 0, new int[]{0, 0, 0});
        //  Сам Tesseract
        private Tess4J tess4J = new Tess4J();
        //  Изменяет картинку, чтобы можно было её обработать
        private void changeImage(){
            //   Преобразует все пиксили в чёрные и белые
            for (int i = 0; i < map.getHeight(); ++i)
                for (int j = 0; j < map.getWidth(); ++j)
                    //  k - рандомный коэф разделения цветов, брал разные, этот сильно не лажал
                    if (mas[i][j].equals(bl, 160)) {
                        mas[i][j].setPixel(0, 0, 0);
                    }else
                        mas[i][j].setPixel(255, 255, 255);
            //---------------------------------------------------------
            //  Ищут оси X и Y, по сути делают одно и то же, но начинают искать с разных сторон
            findX();
            findY();
            //  Зная xLine и yLine продолжает их до пересечения
            //  Рисует линию сверзу - вниз
            for (int i = 0; i < xLine; ++i)
                mas[i][yLine].setPixel(0,0,0);
            //  Рисует линию слева - направо
            for (int j = map.getWidth() - 1; j >= yLine; --j)
                mas[xLine][j].setPixel(0,0,0);
            //------------------------------------------------------------
        }
        private void findX(){
            //  Рандомная константа, нужна для поиска оси - проверить L пикселей на соответствие первому
            int L = 100;
            Pixel[][] mas = map.getPixels();
            for (int j = 0; j < map.getWidth(); ++j)
                for (int i = 0; i < map.getHeight(); ++i)
                    //  Поиск чёрного пикселя
                    if (!mas[i][j].equals(bg, 100) && mas[i][j].equals(bl, 100)) {
                        boolean ok = true;
                        //  Проверка след L пикселей вниз
                        for (int k = 1; k < L; ++k)
                            //  k - рандомная константа сравнения
                            if (!mas[i + k][j].equals(mas[i][j], 100)) {
                                ok = false;
                                break;
                            }
                        //  Если проверка успешно пройдена, то запоминает координату
                        if (ok) {
                            yLine = mas[i][j].getX();
                            return;
                        }
                    }
        }
        //  Тож самое что и findX()
        private void findY(){
            int L = 100;
            for (int i = map.getHeight() - 1; i >= 0; --i)
                for (int j = 0; j < map.getWidth(); ++j)
                    if (!mas[i][j].equals(bg, 100) && mas[i][j].equals(bl, 100)) {
                        boolean ok = true;

                        for (int k = 1; k < L; ++k)
                            if (!mas[i][j + k].equals(mas[i][j], 100)) {
                                ok = false;
                                break;
                            }

                        if (ok) {
                            xLine = mas[i][j].getY();
                            return;
                        }
                    }
        }
        //  Основная функция, собирает данные с графика
        private void createDataList(PixelMap map) {
            // TODO: Переписанная часть, нужно оптимизировать

            this.map = map;
            mas = map.getPixels();
            //  Изменяет изображение, чтобы его легче обработать
            changeImage();

            //  Вызов основной функции тессеракта, обработает ось Y
            tess4J.processImage(xLine,yLine,map,img);

            //  Вернёт пиксель первого штриха на оси Y
            Pixel zeroPixel = tess4J.getZeroPixel();

            //  Работа с осью X
            //  Лист штрихов времени по оси Х
            List<Pixel> pixelTimeList = new ArrayList<>();
            for (int i = yLine; i < map.getWidth()-10; ++i) {
                boolean ok=false;
                //  Проверяем 3 пикселя вниз, если хотя бы 1 чёрный, то считаем штрихом
                for (int k = 1; k < 4; ++k)
                    if (mas[xLine + k][i].equals(mas[xLine][i], 50)){
                        ok=true;
                        break;
                    }
                if(ok)
                    //  Если проверка пройдена, то добавляем в лист
                    pixelTimeList.add(mas[xLine][i]);
            }

            // TODO: Надо изменить работу со временем
            //  Симулирует время оси Х, по сути нумирация строк для проверки данных
            int hour = 0;
            //  Проходим каждый штрих оси Х
            for (Pixel px : pixelTimeList) {
                //  Запоминаем координату х штриха
                int x = px.getX();
                //  Идём вверх
                for (int y = (zeroPixel.getY() - 1); y > 0; --y)
                    //  Ищем 1 чёрный пиксель
                    if (mas[y][x].equals(bl, 150)) {
                        //  Добавляем пару значений в выходной лист: (Время, значение)
                        dataList.addLast(new Pair<>((hour++)%24, tess4J.getValue(mas[y][x])));
                        break;
                    }
            }
        }

    }
    public LinkedList< Pair<Integer,Double> > getData(PixelMap map, BufferedImage img){
        this.img = img;
        //  Выходной лист с данными графика
        dataList = new LinkedList<>();
        Finder finder = new Finder();
        //  Сбор данных
        finder.createDataList(map);
        //  Возврат собранных данных
        return dataList;
    }
}
