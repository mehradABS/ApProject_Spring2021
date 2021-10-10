package app.personalPage.view;


import app.personalPage.subPart.forwardMessage.ForwardPanel;
import app.personalPage.subPart.info.view.DefaultInfoPanel;
import app.personalPage.subPart.newPost.NewPostPanel;
import app.personalPage.subPart.tweetHistory.TweetHistoryPanel;
import app.timeLine.CommentsPagePanel;
import controller.OfflineController;
import network.EventListener;
import resources.Colors;
import responses.visitors.ResponseVisitor;
import view.listeners.StringListener;
import view.postView.commentPage.CommentView;
import view.postView.tweetHistory.PostView;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class PersonalPageMainPanel extends JPanel {

    private StringListener stringListener;

    private final DefaultInfoPanel defaultInfoPanel;

    private final NewPostPanel newPostPanel;

    private final CommentsPagePanel<CommentView> commentsPagePanel;

    private final List<Integer> tweetHistoryKeeperMemento;

    private final JScrollPane tweetHistoryPanelKeeper;

    private final TweetHistoryPanel<PostView> tweetHistoryPanel;

    private final ForwardPanel forwardPanel;

    public PersonalPageMainPanel(EventListener eventListener,
                                 HashMap<String, ResponseVisitor> visitors){
        forwardPanel = new ForwardPanel(eventListener, visitors, "f2");
        commentsPagePanel = new CommentsPagePanel<>(
                CommentView::new, eventListener, visitors, "CommentPage1",
                "CommentPageResponseVisitor1",
                "confirmationNewCommentResponseVisitor2");
        tweetHistoryPanel = new TweetHistoryPanel<>
                (PostView::new, eventListener, visitors,
                        "TweetHistoryResponsesVisitor",
                        "confirmationNewCommentResponseVisitor1");
        OfflineController.ONLINE_PANELS.add(tweetHistoryPanel);
        OfflineController.ONLINE_PANELS.add(commentsPagePanel);
        tweetHistoryKeeperMemento = new LinkedList<>();
        tweetHistoryPanelKeeper = new JScrollPane(tweetHistoryPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        tweetHistoryPanelKeeper.getVerticalScrollBar().setUnitIncrement(16);
        tweetHistoryPanelKeeper.setBounds(0,200,700,500);
        defaultInfoPanel = new DefaultInfoPanel();
        //
        newPostPanel = new NewPostPanel(eventListener, visitors,
                "confirmationNewTweetResponseVisitor");
        OfflineController.ONLINE_PANELS.add(newPostPanel);
        newPostPanel.setStringListener(text -> {
            if(text.equals("tweetButton")){
                removeAll();
                add(defaultInfoPanel);
                if(tweetHistoryKeeperMemento.size() > 0){
                    commentsPagePanel.setInfo();
                }
                else{
                    tweetHistoryPanel.setInfo();
                }
                add(tweetHistoryPanelKeeper);
                revalidate();
                repaint();
            }
        });
        //
        defaultInfoPanel.setStringListener(text -> {
            switch (text) {
                case "newTweet" -> {
                    removeAll();
                    tweetHistoryPanel.stopLoop();
                    commentsPagePanel.stopLoop();
                    add(defaultInfoPanel);
                    newPostPanel.setInfo();
                    add(newPostPanel);
                    repaint();
                    revalidate();
                }
                case "info" -> listenMe("infoPanel");
                case "tweetHistory" -> {
                    removeAll();
                    newPostPanel.resetPanel();
                    add(defaultInfoPanel);
                    if(tweetHistoryKeeperMemento.size() > 0){
                        commentsPagePanel.setInfo();
                    }
                    else{
                        tweetHistoryPanel.setInfo();
                    }
                    add(tweetHistoryPanelKeeper);
                    revalidate();
                    repaint();
                }
            }
        });
        //
        tweetHistoryPanel.addStringListeners(text -> {
            if(text.startsWith("commentPage")){
                tweetHistoryPanel.stopLoop();
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
            else if(text.equals("mute")){
                tweetHistoryPanel.setInfo();
                tweetHistoryPanelKeeper.setViewportView(tweetHistoryPanel);
            }
            else if(text.startsWith("share")){
                defaultInfoPanel.setVisible(false);
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
                defaultInfoPanel.setVisible(false);
                tweetHistoryPanelKeeper.setVisible(false);
                tweetHistoryPanel.getNewCommentPanel().setTweetId(Integer.parseInt(text));
                tweetHistoryPanel.getNewCommentPanel().setInfo();
                add(tweetHistoryPanel.getNewCommentPanel());
            }
            repaint();
            revalidate();
        });
        //
        commentsPagePanel.addStringListeners(text -> {
            if(text.startsWith("commentPage")){
                commentsPagePanel.stopLoop();
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
                tweetHistoryPanel.stopLoop();
                commentsPagePanel.stopLoop();
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
                    tweetHistoryPanel.setInfo();
                    tweetHistoryPanelKeeper.setViewportView(tweetHistoryPanel);
                }
            }
            else if(text.equals("mute")){
                commentsPagePanel.setInfo();
                tweetHistoryPanelKeeper.setViewportView(commentsPagePanel);
            }
            else if(text.startsWith("share")){
                defaultInfoPanel.setVisible(false);
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
                defaultInfoPanel.setVisible(false);
                tweetHistoryPanelKeeper.setVisible(false);
                tweetHistoryPanel.getNewCommentPanel().setTweetId(Integer.parseInt(text));
                tweetHistoryPanel.getNewCommentPanel().setInfo();
                add(tweetHistoryPanel.getNewCommentPanel());
            }
            repaint();
            revalidate();
        });

        //
        tweetHistoryPanel.getNewCommentPanel().setStringListener(text -> {
            if(text.equals("back")){
                remove(tweetHistoryPanel.getNewCommentPanel());
                defaultInfoPanel.setVisible(true);
                tweetHistoryPanelKeeper.setVisible(true);
                repaint();
                revalidate();
            }
            else if(text.equals("tweetButton")){
                remove(tweetHistoryPanel.getNewCommentPanel());
                defaultInfoPanel.setVisible(true);
                tweetHistoryPanel.loadCommentsNumberOfPost(
                        tweetHistoryPanel.getNewCommentPanel().getTweetId());
                commentsPagePanel.setInfo();
                tweetHistoryPanelKeeper.setVisible(true);
                repaint();
                revalidate();
            }
        });

        forwardPanel.setStringListener(text -> {
            remove(forwardPanel);
            forwardPanel.resetPanel();
            defaultInfoPanel.setVisible(true);
            tweetHistoryPanelKeeper.setVisible(true);
            repaint();
            revalidate();
        });

        this.setLayout(null);
        this.setBackground(Color.decode(Colors.PROFILE_MAIN_PANEL));
        this.setBounds(260,50,700,700);
    }

    public void setDefaultInfoPanel() throws IOException {
        defaultInfoPanel.setInfo();
        this.add(defaultInfoPanel);
    }

    public void setStringListener(StringListener stringListener) {
        this.stringListener = stringListener;
    }

    public void listenMe(String name) throws IOException {
        stringListener.stringEventOccurred(name);
    }

    public void updateDefaultInfoPanel() throws IOException {
        defaultInfoPanel.setInfo();
        defaultInfoPanel.repaint();
        defaultInfoPanel.revalidate();
    }

    public void resetPanel() throws IOException {
        this.removeAll();
        tweetHistoryPanel.stopLoop();
        commentsPagePanel.stopLoop();
        this.add(defaultInfoPanel);
        tweetHistoryKeeperMemento.clear();
        tweetHistoryPanelKeeper.setViewportView(tweetHistoryPanel);
        tweetHistoryPanel.getNewCommentPanel().resetPanel();
        defaultInfoPanel.setVisible(true);
        tweetHistoryPanelKeeper.setVisible(true);
        repaint();
        revalidate();
    }
}