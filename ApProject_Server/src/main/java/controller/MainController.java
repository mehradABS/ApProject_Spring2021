package controller;


import models.auth.User;
import db.Context;

import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;

public class MainController {

    protected static final HashMap<Long, Integer> ONLINE_CLIENTS = new HashMap<>();

    private static final SecureRandom secureRandom = new SecureRandom();

    protected static final Context context = new Context();

    public static long addClient(int id){
        synchronized (ONLINE_CLIENTS){
            long test;
            do {
                test = secureRandom.nextLong();
            } while (ONLINE_CLIENTS.containsKey(test) && test == 0);
            ONLINE_CLIENTS.put(test, id);
            return test;
        }
    }

    public boolean isOnline(int id){
        synchronized (ONLINE_CLIENTS){
            return ONLINE_CLIENTS.containsValue(id);
        }
    }

    public static void removeClient(long auth, int clientId){
        synchronized (ONLINE_CLIENTS){
            ONLINE_CLIENTS.remove(auth);
        }
        synchronized (User.LOCK){
            try {
                User user = context.getUsers().get(clientId);
                user.getAccount().setLastSeen(LocalDateTime.now());
                context.getUsers().set(user);
            } catch (IOException ignored) {

            }
        }
    }
}