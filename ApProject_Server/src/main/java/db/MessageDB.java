package db;




import models.messages.Comment;
import models.messages.Message;
import models.messages.Tweet;
import network.ImageReceiver;
import resources.Paths;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static db.DBSet.gson;

public class MessageDB<T extends Message>{

    private static final Object LOCK = new Object();


    public T get(int id, String type) throws IOException {
        String path;
        synchronized (LOCK) {
            switch (type) {
                case "tweet" -> {
                    path = Paths.TWEET_PATH + id
                            + ".json";
                    FileReader fileReader = new FileReader(path);
                    return gson.fromJson(
                            fileReader, (Type) Tweet.class);
                }
                case "comment" -> {
                    path = Paths.COMMENT_PATH + id
                            + ".json";
                    FileReader fileReader = new FileReader(path);
                    return gson.fromJson(
                            fileReader, (Type) Comment.class);
                }
                case "message" -> {
                    path = Paths.MESSAGE_PATH + id
                            + ".json";
                    FileReader fileReader = new FileReader(path);
                    return gson.fromJson(
                            fileReader, (Type) Message.class);
                }
            }
            return null;
        }
    }

    public List<T> getAll(String type) throws IOException {
        String path;
        List<T> ts = new LinkedList<>();
        synchronized (LOCK) {
            if (type.equals("tweet")) {
                path = Paths.TWEET_PATH_GET_ALL;
                File file2 = new File(path);
                for (File TweetFile : Objects.requireNonNull(file2.listFiles())) {
                    FileReader fileReader = new FileReader(TweetFile);
                    T tweet = gson.fromJson(
                            fileReader, (Type) Tweet.class);
                    ts.add(tweet);
                    fileReader.close();
                }
            } else if (type.equals("comment")) {
                path = Paths.COMMENT_PATH_GET_ALL;
                File file2 = new File(path);
                for (File TweetFile : Objects.requireNonNull(file2.listFiles())) {
                    FileReader fileReader = new FileReader(TweetFile);
                    T tweet = gson.fromJson(
                            fileReader, (Type) Comment.class);
                    ts.add(tweet);
                    fileReader.close();
                }
            } else {
                path = Paths.MESSAGE_PATH_GET_ALL;
                File file2 = new File(path);
                for (File TweetFile : Objects.requireNonNull(file2.listFiles())) {
                    FileReader fileReader = new FileReader(TweetFile);
                    T tweet = gson.fromJson(
                            fileReader, (Type) Message.class);
                    ts.add(tweet);
                    fileReader.close();
                }
            }
        }
        return ts;
    }

    public void set(T message , String type) throws IOException {
        String path = switch (type) {
            case "message" -> Paths.MESSAGE_PATH + message.getId()
                    + ".json";
            case "tweet" -> Paths.TWEET_PATH + message.getId()
                    + ".json";
            case "comment" -> Paths.COMMENT_PATH + message.getId()
                    + ".json";
            default -> "";
        };
        synchronized (LOCK) {
            FileWriter fileWriter = new FileWriter
                    (path, false);
            gson.toJson(message, fileWriter);
            fileWriter.flush();
            fileWriter.close();
        }
    }

    public void setIDCounter(int idCounter) throws IOException {
        FileWriter fileWriter=new FileWriter(Paths.MESSAGE_PATH_ID_COUNTER,
                false);
        gson.toJson(idCounter,fileWriter);
        fileWriter.flush();
        fileWriter.close();
    }
//TODO

    public void setMessageImage(int id, String encodeImage) {
        String imagePath = Paths.TWEET_IMAGE_PATH + id + ".png";
        ImageReceiver.decodeImage(encodeImage, imagePath);
    }

    public String loadTweetImage(int id){
        String path = Paths.TWEET_IMAGE_PATH+id+".png";
        File file = new File(path);
        if(file.exists()){
            return path;
        }
        else{
            return "null";
        }
    }

    public int getIDCounter() throws IOException {
        File file10 = new File(Paths.MESSAGE_PATH_ID_COUNTER);
        int id = 0;
        if(file10.exists()) {
            FileReader fileReader=new FileReader(file10);
            id = gson.fromJson(fileReader, Integer.class);
            fileReader.close();
        }
        return id;
    }

    public void deleteTweetImage(int id){
        String path = Paths.TWEET_IMAGE_PATH + id +".png";
        File file = new File(path);
        if(file.delete()){
            System.out.println("deleted");
        }
    }

    public void delete(int id, String type) {

    }
}