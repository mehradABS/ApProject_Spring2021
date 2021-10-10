package responses.tweetHistory;

import responses.Response;
import responses.visitors.ResponseVisitor;
import responses.visitors.tweetHistory.TweetHistoryResponsesVisitor;

public class LikeResponse extends Response {

    protected int[] info;
    protected String visitorType = "TweetHistoryResponsesVisitor";

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        ((TweetHistoryResponsesVisitor)responseVisitor).getLikeText(this);
    }

    @Override
    public String getVisitorType() {
        return visitorType;
    }

    public int[] getInfo() {
        return info;
    }

    public void setInfo(int[] info) {
        this.info = info;
    }

    public void setVisitorType(String visitorType) {
        this.visitorType = visitorType;
    }
}