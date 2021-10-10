package events.visitors.messages;

import events.messages.NewMessageEvent;
import events.visitors.EventVisitor;
import responses.Response;

public interface NewMessageEventVisitor extends EventVisitor {
    Response newMessage(NewMessageEvent newMessageEvent, String responseVisitorType);
}
