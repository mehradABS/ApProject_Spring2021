package events.chat;

import events.Event;
import events.visitors.EventVisitor;
import events.visitors.chat.NewGroupPanelEventVisitor;
import responses.Response;

public class LoadFriendsForNewGroupPanelEvent extends Event {

    @Override
    public Response visit(EventVisitor eventVisitor) {
        return ((NewGroupPanelEventVisitor)eventVisitor).loadFriends();
    }

    @Override
    public String getVisitorType() {
        return "NewGroupPanelEventVisitor";
    }
}
