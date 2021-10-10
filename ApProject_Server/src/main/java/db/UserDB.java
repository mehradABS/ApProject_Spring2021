package db;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gson.Deserializer;
import gson.Serializer;
import models.auth.OUser;
import models.auth.User;
import controller.fileHandling.ImageResizer;
import network.ImageReceiver;
import resources.Paths;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class UserDB implements DBSet<User>{

    private static final Object LOCK = new Object();

    Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(OUser.class, new Deserializer<>())
            .registerTypeAdapter(OUser.class, new Serializer<>())
            .create();

    @Override
    public User get(int id) throws IOException {
        synchronized (LOCK) {
            File file = new File(Paths.USER_PATH + id + ".json");
            FileReader fileReader = new FileReader(file);
            User user = (User) gson.fromJson(fileReader, OUser.class);
            fileReader.close();
            return user;
        }
    }

    @Override
    public List<User> getAll() throws IOException {
        List<User> users = new LinkedList<>();
        synchronized (LOCK) {
            File file = new File(Paths.USER_PATH_GET_ALL);
            for (File UserFile : Objects.requireNonNull(file.listFiles())) {
                FileReader fileReader = new FileReader(UserFile);
                User user = (User) gson.fromJson(fileReader, OUser.class);
                users.add(user);
                fileReader.close();
            }
        }
        return users;
    }

    @Override
    public void set(User user) throws IOException {
        synchronized (LOCK) {
            FileWriter fileWriter = new FileWriter(Paths.USER_PATH + user.
                    getId() + ".json", false);
            gson.toJson(user, OUser.class, fileWriter);
            fileWriter.flush();
            fileWriter.close();
        }
    }

    @Override
    public void setIDCounter(int idCounter) throws IOException {
        FileWriter fileWriter = new FileWriter(Paths.USER_PATH_ID_COUNTER,
                false);
        gson.toJson(idCounter, fileWriter);
        fileWriter.flush();
        fileWriter.close();
    }

    @Override
    public int getIDCounter() throws IOException {
        int id = 0;
        File file7 = new File(Paths.USER_PATH_ID_COUNTER);
        if (file7.exists()) {
            FileReader fileReader = new FileReader(file7);
            id = gson.fromJson(fileReader, Integer.class);
            fileReader.close();
        }
        return id;
    }

    @Override
    public void delete(int id) {
        File file = new File(Paths.USER_PATH + id +".json");
        if(file.delete()){
            System.out.println("deleted");
        }
    }
//TODO

    public void saveProfile(int id, String encodedImage) throws IOException {
       String path = Paths.USER_IMAGE_PATH +
                (id)
                + "\\profile\\profile150.png";
       ImageReceiver.decodeImage(encodedImage, path);
       //
       String outputImagePath2 = Paths.USER_IMAGE_PATH +
               (id)
               +"\\profile\\profile100.png";
       String outputImagePath3 = Paths.USER_IMAGE_PATH +
                (id)
                +"\\profile\\profile60.png";
       ImageResizer.resize
                (path, outputImagePath2, 100, 100);
       ImageResizer.resize
                (path, outputImagePath3, 60, 60);
    }

//TODO

    public void deleteProfileImage(int id){
        String path = Paths.USER_IMAGE_PATH +
                (id)
                +"\\profile";
        File file = new File(path);
        if(file.delete()) {
            System.out.println("deleted");
        }
    }

//TODO

//    public String loadProfile(int id, int scale) {
//
//        String path1 = Paths.USER_IMAGE_PATH +
//               (id)
//               + "\\profile\\profile150.png";
//       String path2 = Paths.USER_IMAGE_PATH +
//               (id)
//               + "\\profile\\profile100.png";
//       String path3 = Paths.USER_IMAGE_PATH +
//               (id)
//               + "\\profile\\profile60.png";
//       File file = new File(path1);
//       if (file.exists()) {
//           switch (scale){
//               case 150: return path1;
//               case 100: return path2;
//               case 60:  return path3;
//           }
//       }
//       else {
//           String defaultPath1 = Paths.USER_DEFAULT_IMAGE_PATH1;
//           String defaultPath2 = Paths.USER_DEFAULT_IMAGE_PATH2;
//           switch (scale){
//               case 60 :
//                   return defaultPath1;
//               case 100: return defaultPath2;
//           }
//       }
//       return null;
//    }

}