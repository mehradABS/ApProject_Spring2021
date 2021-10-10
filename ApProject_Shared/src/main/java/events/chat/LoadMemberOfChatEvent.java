package events.chat;

import events.Event;
import events.visitors.EventVisitor;
import events.visitors.chat.ContactPanelEventVisitor;
import responses.Response;


public class LoadMemberOfChatEvent extends Event {

    private final int chatId;
    private final boolean f;

    public LoadMemberOfChatEvent(int chatId, boolean f) {
        this.f = f;
        this.chatId = chatId;
    }

    @Override
    public Response visit(EventVisitor eventVisitor) {
        return ((ContactPanelEventVisitor)eventVisitor).loadMembersOfChat(chatId, f);
    }

    @Override
    public String getVisitorType() {
        return "ContactPanelEventVisitor";
    }
}