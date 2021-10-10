package responses.watchProfile;

import responses.Response;
import responses.visitors.ResponseVisitor;
import responses.visitors.watchProfile.WatchProfileResponseVisitor;

public class WatchProfileResponse extends Response {

    private boolean f;
    private String answer;

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        ((WatchProfileResponseVisitor)responseVisitor).getAnswer(this);
    }

    @Override
    public String getVisitorType() {
        return "WatchProfileResponseVisitor";
    }

    public boolean isF() {
        return f;
    }

    public void setF(boolean f) {
        this.f = f;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}