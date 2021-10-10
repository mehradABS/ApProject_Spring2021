package responses.chat;

import responses.Response;
import responses.visitors.ResponseVisitor;
import responses.visitors.chat.ContactPanelResponseVisitor;

import java.util.List;

public class LoadGroupChatResponse extends Response {

    private List<String[]> info;

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        ((ContactPanelResponseVisitor)responseVisitor).setGroupChatsInGroupSettingPanel(this);
    }

    @Override
    public String getVisitorType() {
        return "ContactPanelResponseVisitor";
    }

    public List<String[]> getInfo() {
        return info;
    }

    public void setInfo(List<String[]> info) {
        this.info = info;
    }
}
