package view;


import app.auth.view.AuthenticationMainPanel;
import app.bot.BotPanel;
import app.chat.ChatMainPanel;
import app.chat.ContactsPanel;
import app.explore.ExploreMainPanel;
import app.list.ListMainPanel;
import app.notification.NotificationMainPanel;
import app.personalPage.subPart.forwardMessage.ForwardPanel;
import app.personalPage.subPart.info.view.InfoPanel;
import app.personalPage.view.PersonalPageMainPanel;
import app.setting.SettingPanel;
import app.timeLine.CommentsPagePanel;
import app.timeLine.TimelinePage;
import auth.AuthToken;
import controller.OfflineController;
import events.LogoutEvent;
import models.OChat;
import network.EventListener;
import resources.Colors;
import resources.Images;
import responses.visitors.ResponseVisitor;
import responses.visitors.chat.MainPanelVisitor;
import view.listeners.StringListener;
import view.menu.MenuPanel;
import view.postView.commentPage.CommentView;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ProgramMainPanel extends JPanel implements MainPanelVisitor {

    private final BotPanel botPanel;
    private final JScrollPane contactsPanelKeeper;
    private StringListener stringListener;
    private final MenuPanel menuPanel;
    private final PersonalPageMainPanel personalPageMainPanel;
    private final InfoPanel infoPanel;
    private final TimelinePage timelineMainPanel;
    private final CommentsPagePanel<CommentView> commentsPagePanel;
    private final List<Integer> tweetHistoryKeeperMemento;
    private final JScrollPane tweetHistoryPanelKeeper;
    private final ExploreMainPanel exploreMainPanel;
    private final ChatMainPanel chatMainPanel;
    private final ContactsPanel contactsPanel;
    private final ListMainPanel listMainPanel;
    private final SettingPanel settingPanel;
    private final NotificationMainPanel notif;
    private final ForwardPanel forwardPanel;

    public ProgramMainPanel(EventListener eventListener,
                            HashMap<String, ResponseVisitor> visitors,
                            AuthToken authToken) {
        botPanel = new BotPanel(eventListener, visitors);
        forwardPanel = new ForwardPanel(eventListener, visitors, "f3");
        visitors.put("MainPanelVisitor", this);
        forwardPanel.setBounds(260,50,700,500);
        notif = new NotificationMainPanel(eventListener, visitors);
        settingPanel = new SettingPanel(eventListener, visitors);
        listMainPanel = new ListMainPanel(eventListener, visitors);
        exploreMainPanel = new ExploreMainPanel(eventListener, visitors);
        timelineMainPanel = new TimelinePage(CommentView::new, eventListener,
                visitors, "TimeLineResponsesVisitor",
                "confirmationNewCommentResponseVisitor3");
        tweetHistoryKeeperMemento = new LinkedList<>();
        tweetHistoryPanelKeeper = new JScrollPane(timelineMainPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        tweetHistoryPanelKeeper.getVerticalScrollBar().setUnitIncrement(16);
        tweetHistoryPanelKeeper.setBounds(260,50,700,700);
        tweetHistoryPanelKeeper.setBackground(Color.decode(Colors.PROFILE_MAIN_PANEL));
        commentsPagePanel = new CommentsPagePanel<>(
                CommentView::new, eventListener, visitors, "CommentPage2",
                "CommentPageResponseVisitor2",
                "confirmationNewCommentResponseVisitor4");
        OfflineController.ONLINE_PANELS.add(commentsPagePanel);
        OfflineController.ONLINE_PANELS.add(timelineMainPanel);
        timelineMainPanel.addStringListeners(text -> {
            if(text.startsWith("commentPage")){
                timelineMainPanel.stopLoop();
                commentsPagePanel.setOriginalTweetId(Integer.parseInt(
                        text.substring(11)));
                tweetHistoryKeeperMemento.add(Integer.parseInt(
                        text.substring(11)));
                if(tweetHistoryKeeperMemento.size() == 1){
                    commentsPagePanel.setOriginalMessageType("tweet");
                }
                else{
                    commentsPagePanel.setOriginalMessageType("comment");
                }
                commentsPagePanel.setInfo();
                tweetHistoryPanelKeeper.setViewportView(commentsPagePanel);
            }
            else if(text.startsWith("share")){
                tweetHistoryPanelKeeper.setVisible(false);
                forwardPanel.setMessageId(Integer.parseInt(text.substring(5,
                        text.length()-1)));
                if(text.charAt(text.length()-1) == '+') {
                    forwardPanel.setMessageType("tweet");
                }
                else{
                    forwardPanel.setMessageType("comment");
                }
                forwardPanel.getInfo();
                add(forwardPanel);
            }
            else if(text.equals("mute")){
                timelineMainPanel.setInfo();
                tweetHistoryPanelKeeper.setViewportView(timelineMainPanel);
            }
            else {
                tweetHistoryPanelKeeper.setVisible(false);
                timelineMainPanel.getNewCommentPanel().setTweetId(Integer.parseInt(text));
                timelineMainPanel.getNewCommentPanel().setBounds
                        (260,50,700,500);
                timelineMainPanel.getNewCommentPanel().setInfo();
                add(timelineMainPanel.getNewCommentPanel());
            }
            repaint();
            revalidate();
        });
        //
        commentsPagePanel.addStringListeners(text -> {
            if(text.startsWith("commentPage")){
                tweetHistoryKeeperMemento.add(Integer.parseInt(
                        text.substring(11)));
                commentsPagePanel.setOriginalTweetId(Integer.parseInt(
                        text.substring(11)));
                if(tweetHistoryKeeperMemento.size() == 1){
                    commentsPagePanel.setOriginalMessageType("tweet");
                }
                else{
                    commentsPagePanel.setOriginalMessageType("comment");
                }
                commentsPagePanel.setInfo();
                tweetHistoryPanelKeeper.setViewportView(commentsPagePanel);
            }
            else if(text.equals("back")){
                tweetHistoryKeeperMemento.remove(tweetHistoryKeeperMemento
                        .size()-1);
                if(tweetHistoryKeeperMemento.size() > 0){
                    commentsPagePanel.setOriginalTweetId(
                            tweetHistoryKeeperMemento.get(
                                    tweetHistoryKeeperMemento.size()-1));
                    if(tweetHistoryKeeperMemento.size() == 1){
                        commentsPagePanel.setOriginalMessageType("tweet");
                    }
                    else{
                        commentsPagePanel.setOriginalMessageType("comment");
                    }
                    commentsPagePanel.setInfo();
                    tweetHistoryPanelKeeper.setViewportView(commentsPagePanel);
                }
                else{
                    commentsPagePanel.stopLoop();
                    timelineMainPanel.setInfo();
                    tweetHistoryPanelKeeper.setViewportView(timelineMainPanel);
                }
            }
            else if(text.equals("mute")){
                commentsPagePanel.setInfo();
                tweetHistoryPanelKeeper.setViewportView(commentsPagePanel);
            }
            else if(text.startsWith("share")){
                tweetHistoryPanelKeeper.setVisible(false);
                forwardPanel.setMessageId(Integer.parseInt(text.substring(5,
                        text.length()-1)));
                if(text.charAt(text.length()-1) == '+') {
                    forwardPanel.setMessageType("tweet");
                }
                else{
                    forwardPanel.setMessageType("comment");
                }
                forwardPanel.getInfo();
                add(forwardPanel);
            }
            else {
                tweetHistoryPanelKeeper.setVisible(false);
                timelineMainPanel.getNewCommentPanel().setTweetId(Integer.parseInt(text));
                timelineMainPanel.getNewCommentPanel().setBounds
                        (260,50,700,500);
                timelineMainPanel.getNewCommentPanel().setInfo();
                add(timelineMainPanel.getNewCommentPanel());
            }
            repaint();
            revalidate();
        });
        //
        timelineMainPanel.getNewCommentPanel().setStringListener(text -> {
            if(text.equals("back")){
                remove(timelineMainPanel.getNewCommentPanel());
                tweetHistoryPanelKeeper.setVisible(true);
                repaint();
                revalidate();
            }
            else if(text.equals("tweetButton")){
                remove(timelineMainPanel.getNewCommentPanel());
                timelineMainPanel.loadCommentsNumberOfPost(
                        timelineMainPanel.getNewCommentPanel().getTweetId());
                commentsPagePanel.setInfo();
                tweetHistoryPanelKeeper.setVisible(true);
                repaint();
                revalidate();
            }
        });
        forwardPanel.setStringListener(text -> {
            remove(forwardPanel);
            forwardPanel.resetPanel();
            tweetHistoryPanelKeeper.setVisible(true);
            repaint();
            revalidate();
        });

        personalPageMainPanel = new PersonalPageMainPanel(eventListener, visitors);
        infoPanel = new InfoPanel(eventListener, visitors);
        infoPanel.addStringListener(text -> {
            switch (text) {
                case "username", "biography" ->
                        personalPageMainPanel.updateDefaultInfoPanel();
                case "exit" -> {
                    remove(infoPanel);
                    repaint();
                    revalidate();
                }
            }
        });
        //
        personalPageMainPanel.setStringListener(text -> {
            if(text.equals("infoPanel")){
                infoPanel.setInfoPanel();
                add(infoPanel);
                repaint();
                revalidate();
            }
        });
        chatMainPanel = new ChatMainPanel(eventListener, visitors);
        contactsPanel = new ContactsPanel(eventListener, visitors);
        contactsPanelKeeper = new JScrollPane(contactsPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        contactsPanelKeeper.setBounds(265,50,410,700);
        contactsPanelKeeper.setViewportView(contactsPanel);
        contactsPanelKeeper.getVerticalScrollBar().setUnitIncrement(16);
        contactsPanel.setStringListener(text -> {
            if(text.equals("search")){
                //reset All TODO
                remove(botPanel);
                botPanel.resetPanel();
                remove(notif);
                remove(settingPanel);
                remove(listMainPanel);
                remove(infoPanel);
                remove(personalPageMainPanel);
                remove(tweetHistoryPanelKeeper);
                remove(timelineMainPanel.getNewCommentPanel());
                remove(contactsPanelKeeper);
                remove(chatMainPanel);
                resetTimeline();
                chatMainPanel.resetPanel();
                contactsPanel.resetPanel();
                listMainPanel.resetPanel();
                personalPageMainPanel.resetPanel();
                infoPanel.resetPanel();
                exploreMainPanel.setInfo();
                add(exploreMainPanel);
            }
            else if(text.equals("setting")){
                remove(chatMainPanel);
                chatMainPanel.resetPanel();
                repaint();
                revalidate();
            }
            else {
                chatMainPanel.resetPanel();
                chatMainPanel.setChatId(Integer.parseInt(text));
                chatMainPanel.setInfo();
                contactsPanel.loadChats();
                contactsPanel.repaint();
                contactsPanel.revalidate();
                add(chatMainPanel);
            }
            repaint();
            revalidate();
        });
        //
        menuPanel = new MenuPanel();
        OfflineController.ONLINE_PANELS.add(menuPanel);
        //
        AuthenticationMainPanel authenticationMainPanel = new AuthenticationMainPanel(
                eventListener, visitors, authToken);
        authenticationMainPanel.setStringListener(text -> {
            removeAll();
            add(menuPanel);
            repaint();
            revalidate();
        });
        //
        menuPanel.setStringListener(text -> {
            if(text.equals("profile")) {
                //reset explore chats ... TODO
                remove(botPanel);
                botPanel.resetPanel();
                remove(notif);
                remove(settingPanel);
                remove(listMainPanel);
                listMainPanel.resetPanel();
                remove(exploreMainPanel);
                remove(tweetHistoryPanelKeeper);
                remove(timelineMainPanel.getNewCommentPanel());
                remove(contactsPanelKeeper);
                remove(chatMainPanel);
                resetTimeline();
                contactsPanel.resetPanel();
                chatMainPanel.resetPanel();
                exploreMainPanel.resetPanel();
                personalPageMainPanel.setDefaultInfoPanel();
                add(personalPageMainPanel);
            }
            else if(text.equals("timeline")){
                //reset explore chats ... TODO
                remove(botPanel);
                botPanel.resetPanel();
                remove(notif);
                remove(settingPanel);
                remove(listMainPanel);
                listMainPanel.resetPanel();
                remove(exploreMainPanel);
                remove(infoPanel);
                remove(personalPageMainPanel);
                remove(contactsPanelKeeper);
                remove(chatMainPanel);
                personalPageMainPanel.resetPanel();
                chatMainPanel.resetPanel();
                contactsPanel.resetPanel();
                infoPanel.resetPanel();
                timelineMainPanel.setInfo();
                exploreMainPanel.resetPanel();
                add(tweetHistoryPanelKeeper);
            }
            else if(text.equals("explore")){
//                //reset All TODO
                remove(botPanel);
                botPanel.resetPanel();
                remove(notif);
                remove(settingPanel);
                remove(listMainPanel);
                listMainPanel.resetPanel();
                remove(infoPanel);
                remove(personalPageMainPanel);
                remove(tweetHistoryPanelKeeper);
                remove(timelineMainPanel.getNewCommentPanel());
                remove(contactsPanelKeeper);
                remove(chatMainPanel);
                resetTimeline();
                chatMainPanel.resetPanel();
                contactsPanel.resetPanel();
                personalPageMainPanel.resetPanel();
                infoPanel.resetPanel();
                exploreMainPanel.setInfo();
                add(exploreMainPanel);
            }
            else if(text.equals("chat")){
                //reset All TODO
                remove(botPanel);
                botPanel.resetPanel();
                remove(notif);
                remove(settingPanel);
                remove(listMainPanel);
                listMainPanel.resetPanel();
                remove(infoPanel);
                remove(personalPageMainPanel);
                remove(tweetHistoryPanelKeeper);
                remove(timelineMainPanel.getNewCommentPanel());
                remove(exploreMainPanel);
                add(menuPanel);
                resetTimeline();
                personalPageMainPanel.resetPanel();
                infoPanel.resetPanel();
                exploreMainPanel.resetPanel();
                contactsPanel.loadChats();
                add(contactsPanelKeeper);
            }
            else if(text.equals("lists")){
//                //TODO
                remove(botPanel);
                botPanel.resetPanel();
                remove(notif);
                remove(settingPanel);
                remove(contactsPanelKeeper);
                remove(infoPanel);
                remove(personalPageMainPanel);
                remove(tweetHistoryPanelKeeper);
                remove(timelineMainPanel.getNewCommentPanel());
                remove(exploreMainPanel);
                remove(chatMainPanel);
                chatMainPanel.resetPanel();
                contactsPanel.resetPanel();
                add(menuPanel);
                resetTimeline();
                personalPageMainPanel.resetPanel();
                infoPanel.resetPanel();
                exploreMainPanel.resetPanel();
                add(listMainPanel);
            }
            else if(text.equals("setting")){
                remove(botPanel);
                botPanel.resetPanel();
                remove(notif);
                remove(listMainPanel);
                listMainPanel.resetPanel();
                remove(contactsPanelKeeper);
                remove(infoPanel);
                remove(personalPageMainPanel);
                remove(tweetHistoryPanelKeeper);
                remove(timelineMainPanel.getNewCommentPanel());
                remove(exploreMainPanel);
                remove(chatMainPanel);
                chatMainPanel.resetPanel();
                contactsPanel.resetPanel();
                add(menuPanel);
                resetTimeline();
                personalPageMainPanel.resetPanel();
                infoPanel.resetPanel();
                exploreMainPanel.resetPanel();
                settingPanel.setInfo();
                add(settingPanel);
            }
            else if(text.equals("notif")){
                remove(botPanel);
                botPanel.resetPanel();
                remove(settingPanel);
                remove(listMainPanel);
                listMainPanel.resetPanel();
                remove(contactsPanelKeeper);
                remove(infoPanel);
                remove(personalPageMainPanel);
                remove(tweetHistoryPanelKeeper);
                remove(timelineMainPanel.getNewCommentPanel());
                remove(exploreMainPanel);
                remove(chatMainPanel);
                chatMainPanel.resetPanel();
                contactsPanel.resetPanel();
                add(menuPanel);
                resetTimeline();
                personalPageMainPanel.resetPanel();
                infoPanel.resetPanel();
                exploreMainPanel.resetPanel();
                add(notif);
            }
            else if(text.equals("logout")){
                remove(botPanel);
                botPanel.resetPanel();
                remove(settingPanel);
                remove(listMainPanel);
                listMainPanel.resetPanel();
                remove(contactsPanelKeeper);
                remove(infoPanel);
                remove(personalPageMainPanel);
                remove(tweetHistoryPanelKeeper);
                remove(timelineMainPanel.getNewCommentPanel());
                remove(exploreMainPanel);
                remove(chatMainPanel);
                remove(notif);
                chatMainPanel.resetPanel();
                contactsPanel.resetPanel();
                add(menuPanel);
                resetTimeline();
                personalPageMainPanel.resetPanel();
                infoPanel.resetPanel();
                exploreMainPanel.resetPanel();
                remove(menuPanel);
                eventListener.listen(new LogoutEvent());
                add(authenticationMainPanel);
            }
            else if(text.equals("bot")){
                remove(settingPanel);
                remove(listMainPanel);
                listMainPanel.resetPanel();
                remove(contactsPanelKeeper);
                remove(infoPanel);
                remove(personalPageMainPanel);
                remove(tweetHistoryPanelKeeper);
                remove(timelineMainPanel.getNewCommentPanel());
                remove(exploreMainPanel);
                remove(chatMainPanel);
                remove(notif);
                chatMainPanel.resetPanel();
                contactsPanel.resetPanel();
                resetTimeline();
                personalPageMainPanel.resetPanel();
                infoPanel.resetPanel();
                exploreMainPanel.resetPanel();
                botPanel.resetPanel();
                add(botPanel);
            }
            else if(text.equals("connection")){
                new Thread(() -> {
                    OfflineController.changeConnectionState();
                    if(OfflineController.IS_ONLINE){
                        OfflineController.changeConnectionStateToOnline(eventListener);
                    }
                    else{
                        OfflineController.changeConnectionStateToOffline(eventListener);
                    }
                }).start();
            }
            repaint();
            revalidate();
        });
//        //
        exploreMainPanel.setStringListener(text -> {
                if(text.startsWith("message")){
                    //reset All TODO
                    remove(notif);
                    remove(settingPanel);
                    remove(listMainPanel);
                    listMainPanel.resetPanel();
                    remove(infoPanel);
                    remove(personalPageMainPanel);
                    remove(tweetHistoryPanelKeeper);
                    remove(timelineMainPanel.getNewCommentPanel());
                    remove(exploreMainPanel);
                    add(menuPanel);
                    resetTimeline();
                    personalPageMainPanel.resetPanel();
                    infoPanel.resetPanel();
                    exploreMainPanel.resetPanel();
                    contactsPanel.makeNewChat(Integer.parseInt(text.substring(7)));
                }
               else if(text.equals("list")){
                   //TODO
                    remove(notif);
                    remove(settingPanel);
                    remove(contactsPanelKeeper);
                    remove(infoPanel);
                    remove(personalPageMainPanel);
                    remove(tweetHistoryPanelKeeper);
                    remove(timelineMainPanel.getNewCommentPanel());
                    remove(exploreMainPanel);
                    remove(chatMainPanel);
                    chatMainPanel.resetPanel();
                    contactsPanel.resetPanel();
                    add(menuPanel);
                    resetTimeline();
                    personalPageMainPanel.resetPanel();
                    infoPanel.resetPanel();
                    exploreMainPanel.resetPanel();
                    add(listMainPanel);
               }
                repaint();
                revalidate();
        });
        //

        listMainPanel.setStringListener(text->{
            //TODO
            remove(notif);
            remove(listMainPanel);
            remove(infoPanel);
            remove(personalPageMainPanel);
            remove(tweetHistoryPanelKeeper);
            remove(timelineMainPanel.getNewCommentPanel());
            remove(contactsPanelKeeper);
            remove(chatMainPanel);
            resetTimeline();
            chatMainPanel.resetPanel();
            contactsPanel.resetPanel();
            personalPageMainPanel.resetPanel();
            infoPanel.resetPanel();
            exploreMainPanel.removeAll();
            exploreMainPanel.getSearchPanel().resetPanel();
            exploreMainPanel.getProfileMainPanel().setUserId(exploreMainPanel
                    .getSearchPanel().getUserId());
            exploreMainPanel.getProfileMainPanel().getProfileTopPanel().
                    setUserId(Integer.parseInt(text));
            exploreMainPanel.getProfileMainPanel().getProfileTopPanel().setPanelInfo();
            exploreMainPanel.add(exploreMainPanel.getProfileMainPanel());
            exploreMainPanel.setInfo();
            add(exploreMainPanel);
            exploreMainPanel.setWitchPanel("list");
            repaint();
            revalidate();
        });
        //
        setLayout(null);
        setPreferredSize(new Dimension(2000,800));
        if(OfflineController.IS_ONLINE) {
            add(authenticationMainPanel);
        }
        else{
            add(menuPanel);
        }
        repaint();
        revalidate();
    }

    public void listenMe(String name){
        try {
            stringListener.stringEventOccurred(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setStringListener(StringListener stringListener) {
        this.stringListener = stringListener;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(Images.START_PANEL_IMAGE,0,0,null);
    }

    public void resetTimeline() throws IOException {
        timelineMainPanel.stopLoop();
        commentsPagePanel.stopLoop();
        tweetHistoryKeeperMemento.clear();
        tweetHistoryPanelKeeper.setViewportView(timelineMainPanel);
        timelineMainPanel.getNewCommentPanel().resetPanel();
        tweetHistoryPanelKeeper.setVisible(true);
        repaint();
        revalidate();
    }

    @Override
    public void chatPanelHandling(OChat chat, boolean isNewChat) {
        if(isNewChat){
            contactsPanel.setNewChat(chat);
        }
        chatMainPanel.setChatId(chat.getId());
        contactsPanel.getUsersViewPanel().setUserId(chat.getId());
        chatMainPanel.setInfo();
        contactsPanel.loadChats();
        add(contactsPanelKeeper);
        add(chatMainPanel);
    }
}