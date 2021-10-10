package Cli.agent;

import Cli.Chatroom;
import Cli.NewMessage;
import Cli.UserLists;
import Cli.WatchAnotherPersonalPage;
import Logic.LogicalAgent;
import Models.Log;
import Models.User;

import java.io.IOException;
import java.time.LocalDateTime;

public class UserListsAgent {
    public void UserLists(LogicalAgent
                          logicalagent,
                          UserLists userLists
    , WatchAnotherPersonalPageAgent watchAnotherPersonalPageAgent,
                          WatchAnotherPersonalPage watchAnotherPersonalPage,
                          ChatroomAgent chatroomAgent,
                          Chatroom chatroom, NewMessageAgent newMessageAgent
    , NewMessage newMessage)
            throws IOException {
        User current = logicalagent.getProgram().getCurrentUser();
        while (true) {
            userLists.showingOptions();
            String answer = userLists.GetCommand();
            switch (answer) {
                case "0":
                    return;
                case "1":
                    userLists.DrawingLine();
                    userLists.showList(current.getFollowers(), "followers");
                    while (true) {
                        String answer1 = userLists.getUsername();
                        if (!answer1.equals("0")) {
                            User another = null;
                            for (User user : current.getFollowers()) {
                                if(user.getAccount().isActive()){
                                    if (user.getAccount().getUsername().equals(answer1)) {
                                        another = user;
                                        break;
                                    }
                                }
                            }
                            if (another != null) {
                                userLists.DrawingLine();
                                watchAnotherPersonalPageAgent.
                                        WatchAnotherPersonalPage(another,logicalagent,
                                                watchAnotherPersonalPage,
                                                chatroomAgent,chatroom
                                        ,newMessageAgent,newMessage);
                                userLists.DrawingLine();
                                userLists.showList(current.getFollowers(),
                                        "followers");
                            } else {
                                userLists.invalid();
                            }
                        } else {
                            userLists.DrawingLine();
                            break;
                        }
                    }
                    break;
                case "2":
                    userLists.DrawingLine();
                    userLists.showList(current.getFollowings(), "following");
                    while (true) {
                        String answer1 = userLists.getUsername();
                        if (!answer1.equals("0")) {
                            User another = null;
                            for (User user : current.getFollowings()) {
                                if(user.getAccount().isActive()) {
                                    if (user.getAccount().getUsername().equals(answer1)) {
                                        another = user;
                                        break;
                                    }
                                }
                            }
                            if (another != null) {
                                userLists.DrawingLine();
                                watchAnotherPersonalPageAgent.
                                        WatchAnotherPersonalPage(another,logicalagent,
                                                watchAnotherPersonalPage,
                                                chatroomAgent,chatroom,
                                                newMessageAgent,newMessage);
                                userLists.DrawingLine();
                                userLists.showList(current.getFollowings(),
                                        "following");
                            } else {
                                userLists.invalid();
                            }
                        } else {
                            userLists.DrawingLine();
                            break;
                        }
                    }
                    break;
                case "3":
                    userLists.DrawingLine();
                    userLists.showList(current.getBlacklist(), "BlackList");
                    while (true) {
                        String answer1 = userLists.getUsername();
                        if (!answer1.equals("0")) {
                            boolean isUnblock = answer1.endsWith("//");
                            if (isUnblock) {
                                answer1 = answer1.substring(0, answer1.length() - 2);
                            }
                            User another = null;
                            for (User user : current.getBlacklist()) {
                                if(user.getAccount().isActive()){
                                    if (user.getAccount().getUsername().equals(answer1)) {
                                        another = user;
                                        break;
                                    }
                                }
                            }
                            if (another != null) {
                                if (!isUnblock) {
                                    userLists.DrawingLine();
                                    watchAnotherPersonalPageAgent.
                                            WatchAnotherPersonalPage(another,logicalagent,
                                                    watchAnotherPersonalPage,
                                                    chatroomAgent,chatroom
                                            ,newMessageAgent,newMessage);
                                    userLists.DrawingLine();
                                    userLists.showList(current.getBlacklist(),
                                            "BlackList");
                                } else {
                                    current.getBlacklist()
                                            .remove(another);
                                    current.getBlackUsername().remove(
                                            another.getId());
                                    logicalagent.getModelLoader().setUserChanges(current);
                                    another.getSystemMessages().add(
                                            current.getAccount()
                                                    .getUsername() + " unBlocked you   " +
                                                    LocalDateTime.now());
                                    Log log=new Log("unblocked someone",LocalDateTime.now(),2,
                                            logicalagent.getProgram().
                                                    getCurrentUser().getAccount().getUsername());
                                    Log.log(log);
                                    logicalagent.getModelLoader().setUserChanges(another);
                                }
                            } else {
                                userLists.invalid();
                            }
                        } else {
                            userLists.DrawingLine();
                            break;
                        }
                    }
                    break;
                case "4":
                    userLists.DrawingLine();
                    userLists.categorize(current);
                    while (true) {
                        String answer1 = userLists.getUsername();
                        if (!answer1.equals("0")) {
                            User.ListName listName0 = new User.ListName();
                            boolean isNewList = true;
                            for (User.ListName listName : current.getFollowingsList()) {
                                if (listName.getName().equals(answer1)) {
                                    listName0 = listName;
                                    isNewList = false;
                                    break;
                                }
                            }
                            if (isNewList) {
                                listName0.setName(answer1);
                                current.getFollowingsList().add(listName0);
                                Log log=new Log("new list created in following list",LocalDateTime.now(),3,
                                        logicalagent.getProgram().
                                                getCurrentUser().getAccount().getUsername());
                                Log.log(log);
                                logicalagent.getModelLoader().setUserChanges(current);
                            }
                            userLists.DrawingLine();
                            userLists.showList(listName0.getList(), "listName");
                            while (true) {
                                String answer3 = userLists.getUsername();
                                if (!answer3.equals("0")) {
                                    boolean isRemove = answer3.endsWith("[]")
                                            || answer3.endsWith("][");
                                    User another = null;
                                    if (isRemove) {
                                        if (answer3.endsWith("[]")) {
                                            answer3 = answer3.substring(0, answer3.length() - 2);
                                            for (User user : current.getFollowings()) {
                                                if(user.getAccount().isActive()) {
                                                    if (user.getAccount().getUsername().
                                                            equals(answer3)) {
                                                        another = user;
                                                        break;
                                                    }
                                                }
                                            }
                                            if (another != null) {
                                                if(!listName0.getList().contains(another)) {
                                                    listName0.getList().add(another);
                                                    listName0.getUsernames().add(another
                                                            .getId());
                                                    logicalagent.getModelLoader()
                                                            .setUserChanges(current);
                                                    Log log=new Log("one of list in friend's list changed",LocalDateTime.now(),3,
                                                            logicalagent.getProgram().
                                                                    getCurrentUser().getAccount().getUsername());
                                                    Log.log(log);
                                                    userLists.finishingProcess();
                                                }
                                                else{
                                                    userLists.before();
                                                }
                                            } else {
                                                userLists.invalid();
                                            }
                                        } else {
                                            answer3 = answer3.substring(0, answer3.length() - 2);
                                            for (User user : listName0.getList()) {
                                                if(user.getAccount().isActive()) {
                                                    if (user.getAccount().getUsername()
                                                            .equals(answer3)) {
                                                        another = user;
                                                        break;
                                                    }
                                                }
                                            }
                                            if (another != null) {
                                                listName0.getList().remove(another);
                                                listName0.getUsernames().remove(another
                                                        .getId());
                                                logicalagent.getModelLoader().setUserChanges(
                                                        current);
                                                Log log=new Log("one of list in friend's list changed",LocalDateTime.now(),3,
                                                        logicalagent.getProgram().
                                                                getCurrentUser().getAccount().getUsername());
                                                Log.log(log);
                                                userLists.finishingProcess();
                                            } else {
                                                userLists.invalid();
                                            }
                                        }
                                    } else {
                                        for (User user : listName0.getList()) {
                                            if(user.getAccount().isActive()) {
                                                if (user.getAccount().getUsername().equals(answer3)) {
                                                    another = user;
                                                    break;
                                                }
                                            }
                                        }
                                        if (another != null) {
                                            userLists.DrawingLine();
                                            watchAnotherPersonalPageAgent.
                                                    WatchAnotherPersonalPage(another,logicalagent,
                                                            watchAnotherPersonalPage,
                                                            chatroomAgent,chatroom
                                                    ,newMessageAgent
                                                    ,newMessage);
                                            userLists.DrawingLine();
                                            userLists.showList(listName0.getList(),
                                                    "listName");
                                        } else {
                                            userLists.invalid();
                                        }
                                    }
                                } else {
                                    userLists.DrawingLine();
                                    break;
                                }
                            }

                        } else {
                            userLists.DrawingLine();
                            break;
                        }
                    }
                    break;
            }
        }
    }
}
