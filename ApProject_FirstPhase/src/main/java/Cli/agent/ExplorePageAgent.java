package Cli.agent;

import Cli.*;
import Logic.LogicalAgent;
import Models.Tweet;
import Models.User;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ExplorePageAgent {
    public void ExplorePage(ExplorePage explorePage, LogicalAgent
                             logicalagent,
                            WatchAnotherPersonalPageAgent
                                    watchAnotherPersonalPageAgent,
                            WatchAnotherPersonalPage watchAnotherPersonalPage,
                            ChatroomAgent chatroomAgent,
                            Chatroom chatroom,
                            TimeLineAgent timeLineAgent,
                            RandomTweetPage randomTweetPage,
                            TimeLine timeLine,
                            TweetHistory tweetHistory
    ,NewMessageAgent newMessageAgent,NewMessage newMessage,
                            ForwardMessageAgent forwardMessageAgent,
                            ForwardMessage forwardMessage
    ,SendMessageAgent sendMessageAgent,
                            TweetCommentsPage tweetCommentsPage,
                            MessageDetail messageDetail,MessageDetailAgent
                             messageDetailAgent,TweetCommentsPageAgent
                            tweetCommentsPageAgent) throws IOException {
        while (true){
            explorePage.showingOptions();
            String answer= explorePage.GetCommand();
            switch (answer) {
                case "0":
                    return;
                case "1":
                    explorePage.DrawingLine();
                    explorePage.guideUsername();
                    while (true) {
                        String answer1 = explorePage.GetUsername();
                        if (answer1.equals("0")) {
                            break;
                        } else {
                            User user = null;
                            for (User allUser : logicalagent.getProgram().originalUsers()) {
                                if(allUser.getAccount().isActive()) {
                                    if (allUser.getAccount().getUsername().equals(answer1)
                                            && !answer1.equals(logicalagent.getProgram()
                                            .getCurrentUser().getAccount().getUsername())) {
                                        user = allUser;
                                        break;
                                    }
                                }
                            }
                            if (user != null) {
                                explorePage.DrawingLine();
                                watchAnotherPersonalPageAgent.
                                        WatchAnotherPersonalPage(user,logicalagent,
                                                watchAnotherPersonalPage
                                        ,chatroomAgent,chatroom,newMessageAgent,
                                                newMessage);
                                explorePage.DrawingLine();
                                explorePage.guideUsername();
                            } else {
                                explorePage.invalidUse();
                            }
                        }
                    }
                    explorePage.DrawingLine();
                    break;
                case "2":
                    explorePage.DrawingLine();
                    List<Tweet> tweets=new LinkedList<>();
                    for (User user:logicalagent.getProgram().getAllUsers()) {
                        tweets.addAll(user.getMyTweets());
                    }
                    timeLineAgent.TimeLine(logicalagent.getProgram()
                            .randomTweets(tweets),true
                    ,randomTweetPage,timeLine,tweetHistory,logicalagent
                    ,forwardMessageAgent,forwardMessage,sendMessageAgent
                    ,newMessageAgent,newMessage,tweetCommentsPage,messageDetail,
                            messageDetailAgent,watchAnotherPersonalPageAgent,
                            watchAnotherPersonalPage,chatroomAgent,chatroom,
                            tweetCommentsPageAgent);
                    explorePage.DrawingLine();
                    break;
            }
        }
    }
}
