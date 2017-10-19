package Main;


import Data.ImageData;
import Filters.DataFilter;
import Streams.Input;
import Streams.Output;

import static Main.OSType.isWindows;

class System {
    private ImageData image = new ImageData();
    private DataFilter dataFilter = new DataFilter();
    //  Инициализируем создание списка файлов изображений
    private Input in = new Input();
    private Output out = new Output();
    //  Кол-во картинок
    private int size = in.getSize();

    //  Хз зачем я его написал, наверное так надо было
    System() {
    }

    //  Выволняет контроль за обработкой всех изображений
    void searchData() {
        int index = 0;
        while (index++ < size) {
            //  Открываем изображение, считывает пиксили
            image.openImage(in.next());

            java.lang.System.out.println("_________________________\nProcessing: \"" + in.getName() + "\"");
            double proccTimeBegin = java.lang.System.nanoTime() / 1000000000.0;

            //  Обработка и вывод в файл
            // dataBaseName - путь в который выводятся данные с графика
            String databaseName = "GraphData/dataBase";
            if (isWindows()) {
                databaseName = "GraphData\\dataBase";
            }
            out.flush(dataFilter.getData(image.getPixelMap(), image.getImage()),
                    databaseName + (in.getName().replace(".jp?g", "")) + ".txt");


            double proccTimeEnd = java.lang.System.nanoTime() / 1000000000.0;
            java.lang.System.out.println("Processing time - " + (proccTimeEnd - proccTimeBegin));
            java.lang.System.out.println("Complete ");
        }
    }
}
