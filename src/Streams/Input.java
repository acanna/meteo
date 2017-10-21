package Streams;


import Filters.BrokenImage;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

//   Сообщает какие картинки читать
public class Input {
    // Путь до каталога с картинками
    private final String DIR_NAME = "GraphImages";
    //  Имя следующего изображения для обработки
    private String fileName = "";
    private String fileOutPath = "";
    private String dirName = "";
    //  Хранит дискрипторы картинок
    private LinkedList<File> fileList = new LinkedList<>();
    //  Кол-во картинок
    private int size = 0;

    public Input() {
        try {
            // Дискриптор папки с картинками
            File dir = new File(DIR_NAME);
            //  Рекурсивно добавляем все изображения в папке из файла
            addAllImages(dir);
            //  Кол-во изображений
            size = fileList.size();
            if (size == 0)
                throw new IOException();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //  Реккурсивно обходит папки и собирает дискрипторы картинок
    private void addAllImages(File dir) {
        for (File file : dir.listFiles())
            if (file.isDirectory())
                addAllImages(file);
            else
                fileList.addLast(file);

    }

    //  Возвращает след картинку для обработки

    public String next() throws BrokenImage {
        fileName = fileList.getFirst().getAbsolutePath().split("GraphImages")[1];
        dirName = fileList.getFirst().getAbsolutePath().split("GraphImages")[1].split("\\\\")[1];
        fileOutPath = fileList.getFirst().getAbsolutePath()
                .replace("GraphImages","GraphData")
                .replace(".jpg",".txt");
        String[] dirs = fileOutPath.split("\\\\" );
        for(int i =1 ; i < dirs.length ; ++i){
            new File(fileOutPath.split( dirs[i] )[0]).mkdir();
        }
        // Исключение если имя файла не заканчивается на .jpg
        String str[] = fileList.getFirst().getName().split("\\.");
        if(! str[str.length - 1].equals("jpg")) {
            System.out.println("\"" + fileName + "\"");
            fileList.pollFirst();
            throw new BrokenImage("Неправильный формат файла, используйте jpg");
        }
        //--- TODO: Вставь сюда замену пути fileName и fileOutPath и dirName
        //----------
        return fileList.pollFirst().getAbsolutePath();
    }

    public int getSize() {
        return size;
    }
    public String getName(){ return fileName;}
    public String getPath(){ return fileOutPath;}
    public String getDir(){ return dirName;}
}
