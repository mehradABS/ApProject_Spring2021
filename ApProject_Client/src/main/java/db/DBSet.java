package db;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import events.Event;
import gson.Deserializer;
import gson.Serializer;

import java.io.IOException;

public interface DBSet<T> {

    GsonBuilder gsonBuilder = new GsonBuilder();
    Gson gson = gsonBuilder.setPrettyPrinting()
            .registerTypeAdapter(Event.class, new Serializer<>())
            .registerTypeAdapter(Event.class, new Deserializer<>()).create();

    T get(int id) throws IOException;

    void set(T t) throws IOException;

    void delete(int id);
}
