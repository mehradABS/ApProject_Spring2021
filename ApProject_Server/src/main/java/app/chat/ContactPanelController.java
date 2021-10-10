package app.chat;

import events.visitors.chat.ContactPanelEventVisitor;
import models.Chat;
import models.OChat;
import models.auth.OUser;
import models.auth.User;
import controller.ClientHandler;
import controller.MainController;
import db.ChatDB;
import db.UserDB;
import network.ImageSender;
import resources.Texts;
import responses.Response;
import responses.chat.*;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ContactPanelController extends MainController
implements ContactPanelEventVisitor {

    private final ClientHandler clientHandler;

    public ContactPanelController(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public Response loadChats(){
        LoadChatResponse response =  new LoadChatResponse();
        try {
            synchronized (User.LOCK) {
                synchronized (Chat.lock) {
                    List<String[]> chatsInfo = new LinkedList<>();
                    User current = context.getUsers().get(clientHandler.getClientId());
                    List<Chat> chats = new LinkedList<>();
                    List<Integer> unread = new LinkedList<>();
                    for (int i = 0; i < current.getMyUnreadChatIds().size(); i++) {
                        chats.add(context.getChats().get(current.getMyUnreadChatIds().get(i)));
                        unread.add(current.getMyUnreadChatMessagesNumbers().get(i));
                    }
                    for (Chat item : chats) {
                        if (item.getUsersIds().size() == 2 && !item.isGroupChat()) {
                            for (Integer id : item.getUsersIds()) {
                                if (id != clientHandler.getClientId()) {
                                    User user = context.getUsers().get(id);
                                    item.setName(user.getAccount().getUsername());
                                    break;
                                }
                            }
                        }
                    }
                    int i = 0;
                    for (; i < chats.size(); i++) {
                        String[] info = new String[5];
                        if (chats.get(i).getUsersIds().size() == 1) {
                            info[2] = "def";
                            info[3] = "true";
                        } else if (chats.get(i).getUsersIds().size() == 2 && !chats.get(i).isGroupChat()) {
                            int id = 0;
                            for (Integer Id : chats.get(i).getUsersIds()) {
                                if (Id != clientHandler.getClientId()) {
                                    id = Id;
                                    break;
                                }
                            }
                            info[2] = loadImage(id);
                            info[3] = String.valueOf(isOnline(id));
                            info[4] = String.valueOf(id);
                        } else {
                            info[2] = "groupChat";
                            info[3] = "false";
                        }
                        info[1] = chats.get(i).getName() + " (" + unread.get(i) + ")";
                        info[0] = String.valueOf(chats.get(i).getId());
                        chatsInfo.add(info);
                    }
                    for (Integer id : current.getMyChatIds()) {
                        Chat chat = context.getChats().get(id);
                        boolean contain = false;
                        for (Chat value : chats) {
                            if (value.getId() == chat.getId()) {
                                contain = true;
                                break;
                            }
                        }
                        if (!contain) {
                            chats.add(chat);
                        }
                    }
                    if (chats.size() == 0) {
                        Chat.setId_counter(context.getChats().getIDCounter());
                        Chat chat = new Chat();
                        context.getChats().setIDCounter(Chat.getId_counter());
                        chat.setName(Texts.SAVED_MESSAGES);
                        chat.getUsersIds().add(clientHandler.getClientId());
                        current.getMyChatIds().add(chat.getId());
                        current.getMyChatMessagesIds().add(new LinkedList<>());
                        context.getChats().set(chat);
                        context.getUsers().set(current);
                        chats.add(chat);
                    }
                    boolean savedMessage = false;
                    for (Chat value : chats) {
                        if (value.getUsersIds().size() == 1) {
                            savedMessage = true;
                            break;
                        }
                    }
                    if (!savedMessage) {
                        Chat.setId_counter(context.getChats().getIDCounter());
                        Chat chat = new Chat();
                        context.getChats().setIDCounter(Chat.getId_counter());
                        chat.setName(Texts.SAVED_MESSAGES);
                        chat.getUsersIds().add(clientHandler.getClientId());
                        current.getMyChatIds().add(chat.getId());
                        current.getMyChatMessagesIds().add(new LinkedList<>());
                        context.getChats().set(chat);
                        context.getUsers().set(current);
                        chats.add(chat);
                    }
                    for (int j = i; j < chats.size(); j++) {
                        if (chats.get(j).getUsersIds().size() == 2 && !chats.get(j).isGroupChat()) {
                            for (Integer id : chats.get(j).getUsersIds()) {
                                if (id != clientHandler.getClientId()) {
                                    User user = context.getUsers().get(id);
                                    chats.get(j).setName(user.getAccount().getUsername());
                                    break;
                                }
                            }
                        }
                    }
                    for (; i < chats.size(); i++) {
                        String[] info = new String[5];
                        if (chats.get(i).getUsersIds().size() == 1) {
                            info[2] = "def";
                            info[3] = "true";
                        } else if (chats.get(i).getUsersIds().size() == 2
                            && !chats.get(i).isGroupChat()) {
                            int id = 0;
                            for (Integer Id : chats.get(i).getUsersIds()) {
                                if (Id != clientHandler.getClientId()) {
                                    id = Id;
                                    break;
                                }
                            }
                            info[2] = loadImage(id);
                            info[3] = String.valueOf(isOnline(id));
                            info[4] = String.valueOf(id);
                        } else {
                            info[2] = "groupChat";
                            info[3] = "false";
                        }
                        info[1] = chats.get(i).getName();
                        info[0] = String.valueOf(chats.get(i).getId());
                        chatsInfo.add(info);
                    }
                    response.setChatsInfo(chatsInfo);
                    response.setUser(current);
                    List<OChat> userChats = new LinkedList<>();
                    for (Integer chatId: current.getMyChatIds()) {
                        userChats.add(context.getChats().get(chatId));
                    }
                    response.setUserChats(userChats);
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return response;
    }

    public static String loadImage(int id){
        File file = new File("src\\Save\\images\\"+
                (id)
                +"\\profile\\profile60.png");
        if(file.exists()){
            return ImageSender.encodeImage("src\\Save\\images\\" +
                    (id)
                    +"\\profile\\profile60.png");
        }
        else {
            return "def";
        }
    }

    public Response makeNewChat(int userId) {
        try {
            synchronized (User.LOCK) {
                synchronized (Chat.lock) {
                    User current = context.getUsers().get(clientHandler.getClientId());
                    for (Integer chatId : current.getMyChatIds()) {
                        Chat chat = context.getChats().get(chatId);
                        if (chat.getUsersIds().size() == 2 && chat.getUsersIds().contains(userId)
                            && !chat.isGroupChat()) {
                            return new MakeNewChatResponse(false, chat);
                        }
                    }
                    Chat.setId_counter(context.getChats().getIDCounter());
                    Chat chat = new Chat();
                    context.getChats().setIDCounter(Chat.getId_counter());
                    User user = context.getUsers().get(userId);
                    chat.setName(user.getAccount().getUsername());
                    chat.getUsersIds().add(clientHandler.getClientId());
                    chat.getUsersIds().add(userId);
                    current.getMyChatIds().add(chat.getId());
                    current.getMyChatMessagesIds().add(new LinkedList<>());
                    user.getMyChatIds().add(chat.getId());
                    user.getMyChatMessagesIds().add(new LinkedList<>());
                    context.getChats().set(chat);
                    context.getUsers().set(current);
                    context.getUsers().set(user);
                    return new MakeNewChatResponse(true, chat);
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public Response makeNewGroupChat(List<Integer> ids, String chatName) {
        try {
            synchronized (User.LOCK) {
                synchronized (Chat.lock) {
                    User current = context.getUsers().get(clientHandler.getClientId());
                    Chat.setId_counter(context.getChats().getIDCounter());
                    Chat chat = new Chat();
                    context.getChats().setIDCounter(Chat.getId_counter());
                    chat.setName(chatName);
                    chat.getUsersIds().add(clientHandler.getClientId());
                    current.getMyChatIds().add(chat.getId());
                    current.getMyChatMessagesIds().add(new LinkedList<>());
                    context.getUsers().set(current);
                    for (Integer userId : ids) {
                        User user = context.getUsers().get(userId);
                        chat.getUsersIds().add(userId);
                        user.getMyChatIds().add(chat.getId());
                        user.getMyChatMessagesIds().add(new LinkedList<>());
                        context.getUsers().set(user);
                    }
                    chat.setGroupChat(true);
                    context.getChats().set(chat);
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
//
    public Response loadGroupChat() {
        try {
            List<String[]> chatsInfo = new LinkedList<>();
            User current = context.getUsers().get(clientHandler.getClientId());
            List<Chat> myGroupChat = new LinkedList<>();
            for (Integer id:current.getMyChatIds()) {
                Chat chat = context.getChats().get(id);
                if(chat.isGroupChat()){
                    myGroupChat.add(chat);
                }
            }
            for (Chat chat: myGroupChat) {
                String[] info = new String[4];
                info[0] = String.valueOf(chat.getId());
                info[1] = String.valueOf(chat.getName());
                chatsInfo.add(info);
            }
            LoadGroupChatResponse response = new LoadGroupChatResponse();
            response.setInfo(chatsInfo);
            return response;
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
//
    public Response removePersonFromChat(int chatId, List<Integer> userIds){
        try {
            synchronized (User.LOCK) {
                synchronized (Chat.lock) {
                    for (Integer userId : userIds) {
                        Chat chat = context.getChats().get(chatId);
                        User user = context.getUsers().get(userId);
                        chat.getUsersIds().remove(userId);
                        for (int i = 0; i < user.getMyChatIds().size(); i++) {
                            if (user.getMyChatIds().get(i) == chatId) {
                                user.getMyChatIds().remove(i);
                                user.getMyChatMessagesIds().remove(i);
                                break;
                            }
                        }
                        for (int i = 0; i < user.getMyUnreadChatIds().size(); i++) {
                            if (user.getMyUnreadChatIds().get(i) == chatId) {
                                user.getMyUnreadChatIds().remove(i);
                                user.getMyUnreadChatMessagesNumbers().remove(i);
                                break;
                            }
                        }
                        context.getUsers().set(user);
                        context.getChats().set(chat);
                        if (chat.getUsersIds().size() == 0) {
                            context.getChats().delete(chatId);
                            return new DeleteChatResponse(chatId);
                        }
                    }
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
//
    public Response addPersonToChat(int chatId, List<Integer> userIds){
        try {
            synchronized (User.LOCK) {
                synchronized (Chat.lock) {
                    for (Integer userId : userIds) {
                        Chat chat = context.getChats().get(chatId);
                        User user = context.getUsers().get(userId);
                        chat.getUsersIds().add(userId);
                        user.getMyChatIds().add(chatId);
                        user.getMyChatMessagesIds().add(new LinkedList<>());
                        context.getUsers().set(user);
                        context.getChats().set(chat);
                    }
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
//
    public Response loadMembersOfChat(int chatId, boolean f) {
        try {
            Chat chat = context.getChats().get(chatId);
            List<String[]> info = new LinkedList<>();
            for (Integer id: chat.getUsersIds()) {
                User user = context.getUsers().get(id);
                String[] s = new String[5];
                s[0] = String.valueOf(id);
                s[1] = user.getAccount().getUsername();
                s[2] = loadImage(id);
                s[4] = String.valueOf(id);
                info.add(s);
            }
            if(f)
                return new LoadMemberOfChatResponse(info);
            else
                return new LoadFollowingsResponse(info);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
//
    public Response loadFollowings(int chatId) {
        try {
            User current = context.getUsers().get(clientHandler.getClientId());
            Chat chat = context.getChats().get(chatId);
            List<String[]> info = new LinkedList<>();
            for (Integer id: current.getFollowingUsername()) {
                User user = context.getUsers().get(id);
                if(!user.getBlackUsername().contains(clientHandler.getClientId())
                        && user.getAccount().isActive()
                        && !chat.getUsersIds().contains(id)){
                    String[] fo = new String[5];
                    fo[0] = String.valueOf(id);
                    fo[1] = user.getAccount().getUsername();
                    fo[2] = loadImage(id);
                    fo[4] = String.valueOf(id);
                    info.add(fo);
                }
            }
            return new LoadFollowingsResponse(info);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}