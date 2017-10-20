package Streams;


import Filters.Pair;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class Output {

    //  Переводит время из значения в " __:__ "
    private String dataFilter(int time){
        if(time < 10)
            return "0" + time + ":00";
        else
            return time + ":00";
    }

    //  Сохраняет данные в файл
    public void flush(LinkedList<Pair<Integer, Double>> dataList, String dataBaseName) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(new File(dataBaseName)));
            for (Pair<Integer, Double> pair : dataList) {
                out.write(dataFilter(pair.getFirst()) + "-" + pair.getSecond());
                out.newLine();
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}