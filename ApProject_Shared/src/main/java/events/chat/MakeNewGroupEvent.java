package events.chat;

import events.Event;
import events.visitors.EventVisitor;
import events.visitors.chat.ContactPanelEventVisitor;
import responses.Response;

import java.util.List;

public class MakeNewGroupEvent extends Event {

    private final List<Integer> userIds;
    private final String chatName;

    public MakeNewGroupEvent(List<Integer> userIds, String chatName) {
        this.userIds = userIds;
        this.chatName = chatName;
    }

    @Override
    public Response visit(EventVisitor eventVisitor) {
        return ((ContactPanelEventVisitor)eventVisitor).makeNewGroupChat(userIds, chatName);
    }

    @Override
    public String getVisitorType() {
        return "ContactPanelEventVisitor";
    }
}
