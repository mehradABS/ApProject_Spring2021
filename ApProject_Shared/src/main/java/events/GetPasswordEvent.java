package events;

import events.visitors.EventVisitor;
import events.visitors.OnlineEventVisitor;
import responses.Response;

public class GetPasswordEvent extends Event {

    @Override
    public Response visit(EventVisitor eventVisitor) {
        return ((OnlineEventVisitor)eventVisitor).sendPassword();
    }

    @Override
    public String getVisitorType() {
        return "logout";
    }
}
