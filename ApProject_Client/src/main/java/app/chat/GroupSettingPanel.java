package app.chat;

import controller.OfflineController;
import controller.OnlinePanels;
import events.chat.*;
import network.EventListener;
import resources.Colors;
import resources.Fonts;
import resources.Images;
import resources.Texts;
import view.listeners.StringListener;
import view.usersView.UserPanel;
import view.usersView.UserPanelSelectable;
import view.usersView.UsersViewPanel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class GroupSettingPanel extends JPanel implements OnlinePanels {

    private int currentChatId;
    private String state = "chats";
    private StringListener stringListener;
    private final UsersViewPanel<UserPanel> chatsViewPanel;
    private final UsersViewPanel<UserPanelSelectable> addUserViewPanel;
    private final JScrollPane downPanel;
    private final EventListener eventListener;
    private final JLabel connectingLabel;
    private final JButton backButton;

    public GroupSettingPanel (EventListener eventListener){
        OfflineController.ONLINE_PANELS.add(this);
        connectingLabel = new JLabel(Texts.CONNECTING);
        connectingLabel.setBounds(50,0,500,200);
        connectingLabel.setFont(Fonts.WELCOME_LABEL_FONT);
        //
        this.eventListener = eventListener;
        addUserViewPanel = new UsersViewPanel<>(410,UserPanelSelectable::new);
        //
        chatsViewPanel = new UsersViewPanel<>(410,UserPanel::new);
        downPanel = new JScrollPane(chatsViewPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        downPanel.setBackground(Color.decode(Colors.MENU_PANEL_COLOR));
        downPanel.setBounds(0,120,700,580);
        //
        backButton = new JButton(Images.BACK_ICON);
        backButton.setBounds(5,10,54,45);
        backButton.setBackground(Color.decode(Colors.CHANGE_INFO_COLOR));
        //
        JButton removeButton = new JButton(Images.REMOVE_PERSON);
        removeButton.setBounds(200,10,54,45);
        removeButton.setBackground(Color.decode(Colors.CHANGE_INFO_COLOR));
        //
        JButton addButton = new JButton(Images.ADD_PERSON);
        addButton.setBounds(340,10,54,45);
        addButton.setBackground(Color.decode(Colors.CHANGE_INFO_COLOR));
        //
        JButton remove = new JButton(Texts.REMOVE);
        remove.setBounds(240,10,160,60);
        remove.setBackground(Color.decode(Colors.CHANGE_INFO_COLOR));
        remove.setFont(Fonts.BUTTONS_FONT);
        remove.setEnabled(false);
        //
        JButton add = new JButton(Texts.ADD);
        add.setBounds(240,10,160,60);
        add.setBackground(Color.decode(Colors.CHANGE_INFO_COLOR));
        add.setFont(Fonts.BUTTONS_FONT);
        add.setEnabled(false);
        //
        chatsViewPanel.setStringListener(text -> {
            if(!state.equals("members")) {
                chatsViewPanel.resetPanel();
                currentChatId = Integer.parseInt(text);
                state = "members";
                add(addButton);
                add(removeButton);
                loadMembers(currentChatId);
            }
            chatsViewPanel.resetSelectedPanels();
        });
        //
        addButton.addActionListener(e -> {
            chatsViewPanel.resetPanel();
            chatsViewPanel.resetSelectedPanels();
            remove(addButton);
            remove(removeButton);
            add(add);
            state = "add";
            loadFollowings();
        });
        //
        removeButton.addActionListener(e->{
            chatsViewPanel.resetPanel();
            chatsViewPanel.resetSelectedPanels();
            remove(addButton);
            remove(removeButton);
            add(remove);
            state = "remove";
            loadMembersForRemove(currentChatId);
        });
        //
        addUserViewPanel.setStringListener(text -> {
          if(state.equals("remove")){
              remove.setEnabled(addUserViewPanel.isAnyPanelChosen());
          }
          else if(state.equals("add")){
              add.setEnabled(addUserViewPanel.isAnyPanelChosen());
          }
        });
        //
        add.addActionListener(e->{
            loadGroupChats();
            add(currentChatId,addUserViewPanel.selectedPanelIds());
            remove(add);
            addUserViewPanel.resetSelectedPanels();
            addUserViewPanel.resetPanel();
            state = "chats";
        });
        //
        remove.addActionListener(e->{
            remove(currentChatId,addUserViewPanel.selectedPanelIds());
            remove(remove);
            addUserViewPanel.resetSelectedPanels();
            addUserViewPanel.resetPanel();
            state = "chats";
            listenMe("back");
        });
        //
        backButton.addActionListener(e->{
            switch (state) {
                case "members" -> {
                    chatsViewPanel.resetSelectedPanels();
                    chatsViewPanel.resetPanel();
                    remove(addButton);
                    remove(removeButton);
                    state = "chats";
                    loadGroupChats();
                }
                case "add" -> {
                    addUserViewPanel.resetSelectedPanels();
                    addUserViewPanel.resetPanel();
                    add(addButton);
                    add(removeButton);
                    remove(add);
                    loadMembers(currentChatId);
                    state = "members";
                }
                case "remove" -> {
                    addUserViewPanel.resetSelectedPanels();
                    addUserViewPanel.resetPanel();
                    add(addButton);
                    add(removeButton);
                    remove(remove);
                    loadMembers(currentChatId);
                    state = "members";
                }
                case "chats" -> listenMe("back");
            }
        });
        //
        setLayout(null);
        setBackground(Color.decode(Colors.INFO_PANEL));
        setBounds(0,0,410,700);
        add(backButton);
        add(downPanel);
    }

    public void setStringListener(StringListener stringListener) {
        this.stringListener = stringListener;
    }

    public void loadGroupChats(){
        eventListener.listen(new LoadGroupChatEvent());
    }

    public void setGroupChat(List<String[]> info){
        try {
            chatsViewPanel.setUserPanels(info);
            chatsViewPanel.setSize();
            chatsViewPanel.addPanels();
        } catch (IOException e) {
            e.printStackTrace();
        }
        downPanel.setViewportView(chatsViewPanel);
        repaint();
        revalidate();
    }

    public void loadMembers(int chatId){
        eventListener.listen(new LoadMemberOfChatEvent(chatId, true));
    }

    public void setMemberOfChat(List<String[]> info){
        try {
            chatsViewPanel.setUserPanels(info);
            chatsViewPanel.setSize();
            chatsViewPanel.addPanels();
        } catch (IOException e) {
            e.printStackTrace();
        }
        downPanel.setViewportView(chatsViewPanel);
        repaint();
        revalidate();
    }

    public void add(int chatId, List<Integer> userIds){
        eventListener.listen(new AddPersonEvent(chatId, userIds));
    }

    public void remove(int chatId, List<Integer> userIds){
        eventListener.listen(new RemovePersonEvent(chatId, userIds));
    }

    public void listenMe(String name){
        try {
            stringListener.stringEventOccurred(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFollowings(){
        eventListener.listen(new LoadFollowingsEvent(currentChatId));
    }

    public void setFollowings(List<String[]> info){
        try {
            addUserViewPanel.setUserPanels
                    (info);
            addUserViewPanel.setSize();
            addUserViewPanel.addPanels();
        } catch (IOException e) {
            e.printStackTrace();
        }
        downPanel.setViewportView(addUserViewPanel);
        repaint();
        revalidate();
    }

    public void loadMembersForRemove(int chatId){
        eventListener.listen(new LoadMemberOfChatEvent(chatId, false));
    }

    @Override
    public void changeState() {
        setInfo();
    }

    public void addPanels(){
        add(backButton);
        add(downPanel);
        loadGroupChats();
        repaint();
        revalidate();
    }

    public void setInfo(){
        removeAll();
        if(OfflineController.IS_ONLINE){
            addPanels();
        }
        else{
            state = "chats";
            add(backButton);
            add(connectingLabel);
            repaint();
            revalidate();
        }
    }
}