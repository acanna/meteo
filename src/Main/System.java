package Main;


import Data.ImageData;
import Filters.BrokenImage;
import Filters.DataFilter;
import Streams.Input;
import Streams.Output;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class System {
    private ImageData image = new ImageData();
    private DataFilter dataFilter = new DataFilter();
    // Инициализируем создание списка файлов изображений
    private Input in = new Input();
    private Output out = new Output();
    //  Кол-во картинок
    private int size = in.getSize();
    // Строка для лога проверки
    private StringBuilder logStr = new StringBuilder();
    // Переводит секунды
    private String getTime(double sec){
        int mins = (int)( sec / 60 );
        double seconds = sec - mins * 60;
        int hours = mins / 60;
        mins = mins - hours * 60;
     return hours + " : " + mins + " : " + seconds;
    }
    //  Выволняет контроль за обработкой всех изображений
    void searchData() {
        int index = 0;
        int ok=0, error =0;
        while (index++ < size) {
            try {
                java.lang.System.out.print("_________________________\n" + "[ "
                        + index + "/" + size + " ] Обработка: ");
                //  Открываем изображение, считывает пиксили
                image.openImage(in.next());
                java.lang.System.out.println(" \"" + in.getName() + "\"");
                double proccTimeBegin = java.lang.System.nanoTime() / 1000000000.0;
                //  Обработка и вывод в файл
                // getPath - путь к файлу с данными
                out.flush(dataFilter.getData(image.getPixelMap(), image.getImage(), in.getDir()), in.getPath());

                double proccTimeEnd = java.lang.System.nanoTime() / 1000000000.0;
                java.lang.System.out.println("Processing time - " + (proccTimeEnd - proccTimeBegin));
                logStr.append(in.getName() + " - OK \n");
                ++ok;
                java.lang.System.out.println("COMPLETE");
            } catch (BrokenImage e) {
                logStr.append(in.getName() + " - Error " + "  Причина: " + e.getMessage() + "\n");
                ++error;
                java.lang.System.out.println(e.getMessage() + "\nABORT");
            }
        }
        java.lang.System.out.println("_______________________\n      [Done]");
        java.lang.System.out.println("Всего изображений обработано: " + (ok + error) + "\nOk - " + ok + "\nError - " + error);
        logStr.append("_______________________\nВсего изображений: " + (ok + error) + "\n Ok - " + ok + "\n Error - " + error);
        try {
            BufferedWriter outLog = new BufferedWriter(new FileWriter(new File("Log.txt")));
            for(String str : logStr.toString().split("\n")) {
                outLog.write(str);
                outLog.newLine();
            }
            outLog.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        System system = new System();
        java.lang.System.out.println("Всего изображений найдено: " + system.size);
        java.lang.System.out.println("Примерное время обработки: " + system.getTime(system.size * 18) );
        java.lang.System.out.println("      [Processing start]");
        double workTimeBegin = java.lang.System.nanoTime() / 1000000000.0;
        system.searchData();
        double workTimeEnd = java.lang.System.nanoTime() / 1000000000.0;
        java.lang.System.out.println("Обработка завершена за   " + system.getTime(workTimeEnd - workTimeBegin));
        java.lang.System.out.println("Полный лог обработки смотрите Log.txt");
    }
}

