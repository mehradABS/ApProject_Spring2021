package events.watchProfile;

import events.visitors.EventVisitor;
import events.visitors.watchProfile.WatchProfileEventVisitor;
import responses.Response;

public class BlockEvent extends WatchProfileEvent{

    @Override
    public Response visit(EventVisitor eventVisitor) {
        return ((WatchProfileEventVisitor)eventVisitor).block(userId);
    }
}
