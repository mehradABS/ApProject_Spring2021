package app.chat;


import app.chat.controller.ContactPanelController;
import controller.OfflineController;
import controller.OnlinePanels;
import db.UserDB;
import events.chat.LoadChatEvent;
import events.chat.MakeNewChatEvent;
import events.chat.MakeNewGroupEvent;
import models.OChat;
import network.EventListener;
import network.ImageReceiver;
import resources.*;
import responses.chat.LoadChatResponse;
import responses.chat.LoadGroupChatResponse;
import responses.chat.LoadMemberOfChatResponse;
import responses.visitors.ResponseVisitor;
import responses.visitors.chat.ContactPanelResponseVisitor;
import util.Loop;
import view.listeners.StringListener;
import view.usersView.UserPanel;
import view.usersView.UsersViewPanel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class ContactsPanel extends JPanel implements OnlinePanels,
        ContactPanelResponseVisitor {

    private StringListener stringListener;
    private final ContactPanelController contactPanelController;
    private final UsersViewPanel<UserPanel> usersViewPanel;
    private final JPanel upPanel;
    private final JPanel downPanel;
    private final NewGroupPanel newGroupPanel;
    private final EventListener eventListener;
    private final Loop getChatsLoop;
    private boolean currentPage = false;
    private final GroupSettingPanel groupSettingPanel;

    public ContactsPanel(EventListener eventListener, HashMap<String, ResponseVisitor>
                         visitors) {
        visitors.put("ContactPanelResponseVisitor", this);
        getChatsLoop = new Loop(1, this::sendGetChatsEvent);
        this.eventListener = eventListener;
        OfflineController.ONLINE_PANELS.add(this);
        //
        contactPanelController = new ContactPanelController();
        groupSettingPanel = new GroupSettingPanel(eventListener);
        //
        JButton searchButton = new JButton(Images.SEARCH_ICON_SMALL);
        searchButton.setFocusable(false);
        searchButton.setBackground(Color.decode(Colors.BUTTONS_COLOR));
        searchButton.setBounds(20,20,40,40);
        searchButton.addActionListener(e->{
            try {
                resetPanel();
                listenMe("search");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        //
        JButton newGroupButton = new JButton(Texts.NEW_GROUP);
        newGroupButton.setBounds(80,20,250,40);
        newGroupButton.setBackground(Color.decode(Colors.BUTTONS_COLOR));
        newGroupButton.setFocusable(false);
        newGroupButton.setFont(Fonts.BUTTONS_FONT);
        //
        usersViewPanel = new UsersViewPanel<>(410, UserPanel::new);
        usersViewPanel.setStringListener(this::listenMe);
        downPanel = new JPanel();
        downPanel.add(usersViewPanel);
        downPanel.setLayout(null);
        downPanel.setBackground(Color.decode(Colors.MENU_PANEL_COLOR));
        //
        upPanel = new JPanel();
        upPanel.setLayout(null);
        upPanel.setBounds(0,0,410,80);
        upPanel.setBackground(Color.decode(Colors.MENU_PANEL_COLOR));
        upPanel.add(newGroupButton);
        upPanel.add(searchButton);
        JButton button = new JButton(Images.SETTING1);
        button.setBackground(Color.decode(Colors.BUTTONS_COLOR));
        button.setFocusable(false);
        button.setBounds(350,20,40,40);
        upPanel.add(button);
        button.addActionListener(e -> {
            removeAll();
            stopLoop();
            try {
                listenMe("setting");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            add(groupSettingPanel);
            groupSettingPanel.setInfo();
            revalidate();
            repaint();
        });
        //
        newGroupPanel = new NewGroupPanel(eventListener);
        newGroupButton.addActionListener(e -> {
            removeAll();
            stopLoop();
            newGroupPanel.setInfo();
            add(newGroupPanel);
            repaint();
            revalidate();
        });
        //
        newGroupPanel.setStringListener(text -> {
            if(text.equals("back")){
                removeAll();
                this.add(upPanel);
                this.add(downPanel);
                loadChats();
                repaint();
                revalidate();
            }
            else if(text.equals("addGroup")){
                eventListener.listen(new MakeNewGroupEvent(newGroupPanel.getUserIds(),
                        newGroupPanel.groupName()));
                removeAll();
                this.add(upPanel);
                this.add(downPanel);
                loadChats();
                repaint();
                revalidate();
            }
        });
        //
        groupSettingPanel.setStringListener(text -> {
            if(text.equals("back")){
                removeAll();
                add(upPanel);
                add(downPanel);
                loadChats();
                repaint();
                revalidate();
            }
        });
        //
        this.setLayout(null);
        this.setBackground(Color.decode(Colors.MENU_PANEL_COLOR));
        this.add(upPanel);
        this.add(downPanel);
    }

    public void setStringListener(StringListener stringListener) {
        this.stringListener = stringListener;
    }

    public void listenMe(String name) throws IOException {
        stringListener.stringEventOccurred(name);
    }

    public void sendGetChatsEvent(){
        eventListener.listen(new LoadChatEvent());
    }

    public void getChats(LoadChatResponse response) {
        List<String[]> info = response.getChatsInfo();
        imageHandling(info);
        contactPanelController.setChatsAndUser(response);
        setChatsInfo(info);
    }

    public static void imageHandling(List<String[]> info){
        for (String[] chat: info) {
            if(chat[2].equals("def")){
                chat[2] = ((UserDB) OfflineController.getContext().getUsers())
                        .loadProfile(0, 60);
            }
            else if(chat[2].equals("groupChat")){
                chat[2] = Paths.CHAT_DEFAULT_IMAGE_PATH;
            }
            else{
                String outputImagePath2 = Paths.USER_IMAGE_PATH +
                        (chat[4])
                        + "\\profile\\profile100.png";
                String outputImagePath3 = Paths.USER_IMAGE_PATH +
                        (chat[4])
                        + "\\profile\\profile60.png";
                ImageReceiver.decodeImage(chat[2], outputImagePath2);
                ImageReceiver.decodeImage(chat[2], outputImagePath3);
                chat[2] = outputImagePath3;
            }
        }
    }

    @Override
    public void setGroupChatsInGroupSettingPanel
            (LoadGroupChatResponse response) {
        for (String[] info: response.getInfo()) {
            info[2] = Paths.CHAT_DEFAULT_IMAGE_PATH;
            info[3] = "false";
        }
        groupSettingPanel.setGroupChat(response.getInfo());
    }

    public void setMemberOfChatInGroupSettingPanel(LoadMemberOfChatResponse response){
        imageHandling(response.getInfo());
        groupSettingPanel.setMemberOfChat(response.getInfo());
    }

    public void setFollowingsInGroupSettingPanel(List<String[]> info){
        imageHandling(info);
        groupSettingPanel.setFollowings(info);
    }

    public void setNewGroupPanelInfo(List<String[]> info){
        imageHandling(info);
        try {
            newGroupPanel.setUsers(info);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setChatsInfo(List<String[]> info){
        if(info != null) {
            try {
                usersViewPanel.setUserPanels(info);
            } catch (IOException e) {
                e.printStackTrace();
            }
            usersViewPanel.addPanels();
            downPanel.setBounds(0, 80, 410, usersViewPanel.getPreferredHeight());
            this.setPreferredSize(new Dimension(420,
                    Math.max(usersViewPanel.getPreferredHeight() + 80, 690)));
            repaint();
            revalidate();
        }
    }

    public void loadChats(){
        currentPage = true;
        if(OfflineController.IS_ONLINE){
            getChatsLoop.restart();
        }
        else{
            getChatsLoop.stop();
            try {
                setChatsInfo(contactPanelController.loadChats());
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void changeState() {
        if(currentPage) {
            loadChats();
        }
        else {
            stopLoop();
        }
    }

    public void resetPanel(){
        usersViewPanel.setUserId(-1);
        removeAll();
        this.add(upPanel);
        this.add(downPanel);
        stopLoop();
        repaint();
        revalidate();
    }

    public void makeNewChat(int userId){
        eventListener.listen(new MakeNewChatEvent(userId));
    }

    public void setNewChat(OChat chat){
        contactPanelController.setNewChat(chat);
    }

    public void deleteChat(int chatId){
        contactPanelController.deleteChat(chatId);
    }

    public UsersViewPanel<UserPanel> getUsersViewPanel() {
        return usersViewPanel;
    }

    public void stopLoop(){
        getChatsLoop.stop();
        currentPage = false;
    }
}