package events.messages;

import events.visitors.EventVisitor;
import events.visitors.messages.NewMessageEventVisitor;
import responses.Response;

public class NewCommentEvent extends NewTweetEvent{

    private int tweetId;
    private String responseVisitorType;

    @Override
    public Response visit(EventVisitor eventVisitor) {
        return ((NewMessageEventVisitor)eventVisitor).newMessage(this
        ,responseVisitorType);
    }

    @Override
    public String getVisitorType() {
        return "NewCommentEventVisitor";
    }

    public int getTweetId() {
        return tweetId;
    }

    public void setTweetId(int tweetId) {
        this.tweetId = tweetId;
    }

    public void setResponseVisitorType(String responseVisitorType) {
        this.responseVisitorType = responseVisitorType;
    }
}
