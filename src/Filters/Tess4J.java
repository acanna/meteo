package Filters;

import Data.Pixel;
import Data.PixelMap;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import static Main.OSType.isWindows;

class Tess4J {



    // Коэф проверки
    private final double accuracy = 0.2;
    // Расстояние между штрихами
    private double range = -1;
    // Хранит пиксили штрихов
    private List<Pixel> pixelList;
    // Хранит значения штрихов
    private List<Double> dataList = new ArrayList<>();

    void processImage(int xLine, int yLine, PixelMap map, BufferedImage in) {
        // Работа с осью Y
        Pixel[][] mas = map.getPixels();
        pixelList = new ArrayList<>();
        // Хранит значения штрихов
        List<Double> nums = new ArrayList<>();
        // Хранит пары (расстояние между соседними штрихами, кол-во)
        TreeMap<Double, Integer> ranges = new TreeMap<>();
        // Ищем штрихи по оси Y
        for (int i = xLine; i >= 10; --i) {
            boolean ok = false;
            // Проверяем 3 соседние пиксели на чёрной
            for (int k = 1; k < 4; ++k)
                if (mas[i][yLine - k].equals(mas[i][yLine], 50)) {
                    ok = true;
                    break;
                }
            // Если проверка пройдена то передаём его тессеракту
            if (ok)
                pixelList.add(mas[i][yLine]);
        }
        // Кол-во значений штрихов
        int i = 0;
        int maxCount = 0;
        for (Pixel px : pixelList) {
            // Увеличение изображения
            // Скейл увеличения, рандом, 2 и 3 наиболее эффективные, больше начинает вызывать ошибки
            int scale = 2;
            // Вырезаем кусок с цифрами штриха, высота в 20 пикселей рандомная
            BufferedImage buf = in.getSubimage(0, px.getY() - 10,
                    px.getX() - 3, 20);

            //-- Увеличение изображения(5 след строк)
            BufferedImage scaledImg = new BufferedImage(buf.getWidth() * scale, buf.getHeight() * scale,
                    BufferedImage.TYPE_INT_RGB);

            Graphics2D gA = scaledImg.createGraphics();
            gA.drawImage(buf, 0, 0, buf.getWidth() * scale, buf.getHeight() * scale, null);
            gA.dispose();
            //----------------------

            //-- Работа Tesseract
            Tesseract instance = new Tesseract();
            // Пуст к папке с найстройками тессеракта
            String DataPath = "Tess4J/tessdata";
            if (isWindows()) {
                DataPath = "Tess4J\\tessdata";
            }
            instance.setDatapath(DataPath);
            // Список различаемых символов
            instance.setTessVariable("tessedit_char_whitelist", "-0123456789.");
            try {
                // Результат распознования
                String result = instance.doOCR(scaledImg);
                //-- TODO: Правка строки, плокие строки заменяет на "0" ( переделать через исключения)
                //Удаляем переводы строк
                result = result.replaceAll("\n", "").replaceAll(" ", "");
                if (result.length() == 1) {
                    // Убиваем односимвольные строки состоящие из '.' и ','
                    if ((result.equals(".")) && (result.equals(",")))
                        result = "0";
                } else {
                    int k = 0;
                    for (int index = 0; index < result.length(); ++index) {
                        // Убираем строки с 2-мя знаками разделения
                        if ((result.charAt(index) == ',') || (result.charAt(index) == '.'))
                            if (++k > 1) {
                                result = "0";
                                break;
                            }
                        // Убираем строки типа 032, 00
                        if ((result.charAt(0) == '0') && ((result.charAt(1) - '0') >= 0)) {
                            result = "0";
                            break;
                        }
                        // Убираем строки с рандомным '-' не на 0 позиции
                        if ((result.charAt(index) == '-') && (index != 0)) {
                            result = "0";
                            break;
                        }

                    }
                }
                // Превращаем строку в double и добавляем в список значений штрихов
                nums.add(Double.parseDouble(result));
                // Велосипед для добавления расстояний и подсчёт их кол-ва
                if (i > 0) {
                    double subRange = Double.parseDouble(String.format("%.6f", (nums.get(i) - nums.get(i - 1)))
                            .replace(",", "."));
                    if (ranges.containsKey(subRange)) {
                        int count = ranges.get(subRange);
                        if (count > maxCount) {
                            maxCount = count;
                            range = subRange;
                        }

                        if (count > (int) (pixelList.size() * accuracy)) {
                            range = subRange;
                            System.out.println("Range - " + range + " count - " + count);
                            break;
                        }
                        ranges.put(subRange, count + 1);
                    } else
                        ranges.put(subRange, 1);
                }
                ++i;
                //------------------------------
            } catch (TesseractException e) {
                e.printStackTrace();
            }
        }
        //---------------------------------------------------------------
        // Вызываем обработку списка штрихов
        findZero(nums);
    }

    // TODO: Поиск расстояния между штрихами, корректировка значений штрихов(  переписать велосипеды)
    private void findZero(List<Double> nums) {
        // Ищем первый промежуток равный самому частому
        for (int index = 0; index < nums.size() - 1; ++index) {
            if (Double.parseDouble(
                    String.format("%.6f", (nums.get(index + 1) - nums.get(index))).replace(",", ".")) == range) {
                for (int i = 0; i < pixelList.size(); ++i)
                    // Относительно найденного штриха переписываем правильные значениях всех остальных
                    dataList.add(i, nums.get(index) - (index - i) * range);
                break;
            }
        }
        // Вывод (для проверки)
        System.out.println("Lower value - " + nums.get(0));
    }

    // Возврат пикселя первого штриха
    Pixel getZeroPixel() {
        return pixelList.get(0);
    }

    // Принимает пиксель значение которого надо посчитать
    double getValue(Pixel dataPixel) {
        // Пиксели начала и конца промежутка
        Pixel begin = null, end = null;
        // Значение ограничения снизу
        double beginValue = 0;
        // Ищем в какой промежуток попадает пиксель данных и его ограничения снизу и сверху
        for (int i = 0; i < pixelList.size(); ++i) {
            if (pixelList.get(i).getY() >= dataPixel.getY()) {
                begin = pixelList.get(i);
                beginValue = dataList.get(i);
            } else {
                end = pixelList.get(i);
                break;
            }
        }
        // Считаем значение 1 px данных
        double scale = range / (begin.getY() - end.getY());
        // Считаем значение пикселя
        return (begin.getY() - dataPixel.getY()) * scale + beginValue;
    }
}
