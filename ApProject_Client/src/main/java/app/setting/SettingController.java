package app.setting;

import controller.OfflineController;
import models.auth.OUser;

import java.io.IOException;

public class SettingController extends OfflineController {

    public void setUser(OUser user){
        CURRENT_USER = user;
        try {
            context.getUsers().set(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] loadInfo() throws IOException {
        String[] info = new String[3];
        OUser current = context.getUsers().get(CURRENT_USER.getId());
        info[0] = current.getAccount().getPrivacy();
        info[1] = current.getAccount().getWhoCanSeeLastSeen();
        info[2] = String.valueOf(current.getAccount().isActive());
        return info;
    }

    public void setPrivacy() throws IOException {
        OUser current = context.getUsers().get(CURRENT_USER.getId());
        if(current.getAccount().getPrivacy().equals("public")){
            current.getAccount().setPrivacy("private");
        }
        else{
            current.getAccount().setPrivacy("public");
        }
        context.getUsers().set(current);
        CURRENT_USER = current;
    }

    public void setLastSeen(String lastSeen) throws IOException {
        OUser current = context.getUsers().get(CURRENT_USER.getId());
        current.getAccount().setWhoCanSeeLastSeen(lastSeen);
        context.getUsers().set(current);
        CURRENT_USER = current;
    }

    public void setActivate(boolean activate) throws IOException {
        OUser current = context.getUsers().get(CURRENT_USER.getId());
        current.getAccount().setActive(activate);
        context.getUsers().set(current);
        CURRENT_USER = current;
    }
}
