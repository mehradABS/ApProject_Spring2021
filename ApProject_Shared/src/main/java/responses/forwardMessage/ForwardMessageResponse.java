package responses.forwardMessage;

import responses.Response;
import responses.visitors.ResponseVisitor;

import java.util.List;

public class ForwardMessageResponse extends Response {

    private List<String[]> info;
    private String visitorType;

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        ((ForwardMessageResponseVisitor)responseVisitor).getAnswer(this);
    }

    @Override
    public String getVisitorType() {
        return visitorType;
    }

    public List<String[]> getInfo() {
        return info;
    }

    public void setInfo(List<String[]> info) {
        this.info = info;
    }

    public void setVisitorType(String visitorType) {
        this.visitorType = visitorType;
    }
}