package Cli.agent;

import Cli.ForwardMessage;
import Logic.LogicalAgent;
import Models.Message;
import Models.User;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ForwardMessageAgent {
    public int ForwardMessage(Message message
    , ForwardMessage forwardMessage, LogicalAgent logicalagent,
                              SendMessageAgent sendMessageAgent)
            throws IOException {
        forwardMessage.showingOptions();
        User currentUser = logicalagent.getProgram().getCurrentUser();
        String answer = forwardMessage.GetCommand();
        switch (answer) {
            case "1": {
                boolean block = false;
                for (User user : currentUser.getFollowings()) {
                    if(user.getAccount().isActive()) {
                        if (!user.getBlacklist().contains(currentUser)
                                && !currentUser.getBlacklist().contains(user)) {
                            sendMessageAgent.
                                    sendMessage
                                            (currentUser, user, message,
                                                    true,logicalagent);
                            block = true;
                        }
                    }
                }
                if (block) {
                    forwardMessage.finishingProcess();
                } else {
                    forwardMessage.Block();
                }
                return 0;
            }
            case "2": {
                String[] names;
                forwardMessage.Lists();
                while (true) {
                    names = forwardMessage.getList();
                    if (names.length == 1 && names[0].equals("0")) {
                        return 0;
                    }
                    boolean isValid1 = true;
                    for (String listName : names) {
                        boolean isValid2 = false;
                        for (User.ListName list : currentUser.getFollowingsList()) {
                            if (list.getName().equals(listName)) {
                                isValid2 = true;
                                break;
                            }
                        }
                        if (!isValid2) {
                            isValid1 = false;
                            break;
                        }
                    }
                    if (!isValid1) {
                        forwardMessage.invalid();
                    } else {
                        break;
                    }
                }
                boolean block = false;
                List<User> repeatedUser = new LinkedList<>();
                for (String name : names) {
                    for (User.ListName listName : currentUser.getFollowingsList()) {
                        if (listName.getName().equals(name)) {
                            for (User receiver : listName.getList()) {
                                if(receiver.getAccount().isActive()){
                                    if (!receiver.getBlacklist().contains(currentUser)
                                            && !currentUser.getBlacklist().contains(receiver)) {
                                        if(!repeatedUser.contains(receiver)) {
                                            sendMessageAgent.sendMessage
                                                    (currentUser, receiver, message,
                                                    true,
                                                            logicalagent);
                                            block = true;
                                            repeatedUser.add(receiver);
                                        }
                                    }
                                }
                            }
                            break;
                        }
                    }
                }
                if (block) {
                    forwardMessage.finishingProcess();
                } else {
                    forwardMessage.Block1();
                }
                return 0;
            }
            case "3": {
                String[] names;
                forwardMessage.Usernames();
                while (true) {
                    names = forwardMessage.getUsername();
                    if (names.length == 1 && names[0].equals("0")) {
                        return 0;
                    }
                    boolean isValid1 = true;
                    for (String name : names) {
                        boolean isValid2 = false;
                        for (User myFollowing : currentUser.getFollowings()) {
                            if(myFollowing.getAccount().isActive()) {
                                if (myFollowing.getAccount().getUsername().
                                        equals(name)) {
                                    isValid2 = true;
                                    break;
                                }
                            }
                        }
                        if (!isValid2) {
                            isValid1 = false;
                            break;
                        }
                    }
                    if (!isValid1) {
                        forwardMessage.invalid();
                    } else {
                        break;
                    }
                }
                boolean block = false;
                for (String name : names) {
                    for (User myFollowing : currentUser.getFollowings()) {
                        if(myFollowing.getAccount().isActive()){
                            if (myFollowing.getAccount().getUsername().equals(name)) {
                                if (!myFollowing.getBlacklist().contains(currentUser)
                                        && !currentUser.getBlacklist().contains(myFollowing)) {
                                    sendMessageAgent.sendMessage(currentUser, myFollowing, message,
                                            true,logicalagent);
                                    block = true;
                                }
                                break;
                            }
                        }
                    }
                }
                if (block) {
                    forwardMessage.finishingProcess();
                } else {
                    forwardMessage.Block();
                }
                return 0;
            }
        }
        return -1;
    }
}
