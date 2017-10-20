package Main;


import Data.ImageData;
import Filters.BrokenImage;
import Filters.DataFilter;
import Streams.Input;
import Streams.Output;

class System {
  private ImageData image = new ImageData();
  private DataFilter dataFilter = new DataFilter();
  //  Инициализируем создание списка файлов изображений
  private Input in =new Input();
  private Output out = new Output();
  //  Кол-во картинок
  private int size = in.getSize();
  //  Хз зачем я его написал, наверное так надо было
  System(){}
  //  Выволняет контроль за обработкой всех изображений
  void searchData(){
      int index = 0;
      while(index++ < size) {
          try {
              //  Открываем изображение, считывает пиксили
              image.openImage(in.next());

              java.lang.System.out.println("_________________________\nProcessing: \"" + in.getName() + "\"");
              double proccTimeBegin = java.lang.System.nanoTime() / 1000000000.0;


              //  Обработка и вывод в файл
              // dataBaseName - путь в который выводятся данные с графика
              out.flush(dataFilter.getData(image.getPixelMap(), image.getImage()),
                      ".\\GraphData\\dataBase" + (in.getName().replace("\\.jpg", "")) + ".txt");


              double proccTimeEnd = java.lang.System.nanoTime() / 1000000000.0;
              java.lang.System.out.println("Processing time - " + (proccTimeEnd - proccTimeBegin));
              java.lang.System.out.println("Complete ");
          }catch (BrokenImage e){
              java.lang.System.out.println(e.getMessage());
              java.lang.System.out.println("Abort");
          }
      }
  }
  //  Лишняя функция
  int getResults(){ return 0;}
}
