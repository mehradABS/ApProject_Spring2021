package app.personalPage.tweetHistory.newComment;

import models.auth.User;
import models.messages.Comment;
import models.messages.Message;
import models.messages.Tweet;
import controller.ClientHandler;
import controller.MainController;
import controller.log.Log;
import events.messages.NewCommentEvent;
import events.messages.NewMessageEvent;
import events.visitors.messages.NewMessageEventVisitor;
import responses.Response;
import responses.messages.NewCommentResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class CommentController extends MainController implements
        NewMessageEventVisitor {

    private final ClientHandler clientHandler;

    public CommentController(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public Response newMessage(NewMessageEvent event, String responseVisitorType) {
        try {
            boolean isTweet = false;
            List<Tweet> tweets = context.getTweets().getAll("tweet");
            for (Tweet value : tweets) {
                if (value.getId() == ((NewCommentEvent)event).getTweetId()) {
                    isTweet = true;
                    break;
                }
            }
            synchronized (Message.LOCK) {
                if (isTweet) {
                    Tweet tweet = context.getTweets().get(((NewCommentEvent) event).getTweetId(),
                            "tweet");
                    User user = context.getUsers().get(clientHandler.getClientId());
                    Message.setId_counter(context.getMessages().getIDCounter());
                    //
                    Comment comment = new Comment("Comment",
                            event.getText(),
                            user,
                            LocalDateTime.now());
                    tweet.getCommentsId().add(comment.getId());
                    context.getMessages().setIDCounter(Message.getId_counter());
                    context.getTweets().set(tweet, "tweet");
                    context.getComments().set(comment, "comment");
                    Log log = new Log("wrote a new comment for a tweet", LocalDateTime.now(),
                            2, clientHandler.getClientId());
                    Log.log(log);
                    if(!event.getEncodedImage().equals("null")) {
                        getCommentImage(event.getEncodedImage(), comment.getId());
                    }
                } else {
                    Comment comment1 = context.getComments().get(((NewCommentEvent) event).getTweetId(),
                            "comment");
                    User user = context.getUsers().get(clientHandler.getClientId());
                    Message.setId_counter(context.getMessages().getIDCounter());
                    //
                    Comment comment = new Comment("Comment",
                            event.getText(),
                            user,
                            LocalDateTime.now());
                    comment1.getCommentsId().add(comment.getId());
                    context.getMessages().setIDCounter(Message.getId_counter());
                    context.getComments().set(comment1, "comment");
                    context.getComments().set(comment, "comment");
                    Log log = new Log("wrote a new comment for a comment", LocalDateTime.now(),
                            2, clientHandler.getClientId());
                    Log.log(log);
                    if(!event.getEncodedImage().equals("null")) {
                        getCommentImage(event.getEncodedImage(), comment.getId());
                    }
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        NewCommentResponse response = new NewCommentResponse();
        response.setResponseVisitorType(responseVisitorType);
        return response;
    }

    public void getCommentImage(String encodeImage, int id){
        context.getMessages().setMessageImage(id, encodeImage);
    }
}