package events.tweetHistory;

import events.visitors.EventVisitor;
import events.visitors.tweetHistory.TweetHistoryEventsVisitor;
import responses.Response;

public class BlockEvent extends TweetHistoryEvent{
    @Override
    public Response visit(EventVisitor eventVisitor) {
        return ((TweetHistoryEventsVisitor)eventVisitor).block(tweetId, messageType
        , responseVisitorType);
    }
}
