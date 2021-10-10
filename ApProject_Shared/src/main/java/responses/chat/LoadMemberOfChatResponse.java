package responses.chat;

import responses.Response;
import responses.visitors.ResponseVisitor;
import responses.visitors.chat.ContactPanelResponseVisitor;

import java.util.List;

public class LoadMemberOfChatResponse extends Response {

    private final List<String[]> info;

    public LoadMemberOfChatResponse(List<String[]> info) {
        this.info = info;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        ((ContactPanelResponseVisitor)responseVisitor).setMemberOfChatInGroupSettingPanel(this);
    }

    @Override
    public String getVisitorType() {
        return "ContactPanelResponseVisitor";
    }

    public List<String[]> getInfo() {
        return info;
    }
}
