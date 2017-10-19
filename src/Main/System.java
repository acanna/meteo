package Main;


import Data.ImageData;
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
          //  Открываем изображение, считывает пиксили
          java.lang.System.out.println( "Processing " + in.getName());
          double proccTimeBegin = java.lang.System.nanoTime()/1000000000.0;

          image.openImage(in.next());
          //  Обработка и вывод в файл
          // dataBaseName - путь в который выводятся данные с графика
          out.flush( dataFilter.getData(image.getPixelMap(), image.getImage()),
                  ".\\GraphData\\dataBase" +(in.getName().replace("\\.jp?g","")) + ".txt" );

          double proccTimeEnd = java.lang.System.nanoTime()/1000000000.0;
          java.lang.System.out.println( "Test time - " + (proccTimeEnd - proccTimeBegin));
          java.lang.System.out.println( "Complete " + in.getName());
      }
  }
  //  Лишняя функция
  int getResults(){ return 0;}
}
