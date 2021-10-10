package db;


import controller.fileHandling.ImageResizer;
import models.auth.OUser;
import resources.Paths;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class UserDB implements DBSet<OUser>{


    @Override
    public OUser get(int id) throws IOException {
        File file = new File(Paths.USER_PATH + id + ".json");
        FileReader fileReader = new FileReader(file);
        OUser user = gson.fromJson(fileReader, OUser.class);
        fileReader.close();
        return user;
    }

    @Override
    public void set(OUser user) throws IOException {
        FileWriter fileWriter = new FileWriter(Paths.USER_PATH + user.
                getId() + ".json", false);
        gson.toJson(user, fileWriter);
        fileWriter.flush();
        fileWriter.close();
    }


    @Override
    public void delete(int id) {
        File file = new File(Paths.USER_PATH+id + ".json");
        if(file.delete()){
            System.out.println("deleted");
        }
    }

    public void saveProfile(int id, String lastPath, int scaledWidth
   , int scaledHeight) throws IOException {
       String outputImagePath1 = Paths.USER_IMAGE_PATH+
               (id)
               +"\\profile\\profile150.png";
       String outputImagePath2 = Paths.USER_IMAGE_PATH+
               (id)
               +"\\profile\\profile100.png";
       String outputImagePath3 = Paths.USER_IMAGE_PATH+
                (id)
                +"\\profile\\profile60.png";
       ImageResizer.resize
               (lastPath, outputImagePath1, scaledWidth, scaledHeight);
       ImageResizer.resize
                (lastPath, outputImagePath2, 100, 100);
        ImageResizer.resize
                (lastPath, outputImagePath3, 60, 60);
    }

    public void deleteProfileImage(int id){
        String path = Paths.USER_IMAGE_PATH+
                (id)
                +"\\profile";
        File file = new File(path);
        if(file.delete()){
            System.out.println("deleted");
        }
    }

   public String loadProfile(int id, int scale) {
       String path1 = Paths.USER_IMAGE_PATH +
               (id)
               + "\\profile\\profile150.png";
       String path2 = Paths.USER_IMAGE_PATH +
               (id)
               + "\\profile\\profile100.png";
       String path3 = Paths.USER_IMAGE_PATH +
               (id)
               + "\\profile\\profile60.png";
       File file = new File(path1);
       if (file.exists()) {
           switch (scale){
               case 150: return path1;
               case 100: return path2;
               case 60: return path3;
           }
       }
       else {
           String defaultPath1 = Paths.USER_DEFAULT_IMAGE_PATH1;
           String defaultPath2 = Paths.USER_DEFAULT_IMAGE_PATH2;
           switch (scale){
               case 60 :
                   return defaultPath1;
               case 100:
                   return defaultPath2;
           }
       }
       return null;
    }
}