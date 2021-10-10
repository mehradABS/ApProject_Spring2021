package Cli.agent;

import Cli.Chatroom;
import Cli.NewMessage;
import Cli.WatchAnotherPersonalPage;
import Logic.LogicalAgent;
import Models.Chat;
import Models.Log;
import Models.Request;
import Models.User;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedList;

public class WatchAnotherPersonalPageAgent {
    public void WatchAnotherPersonalPage(User user,
                                         LogicalAgent logicalagent
    , WatchAnotherPersonalPage watchAnotherPersonalPage,
                                         ChatroomAgent
                                         chatroomAgent,
                                         Chatroom chatroom,
                                         NewMessageAgent
                                         newMessageAgent,
                                         NewMessage newMessage) throws IOException {
        String follow;
        String Block;
        String lastSeen;
        while (true) {
            if(user.getAccount().getWhoCanSeeLastSeen().equals("nobody")){
                lastSeen="lastSeenRecently";
            }
            else if(user.getAccount().getWhoCanSeeLastSeen().equals("allUsers")){
                if(user.getAccount().getLastSeen()==null){
                    lastSeen="online";
                }
                else{
                    lastSeen=user.getAccount().getLastSeen().toString();
                }
            }
            else{
                if(logicalagent.getProgram().getCurrentUser().getFollowers()
                        .contains(user)){
                    if(user.getAccount().getLastSeen()==null){
                        lastSeen="online";
                    }
                    else{
                        lastSeen=user.getAccount().getLastSeen().toString();
                    }
                }
                else{
                    lastSeen="lastSeenRecently";
                }
            }
            watchAnotherPersonalPage.showingAccount(user.getAccount(),
                    lastSeen);
            watchAnotherPersonalPage.Exit();
            if (logicalagent.getProgram().getCurrentUser().getFollowings().
                    contains(user)) {
                watchAnotherPersonalPage.follow(user.getAccount());
                watchAnotherPersonalPage.unfollowAsk(user.getAccount());
                follow = "u";
            } else {
                if (user.getAccount().getPrivacy().equals("public")) {
                    watchAnotherPersonalPage.followAsk(user.getAccount());
                    follow = "a";
                } else {
                    watchAnotherPersonalPage.followRequest();
                    follow = "r";
                }
            }
            watchAnotherPersonalPage.report(user.getAccount());
            if (logicalagent.getProgram().getCurrentUser().getBlacklist().
                    contains(user)) {
                watchAnotherPersonalPage.unBlock(user.getAccount());
                Block = "u";
            } else {
                watchAnotherPersonalPage.Block(user.getAccount());
                Block = "b";
            }
            boolean four = follow.equals("u") && !user.getBlacklist().
                    contains(logicalagent.getProgram().getCurrentUser())
                    && !logicalagent.getProgram().getCurrentUser().getBlacklist()
                    .contains(user);
            if (four) {
                watchAnotherPersonalPage.sendMessage();
            }
            boolean mute=logicalagent.getProgram().getCurrentUser().getSilentUsers()
                    .contains(user);
            if(mute){
                watchAnotherPersonalPage.unMute();
            }
            else{
                watchAnotherPersonalPage.mute();
            }
            String answer = watchAnotherPersonalPage.GetCommand(four);
            switch (answer) {
                case "0":
                    return;
                case "1":
                    if (follow.equals("u")) {
                        logicalagent.getProgram().getCurrentUser()
                                .getFollowings().remove(user);
                        logicalagent.getProgram().getCurrentUser()
                                .getFollowingUsername().remove(
                                user.getId());
                        for (User.ListName list : logicalagent.getProgram().getCurrentUser()
                                .getFollowingsList()) {
                            list.getList().remove(user);
                            list.getUsernames().remove(user.getId());
                        }
                        user.getFollowers().remove(logicalagent.getProgram()
                                .getCurrentUser());
                        user.getFollowerUsername().remove(logicalagent.getProgram()
                                .getCurrentUser().getId());
                        user.getSystemMessages().add(
                                logicalagent.getProgram().getCurrentUser().getAccount()
                                        .getUsername() + " unFollowed you   " +
                                        LocalDateTime.now());
                        Log log=new Log("unfollowed someone",LocalDateTime.now(),2,
                                logicalagent.getProgram().
                                        getCurrentUser().getAccount().getUsername());
                        Log.log(log);
                        logicalagent.getModelLoader().setUserChanges(
                                logicalagent.getProgram()
                                        .getCurrentUser());
                        logicalagent.getModelLoader().setUserChanges(user);
                    } else if (follow.equals("a")) {
                        logicalagent.getProgram().getCurrentUser()
                                .getFollowings().add(user);
                        logicalagent.getProgram().getCurrentUser()
                                .getFollowingUsername().add(user
                                .getId());
                        logicalagent.getModelLoader().setUserChanges(logicalagent.
                                getProgram()
                                .getCurrentUser());
                        user.getFollowers().add(logicalagent.getProgram()
                                .getCurrentUser());
                        user.getFollowerUsername().add(logicalagent.getProgram()
                                .getCurrentUser().getId());
                        user.getSystemMessages().add(
                                logicalagent.getProgram().getCurrentUser().getAccount()
                                        .getUsername() + " Followed you   " +
                                        LocalDateTime.now());
                        Log log=new Log("followed someone",LocalDateTime.now(),2,
                                logicalagent.getProgram().
                                        getCurrentUser().getAccount().getUsername());
                        Log.log(log);
                        logicalagent.getModelLoader().setUserChanges(user);
                    } else {
                        Request request = new Request(logicalagent.
                                getProgram().getCurrentUser().getAccount()
                                .getUsername() + " wants follow you"
                                + "  "
                                + LocalDateTime.now(), logicalagent.getProgram()
                                .getCurrentUser());
                        Log log=new Log("send a request",LocalDateTime.now(),2,
                                logicalagent.getProgram().
                                        getCurrentUser().getAccount().getUsername());
                        Log.log(log);
                        logicalagent.getModelLoader().setRequestID_counter(Request.getId_counter());
                        logicalagent.getModelLoader().setRequestChanges(request);
                        user.getRequestsFromMe().add(request);
                        user.getReqFromMeId().add(request.getId());
                        logicalagent.getModelLoader().setUserChanges(user);
                        logicalagent.getProgram().getCurrentUser().
                                getMyRequests().add(request);
                        logicalagent.getProgram().getCurrentUser().
                                getMyReqId().add(request.getId());
                        logicalagent.getModelLoader().setUserChanges(
                                logicalagent.getProgram().getCurrentUser());
                    }
                    watchAnotherPersonalPage.finishingProcess();
                    break;
                case "2": {
                    logicalagent.getProgram().getReportsId().put(
                            logicalagent.getProgram().getCurrentUser().getAccount()
                                    .getUsername(), user.getAccount().getUsername());
                    logicalagent.getModelLoader().setReports(logicalagent
                            .getProgram().getReportsId());
                    Log log = new Log("report someone", LocalDateTime.now(), 2,
                            logicalagent.getProgram().
                                    getCurrentUser().getAccount().getUsername());
                    Log.log(log);
                    watchAnotherPersonalPage.finishingProcess();
                    break;
                }
                case "3": {
                    if (Block.equals("u")) {
                        logicalagent.getProgram().getCurrentUser().getBlacklist()
                                .remove(user);
                        logicalagent.getProgram().getCurrentUser()
                                .getBlackUsername().remove(user.getId());
                        logicalagent.getModelLoader().setUserChanges(
                                logicalagent.getProgram().getCurrentUser());
                        user.getSystemMessages().add(
                                logicalagent.getProgram().getCurrentUser().getAccount()
                                        .getUsername() + " unBlocked you   " +
                                        LocalDateTime.now());
                        Log log = new Log("unblocked someone", LocalDateTime.now(), 2,
                                logicalagent.getProgram().
                                        getCurrentUser().getAccount().getUsername());
                        Log.log(log);
                    } else {
                        logicalagent.getProgram().getCurrentUser().getBlacklist()
                                .add(user);
                        logicalagent.getProgram().getCurrentUser()
                                .getBlackUsername().add(user.getId());
                        logicalagent.getModelLoader().setUserChanges(
                                logicalagent.getProgram().getCurrentUser());
                        user.getSystemMessages().add(
                                logicalagent.getProgram().getCurrentUser().getAccount()
                                        .getUsername() + " Blocked you   " +
                                        LocalDateTime.now());
                        Log log=new Log("blocked someone",LocalDateTime.now(),2,
                                logicalagent.getProgram().
                                        getCurrentUser().getAccount().getUsername());
                        Log.log(log);
                    }
                    logicalagent.getModelLoader().setUserChanges(user);
                    watchAnotherPersonalPage.finishingProcess();
                    break;
                }
                case "5": {
                    watchAnotherPersonalPage.DrawingLine();
                    boolean IsNewChat = true;
                    Chat thisChat = null;
                    for (Chat chat : logicalagent.getProgram().getCurrentUser()
                            .getMyChats().keySet()) {
                        if (chat.getMembers().contains(user.getAccount().
                                getUsername())) {
                            thisChat = chat;
                            IsNewChat = false;
                            break;
                        }
                    }
                    if (IsNewChat) {
                        thisChat = new Chat();
                        logicalagent.getModelLoader().setChatID_counter(Chat.getId_counter());
                        logicalagent.getProgram().getAllChats().add(thisChat);
                        thisChat.getMembers().add(user.getAccount().
                                getUsername());
                        thisChat.getMembers().add(
                                logicalagent.getProgram().
                                        getCurrentUser().getAccount()
                                        .getUsername());
                        logicalagent.getProgram().getCurrentUser()
                                .getMyChats().put(thisChat, new LinkedList<>());
                        logicalagent.getProgram().getCurrentUser()
                                .getChatID().add(thisChat.getId());
                        logicalagent.getProgram().getCurrentUser()
                                .getUnreadChats().put(thisChat, new LinkedList<>());
                        user.getMyChats().put(thisChat, new LinkedList<>());
                        user.getUnreadChats().put(thisChat, new LinkedList<>());
                        user.getChatID().add(thisChat.getId());
                        logicalagent.getModelLoader().setUserChanges(user);
                        logicalagent.getModelLoader().setUserChanges(
                                logicalagent.getProgram().getCurrentUser());
                        logicalagent.getModelLoader().setChatChanges(thisChat);
                    }
                    chatroomAgent.Chatroom(user, thisChat, chatroom,
                            logicalagent, newMessageAgent
                            , newMessage);
                    watchAnotherPersonalPage.DrawingLine();
                    break;
                }
                case "4": {
                    if(!mute){
                        logicalagent.getProgram().getCurrentUser().getSilentUsers()
                                .add(user);
                        logicalagent.getProgram().getCurrentUser().getSilentUsername()
                                .add(user.getId());
                        Log log=new Log("mute someone",LocalDateTime.now(),2,
                                logicalagent.getProgram().
                                        getCurrentUser().getAccount().getUsername());
                        Log.log(log);
                    }
                    else{
                        logicalagent.getProgram().getCurrentUser().getSilentUsers()
                                .remove(user);
                        logicalagent.getProgram().getCurrentUser().getSilentUsername()
                                .remove(user.getId());
                        Log log=new Log("unMute someone",LocalDateTime.now(),2,
                                logicalagent.getProgram().
                                        getCurrentUser().getAccount().getUsername());
                        Log.log(log);
                    }
                    logicalagent.getModelLoader().setUserChanges(logicalagent
                            .getProgram().getCurrentUser());
                    watchAnotherPersonalPage.finishingProcess();
                }
            }
        }
    }
}
