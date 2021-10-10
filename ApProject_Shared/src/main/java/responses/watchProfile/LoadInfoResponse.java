package responses.watchProfile;

import responses.Response;
import responses.visitors.ResponseVisitor;
import responses.visitors.watchProfile.WatchProfileResponseVisitor;

public class LoadInfoResponse extends Response {

    private String[] info;

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        ((WatchProfileResponseVisitor)responseVisitor).setInfo(this);
    }

    @Override
    public String getVisitorType() {
        return "WatchProfileResponseVisitor";
    }

    public String[] getInfo() {
        return info;
    }

    public void setInfo(String[] info) {
        this.info = info;
    }
}
