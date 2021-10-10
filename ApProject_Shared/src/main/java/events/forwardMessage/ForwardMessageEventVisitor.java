package events.forwardMessage;

import events.visitors.EventVisitor;
import responses.Response;

public interface ForwardMessageEventVisitor extends EventVisitor {

    Response getEvent(ForwardMessageEvent event);
}
