package app.personalPage.subPart.tweetHistory;

import app.personalPage.subPart.tweetHistory.subPart.NewCommentPanel;
import controller.OfflineController;
import controller.OnlinePanels;
import db.UserDB;
import events.tweetHistory.*;
import network.EventListener;
import network.ImageReceiver;
import resources.Colors;
import resources.Fonts;
import resources.Paths;
import resources.Texts;
import responses.tweetHistory.DisLikeResponse;
import responses.tweetHistory.LikeResponse;
import responses.tweetHistory.LoadTweetsResponse;
import responses.visitors.ResponseVisitor;
import responses.visitors.tweetHistory.TweetHistoryResponsesVisitor;
import util.Loop;
import view.listeners.StringListener;
import view.postView.tweetHistory.PostView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class TweetHistoryPanel<T extends PostView> extends JPanel
        implements TweetHistoryResponsesVisitor,
        OnlinePanels {

    protected boolean currentPage = false;
    protected final JLabel connectingLabel;
    protected PostView currentPostView;
    protected final EventListener eventListener;
    protected final Loop getTweetsLoop;
    protected int height = 0;
    protected final Supplier<? extends T> ctor;
    protected final NewCommentPanel newCommentPanel;
    protected List<String[]> tweetsInfo;
    protected final JLabel noTweetLabel;
    protected final List<PostView> postViews;
    protected final List<StringListener> stringListeners;
    protected String classType;
    protected String responseVisitorType;

    public TweetHistoryPanel(Supplier<? extends T> ctor, EventListener eventListener,
                             HashMap<String, ResponseVisitor> visitors,
                             String type, String newCommentResponseVisitor) {
        this.eventListener = eventListener;
        visitors.put(type, this);
        this.responseVisitorType = type;
        //
        getTweetsLoop = new Loop(1, this::loadTweets);
        //
        this.ctor  = Objects.requireNonNull(ctor);
        //
        stringListeners = new LinkedList<>();
        //
        newCommentPanel = new NewCommentPanel(eventListener, visitors,
                newCommentResponseVisitor);
        //
        postViews = new LinkedList<>();
        //
        noTweetLabel = new JLabel(Texts.NO_TWEET);
        noTweetLabel.setBounds(180,130,400,200);
        noTweetLabel.setForeground(Color.decode(Colors.WELCOME_LABEL_COLOR));
        noTweetLabel.setFont(Fonts.NO_TWEET_LABEL_FONT);
        //
        connectingLabel = new JLabel(Texts.CONNECTING);
        connectingLabel.setBounds(150,0,500,200);
        connectingLabel.setFont(Fonts.WELCOME_LABEL_FONT);
        //
        classType = "tweet";
        //
        this.setLayout(null);
        this.setBackground(Color.decode(Colors.PERSONAL_PAGE_DOWN_PANEL));
    }


    public NewCommentPanel getNewCommentPanel() {
        return newCommentPanel;
    }

    public void listenMe(String name) throws IOException {
             for (StringListener stringListener: stringListeners){
                 stringListener.stringEventOccurred(name);
             }
    }

    public void addStringListeners(StringListener stringListener){
        stringListeners.add(stringListener);
    }

    public void loadTweets() {
        eventListener.listen(new LoadTweetsEvent());
    }

    public boolean checkTweetIds(){
        int l = tweetsInfo.size() - 1;
        if(l != postViews.size()){
            return false;
        }
        for (int i = l; i >=0 ; i--) {
            if(postViews.get(l - i).getTweetId() != Integer.parseInt(
                    tweetsInfo.get(i)[6])){
                return false;
            }
        }
        return true;
    }

    public synchronized void getTweets(LoadTweetsResponse response){
            this.tweetsInfo = response.getTweetsInfo();
            if(postViews.size() == 0 || postViews.size() != tweetsInfo.size()
                || !checkTweetIds()) {
                try {
                    postViews.clear();
                    this.makeTweetsPanels();
                    this.addPanels();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                int l = tweetsInfo.size() - 1;
                for (int i = l; i >=0 ; i--) {
                    try {
                        setPostViewInfo(postViews.get(l - i), tweetsInfo.get(i));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        height = 0;
    }

    public void makeTweetsPanels() throws IOException {
        for (int i = tweetsInfo.size()-1; i >=0 ; i--) {
            postViews.add(makePostView(tweetsInfo.get(i), classType));
        }
        this.setPreferredSize(new Dimension(700,height));
    }

    public T makePostView(String[] info, String type) throws IOException {
        T postView = ctor.get();
        setPostViewInfo(postView, info);
        postView.setTweetId(Integer.parseInt(info[6]));
        addStringListenerToPosts(postView, type);
        postView.setSizes(0,height);
        height += postView.getHeight() + 50;
        return postView;
    }

    public void setPostViewInfo(PostView postView, String[] info) throws IOException{
        ImageIcon icon = null;
        if(!info[1].equals("null")){
            String localPath = Paths.TWEET_IMAGE_PATH + info[6] + ".png";
            ImageReceiver.decodeImage(info[1], localPath);
            icon = new ImageIcon(ImageIO.read(new File(localPath)));
        }
        if(info[3].equals("def")){
            info[3] = ((UserDB)OfflineController.getContext().getUsers()).loadProfile(
                    0, 60);
        }
        else{
            String outputImagePath2 = Paths.USER_IMAGE_PATH +
                    (info[12])
                    +"\\profile\\profile100.png";
            String outputImagePath3 = Paths.USER_IMAGE_PATH+
                    (info[12])
                    +"\\profile\\profile60.png";
            ImageReceiver.decodeImage(info[3], outputImagePath2);
            ImageReceiver.decodeImage(info[3], outputImagePath3);
            info[3] = outputImagePath3;
        }
        postView.setInfo(info[0],icon,
                info[2],new ImageIcon
                        (ImageIO.read(new File(info[3]))),
                Integer.parseInt(info[4]),
                Integer.parseInt(info[5]),
                Integer.parseInt(info[8]));
        postView.setBlockItem(Boolean.parseBoolean(info[9]));
        postView.setMoreIcon(!Boolean.parseBoolean(info[10]));
        postView.setRetweetText(info[11]);
        postView.addComponent(Boolean.parseBoolean(info[7]));
    }

    public void addPanels(){
        removeAll();
        for(PostView postView: postViews){
            this.add(postView);
        }
        if(postViews.size() == 0){
            this.add(noTweetLabel);
        }
        repaint();
        revalidate();
    }

    public void loadCommentsNumberOfPost(int tweetId){
        for (PostView postView : postViews) {
            if (postView.getTweetId() == tweetId) {
                currentPostView = postView;
                GetNumberOfCommentEvent event = new GetNumberOfCommentEvent();
                event.setTweetId(currentPostView.getTweetId());
                event.setMessageType("tweet");
                event.setResponseVisitorType(responseVisitorType);
                eventListener.listen(event);
            }
        }
    }

    public synchronized void getCommentsNumber(int number){
        currentPostView.setReplyText(String.valueOf(number));
    }

    public void addStringListenerToPosts(PostView postView, String type){
        postView.addStringListener(text -> {
            if(text.startsWith("like")){
                LikeEvent event = new LikeEvent();
                event.setTweetId(postView.getTweetId());
                event.setMessageType(type);
                currentPostView = postView;
                event.setResponseVisitorType(responseVisitorType);
                eventListener.listen(event);
            }
            else if(text.startsWith("dislike")){
                DisLikeEvent disLikeEvent = new DisLikeEvent();
                disLikeEvent.setTweetId(postView.getTweetId());
                disLikeEvent.setMessageType(type);
                currentPostView = postView;
                disLikeEvent.setResponseVisitorType(responseVisitorType);
                eventListener.listen(disLikeEvent);
            }
            else if(text.startsWith("share")){
                if(type.equals("tweet")) {
                    listenMe(text+"+");
                }
                else{
                    listenMe(text+"-");
                }
            }
            else if(text.startsWith("reply")){
                listenMe(String.valueOf(postView.getTweetId()));
                if (postView.getMoreIconVisibility()) {
                    newCommentPanel.setDefaultText(
                            Texts.COMMENT_J_TEXTAREA_DEFAULT_TEXT_2);
                }
            }
            else if(text.startsWith("block")){
                BlockEvent event = new BlockEvent();
                event.setTweetId(postView.getTweetId());
                event.setMessageType("tweet");
                event.setResponseVisitorType(responseVisitorType);
                eventListener.listen(event);
            }
            else if(text.startsWith("unblock")){
                UnBlockEvent event = new UnBlockEvent();
                event.setTweetId(postView.getTweetId());
                event.setMessageType("tweet");
                event.setResponseVisitorType(responseVisitorType);
                eventListener.listen(event);
            }
            else if(text.startsWith("report")){
                ReportEvent event = new ReportEvent();
                event.setTweetId(postView.getTweetId());
                event.setMessageType("tweet");
                event.setResponseVisitorType(responseVisitorType);
                eventListener.listen(event);
            }
            else if(text.startsWith("mute")){
                MuteEvent event = new MuteEvent();
                event.setTweetId(postView.getTweetId());
                event.setMessageType("tweet");
                event.setResponseVisitorType(responseVisitorType);
                eventListener.listen(event);
            }
            else if(text.startsWith("retweet")) {
                ReTweetEvent event = new ReTweetEvent();
                event.setTweetId(postView.getTweetId());
                event.setMessageType("tweet");
                currentPostView = postView;
                event.setResponseVisitorType(responseVisitorType);
                eventListener.listen(event);
            }
            else{
                listenMe("commentPage" + text);
            }
        });
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
        }
        else if("unblock".equals(type)){
            for(PostView postView1: postViews){
                postView1.setBlockItem(false);
            }
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

    public void stopLoop(){
        currentPage = false;
        getTweetsLoop.stop();
    }

    @Override
    public void changeState() {
        if (currentPage) {
            setInfo();
        }
        else {
            removeAll();
            getTweetsLoop.stop();
            add(connectingLabel);
            repaint();
            revalidate();
        }
    }

    public void setInfo(){
        currentPage = true;
        postViews.clear();
        height = 0;
        if(OfflineController.IS_ONLINE) {
            getTweetsLoop.restart();
        }
        else{
            getTweetsLoop.stop();
            removeAll();
            add(connectingLabel);
            repaint();
            revalidate();
        }
    }
}