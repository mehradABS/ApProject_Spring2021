package events.chat;

import events.Event;
import events.visitors.EventVisitor;
import events.visitors.chat.ContactPanelEventVisitor;
import responses.Response;

import java.util.List;

public class AddPersonEvent extends Event {

    protected final int chatId;
    protected final List<Integer> userIds;

    public AddPersonEvent(int chatId, List<Integer> userIds) {
        this.chatId = chatId;
        this.userIds = userIds;
    }

    @Override
    public Response visit(EventVisitor eventVisitor) {
        return ((ContactPanelEventVisitor)eventVisitor).addPersonToChat(chatId,
                userIds);
    }

    @Override
    public String getVisitorType() {
        return "ContactPanelEventVisitor";
    }
}
