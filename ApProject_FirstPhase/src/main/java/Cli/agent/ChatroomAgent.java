package Cli.agent;

import Cli.Chatroom;
import Cli.NewMessage;
import Logic.LogicalAgent;
import Models.Chat;
import Models.Message;
import Models.User;

import java.io.IOException;

public class ChatroomAgent {
    public void Chatroom(User user, Chat chat,
                         Chatroom chatroom,
                         LogicalAgent logicalagent
    , NewMessageAgent newMessageAgent, NewMessage newMessage) throws IOException {
        User Current = logicalagent.getProgram().getCurrentUser();
        int length = chat.getMessages().size() - 1;
        chatroom.showingOptions();
        if(length==-1){
            chatroom.noMessage();
        }
        for (int i = 0; i < 4; i++) {
            if (length >= 0) {
                if(chat.getMessages().get(length).getUser()!=null){
                    if (chat.getMessages().get(length)
                            .getUser().getAccount().getUsername().equals(
                                    Current.getAccount().getUsername())) {
                        chatroom.ShowMessage("you", chat.getMessages().get(length));
                    } else {
                        chatroom.ShowMessage(user.getAccount().getUsername(),
                                chat.getMessages().get(length));
                    }
                }
                else{
                    chatroom.ShowMessage("deletedAccount",chat.
                            getMessages().get(length));
                }
                Current.getUnreadChats().get(chat).
                        remove(chat.getMessages().get(length));
                int a=chat.getMessages().get(length)
                        .getId();
                for (int j = 0; j < Current.getUnreadMessage().size(); j++) {
                    if(Current.getUnreadMessage().get(j)==a){
                        Current.getUnreadMessage().remove(j);
                        break;
                    }
                }
                logicalagent.getModelLoader().setUserChanges(Current);
                if (chat.getForwardMessages().
                        contains(chat.getMessages().get(length))) {
                    if(chat.getMessages().get(length).getUser()!=null) {
                        chatroom.showForwardDetail(chat.getMessages().get(length)
                                .getUser().getAccount().getUsername());
                    }
                    else{
                        chatroom.showForwardDetail("deletedAccount");
                    }
                }
                length--;
            } else {
                chatroom.first();
                break;
            }
        }
        while (true) {
            String answer = chatroom.GetCommand();
            switch (answer) {
                case "0":
                    return;
                case "1":
                    for (int i = 0; i < 4; i++) {
                        if (length >= 0) {
                            if(chat.getMessages().get(length).getUser()!=null){
                                if (chat.getMessages().get(length)
                                        .getUser().getAccount().getUsername().equals(
                                                Current.getAccount().getUsername())) {
                                    chatroom.ShowMessage("you", chat.getMessages().get(length));
                                } else {
                                    chatroom.ShowMessage(user.getAccount().getUsername(),
                                            chat.getMessages().get(length));
                                }
                            }
                            else{
                                chatroom.ShowMessage("deletedAccount",chat.
                                        getMessages().get(length));
                            }
                            Current.getUnreadChats().get(chat).
                                    remove(chat.getMessages().get(length));
                            int a=chat.getMessages().get(length)
                                    .getId();
                            for (int j = 0; j < Current.getUnreadMessage().size(); j++) {
                                if(Current.getUnreadMessage().get(j)==a){
                                    Current.getUnreadMessage().remove(j);
                                    break;
                                }
                            }
                            logicalagent.getModelLoader().setUserChanges(Current);
                            if (chat.getForwardMessages().
                                    contains(chat.getMessages().get(length))) {
                                if(chat.getMessages().get(length).getUser()!=null) {
                                    chatroom.showForwardDetail(chat.getMessages().get(length)
                                            .getUser().getAccount().getUsername());
                                }
                                else{
                                    chatroom.showForwardDetail("deletedAccount");
                                }
                            }
                            length--;
                        } else {
                            chatroom.first();
                            break;
                        }
                    }
                    break;
                case "2": {
                    int old = length;
                    length = chat.getMessages().size() - 1;
                    for (int i = 0; i < Math.min(length - old, 4); i++) {
                        if (length >= 0) {
                            if(chat.getMessages().get(length).getUser()!=null) {
                                if (chat.getMessages().get(length)
                                        .getUser().getAccount().getUsername().equals(
                                                Current.getAccount().getUsername())) {
                                    chatroom.ShowMessage("you", chat.getMessages().get(length));
                                } else {
                                    chatroom.ShowMessage(user.getAccount().getUsername(),
                                            chat.getMessages().get(length));
                                }
                            }else{
                                chatroom.ShowMessage("deletedAccount",chat.
                                        getMessages().get(length));
                            }
                            Current.getUnreadChats().get(chat).
                                    remove(chat.getMessages().get(length));
                            int a=chat.getMessages().get(length)
                                    .getId();
                            for (int j = 0; j < Current.getUnreadMessage().size(); j++) {
                                if(Current.getUnreadMessage().get(j)==a){
                                    Current.getUnreadMessage().remove(j);
                                    break;
                                }
                            }
                            logicalagent.getModelLoader().setUserChanges(Current);
                            if (chat.getForwardMessages().
                                    contains(chat.getMessages().get(length))) {
                                if(chat.getMessages().get(length).getUser()!=null) {
                                    chatroom.showForwardDetail(chat.getMessages().get(length)
                                            .getUser().getAccount().getUsername());
                                }
                                else{
                                    chatroom.showForwardDetail("deletedAccount");
                                }
                            }
                            length--;
                        } else {
                            chatroom.first();
                            break;
                        }
                    }
                    break;
                }
                case "3": {
                    chatroom.DrawingLine();
                    Message message = newMessageAgent.NewMessage(newMessage,logicalagent);
                    chatroom.finishingProcess();
                    chatroom.DrawingLine();
                    chatroom.showingOptions();
                    chat.getMessages().add(message);
                    chat.getId_Message().add(message.getId());
                    if(chat.getMembers().size()==1){
                        user.getSavedMessage().add(message);
                        user.getSavedMessageId().add(message.getId());
                    }
                    if(chat.getMembers().size()!=1) {
                        logicalagent.getModelLoader().setChatChanges(chat);
                    }
                    user.getUnreadChats().get(chat).add(message);
                    user.getUnreadMessage().add(message.getId());
                    logicalagent.getModelLoader().setUserChanges(user);
                    Current.getMyChats().get(chat).add(message);
                    length = chat.getMessages().size()-1;
                    for (int i = 0; i < 4; i++) {
                        if (length >= 0) {
                            if(chat.getMessages().get(length).getUser()!=null) {
                                if (chat.getMessages().get(length)
                                        .getUser().getAccount().getUsername().equals(
                                                Current.getAccount().getUsername())) {
                                    chatroom.ShowMessage("you", chat.getMessages().get(length));
                                } else {
                                    chatroom.ShowMessage(user.getAccount().getUsername(),
                                            chat.getMessages().get(length));
                                }
                            }
                            else{
                                chatroom.ShowMessage("deletedAccount",
                                        chat.getMessages().get(length));
                            }
                            if (chat.getForwardMessages().
                                    contains(chat.getMessages().get(length))) {
                                chatroom.showForwardDetail(chat.getMessages().get(length)
                                        .getUser().getAccount().getUsername());
                            }
                            length--;
                        } else {
                            chatroom.first();
                            break;
                        }
                    }
                    break;
                }
            }
        }
    }
}
