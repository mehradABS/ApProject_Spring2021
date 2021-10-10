package responses.commentPage;

import responses.Response;
import responses.visitors.ResponseVisitor;
import responses.visitors.commentPage.CommentPageResponseVisitor;

public class GetOriginalTweetResponse extends Response {

    private String[] info;
    private String visitorType;
    @Override
    public void visit(ResponseVisitor responseVisitor) {
        ((CommentPageResponseVisitor)responseVisitor).getOriginalTweet(this);
    }

    @Override
    public String getVisitorType() {
        return visitorType;
    }

    public String[] getInfo() {
        return info;
    }

    public void setInfo(String[] info) {
        this.info = info;
    }

    public void setVisitorType(String visitorType) {
        this.visitorType = visitorType;
    }
}