package events.watchProfile;
import events.visitors.watchProfile.WatchProfileEventVisitor;
import events.visitors.EventVisitor;
import responses.Response;

public class FollowEvent extends WatchProfileEvent {

    @Override
    public Response visit(EventVisitor eventVisitor) {
        return ((WatchProfileEventVisitor)eventVisitor).follow(userId);
    }
}