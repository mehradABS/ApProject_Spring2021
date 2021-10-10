package responses.list;

import responses.Response;
import responses.visitors.ResponseVisitor;
import responses.visitors.list.ListPanelResponseVisitor;

import java.util.List;

public class ListPanelResponse extends Response {

    private List<String[]> info;
    private String answer;

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        ((ListPanelResponseVisitor)responseVisitor).getResponse(this);
    }

    @Override
    public String getVisitorType() {
        return "ListPanelResponseVisitor";
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
