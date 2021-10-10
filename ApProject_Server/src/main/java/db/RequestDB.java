package db;


import models.Request;
import resources.Paths;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class RequestDB implements DBSet<Request> {

    private static final Object LOCK = new Object();

    @Override
    public Request get(int id) throws IOException {
        synchronized (LOCK) {
            FileReader fileReader = new FileReader(Paths.REQUEST_PATH
                    + id + ".json");
            Request request = gson.fromJson(fileReader, Request.class);
            fileReader.close();
            return request;
        }
    }

    @Override
    public List<Request> getAll() throws IOException {
        return null;
    }

    @Override
    public void set(Request request) throws IOException {
        synchronized (LOCK) {
            FileWriter fileWriter = new FileWriter(Paths.REQUEST_PATH +
                    request.getId() + ".json",
                    false);
            gson.toJson(request, fileWriter);
            fileWriter.flush();
            fileWriter.close();
        }
    }

    @Override
    public void setIDCounter(int idCounter) throws IOException {

    }

    @Override
    public int getIDCounter() throws IOException {
        return 0;
    }

    @Override
    public void delete(int id) {

    }

    public int getId_Counter() throws IOException {
        File file9=new File(Paths.REQUEST_PATH_ID_COUNTER);
        int id = 0;
        if(file9.exists()) {
            FileReader fileReader=new FileReader(file9);
            id = gson.fromJson(fileReader, Integer.class);
            fileReader.close();

        }
        return id;
    }

    public void setId_Counter(int id) throws IOException {
        FileWriter fileWriter=new FileWriter(Paths.REQUEST_PATH_ID_COUNTER,
                false);
        gson.toJson(id,fileWriter);
        fileWriter.flush();
        fileWriter.close();
    }
}
