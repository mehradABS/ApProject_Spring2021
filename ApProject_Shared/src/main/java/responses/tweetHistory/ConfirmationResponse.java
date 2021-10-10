package responses.tweetHistory;

import responses.Response;
import responses.visitors.ResponseVisitor;
import responses.visitors.tweetHistory.TweetHistoryResponsesVisitor;

public class ConfirmationResponse extends Response {

    private String type;
    private String visitorType = "TweetHistoryResponsesVisitor";

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        ((TweetHistoryResponsesVisitor)responseVisitor).getConfirmation(type);
    }

    @Override
    public String getVisitorType() {
        return visitorType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setVisitorType(String visitorType) {
        this.visitorType = visitorType;
    }
}