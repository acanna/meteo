package Main;


import Data.ImageData;
import Filters.DataFilter;
import Streams.Input;
import Streams.Output;

class System {
  private ImageData image = new ImageData();
  private DataFilter dataFilter = new DataFilter();
  private Input in =new Input(); //  Инициализируем создание списка файлов изображений
  private Output out = new Output();
  private int size = in.getSize(); //  Кол-во картинок
  System(){} //  Хз зачем я его написал, наверное так надо было
  void searchData(){ //  Выволняет контроль за обработкой всех изображений
      int index = 0;
      while(index++ < size) {
          image.openImage(in.next()); //  Открываем изображение
          out.flush( dataFilter.getData(image.getPixelMap(), image.getImage()), ".\\dataBase.txt" ); //  Обработка и вывод в файл
      }
  }
  int getResults(){ return 0;} //  Лишняя
}
