package app.explore;

import app.explore.search.SearchPanel;
import app.explore.watchProfile.ProfileMainPanel;
import app.personalPage.subPart.forwardMessage.ForwardPanel;
import app.timeLine.CommentsPagePanel;
import controller.OfflineController;
import network.EventListener;
import resources.Colors;
import responses.visitors.ResponseVisitor;
import view.listeners.StringListener;
import view.postView.commentPage.CommentView;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ExploreMainPanel extends JPanel {

    private String witchPanel = " ";
    private final SearchPanel searchPanel;
    private StringListener stringListener;
    private final ProfileMainPanel profileMainPanel;
    private final RandomTweetPanel randomTweetPanel;
    private final List<Integer> tweetHistoryKeeperMemento;
    private final JScrollPane tweetHistoryPanelKeeper;
    private final CommentsPagePanel<CommentView> commentsPagePanel;
    private final ForwardPanel forwardPanel;

    public ExploreMainPanel(EventListener eventListener,
                            HashMap<String, ResponseVisitor> visitors) {
        forwardPanel = new ForwardPanel(eventListener, visitors, "f1");
        randomTweetPanel = new RandomTweetPanel(CommentView::new, visitors,
                eventListener,
                "RandomTweetPanelResponsesVisitor",
                "confirmationNewCommentResponseVisitor5");
        OfflineController.ONLINE_PANELS.add(randomTweetPanel);
        searchPanel = new SearchPanel(eventListener, visitors);
        OfflineController.ONLINE_PANELS.add(searchPanel);
        //
        profileMainPanel = new ProfileMainPanel(eventListener, visitors);
        profileMainPanel.setStringListener(text -> {
            if(text.equals("back")){
                if(witchPanel.equals("list")){
                    witchPanel = "";
                    listenMe("list");
                }
                else {
                    resetPanel();
                    randomTweetPanel.setInfo();
                }
                repaint();
                revalidate();
            }
            else if(text.startsWith("message")){
                listenMe(text);
            }
        });
        //
        commentsPagePanel = new CommentsPagePanel<>(
                CommentView::new, eventListener, visitors,"CommentPage3",
                "CommentPageResponseVisitor3"
        ,"confirmationNewCommentResponseVisitor6");
        OfflineController.ONLINE_PANELS.add(commentsPagePanel);
        //
        searchPanel.setStringListener(text -> {
            searchPanel.resetPanel();
            randomTweetPanel.stopLoop();
            commentsPagePanel.stopLoop();
            removeAll();
            profileMainPanel.setUserId(searchPanel.getUserId());
            profileMainPanel.getProfileTopPanel().setUserId(searchPanel.getUserId());
            profileMainPanel.getProfileTopPanel().setPanelInfo();
            add(profileMainPanel);
            repaint();
            revalidate();
        });
        //
        tweetHistoryKeeperMemento = new LinkedList<>();
        tweetHistoryPanelKeeper = new JScrollPane(randomTweetPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        tweetHistoryPanelKeeper.getVerticalScrollBar().setUnitIncrement(16);
        tweetHistoryPanelKeeper.setBounds(0,160,700,550);
        tweetHistoryPanelKeeper.setBackground(Color.decode(Colors.PROFILE_MAIN_PANEL));
        OfflineController.ONLINE_PANELS.add(randomTweetPanel);
        OfflineController.ONLINE_PANELS.add(commentsPagePanel);
        //
        randomTweetPanel.addStringListeners(text -> {
            if(text.startsWith("commentPage")){
                randomTweetPanel.stopLoop();
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
                randomTweetPanel.setInfo();
                tweetHistoryPanelKeeper.setViewportView(randomTweetPanel);
            }
            else if(text.startsWith("share")){
                searchPanel.setVisible(false);
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
                randomTweetPanel.getNewCommentPanel().setTweetId(Integer.parseInt(text));
                randomTweetPanel.getNewCommentPanel().setBounds
                        (0,160,700,550);
                randomTweetPanel.getNewCommentPanel().setInfo();
                add(randomTweetPanel.getNewCommentPanel());
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
                    randomTweetPanel.setInfo();
                    tweetHistoryPanelKeeper.setViewportView(randomTweetPanel);
                }
            }
            else if(text.equals("mute")){
                commentsPagePanel.setInfo();
                tweetHistoryPanelKeeper.setViewportView(commentsPagePanel);
            }
            else if(text.startsWith("share")){
                searchPanel.setVisible(false);
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
                randomTweetPanel.getNewCommentPanel().setTweetId(Integer.parseInt(text));
                randomTweetPanel.getNewCommentPanel().setBounds
                        (0,160,700,550);
                randomTweetPanel.getNewCommentPanel().setInfo();
                add(randomTweetPanel.getNewCommentPanel());
            }
            repaint();
            revalidate();
        });
        //
        randomTweetPanel.getNewCommentPanel().setStringListener(text -> {
            if(text.equals("back")){
                remove(randomTweetPanel.getNewCommentPanel());
                tweetHistoryPanelKeeper.setVisible(true);
                repaint();
                revalidate();
            }
            else if(text.equals("tweetButton")){
                remove(randomTweetPanel.getNewCommentPanel());
                randomTweetPanel.loadCommentsNumberOfPost(
                        randomTweetPanel.getNewCommentPanel().getTweetId());
                commentsPagePanel.setInfo();
                tweetHistoryPanelKeeper.setVisible(true);
                repaint();
                revalidate();
            }
        });
        //
        forwardPanel.setStringListener(text -> {
            remove(forwardPanel);
            forwardPanel.resetPanel();
            searchPanel.setVisible(true);
            tweetHistoryPanelKeeper.setVisible(true);
            repaint();
            revalidate();
        });
        //
        this.setLayout(null);
        this.setBackground(Color.decode(Colors.INFO_PANEL));
        this.setBounds(260,50,700,700);
        this.add(searchPanel);
        add(tweetHistoryPanelKeeper);
    }

    public SearchPanel getSearchPanel() {
        return searchPanel;
    }

    public void setStringListener(StringListener stringListener) {
        this.stringListener = stringListener;
    }

    public void listenMe(String name){
        try {
            stringListener.stringEventOccurred(name);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public ProfileMainPanel getProfileMainPanel() {
        return profileMainPanel;
    }

    public void resetPanel(){
        removeAll();
        searchPanel.resetPanel();
        profileMainPanel.resetPanel();
        try {
            resetRandomTweetsPanel();
        } catch (IOException e) {
            e.printStackTrace();
        }
        commentsPagePanel.stopLoop();
        randomTweetPanel.stopLoop();
        add(tweetHistoryPanelKeeper);
        searchPanel.setInfo();
        add(searchPanel);
        repaint();
        revalidate();

        //TODO
    }

    public void setWitchPanel(String witchPanel) {
        this.witchPanel = witchPanel;
    }

    public void resetRandomTweetsPanel() throws IOException {
        tweetHistoryKeeperMemento.clear();
        tweetHistoryPanelKeeper.setViewportView(randomTweetPanel);
        randomTweetPanel.getNewCommentPanel().resetPanel();
        tweetHistoryPanelKeeper.setVisible(true);
        repaint();
        revalidate();
    }

    public void setInfo(){
        searchPanel.setInfo();
        randomTweetPanel.setInfo();
    }
}