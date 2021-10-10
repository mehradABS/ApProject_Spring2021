package responses.tweetHistory;

import responses.Response;
import responses.visitors.ResponseVisitor;
import responses.visitors.tweetHistory.TweetHistoryResponsesVisitor;

public class GetCommentResponse extends Response {

    private int commentsNumber;
    private String visitorType = "TweetHistoryResponsesVisitor";

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        ((TweetHistoryResponsesVisitor)responseVisitor).getCommentsNumber(commentsNumber);
    }

    @Override
    public String getVisitorType() {
        return visitorType;
    }

    public int getCommentsNumber() {
        return commentsNumber;
    }

    public void setCommentsNumber(int commentsNumber) {
        this.commentsNumber = commentsNumber;
    }

    public void setVisitorType(String visitorType) {
        this.visitorType = visitorType;
    }
}