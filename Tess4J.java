package Filters;

import Data.Pixel;
import Data.PixelMap;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

class Tess4J {
    private Pixel zeroPixel = null; //TODO: Первый штрих по оси Y
    private Pixel secondPixel = null; //TODO: Второй штрих по оси Y
    private double zeroValue = -1; //TODO: Значение первого штриха
    private double scale = -1; //TODO: Скейл

    void processImage(int xLine, int yLine, PixelMap map, BufferedImage in){ //TODO:
        //TODO: Работа с осью Y
        Pixel[][]mas = map.getPixels();
        List<Double> nums = new ArrayList<>(); //TODO: Массив со значениями штрихов по оси Y

        for (int i = xLine; i >= 10; --i) { //TODO: Ищем штрихи по оси Y
            boolean ok=false;
            for (int k = 1; k < 4; ++k) //TODO: Проверяем 3 соседние пиксели на чёрной
                if (mas[i][yLine - k].equals(mas[i][yLine], 50)){
                    ok=true;
                    break;
                }
            if(ok) { //TODO: Если проверка пройдена то передаём его тессеракту

                if(zeroPixel == null) //TODO: Запоминаем первый пиксель
                    zeroPixel = mas[i][yLine];
                else if (secondPixel == null) //TODO: Запоминаем второй пиксель
                    secondPixel = mas[i][yLine];

                //TODO: Увеличение изображения
                int scale = 2; //TODO: Скейл увеличения, рандом, 2 и 3 наиболее эффективные, больше начинает вызывать ошибки

                BufferedImage buf =  in.getSubimage(0, mas[i][yLine].getY() - 10,
                        mas[i][yLine].getX() - 3, 20); //TODO: Вырезаем кусок с цифрами штриха, высота в 20 пикселей рандомная

                //TODO: Увеличение изображения(строки до пунктира)
                BufferedImage scaledImg = new BufferedImage(buf.getWidth() * scale, buf.getHeight() * scale,
                        BufferedImage.TYPE_INT_RGB);

                Graphics2D gA = scaledImg.createGraphics();
                gA.drawImage(buf, 0, 0, buf.getWidth() * scale, buf.getHeight() * scale, null);
                gA.dispose();
                //----------------------

                //TODO: Работа Tesseract
                Tesseract instance = new Tesseract();
                instance.setDatapath(".\\Tess4J\\tessdata"); //TODO: Пуст к папке с найстройками тессеракта
                instance.setTessVariable("tessedit_char_whitelist", "-0123456789."); //TODO: Список различаемых символов
                try {
                    String result = instance.doOCR(scaledImg); //TODO: Результат распознования

                    //TODO: Правка строки(Потом переделаю через исключения), плокие строки заменяем 0
                    result = result.replaceAll("\n","").replaceAll(" ",""); //TODO:Удаляем переводы строк
                    if(result.length() == 1){
                        if( ( result.equals(".") )&&( result.equals(",") )) //TODO: Убиваем односимвольные строки состоящие из '.' и ','
                            result = "0";
                    } else {
                        int k = 0;
                        for (int index = 0; index < result.length(); ++index) {
                            if ((result.charAt(index) == ',') || (result.charAt(index) == '.')) //TODO: Убираем строки с 2-мя знаками разделения
                                if (++k > 1) {
                                    result = "0";
                                    break;
                                }

                            if ((result.charAt(0) == '0') && ((result.charAt(1) - '0') >= 0)) { //TODO: Убираем строки типа 032, 00
                                result = "0";
                                break;
                            }

                            if ((result.charAt(index) == '-') && (index != 0)) { //TODO: Убираем строки с рандомным '-' не на 0 позиции
                                result = "0";
                                break;
                            }

                        }
                    }
                    //-------------
                    nums.add( Double.parseDouble(result) ); //TODO: Превращаем строку в double и добавляем в список значений штрихов

                } catch (TesseractException e) {
                    e.printStackTrace();
                }
            }
        }
        //---------------------------------------------------------------
        findZero(nums); //TODO: Вызываем обработку списка штрихов
    }
    private void findZero(List<Double> nums){//TODO: Поиск начала и скейла (полностью перепишу)

        TreeMap<Double, Integer> ranges = new TreeMap<>(); //TODO: Хранит пары (расстояние между соседними штрихами, кол-во)

        for(int index = nums.size() - 1; index > 0; --index){ //TODO: Велосипед для добавления расстояний
            double range = Double.parseDouble( String.format("%.6f", (nums.get(index) - nums.get(index -1)) ).replace(",",".") );
            if(ranges.containsKey(range))
                ranges.put(range, ranges.get(range) + 1);
            else
                ranges.put(range, 1);

        }

        Map.Entry<Double,Integer> max = null; //TODO: Ищем наиболее встречающуюся пару
        for(Map.Entry<Double,Integer> set :ranges.entrySet()){
            if(max == null)
                max = set;
            else if (set.getValue() > max.getValue())
                max = set;
        }



        double begin = -1; //TODO: Хранит значение первого штриха по Y
        System.out.println(max.getKey() + " - " + max.getValue()); //TODO: Вывод "расстояния - кол-во" (для проверки)
        for(int index =0; index < nums.size() - 1; ++index) { //TODO: Ищем первый промежуток равный самому частому
            if( Double.parseDouble(
                    String.format("%.6f", (nums.get(index + 1) - nums.get(index)) ).replace(",",".")) == max.getKey() ){
                begin = nums.get(index) - max.getKey()*index; //TODO: Вычитает разницу, находит значение первого штриха
                break;
            }
        }

        zeroValue = begin; //TODO: Хранит значение первого штриха
        scale = max.getKey()/(zeroPixel.getY() - secondPixel.getY()); //TODO: Считает скейл(числовое значение 1 пикселя)

        System.out.println("Begin - " + begin); //TODO: Вывод (для проверки)
        System.out.println("Range - " + max.getKey());
    }
    Pixel getZeroPixel(){ return zeroPixel;} //TODO: Возврат найденных значений
    double getZeroValue(){ return zeroValue;}
    double getScale(){ return scale;}
}
