package events;

import events.visitors.EventVisitor;
import responses.Response;

public class OfflineEvent extends Event{

    @Override
    public Response visit(EventVisitor eventVisitor) {
        return null;
    }

    @Override
    public String getVisitorType() {
        return "I'm offline";
    }
}
