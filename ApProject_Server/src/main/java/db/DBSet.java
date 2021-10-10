package db;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.auth.User;

import java.io.IOException;
import java.util.List;

public interface DBSet<T> {

    GsonBuilder gsonBuilder = new GsonBuilder();
    Gson gson = gsonBuilder.setPrettyPrinting().create();

    T get(int id) throws IOException;

    List<T> getAll() throws IOException;

    void set(T t) throws IOException;

    void setIDCounter(int idCounter) throws IOException;

    int getIDCounter() throws IOException;

    void delete(int id);
}
