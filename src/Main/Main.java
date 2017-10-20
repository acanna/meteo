package Main;

import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        java.lang.System.out.println("      [Processing start]");
        System system = new System();
        double workTimeBegin = java.lang.System.nanoTime() / 1000000000.0;
        LinkedList<String> badImagesList = system.searchData();
        double workTimeEnd = java.lang.System.nanoTime() / 1000000000.0;
        java.lang.System.out.println("_______________________\n      [Done]");
        java.lang.System.out.println("Total time - " + (workTimeEnd - workTimeBegin));
        java.lang.System.out.println("Список необработанных изображений: ");
        for(String fName : badImagesList)
            java.lang.System.out.println(fName);
    }
}
