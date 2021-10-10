package app.auth.controller;


import controller.OfflineController;
import events.auth.RegistrationEvent;
import models.auth.OUser;
import resources.Texts;

import java.io.IOException;

public class RegisterController extends OfflineController {

    public void signup(int id, RegistrationEvent rgEvent){
        OUser user = new OUser(rgEvent.getFirstname(), rgEvent.getLastname(),
                rgEvent.getBirth().getYear(),
                rgEvent.getBirth().getMonthValue(),
                rgEvent.getBirth().getDayOfMonth(),
                rgEvent.getEmail(), rgEvent.getPhone(), "null",
                rgEvent.getUsername(),id);
        setCurrentUser(user);
        try {
            context.getUsers().set(CURRENT_USER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setBio(String bio){
        if("".equals(bio)){
            bio = Texts.DEFAULT_BIO_TEXT;
        }
        CURRENT_USER.setBiography(bio);
        try {
            context.getUsers().set(CURRENT_USER);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}