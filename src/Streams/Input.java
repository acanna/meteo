package Streams;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class Input {
    private final String DIR_NAME = "input.txt";
    private LinkedList<File> fileList = new LinkedList<>();
    private int size = 0;

    public Input() {
        try {
            BufferedReader buf = new BufferedReader(new FileReader(new File(DIR_NAME)));
            File dir = new File(buf.readLine());
            addAllImages(dir);
            size = fileList.size();
            if (size == 0)
                throw new IOException();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void addAllImages(File dir) {
        for (File file : dir.listFiles())
            if (file.isDirectory())
                addAllImages(file);
            else
                fileList.addLast(file);

    }

    public String next() {
        return fileList.pollFirst().getAbsolutePath();
    }

    public int getSize() {
        return size;
    }
}
