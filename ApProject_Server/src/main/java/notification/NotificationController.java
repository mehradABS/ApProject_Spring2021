package notification;


import controller.ClientHandler;
import controller.MainController;
import controller.log.Log;
import events.notofication.NotifEvent;
import events.visitors.notification.NotifEventVisitor;
import models.Request;
import models.auth.User;
import responses.Response;
import responses.notification.NotifResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class NotificationController extends MainController implements NotifEventVisitor {

    private final ClientHandler clientHandler;

    public NotificationController(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public Response getEvent(NotifEvent event){
        try {
            if("loadOtherReq".equals(event.getEvent())){
                NotifResponse response = new NotifResponse();
                response.setInfo(loadOtherRequests());
                response.setAnswer(event.getEvent());
                return response;
            }
            else if("loadMyReq".equals(event.getEvent())){
                NotifResponse response = new NotifResponse();
                response.setInfo(loadMyRequest());
                response.setAnswer(event.getEvent());
                return response;
            }
            else if("loadSystemMessages".equals(event.getEvent())){
                NotifResponse response = new NotifResponse();
                response.setInfo(loadSystemMessages());
                response.setAnswer(event.getEvent());
                return response;
            }
            else if("accept".equals(event.getEvent())){
                acceptReq(event.getReqId());
            }
            else if("delete".equals(event.getEvent())){
                rejectWithoutMessage(event.getReqId());
            }
            else if("reject".equals(event.getEvent())){
                rejectWithMessage(event.getReqId());
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public List<String[]> loadSystemMessages() throws IOException {
        List<String[]> strings = new LinkedList<>();
        User current = context.getUsers().get(clientHandler.getClientId());
        for (String s: current.getSystemMessages()) {
             strings.add(new String[]{messageHandling(s),"1"});
        }
        return strings;
    }

    public List<String[]> loadMyRequest() throws IOException {
        List<String[]> strings = new LinkedList<>();
        User current = context.getUsers().get(clientHandler.getClientId());
        for (Integer id:current.getMyReqId()) {
            Request req = context.getRequests().get(id);
            String p = messageHandling(req.getText());
            if(req.isAnswered()){
                if(req.isAccepted()){
                    p = p+": \naccepted";
                }
                else if(req.isSystemTellUser()){
                    p = p+": \nrejected";
                }
            }
            strings.add(new String[]{p, "1"});
        }
        return strings;
    }

    public List<String[]> loadOtherRequests() throws IOException {
        List<String[]> strings = new LinkedList<>();
        User current = context.getUsers().get(clientHandler.getClientId());
        for (Integer id:current.getReqFromMeId()) {
            Request req = context.getRequests().get(id);
            strings.add(new String[]{messageHandling(req.getText()), String.valueOf(id)});
        }
        return strings;
    }

    public String messageHandling(String s){
        StringBuilder ss = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == '.'){
                break;
            }
            else if(s.charAt(i) != 'T'){
                ss.append(s.charAt(i));
            }
            else{
                ss.append(' ');
            }
        }
        return ss.toString();
    }

    public void acceptReq(int reqId) throws IOException {
        synchronized (User.LOCK) {
            User user = context.getUsers().get(clientHandler.getClientId());
            Request req = context.getRequests().get(reqId);
            User current = context.getUsers().get(req.getUserId());
            current.getFollowingUsername().add(user
                    .getId());
            user.getFollowerUsername().add(req.getUserId());
            user.getSystemMessages().add(current.getAccount()
                    .getUsername() + " accepted your request    " +
                    LocalDateTime.now());
            Log log = new Log("followed someone", LocalDateTime.now(), 2,
                    clientHandler.getClientId());
            Log.log(log);
            req.setAnswered(true);
            req.setAccepted(true);
            req.setSystemTellUser(true);
            context.getRequests().set(req);
            user.getReqFromMeId().remove(reqId);
            context.getUsers().set(current);
            context.getUsers().set(user);
        }
    }

    public void rejectWithMessage(int reqId) throws IOException {
        synchronized (User.LOCK) {
            User current = context.getUsers().get(clientHandler.getClientId());
            Request req = context.getRequests().get(reqId);
            req.setAnswered(true);
            req.setAccepted(false);
            req.setSystemTellUser(true);
            context.getRequests().set(req);
            current.getReqFromMeId().remove(reqId);
            context.getUsers().set(current);
        }
    }

    public void rejectWithoutMessage(int reqId) throws IOException {
        synchronized (User.LOCK) {
            User current = context.getUsers().get(clientHandler.getClientId());
            Request req = context.getRequests().get(reqId);
            req.setAnswered(true);
            req.setAccepted(false);
            req.setSystemTellUser(false);
            context.getRequests().set(req);
            current.getReqFromMeId().remove(reqId);
            context.getUsers().set(current);
        }
    }
}