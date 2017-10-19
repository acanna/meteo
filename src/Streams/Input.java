package Streams;


import java.io.*;
import java.util.LinkedList;
//  TODO: Сообщает какие картинки читать(старая часть, надо переписать)
public class Input {
    // Путь до каталога с картинками
    private final String DIR_NAME = ".\\GraphImages";
    //  Имя следующего изображения для обработки
    private String fileName = "";
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
            // Имя первой картинки
            fileName = fileList.getFirst().getName();
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
        fileName = fileList.getFirst().getName();
        return fileList.pollFirst().getAbsolutePath();
    }
    public int getSize(){
        return size;
    }
    public String getName(){ return fileName;}
}
