package events.commentPage;

import events.tweetHistory.TweetHistoryEvent;
import events.visitors.EventVisitor;
import events.visitors.commentPage.CommentPageEventVisitor;
import responses.Response;

public class GetOriginalTweetEvent extends TweetHistoryEvent {

    @Override
    public Response visit(EventVisitor eventVisitor) {
        return ((CommentPageEventVisitor)eventVisitor).loadOriginalTweet(
                tweetId, messageType, responseVisitorType);
    }

    @Override
    public String getVisitorType() {
        return "CommentPageEventVisitor";
    }
}
