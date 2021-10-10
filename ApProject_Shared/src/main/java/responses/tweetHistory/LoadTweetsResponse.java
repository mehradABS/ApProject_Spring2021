package responses.tweetHistory;

import responses.Response;
import responses.visitors.ResponseVisitor;
import responses.visitors.tweetHistory.TweetHistoryResponsesVisitor;

import java.util.List;

public class LoadTweetsResponse extends Response {

    private List<String[]> tweetsInfo;
    private String visitorType = "TweetHistoryResponsesVisitor";

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        ((TweetHistoryResponsesVisitor)responseVisitor).getTweets(this);
    }

    @Override
    public String getVisitorType() {
        return visitorType;
    }

    public List<String[]> getTweetsInfo() {
        return tweetsInfo;
    }

    public void setTweetsInfo(List<String[]> tweetsInfo) {
        this.tweetsInfo = tweetsInfo;
    }

    public void setVisitorType(String visitorType) {
        this.visitorType = visitorType;
    }
}