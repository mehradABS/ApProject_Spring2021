package controller;

import db.Context;
import events.OfflineEvent;
import events.OnlineEvent;
import models.auth.OUser;
import network.EventListener;
import resources.ServerAddress;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class OfflineController {

    public static boolean IS_ONLINE;

    protected static final Context context = new Context();

    protected static OUser CURRENT_USER;

    public static void setCurrentUser(OUser user){
        CURRENT_USER = user;
    }

    public static final List<OnlinePanels> ONLINE_PANELS
             = new LinkedList<>();

    public static void setCurrentUser(int id){
        try {
            CURRENT_USER = context.getUsers().get(id);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void changeConnectionStateToOffline
            (EventListener eventListener){
        IS_ONLINE = false;
        eventListener.listen(new OfflineEvent());
        for (OnlinePanels panels: ONLINE_PANELS) {
            panels.changeState();
        }
    }

    public static void changeConnectionStateToOnline
            (EventListener eventListener) {
        try {
            Socket socket = new Socket
                    (ServerAddress.MAIN_ADDRESS, ServerAddress.MAIN_PORT);
            OnlineEvent onlineEvent = new OnlineEvent(socket);
            System.out.println("connected");
            eventListener.listen(onlineEvent);
        }
        catch (ConnectException e){
            IS_ONLINE = false;
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void changeConnectionState(){
        IS_ONLINE = !IS_ONLINE;
    }

    public static Context getContext(){
        return context;
    }

    public static OUser getCurrentUser(){
        return CURRENT_USER;
    }
}