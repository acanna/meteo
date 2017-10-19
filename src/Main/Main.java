package Main;
//  Вызывает System( пока лишний класс)
public class Main {
    public static void main(String[] args) {
        java.lang.System.out.println( "Processing begin\n****************** " );
        System system = new System();
        double workTimeBegin = java.lang.System.nanoTime()/1000000000.0;
        system.searchData();
        double workTimeEnd = java.lang.System.nanoTime()/1000000000.0;
        java.lang.System.out.println( "******************\ndone" );
        java.lang.System.out.println( "Total time - " + (workTimeEnd - workTimeBegin));
    }
}
