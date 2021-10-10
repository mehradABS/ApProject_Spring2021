package events.visitors.notification;

import events.notofication.NotifEvent;
import events.visitors.EventVisitor;
import responses.Response;

public interface NotifEventVisitor extends EventVisitor {

    Response getEvent(NotifEvent event);
}
