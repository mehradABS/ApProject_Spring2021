package events.chat;

import events.Event;
import events.visitors.EventVisitor;
import events.visitors.chat.ChatMainPanelEventVisitor;
import responses.Response;

public class LoadMessagesEvent extends Event {

    private final int chatId;

    public LoadMessagesEvent(int chatId) {
        this.chatId = chatId;
    }

    @Override
    public Response visit(EventVisitor eventVisitor) {
        return ((ChatMainPanelEventVisitor)eventVisitor).loadMessages(chatId);
    }

    @Override
    public String getVisitorType() {
        return "ChatMainPanelEventVisitor";
    }
}
