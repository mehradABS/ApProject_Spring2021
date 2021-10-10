package app.list;

import app.chat.AddPhotoPanel;
import app.chat.ContactsPanel;
import events.list.ListPanelEvent;
import network.EventListener;
import resources.Colors;
import resources.Fonts;
import resources.Images;
import resources.Texts;
import responses.list.ListPanelResponse;
import responses.visitors.ResponseVisitor;
import responses.visitors.list.ListPanelResponseVisitor;
import view.listeners.StringListener;
import view.postView.message.MessageSenderPanel;
import view.usersView.UserPanel;
import view.usersView.UserPanelSelectable;
import view.usersView.UsersViewPanel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class ListMainPanel extends JPanel implements ListPanelResponseVisitor {

    private String currentListName;
    private String state;
    private final JButton followersButton;
    private final JButton followingsButton;
    private final JButton blackListButton;
    private final JButton myListButton;
    private final JButton addListButton;
    private final JButton backButton;
    private UsersViewPanel<UserPanel> usersViewPanel;
    private final UsersViewPanel<UserPanel> listsViewPanel;
    private final UsersViewPanel<UserPanelSelectable> addUserViewPanel;
    private StringListener stringListener;
    private final JLabel noFriendLabel;
    private final JScrollPane downPanel;
    private final JTextArea textArea;
    private final JLabel defaultText;
    private final MessageSenderPanel messageSenderPanel;
    private final AddPhotoPanel addPhotoPanel;
    private final EventListener eventListener;

    public ListMainPanel(EventListener eventListener, HashMap<String, ResponseVisitor>
                         visitors) {
        addPhotoPanel = new AddPhotoPanel(eventListener, visitors, "null");
        visitors.put("ListPanelResponseVisitor", this);
        this.eventListener = eventListener;
        //
        messageSenderPanel = new MessageSenderPanel(615,800);
        //
        JButton add = new JButton(Texts.ADD);
        JButton remove = new JButton(Texts.REMOVE);
        add.setBounds(300,10,160,60);
        add.setBackground(Color.decode(Colors.CHANGE_INFO_COLOR));
        add.setFont(Fonts.BUTTONS_FONT);
        remove.setBounds(300,10,160,60);
        remove.setBackground(Color.decode(Colors.CHANGE_INFO_COLOR));
        remove.setFont(Fonts.BUTTONS_FONT);
        //
        JButton addButton = new JButton(Images.ADD_PERSON);
        addButton.setBounds(200,10,54,45);
        addButton.setBackground(Color.decode(Colors.CHANGE_INFO_COLOR));
        addListButton = new JButton(Images.ADD_LIST);
        JButton removeListButton = new JButton(Images.REMOVE_LIST);
        JButton removeButton = new JButton(Images.REMOVE_PERSON);
        removeButton.setBounds(500,10,54,45);
        removeButton.setBackground(Color.decode(Colors.CHANGE_INFO_COLOR));
        backButton = new JButton(Images.BACK_ICON);
        backButton.setBounds(5,10,54,45);
        backButton.setBackground(Color.decode(Colors.CHANGE_INFO_COLOR));
        textArea = new JTextArea();
        textArea.setBackground(Color.decode(Colors.CHANGE_INFO_COLOR));
        textArea.setBounds(150,10,300,46);
        textArea.setFont(Fonts.TWEET_FONT);
        addListButton.setBackground(Color.decode(Colors.CHANGE_INFO_COLOR));
        addListButton.setBounds(80, 10, 54, 45);
        removeListButton.setBackground(Color.decode(Colors.CHANGE_INFO_COLOR));
        removeListButton.setBounds(500, 10, 54, 45);
        defaultText = new JLabel(Texts.LIST_NAME);
        defaultText.setFont(Fonts.TWEET_FONT);
        defaultText.setBounds(0, -7, 200, 40);
        textArea.add(defaultText);
        textArea.getDocument().putProperty("name", "TextArea");
        addListButton.setEnabled(false);
        addListButton.setFocusable(false);
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                defaultText.setVisible(false);
                addListButton.setEnabled(true);
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                if (textArea.getText().isEmpty()) {
                    defaultText.setVisible(true);
                    addListButton.setEnabled(false);
                }
            }
            @Override
            public void changedUpdate(DocumentEvent e) {}
        });
        //
        listsViewPanel = new UsersViewPanel<>(800,UserPanel::new);
        listsViewPanel.setStringListener(text -> {
            remove(addListButton);
            remove(textArea);
            remove(removeListButton);
            add(addButton);
            add(messageSenderPanel);
            add(removeButton);
            state = "usersList";
            currentListName = text;
            usersViewPanel.resetSelectedPanels();
            usersViewPanel.resetPanel();
            listsViewPanel.resetSelectedPanels();
            ListPanelEvent event = new ListPanelEvent();
            event.setEvent("loadUsersOfList");
            event.setListName(text);
            eventListener.listen(event);
            repaint();
            revalidate();
        });
        addUserViewPanel = new UsersViewPanel<>(800,UserPanelSelectable::new);
        //
        noFriendLabel = new JLabel(Texts.NO_PEOPLE);
        noFriendLabel.setBounds(300,140,600,200);
        noFriendLabel.setForeground(Color.decode(Colors.WELCOME_LABEL_COLOR));
        noFriendLabel.setFont(Fonts.WELCOME_LABEL_FONT);
        //
        followersButton = new JButton(Texts.FOLLOWERS);
        followersButton.setBounds(30,10,150,40);
        followersButton.setBackground(Color.decode(Colors.BUTTONS_COLOR));
        followersButton.setFont(Fonts.BUTTONS_FONT);
        followersButton.setFocusable(false);
        followersButton.addActionListener(e->{
            ListPanelEvent event = new ListPanelEvent();
            event.setEvent("follower");
            eventListener.listen(event);
        });
        //
        followingsButton = new JButton(Texts.FOLLOWINGS);
        followingsButton.setBounds(220,10,150,40);
        followingsButton.setBackground(Color.decode(Colors.BUTTONS_COLOR));
        followingsButton.setFont(Fonts.BUTTONS_FONT);
        followingsButton.setFocusable(false);
        followingsButton.addActionListener(e->{
            ListPanelEvent event = new ListPanelEvent();
            event.setEvent("following");
            eventListener.listen(event);
        });
        //
        blackListButton = new JButton(Texts.BLACK_LIST);
        blackListButton.setBounds(410,10,150,40);
        blackListButton.setBackground(Color.decode(Colors.BUTTONS_COLOR));
        blackListButton.setFont(Fonts.BUTTONS_FONT);
        blackListButton.setFocusable(false);
        blackListButton.addActionListener(e->{
            ListPanelEvent event = new ListPanelEvent();
            event.setEvent("blackList");
            eventListener.listen(event);
        });
        //
        myListButton = new JButton(Texts.MY_LIST);
        myListButton.setBounds(600,10,150,40);
        myListButton.setBackground(Color.decode(Colors.BUTTONS_COLOR));
        myListButton.setFont(Fonts.BUTTONS_FONT);
        myListButton.setFocusable(false);
        myListButton.addActionListener(e->{
            state = "list";
            remove(followersButton);
            remove(followingsButton);
            remove(myListButton);
            remove(blackListButton);
            add(backButton);
            add(textArea);
            add(addListButton);
            add(removeListButton);
            ListPanelEvent event = new ListPanelEvent();
            event.setEvent("loadList");
            eventListener.listen(event);
            repaint();
            revalidate();
        });
        //
        usersViewPanel = new UsersViewPanel<>(800,UserPanel::new);
        usersViewPanel.setStringListener(text -> {
            usersViewPanel.resetSelectedPanels();
            listenMe(text);
        });
        downPanel = new JScrollPane(usersViewPanel,
                 JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        downPanel.setBackground(Color.decode(Colors.MENU_PANEL_COLOR));
        downPanel.setBounds(0,120,800,490);
        //
        backButton.addActionListener(e->{
            switch (state) {
                case "list" -> {
                    add(followersButton);
                    add(followingsButton);
                    add(myListButton);
                    add(blackListButton);
                    remove(backButton);
                    remove(textArea);
                    remove(addListButton);
                    remove(removeListButton);
                    addUserViewPanel.resetSelectedPanels();
                    addUserViewPanel.resetPanel();
                    usersViewPanel.resetPanel();
                    usersViewPanel.resetSelectedPanels();
                    listsViewPanel.resetSelectedPanels();
                    downPanel.setViewportView(usersViewPanel);
                }
                case "removeList" -> {
                    remove(remove);
                    state = "list";
                    remove(followersButton);
                    remove(followingsButton);
                    remove(myListButton);
                    remove(blackListButton);
                    add(backButton);
                    add(textArea);
                    add(addListButton);
                    add(removeListButton);
                    addUserViewPanel.resetSelectedPanels();
                    addUserViewPanel.resetPanel();
                    usersViewPanel.resetSelectedPanels();
                    listsViewPanel.resetSelectedPanels();
                    ListPanelEvent event = new ListPanelEvent();
                    event.setEvent("loadList");
                    eventListener.listen(event);
                    repaint();
                    revalidate();
                }
                case "removeUser", "addUser" -> {
                    remove(remove);
                    remove(add);
                    remove(addListButton);
                    remove(textArea);
                    remove(removeListButton);
                    add(addButton);
                    add(messageSenderPanel);
                    add(removeButton);
                    state = "usersList";
                    addUserViewPanel.resetSelectedPanels();
                    addUserViewPanel.resetPanel();
                    usersViewPanel.resetSelectedPanels();
                    listsViewPanel.resetSelectedPanels();
                    ListPanelEvent event1 = new ListPanelEvent();
                    event1.setEvent("loadUsersOfList");
                    event1.setListName(currentListName);
                    eventListener.listen(event1);
                    repaint();
                    revalidate();
                }
                default -> {
                    state = "list";
                    remove(removeButton);
                    remove(addButton);
                    remove(messageSenderPanel);
                    messageSenderPanel.resetPanel();
                    remove(followersButton);
                    remove(followingsButton);
                    remove(myListButton);
                    remove(blackListButton);
                    add(backButton);
                    add(textArea);
                    add(addListButton);
                    addUserViewPanel.resetSelectedPanels();
                    addUserViewPanel.resetPanel();
                    usersViewPanel.resetSelectedPanels();
                    listsViewPanel.resetSelectedPanels();
                    ListPanelEvent event2 = new ListPanelEvent();
                    event2.setEvent("loadList");
                    eventListener.listen(event2);
                }
            }
            repaint();
            revalidate();
        });
        //
        addListButton.addActionListener(e->{
            usersViewPanel.resetPanel();
            String name = textArea.getText();
            textArea.setText("");
            textArea.add(defaultText);
            addListButton.setEnabled(false);
            ListPanelEvent event1 = new ListPanelEvent();
            ListPanelEvent event2 = new ListPanelEvent();
            event1.setEvent("loadList");
            event2.setEvent("addList");
            event2.setListName(name);
            eventListener.listen(event2);
            eventListener.listen(event1);
        });
        //
        removeListButton.addActionListener(e -> {
            state = "removeList";
            remove(addListButton);
            remove(textArea);
            remove(removeListButton);
            add(remove);
            remove.setEnabled(false);
            ListPanelEvent event = new ListPanelEvent();
            event.setEvent("loadListForRemove");
            eventListener.listen(event);
            repaint();
            revalidate();
        });
        //
        removeButton.addActionListener(e -> {
            state = "removeUser";
            remove(removeButton);
            remove(addButton);
            remove(messageSenderPanel);
            messageSenderPanel.resetPanel();
            add(remove);
            remove.setEnabled(false);
            ListPanelEvent event = new ListPanelEvent();
            event.setEvent("loadUsersOfListForRemove");
            event.setListName(currentListName);
            eventListener.listen(event);
            repaint();
            revalidate();
        });
        //
        addButton.addActionListener(e->{
            state = "addUser";
            remove(removeButton);
            remove(addButton);
            remove(messageSenderPanel);
            messageSenderPanel.resetPanel();
            add(add);
            add.setEnabled(false);
            ListPanelEvent event = new ListPanelEvent();
            event.setEvent("loadUsersForAdd");
            event.setListName(currentListName);
            eventListener.listen(event);
            repaint();
            revalidate();
        });
        //
        remove.setFocusable(false);
        add.setFocusable(false);
        remove.addActionListener(e->{
            ListPanelEvent event = new ListPanelEvent();
            ListPanelEvent event1 = new ListPanelEvent();
            if(state.equals("removeUser")){
                event1.setEvent("removePerson");
                event1.setListName(currentListName);
                event1.setIds(addUserViewPanel.selectedPanelIds());
                eventListener.listen(event1);
                remove.setEnabled(false);
                addUserViewPanel.resetPanel();
                event.setEvent("loadUsersOfListForRemove");
                event.setListName(currentListName);
            }
            else{
                event1.setEvent("deleteList");
                event1.setNames(addUserViewPanel.selectedPanelNames());
                eventListener.listen(event1);
                addUserViewPanel.resetPanel();
                event.setEvent("loadListForRemove");
            }
            eventListener.listen(event);
            repaint();
            revalidate();
        });
        //
        add.addActionListener(e->{
            ListPanelEvent event1 = new ListPanelEvent();
            event1.setEvent("addPerson");
            event1.setIds(addUserViewPanel.selectedPanelIds());
            event1.setListName(currentListName);
            eventListener.listen(event1);
            ListPanelEvent event = new ListPanelEvent();
            event.setEvent("loadUsersOfList");
            event.setListName(currentListName);
            eventListener.listen(event);
            add(removeButton);
            add(addButton);
            add(messageSenderPanel);
            add.setEnabled(false);
            state = "usersList";
            addUserViewPanel.resetPanel();
            remove(add);
            repaint();
            revalidate();
        });
        //
        addUserViewPanel.setStringListener(text -> {
            if(state.equals("removeList") || state.equals("removeUser")) {
                if (text.equals("exit")) {
                    if (!addUserViewPanel.isAnyPanelChosen()) {
                        remove.setEnabled(false);
                    }
                } else {
                    if (addUserViewPanel.isAnyPanelChosen()) {
                        remove.setEnabled(true);
                    }
                }
            }
            else{
                if (text.equals("exit")) {
                    if (!addUserViewPanel.isAnyPanelChosen()) {
                        add.setEnabled(false);
                    }
                } else {

                    if (addUserViewPanel.isAnyPanelChosen()) {
                        add.setEnabled(true);
                    }
                }
            }
        });
        //
        messageSenderPanel.setStringListener(text -> {
            if(text.equals("send")){
                ListPanelEvent event = new ListPanelEvent();
                event.setEvent("sendMessage");
                event.setListName(currentListName);
                event.setText(messageSenderPanel.getTextArea().getText());
                event.setEncodedImage(addPhotoPanel.getEncodedImage());
                eventListener.listen(event);
                addPhotoPanel.setEncodedImage("null");
                messageSenderPanel.resetPanel();
                downPanel.setBounds(0,120,800,490);
            }
            else if(text.equals("photo")){
                 removeAll();
                 add(addPhotoPanel);
                 add(messageSenderPanel);
                 repaint();
                 revalidate();
                 addPhotoPanel.addPhotoButtonAction();
            }
            else{
                downPanel.setBounds(0,120,800,490 -
                        Integer.parseInt(text));
            }
        });
        //
        addPhotoPanel.setStringListener(text->{
            if(text.equals("back")){
                remove(addPhotoPanel);
                add(addButton);
                add(messageSenderPanel);
                add(downPanel);
                add(removeButton);
                state = "usersList";
                ListPanelEvent event = new ListPanelEvent();
                event.setEvent("loadUsersOfList");
                event.setListName(currentListName);
                eventListener.listen(event);
                repaint();
                revalidate();
            }
        });
        //
        this.setLayout(null);
        this.setBackground(Color.decode(Colors.SUB_PANEL));
        this.setBounds(265,50,800,700);
        this.add(followersButton);
        this.add(followingsButton);
        this.add(blackListButton);
        this.add(myListButton);
        this.add(downPanel);
    }

    public void getResponse(ListPanelResponse response){
        if("following".equals(response.getAnswer())){
            setFollowings(response.getInfo());
        }
        else if("follower".equals(response.getAnswer())){
            setFollowers(response.getInfo());
        }
        else if("blackList".equals(response.getAnswer())){
            setBlackUsers(response.getInfo());
        }
        else if("loadList".equals(response.getAnswer())){
            setLists(response.getInfo());
        }
        else if("loadListForRemove".equals(response.getAnswer())){
            setListsForRemove(response.getInfo());
        }
        else if("loadUsersOfList".equals(response.getAnswer())){
            setUsersOfLists(response.getInfo());
        }
        else if("loadUsersOfListForRemove".equals(response.getAnswer())){
            setUsersForRemove(response.getInfo());
        }
        else if("loadUsersForAdd".equals(response.getAnswer())){
            setUsersForAdd(response.getInfo());
        }
    }

    public void setFollowings(List<String[]> info){
        ContactsPanel.imageHandling(info);
        for (String[] i: info) {
            i[3] = "false";
        }
        try {
            usersViewPanel.setUserPanels(info);
        } catch (IOException e) {
            e.printStackTrace();
        }
        usersViewPanel.setSize();
        usersViewPanel.addPanels();
        if(usersViewPanel.getUserPanelList().size() == 0){
            usersViewPanel.add(noFriendLabel);
        }
        downPanel.setViewportView(usersViewPanel);
        repaint();
        revalidate();
    }

    public void setFollowers(List<String[]> info) {
        ContactsPanel.imageHandling(info);
        for (String[] i: info) {
            i[3] = "false";
        }
        try {
            usersViewPanel.setUserPanels(info);
        } catch (IOException e) {
            e.printStackTrace();
        }
        usersViewPanel.setSize();
        usersViewPanel.addPanels();
        if(usersViewPanel.getUserPanelList().size() == 0){
            usersViewPanel.add(noFriendLabel);
        }
        downPanel.setViewportView(usersViewPanel);
        repaint();
        revalidate();
    }

    public void setBlackUsers(List<String[]> info) {
        ContactsPanel.imageHandling(info);
        for (String[] i: info) {
            i[3] = "false";
        }
        try {
            usersViewPanel.setUserPanels(info);
        } catch (IOException e) {
            e.printStackTrace();
        }
        usersViewPanel.setSize();
        usersViewPanel.addPanels();
        if(usersViewPanel.getUserPanelList().size() == 0){
            usersViewPanel.add(noFriendLabel);
        }
        downPanel.setViewportView(usersViewPanel);
        repaint();
        revalidate();
    }


    public void setLists(List<String[]> info) {
        ContactsPanel.imageHandling(info);
        try {
            listsViewPanel.setUserPanels(info);
        } catch (IOException e) {
            e.printStackTrace();
        }
        listsViewPanel.setSize();
        listsViewPanel.addPanels();
        if(listsViewPanel.getUserPanelList().size() == 0){
            listsViewPanel.add(noFriendLabel);
        }
        downPanel.setViewportView(listsViewPanel);
        repaint();
        revalidate();
    }

    public void setListsForRemove(List<String[]> info){
        ContactsPanel.imageHandling(info);
        try {
            addUserViewPanel.setUserPanels(info);
        } catch (IOException e) {
            e.printStackTrace();
        }
        addUserViewPanel.setSize();
        addUserViewPanel.addPanels();
        if(addUserViewPanel.getUserPanelList().size() == 0){
            addUserViewPanel.add(noFriendLabel);
        }
        downPanel.setViewportView(addUserViewPanel);
        repaint();
        revalidate();
    }

    public void setUsersOfLists(List<String[]> info){
        ContactsPanel.imageHandling(info);
        try {
            usersViewPanel.setUserPanels(info);
        } catch (IOException e) {
            e.printStackTrace();
        }
        usersViewPanel.setSize();
        usersViewPanel.addPanels();
        if(usersViewPanel.getUserPanelList().size() == 0){
            usersViewPanel.add(noFriendLabel);
        }
        listsViewPanel.resetPanel();
        downPanel.setViewportView(usersViewPanel);
        repaint();
        revalidate();
    }


    public void setUsersForRemove(List<String[]> info) {
        ContactsPanel.imageHandling(info);
        try {
            addUserViewPanel.setUserPanels(info);
        } catch (IOException e) {
            e.printStackTrace();
        }
        addUserViewPanel.setSize();
        addUserViewPanel.addPanels();
        if(addUserViewPanel.getUserPanelList().size() == 0){
            addUserViewPanel.add(noFriendLabel);
        }
        usersViewPanel.resetPanel();
        downPanel.setViewportView(addUserViewPanel);
        repaint();
        revalidate();
    }

    public void setUsersForAdd(List<String[]> info) {
        ContactsPanel.imageHandling(info);
        try {
            addUserViewPanel.setUserPanels(info);
        } catch (IOException e) {
            e.printStackTrace();
        }
        addUserViewPanel.setSize();
        addUserViewPanel.addPanels();
        if(addUserViewPanel.getUserPanelList().size() == 0){
            addUserViewPanel.add(noFriendLabel);
        }
        usersViewPanel.resetPanel();
        downPanel.setViewportView(addUserViewPanel);
        repaint();
        revalidate();
    }

    public void resetPanel(){
        removeAll();
        usersViewPanel.resetPanel();
        addUserViewPanel.resetPanel();
        listsViewPanel.resetPanel();
        this.add(followersButton);
        this.add(followingsButton);
        this.add(blackListButton);
        this.add(myListButton);
        this.add(downPanel);
        repaint();
        revalidate();
    }

    public void setStringListener(StringListener stringListener){
        this.stringListener = stringListener;
    }

    public void listenMe(String text){
        try {
            stringListener.stringEventOccurred(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public EventListener getEventListener() {
        return eventListener;
    }
}