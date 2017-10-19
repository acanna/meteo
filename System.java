package Main;


import Data.ImageData;
import Filters.DataFilter;
import Streams.Input;
import Streams.Output;

class System {
  private ImageData image = new ImageData();
  private DataFilter dataFilter = new DataFilter();
  private Input in =new Input(); // TODO: Инициализируем создание списка файлов изображений
  private Output out = new Output();
  private int size = in.getSize(); // TODO: Кол-во картинок
  System(){} // TODO: Хз зачем я его написал, наверное так надо было
  void searchData(){ // TODO: Выволняет контроль за обработкой всех изображений
      int index = 0;
      while(index++ < size) {
          image.openImage(in.next()); // TODO: Открываем изображение
          out.flush( dataFilter.getData(image.getPixelMap(), image.getImage()), ".\\dataBase.txt" ); // TODO: Обработка и вывод в файл
      }
  }
  int getResults(){ return 0;} // TODO: Лишняя
}
