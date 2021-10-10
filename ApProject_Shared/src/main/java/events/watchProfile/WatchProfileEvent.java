package events.watchProfile;

import events.Event;
import events.visitors.EventVisitor;
import responses.Response;

public  abstract class WatchProfileEvent extends Event {

    protected int userId;

    @Override
    public Response visit(EventVisitor eventVisitor) {
        return null;
    }

    @Override
    public String getVisitorType() {
        return "WatchProfileEventVisitor";
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
