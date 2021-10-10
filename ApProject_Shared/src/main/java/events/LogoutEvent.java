package events;

import events.visitors.EventVisitor;
import events.visitors.OnlineEventVisitor;
import responses.Response;

public class LogoutEvent extends Event {

    @Override
    public Response visit(EventVisitor eventVisitor) {
        return ((OnlineEventVisitor)eventVisitor).logout();
    }

    @Override
    public String getVisitorType() {
        return "logout";
    }
}
