package Filters;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

public class ImageFilter {
    Save save;
    BufferedImage img;

    public ImageFilter() {
        try {
            img = ImageIO.read(new File("D:\\Projects\\ITMO\\GraphDetection\\Images\\12200.jpeg"));
            Raster rast = img.getRaster();
            save = new Save(rast.getSampleModel(), rast.getDataBuffer(), new Point());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saVe() {
        try {
            ImageIO.write(img, "jpeg", new File("D:\\Projects\\ITMO\\GraphDetection\\Images\\new12200.jpeg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //--------------

    //-----------
    class Save extends WritableRaster {
        Save(SampleModel sampleModel, DataBuffer dataBuffer, Point origin) {
            super(sampleModel, dataBuffer, origin);
        }
    }


}
