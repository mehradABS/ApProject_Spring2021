package Cli.agent;

import Cli.*;
import Logic.LogicalAgent;
import Models.Log;
import Models.Message;
import Models.Tweet;
import Models.User;

import java.io.IOException;
import java.time.LocalDateTime;

public class TweetHistoryAgent {
    public void TweetHistory(TweetHistory tweetHistory,
                             LogicalAgent logicalagent,
                             ForwardMessageAgent forwardMessageAgent
    , ForwardMessage forwardMessage,
                             SendMessageAgent sendMessageAgent,
                             NewMessageAgent newMessageAgent,
                             NewMessage newMessage, TweetCommentsPageAgent
                             tweetCommentsPageAgent, MessageDetailAgent
                             messageDetailAgent
    , TweetCommentsPage tweetCommentsPage, MessageDetail
                              messageDetail,
                             TimeLine timeLine,
                             WatchAnotherPersonalPageAgent
                                     watchAnotherPersonalPageAgent,
                             WatchAnotherPersonalPage watchAnotherPersonalPage,
                             ChatroomAgent chatroomAgent,
                             Chatroom chatroom) throws IOException {
        tweetHistory.showingOptions();
        int length = logicalagent.getProgram().
                getCurrentUser().getMyTweets().size() - 1;
        int first = length;
        if (length != -1) {
            boolean show = true;
            while (true) {

                if (show) {
                    tweetHistory.showMessage(logicalagent.getProgram().
                            getCurrentUser().getMyTweets().get(length));
                }
                String answer = tweetHistory.GetCommand();
                switch (answer) {
                    case "0":
                        return;
                    case "1": {
                        if (length > 0) {
                            length--;
                            show = true;
                        } else {
                            show = false;
                            tweetHistory.last();
                        }
                        break;
                    }
                    case "2": {
                        if (length==first) {
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
                        Tweet tweet = logicalagent.getProgram().getCurrentUser()
                                .getMyTweets().get(length);
                        User user = logicalagent.getProgram().getCurrentUser();
                        tweet.getUsersLikeThis().add(user);
                        tweet.getLike().add(user.getId());
                        user.getFavouriteTweets().add(tweet);
                        user.getFavouriteID().add(tweet.getId());
                        tweet.getUsersDisLikeThis().remove(user);
                        tweet.getDislike().remove(user.getId());
                        logicalagent.getModelLoader().setUserChanges(user);
                        logicalagent.getModelLoader().setTweetChanges(tweet);
                        Log log=new Log("liked his/her tweet",LocalDateTime.now(),
                                2,logicalagent.getProgram().getCurrentUser().getAccount()
                                .getUsername());
                        Log.log(log);
                        tweetHistory.finishingProcess();
                        break;
                    }
                    case "4": {
                        show = false;
                        Tweet tweet = logicalagent.getProgram().getCurrentUser()
                                .getMyTweets().get(length);
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
                        tweet.getUsersDisLikeThis().add(user);
                        tweet.getDislike().add(user.getId());
                        logicalagent.getModelLoader().setUserChanges(user);
                        logicalagent.getModelLoader().setTweetChanges(tweet);
                        Log log=new Log("disliked his/her tweet",LocalDateTime.now(),
                                2,logicalagent.getProgram().getCurrentUser().getAccount()
                                .getUsername());
                        Log.log(log);
                        tweetHistory.finishingProcess();
                        break;
                    }
                    case "5": {
                        show = false;
                        Tweet tweet = logicalagent.getProgram().getCurrentUser()
                                .getMyTweets().get(length);
                        User user = logicalagent.getProgram().getCurrentUser();
                        Tweet tweet1=new Tweet(tweet.getMessageType(),
                                tweet.getText(), tweet.getUser(), LocalDateTime.now()
                                ,tweet.getTweetTopic());
                        logicalagent.getModelLoader().setMessageID_counter(
                                Message.getId_counter());
                        logicalagent.getModelLoader().setTweetChanges(tweet1);
                        user.getSavedMessage().add(tweet1);
                        user.getSavedMessageId().add(tweet1.getId());
                        logicalagent.getModelLoader().setUserChanges(user);
                        Log log=new Log("forwarded his/her tweet to " +
                                "saved Messages",LocalDateTime.now(),
                                2,logicalagent.getProgram().getCurrentUser().getAccount()
                                .getUsername());
                        Log.log(log);
                        tweetHistory.finishingProcess();
                        break;
                    }
                    case "6": {
                        show = true;
                        Tweet tweet = logicalagent.getProgram().getCurrentUser()
                                .getMyTweets().get(length);
                        Tweet tweet1=new Tweet(tweet.getMessageType(),
                                tweet.getText(), tweet.getUser(), LocalDateTime.now()
                                ,tweet.getTweetTopic());
                        logicalagent.getModelLoader().setMessageID_counter(
                                Message.getId_counter());
                        logicalagent.getModelLoader().setTweetChanges(tweet1);
                        tweetHistory.DrawingLine();
                        while (true) {
                            int a = forwardMessageAgent.ForwardMessage(tweet1
                            ,forwardMessage,logicalagent,sendMessageAgent);
                            if (a == -1) {
                                tweetHistory.DrawingLine();
                                break;
                            }
                        }
                        break;
                    }
                    case "7": {
                        show = true;
                        Tweet tweet = logicalagent.getProgram().getCurrentUser()
                                .getMyTweets().get(length);
                        tweetHistory.DrawingLine();
                        newMessageAgent.newComment(tweet
                        ,newMessage,logicalagent);
                        tweetHistory.DrawingLine();
                        break;
                    }
                    case "8": {
                        show = true;
                        tweetHistory.DrawingLine();
                        Tweet tweet = logicalagent.getProgram().getCurrentUser()
                                .getMyTweets().get(length);
                        tweetCommentsPageAgent.TweetCommentsPage(tweet
                        ,tweetCommentsPage,logicalagent,forwardMessageAgent
                        ,forwardMessage,sendMessageAgent,newMessageAgent,
                                newMessage,messageDetailAgent,messageDetail,
                                timeLine,watchAnotherPersonalPageAgent,
                                watchAnotherPersonalPage,chatroomAgent,chatroom);
                        tweetHistory.DrawingLine();
                        break;
                    }
                    case "9": {
                        show = false;
                        tweetHistory.DrawingLine();
                        Tweet tweet = logicalagent.getProgram().getCurrentUser()
                                .getMyTweets().get(length);
                        messageDetailAgent.MessageDetail(tweet
                        ,messageDetail);
                        tweetHistory.DrawingLine();
                        break;
                    }
                    case "10":
                        show = true;
                        tweetHistory.showingOptions();
                        break;
                }
            }
        } else {
            tweetHistory.noTweet();
            while (true) {
                String answer = tweetHistory.GetCommand();
                if (answer.equals("0")) {
                    return;
                } else {
                    tweetHistory.invalidCommand();
                }
            }
        }
    }
}
