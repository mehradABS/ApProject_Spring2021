package network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import events.Event;
import gson.Deserializer;
import gson.Serializer;
import responses.Response;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class SocketResponseSender implements ResponseSender{

    private final Gson gson;
    private final Scanner scanner;
    private final PrintStream printStream;
    private final Socket socket;

    public SocketResponseSender(Socket socket) throws IOException {
        this.socket = socket;
        printStream = new PrintStream(socket.getOutputStream());
        this.scanner = new Scanner(socket.getInputStream());
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Event.class, new Deserializer<>())
                .registerTypeAdapter(Response.class, new Serializer<>())
                .create();
    }

    @Override
    public Event getEvent() {
        String eventString = scanner.nextLine();
        return gson.fromJson(eventString, Event.class);
    }

    @Override
    public void sendResponse(Response response) {
        printStream.println(gson.toJson(response, Response.class));
    }

    @Override
    public void close() {
        scanner.close();
        printStream.close();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}