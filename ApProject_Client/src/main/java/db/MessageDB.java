package db;



import controller.fileHandling.ImageResizer;
import models.messages.OComment;
import models.messages.OMessage;
import models.messages.OTweet;
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

public class MessageDB<T extends OMessage> {

    public T get(int id, String type) throws IOException {
        String path;
        switch (type) {
            case "tweet" -> {
                path = Paths.TWEET_PATH + id
                        + ".json";
                FileReader fileReader = new FileReader(path);
                return gson.fromJson(
                        fileReader, (Type) OTweet.class);
            }
            case "comment" -> {
                path = Paths.COMMENT_PATH + id
                        + ".json";
                FileReader fileReader = new FileReader(path);
                return gson.fromJson(
                        fileReader, (Type) OComment.class);
            }
            case "message" -> {
                path = Paths.MESSAGE_PATH + id
                        + ".json";
                FileReader fileReader = new FileReader(path);
                return gson.fromJson(
                        fileReader, (Type) OMessage.class);
            }
        }
        return null;
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
        FileWriter fileWriter = new FileWriter
                (path,false);
        gson.toJson(message,fileWriter);
        fileWriter.flush();
        fileWriter.close();
    }

    public void setMessageImage(int tweetId, String lastPath, int scaledWidth
            , int scaledHeight) throws IOException {
        String outputImagePath = Paths.TWEET_IMAGE_PATH + tweetId + ".png";
        ImageResizer.resize
                (lastPath, outputImagePath, scaledWidth, scaledHeight);
    }

    public int getAll(String type) throws IOException {
        String path;
        path = "src\\Save\\messages";
        File file2 = new File(path);
        int idCounter = 1;
        for (File TweetFile : Objects.requireNonNull(file2.listFiles())) {
            FileReader fileReader = new FileReader(TweetFile);
            OMessage message = gson.fromJson(
                    fileReader, OMessage.class);
            if(message.getId() > idCounter){
                idCounter = message.getId();
            }
            fileReader.close();
        }
        return idCounter;
    }


    public String loadMessageImage(int id){
        String path = Paths.TWEET_IMAGE_PATH + id +".png";
        File file = new File(path);
        if(file.exists()){
            return path;
        }
        else{
            return "null";
        }
    }

    public void deleteMessageImage(int id){
        String path = Paths.TWEET_IMAGE_PATH + id +".png";
        File file = new File(path);
        if(file.exists()){
            if(file.delete()){
                System.out.println("deleted");
            }
        }
    }

    public void delete(int id, String type) {

    }
}