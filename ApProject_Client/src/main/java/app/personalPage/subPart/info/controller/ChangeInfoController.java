package app.personalPage.subPart.info.controller;

import controller.OfflineController;
import events.info.ChangeInfoEvent;
import resources.Texts;

import java.io.IOException;


public class ChangeInfoController extends OfflineController {
    public void changeInfo(String type, ChangeInfoEvent changeInfoEvent){
        switch (type) {
            case Texts.BIRTH ->
                    CURRENT_USER.setBirth(changeInfoEvent.getBirth());
            case Texts.EMAIL_INFO_PANEL ->
                    CURRENT_USER.setEmailAddress(changeInfoEvent.getEmail());
            case Texts.BIOGRAPHY_INFO_PANEL ->
                    CURRENT_USER.setBiography(changeInfoEvent.getEmail());
            case Texts.FIRSTNAME_INFO_PANEL ->
                    CURRENT_USER.setFirstname(changeInfoEvent.getFirstname());
            case Texts.LASTNAME_INFO_PANEL ->
                    CURRENT_USER.setLastname(changeInfoEvent.getLastname());
            case Texts.USERNAME_INFO_PANEL ->
                    CURRENT_USER.getAccount().setUsername(changeInfoEvent.getUsername());
            case Texts.PHONE_INFO_PANEL ->
                    CURRENT_USER.setPhoneNumber(changeInfoEvent.getPhone());
        }
        try {
            context.getUsers().set(CURRENT_USER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
