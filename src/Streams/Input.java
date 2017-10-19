package Streams;


import java.io.*;
import java.util.LinkedList;

public class Input { // TODO: Сообщает какие картинки читать(перепишу полностью)
    private final String DIR_NAME = ".\\input.txt";  // TODO: Путь до файла с путём до картинок
    private LinkedList<File> fileList = new LinkedList<>(); // TODO: Хранит дискрипторы картинок
    private int size=0; // TODO: Кол-во картинок
    public Input(){
        try {
            BufferedReader buf = new BufferedReader(new FileReader(new File(DIR_NAME))); // TODO: Читам файл с путём до картинок
            File dir = new File(buf.readLine());
            addAllImages( dir ); // TODO: Рекурсивно добавляем все изображения в папке из файла
            size = fileList.size(); // TODO: Кол-во изображений
            if( size == 0 )
                throw new IOException();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void addAllImages(File dir){ // TODO: Реккурсивно обходим папки и собираем дискрипторы картинок
            for (File file : dir.listFiles())
                if (file.isDirectory())
                    addAllImages(file);
                else
                    fileList.addLast(file);

    }
    public String next(){ // TODO: Возвращает след картинку для обработки
            return fileList.pollFirst().getAbsolutePath();
    }
    public int getSize(){
        return size;
    }
}
