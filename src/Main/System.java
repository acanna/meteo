package Main;


import Data.ImageData;
import Filters.BrokenImage;
import Filters.DataFilter;
import Streams.Input;
import Streams.Output;

import java.util.LinkedList;

class System {


  //  Кол-во картинок
  private int size = in.getSize();
  // Список необработанных изображений
  private LinkedList<String> badImage = new LinkedList<>();
  //  Выволняет контроль за обработкой всех изображений
  LinkedList<String> searchData(){
      int index = 0;
      while(index++ < size) {
          try {
              //  Открываем изображение, считывает пиксили
              image.openImage(in.next());
              java.lang.System.out.println("_________________________\nProcessing: \"" + in.getName() + "\"");
              double proccTimeBegin = java.lang.System.nanoTime() / 1000000000.0;
              //  Обработка и вывод в файл
              // getPath - путь к файлу с данными
              out.flush(dataFilter.getData(image.getPixelMap(), image.getImage(),in.getDir()),in.getPath());
            
              double proccTimeEnd = java.lang.System.nanoTime() / 1000000000.0;
              java.lang.System.out.println("Processing time - " + (proccTimeEnd - proccTimeBegin));
              java.lang.System.out.println("COMPLETE");
            }catch (BrokenImage e){
                java.lang.System.err.println(e.getMessage());
                badImage.addLast("    " + in.getName() + "  Причина: " + e.getMessage());
                java.lang.System.err.println("ABORT");
            }
        }
        return badImage;
    }
}