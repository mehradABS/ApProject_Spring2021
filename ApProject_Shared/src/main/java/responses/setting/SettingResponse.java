package responses.setting;

import models.auth.OUser;
import responses.Response;
import responses.visitors.ResponseVisitor;
import responses.visitors.setting.SettingPanelResponseVisitor;

public class SettingResponse extends Response {

    private OUser user;
    private String[] info;

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        ((SettingPanelResponseVisitor)responseVisitor).getResponse(this);
    }

    @Override
    public String getVisitorType() {
        return "SettingPanelResponseVisitor";
    }

    public OUser getUser() {
        return user;
    }

    public void setUser(OUser user) {
        this.user = user;
    }

    public String[] getInfo() {
        return info;
    }

    public void setInfo(String[] info) {
        this.info = info;
    }
}
