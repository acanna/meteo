package Main;


import Data.ImageData;
import Filters.DataFilter;
import Streams.Input;
import Streams.Output;

class System {
    private ImageData image = new ImageData();
    private DataFilter dataFilter = new DataFilter();
    private Input in = new Input();
    private Output out = new Output();
    private int size = in.getSize();

    System() {
    }

    void searchData() {
        int index = 0;
        while (index++ < size) {
            image.openImage(in.next());
            out.flush(dataFilter.getData(image.getPixelMap(), image.getImage()), "dataBase.txt");
        }
    }
}
