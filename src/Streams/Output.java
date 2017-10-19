package Streams;


import javafx.util.Pair;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class Output {

    private String dataFilter(int time){ // TODO: Должна приводить время в другой вид(пока ничего не делает)
        if(time < 10)
            return "0" + time + ":00";
        else
            return time +":00";
    }
    public void flush(LinkedList<Pair<Integer,Double>> dataList, String dataBaseName) { // TODO: Сохраняет данные в файл
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(new File(dataBaseName)));
            for (Pair<Integer, Double> pair : dataList) {
                out.write(dataFilter(pair.getKey()) + "-" + pair.getValue());
                out.newLine();
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
