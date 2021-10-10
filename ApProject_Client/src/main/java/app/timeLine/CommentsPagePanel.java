package app.timeLine;

import app.personalPage.subPart.tweetHistory.TweetHistoryPanel;
import events.commentPage.GetOriginalTweetEvent;
import events.commentPage.LoadCommentsEvent;
import events.tweetHistory.*;
import network.EventListener;
import resources.Colors;
import resources.Images;
import resources.Texts;
import responses.commentPage.GetOriginalTweetResponse;
import responses.tweetHistory.DisLikeResponse;
import responses.tweetHistory.LikeResponse;
import responses.visitors.ResponseVisitor;
import responses.visitors.commentPage.CommentPageResponseVisitor;
import view.postView.commentPage.CommentView;
import view.postView.tweetHistory.PostView;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.function.Supplier;


public class CommentsPagePanel<T extends CommentView>
        extends TweetHistoryPanel<T> implements CommentPageResponseVisitor {

    private CommentView firstPanelCommentView;
    private final JButton backButton;
    private final GetOriginalTweetEvent getOriginalTweetEvent;
    private final JPanel firstPanel;

    public CommentsPagePanel(Supplier<? extends T> ctor, EventListener eventListener,
                             HashMap<String, ResponseVisitor> visitors,
                             String type, String type2,
                             String newCommentResponseVisitorType) {
        super(ctor, eventListener, visitors, type, newCommentResponseVisitorType);
        //
        classType = "comment";
        //
        getOriginalTweetEvent = new GetOriginalTweetEvent();
        //
        firstPanel = new JPanel();
        firstPanel.setBackground(Color.decode(Colors.PERSONAL_PAGE_DOWN_PANEL));
        firstPanel.setLayout(null);
        //
        backButton = new JButton(Images.BACK_ICON);
        backButton.setBounds(630,8,45,45);
        backButton.setFocusable(false);
        backButton.setBackground(Color.decode(Colors.SUB_PANEL));
        backButton.addActionListener(e -> {
            try {
                listenMe("back");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        visitors.put(type2, this);
        getOriginalTweetEvent.setResponseVisitorType(type2);
    }

    public void loadTweets() {
        setFirstPanel();
    }

    public void setOriginalTweetId(int id){
        getOriginalTweetEvent.setTweetId(id);
    }

    public void setOriginalMessageType(String type){
        getOriginalTweetEvent.setMessageType(type);
    }

    public void setFirstPanel(){
        firstPanel.removeAll();
        if(getOriginalTweetEvent.getMessageType() == null){
            currentPage = false;
            return;
        }
        eventListener.listen(getOriginalTweetEvent);
    }

    public void getOriginalTweet(GetOriginalTweetResponse response){
        try {
            String[] info = response.getInfo();
            firstPanelCommentView =
                    makePostView(info,
                            getOriginalTweetEvent.getMessageType());
            firstPanelCommentView.getMenuBar().setBounds(570,10,50,40);
            firstPanelCommentView.add(backButton);
            firstPanelCommentView.removeMouseListener(firstPanelCommentView.
                    getMouseListeners()[0]);
            firstPanel.add(firstPanelCommentView);
            height += 10;
            height += 50;
            firstPanel.setBounds(0,0,700,height);
            this.add(firstPanel);
            noTweetLabel.setBounds(130,height,500,140);
            height += 150;
            noTweetLabel.setText(Texts.NO_COMMENT);
            repaint();
            revalidate();
            //
            LoadCommentsEvent event = new LoadCommentsEvent();
            event.setTweetId(getOriginalTweetEvent.getTweetId());
            event.setMessageType(getOriginalTweetEvent.getMessageType());
            event.setResponseVisitorType(responseVisitorType);
            if(getOriginalTweetEvent.getMessageType() == null){
                currentPage = false;
                return;
            }
            eventListener.listen(event);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void addStringListenerToPosts(PostView postView, String type){
       postView.addStringListener(text -> {
           if(text.startsWith("like")){
               try {
                   LikeEvent event = new LikeEvent();
                   event.setTweetId(postView.getTweetId());
                   event.setMessageType(type);
                   currentPostView = postView;
                   event.setResponseVisitorType(responseVisitorType);
                   eventListener.listen(event);
               }
               catch (Exception ignored){

               }
           }
           else if(text.startsWith("dislike")){
               try {
                   DisLikeEvent disLikeEvent = new DisLikeEvent();
                   disLikeEvent.setTweetId(postView.getTweetId());
                   disLikeEvent.setMessageType(type);
                   currentPostView = postView;
                   disLikeEvent.setResponseVisitorType(responseVisitorType);
                   eventListener.listen(disLikeEvent);
               }
               catch (Exception ignored){

               }
           }
           else if(text.startsWith("share")){
               if(type.equals("tweet")) {
                   listenMe(text+"+");
               }
               else{
                   listenMe(text+"-");
               }
           }
           else if(text.startsWith("reply")) {
               listenMe(String.valueOf(postView.getTweetId()));
               if (postView.getMoreIconVisibility()) {
                   newCommentPanel.setDefaultText(
                           Texts.COMMENT_J_TEXTAREA_DEFAULT_TEXT_2);
               }
           }
           else if(text.startsWith("block")){
               BlockEvent event = new BlockEvent();
               event.setTweetId(postView.getTweetId());
               event.setMessageType(type);
               event.setResponseVisitorType(responseVisitorType);
               eventListener.listen(event);
           }
           else if(text.startsWith("unblock")){
               UnBlockEvent event = new UnBlockEvent();
               event.setTweetId(postView.getTweetId());
               event.setMessageType(type);
               event.setResponseVisitorType(responseVisitorType);
               eventListener.listen(event);
           }

           else if(text.startsWith("report")){
               ReportEvent event = new ReportEvent();
               event.setTweetId(postView.getTweetId());
               event.setMessageType(type);
               event.setResponseVisitorType(responseVisitorType);
               eventListener.listen(event);
           }
           else if(text.startsWith("mute")){
               MuteEvent event = new MuteEvent();
               event.setTweetId(postView.getTweetId());
               event.setMessageType(type);
               event.setResponseVisitorType(responseVisitorType);
               eventListener.listen(event);
           }
           else if(text.startsWith("retweet")){
               ReTweetEvent event = new ReTweetEvent();
               event.setTweetId(postView.getTweetId());
               event.setMessageType(type);
               currentPostView = postView;
               event.setResponseVisitorType(responseVisitorType);
               eventListener.listen(event);
           }
           else if(text.startsWith("goodPeople")){
               System.out.println("salam");
           }
           else if(text.startsWith("badPeople")){
               System.out.println("salam");
           }
           else{
               listenMe("commentPage" + text);
           }
       });
   }

   public void addPanels(){
        super.addPanels();
        this.add(firstPanel);
        repaint();
        revalidate();
   }

   public synchronized void getLikeText(LikeResponse likeResponse){
       int[] a = likeResponse.getInfo();
       currentPostView.setLikeText(String.valueOf(a[0]), String.valueOf(a[1]));
   }

    public synchronized void getDisLikeText(DisLikeResponse disLikeResponse){
        int[] a = disLikeResponse.getInfo();
        currentPostView.setDisLikeText(String.valueOf(a[0]), String.valueOf(a[1]));
    }

    public synchronized void getConfirmation(String type){
        if("block".equals(type)){
            for(PostView postView1: postViews){
                   postView1.setBlockItem(true);
            }
            firstPanelCommentView.setBlockItem(true);
        }
        else if("unblock".equals(type)){
            for(PostView postView1: postViews){
                postView1.setBlockItem(false);
            }
            firstPanelCommentView.setBlockItem(false);
        }
        else if("mute".equals(type)){
            try {
                listenMe("mute");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if("retweet".equals(type)){
            currentPostView.setRetweetText(String.valueOf(
                    currentPostView.getRetweetText()+1));
        }
    }

    @Override
    public void changeState() {
        if (currentPage) {
            if(getOriginalTweetEvent.getMessageType() != null) {
                setInfo();
            }
            else{
                stopLoop();
            }
        }
        else {
            removeAll();
            getTweetsLoop.stop();
            add(connectingLabel);
            repaint();
            revalidate();
        }
    }
}