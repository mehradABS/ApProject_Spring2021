package events.watchProfile;

import events.visitors.EventVisitor;
import events.visitors.watchProfile.WatchProfileEventVisitor;
import responses.Response;

public class MuteEvent extends WatchProfileEvent {

    @Override
    public Response visit(EventVisitor eventVisitor) {
        return ((WatchProfileEventVisitor)eventVisitor).mute(userId);
    }
}
