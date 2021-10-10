package app.explore.watchProfile;

import models.Report;
import models.Request;
import models.auth.User;
import controller.ClientHandler;
import controller.MainController;
import controller.log.Log;
import db.RequestDB;
import events.visitors.watchProfile.WatchProfileEventVisitor;
import network.ImageSender;
import resources.Texts;
import responses.Response;
import responses.watchProfile.LoadInfoResponse;
import responses.watchProfile.WatchProfileResponse;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

public class WatchProfileController extends MainController
    implements WatchProfileEventVisitor {

    private static final Object REPORT_LOCK = new Object();
    private final ClientHandler clientHandler;

    public WatchProfileController(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public Response loadInfo(int userId){
        LoadInfoResponse response = new LoadInfoResponse();
        String[] info = new String[10];
        try {
            User user = context.getUsers().get(userId);
            User current = context.getUsers().get(clientHandler.getClientId());
            //
            String follow;
            String Block;
            String lastSeen;
            String message = "false";
            String mute = "false";
            //
            if(user.getAccount().getWhoCanSeeLastSeen().equals("nobody")){
                lastSeen = "lastSeenRecently";
            }
            else if(user.getAccount().getWhoCanSeeLastSeen().equals("allUsers")){
                if(isOnline(userId)){
                    lastSeen = "online";
                }
                else{
                    lastSeen = user.getAccount().getLastSeen().getMonth().toString()
                            + ", " + user.getAccount().getLastSeen().getDayOfMonth() + " -- " +
                            user.getAccount().getLastSeen().getHour()
                            + ": "+user.getAccount().getLastSeen().getMinute();
                }
            }
            else{
                if(current.getFollowerUsername().contains(user.getId())){
                    if(isOnline(userId)){
                        lastSeen = "online";
                    }
                    else{
                        lastSeen = user.getAccount().getLastSeen().getMonth().toString()
                                +", "+user.getAccount().getLastSeen().getDayOfMonth()+" -- "+
                                user.getAccount().getLastSeen().getHour()
                                +": "+user.getAccount().getLastSeen().getMinute();
                    }
                }
                else{
                    lastSeen = "lastSeenRecently";
                }
            }
            if (current.getFollowingUsername().contains(user.getId())) {
                follow = Texts.UNFOLLOW;
            } else {
                if (user.getAccount().getPrivacy().equals("public")) {
                    follow = Texts.FOLLOW;
                } else {
                    follow = Texts.REQUEST;
                }
            }
            if (current.getBlackUsername().
                    contains(user.getId())) {
                Block = "unblock";
            } else {
                Block = "block";
            }
            boolean four = follow.equals("unfollow") && !user.getBlackUsername().
                    contains(clientHandler.getClientId())
                    && !current.getBlackUsername()
                    .contains(user.getId());
            if (four) {
                message = "true";
            }
            boolean mute1 = current.getSilentUsername()
                    .contains(user.getId());
            if(!mute1){
                mute = "true";
            }
            info[0] = lastSeen;
            info[1] = follow;//follow,unfollow,request
            info[2] = Block;//block,unblock
            info[3] = message;//true,false
            info[4] = mute;//true,false
            info[5] = user.getBiography();//bio
            info[6] = user.getAccount().getUsername();//username
            info[7] = String.valueOf(user.getFollowerUsername().size());
            info[8] = String.valueOf(user.getFollowingUsername().size());
            File file = new File("src\\Save\\images\\"+
                    (user.getId())
                    +"\\profile\\profile100.png");
            if(file.exists()){
                info[9] = ImageSender.encodeImage("src\\Save\\images\\" +
                        (user.getId())
                        +"\\profile\\profile100.png");
            }
            else {
                info[9] = "def";
            }
            response.setInfo(info);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return response;
    }

    public Response follow(int userId) {
        WatchProfileResponse response = new WatchProfileResponse();
        try {
            synchronized (User.LOCK){
                User current = context.getUsers().get(clientHandler.getClientId());
                User user = context.getUsers().get(userId);
                current.getFollowingUsername().add(user
                        .getId());
                user.getFollowerUsername().add(clientHandler.getClientId());
                user.getSystemMessages().add(current.getAccount()
                        .getUsername() + " Followed you   " +
                        LocalDateTime.now());
                Log log = new Log("followed someone",LocalDateTime.now(),2,
                        clientHandler.getClientId());
                Log.log(log);
                context.getUsers().set(user);
                context.getUsers().set(current);
                response.setF(!user.getBlackUsername().
                        contains(clientHandler.getClientId())
                        && !current.getBlackUsername()
                        .contains(user.getId()));
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        response.setAnswer("follow");
        return response;
    }

    public Response unfollow(int userId) {
        WatchProfileResponse response = new WatchProfileResponse();
        try {
            synchronized (User.LOCK) {
                User current = context.getUsers().get(clientHandler.getClientId());
                User user = context.getUsers().get(userId);
                current.getFollowingUsername().remove(
                        user.getId());
                for (User.ListName list : current.getFollowingsList()) {
                    list.getUsernames().remove(user.getId());
                }
                user.getFollowerUsername().remove(clientHandler.getClientId());
                user.getSystemMessages().add(current.getAccount()
                        .getUsername() + " unFollowed you   " +
                        LocalDateTime.now());
                Log log = new Log("unfollowed someone", LocalDateTime.now(), 2,
                        clientHandler.getClientId());
                Log.log(log);
                context.getUsers().set(current);
                context.getUsers().set(user);
                response.setF(current.getAccount().getPrivacy().equals("private"));
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        response.setAnswer("unfollow");
        return response;
    }

    public Response request(int userId){
        try {
            synchronized (User.LOCK) {
                User current = context.getUsers().get(clientHandler.getClientId());
                User user = context.getUsers().get(userId);
                Request.setId_counter(((RequestDB) context.getRequests()).getId_Counter());
                Request request = new Request(current.getAccount().getUsername() +
                        "  wants follow  "
                        + user.getAccount().getUsername()
                        + LocalDateTime.now(), current.getId());
                Log log = new Log("send a request", LocalDateTime.now(), 2,
                        clientHandler.getClientId());
                Log.log(log);
                ((RequestDB) context.getRequests()).setId_Counter(Request.getId_counter());
                context.getRequests().set(request);
                user.getReqFromMeId().add(request.getId());
                context.getUsers().set(user);
                current.getMyReqId().add(request.getId());
                context.getUsers().set(current);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        WatchProfileResponse response = new WatchProfileResponse();
        response.setAnswer("request");
        return response;
    }

    public Response report(int userId) {
        WatchProfileResponse response = new WatchProfileResponse();
        try {
            synchronized (REPORT_LOCK) {
                Report rp = context.getProgram().getReports();
                rp.getFirstId().add(clientHandler.getClientId());
                rp.getLastId().add(userId);
                context.getProgram().setReports(rp);
                Log log = new Log("report someone", LocalDateTime.now(), 2,
                        clientHandler.getClientId());
                Log.log(log);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        response.setAnswer("report");
        return response;
    }

    public Response unblock(int userId) {
        WatchProfileResponse response = new WatchProfileResponse();
        try {
            synchronized (User.LOCK) {
                User current = context.getUsers().get(clientHandler.getClientId());
                User user = context.getUsers().get(userId);
                current.getBlackUsername().remove(user.getId());
                context.getUsers().set(current);
                user.getSystemMessages().add(
                        current.getAccount().getUsername() + " unBlocked you   " +
                                LocalDateTime.now());
                Log log = new Log("unblocked someone", LocalDateTime.now(), 2,
                        clientHandler.getClientId());
                Log.log(log);
                response.setF(!user.getBlackUsername().contains(current.getId())
                        && current.getFollowingUsername().contains(user.getId()));
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        response.setAnswer("unblock");
        return response;
    }

    public Response block(int userId) {
        WatchProfileResponse response = new WatchProfileResponse();
        try {
            synchronized (User.LOCK) {
                User current = context.getUsers().get(clientHandler.getClientId());
                User user = context.getUsers().get(userId);
                current.getBlackUsername().add(user.getId());
                context.getUsers().set(current);
                user.getSystemMessages().add(
                        current.getAccount()
                                .getUsername() + " Blocked you   " +
                                LocalDateTime.now());
                Log log = new Log("blocked someone", LocalDateTime.now(), 2,
                        clientHandler.getClientId());
                Log.log(log);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        response.setAnswer("block");
        return response;
    }

    public Response mute(int userId) {
        WatchProfileResponse response = new WatchProfileResponse();
        try {
            synchronized (User.LOCK) {
                User current = context.getUsers().get(clientHandler.getClientId());
                current.getSilentUsername().add(userId);
                Log log = new Log("mute someone", LocalDateTime.now(), 2,
                        clientHandler.getClientId());
                Log.log(log);
                context.getUsers().set(current);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        response.setAnswer("mute");
        return response;
    }

    public Response unMute(int userId) {
        WatchProfileResponse response = new WatchProfileResponse();
        try {
            synchronized (User.LOCK) {
                User current = context.getUsers().get(clientHandler.getClientId());
                current.getSilentUsername()
                        .remove(userId);
                Log log = new Log("unMute someone", LocalDateTime.now(), 2,
                        clientHandler.getClientId());
                Log.log(log);
                context.getUsers().set(current);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        response.setAnswer("unMute");
        return response;
    }
}