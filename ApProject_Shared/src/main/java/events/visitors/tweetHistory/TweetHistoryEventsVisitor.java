package events.visitors.tweetHistory;

import events.visitors.EventVisitor;
import responses.Response;

public interface TweetHistoryEventsVisitor extends EventVisitor {

    Response loadTweets();
    Response sendCommentsNumber(int tweetId, String type, String visitorType);
    Response like(int tweetId, String type, String visitorType);
    Response disLike(int tweetId, String type, String visitorType);
    Response block(int tweetId, String type, String visitorType);
    Response unblock(int tweetId, String type, String visitorType);
    Response report(int tweetId, String type, String visitorType);
    Response mute(int tweetId, String type, String visitorType);
    Response reTweet(int tweetId, String type, String visitorType);
}