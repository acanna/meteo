package Main;

import java.lang.System;

public class OSType {
    public static boolean isWindows() {

        String os = System.getProperty("os.name").toLowerCase();
        //windows
        return (os.contains("win"));

    }
}


