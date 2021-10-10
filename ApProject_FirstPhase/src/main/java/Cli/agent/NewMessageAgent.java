package Cli.agent;

import Cli.NewMessage;
import Logic.LogicalAgent;
import Models.Comment;
import Models.Log;
import Models.Message;
import Models.Tweet;

import java.io.IOException;
import java.time.LocalDateTime;

public class NewMessageAgent {
    public void NewTweet(NewMessage newMessage,
                         LogicalAgent logicalagent) throws IOException {
        newMessage.guideLine("Tweet");
        String text = newMessage.GetMessage();
        if (!text.equals("-1")) {
            Tweet tweet = new Tweet("Tweet", text,
                    logicalagent.getProgram().getCurrentUser(),
                    LocalDateTime.now(),
                    "hello");
            logicalagent.getModelLoader().setMessageID_counter(Message.getId_counter());
            logicalagent.getProgram().getCurrentUser().
                    getMyTweets().add(tweet);
            logicalagent.getProgram().getCurrentUser()
                    .getTweetsID().add(tweet.getId());
            logicalagent.getModelLoader().setUserChanges(logicalagent.getProgram()
                    .getCurrentUser());
            logicalagent.getModelLoader().setTweetChanges(tweet);
            logicalagent.getProgram().getAllTweets().add(tweet);
            newMessage.finishingProcess();
            Log log=new Log("wrote a new tweet",LocalDateTime.now(),
                    2,logicalagent.getProgram().getCurrentUser().getAccount()
            .getUsername());
            Log.log(log);
        }
    }


    public Message NewMessage(NewMessage newMessage,
                              LogicalAgent logicalagent) throws IOException {
        newMessage.guideLine("message");
        String text = newMessage.GetMessage();
        if (!text.equals("-1")) {
            Message sms= new Message("message", text,
                    logicalagent.getProgram().getCurrentUser(),
                    LocalDateTime.now());
            logicalagent.getModelLoader().setMessageID_counter(Message.getId_counter());
            logicalagent.getModelLoader().setMessageChanges(sms);
            logicalagent.getProgram().getAllMessages().add(sms);
            Log log=new Log("wrote a new Message",LocalDateTime.now(),
                    2,logicalagent.getProgram().getCurrentUser().getAccount()
                    .getUsername());
            Log.log(log);
            return sms;
        }
        return null;
    }

    public void newComment(Tweet tweet,NewMessage newMessage,
                           LogicalAgent logicalagent) throws IOException {
        newMessage.guideLine("Comment");
        String text = newMessage.GetMessage();
        if (!text.equals("-1")) {
            Comment comment = new Comment("Comment", text,
                    logicalagent.getProgram().getCurrentUser(),
                    LocalDateTime.now());
            logicalagent.getProgram().getAllComments().add(comment);
            tweet.getComments().add(comment);
            tweet.getCommentsId().add(comment.getId());
            logicalagent.getModelLoader().setMessageID_counter(Message.getId_counter());
            logicalagent.getModelLoader().setTweetChanges(tweet);
            logicalagent.getModelLoader().setCommentChanges(comment);
            Log log=new Log("wrote a new comment for a tweet",LocalDateTime.now(),
                    2,logicalagent.getProgram().getCurrentUser().getAccount()
                    .getUsername());
            Log.log(log);
            newMessage.finishingProcess();
        }
    }

    public void newComment(Comment comment1,NewMessage newMessage,
                           LogicalAgent logicalagent) throws IOException {
        newMessage.guideLine("Comment");
        String text = newMessage.GetMessage();
        if (!text.equals("-1")) {
            Comment comment = new Comment("Comment", text,
                    logicalagent.getProgram().getCurrentUser(),
                    LocalDateTime.now());
            logicalagent.getProgram().getAllComments().add(comment);
            comment1.getComments().add(comment);
            comment1.getCommentsId().add(comment.getId());
            logicalagent.getModelLoader().setMessageID_counter(Message.getId_counter());
            logicalagent.getModelLoader().setCommentChanges(comment1);
            logicalagent.getModelLoader().setCommentChanges(comment);
            Log log=new Log("wrote a new comment for a comment",LocalDateTime.now(),
                    2,logicalagent.getProgram().getCurrentUser().getAccount()
                    .getUsername());
            Log.log(log);
            newMessage.finishingProcess();
        }
    }
}
