package events.chat;

import events.Event;
import events.visitors.EventVisitor;
import events.visitors.chat.ContactPanelEventVisitor;
import responses.Response;

public class LoadFollowingsEvent extends Event {

    private final int chatId;

    public LoadFollowingsEvent(int chatId) {
        this.chatId = chatId;
    }

    @Override
    public Response visit(EventVisitor eventVisitor) {
        return ((ContactPanelEventVisitor) eventVisitor).loadFollowings(chatId);
    }

    @Override
    public String getVisitorType() {
        return "ContactPanelEventVisitor";
    }
}
