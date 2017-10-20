package Streams;


import Filters.Pair;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class Output {
<<<<<<< HEAD
    //  Переводит время из значения в " __:__ "
    private String dataFilter(int time){
        if(time < 10)
=======
    //  TODO: Должна приводить время в другой вид(пока ничего не делает)
    private String dataFilter(int time) {
        if (time < 10)
>>>>>>> 59dab96eff8e814c31e65faf0901f1ecb2afade6
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