package events.timeLine;

import events.tweetHistory.LoadTweetsEvent;
import events.visitors.EventVisitor;
import events.visitors.timeLine.TimeLineEventVisitor;
import responses.Response;

public class LoadTimeLineTweetsEvent extends LoadTweetsEvent {

    @Override
    public Response visit(EventVisitor eventVisitor) {
        return ((TimeLineEventVisitor)eventVisitor).loadTweets();
    }

    @Override
    public String getVisitorType() {
        return responseVisitorType;
    }
}
