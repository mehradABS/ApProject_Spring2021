package Cli.agent;

import Cli.*;
import Logic.LogicalAgent;
import Models.Comment;
import Models.Log;
import Models.Message;
import Models.User;

import java.io.IOException;
import java.time.LocalDateTime;

public class TweetCommentsPageAgent {
    public void TweetCommentsPage(Message message,
                                  TweetCommentsPage tweetCommentsPage,
                                  LogicalAgent logicalagent,
                                  ForwardMessageAgent forwardMessageAgent,
                                  ForwardMessage forwardMessage,
                                  SendMessageAgent sendMessageAgent,
                                  NewMessageAgent newMessageAgent,
                                  NewMessage newMessage,
                                  MessageDetailAgent messageDetailAgent,
                                  MessageDetail messageDetail,
                                  TimeLine timeLine,
                                  WatchAnotherPersonalPageAgent
                                          watchAnotherPersonalPageAgent,
                                  WatchAnotherPersonalPage watchAnotherPersonalPage,
                                  ChatroomAgent chatroomAgent,
                                  Chatroom chatroom)
            throws IOException {
        tweetCommentsPage.showingOptions();
        int length = message.getComments().size() - 1;
        int first = length;
        if (length != -1) {
            boolean show = true;
            while (true) {
                if (show) {
                    tweetCommentsPage.showMessage(message.getComments().get(length));
                }
                String answer = tweetCommentsPage.GetCommand();
                switch (answer) {
                    case "0":
                        return;
                    case "1": {
                        if (length > 0) {
                            show = true;
                            length--;
                        } else {
                            show = false;
                            tweetCommentsPage.last();
                        }
                        break;
                    }
                    case "2": {
                        if (first==length) {
                            show = false;
                            tweetCommentsPage.first();
                        } else {
                            length++;
                            show = true;
                        }
                        break;
                    }
                    case "3": {
                        show = false;
                        Comment comment = message.getComments().get(length);
                        User user = logicalagent.getProgram().getCurrentUser();
                        comment.getUsersLikeThis().add(user);
                        comment.getUsersDisLikeThis().remove(user);
                        comment.getLike().add(user.getId());
                        comment.getDislike().remove(user.getId());
                        logicalagent.getModelLoader().setCommentChanges(comment);
                        tweetCommentsPage.finishingProcess();
                        Log log=new Log("liked a tweet/comment",LocalDateTime.now(),
                                2,logicalagent.getProgram().getCurrentUser().getAccount()
                                .getUsername());
                        Log.log(log);
                        break;
                    }
                    case "4": {
                        show = false;
                        Comment comment = message.getComments().get(length);
                        User user = logicalagent.getProgram().getCurrentUser();
                        comment.getUsersLikeThis().remove(user);
                        comment.getUsersDisLikeThis().add(user);
                        comment.getLike().remove(user.getId());
                        comment.getDislike().add(user.getId());
                        logicalagent.getModelLoader().setCommentChanges(comment);
                        tweetCommentsPage.finishingProcess();
                        Log log=new Log("disliked a tweet/comment",LocalDateTime.now(),
                                2,logicalagent.getProgram().getCurrentUser().getAccount()
                                .getUsername());
                        Log.log(log);
                        break;
                    }
                    case "5": {
                        show = false;
                        Comment comment1 = message.getComments().get(length);
                        User user = logicalagent.getProgram().getCurrentUser();
                        Comment comment=new Comment(comment1.getMessageType(),
                                comment1.getText(),comment1.getUser(), LocalDateTime.now());
                        logicalagent.getModelLoader().setMessageID_counter(
                                Message.getId_counter());
                        logicalagent.getModelLoader().setCommentChanges(comment);
                        user.getSavedMessage().add(comment);
                        user.getSavedMessageId().add(comment.getId());
                        logicalagent.getModelLoader().setUserChanges(user);
                        tweetCommentsPage.finishingProcess();
                        Log log=new Log("forwarded a comment" +
                                " to saved Messages",LocalDateTime.now(),
                                2,logicalagent.getProgram().getCurrentUser().getAccount()
                                .getUsername());
                        Log.log(log);
                        break;
                    }
                    case "6": {
                        show = true;
                        Comment comment1 = message.getComments().get(length);
                        Comment comment=new Comment(comment1.getMessageType(),
                                comment1.getText(),comment1.getUser(),LocalDateTime.now());
                        logicalagent.getModelLoader().setMessageID_counter(
                                Message.getId_counter());
                        logicalagent.getModelLoader().setCommentChanges(comment);
                        tweetCommentsPage.DrawingLine();
                        while (true) {
                            int a = forwardMessageAgent.ForwardMessage(comment
                            ,forwardMessage,logicalagent,sendMessageAgent);
                            if (a == -1) {
                                tweetCommentsPage.DrawingLine();
                                break;
                            }
                        }
                        break;
                    }
                    case "7": {
                        show = true;
                        Comment comment = message.getComments().get(length);
                        tweetCommentsPage.DrawingLine();
                        newMessageAgent.newComment(comment,newMessage,
                                logicalagent);
                        tweetCommentsPage.DrawingLine();
                        break;
                    }
                    case "8": {
                        show = true;
                        tweetCommentsPage.DrawingLine();
                        TweetCommentsPage(message.getComments().get(length),
                                tweetCommentsPage,logicalagent,forwardMessageAgent,
                                forwardMessage,sendMessageAgent,
                                newMessageAgent,newMessage,messageDetailAgent,
                                messageDetail,timeLine,
                                watchAnotherPersonalPageAgent,watchAnotherPersonalPage,
                                chatroomAgent,chatroom);
                        tweetCommentsPage.DrawingLine();
                        break;
                    }
                    case "9": {
                        tweetCommentsPage.DrawingLine();
                        show = false;
                        Comment comment = message.getComments().get(length);
                        messageDetailAgent.MessageDetail(comment,messageDetail);
                        tweetCommentsPage.DrawingLine();
                        break;
                    }
                    case "10": {
                        show = false;
                        if(message.getComments().get(length).getUser()!=null){
                            if(logicalagent.getProgram().getCurrentUser().getId()
                                    !=message.getComments().get(length).getUser().getId()) {
                                if(message.getComments().get(length).getUser().getAccount()
                                        .isActive()){
                                    if (!logicalagent.getProgram().getCurrentUser().getBlacklist()
                                            .contains(message.getComments().get(length).getUser())) {
                                        logicalagent.getProgram().getCurrentUser().getBlacklist()
                                                .add(message.getComments().get(length).getUser());
                                        logicalagent.getProgram().getCurrentUser()
                                                .getBlackUsername().add(message.getComments().
                                                get(length).getUser().getId());
                                        logicalagent.getModelLoader().setUserChanges(
                                                logicalagent.getProgram().getCurrentUser());
                                        message.getComments().get(length).getUser().getSystemMessages().add(
                                                logicalagent.getProgram().getCurrentUser().getAccount()
                                                        .getUsername() + " Blocked you   " +
                                                        LocalDateTime.now());
                                        logicalagent.getModelLoader().setUserChanges(
                                                message.getComments().get(length).getUser());
                                        tweetCommentsPage.finishingProcess();
                                        Log log=new Log("blocked someone",LocalDateTime.now(),
                                                2,logicalagent.getProgram().getCurrentUser().getAccount()
                                                .getUsername());
                                        Log.log(log);
                                    } else {
                                        timeLine.Block();
                                    }
                                }
                                else{
                                    timeLine.active();
                                }
                            }
                            else {
                                timeLine.crazy();
                            }
                        }
                        else{
                            timeLine.delete();
                        }
                        break;
                    }
                    case "11": {
                        if(message.getComments().get(length).getUser()!=null) {
                            if(message.getComments().get(length).getUser().getId()
                                    !=logicalagent.getProgram().getCurrentUser().getId()){
                                if(message.getComments().get(length).getUser()
                                        .getAccount().isActive()) {
                                    show = true;
                                    tweetCommentsPage.DrawingLine();
                                    watchAnotherPersonalPageAgent.WatchAnotherPersonalPage
                                            (message.getComments().get(length).getUser()
                                            ,logicalagent,watchAnotherPersonalPage,
                                                    chatroomAgent,chatroom,newMessageAgent
                                            ,newMessage);
                                    tweetCommentsPage.DrawingLine();
                                }
                                else {
                                    show=false;
                                    timeLine.active();
                                }
                            }
                            else{
                                timeLine.crazy();
                            }
                        }
                        else{
                            show=false;
                            timeLine.delete();
                        }
                        break;
                    }
                    case "12": {
                        show = false;
                        if(message.getComments().get(length).getUser()!=null) {
                            if(message.getComments().get(length).getUser().getId()
                                    !=logicalagent.getProgram().getCurrentUser().getId()){
                                logicalagent.getProgram().getReportsId().put(
                                        logicalagent.getProgram().getCurrentUser().getAccount()
                                                .getUsername(), message.getComments().get(length).getUser()
                                                .getAccount().getUsername());
                                logicalagent.getModelLoader().setReports(logicalagent
                                        .getProgram().getReportsId());
                                tweetCommentsPage.finishingProcess();
                                Log log=new Log("reported someone",LocalDateTime.now(),
                                        2,logicalagent.getProgram().getCurrentUser().getAccount()
                                        .getUsername());
                                Log.log(log);
                            }
                            else {
                                timeLine.crazy();
                            }
                        }
                        else{
                            timeLine.delete();
                        }
                        break;
                    }
                    case "13": {
                        show = true;
                        tweetCommentsPage.showingOptions();
                        break;
                    }
                }
            }
        } else {
            tweetCommentsPage.noComment();
            while (true) {
                String answer = tweetCommentsPage.GetCommand();
                if (answer.equals("0")) {
                    return;
                } else {
                    tweetCommentsPage.invalidCommand();
                }
            }
        }
    }
}
