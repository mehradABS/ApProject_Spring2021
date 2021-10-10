package app.personalPage.subPart.info.controller;


import controller.OfflineController;
import db.UserDB;
import models.auth.OUser;

import java.io.IOException;

public class LoadInfoController extends OfflineController {

    public String loadImage(int profileScaledHeight) {
         return ((UserDB) context.getUsers()).loadProfile
                 (CURRENT_USER.getId(),
                         profileScaledHeight);
    }

    public String[] getInfo() throws IOException {
        OUser user = context.getUsers().get(CURRENT_USER.getId());
        String[] info = new String[2];
        info[0] = user.getBiography();
        info[1] = user.getAccount().getUsername();
        return info;
    }

    public String[] getAllInfo() throws IOException {
        OUser user = context.getUsers().get(CURRENT_USER.getId());
        String[] info = new String[8];
        info[0] = user.getFirstname();
        info[1] = user.getLastname();
        info[2] = user.getAccount().getUsername();
        info[3] = "---";
        info[4] = user.getEmailAddress();
        info[5] = user.getPhoneNumber();
        info[6] = user.getBiography();
        info[7] = user.getBirth().toString();
        return info;
    }
}