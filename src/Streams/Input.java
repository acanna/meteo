package Streams;


import java.io.*;
import java.util.LinkedList;
//  TODO: Сообщает какие картинки читать(старая часть, надо переписать)
public class Input {
    //  Путь до файла с путём до картинок
    private final String DIR_NAME = ".\\input.txt";
    //  Хранит дискрипторы картинок
    private LinkedList<File> fileList = new LinkedList<>();
    //  Кол-во картинок
    private int size=0;
    public Input(){
        try {
            //  Читам файл с путём до картинок
            BufferedReader buf = new BufferedReader(new FileReader(new File(DIR_NAME)));
            File dir = new File(buf.readLine());
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
            return fileList.pollFirst().getAbsolutePath();
    }
    public int getSize(){
        return size;
    }
}
