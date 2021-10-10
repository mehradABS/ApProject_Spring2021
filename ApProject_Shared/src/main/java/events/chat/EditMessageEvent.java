package events.chat;

import events.visitors.EventVisitor;
import events.visitors.chat.ChatMainPanelEventVisitor;
import responses.Response;

public class EditMessageEvent extends SendMessageEvent {

    @Override
    public Response visit(EventVisitor eventVisitor) {
        return ((ChatMainPanelEventVisitor)eventVisitor).editMessage(this);
    }
}
