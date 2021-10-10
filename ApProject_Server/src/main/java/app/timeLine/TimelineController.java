package app.timeLine;

import models.auth.User;
import models.messages.Tweet;
import app.personalPage.tweetHistory.TweetHistoryController;
import controller.ClientHandler;
import controller.MainController;
import events.visitors.timeLine.TimeLineEventVisitor;
import responses.Response;
import responses.tweetHistory.LoadTweetsResponse;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;

public class TimelineController extends MainController implements TimeLineEventVisitor {

    private final TweetHistoryController tweetHistoryController;
    private final ClientHandler clientHandler;

    public TimelineController(TweetHistoryController tweetHistoryController,
                              ClientHandler clientHandler) {
        this.tweetHistoryController = tweetHistoryController;
        this.clientHandler = clientHandler;
    }

    public Response loadTweets() {
        LoadTweetsResponse response = new LoadTweetsResponse();
        try {
           LinkedList<String[]> allIfo = new LinkedList<>();
           User current = context.getUsers().get(clientHandler.getClientId());
           HashSet<Integer> ids = new HashSet<>();
           for (Integer userId : current.getFollowingUsername()) {
               if (!current.getSilentUsername().contains(userId)) {
                   User user = context.getUsers().get(userId);
                   if (user.getAccount().isActive()) {
                       for (Integer tweetId : user.getTweetsID()) {
                           if (!ids.contains(tweetId)) {
                               Tweet tweet = context.getTweets().get(tweetId, "tweet");
                               User user1 = context.getUsers().get(tweet.getUserId());
                               if (tweet.getUserId() != user.getId()) {
                                   allIfo.add(tweetHistoryController.loadTweetInfo
                                           (tweet, user1,
                                                   user.
                                                           getAccount().getUsername() +
                                                           " retweeted: "));
                               } else {
                                   allIfo.add(tweetHistoryController.loadTweetInfo(
                                           tweet, user1, ""));
                               }
                               ids.add(tweetId);
                           }
                       }
                   }
               }
           }
           for (Integer tweetId: current.getFavouriteID()) {
               if(!ids.contains(tweetId)) {
                   Tweet tweet = context.getTweets().get(tweetId, "tweet");
                   User user = context.getUsers().get(tweet.getUserId());
                   allIfo.add(tweetHistoryController.loadTweetInfo(tweet, user,
                           ""));
                   ids.add(tweetId);
               }
           }
           response.setTweetsInfo(allIfo);
       }
       catch (IOException e){
           e.printStackTrace();
       }
       response.setVisitorType("TimeLineResponsesVisitor");
       return response;
    }
}