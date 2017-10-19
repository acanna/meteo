package Streams;


import java.io.*;
import java.util.LinkedList;

public class Input { //  Сообщает какие картинки читать(перепишу полностью)
    private final String DIR_NAME = ".\\input.txt";  //  Путь до файла с путём до картинок
    private LinkedList<File> fileList = new LinkedList<>(); //  Хранит дискрипторы картинок
    private int size=0; //  Кол-во картинок
    public Input(){
        try {
            BufferedReader buf = new BufferedReader(new FileReader(new File(DIR_NAME))); //  Читам файл с путём до картинок
            File dir = new File(buf.readLine());
            addAllImages( dir ); //  Рекурсивно добавляем все изображения в папке из файла
            size = fileList.size(); //  Кол-во изображений
            if( size == 0 )
                throw new IOException();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void addAllImages(File dir){ //  Реккурсивно обходим папки и собираем дискрипторы картинок
            for (File file : dir.listFiles())
                if (file.isDirectory())
                    addAllImages(file);
                else
                    fileList.addLast(file);

    }
    public String next(){ //  Возвращает след картинку для обработки
            return fileList.pollFirst().getAbsolutePath();
    }
    public int getSize(){
        return size;
    }
}
