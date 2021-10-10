package network;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

public class ImageReceiver {

    public static void decodeImage(String encodedImage, String localImagePath) {
        try {
            byte[] imageByteArray = Base64.getDecoder().decode(encodedImage);
            File file = new File(localImagePath);
            if(!file.exists()){
                if(file.getParentFile().mkdirs()
                        && file.createNewFile()){
                    System.out.println("file created");
                }
            }
            FileOutputStream imageOutFile = new FileOutputStream(localImagePath);
            imageOutFile.write(imageByteArray);
            imageOutFile.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }
}