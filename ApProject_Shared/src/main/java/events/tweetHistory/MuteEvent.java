package events.tweetHistory;

import events.visitors.EventVisitor;
import events.visitors.tweetHistory.TweetHistoryEventsVisitor;
import responses.Response;

public class MuteEvent extends TweetHistoryEvent{

    @Override
    public Response visit(EventVisitor eventVisitor) {
        return ((TweetHistoryEventsVisitor)eventVisitor).mute(tweetId, messageType
        , responseVisitorType);
    }
}
