package Streams;


import java.io.*;
import java.util.LinkedList;

public class Input {
    // Путь до каталога с картинками
    private final String DIR_NAME = "GraphImages";
    //  Имя следующего изображения для обработки
    private String fileName = "";
    private String fileOutPath = "";
    //  Хранит дискрипторы картинок
    private LinkedList<File> fileList = new LinkedList<>();
    //  Кол-во картинок
    private int size=0;
    public Input(){
        try {
            // Дискриптор папки с картинками
            File dir = new File(DIR_NAME);
            //  Рекурсивно добавляем все изображения в папке из файла
            addAllImages( dir );
            //  Кол-во изображений
            size = fileList.size();
            if( size == 0 )
                throw new IOException();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    //  Реккурсивно обходит папки и собирает дискрипторы картинок
    private void addAllImages(File dir){
            for (File file : dir.listFiles())
                if (file.isDirectory())
                    addAllImages(file);
                else
                    fileList.addLast(file);

    }
    //  Возвращает след картинку для обработки
    public String next(){
        fileName = fileList.getFirst().getAbsolutePath().split("GraphImages")[1];
        fileOutPath = fileList.getFirst().getAbsolutePath()
                .replace("GraphImages","GraphData")
                .replace(".jpg",".txt");
        String[] dirs = fileOutPath.split("\\\\" );
        for(int i =1 ; i < dirs.length ; ++i){
            new File(fileOutPath.split( dirs[i] )[0]).mkdir();
        }
        //--- TODO: Вставь сюда замену пути fileName и fileOutPath
        //----------
        return fileList.pollFirst().getAbsolutePath();
    }
    public int getSize(){
        return size;
    }
    public String getName(){ return fileName;}
    public String getPath(){ return fileOutPath;}
}
