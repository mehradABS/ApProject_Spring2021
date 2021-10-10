package events.tweetHistory;

import events.Event;
import events.visitors.EventVisitor;
import responses.Response;

public abstract class TweetHistoryEvent extends Event {

    protected String responseVisitorType;
    protected int tweetId;
    protected String messageType;

    @Override
    public Response visit(EventVisitor eventVisitor) {
        return null;
    }

    @Override
    public String getVisitorType() {
        return "TweetHistoryEventsVisitor";
    }

    public int getTweetId() {
        return tweetId;
    }

    public void setTweetId(int tweetId) {
        this.tweetId = tweetId;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getResponseVisitorType() {
        return responseVisitorType;
    }

    public void setResponseVisitorType(String responseVisitorType) {
        this.responseVisitorType = responseVisitorType;
    }
}