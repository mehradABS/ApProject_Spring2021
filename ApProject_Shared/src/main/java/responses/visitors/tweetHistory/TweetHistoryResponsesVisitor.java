package responses.visitors.tweetHistory;

import responses.tweetHistory.DisLikeResponse;
import responses.tweetHistory.LikeResponse;
import responses.tweetHistory.LoadTweetsResponse;
import responses.visitors.ResponseVisitor;

public interface TweetHistoryResponsesVisitor extends ResponseVisitor {

    void getTweets(LoadTweetsResponse response);
    void getCommentsNumber(int number);
    void getLikeText(LikeResponse likeResponse);
    void getDisLikeText(DisLikeResponse likeResponse);
    void getConfirmation(String type);
}
