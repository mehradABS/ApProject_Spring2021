package app.explore;

import models.auth.User;
import models.messages.Tweet;
import app.personalPage.tweetHistory.TweetHistoryController;
import controller.ClientHandler;
import controller.MainController;
import events.visitors.timeLine.TimeLineEventVisitor;
import responses.Response;
import responses.tweetHistory.LoadTweetsResponse;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class RandomTweetController extends MainController implements TimeLineEventVisitor {

    private final TweetHistoryController tweetHistoryController;
    private final ClientHandler clientHandler;

    public RandomTweetController(TweetHistoryController tweetHistoryController,
                                 ClientHandler clientHandler) {
        this.tweetHistoryController = tweetHistoryController;
        this.clientHandler = clientHandler;
    }

    public Response loadTweets() {
        LoadTweetsResponse response = new LoadTweetsResponse();
        try {
            LinkedList<String[]> allIfo = new LinkedList<>();
            List<Tweet> tweets = randomTweets(context.getTweets().getAll("tweet"));
            for (Tweet tweet: tweets) {
                allIfo.add(tweetHistoryController.loadTweetInfo(tweet,
                        context.getUsers().get(tweet.getUserId()), ""));
            }
            response.setTweetsInfo(allIfo);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        response.setVisitorType("RandomTweetPanelResponsesVisitor");
        return response;
    }

    public List<Tweet> randomTweets(List<Tweet> allTweets) throws IOException {
        List<Tweet> publicTweets = new LinkedList<>();
        User current = context.getUsers().get(clientHandler.getClientId());
        for (Tweet tweet:allTweets) {
             if(tweet.getForwarderId() == -1) {
                 User user = context.getUsers().get(tweet.getUserId());
                 if (user.getAccount().getPrivacy().equals("public")
                         && user.getAccount().isActive()
                         && !current.getSilentUsername().contains(tweet.getUserId())) {
                     publicTweets.add(tweet);
                 }
             }
        }
        for (int i = 1; i < publicTweets.size(); i++) {
            for (int j = i; j >0 ; j--) {
                if(publicTweets.get(j).getLike().size()<
                        publicTweets.get(j-1).getLike().size()){
                    Tweet tweet = publicTweets.get(j);
                    publicTweets.set(j,publicTweets.get(j-1));
                    publicTweets.set(j-1,tweet);
                }
            }
        }
        return publicTweets;
    }
}