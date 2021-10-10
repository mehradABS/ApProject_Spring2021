package app.auth.controller;

import controller.OfflineController;
import db.UserDB;

import java.io.IOException;

public class ProfileImageController extends OfflineController {

    public String checkFile (String filePath, int profileScaledWidth
    , int profileScaledHeight) throws IOException{
        if (filePath.endsWith(".png")) {
            ((UserDB) context.getUsers()).saveProfile(
                    CURRENT_USER.getId()
                    ,filePath, profileScaledWidth,
                    profileScaledHeight);
            return ((UserDB) context.getUsers()).loadProfile
                    (CURRENT_USER.getId(),
                            profileScaledHeight);
        }
        else {
            return "bad image";
        }
    }
}