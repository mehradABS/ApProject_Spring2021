package app.explore.watchProfile;


import controller.OfflineController;
import controller.OnlinePanels;
import db.UserDB;
import events.watchProfile.*;
import network.EventListener;
import network.ImageReceiver;
import resources.*;
import responses.visitors.ResponseVisitor;
import responses.visitors.watchProfile.WatchProfileResponseVisitor;
import responses.watchProfile.LoadInfoResponse;
import responses.watchProfile.WatchProfileResponse;
import util.Loop;
import view.listeners.StringListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ProfileTopPanel extends JPanel implements WatchProfileResponseVisitor,
        OnlinePanels {

    private final JLabel usernameLabel;
    private final JTextArea biographyText;
    private StringListener stringListener;
    private final JLabel image;
    private final JButton followButton;
    private final JButton messageButton;
    private final JMenu fileMenu;
    private final JMenuItem blockItem;
    private final JMenuItem muteItem;
    private final JMenuItem unblockItem;
    private final JMenuItem unMuteItem;
    private final JMenuBar menuBar;
    private final JLabel followings;
    private final JLabel followers;
    private final JLabel lastSeen;
    private final JPanel imagePanel;
    private final Loop getInfoLoop;
    private int userId = 1;
    private final EventListener eventListener;
    private final JLabel connectingLabel;
    private final JButton backButton;
    private boolean currentPage = false;

    public ProfileTopPanel(EventListener eventListener,
                           HashMap<String, ResponseVisitor> visitors) {
        OfflineController.ONLINE_PANELS.add(this);
        //
        getInfoLoop = new Loop(1, this::getInfo);
        this.eventListener = eventListener;
        visitors.put("WatchProfileResponseVisitor", this);
        //
        connectingLabel = new JLabel(Texts.CONNECTING);
        connectingLabel.setBounds(150,0,500,200);
        connectingLabel.setFont(Fonts.WELCOME_LABEL_FONT);
        //
        followButton = new JButton(Texts.FOLLOW);
        followButton.setBounds(540,10,100,45);
        followButton.setBackground(Color.decode(Colors.SUB_PANEL));
        followButton.setFocusable(false);
        //
        messageButton = new JButton(Images.MESSAGE_ICON);
        messageButton.setFocusable(false);
        messageButton.setBackground(Color.decode(Colors.POST_VIEW));
        messageButton.setBounds(490,10,45,45);
        //
        biographyText = new JTextArea();
        biographyText.setLineWrap(true);
        biographyText.setBackground(Color.decode(Colors.DEFAULT_INFO_PANEL));
        biographyText.setEditable(false);
        biographyText.setFont(Fonts.LABEL_FONT_INFO_PANEL);
        biographyText.setLayout(null);
        //
        image =new JLabel();
        image.setBounds(0,0,100,100);
        imagePanel = new JPanel();
        imagePanel.setLayout(null);
        imagePanel.setBounds(20,20,100,100);
        imagePanel.add(image);
        //
        usernameLabel = new JLabel();
        usernameLabel.setBounds(30,115,200,40);
        usernameLabel.setForeground(Color.decode(Colors.LABEL_COLOR));
        usernameLabel.setFont(Fonts.USERNAME_FONT);
        //
        menuBar = new JMenuBar();
        fileMenu = new JMenu(Texts.MENUBAR);
        fileMenu.setBackground(Color.decode(Colors.POST_VIEW));
        menuBar.add(fileMenu);
        menuBar.setBounds(620,10,50,40);
        menuBar.setBackground(Color.decode(Colors.POST_VIEW));
        blockItem = new JMenuItem(Images.BLOCK_ICON);
        JMenuItem reportItem = new JMenuItem(Images.REPORT_ICON);
        muteItem = new JMenuItem(Images.MUTE_ICON);
        unMuteItem = new JMenuItem(Images.UNMUTE_ICON);
        unMuteItem.setBackground(Color.decode(Colors.BLOCK));
        blockItem.setBackground(Color.decode(Colors.BLOCK));
        unblockItem = new JMenuItem(Images.UNBLOCK_ICON);
        unblockItem.setBackground(Color.decode(Colors.BLOCK));
        reportItem.setBackground(Color.decode(Colors.BLOCK));
        muteItem.setBackground(Color.decode(Colors.BLOCK));
        fileMenu.add(reportItem);
        menuBar.setBounds(430,10,50,40);
        //TODO add actionListener
        //
        backButton = new JButton(Images.BACK_ICON);
        backButton.setBounds(650,10,45,45);
        backButton.setFocusable(false);
        backButton.setBackground(Color.decode(Colors.SUB_PANEL));
        backButton.addActionListener(e -> listenMe("back"));
        //
        followers = new JLabel(Texts.FOLLOWERS);
        followings = new JLabel(Texts.FOLLOWINGS);
        followings.setBackground(Color.decode(Colors.SUB_PANEL));
        followers.setBackground(Color.decode(Colors.SUB_PANEL));
        followings.setBounds(230,150,160,40);
        followers.setBounds(400,150,160,40);
        followings.setFont(Fonts.BUTTONS_FONT);
        followers.setFont(Fonts.BUTTONS_FONT);
        lastSeen = new JLabel("lastSeenRecently");
        lastSeen.setBackground(Color.decode(Colors.SUB_PANEL));
        lastSeen.setBounds(10,150,200,40);
        lastSeen.setFont(Fonts.BUTTONS_FONT);
        //
        unMuteItem.addActionListener(e -> {
            UnMuteEvent event = new UnMuteEvent();
            event.setUserId(userId);
            eventListener.listen(event);
        });
        //
        muteItem.addActionListener(e -> {
            MuteEvent event = new MuteEvent();
            event.setUserId(userId);
            eventListener.listen(event);
        });
        //
        blockItem.addActionListener(e->{
            BlockEvent event = new BlockEvent();
            event.setUserId(userId);
            eventListener.listen(event);
        });
        //
        unblockItem.addActionListener(e->{
            UnblockEvent event = new UnblockEvent();
            event.setUserId(userId);
            eventListener.listen(event);
        });
        //
        reportItem.addActionListener(e->{
            ReportEvent event = new ReportEvent();
            event.setUserId(userId);
            eventListener.listen(event);
        });
        //
        followButton.addActionListener(e->{
            if(followButton.getText().equals(Texts.FOLLOW)){
                FollowEvent event = new FollowEvent();
                event.setUserId(userId);
                eventListener.listen(event);
            }
            else if(followButton.getText().equals(Texts.UNFOLLOW)){
                UnfollowEvent event = new UnfollowEvent();
                event.setUserId(userId);
                eventListener.listen(event);
            }
            else{
                RequestEvent event = new RequestEvent();
                event.setUserId(userId);
                eventListener.listen(event);
            }
            repaint();
            revalidate();
        });
        //
        messageButton.addActionListener(e -> listenMe("message" + userId));
        //
        this.setLayout(null);
        this.setBounds(0,0,700,200);
        this.setBackground(Color.decode(Colors.SUB_PANEL));
    }

    public void listenMe(String name){
        try {
            stringListener.stringEventOccurred(name);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void getInfo(){
        LoadInfoEvent event = new LoadInfoEvent();
        event.setUserId(userId);
        eventListener.listen(event);
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setStringListener(StringListener stringListener) {
        this.stringListener = stringListener;
    }

    @Override
    public synchronized void getAnswer(WatchProfileResponse response) {
        //do nothing
    }

    @Override
    public synchronized void setInfo(LoadInfoResponse loadInfoResponse) {
        String[] info = loadInfoResponse.getInfo();
        if (info[9].equals("def")) {
            info[9] = ((UserDB) OfflineController.getContext().getUsers()).loadProfile(
                    0, 100);
        } else {
            String outputImagePath2 = Paths.USER_IMAGE_PATH +
                    (userId)
                    + "\\profile\\profile100.png";
            String outputImagePath3 = Paths.USER_IMAGE_PATH +
                    (userId)
                    + "\\profile\\profile60.png";
            ImageReceiver.decodeImage(info[9], outputImagePath2);
            ImageReceiver.decodeImage(info[9], outputImagePath3);
            info[9] = outputImagePath2;
        }
        try {
            image.setIcon(new ImageIcon(ImageIO.read(new File(info[9]))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        usernameLabel.setText(info[6]);
        biographyText.setText(info[5]);
        lastSeen.setText(info[0]);
        followButton.setText(info[1]);
        if (info[2].equals("block")) {
            fileMenu.remove(unblockItem);
            fileMenu.add(blockItem);
        } else {
            fileMenu.add(unblockItem);
            fileMenu.remove(blockItem);
        }
        messageButton.setEnabled(info[3].equals("true"));
        if (info[4].equals("true")) {
            fileMenu.add(muteItem);
            fileMenu.remove(unMuteItem);
        } else {
            fileMenu.add(unMuteItem);
            fileMenu.remove(muteItem);
        }
        followers.setText(Texts.FOLLOWERS + info[7]);
        followings.setText(Texts.FOLLOWINGS + info[8]);
    }

    @Override
    public void changeState() {
        removeAll();
        if(currentPage){
            setPanelInfo();
        }
        else{
            add(backButton);
            add(connectingLabel);
            getInfoLoop.stop();
            repaint();
            revalidate();
        }
    }

    public void addPanels(){
        removeAll();
        this.add(imagePanel);
        this.add(usernameLabel);
        this.add(menuBar);
        this.add(followButton);
        this.add(messageButton);
        this.add(followings);
        this.add(followers);
        this.add(lastSeen);
        this.add(backButton);
        repaint();
        revalidate();
    }

    public void setPanelInfo(){
        currentPage = true;
        if(OfflineController.IS_ONLINE){
            addPanels();
            getInfoLoop.restart();
        }
        else{
            removeAll();
            getInfoLoop.stop();
            add(connectingLabel);
            add(backButton);
            repaint();
            revalidate();
        }
    }

    public synchronized void stopLoop(){
        currentPage = false;
        getInfoLoop.stop();
        image.setIcon(null);
        followings.setText(Texts.FOLLOWINGS);
        followers.setText(Texts.FOLLOWERS);
        lastSeen.setText("");
    }
}