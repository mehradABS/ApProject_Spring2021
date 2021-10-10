package app.chat.controller;

import controller.OfflineController;
import db.ChatDB;
import db.UserDB;
import models.OChat;
import models.auth.OUser;
import models.messages.OMessage;
import resources.Texts;
import responses.chat.LoadChatResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class ContactPanelController extends OfflineController {

    public List<String[]> loadChats() throws IOException {
        List<String[]> chatsInfo = new LinkedList<>();
        OUser current = CURRENT_USER;
        List<OChat> chats = new LinkedList<>();
        List<Integer> unread = new LinkedList<>();
        for (int i = 0; i < current.getMyUnreadChatIds().size(); i++) {
            chats.add(context.getChats().get(current.getMyUnreadChatIds().get(i)));
            unread.add(current.getMyUnreadChatMessagesNumbers().get(i));
        }
        for (OChat item : chats) {
            if (item.getUsersIds().size() == 2 && !item.isGroupChat()) {
                for (Integer id : item.getUsersIds()) {
                    if (id != current.getId()) {
                        OUser user = context.getUsers().get(id);
                        item.setName(user.getAccount().getUsername());
                        break;
                    }
                }
            }
        }
        int i = 0;
        for (; i < chats.size(); i++) {
            String[] info = new String[4];
            if(chats.get(i).getUsersIds().size() == 1){
                info[2] = ((UserDB)context.getUsers()).loadProfile(-1,
                        60);
            }
            else if(chats.get(i).getUsersIds().size() == 2 && !chats.get(i).isGroupChat()){
                int id = 0;
                for (Integer Id:chats.get(i).getUsersIds()) {
                    if(Id != current.getId()){
                        id = Id;
                        break;
                    }
                }
                info[2] = ((UserDB)context.getUsers()).loadProfile(id,
                        60);
            }
            else{
                info[2] = ((ChatDB)context.getChats()).loadChatImage
                        (chats.get(i).getId());
            }
            info[1] = chats.get(i).getName() +" ("+ unread.get(i) +")";
            info[0] = String.valueOf(chats.get(i).getId());
            chatsInfo.add(info);
        }
        for (Integer id: current.getMyChatIds()) {
            OChat chat = context.getChats().get(id);
            boolean contain = false;
            for (OChat value : chats) {
                if (value.getId() == chat.getId()) {
                    contain = true;
                    break;
                }
            }
            if(!contain){
                chats.add(chat);
            }
        }
        if(chats.size() == 0){
            return null;
        }
        for (int j = i; j < chats.size(); j++) {
            if(chats.get(j).getUsersIds().size() == 2 && !chats.get(j).isGroupChat()){
                for (Integer id:chats.get(j).getUsersIds()) {
                    if(id != current.getId()){
                        OUser user = context.getUsers().get(id);
                        chats.get(j).setName(user.getAccount().getUsername());
                        break;
                    }
                }
            }
        }
        for (; i < chats.size() ; i++) {
            String[] info = new String[4];
            if(chats.get(i).getUsersIds().size() == 1){
                info[2] = ((UserDB)context.getUsers()).loadProfile(-1,
                        60);
            }
            else if(chats.get(i).getUsersIds().size() == 2 && !chats.get(i).isGroupChat()){
                int id = 0;
                for (Integer Id:chats.get(i).getUsersIds()) {
                    if(Id != current.getId()){
                        id = Id;
                        break;
                    }
                }
                info[2] = ((UserDB)context.getUsers()).loadProfile(id,
                        60);
            }
            else{
                info[2] = ((ChatDB)context.getChats()).loadChatImage
                        (chats.get(i).getId());
            }
            info[1] = chats.get(i).getName();
            info[0] = String.valueOf(chats.get(i).getId());
            chatsInfo.add(info);
        }
        for (String[] info: chatsInfo) {
            info[3] = "false";
        }
        return chatsInfo;
    }

    public void setChatsAndUser(LoadChatResponse response){
        try {
            CURRENT_USER = response.getUser();
            context.getUsers().set(CURRENT_USER);
            for (OChat chat: response.getUserChats()) {
                context.getChats().set(chat);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void setNewChat(OChat chat){
        try {
            context.getChats().set(chat);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteChat(int chatId){
        context.getChats().delete(chatId);
    }
}