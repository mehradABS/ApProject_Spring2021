package events.chat;

import events.Event;
import events.visitors.EventVisitor;
import events.visitors.chat.ContactPanelEventVisitor;
import responses.Response;

public class MakeNewChatEvent extends Event {

    private final int userid;

    public MakeNewChatEvent(int userid) {
        this.userid = userid;
    }

    @Override
    public Response visit(EventVisitor eventVisitor) {
        return ((ContactPanelEventVisitor)eventVisitor).makeNewChat(userid);
    }

    @Override
    public String getVisitorType() {
        return "ContactPanelEventVisitor";
    }
}
