package responses.chat;

import responses.Response;
import responses.visitors.ResponseVisitor;
import responses.visitors.chat.ContactPanelResponseVisitor;

import java.util.List;

public class LoadFollowingsResponse extends Response {

    private final List<String[]> info;

    public LoadFollowingsResponse(List<String[]> info) {
        this.info = info;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        ((ContactPanelResponseVisitor)responseVisitor).setFollowingsInGroupSettingPanel(info);
    }

    @Override
    public String getVisitorType() {
        return "ContactPanelResponseVisitor";
    }
}
