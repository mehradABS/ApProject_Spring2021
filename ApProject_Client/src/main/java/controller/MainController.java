package controller;

import auth.AuthToken;
import events.Event;
import events.OnlineEvent;
import network.EventSender;
import network.SocketEventSender;
import responses.Response;
import responses.visitors.ConnectionResponseVisitor;
import responses.visitors.ResponseVisitor;
import util.Loop;
import view.FatherFrame;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;


public class MainController implements ConnectionResponseVisitor {

    private final Loop loop;
    private EventSender eventSender;
    private final List<Event> events;
    private final HashMap<String, ResponseVisitor> visitors;
    private final AuthToken authToken;

    public MainController(){
        List<Event> events1;
        authToken = new AuthToken();
        visitors = new HashMap<>();
        visitors.put("ConnectionResponseVisitor", this);
        events = new LinkedList<>();
        loop = new Loop(10, this::sendEvent);
    }

    public MainController(Socket socket) throws IOException {
        this();
        eventSender = new SocketEventSender(socket);
    }

    public void start(){
        if(OfflineController.IS_ONLINE) {
            loop.start();
        }
        new FatherFrame(this::addEvent, visitors, authToken).initialize();
    }

    public void addEvent(Event event) {
        if ("I'm offline".equals(event.getVisitorType())) {
            close();
        } else if ("OnlineEventVisitor".equals(event.getVisitorType())) {
            event.setAuthToken(0);
            ((OnlineEvent)event).setClientId(OfflineController.getCurrentUser().getId());
            synchronized (events) {
                events.add(0, event);
            }
            restart(((OnlineEvent)event).getSocket());
        } else {
            event.setAuthToken(authToken.getAuthToken());
            synchronized (events) {
                events.add(event);
            }
        }
    }

    public void sendEvent(){
        List<Event> temp;
        synchronized (events) {
            temp = new LinkedList<>(events);
            events.clear();
        }
        for (Event event : temp) {
            try {
                Response response = eventSender.sendEvent(event);
                if(response != null) {
                    response.visit(visitors.get(response.getVisitorType()));
                }
                if("OnlineEventVisitor".equals(event.getVisitorType())){
                    for (Event e: temp) {
                        e.setAuthToken(authToken.getAuthToken());
                    }
                }
            }
            catch (NoSuchElementException e){
                OfflineController.changeConnectionState();
                OfflineController.changeConnectionStateToOffline(this::addEvent);
                return;
            }
        }
    }

    public void close(){
        loop.stop();
        eventSender.close();
    }

    public void restart(Socket socket){
        try {
            eventSender = new SocketEventSender(socket);
            loop.restart();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void getInfo(long token) {
        authToken.setAuthToken(token);
        for (OnlinePanels panels: OfflineController.ONLINE_PANELS) {
            panels.changeState();
        }
    }
}