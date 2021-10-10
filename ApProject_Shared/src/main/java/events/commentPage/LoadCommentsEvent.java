package events.commentPage;

import events.tweetHistory.TweetHistoryEvent;
import events.visitors.EventVisitor;
import events.visitors.commentPage.CommentPageEventVisitor;
import responses.Response;

public class LoadCommentsEvent extends TweetHistoryEvent {

    @Override
    public Response visit(EventVisitor eventVisitor) {
        return ((CommentPageEventVisitor)eventVisitor).loadComments(tweetId, messageType
        , responseVisitorType);
    }

    @Override
    public String getVisitorType() {
        return "CommentPageEventVisitor";
    }
}