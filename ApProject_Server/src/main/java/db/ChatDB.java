package db;


import models.Chat;
import models.auth.User;
import resources.Paths;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class ChatDB implements DBSet<Chat>{

    private static final Object lock = new Object();

    @Override
    public Chat get(int id) throws IOException {
        synchronized (lock) {
            File file5 = new File(Paths.CHAT_PATH + id + ".json");
            FileReader fileReader = new FileReader(file5);
            Chat chat = gson.fromJson(fileReader, Chat.class);
            fileReader.close();
            return chat;
        }
    }

    @Override
    public List<Chat> getAll() throws IOException {
        return null;
    }

    @Override
    public void set(Chat chat) throws IOException {
        synchronized (lock) {
            FileWriter fileWriter = new FileWriter(Paths.CHAT_PATH + chat.getId()
                    + ".json", false);
            gson.toJson(chat, fileWriter);
            fileWriter.flush();
            fileWriter.close();
        }
    }

    @Override
    public void setIDCounter(int id) throws IOException {
        FileWriter fileWriter = new FileWriter(Paths.CHAT_PATH_ID_COUNTER,
                false);
        gson.toJson(id,fileWriter);
        fileWriter.flush();
        fileWriter.close();
    }

    @Override
    public int getIDCounter() throws IOException {
        int id = 0;
        File file8=new File(Paths.CHAT_PATH_ID_COUNTER);
        if(file8.exists()) {
            FileReader fileReader=new FileReader(file8);
            id = gson.fromJson(fileReader, Integer.class);
            fileReader.close();
        }
        return id;
    }

    @Override
    public void delete(int id) {
        File file5 = new File(Paths.CHAT_PATH+id+".json");
        if(file5.delete()){
            System.out.println("deleted");
        }
    }
}
