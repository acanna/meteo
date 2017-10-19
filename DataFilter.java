package Filters;

import Data.Pixel;
import Data.PixelMap;
import javafx.util.Pair;

import java.awt.image.BufferedImage;
import java.util.*;

public class DataFilter { // TODO:  Получает данные с графика
    private BufferedImage img; // TODO: Ссылка на открытую картинку графика
    private LinkedList<Pair<Integer,Double>> dataList; // TODO: Выходные данные с графика

    private class Finder{ // TODO: Подкласс, делает всю работу

        private int yLine = -1, xLine = -1; // TODO: Неизменяемые координаты осей: для Y оси(xLine), для Xоси (yLine)
        private PixelMap map; // TODO: Ссылка на карту с пикселями
        private Pixel[][] mas; // TODO: Ссылка на сам массив с пикселями
        private Pixel bg = new Pixel(0, 0, new int[]{255, 255, 255}); // TODO: Белый пиксель(для сравнения)
        private Pixel bl = new Pixel(0, 0, new int[]{0, 0, 0}); // TODO: Чёрный пиксель(для сравнения)
        private Tess4J tess4J = new Tess4J(); // TODO: Сам Tesseract

        private void changeImage(){ // TODO: Изменяет картинку, чтобы можно было её обработать
            // TODO:  Преобразует все пиксили в чёрные и белые

            for (int i = 0; i < map.getHeight(); ++i)
                for (int j = 0; j < map.getWidth(); ++j)
                    if (mas[i][j].equals(bl, 160)) { // TODO: k - рандомный коэф разделения цветов, брал разные, этот сильно не лажал
                        mas[i][j].setPixel(0, 0, 0);
                    }else
                        mas[i][j].setPixel(255, 255, 255);
            //---------------------------------------------------------
            findX(); // TODO: Ищут оси X и Y, по сути делают одно и то же, но начинают искать с разных сторон
            findY(); // TODO:
            // TODO: Зная xLine и yLine продолжент их до пересечения
            for (int i = 0; i < xLine; ++i) // TODO: Рисует линию сверзу вниз
                mas[i][yLine].setPixel(0,0,0);

            for (int j = map.getWidth() - 1; j >= yLine; --j) // TODO: Рисует линию слева направо
                mas[xLine][j].setPixel(0,0,0);
            //------------------------------------------------------------
        }
        private void findX(){
            int L = 100; // TODO: Рандомная константа, нужна для поиска оси - проверить L пикселей на соответствие первому
            Pixel[][] mas = map.getPixels();
            for (int j = 0; j < map.getWidth(); ++j)
                for (int i = 0; i < map.getHeight(); ++i)
                    if (!mas[i][j].equals(bg, 100) && mas[i][j].equals(bl, 100)) { // TODO: Поиск чёрного пикселя
                        boolean ok = true;

                        for (int k = 1; k < L; ++k) // TODO: Проверка след L пикселей вниз
                            if (!mas[i + k][j].equals(mas[i][j], 100)) { // TODO: k - рандомная константа сравнения
                                ok = false;
                                break;
                            }

                        if (ok) { // TODO: Если проверка успешно пройдена, то запоминает координату
                            yLine = mas[i][j].getX();
                            return;
                        }
                    }
        }
        private void findY(){// TODO: Тож самое что и findX()
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
        private void createDataList(PixelMap map) { // TODO: Основная функция, собирает данные с графика
            //TODO: Переписанная часть
            this.map = map;
            mas = map.getPixels();

            changeImage(); // TODO: Изменяет изображение, чтобы его легче обработать
            tess4J.processImage(xLine,yLine,map,img); // TODO: Вызов основной функции тессеракта, обработает ось Y
            Pixel zeroPixel = tess4J.getZeroPixel(); // TODO: Вернёт пиксель первого штриха на оси Y
            double zeroValue = tess4J.getZeroValue(); // TODO: Вернёт значение первого штриха по оси(прочитанное тессерактом)
            double scale =tess4J.getScale(); // TODO: Вернёт скейл (значение 1 пикселя в числах)


            // TODO: Работа с осью X
            List<Pixel> pixelTimeList = new ArrayList<>(); // TODO: Лист штрихов времени по оси Х
            for (int i = yLine; i < map.getWidth()-10; ++i) {
                boolean ok=false;
                for (int k = 1; k < 4; ++k)// TODO: Проверяем 3 пикселя вниз, если хотя бы 1 чёрный, то считаем штрихом
                    if (mas[xLine + k][i].equals(mas[xLine][i], 50)){
                        ok=true;
                        break;
                    }
                if(ok)
                    pixelTimeList.add(mas[xLine][i]); // TODO: Если проверка пройдена, то добавляем в лист
            }
            //------------------------------------------------------------------// TODO: старая часть

            int hour = 0; // TODO: Симулирует время оси Х, по сути нумирация строк для проверки данных

            for (Pixel px : pixelTimeList) { // TODO: Проходим каждый штрих оси Х
                int x = px.getX(); // TODO: Запоминаем координату х штриха

                for (int y = (zeroPixel.getY() - 1); y > 0; --y) // TODO: Идём вверх
                    if (mas[y][x].equals(bl, 150)) { // TODO: Ищем 1 чёрный пиксель
                        int pixelY = zeroPixel.getY() - mas[y][x].getY(); // TODO: Запоминаем разницу в пикселях между 1 штрих по оси Y и найденной точкой
                        dataList.addLast(new Pair<>((hour++)%24, pixelY * scale + zeroValue)); // TODO: Добавляем пару значений в выходной лист:
                        break;                                                                 // TODO: (Время, Кол-во пикселей разницы * скейл + значение 1 штриха по Y)
                    }
            }
        }
    }
    public LinkedList< Pair<Integer,Double> > getData(PixelMap map, BufferedImage img){
        this.img = img;
        dataList = new LinkedList<>(); // TODO: Выходной лист с данными графика
        Finder finder = new Finder();
        finder.createDataList(map); // TODO: Сбор данных
        return dataList; // TODO: Возврат собранных данных
    }
}
