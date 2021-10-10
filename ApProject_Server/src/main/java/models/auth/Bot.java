package models.auth;

import events.chat.EditMessageEvent;
import events.chat.SendMessageEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.time.LocalDateTime;
import java.util.Arrays;


public class Bot extends User {

    private final String url;
    private final SendMessageEvent event = new EditMessageEvent();
    public static transient MessageSender messageSender;

    public Bot(String firstname, String lastname, int year, int month,
               int day, String emailAddress, String phoneNumber,
               String biography, String username, String password, String url){
        super(firstname, lastname, year, month, day, emailAddress,
                phoneNumber, biography, username, password);
        this.url = url;
    }

    public void receiveMessage(int chatId, String messageTxt, int userId) {
        if(userId != id) {
            new Thread(() -> {
                try {
                    Class<?> mainClass = new URLClassLoader(new URL[]{new URL(url)})
                            .loadClass("Calculator");
                    Method method = mainClass.getMethod("getCommand", String.class,
                            int.class);
                    String answer = (String) method.invoke(null, messageTxt, chatId);
                    if (answer != null) {
                        setSendEventParameter(chatId, answer);
                        messageSender.sendMessage(event, id);
                    }
                } catch (MalformedURLException |
                        ClassNotFoundException |
                        NoSuchMethodException |
                        InvocationTargetException |
                        IllegalAccessException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    public void setSendEventParameter(int chatId, String txt){
        event.setText(txt);
        event.setChatId(chatId);
        event.setLocalDateTime(LocalDateTime.now());
        event.setEncodedImage("null");
    }

    public SendMessageEvent getEvent() {
        return event;
    }
}