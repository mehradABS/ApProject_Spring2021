package app.timeLine.commentPage;


import models.auth.User;
import models.messages.Comment;
import models.messages.Tweet;
import app.personalPage.tweetHistory.TweetHistoryController;
import controller.ClientHandler;
import controller.MainController;
import events.visitors.commentPage.CommentPageEventVisitor;
import responses.Response;
import responses.commentPage.GetOriginalTweetResponse;
import responses.tweetHistory.LoadTweetsResponse;

import java.io.IOException;
import java.util.LinkedList;

public class CommentPageController extends MainController
        implements CommentPageEventVisitor {

    private final TweetHistoryController tweetHistoryController;
    private final ClientHandler clientHandler;

    public CommentPageController(ClientHandler clientHandler, TweetHistoryController
            tweetHistoryController) {
        this.clientHandler = clientHandler;
        this.tweetHistoryController = tweetHistoryController;
    }

    public Response loadOriginalTweet(int tweetId, String type, String responseVisitorType){
        GetOriginalTweetResponse response = new GetOriginalTweetResponse();
        response.setVisitorType(responseVisitorType);
        try {
            if(type.equals("tweet")){
                Tweet tweet = context.getTweets().get(tweetId, type);
                User user = context.getUsers().get(tweet.getUserId());
                response.setInfo(tweetHistoryController.
                        loadTweetInfo(tweet, user,""));
            }
            else{
                Comment tweet = context.getComments().get(tweetId, type);
                User user = context.getUsers().get(tweet.getUserId());
                response.setInfo(tweetHistoryController.
                        loadTweetInfo(tweet, user,""));
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return response;
    }

    public Response loadComments(int originalId, String type, String visitorType) {
         try {
             LoadTweetsResponse response = new LoadTweetsResponse();
             response.setVisitorType(visitorType);
             LinkedList<String[]> allInfo = new LinkedList<>();
             if(type.equals("tweet")){
                 Tweet message = context.getTweets().get(originalId, type);
                 for(Integer commentId: message.getCommentsId()){
                     Comment message1 = context.getComments().get(commentId, "comment");
                     User user = context.getUsers().get(message1.getUserId());
                     if(!context.getUsers().get(clientHandler.getClientId()).
                             getSilentUsername().contains(message1.getUserId())
                             && user.getAccount().isActive()) {
                         allInfo.add(tweetHistoryController.loadTweetInfo(message1, user,""));
                     }
                 }
             }
             else{
                 Comment message = context.getComments().get(originalId, type);
                 for(Integer commentId: message.getCommentsId()){
                     Comment message1 = context.getComments().get(commentId, "comment");
                     User user = context.getUsers().get(message1.getUserId());
                     if(!context.getUsers().get(clientHandler.getClientId()).
                             getSilentUsername().contains(message1.getUserId())
                             && user.getAccount().isActive()) {
                         allInfo.add(tweetHistoryController.loadTweetInfo(message1, user,
                                 ""));
                     }
                 }
             }
             response.setTweetsInfo(allInfo);
             return response;
         }
         catch (IOException e){
             e.printStackTrace();
         }
         return null;
    }
}