package app.personalPage.tweetHistory;

import models.Report;
import models.auth.User;
import models.messages.Comment;
import models.messages.Message;
import models.messages.Tweet;
import controller.ClientHandler;
import controller.MainController;
import controller.log.Log;
import events.visitors.tweetHistory.TweetHistoryEventsVisitor;
import network.ImageSender;
import responses.Response;
import responses.tweetHistory.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedList;

public class TweetHistoryController extends MainController
        implements TweetHistoryEventsVisitor {

    private final ClientHandler clientHandler;

    public TweetHistoryController(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public Response loadTweets() {
        LinkedList<String[]> allInfo = new LinkedList<>();
        LoadTweetsResponse response = new LoadTweetsResponse();
        try {
            User user = context.getUsers().get(clientHandler.getClientId());
            for(Integer tweetId: user.getTweetsID()){
                Tweet tweet = context.getTweets().get(tweetId, "tweet");
                User user1 = context.getUsers().get(tweet.getUserId());
                if(tweet.getUserId() != user.getId()) {
                    allInfo.add(loadTweetInfo(tweet, user1, user.
                            getAccount().getUsername() + " retweeted: "));
                }
                else {
                    allInfo.add(loadTweetInfo(tweet, user1, ""));
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        response.setTweetsInfo(allInfo);
        return response;
    }

    public <T extends Message>
    String[] loadTweetInfo(T tweet, User user, String retweetUsername) throws IOException {
        String [] info = new String[13];
        info[0] =retweetUsername + tweet.getText();
        File file = new File("src\\Save\\images\\tweets\\"+tweet.getId()+".png");
        if(file.exists()){
            String path = "src\\Save\\images\\tweets\\"+tweet.getId()+".png";
            info[1] = ImageSender.encodeImage(path);
        }
        else {
            info[1] = "null";
        }
        String time = tweet.getLocalDateTime().getMonth().toString()
                +", "+tweet.getLocalDateTime().getDayOfMonth()+" -- "+
                tweet.getLocalDateTime().getHour()
                +": "+tweet.getLocalDateTime().getMinute();
        info[2] = "<html>"+user.getAccount().getUsername()+"<br>"+time+"</html>";
        File file1 = new File("src\\Save\\images\\"+
                (user.getId())
                +"\\profile\\profile60.png");
        if(file1.exists()){
            info[3] = ImageSender.encodeImage("src\\Save\\images\\" +
                    (user.getId())
                    +"\\profile\\profile60.png");
        }
        else {
            info[3] = "def";
        }
        info[4] = String.valueOf(tweet.getLike().size());
        info[5] = String.valueOf(tweet.getDislike().size());
        info[6] = String.valueOf(tweet.getId());
        boolean like = tweet.getLike().contains(clientHandler.getClientId());
        info[7] = String.valueOf(like);
        info[8] = String.valueOf(tweet.getCommentsId().size());
        info[9] = String.valueOf(context.getUsers().get(clientHandler.getClientId()).
                getBlackUsername()
        .contains(user.getId()));
        info[10] = String.valueOf(clientHandler.getClientId() == tweet.getUserId());
        if(tweet.getRetweet_userId() != null) {
            info[11] = String.valueOf(tweet.getRetweet_userId().size() - 1);
        }
        else{
            info[11] = "0";
        }
        info[12] = String.valueOf(user.getId());
        return info;
    }

    public Response like(int id, String type, String responseVisitorType) {
        LikeResponse likeResponse = new LikeResponse();
        likeResponse.setVisitorType(responseVisitorType);
        try {
            synchronized (User.LOCK) {
                synchronized (Message.LOCK) {
                    if (type.equals("tweet")) {
                        User user = context.getUsers().get(clientHandler.getClientId());
                        Tweet tweet = context.getTweets().get(id, "tweet");
                        tweet.getLike().add(user.getId());
                        user.getFavouriteID().add(tweet.getId());
                        tweet.getDislike().remove(user.getId());
                        context.getUsers().set(user);
                        context.getTweets().set(tweet, "tweet");
                        Log log = new Log("liked his/her tweet", LocalDateTime.now(),
                                2, clientHandler.getClientId());
                        Log.log(log);
                        int[] a = new int[2];
                        a[0] = tweet.getLike().size();
                        a[1] = tweet.getDislike().size();
                        likeResponse.setInfo(a);
                    } else {
                        Comment comment = context.getComments().get(id, "comment");
                        comment.getLike().add(clientHandler.getClientId());
                        comment.getDislike().remove(clientHandler.getClientId());
                        context.getComments().set(comment, "comment");
                        Log log = new Log("liked a tweet/comment", LocalDateTime.now(),
                                2, clientHandler.getClientId());
                        Log.log(log);
                        int[] a = new int[2];
                        a[0] = comment.getLike().size();
                        a[1] = comment.getDislike().size();
                        likeResponse.setInfo(a);
                    }
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return likeResponse;
    }

    public Response disLike(int id , String type, String responseVisitorType) {
        DisLikeResponse disLikeResponse = new DisLikeResponse();
        disLikeResponse.setVisitorType(responseVisitorType);
        try {
            synchronized (User.LOCK) {
                synchronized (Message.LOCK) {
                    if (type.equals("tweet")) {
                        Tweet tweet = context.getTweets().get(id, "tweet");
                        User user = context.getUsers().get(clientHandler.getClientId());
                        tweet.getLike().remove(user.getId());
                        int a = tweet.getId();
                        user.getFavouriteID().remove(a);
                        tweet.getDislike().add(user.getId());
                        context.getUsers().set(user);
                        context.getTweets().set(tweet, "tweet");
                        Log log = new Log("disliked his/her tweet", LocalDateTime.now(),
                                2, clientHandler.getClientId());
                        Log.log(log);
                        int[] b = new int[2];
                        b[0] = tweet.getLike().size();
                        b[1] = tweet.getDislike().size();
                        disLikeResponse.setInfo(b);
                    } else {
                        Comment comment = context.getComments().get(id, "comment");
                        comment.getLike().remove(clientHandler.getClientId());
                        comment.getDislike().add(clientHandler.getClientId());
                        context.getComments().set(comment, "comment");
                        Log log = new Log("disliked a tweet/comment", LocalDateTime.now(),
                                2, clientHandler.getClientId());
                        Log.log(log);
                        int[] b = new int[2];
                        b[0] = comment.getLike().size();
                        b[1] = comment.getDislike().size();
                        disLikeResponse.setInfo(b);
                    }
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return disLikeResponse;
    }

    public Response sendCommentsNumber
            (int tweetId, String type, String responseVisitorType) {
        GetCommentResponse response = new GetCommentResponse();
        response.setVisitorType(responseVisitorType);
        try {
            if(type.equals("tweet")) {
                Tweet tweet = context.getTweets().get(tweetId, "tweet");
                response.setCommentsNumber(tweet.getCommentsId().size());
            }
            else {
                Comment comment = context.getComments().get(tweetId, "tweet");
                response.setCommentsNumber(comment.getCommentsId().size());
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return response;
    }

    public Response block(int id, String type, String responseVisitorType) {
        try {
            synchronized (User.LOCK) {
                User user = context.getUsers().get(clientHandler.getClientId());
                if (type.equals("comment")) {
                    Comment comment = context.getComments().get
                            (id, "comment");
                    blockHandling(comment, user);
                } else if (type.equals("tweet")) {
                    Tweet comment = context.getTweets().get
                            (id, "tweet");
                    blockHandling(comment, user);
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        ConfirmationResponse response = new ConfirmationResponse();
        response.setVisitorType(responseVisitorType);
        response.setType("block");
        return response;
    }

    public void blockHandling(Message comment, User user)
            throws IOException {
        user.getBlackUsername().add(comment.getUserId());
        context.getUsers().set(user);
        User user1 = context.getUsers().get(comment.getUserId());
        user1.getSystemMessages().add(
                user.getAccount()
                        .getUsername() + " Blocked you   " +
                        LocalDateTime.now());
        context.getUsers().set(user1);
        Log log = new Log("blocked someone",LocalDateTime.now(),
                2, clientHandler.getClientId());
        Log.log(log);
    }

    public void unblockHandling(Message comment, User user) throws IOException {
        user.getBlackUsername().remove(comment.getUserId());
        context.getUsers().set(user);
        User user1 = context.getUsers().get(comment.getUserId());
        user1.getSystemMessages().add(
                user.getAccount()
                        .getUsername() + " unBlocked you   " +
                        LocalDateTime.now());
        context.getUsers().set(user1);
        Log log = new Log("unblocked someone", LocalDateTime.now(), 2,
                clientHandler.getClientId());
        Log.log(log);
    }

    public Response unblock(int id, String type, String responseVisitorType) {
        try {
            synchronized (User.LOCK) {
                User user = context.getUsers().get(clientHandler.getClientId());
                if (type.equals("comment")) {
                    Comment comment = context.getComments().get
                            (id, "comment");
                    unblockHandling(comment, user);
                } else if (type.equals("tweet")) {
                    Tweet comment = context.getTweets().get
                            (id, "tweet");
                    unblockHandling(comment, user);
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        ConfirmationResponse response = new ConfirmationResponse();
        response.setVisitorType(responseVisitorType);
        response.setType("unblock");
        return response;
    }

    public void reportHandling(Message message, User user)
            throws IOException {
        User user1 = context.getUsers().get(message.getUserId());
        synchronized(Report.LOCK){
            Report rp = context.getProgram().getReports();
            rp.getFirstId().add(user.getId());
            rp.getLastId().add(user1.getId());
            context.getProgram().setReports(rp);
        }
        Log log = new Log("report someone",LocalDateTime.now(),2,
                clientHandler.getClientId());
        Log.log(log);
    }

    public Response report(int id, String type, String responseVisitorType) {
        try {
            User user = context.getUsers().get(clientHandler.getClientId());
            if(type.equals("comment")){
                Comment comment = context.getComments().get
                        (id, "comment");
                reportHandling(comment, user);
            }
            else if(type.equals("tweet")){
                Tweet comment = context.getTweets().get
                        (id, "tweet");
                reportHandling(comment, user);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        ConfirmationResponse response = new ConfirmationResponse();
        response.setVisitorType(responseVisitorType);
        response.setType("report");
        return response;
    }

   public void muteHandling(Message message, User user)
           throws IOException {
       user.getSilentUsername().add(message.getUserId());
       context.getUsers().set(user);
       Log log = new Log("muted someone",LocalDateTime.now(),2,
               clientHandler.getClientId());
       Log.log(log);
    }

    public Response mute(int id, String type, String responseVisitorType) {
        try {
            synchronized (User.LOCK) {
                User user = context.getUsers().get(clientHandler.getClientId());
                if (type.equals("comment")) {
                    Comment comment = context.getComments().get
                            (id, "comment");
                    muteHandling(comment, user);
                } else if (type.equals("tweet")) {
                    Tweet comment = context.getTweets().get
                            (id, "tweet");
                    muteHandling(comment, user);
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        ConfirmationResponse response = new ConfirmationResponse();
        response.setVisitorType(responseVisitorType);
        response.setType("mute");
        return response;
    }

    public Response reTweet(int id, String type, String responseVisitorType) {
        ConfirmationResponse response = new ConfirmationResponse();
        response.setVisitorType(responseVisitorType);
        response.setType("retweet");
        try {
            synchronized (User.LOCK) {
                synchronized (Message.LOCK) {
                    if (type.equals("tweet")) {
                        Tweet tweet = context.getTweets().get(id,
                                "tweet");
                        if (!tweet.getRetweet_userId().contains(clientHandler.getClientId())) {
                            User current = context.getUsers().get(clientHandler.getClientId());
                            Log log = new Log("retweeted a tweet", LocalDateTime.now(), 2,
                                    clientHandler.getClientId());
                            Log.log(log);
                            current.getTweetsID().add(tweet.getId());
                            tweet.getRetweet_userId().add(clientHandler.getClientId());
                            context.getUsers().set(current);
                            context.getTweets().set(tweet, type);
                        } else {
                            response.setType("");
                        }
                    } else {
                        Comment tweet = context.getComments().get(id,
                                "comment");
                        if (!tweet.getRetweet_userId().contains(clientHandler.getClientId())) {
                            User current = context.getUsers().get(clientHandler.getClientId());
                            Log log = new Log("retweeted a tweet", LocalDateTime.now(), 2,
                                    clientHandler.getClientId());
                            Log.log(log);
                            current.getTweetsID().add(tweet.getId());
                            tweet.getRetweet_userId().add(clientHandler.getClientId());
                            context.getUsers().set(current);
                            context.getComments().set(tweet, "tweet");
                            context.getMessages().set(tweet, "comment");
                        } else {
                            response.setType("");
                        }
                    }
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return response;
    }
}