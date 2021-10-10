package network;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

public class ImageSender {
    public static String encodeImage(String imageAddress){
        File file = new File(imageAddress);
        try {
            FileInputStream imageInFile = new FileInputStream(file);
            byte[] imageData = new byte[(int) file.length()];
            int i = imageInFile.read(imageData);
            String imageDataString = Base64.getEncoder().encodeToString(imageData);
            imageInFile.close();
            return imageDataString;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
