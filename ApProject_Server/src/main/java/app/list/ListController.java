package app.list;


import app.chat.ChatPanelController;
import app.chat.ContactPanelController;
import controller.ClientHandler;
import controller.MainController;
import events.chat.EditMessageEvent;
import events.chat.SendMessageEvent;
import events.list.ListPanelEvent;
import events.visitors.list.ListPanelEventVisitor;
import models.auth.User;
import responses.Response;
import responses.chat.MakeNewChatResponse;
import responses.list.ListPanelResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class ListController extends MainController implements ListPanelEventVisitor {

    private final ChatPanelController chatPanelController;

    private final ContactPanelController contactPanelController;

    private final ClientHandler clientHandler;

    public ListController(ChatPanelController chatPanelController,
                          ContactPanelController contactPanelController,
                          ClientHandler clientHandler) {
        this.chatPanelController = chatPanelController;
        this.contactPanelController = contactPanelController;
        this.clientHandler = clientHandler;
    }

    public Response getEvent(ListPanelEvent event) {
        try {
            List<String[]> info;
            ListPanelResponse response = new ListPanelResponse();
            if("following".equals(event.getEvent())){
                info = loadFollowings();
                response.setAnswer(event.getEvent());
                response.setInfo(info);
            }
            else if("follower".equals(event.getEvent())){
                info = loadFollowers();
                response.setAnswer(event.getEvent());
                response.setInfo(info);
            }
            else if("blackList".equals(event.getEvent())){
                info = loadBlackList();
                response.setAnswer(event.getEvent());
                response.setInfo(info);
            }
            else if("loadList".equals(event.getEvent())
                || "loadListForRemove".equals(event.getEvent())){
                info = loadLists();
                response.setAnswer(event.getEvent());
                response.setInfo(info);
            }
            else if("loadUsersOfList".equals(event.getEvent())
                || "loadUsersOfListForRemove".equals(event.getEvent())){
                info = loadUsersOfLists(event.getListName());
                response.setAnswer(event.getEvent());
                response.setInfo(info);
            }
            else if("loadUsersForAdd".equals(event.getEvent())){
                info = loadFollowingsForAddTOList(event.getListName());
                response.setAnswer(event.getEvent());
                response.setInfo(info);
            }
            else if("addList".equals(event.getEvent())){
                addList(event.getListName());
                return null;
            }
            else if("removePerson".equals(event.getEvent())){
                for (Integer id: event.getIds()) {
                    removeUser(event.getListName(), id);
                }
                return null;
            }
            else if("deleteList".equals(event.getEvent())){
                for (String name: event.getNames()) {
                    removeList(name);
                }
                return null;
            }
            else if("addPerson".equals(event.getEvent())){
                for (Integer id: event.getIds()) {
                    addUser(event.getListName(), id);
                }
                return null;
            }
            else if("sendMessage".equals(event.getEvent())){
                sendMessage(event);
                return null;
            }
            return response;
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public List<String[]> loadFollowings() throws IOException {
        User current = context.getUsers().get(clientHandler.getClientId());
        List<String[]> info = new LinkedList<>();
        for (Integer id: current.getFollowingUsername()) {
            User user = context.getUsers().get(id);
            if(!user.getBlackUsername().contains(clientHandler.getClientId())
                    && user.getAccount().isActive()) {
                String[] fo = new String[5];
                fo[0] = String.valueOf(id);
                fo[1] = user.getAccount().getUsername();
                fo[2] = ContactPanelController.loadImage(id);
                fo[4] = String.valueOf(id);
                info.add(fo);
            }
        }
        return info;
    }

    public List<String[]> loadFollowers() throws IOException {
        User current = context.getUsers().get(clientHandler.getClientId());
        List<String[]> info = new LinkedList<>();
        for (Integer id: current.getFollowerUsername()) {
            User user = context.getUsers().get(id);
            if(!user.getBlackUsername().contains(clientHandler.getClientId())
                    && user.getAccount().isActive()){
                String[] fo = new String[5];
                fo[0] = String.valueOf(id);
                fo[1] = user.getAccount().getUsername();
                fo[2] = ContactPanelController.loadImage(id);
                fo[4] = String.valueOf(id);
                info.add(fo);
            }
        }
        return info;
    }

    public List<String[]> loadBlackList() throws IOException {
        User current = context.getUsers().get(clientHandler.getClientId());
        List<String[]> info = new LinkedList<>();
        for (Integer id:current.getBlackUsername()) {
            User user = context.getUsers().get(id);
            if(user.getAccount().isActive()) {
                String[] fo = new String[5];
                fo[0] = String.valueOf(id);
                fo[1] = user.getAccount().getUsername();
                fo[2] = ContactPanelController.loadImage(id);
                fo[4] = String.valueOf(id);
                info.add(fo);
            }
        }
        return info;
    }

    public List<String[]> loadLists() throws IOException {
        User current = context.getUsers().get(clientHandler.getClientId());
        List<String[]> info = new LinkedList<>();
        for (User.ListName listName: current.getFollowingsList()) {
            String[] fo = new String[4];
            fo[0] = listName.getName();
            fo[1] = listName.getName();
            fo[2] = "groupChat";
            fo[3] = "false";
            info.add(fo);
        }
        return info;
    }

    public List<String[]> loadUsersOfLists(String listName) throws IOException {
        User current = context.getUsers().get(clientHandler.getClientId());
        List<String[]> info = new LinkedList<>();
        int index = 0;
        for (int i = 0; i < current.getFollowingsList().size(); i++) {
            if(current.getFollowingsList().get(i).getName().equals(listName)){
                index = i;
                break;
            }
        }
        for (Integer id:current.getFollowingsList().get(index).getUsernames()) {
            User user = context.getUsers().get(id);
            if(user.getAccount().isActive()) {
                String[] fo = new String[5];
                fo[0] = String.valueOf(id);
                fo[1] = user.getAccount().getUsername();
                fo[2] = ContactPanelController.loadImage(id);
                fo[3] = "false";
                fo[4] = String.valueOf(id);
                info.add(fo);
            }
        }
        return info;
    }


    public void sendMessage(ListPanelEvent listEvent) throws IOException {
        User current = context.getUsers().get(clientHandler.getClientId());
        int index = 0;
        for (int i = 0; i < current.getFollowingsList().size(); i++) {
            if(current.getFollowingsList().get(i).getName().equals(listEvent
            .getListName())) {
                index = i;
                break;
            }
        }
        SendMessageEvent event = new EditMessageEvent();
        event.setLocalDateTime(LocalDateTime.now());
        event.setText(listEvent.getText());
        event.setEncodedImage(listEvent.getEncodedImage());
        for (Integer id: current.getFollowingsList().get(index).getUsernames()) {
            Response response = contactPanelController.makeNewChat(id);
            event.setChatId(((MakeNewChatResponse)response).getChatID());
            chatPanelController.newMessage(event);
        }
    }

    public void addUser(String listName, int userId) throws IOException {
        synchronized (User.LOCK) {
            User current = context.getUsers().get(clientHandler.getClientId());
            for (int i = 0; i < current.getFollowingsList().size(); i++) {
                if (current.getFollowingsList().get(i).getName().equals(listName)) {
                    current.getFollowingsList().get(i).getUsernames().add(userId);
                    break;
                }
            }
            context.getUsers().set(current);
        }
    }

    public void removeUser(String listName, int userId) throws IOException {
        synchronized (User.LOCK) {
            User current = context.getUsers().get(clientHandler.getClientId());
            for (int i = 0; i < current.getFollowingsList().size(); i++) {
                if (current.getFollowingsList().get(i).getName().equals(listName)) {
                    current.getFollowingsList().get(i).getUsernames().remove(userId);
                    break;
                }
            }
            context.getUsers().set(current);
        }
    }

    public List<String[]> loadFollowingsForAddTOList(String listName)
            throws IOException {
        User current = context.getUsers().get(clientHandler.getClientId());
        int index = 0;
        for (int i = 0; i < current.getFollowingsList().size(); i++) {
            if(current.getFollowingsList().get(i).getName().equals(listName)){
                index = i;
                break;
            }
        }
        List<String[]> info = new LinkedList<>();
        for (Integer id:current.getFollowingUsername()) {
            User user = context.getUsers().get(id);
            if(!user.getBlackUsername().contains(clientHandler.getClientId())
                    && user.getAccount().isActive()
             && !current.getFollowingsList().get(index).getUsernames().contains(id)){
                String[] fo = new String[5];
                fo[0] = String.valueOf(id);
                fo[1] = user.getAccount().getUsername();
                fo[2] = ContactPanelController.loadImage(id);
                fo[3] = "false";
                fo[4] = String.valueOf(id);
                info.add(fo);
            }
        }
        return info;
    }

    public void addList(String name) throws IOException {
        synchronized (User.LOCK) {
            User current = context.getUsers().get(clientHandler.getClientId());
            User.ListName listName = new User.ListName();
            listName.setName(name);
            current.getFollowingsList().add(listName);
            context.getUsers().set(current);
        }
    }

    public void removeList(String name) throws IOException {
        synchronized (User.LOCK) {
            User current = context.getUsers().get(clientHandler.getClientId());
            for (int i = 0; i < current.getFollowingsList().size(); i++) {
                if (current.getFollowingsList().get(i).getName().equals(name)) {
                    current.getFollowingsList().remove(i);
                    break;
                }
            }
            context.getUsers().set(current);
        }
    }
}
