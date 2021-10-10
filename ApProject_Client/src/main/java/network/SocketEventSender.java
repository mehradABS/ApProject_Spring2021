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
import java.util.NoSuchElementException;
import java.util.Scanner;

public class SocketEventSender implements EventSender{

    private final Socket socket;
    private final Gson gson;
    private final PrintStream printStream;
    private final Scanner scanner;

    public SocketEventSender(Socket socket) throws IOException {
        this.socket = socket;
        printStream = new PrintStream(socket.getOutputStream());
        scanner = new Scanner(this.socket.getInputStream());
        gson = new GsonBuilder()
                .registerTypeAdapter(Response.class, new Deserializer<>())
                .registerTypeAdapter(Event.class, new Serializer<>())
                .create();
    }

    @Override
    public Response sendEvent(Event event) throws NoSuchElementException {
        printStream.println(gson.toJson(event, Event.class));
        String responseString = scanner.nextLine();
        return gson.fromJson(responseString, Response.class);
    }

    @Override
    public void close() {
        printStream.close();
        scanner.close();
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
