package db;

import models.OChat;
import resources.Paths;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ChatDB implements DBSet<OChat>{

    @Override
    public OChat get(int id) throws IOException {
        File file5 = new File(Paths.CHAT_PATH+id+".json");
        FileReader fileReader=new FileReader(file5);
        OChat chat = gson.fromJson(fileReader, OChat.class);
        fileReader.close();
        return chat;
    }


    @Override
    public void set(OChat chat) throws IOException {
        FileWriter fileWriter = new FileWriter(Paths.CHAT_PATH+chat.getId()
                +".json",false);
        gson.toJson(chat,fileWriter);
        fileWriter.flush();
        fileWriter.close();
    }

    @Override
    public void delete(int id) {
        File file5 = new File(Paths.CHAT_PATH+ id +".json");
        if(file5.delete()){
            System.out.println("deleted");
        }
    }

    public String loadChatImage(int id){
        String path = Paths.CHAT_IMAGE_PATH + id + ".png";
        File file = new File(path);
        if(!file.exists()){
            path = Paths.CHAT_DEFAULT_IMAGE_PATH;
        }
        return path;
    }
}