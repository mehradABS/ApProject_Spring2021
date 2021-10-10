package events.chat;

import events.Event;
import events.visitors.EventVisitor;
import events.visitors.chat.ContactPanelEventVisitor;
import responses.Response;

public class LoadChatEvent extends Event {

    @Override
    public Response visit(EventVisitor eventVisitor) {
        return ((ContactPanelEventVisitor)eventVisitor).loadChats();
    }

    @Override
    public String getVisitorType() {
        return "ContactPanelEventVisitor";
    }
}
