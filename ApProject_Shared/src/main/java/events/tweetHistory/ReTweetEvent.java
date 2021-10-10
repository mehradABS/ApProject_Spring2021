package events.tweetHistory;

import events.visitors.EventVisitor;
import events.visitors.tweetHistory.TweetHistoryEventsVisitor;
import responses.Response;

public class ReTweetEvent extends TweetHistoryEvent{

    @Override
    public Response visit(EventVisitor eventVisitor) {
        return ((TweetHistoryEventsVisitor)eventVisitor).reTweet(tweetId, messageType
        , responseVisitorType);
    }
}
