package app.setting;

import controller.ClientHandler;
import controller.MainController;
import db.UserDB;
import events.setting.SettingEvent;
import events.visitors.setting.SettingPanelEventVisitor;
import models.auth.User;
import responses.Response;
import responses.setting.SettingResponse;

import java.io.IOException;

public class SettingController extends MainController implements SettingPanelEventVisitor {

    private final ClientHandler clientHandler;

    public SettingController(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    @Override
    public Response getEvent(SettingEvent event) {
        try {
            if("removeAccount".equals(event.getEvent())){
                removeAccount();
            }
            else if("getInfo".equals(event.getEvent())){
                SettingResponse response = new SettingResponse();
                response.setInfo(loadInfo());
                response.setUser(context.getUsers().get(clientHandler.getClientId()));
                return response;
            }
            else if("lastSeen".equals(event.getEvent())){
                setLastSeen(event.getLastSeen());
                SettingResponse response = new SettingResponse();
                response.setInfo(null);
                response.setUser(context.getUsers().get(clientHandler.getClientId()));
                return response;
            }
            else if("account".equals(event.getEvent())){
                setActivate(event.isActivate());
                SettingResponse response = new SettingResponse();
                response.setInfo(null);
                response.setUser(context.getUsers().get(clientHandler.getClientId()));
                return response;
            }
            else if("privacy".equals(event.getEvent())){
                setPrivacy();
                SettingResponse response = new SettingResponse();
                response.setInfo(null);
                response.setUser(context.getUsers().get(clientHandler.getClientId()));
                return response;
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public String[] loadInfo() throws IOException {
        String[] info = new String[3];
        User current = context.getUsers().get(clientHandler.getClientId());
        info[0] = current.getAccount().getPrivacy();
        info[1] = current.getAccount().getWhoCanSeeLastSeen();
        info[2] = String.valueOf(current.getAccount().isActive());
        return info;
    }

    public void setPrivacy() throws IOException {
        User current = context.getUsers().get(clientHandler.getClientId());
        if(current.getAccount().getPrivacy().equals("public")){
            current.getAccount().setPrivacy("private");
        }
        else{
            current.getAccount().setPrivacy("public");
        }
        context.getUsers().set(current);
    }

    public void setLastSeen(String lastSeen) throws IOException {
        synchronized (User.LOCK) {
            User current = context.getUsers().get(clientHandler.getClientId());
            current.getAccount().setWhoCanSeeLastSeen(lastSeen);
            context.getUsers().set(current);
        }
    }

    public void setActivate(boolean activate) throws IOException {
        synchronized (User.LOCK) {
            User current = context.getUsers().get(clientHandler.getClientId());
            current.getAccount().setActive(activate);
            context.getUsers().set(current);
        }
    }

    public void removeAccount() throws IOException {
        synchronized (User.LOCK) {
            User current = context.getUsers().get(clientHandler.getClientId());
            current.getAccount().setUsername("deleted Account");
            current.getAccount().setPassword("");
            current.setEmailAddress("-1");
            current.setPhoneNumber("ss");
            current.getAccount().setActive(false);
            ((UserDB) context.getUsers()).deleteProfileImage(clientHandler.getClientId());
            context.getUsers().set(current);
        }
    }
}
