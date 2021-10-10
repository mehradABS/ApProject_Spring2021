package Cli.agent;

import Cli.*;
import Logic.LogicalAgent;
import Models.Log;
import Models.Message;
import Models.Tweet;
import Models.User;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class TimeLineAgent {
    public void TimeLine(List<Tweet> tweets, boolean isRandomTweetPage
    , RandomTweetPage randomTweetPage, TimeLine timeLine,
                         TweetHistory tweetHistory, LogicalAgent
                         logicalagent, ForwardMessageAgent
                          forwardMessageAgent, ForwardMessage forwardMessage,
                         SendMessageAgent sendMessageAgent,
                         NewMessageAgent newMessageAgent,
                         NewMessage newMessage,TweetCommentsPage
                         tweetCommentsPage,MessageDetail messageDetail,
                         MessageDetailAgent messageDetailAgent,
                         WatchAnotherPersonalPageAgent watchAnotherPersonalPageAgent,
                         WatchAnotherPersonalPage watchAnotherPersonalPage,
                         ChatroomAgent chatroomAgent,
                         Chatroom chatroom,TweetCommentsPageAgent
                                 tweetCommentsPageAgent)
            throws IOException {
        if(isRandomTweetPage){
            randomTweetPage.Introduction();
        }
        else {
            timeLine.Introduction();
        }
        timeLine.showingOptions();
        int length = tweets.size() - 1;
        int first = length;
        if (length != -1) {
            boolean show = true;
            while (true) {
                if (show) {
                    timeLine.showMessage(tweets.get(length));
                }
                String answer = timeLine.GetCommand();
                switch (answer) {
                    case "0":
                        return;
                    case "1": {
                        if (length > 0) {
                            show = true;
                            length--;
                        } else {
                            show = false;
                            tweetHistory.last();
                        }
                        break;
                    }
                    case "2": {
                        if (first==length) {
                            show = false;
                            tweetHistory.first();
                        } else {
                            length++;
                            show = true;
                        }
                        break;
                    }
                    case "3": {
                        show = false;
                        Tweet tweet = tweets.get(length);
                        User user = logicalagent.getProgram().getCurrentUser();
                        tweet.getUsersLikeThis().add(user);
                        tweet.getLike().add(user.getId());
                        user.getFavouriteTweets().add(tweet);
                        user.getFavouriteID().add(tweet.getId());
                        logicalagent.getModelLoader().setUserChanges(user);
                        tweet.getUsersDisLikeThis().remove(user);
                        tweet.getDislike().remove(user.getId());
                        logicalagent.getModelLoader().setTweetChanges(tweet);
                        Log log=new Log("liked a tweet",LocalDateTime.now(),2,
                                logicalagent.getProgram().
                                        getCurrentUser().getAccount().getUsername());
                        Log.log(log);
                        timeLine.finishingProcess();
                        break;
                    }
                    case "4": {
                        show = false;
                        Tweet tweet = tweets.get(length);
                        User user = logicalagent.getProgram().getCurrentUser();
                        tweet.getUsersLikeThis().remove(user);
                        tweet.getLike().remove(user.getId());
                        user.getFavouriteTweets().remove(tweet);
                        int a=tweet.getId();
                        for (int i = 0; i < user.getFavouriteID().size(); i++) {
                            if(user.getFavouriteID().get(i)==a){
                                user.getFavouriteID().remove(i);
                                break;
                            }
                        }
                        Log log=new Log("disliked a tweet",LocalDateTime.now(),2,
                                logicalagent.getProgram().
                                        getCurrentUser().getAccount().getUsername());
                        Log.log(log);
                        logicalagent.getModelLoader().setUserChanges(user);
                        tweet.getUsersDisLikeThis().add(user);
                        tweet.getDislike().add(user.getId());
                        logicalagent.getModelLoader().setTweetChanges(tweet);
                        timeLine.finishingProcess();
                        break;
                    }
                    case "5": {
                        show = false;
                        Tweet tweet = tweets.get(length);
                        User user = logicalagent.getProgram().getCurrentUser();
                        Tweet tweet1=new Tweet(tweet.getMessageType(),
                                tweet.getText(), tweet.getUser(), LocalDateTime.now()
                                ,tweet.getTweetTopic());
                        Log log=new Log("forwarded tweet to saved Messages",LocalDateTime.now(),2,
                                logicalagent.getProgram().
                                        getCurrentUser().getAccount().getUsername());
                        Log.log(log);
                        logicalagent.getModelLoader().setMessageID_counter(
                                Message.getId_counter());
                        logicalagent.getModelLoader().setTweetChanges(tweet1);
                        user.getSavedMessage().add(tweet1);
                        user.getSavedMessageId().add(tweet1.getId());
                        logicalagent.getModelLoader().setUserChanges(user);
                        timeLine.finishingProcess();
                        break;
                    }
                    case "6": {
                        show = true;
                        Tweet tweet = tweets.get(length);
                        Tweet tweet1=new Tweet(tweet.getMessageType(),
                                tweet.getText(), tweet.getUser(), LocalDateTime.now()
                                ,tweet.getTweetTopic());
                        logicalagent.getModelLoader().setMessageID_counter(
                                Message.getId_counter());
                        logicalagent.getModelLoader().setTweetChanges(tweet1);
                        timeLine.DrawingLine();
                        while (true) {
                            int a = forwardMessageAgent.ForwardMessage(tweet1
                            ,forwardMessage,logicalagent,sendMessageAgent);
                            if (a == -1) {
                                timeLine.DrawingLine();
                                break;
                            }
                        }
                        break;
                    }
                    case "7": {
                        show = true;
                        Tweet tweet = tweets.get(length);
                        timeLine.DrawingLine();
                        newMessageAgent.newComment(tweet
                        ,newMessage,logicalagent);
                        timeLine.DrawingLine();
                        break;
                    }
                    case "8": {
                        show = true;
                        timeLine.DrawingLine();
                        tweetCommentsPageAgent.TweetCommentsPage(tweets.get(length)
                        ,tweetCommentsPage,logicalagent,forwardMessageAgent,
                                forwardMessage,sendMessageAgent,
                                newMessageAgent,newMessage,messageDetailAgent,
                                messageDetail,timeLine,watchAnotherPersonalPageAgent
                        ,watchAnotherPersonalPage,chatroomAgent,
                                chatroom);
                        timeLine.DrawingLine();
                        break;
                    }
                    case "9": {
                        show = false;
                        tweetCommentsPage.DrawingLine();
                        Tweet tweet = tweets.get(length);
                        messageDetailAgent.MessageDetail(tweet,messageDetail);
                        tweetCommentsPage.DrawingLine();
                        break;
                    }
                    case "10": {
                        show = false;
                        if(tweets.get(length).getUser()!=null){
                            if(logicalagent.getProgram().getCurrentUser().getId()
                                    !=tweets.get(length).getUser().getId()) {
                                if(tweets.get(length).getUser().getAccount().isActive()){
                                    if (!logicalagent.getProgram().getCurrentUser()
                                            .getBlacklist().contains(tweets.get(length).getUser())) {
                                        logicalagent.getProgram().getCurrentUser().getBlacklist()
                                                .add(tweets.get(length).getUser());
                                        logicalagent.getProgram().getCurrentUser()
                                                .getBlackUsername().add(tweets.get(length).getUser()
                                                .getId());
                                        logicalagent.getModelLoader().setUserChanges(
                                                logicalagent.getProgram().getCurrentUser());
                                        tweets.get(length).getUser().getSystemMessages().add(
                                                logicalagent.getProgram().getCurrentUser().getAccount()
                                                        .getUsername() + " Blocked you   " +
                                                        LocalDateTime.now());
                                        Log log=new Log("blocked someone",LocalDateTime.now(),2,
                                                logicalagent.getProgram().
                                                        getCurrentUser().getAccount().getUsername());
                                        Log.log(log);
                                        logicalagent.getModelLoader().setUserChanges(tweets.
                                                get(length).getUser());
                                        timeLine.finishingProcess();
                                    } else {
                                        timeLine.Block();
                                    }
                                }
                                else{
                                    timeLine.active();
                                }
                            }
                            else{
                                timeLine.crazy();
                            }
                        }
                        else{
                            timeLine.delete();
                        }
                        break;
                    }
                    case "11": {
                        if(tweets.get(length).getUser()!=null) {
                            if(tweets.get(length).getUser().getId()!=
                                    logicalagent.getProgram().getCurrentUser().getId()){
                                if(tweets.get(length).getUser().getAccount().isActive()) {
                                    show = true;
                                    timeLine.DrawingLine();
                                    watchAnotherPersonalPageAgent.WatchAnotherPersonalPage
                                            (tweets.get(length).getUser(),
                                                    logicalagent,
                                                    watchAnotherPersonalPage,
                                                    chatroomAgent,
                                                    chatroom,
                                                    newMessageAgent,
                                                    newMessage);
                                    timeLine.DrawingLine();
                                }
                                else{
                                    show=false;
                                    timeLine.active();
                                }
                            }
                            else{
                                timeLine.crazy();
                            }
                        }
                        else{
                            timeLine.delete();
                        }
                        break;
                    }
                    case "12": {
                        show = false;
                        if(tweets.get(length).getUser()!=null) {
                            if(tweets.get(length).getUser().getId()!=
                                    logicalagent.getProgram().getCurrentUser().getId()){
                                logicalagent.getProgram().getReportsId().put(
                                        logicalagent.getProgram().getCurrentUser().getAccount()
                                                .getUsername(), tweets.get(length).getUser()
                                                .getAccount().getUsername());
                                logicalagent.getModelLoader().setReports(
                                        logicalagent.getProgram().getReportsId());
                                Log log=new Log("report someone",LocalDateTime.now(),2,
                                        logicalagent.getProgram().
                                                getCurrentUser().getAccount().getUsername());
                                Log.log(log);
                                timeLine.finishingProcess();
                            }
                            else{
                                timeLine.crazy();
                            }
                        }
                        else{
                            timeLine.delete();
                        }
                        break;
                    }
                    case "15": {
                        show = true;
                        timeLine.showingOptions();
                        break;
                    }
                    case "13": {
                        show=false;
                        String original;
                        if(tweets.get(length).getUser()!=null){
                            if(tweets.get(length).getUser().getId()!=
                                    logicalagent.getProgram().getCurrentUser().getId()) {
                                original = tweets.get(length).getUser().
                                        getAccount().getUsername();
                            }
                            else{
                                original="myself";
                                timeLine.crazy();
                            }
                        }
                        else{
                            original="deleted account";
                        }
                        if(!original.equals("myself")) {
                            String text = logicalagent.getProgram().getCurrentUser()
                                    .getAccount().getUsername() + "reTweeted this from" +
                                    original + ": " + tweets.get(length).getText() + "("
                                    + tweets.get(length).getLocalDateTime() + ")";
                            Tweet tweet =
                                    new Tweet("reTweet", text,
                                            tweets.get(length).getUser(),
                                            LocalDateTime.now(),
                                            "hello");
                            Log log=new Log("retweeted a tweet",LocalDateTime.now(),2,
                                    logicalagent.getProgram().
                                            getCurrentUser().getAccount().getUsername());
                            Log.log(log);
                            tweet.setUsersLikeThis(tweets.get(length).getUsersLikeThis());
                            tweet.setLike(tweets.get(length).getLike());
                            tweet.setUsersDisLikeThis(tweets.get(length).getUsersDisLikeThis());
                            tweet.setDislike(tweets.get(length).getDislike());
                            logicalagent.getModelLoader().setMessageID_counter(Message.getId_counter());
                            logicalagent.getProgram().getCurrentUser().
                                    getMyTweets().add(tweet);
                            logicalagent.getProgram().getCurrentUser()
                                    .getTweetsID().add(tweet.getId());
                            logicalagent.getModelLoader().setUserChanges(
                                    logicalagent.getProgram().getCurrentUser());
                            logicalagent.getModelLoader().setTweetChanges(tweet);
                            timeLine.finishingProcess();
                        }
                        break;
                    }
                    case "14": {
                        if(tweets.get(length).getUser()!=null) {
                            if(tweets.get(length).getUser().getId()!=
                                    logicalagent.getProgram().getCurrentUser().getId()) {
                                logicalagent.getProgram().getCurrentUser()
                                        .getSilentUsers().add(tweets.get(length).getUser());
                                logicalagent.getProgram().getCurrentUser()
                                        .getSilentUsername().add(tweets.get(length).getUser()
                                        .getId());
                                logicalagent.getModelLoader().setUserChanges(
                                        logicalagent.getProgram().getCurrentUser());
                                Log log=new Log("muted someone",LocalDateTime.now(),2,
                                        logicalagent.getProgram().
                                                getCurrentUser().getAccount().getUsername());
                                Log.log(log);
                                timeLine.finishingProcess();
                            }
                            else{
                                timeLine.crazy();
                            }
                        }
                        else{
                            timeLine.delete();
                        }
                        break;
                    }
                }
            }
        } else {
            timeLine.noTweet();
            while (true) {
                String answer = timeLine.GetCommand();
                if (answer.equals("0")) {
                    return;
                } else {
                    timeLine.invalidCommand();
                }
            }
        }
    }
}
