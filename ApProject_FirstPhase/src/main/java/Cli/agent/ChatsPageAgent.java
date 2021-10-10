package Cli.agent;

import Cli.Chatroom;
import Cli.ChatsPage;
import Cli.NewMessage;
import Logic.LogicalAgent;
import Models.Chat;
import Models.Message;
import Models.User;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ChatsPageAgent {
    public void ChatsPage(ChatsPage chatsPage,
                          LogicalAgent logicalagent,
                          ChatroomAgent chatroomAgent
    , Chatroom chatroom, NewMessageAgent newMessageAgent,
                          NewMessage newMessage) throws IOException {
        chatsPage.chatHistory();
        String name;
        HashMap<Chat,Integer> map=logicalagent.getProgram().getCurrentUser()
                .unreadChat();
        chatsPage.showUsernames(0,"saved Messages");
        for (Chat chat :map.keySet()) {
            if(chat.getMembers().get(0)
                    .equals(logicalagent.getProgram().getCurrentUser().
                            getAccount().getUsername())){
                name=chat.getMembers().get(1);
            }
            else{
                name=chat.getMembers().get(0);
            }
            chatsPage.showUsernames(map.get(chat),name);
        }
        for (Chat chat:logicalagent.getProgram().getCurrentUser().
                getUnreadChats().keySet()) {
            if(!map.containsKey(chat)){
                if(chat.getMembers().get(0)
                        .equals(logicalagent.getProgram().getCurrentUser().
                                getAccount().getUsername())){
                    name=chat.getMembers().get(1);
                }
                else{
                    name=chat.getMembers().get(0);
                }
                chatsPage.showUsernames(0,name);
            }
        }
        chatsPage.showingOptions();
        while(true){
            String answer= chatsPage.GetCommand();
            if(answer.equals("0")){
                return;
            }
            else if(answer.equals("myself")){
                Chat chat=new Chat();
                chat.getMembers().add(logicalagent.getProgram().getCurrentUser()
                        .getAccount().getUsername());
                chat.getMessages().addAll(
                        logicalagent.getProgram().getCurrentUser()
                                .getSavedMessage());
                for (Message message: chat.getMessages()) {
                    if(message.getUser()!=null){
                        if(!message.getUser().getAccount().getUsername().
                                equals(logicalagent.getProgram().getCurrentUser()
                                        .getAccount().getUsername())){
                            chat.getForwardMessages().add(message);
                        }
                    }
                }
                logicalagent.getProgram().getCurrentUser()
                        .getMyChats().put(chat,new LinkedList<>());
                logicalagent.getProgram().getCurrentUser()
                        .getUnreadChats().put(chat,new LinkedList<>());
                chatsPage.DrawingLine();
                chatroomAgent.Chatroom(logicalagent.getProgram().getCurrentUser()
                        ,chat,chatroom,logicalagent,newMessageAgent
                ,newMessage);
                chatsPage.DrawingLine();
                chatsPage.chatHistory();
                logicalagent.getProgram().getCurrentUser().getUnreadChats()
                        .remove(chat);
                logicalagent.getProgram().getCurrentUser().getMyChats()
                        .remove(chat);
                map=logicalagent.getProgram().getCurrentUser()
                        .unreadChat();
                chatsPage.showUsernames(0,"savedMessage");
                for (Chat chat1 :map.keySet()) {
                    if(chat1.getMembers().get(0)
                            .equals(logicalagent.getProgram().getCurrentUser().
                                    getAccount().getUsername())){
                        name=chat1.getMembers().get(1);
                    }
                    else{
                        name=chat1.getMembers().get(0);
                    }
                    chatsPage.showUsernames(map.get(chat1),name);
                }
                for (Chat chat1:logicalagent.getProgram().getCurrentUser().
                        getUnreadChats().keySet()) {
                    if(!map.containsKey(chat1)){
                        if(chat1.getMembers().get(0)
                                .equals(logicalagent.getProgram().getCurrentUser().
                                        getAccount().getUsername())){
                            name=chat1.getMembers().get(1);
                        }
                        else{
                            name=chat1.getMembers().get(0);
                        }
                        chatsPage.showUsernames(0,name);
                    }
                }
                chatsPage.showingOptions();
            }
            else {
                boolean block=true;
                boolean block1=true;
                User user=null;
                User current=logicalagent.getProgram().getCurrentUser();
                List<User> search = new LinkedList<>(current.getFollowers());
                search.addAll(current.getFollowings());
                for (User valid:search) {
                    if(valid.getAccount().isActive()) {
                        if (valid.getAccount().getUsername().equals(answer)) {
                            user = valid;
                            if (!valid.getBlacklist().contains(current)) {
                                block = false;
                            }
                            if (!current.getBlacklist().contains(valid) &&
                                    !block) {
                                block1 = false;
                            }
                            break;
                        }
                    }
                }
                if(user!=null){
                    if(block){
                        chatsPage.BlockUser1(user.getAccount().getUsername());
                    }
                    else if(block1){
                        chatsPage.BlockUser();
                    }
                    else{
                        Chat chat=null;
                        for (Chat ch: user.getMyChats().keySet()) {
                            if(ch.getMembers().contains(current.getAccount()
                                    .getUsername())){
                                chat=ch;
                                break;
                            }
                        }
                        if(chat==null) {
                            chat=new Chat();
                            logicalagent.getModelLoader().setChatID_counter(Chat.getId_counter());
                            logicalagent.getProgram().getAllChats().add(chat);
                            chat.getMembers().add(current.getAccount().getUsername());
                            chat.getMembers().add(user.getAccount().getUsername());
                            logicalagent.getModelLoader().setChatChanges(chat);
                            current.getChatID().add(chat.getId());
                            user.getChatID().add(chat.getId());
                            current.getMyChats().put(chat,new LinkedList<>());
                            current.getUnreadChats().put(chat,new LinkedList<>());
                            user.getMyChats().put(chat,new LinkedList<>());
                            user.getUnreadChats().put(chat,new LinkedList<>());
                        }
                        chatsPage.DrawingLine();
                        chatroomAgent.Chatroom(user,chat
                        ,chatroom,logicalagent,newMessageAgent
                        ,newMessage);
                        chatsPage.DrawingLine();
                        chatsPage.chatHistory();
                        map=logicalagent.getProgram().getCurrentUser()
                                .unreadChat();
                        chatsPage.showUsernames(0,"savedMessage");
                        for (Chat chat1 :map.keySet()) {
                            if(chat1.getMembers().get(0)
                                    .equals(logicalagent.getProgram().getCurrentUser().
                                            getAccount().getUsername())){
                                name=chat1.getMembers().get(1);
                            }
                            else{
                                name=chat1.getMembers().get(0);
                            }
                            chatsPage.showUsernames(map.get(chat1),name);
                        }
                        for (Chat chat1:logicalagent.getProgram().getCurrentUser().
                                getUnreadChats().keySet()) {
                            if(!map.containsKey(chat1)){
                                if(chat1.getMembers().get(0)
                                        .equals(logicalagent.getProgram().getCurrentUser().
                                                getAccount().getUsername())){
                                    name=chat1.getMembers().get(1);
                                }
                                else{
                                    name=chat1.getMembers().get(0);
                                }
                                chatsPage.showUsernames(0,name);
                            }
                        }
                        chatsPage.showingOptions();
                    }
                }
                else{
                    chatsPage.invalid();
                }

            }
        }
    }
}
