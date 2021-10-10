package events.chat;

import events.visitors.EventVisitor;
import events.visitors.chat.ContactPanelEventVisitor;
import responses.Response;

import java.util.List;

public class RemovePersonEvent extends AddPersonEvent {

    public RemovePersonEvent(int chatId, List<Integer> userIds) {
        super(chatId, userIds);
    }

    @Override
    public Response visit(EventVisitor eventVisitor) {
        return ((ContactPanelEventVisitor)eventVisitor).removePersonFromChat(chatId
        , userIds);
    }

}
