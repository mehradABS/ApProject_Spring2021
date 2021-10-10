package events.tweetHistory;

import events.visitors.EventVisitor;
import events.visitors.tweetHistory.TweetHistoryEventsVisitor;
import responses.Response;

public class DisLikeEvent extends TweetHistoryEvent{

    @Override
    public Response visit(EventVisitor eventVisitor) {
        return ((TweetHistoryEventsVisitor)eventVisitor).disLike(tweetId, messageType
        , responseVisitorType);
    }
}
