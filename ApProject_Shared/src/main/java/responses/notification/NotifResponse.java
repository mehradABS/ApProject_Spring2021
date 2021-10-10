package responses.notification;

import responses.Response;
import responses.visitors.ResponseVisitor;
import responses.visitors.notification.NotifResponseVisitor;

import java.util.List;

public class NotifResponse extends Response {

    private List<String[]> info;
    private String answer;

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        ((NotifResponseVisitor)responseVisitor).getAnswer(this);
    }

    @Override
    public String getVisitorType() {
        return "NotifResponseVisitor";
    }

    public List<String[]> getInfo() {
        return info;
    }

    public void setInfo(List<String[]> info) {
        this.info = info;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
