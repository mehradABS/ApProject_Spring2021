package responses.tweetHistory;

import responses.visitors.ResponseVisitor;
import responses.visitors.tweetHistory.TweetHistoryResponsesVisitor;

public class DisLikeResponse extends LikeResponse{
    @Override
    public void visit(ResponseVisitor responseVisitor) {
        ((TweetHistoryResponsesVisitor)responseVisitor).getDisLikeText(this);
    }
}
