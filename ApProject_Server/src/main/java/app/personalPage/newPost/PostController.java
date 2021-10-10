package app.personalPage.newPost;

import models.auth.User;
import models.messages.Message;
import models.messages.Tweet;
import controller.ClientHandler;
import controller.MainController;
import controller.log.Log;
import events.messages.NewMessageEvent;
import events.visitors.messages.NewMessageEventVisitor;
import responses.Response;
import responses.messages.NewTweetResponse;

import java.io.IOException;
import java.time.LocalDateTime;

public class PostController extends MainController implements NewMessageEventVisitor {

    private final ClientHandler clientHandler;

    public PostController(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public Response newMessage(NewMessageEvent newMessageEvent, String visitorType){
        try {
            synchronized (User.LOCK) {
                synchronized (Message.LOCK) {
                    User user = context.getUsers().get(clientHandler.getClientId());
                    Tweet tweet;
                    Message.setId_counter(context.getMessages().getIDCounter());
                    tweet = new Tweet("Tweet", newMessageEvent.getText(),
                            user,
                            LocalDateTime.now(),
                            "hello");
                    context.getMessages().setIDCounter(Message.getId_counter());
                    user.getTweetsID().add(tweet.getId());
                    context.getUsers().set(user);
                    context.getTweets().set(tweet, "tweet");
                    Log log = new Log("wrote a new tweet", LocalDateTime.now(),
                            2, clientHandler.getClientId());
                    Log.log(log);
                    if(!newMessageEvent.getEncodedImage().equals("null")) {
                        getTweetImage(newMessageEvent.getEncodedImage(), tweet.getId());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new NewTweetResponse();
    }

    public void getTweetImage(String encodeImage, int id){
        context.getMessages().setMessageImage(id, encodeImage);
    }
}